package com.example.ssodemo.models;

public class ResponseResult {
    public ResponseResult(Integer code, String msg, Object data) {
        this.msg = msg;
        this.data = data;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    private Integer code;

    private String msg;

    private Object data;
}
