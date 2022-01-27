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

    public Response(boolean success){
        this.success = success;
        this.errorCode = success ? ErrorCodes.SUCCESS : ErrorCodes.GENERAL_ERROR;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
