package com.darknightcoder.ems.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String resourceName, String fieldId, long fieldValue){
        super(String.format("%s is not found with %s : %s",resourceName,fieldId,fieldValue));
    }

}
