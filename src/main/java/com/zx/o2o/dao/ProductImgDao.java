package com.zx.o2o.dao;

import com.zx.o2o.entity.ProductImg;

import java.util.List;

public interface ProductImgDao {

    /**
     * 批量添加商品图片
     * @param productImgs
     * @return
     */
    int batchInsertProductImg(List<ProductImg> productImgs);

}
