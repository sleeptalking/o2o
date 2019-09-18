package com.zx.o2o.service;

import com.zx.o2o.dto.ImageHolder;
import com.zx.o2o.dto.ProductExecution;
import com.zx.o2o.entity.Product;
import com.zx.o2o.exceptions.ProductOperationExceptions;

import java.io.InputStream;
import java.util.List;

public interface ProductService {


    /**
     * 添加商品信息及图片处理
     * @param product
     * @return
     * @throws ProductOperationExceptions
     */
    ProductExecution addProduct(Product product,ImageHolder thumbnail,List<ImageHolder> productImgList) throws ProductOperationExceptions;
}
