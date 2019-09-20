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

    /**
     * 根据商品id 删除所有详情图片
     * @param productId
     * @return
     */
    int deleteProductImgByProductId(long productId);

    /**
     * 同过商品id查询该商品详情图
     * @param productId
     * @return
     */
    List<ProductImg> queryProductImgList(Long productId);

}
