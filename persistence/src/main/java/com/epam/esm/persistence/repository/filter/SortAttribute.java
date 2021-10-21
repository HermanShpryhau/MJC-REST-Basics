package com.epam.esm.persistence.repository.filter;

public enum SortAttribute {
    DATE("createDate"), NAME("name");

    private static final String NAME_SORT_PARAM = "name";
    private static final String DATE_SORT_PARAM = "date";

    private final String attributeName;

    SortAttribute(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public static SortAttribute inferAttribute(String name) {
        switch (name) {
            case NAME_SORT_PARAM:
                return NAME;
            case DATE_SORT_PARAM:
                return DATE;
            default:
                return null;
        }
    }
}
