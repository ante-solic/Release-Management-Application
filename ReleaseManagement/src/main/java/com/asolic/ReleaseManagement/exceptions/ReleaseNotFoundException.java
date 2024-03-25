package com.asolic.ReleaseManagement.exceptions;

import com.asolic.ReleaseManagement.models.Release;

public class ReleaseNotFoundException extends Exception{
    public ReleaseNotFoundException(String message){
        super(message);
    }
}
