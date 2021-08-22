package com.kdwu.lightninggo.common;

public enum ResultCode implements IErrorCode {
    SUCCESS(200, "OK! 請求已成功"),
    BAD_REQUEST(400, "錯誤的請求"),
    UNAUTHORIZED(401, "登入失敗或網頁未授權"),
    FORBIDDEN(403, "禁止使用"),
    NOT_FOUND(404, "找不到網頁"),
    METHOD_NOT_ALLOWED(405, "禁用请求中指定的方法"),
    SERVER_ERROR(500, "內部伺服器錯誤"),
    SERVER_UNAVAILABLE(503, "伺服器重啟")
    ;

    private int code;
    private String message;

    private ResultCode(int code, String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
