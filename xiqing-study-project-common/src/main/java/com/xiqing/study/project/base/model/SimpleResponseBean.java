package com.xiqing.study.project.base.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class SimpleResponseBean implements Serializable {
    private static final long serialVersionUID = -475198552465551193L;
    // 响应代码
    private Integer code;
    // 响应信息
    private String message;

    public SimpleResponseBean(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public SimpleResponseBean(String message) {
        this.message = message;
    }


    /**
     * 响应成功
     * @author : XI.QING
     * @date : 2021/12/16
     */
    public static SimpleResponseBean SUCCESS() {
        return new SimpleResponseBean(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMessage());
    }

    /**
     * 自定义响应成功信息
     * @author : XI.QING
     * @date : 2021/12/16
     */
    public static SimpleResponseBean SUCCESS(String message) {
        return new SimpleResponseBean(ResponseEnum.SUCCESS.getCode(), message);
    }

    /**
     * 响应失败
     * @author : XI.QING
     * @date : 2021/12/16
     */
    public static SimpleResponseBean FAILED() {
        return new SimpleResponseBean(ResponseEnum.FAILED.getCode(), ResponseEnum.FAILED.getMessage());
    }

    /**
     * 自定义响应失败信息
     * @author : XI.QING
     * @date : 2021/12/16
     */
    public static SimpleResponseBean FAILED(String message) {
        return new SimpleResponseBean(ResponseEnum.FAILED.getCode(), message);
    }


}
