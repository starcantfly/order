package com.imooc.order_new.VO;

import lombok.Data;

import javax.persistence.Table;
@Data
public class ResultVO<T> {

    private Integer code;

    private String msg;

    private T data;
}
