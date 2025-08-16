package com.learn.resource_management_service.constants;

public enum ResourceTypes {
    SECTION("Section");

    String value;

    ResourceTypes(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static ResourceTypes fromValue(String value) {
        for (ResourceTypes type : ResourceTypes.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with value: " + value);
    }
}
