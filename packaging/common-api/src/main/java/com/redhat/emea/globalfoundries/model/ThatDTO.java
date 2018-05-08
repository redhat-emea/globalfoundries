package com.redhat.emea.globalfoundries.model;

import java.io.Serializable;

public class ThatDTO implements Serializable {

    private String value;

    public ThatDTO() {
    }

    public ThatDTO(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "ThatDTO{" +
                "value='" + value + '\'' +
                '}';
    }
}
