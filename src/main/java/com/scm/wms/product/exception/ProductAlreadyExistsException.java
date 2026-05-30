package com.scm.wms.product.exception;

public class ProductAlreadyExistsException extends RuntimeException{
    public ProductAlreadyExistsException(String msg){
        super(msg);
    }
}
