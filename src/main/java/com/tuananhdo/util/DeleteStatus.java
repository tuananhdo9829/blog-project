package com.tuananhdo.util;

public enum DeleteStatus {
    ACTIVE("ACTIVE"),
    DELETED("DELETED");

    private final String value;

    DeleteStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
