package com.scm.wms.exception;

public class DuplicateResourceException extends RuntimeException{
    public DuplicateResourceException(String msg){
        super(msg);
    }
}
