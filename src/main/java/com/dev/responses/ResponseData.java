package com.dev.responses;

import java.util.List;

public class ResponseData extends Response{
    private List<Object> dataSet;


    public ResponseData(boolean success, int errorCode, List<Object> dataSet){
        super(true,ErrorCodes.SUCCESS);
        this.dataSet = dataSet;
    }

    public ResponseData(List<Object> dataSet){
        this.dataSet = dataSet;
    }

    public List<Object> getDataSet() {
        return dataSet;
    }
}
