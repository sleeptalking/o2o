package com.zx.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zx.o2o.dto.ImageHolder;
import com.zx.o2o.dto.ProductExecution;
import com.zx.o2o.entity.Product;
import com.zx.o2o.entity.Result;
import com.zx.o2o.entity.Shop;
import com.zx.o2o.enums.ProductStateEnum;
import com.zx.o2o.exceptions.ProductOperationExceptions;
import com.zx.o2o.service.ProductService;
import com.zx.o2o.util.CodeUtil;
import com.zx.o2o.util.HttpServletRequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {

    @Autowired
    ProductService productService;

    //支持上传商品详情图片的最大数量
    private static final int IMAGEMAXCOUNT = 6;

    @RequestMapping(value = "/getproductlist", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Result<List<Product>> getProductList(HttpServletRequest request) {
        Product product = new Product();
        ObjectMapper objectMapper = new ObjectMapper();
        String productStr = HttpServletRequestUtils.getString(request, "productStr");
        int pageIndex = HttpServletRequestUtils.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtils.getInt(request, "pageSzie");
        if (productStr != null) {
            try {
                product = objectMapper.readValue(productStr, Product.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            ProductExecution pe = productService.getProductList(product, 0, 100);
            return new Result<List<Product>>(true, pe.getProductList());

        } catch (ProductOperationExceptions e) {
            return new Result<List<Product>>(false, e.getMessage());

        }
    }


    @RequestMapping(value = "/modityproduct", method = RequestMethod.POST)
    @ResponseBody
    public Result<Product> modifyProduct(HttpServletRequest request) {
        if (!CodeUtil.checkVerifyCode(request)) {
            return new Result<Product>(false, "验证码错误");
        }

        MultipartHttpServletRequest multipartRequest = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgs = new ArrayList<>();
        CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        Product product = null;
        String productStr = HttpServletRequestUtils.getString(request, "productStr");
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (resolver.isMultipart(request)) {
                multipartRequest = (MultipartHttpServletRequest) request;
                CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
                thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
                for (int i = 0; i < IMAGEMAXCOUNT; i++) {
                    CommonsMultipartFile productImg = (CommonsMultipartFile) multipartRequest.getFile("productImg" + i);
                    if (productImg != null) {
                        productImgs.add(new ImageHolder(productImg.getOriginalFilename(), productImg.getInputStream()));
                    }
                }
            }

            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            return new Result<Product>(false, e.getMessage());
        }

        if (product != null) {
            try {
                Result<Product> result = productService.modifyProduct(product, thumbnail, productImgs);
                return result;
            } catch (ProductOperationExceptions e) {
                return new Result<>(false, e.getMessage());
            }
        } else {
            return new Result<>(false, "请输入商品信息");
        }

    }

    @RequestMapping(value = "/getproductbyid", method = RequestMethod.POST)
    @ResponseBody
    public Result<Product> getProductById(HttpServletRequest request) {

        long productId = HttpServletRequestUtils.getLong(request, "productId");
        try {
            Product product = productService.getProductById(productId);
            return new Result<Product>(true, product);
        } catch (ProductOperationExceptions e) {
            return new Result<Product>(false, e.getMessage());
        }
    }

    @RequestMapping(value = "/addproduct", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误");
            return modelMap;
        }

        //接受前端参数的变量的初始化，包括商品，缩略图，商品详情图列表实体类
        ObjectMapper objectMapper = new ObjectMapper();
        Product product = null;
        String productStr = HttpServletRequestUtils.getString(request, "productStr");
        MultipartHttpServletRequest multipartRequest = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<>();
        CommonsMultipartResolver multpartResolver = new CommonsMultipartResolver(request.getServletContext());

        try {
            if (multpartResolver.isMultipart(request)) {
                multipartRequest = (MultipartHttpServletRequest) request;
                CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
                thumbnail = new ImageHolder(file.getOriginalFilename(), file.getInputStream());

                for (int i = 0; i < IMAGEMAXCOUNT; i++) {
                    CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest.getFile("productImg" + i);
                    if (productImgFile != null) {
                        ImageHolder productImg = new ImageHolder(productImgFile.getOriginalFilename(), productImgFile.getInputStream());
                        productImgList.add(productImg);
                    } else {
                        break;
                    }
                }
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传的图片不能为空");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        try {
            product = objectMapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        if (product != null && thumbnail != null && productImgList.size() > 0) {

            try {

                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                Shop shop = new Shop();
                if (currentShop == null) {
                    String shopId = HttpServletRequestUtils.getString(request, "shopId");
                    shop.setShopId(Long.parseLong(shopId));

                } else {
                    shop.setShopId(currentShop.getShopId());
                }

                product.setShop(shop);
                ProductExecution productExecution = productService.addProduct(product, thumbnail, productImgList);
                if (productExecution.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", productExecution.getStateInfo());
                }

            } catch (ProductOperationExceptions e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
            return modelMap;
        }

        return modelMap;
    }


}



























