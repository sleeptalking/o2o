package com.zx.o2o.service;

import com.zx.o2o.dto.ImageHolder;
import com.zx.o2o.dto.ProductExecution;
import com.zx.o2o.entity.Product;
import com.zx.o2o.exceptions.ProductOperationExceptions;

import java.util.List;

public interface ProductService {


    /**
     * 添加商品信息及图片处理
     * @param product
     * @return
     * @throws ProductOperationExceptions
     */
    ProductExecution addProduct(Product product,ImageHolder thumbnail,List<ImageHolder> productImgList)
            throws ProductOperationExceptions;

    /**
     * 查询商品列表
     * @param productCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ProductExecution getProductList(Product productCondition,int pageIndex,int pageSize);
}
