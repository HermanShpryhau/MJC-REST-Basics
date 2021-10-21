package com.epam.esm.persistence.repository.filter;

import java.util.EnumMap;
import java.util.Map;

public class QueryFiltersConfig {
    private final String tag;
    private final Map<SortAttribute, SortDirection> sortParameters;
    private final String searchPattern;

    private QueryFiltersConfig(String tag, Map<SortAttribute, SortDirection> sortParameters, String searchPattern) {
        this.tag = tag;
        this.sortParameters = sortParameters;
        this.searchPattern = searchPattern;
    }

    public String getTag() {
        return tag;
    }

    public Map<SortAttribute, SortDirection> getSortParameters() {
        return sortParameters;
    }

    public String getSearchPattern() {
        return searchPattern;
    }

    public boolean hasTag() {
        return tag != null;
    }

    public boolean hasSortParameters() {
        return !sortParameters.isEmpty();
    }

    public boolean hasSearchPattern() {
        return searchPattern != null;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String tag;
        private final Map<SortAttribute, SortDirection> sortParameters = new EnumMap<>(SortAttribute.class);
        private String searchPattern;

        public Builder withTag(String tagName) {
            tag = tagName;
            return this;
        }

        public Builder withSort(SortAttribute column, SortDirection direction) {
            sortParameters.put(column, direction);
            return this;
        }

        public Builder withSearchPattern(String searchPattern) {
            this.searchPattern = searchPattern;
            return this;
        }

        public QueryFiltersConfig build() {
            return new QueryFiltersConfig(tag, sortParameters, searchPattern);
        }
    }
}
