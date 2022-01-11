package com.dev.responses;

public class Response {

    private boolean success;
    private int errorCode;

    public Response(){
        this.success = true;
        this.errorCode = ErrorCodes.SUCCESS;
    }

    public Response(boolean success, int errorCode){
        this.success = success;
        this.errorCode = errorCode;
    }


}
