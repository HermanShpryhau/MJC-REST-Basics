package com.epam.esm.persistence.repository.filter;

import java.util.Map;
import java.util.Set;

public class QueryFiltersConfig {
    private String tag;
    private Map<SortColumn, SortDirection> sortParameters;
    private String searchPattern;

    private QueryFiltersConfig(String tag, Map<SortColumn, SortDirection> sortParameters, String searchPattern) {
        this.tag = tag;
        this.sortParameters = sortParameters;
        this.searchPattern = searchPattern;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Map<SortColumn, SortDirection> getSortParameters() {
        return sortParameters;
    }

    public void setSortParameters(Map<SortColumn, SortDirection> sortParameters) {
        this.sortParameters = sortParameters;
    }

    public String getSearchPattern() {
        return searchPattern;
    }

    public void setSearchPattern(String searchPattern) {
        this.searchPattern = searchPattern;
    }

    public boolean hasTag() {
        return tag != null;
    }

    public boolean hasSortParameters() {
        return sortParameters != null && !sortParameters.isEmpty();
    }

    public boolean hasSearchPattern() {
        return searchPattern != null;
    }

    public static class Builder {
        private String tag;
        private Map<SortColumn, SortDirection> sortParameters;
        private String searchPattern;

        public Builder withTag(String tagName) {
            tag = tagName;
            return this;
        }

        public Builder withSort(SortColumn column, SortDirection direction) {
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
