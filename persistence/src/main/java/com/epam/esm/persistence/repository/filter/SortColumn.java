package com.epam.esm.persistence.repository.filter;

public enum SortColumn {
    DATE("create_date"), NAME("name");

    private final String columnName;

    SortColumn(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}
