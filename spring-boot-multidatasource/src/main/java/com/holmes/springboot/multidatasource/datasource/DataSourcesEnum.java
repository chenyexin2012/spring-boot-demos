package com.holmes.springboot.multidatasource.datasource;

public enum DataSourcesEnum {

    MASTER("master"),
    SLAVE("slave");

    private String value;

    DataSourcesEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
