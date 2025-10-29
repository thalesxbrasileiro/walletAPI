package com.udemy.wallet.util.enums;

public enum TypeEnum {

    EN("ENTRADA"),
    SA("SAÍDA");

    private final String value;

    TypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TypeEnum getEnum(String value) {
        for (TypeEnum type : values()) {
            if (value.equals(type.getValue())) {
                return type;
            }
        }
        return null;
    }

}
