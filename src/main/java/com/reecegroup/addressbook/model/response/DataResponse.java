package com.reecegroup.addressbook.model.response;

public class DataResponse<T> {
    protected T data;

    public DataResponse(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
