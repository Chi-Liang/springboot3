package com.springboot3.template.model.res;

public record SampleRes(Long id,String name,String mobilePhone) {

    public SampleRes(Long id, String name, String mobilePhone) {
        this.id = id;
        this.name = name;
        this.mobilePhone = mobilePhone;
    }
}