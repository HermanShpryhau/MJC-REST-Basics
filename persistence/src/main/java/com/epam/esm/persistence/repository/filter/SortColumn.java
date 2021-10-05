package com.epam.esm.persistence.repository.filter;

public enum SortColumn {
    DATE("create_date"), NAME("name");

    private static final String NAME_SORT_PARAM = "name";
    private static final String DATE_SORT_PARAM = "date";

    private final String columnName;

    SortColumn(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }

    public static SortColumn tryInferColumn(String name) {
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
