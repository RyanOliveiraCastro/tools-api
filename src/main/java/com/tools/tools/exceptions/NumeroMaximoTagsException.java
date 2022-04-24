package com.tools.tools.exceptions;

public class NumeroMaximoTagsException extends RuntimeException{

    public NumeroMaximoTagsException(String msg){
        super(msg);
    }

    public NumeroMaximoTagsException(String msg, Throwable e){
        super(msg, e);
    }
}
