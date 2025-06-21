package com.sanem.share.ong.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Status {
    ACTIVE("active"),
    DEACTIVATED("deactivated");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    @JsonCreator
    public static Status fromString(String value) {
        for (Status s : Status.values()) {
            if (s.status.equalsIgnoreCase(value) || s.name().equalsIgnoreCase(value)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + value);
    }
}
