package com.scm.wms.warehouse.exception;

public class DuplicateResourceException extends RuntimeException{
    public DuplicateResourceException(String msg){
        super(msg);
    }
}
