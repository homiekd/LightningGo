package com.kdwu.lightninggo.common;

import lombok.Data;

@Data
public class CommonResult<T> {
    private int code;
    private String message;
    private T data;

    protected CommonResult(){
    }

    protected CommonResult(int code, String message, T data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回結果
     * @param data 獲取的數據
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> success(T data){
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回結果
     * @param data 獲取的數據
     * @param message 提示訊息
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> success(T data, String message){
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 失敗返回結果
     * @param errorCode 錯誤狀態碼
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> failed(IErrorCode errorCode){
        return new CommonResult<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 失敗返回結果
     * @param message
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> failed(String message){
        return new CommonResult<T>(ResultCode.SERVER_ERROR.getCode(), message, null);
    }

    /**
     * 失敗返回結果
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> failed(){
        return failed(ResultCode.SERVER_ERROR);
    }

    /**
     * 找不到網頁失敗返回結果
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> validateFailed(){
        return failed(ResultCode.NOT_FOUND);
    }

    /**
     * 找不到網頁失敗返回結果
     * @param message 提示訊息
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> validateFailed(String message){
        return new CommonResult<T>(ResultCode.NOT_FOUND.getCode(), message, null);
    }

    /**
     * 登入失敗或網頁未授權返回結果
     */
    public static <T> CommonResult<T> unauthorized(T data) {
        return new CommonResult<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 禁止使用返回結果
     */
    public static <T> CommonResult<T> forbidden(T data) {
        return new CommonResult<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
    }
}
