package com.darknightcoder.ems.exception;

public class DeleteResourceException extends RuntimeException{
    public DeleteResourceException(String resource, long id){
        super(String.format("%s with Id : %s cannot be deleted",resource,id));
    }
}
