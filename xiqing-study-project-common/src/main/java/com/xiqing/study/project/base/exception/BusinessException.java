package com.xiqing.study.project.base.exception;
/**
 * 自定义异常
 * @author : XI.QING 
 * @date : 2021/12/17       
 */
public class BusinessException extends RuntimeException{

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }
}
