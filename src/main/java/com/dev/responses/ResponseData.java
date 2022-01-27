package com.dev.responses;

import java.util.List;

public class ResponseData extends Response{
    private List<Object> dataSet;


    public ResponseData(boolean success, int errorCode, List<Object> dataSet){
        super(success,errorCode);
        this.dataSet = dataSet;
    }

    public ResponseData(List<Object> dataSet){ // notice we have a default constructor in Response() that makes success "true" automatically
        this.dataSet = dataSet;
        if(dataSet == null){
            super.setSuccess(false);
            super.setErrorCode(ErrorCodes.GENERAL_ERROR);
        }
    }

    public List<Object> getDataSet() {
        return dataSet;
    }

    public void setResponseWithData(boolean b, int errorCode, String s) {
    }
}
