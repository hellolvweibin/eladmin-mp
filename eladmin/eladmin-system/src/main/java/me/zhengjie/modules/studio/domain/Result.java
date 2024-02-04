package me.zhengjie.modules.studio.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {

    //判断结果
    private int code;
    //返回信息
    private String message;
    /**
     * 返回结果
     **/
    private T data;
}
