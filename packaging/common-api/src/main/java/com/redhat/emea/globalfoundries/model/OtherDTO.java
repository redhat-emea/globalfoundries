package com.redhat.emea.globalfoundries.model;

import java.io.Serializable;

public class OtherDTO implements Serializable {

    private String value;

    public OtherDTO() {
    }

    public OtherDTO(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "OtherDTO{" +
                "value='" + value + '\'' +
                '}';
    }
}
