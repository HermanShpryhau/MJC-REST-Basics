package com.epam.esm.persistence.repository.filter;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class QueryFiltersConfig {
    private final List<String> tags;
    private final Map<SortAttribute, SortDirection> sortParameters;
    private final String searchPattern;

    private QueryFiltersConfig(List<String> tags, Map<SortAttribute, SortDirection> sortParameters, String searchPattern) {
        this.tags = tags;
        this.sortParameters = sortParameters;
        this.searchPattern = searchPattern;
    }

    public List<String> getTags() {
        return tags;
    }

    public Map<SortAttribute, SortDirection> getSortParameters() {
        return sortParameters;
    }

    public String getSearchPattern() {
        return searchPattern;
    }

    public boolean hasTags() {
        return !tags.isEmpty();
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
        private List<String> tags = new ArrayList<>();
        private final Map<SortAttribute, SortDirection> sortParameters = new EnumMap<>(SortAttribute.class);
        private String searchPattern;

        public Builder withTags(List<String> tagsList) {
            tags = tagsList;
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
            return new QueryFiltersConfig(tags, sortParameters, searchPattern);
        }
    }
}
