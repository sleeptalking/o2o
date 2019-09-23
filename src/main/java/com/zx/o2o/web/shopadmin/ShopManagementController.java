package com.zx.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zx.o2o.dto.ImageHolder;
import com.zx.o2o.dto.ShopExecution;
import com.zx.o2o.entity.Area;
import com.zx.o2o.entity.PersonInfo;
import com.zx.o2o.entity.Shop;
import com.zx.o2o.entity.ShopCategory;
import com.zx.o2o.enums.ShopStateEnum;
import com.zx.o2o.service.AreaService;
import com.zx.o2o.service.ShopCategoryService;
import com.zx.o2o.service.ShopService;
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
public class ShopManagementController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/getshopmanagementinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopManagementIfo(HttpServletRequest request){
        Map<String,Object> modleMap = new HashMap<>();

        Long shopId = HttpServletRequestUtils.getLong(request,"shopId");
        if(shopId <= 0){
            Object shopCurrentObj = request.getSession().getAttribute("currentShop");
            if(shopCurrentObj == null){
                modleMap.put("redirect",true);
                modleMap.put("url","/o2o/shopadmin/shoplist");
            }else{
                Shop currentShop = (Shop) shopCurrentObj;
                modleMap.put("redirect",false);
                modleMap.put("shopId",currentShop.getShopId());
            }
        }else{
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currrentShop",currentShop);
            modleMap.put("redirect",false);
        }
        return modleMap;
    }


    @RequestMapping(value = "/getshoplist",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopList(HttpServletRequest request){
        Map<String,Object> modleMap = new HashMap<>();
        PersonInfo user = new PersonInfo();
        user.setUserId(1l);
        user.setName("test");
        request.getSession().setAttribute("user",user);
        user = (PersonInfo) request.getSession().getAttribute("user");

        try{
            Shop shopCondition = new Shop();
            shopCondition.setOwner(user);
            ShopExecution se = shopService.getShopList(shopCondition,0,100);
            modleMap.put("shopList",se.getShopList());
            modleMap.put("user",user);
            modleMap.put("success",true);
        }catch (Exception e){
            modleMap.put("success",false);
            modleMap.put("message",e.getMessage());
        }

        return modleMap;
    }


    @RequestMapping(value = "/getshopbyid",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    private Map<String, Object> getShopById(HttpServletRequest request) {
        Map<String, Object> modleMap = new HashMap<>();
        Long shopId = HttpServletRequestUtils.getLong(request, "shopId");
        if (shopId > -1) {
            try {
                Shop shop = shopService.getByShopId(shopId);
                List<Area> areaList = areaService.getAreaList();
                modleMap.put("success", true);
                modleMap.put("shop", shop);
                modleMap.put("areaList", areaList);
            } catch (Exception e) {
                modleMap.put("success", false);
                modleMap.put("errMsg", e.toString());
            }
        } else {
            modleMap.put("success", false);
            modleMap.put("errMsg", "empty shopId");
        }
        return modleMap;

    }

    @RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopInitInfo() {
        Map<String, Object> modleMap = new HashMap<>();
        List<ShopCategory> shopCategoryList = new ArrayList<>();
        List<Area> areaList = new ArrayList<>();
        try {
            shopCategoryList = shopCategoryService.getShopCategory(new ShopCategory());
            areaList = areaService.getAreaList();
            modleMap.put("shopCategoryList", shopCategoryList);
            modleMap.put("areaList", areaList);
            modleMap.put("success", true);
        } catch (Exception e) {
            modleMap.put("success", false);
            modleMap.put("errMsg", e.getMessage());
        }
        return modleMap;

    }

    @RequestMapping(value = "/registershop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> registerShop(HttpServletRequest request) {
        Map<String, Object> modleMap = new HashMap<>();

        if (!CodeUtil.checkVerifyCode(request)) {
            modleMap.put("success", false);
            modleMap.put("errMsg", "验证码错误!");
            return modleMap;
        }

        //1.接收并转换相应的参数，包括商铺信息和图片信息
        String shopStr = HttpServletRequestUtils.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modleMap.put("success", false);
            modleMap.put("errMsg", e.getMessage());
            return modleMap;
        }

        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
      //  if (true) {
        boolean b = commonsMultipartResolver.isMultipart(request);

        if (b) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        } else {
            modleMap.put("success", false);
            modleMap.put("errMsg", "上传图片不能为空");
            return modleMap;
        }
        //2.注册店铺
        if (shop != null && shopImg != null) {
            PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
            if(owner == null){
                owner = new PersonInfo();
                owner.setUserId(1l);
            }
            shop.setOwner(owner);
            ShopExecution shopExecution = null;
            try {
                ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(),shopImg.getInputStream());
                shopExecution = shopService.addShop(shop,imageHolder);
                if (shopExecution.getState() == ShopStateEnum.CHECK.getState()) {
                    modleMap.put("success", true);
                    //该用户可以操作的店铺列表
                    List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
                    if(shopList == null ||shopList.size() ==0){
                        shopList = new ArrayList<>();
                    }
                    shopList.add(shopExecution.getShop());
                    request.getSession().setAttribute("shopList",shopList);

                } else {
                    modleMap.put("success", false);
                    modleMap.put("errMsg", shopExecution.getStateInfo());
                }
            } catch (IOException e) {
                modleMap.put("success", false);
                modleMap.put("errMsg", e.getMessage());
            }
            return modleMap;
        } else {
            modleMap.put("success", false);
            modleMap.put("errMsg", "请输入商铺信息");
            return modleMap;
        }
    }
    @RequestMapping(value = "/upimage", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> upImage(HttpServletRequest request) {
        Map<String, Object> modleMap = new HashMap<>();
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        boolean b = commonsMultipartResolver.isMultipart(request);
        if (b) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");

        } else {
            modleMap.put("success", false);
            modleMap.put("errMsg", "上传图片不能为空");
            return modleMap;
        }

        return modleMap;
    }
    @RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyShop(HttpServletRequest request) {
        Map<String, Object> modleMap = new HashMap<>();

        if (!CodeUtil.checkVerifyCode(request)) {
            modleMap.put("success", false);
            modleMap.put("errMsg", "验证码错误!");
            return modleMap;
        }

        //1.接收并转换相应的参数，包括商铺信息和图片信息
        String shopStr = HttpServletRequestUtils.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modleMap.put("success", false);
            modleMap.put("errMsg", e.getMessage());
            return modleMap;
        }
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
         if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }

        //2.修改店铺信息
        if (shop != null && shop.getShopId() != null) {
            ShopExecution shopExecution = null;
            try {
                if(shopImg == null){
                    shopExecution = shopService.modifyShop(shop, null);
                }else{
                    ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(),shopImg.getInputStream());
                    shopExecution = shopService.modifyShop(shop, imageHolder);
                }
                if (shopExecution.getState() == ShopStateEnum.SUCCESS.getState()) {
                    modleMap.put("success", true);
                } else {
                    modleMap.put("success", false);
                    modleMap.put("errMsg", shopExecution.getStateInfo());
                }
            } catch (IOException e) {
                modleMap.put("success", false);
                modleMap.put("errMsg", e.getMessage());
            }
            return modleMap;
        } else {
            modleMap.put("success", false);
            modleMap.put("errMsg", "请输入商铺ID");
            return modleMap;
        }
    }

}
