package com.xiqing.study.project.base.model;

import lombok.Data;

@Data
public class DataResponseBean<T>{

    private Integer code;
    private String message;
    private T data;

    public DataResponseBean(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public DataResponseBean(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public DataResponseBean(T data) {
        this.data = data;
    }


    /**
     * 带数据响应成功
     * @author : XI.QING
     * @date : 2021/12/16
     */
    public static <T> DataResponseBean<T> SUCCESS(T data){
        return new DataResponseBean<T>(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMessage(), data);
    }
    /**
     * 自定义带数据响应成功
     * @author : XI.QING
     * @date : 2021/12/16
     */
    public static <T> DataResponseBean<T> SUCCESS(String message, T data){
        return new DataResponseBean<T>(ResponseEnum.SUCCESS.getCode(), message, data);
    }
    /**
     * 带数据响应失败
     * @author : XI.QING
     * @date : 2021/12/16
     */
    public static <T> DataResponseBean<T> FAILED(T data){
        return new DataResponseBean<T>(ResponseEnum.FAILED.getCode(), ResponseEnum.FAILED.getMessage(), data);
    }
    /**
     * 自定义带数据响应失败
     * @author : XI.QING
     * @date : 2021/12/16
     */
    public static <T> DataResponseBean<T> FAILED(String message, T data){
        return new DataResponseBean<T>(ResponseEnum.FAILED.getCode(), message, data);
    }

}
