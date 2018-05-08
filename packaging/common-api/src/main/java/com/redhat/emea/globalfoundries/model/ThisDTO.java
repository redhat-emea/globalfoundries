package com.redhat.emea.globalfoundries.model;

public class ThisDTO {

    private String value;

    public ThisDTO() {
    }

    public ThisDTO(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "ThisDTO{" +
                "value='" + value + '\'' +
                '}';
    }

}
