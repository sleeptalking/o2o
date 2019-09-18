package com.zx.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zx.o2o.dto.ProductCategoryExecution;
import com.zx.o2o.entity.ProductCategory;
import com.zx.o2o.entity.Result;
import com.zx.o2o.entity.Shop;
import com.zx.o2o.enums.ProductCategoryStateEnum;
import com.zx.o2o.exceptions.ProductCategoryOperationExceptions;
import com.zx.o2o.service.ProductCategoryService;
import com.zx.o2o.util.HttpServletRequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/shopadmin")
@Controller
public class ProductCategoryManagementController {


    @Autowired
    ProductCategoryService productCategoryService;

    @ResponseBody
    @RequestMapping(value = "/getproductcategorylist", method = {RequestMethod.POST,RequestMethod.GET})
    public Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request) {
        Shop shop = new Shop();
        request.getSession().setAttribute("currentShop", shop);
        Long shopId = HttpServletRequestUtils.getLong(request,"shopId");
        if(shopId == null){
            shop.setShopId(1l);
        }else{
            shop.setShopId(shopId);
        }

        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        List<ProductCategory> list = null;
        if (currentShop != null && currentShop.getShopId() > 0) {
            list = productCategoryService.getProductCategoryList(currentShop.getShopId());
            return new Result<List<ProductCategory>>(true, list);
        } else {
            ProductCategoryStateEnum pc = ProductCategoryStateEnum.INNER_ERROR;
            return new Result<List<ProductCategory>>(false, pc.getState(), pc.getStateInfo());
        }

    }

    @RequestMapping(value = "/addproductcatrogys", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();

        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        for (ProductCategory pc : productCategoryList) {
            pc.setShopId(currentShop.getShopId());
        }

        if (productCategoryList != null && productCategoryList.size() > 0) {
            try {
                ProductCategoryExecution productCategoryExecution = productCategoryService.batchAddProductCategory(productCategoryList);
                if (productCategoryExecution.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                    modelMap.put("errMsg", "");
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", productCategoryExecution.getStateIfon());
                }
            } catch (ProductCategoryOperationExceptions e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请至少输入一个商品类别");
        }

        return modelMap;
    }

    @RequestMapping(value = "/addproductcatrogy", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addProductCategory(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();

        String categoryStr = HttpServletRequestUtils.getString(request, "productCategory");
        long shopId = HttpServletRequestUtils.getLong(request,"shopId");
        System.out.println("/////"+categoryStr);
        ObjectMapper mapper = new ObjectMapper();
        ProductCategory productCategory = null;
        try {
            productCategory = mapper.readValue(categoryStr, ProductCategory.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        productCategory.setShopId(shopId);
        List<ProductCategory> productCategoryList = new ArrayList<>();
        productCategoryList.add(productCategory);
        try {
            ProductCategoryExecution productCategoryExecution = productCategoryService.batchAddProductCategory(productCategoryList);
            if (productCategoryExecution.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
                modelMap.put("errMsg", "");
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", productCategoryExecution.getStateIfon());
            }

        } catch (ProductCategoryOperationExceptions e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        return modelMap;

    }


    @RequestMapping(value = "/removeproductcategory", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> removeProductCategory(Long productCategoryId, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        if (productCategoryId == null || productCategoryId <= 0) {
            productCategoryId = HttpServletRequestUtils.getLong(request,"productCategoryId");
        }
        if (productCategoryId != null && productCategoryId > 0) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                Long shopId;
                if(currentShop != null){
                    shopId = currentShop.getShopId();
                }else{
                    shopId = HttpServletRequestUtils.getLong(request,"shopId");
                }

                ProductCategoryExecution productCategoryExecution = productCategoryService.deleteProductCategory(productCategoryId, shopId);
                if (productCategoryExecution.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                    modelMap.put("errMsg", "传入的商品类别为空");
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", productCategoryExecution.getStateIfon());
                }
            } catch (ProductCategoryOperationExceptions e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请至少输入一个商品类别");
        }
        return modelMap;

    }


   /* public Map<String, Object> getProductCategoryList(HttpServletRequest request) {

        Shop shop = new Shop();
        shop.setShopId(1l);
        request.getSession().setAttribute("currentShop",shop);

        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");

        Map<String, Object> modleMap = new HashMap<>();
        try {
            List<ProductCategory> list = productCategoryService.getProductCategoryList(currentShop.getShopId());
            modleMap.put("success", true);
            modleMap.put("productCategoryList", list);
        } catch (Exception e) {
            modleMap.put("success", false);
            modleMap.put("message", e.getMessage());
        }
        return modleMap;
    }*/

}
