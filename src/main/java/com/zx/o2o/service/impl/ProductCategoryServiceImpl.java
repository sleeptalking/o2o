package com.zx.o2o.service.impl;

import com.zx.o2o.dao.ProductCategoryDao;
import com.zx.o2o.dto.ProductCategoryExecution;
import com.zx.o2o.entity.ProductCategory;
import com.zx.o2o.enums.ProductCategoryStateEnum;
import com.zx.o2o.exceptions.ProductCategoryOperationExceptions;
import com.zx.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    ProductCategoryDao productCategoryDao;

    @Override
    public List<ProductCategory> getProductCategoryList(Long shopId) {
        return productCategoryDao.queryProductCategoryByShopId(shopId);
    }

    @Override
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationExceptions {
        if(productCategoryList != null && productCategoryList.size() > 0){
            int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
            try{
                if(effectedNum <= 0){
                    throw  new ProductCategoryOperationExceptions("店铺类别创建失败");
                }else{
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS,productCategoryList);
                }
            }catch (Exception e){
               throw  new ProductCategoryOperationExceptions("batchAddProductCategory err"+e.getMessage());
            }

        }else{
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }

    }

    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(long productCategory, long shopId) throws ProductCategoryOperationExceptions{
        //TODO 清空该商品类别下的商品

        try{
            int effectedNum = productCategoryDao.deleteProductCategory(productCategory,shopId);
            if(effectedNum <= 0){
                throw  new ProductCategoryOperationExceptions("商品类别删除失败");
            }else{
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            }

        }catch (Exception e){
            throw new ProductCategoryOperationExceptions("deleteProductCategory err: "+e.getMessage());
        }

    }


}
