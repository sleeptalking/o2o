package com.zx.o2o.entity;

public class Result<T> {
    private boolean success;
    private int errorCode;
    private String errorMsg;
    private T data;

    public Result(){}
    public Result(boolean success,T data){
        this.success = success;
        this.data = data;
    }
    public Result(boolean success,int errorCode,String errorMsg){
        this.success = success;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;

    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
