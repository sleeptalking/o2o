package com.zx.o2o.entity;

public class Result<T> {
    private boolean success;
    private int code;
    private String msg;
    private T data;

    public Result() {
    }

    public Result(boolean success, T data) {
        this.success = success;
        this.data = data;
        this.code = 200;
        this.msg = "成功";
    }

    public Result(boolean success, int code, String msg) {
        this.success = success;
        this.data = null;
        this.code = code;
        this.msg = msg;

    }

    public Result(boolean success, String msg) {
        this.success = success;
        this.data = null;
        if (!success) {
            this.code = -1;
        }
        this.msg = msg;

    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
