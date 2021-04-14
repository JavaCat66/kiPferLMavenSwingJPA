package com.javahelps.kiPferL.viewing.mainFrame;

import java.util.Objects;

public class MyComponentItem<String> {

    private final String keyID;
    private final String value;

    public MyComponentItem(String keyId, String value) {
        this.keyID = Objects.requireNonNullElseGet(keyId, () -> (String) "");
        this.value = Objects.requireNonNullElseGet(value, () -> (String) "");
    }

    public String getKeyID() {
        return keyID;
    }
    public String getValue() {
        return value;
    }

    @Override
    public java.lang.String toString() {
        return (java.lang.String) this.value;
    }
}
