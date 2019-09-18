package com.zx.o2o.dto;

import com.zx.o2o.entity.ProductCategory;
import com.zx.o2o.enums.ProductCategoryStateEnum;

import java.util.List;

public class ProductCategoryExecution {

    private String stateIfon;
    private int state;
    private List<ProductCategory> productCategoryList;
    public ProductCategoryExecution(){

    }

    public ProductCategoryExecution(ProductCategoryStateEnum productCategoryStateEnum) {
        this.state = productCategoryStateEnum.getState();
        this.stateIfon = productCategoryStateEnum.getStateInfo();
    }
    public ProductCategoryExecution(ProductCategoryStateEnum productCategoryStateEnum,List<ProductCategory> productCategoryList) {
        this.state = productCategoryStateEnum.getState();
        this.stateIfon = productCategoryStateEnum.getStateInfo();
        this.productCategoryList = productCategoryList;
    }


    public String getStateIfon() {
        return stateIfon;
    }

    public void setStateIfon(String stateIfon) {
        this.stateIfon = stateIfon;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<ProductCategory> getProductCategoryList() {
        return productCategoryList;
    }

    public void setProductCategoryList(List<ProductCategory> productCategoryList) {
        this.productCategoryList = productCategoryList;
    }
}
