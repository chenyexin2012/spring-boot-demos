package com.holmes.springboot.web.model;

import lombok.Data;

@Data
public class ResponseData {

    /**
     * 成功
     */
    private static final Integer SUCCESS_CODE = 200;

    /**
     * 未认证
     */
    private static final Integer UNAUTHORIZED_CODE = 401;

    /**
     * 为授权
     */
    private static final Integer FORBIDDEN_CODE = 403;

    /**
     * 失败
     */
    private static final Integer FAILED_CODE = 500;


    private Integer code;

    private String message;

    private Object data;

    public ResponseData success() {
        this.code = SUCCESS_CODE;
        return this;
    }

    public ResponseData success(Object data) {
        this.code = SUCCESS_CODE;
        this.data = data;
        return this;
    }

    public ResponseData success(Object data, String message) {
        this.code = SUCCESS_CODE;
        this.message = message;
        this.data = data;
        return this;
    }

    public ResponseData fail(String message) {
        this.code = FAILED_CODE;
        this.message = message;
        return this;
    }

    public ResponseData forbidden(String message) {
        this.code = FORBIDDEN_CODE;
        this.message = message;
        return this;
    }

    public ResponseData unauthorized(String message) {
        this.code = UNAUTHORIZED_CODE;
        this.message = message;
        return this;
    }
}
