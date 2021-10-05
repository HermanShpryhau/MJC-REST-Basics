package com.epam.esm.persistence.repository.impl;

import com.epam.esm.persistence.repository.filter.QueryFiltersConfig;
import com.epam.esm.persistence.repository.filter.SortColumn;
import com.epam.esm.persistence.repository.filter.SortDirection;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class GiftCertificatePreparedStatementCreator implements PreparedStatementCreator {
    private static final String BASE_QUERY = "SELECT Gift_certificate.id, Gift_certificate.name, " +
            "Gift_certificate.description, Gift_certificate.price, Gift_certificate.duration, " +
            "Gift_certificate.create_date, Gift_certificate.last_update_date FROM Gift_certificate";
    private static final String JOIN_TAGS = " JOIN Gift_certificate_has_Tag GchT on Gift_certificate.id = GchT.certificate JOIN Tag T on T.id = GchT.tag";
    private static final String WHERE = " WHERE ";
    private static final String TAG_NAME = "T.name LIKE  CONCAT('%', ?, '%')";
    private static final String NAME_DESCRIPTION_PATTERN = "Gift_certificate.name LIKE CONCAT('%', ?, '%') OR Gift_certificate.description LIKE CONCAT('%', ?, '%')";
    private static final String AND = " AND ";
    private static final String ORDER_BY = " ORDER BY ";

    private final QueryFiltersConfig filtersConfig;
    private boolean hasWhereSection;
    private boolean hasTagSection;

    public GiftCertificatePreparedStatementCreator(QueryFiltersConfig filtersConfig) {
        this.filtersConfig = filtersConfig;
    }

    @Override
    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        StringBuilder queryBuilder = new StringBuilder(BASE_QUERY);
        appendTagSection(queryBuilder);
        appendSearchSection(queryBuilder);
        appendSortSection(queryBuilder);
        PreparedStatement statement = con.prepareStatement(queryBuilder.toString());
        setParameters(statement);
        return statement;
    }

    private void setParameters(PreparedStatement statement) throws SQLException {
        int parameterIndex = 1;
        if (filtersConfig.hasTag()) {
            statement.setObject(parameterIndex++, filtersConfig.getTag());
        }
        if (filtersConfig.hasSearchPattern()) {
            statement.setObject(parameterIndex++, filtersConfig.getSearchPattern());
            statement.setObject(parameterIndex, filtersConfig.getSearchPattern());
        }
    }

    private void appendTagSection(StringBuilder queryBuilder) {
        if (filtersConfig.hasTag()) {
            queryBuilder.append(JOIN_TAGS);
            if (!hasWhereSection) {
                queryBuilder.append(WHERE);
                hasWhereSection = true;
            }
            queryBuilder.append(TAG_NAME);
            hasTagSection = true;
        }
    }

    private void appendSearchSection(StringBuilder queryBuilder) {
        if (filtersConfig.hasSearchPattern()) {
            if (hasTagSection) {
                queryBuilder.append(AND);
            } else {
                queryBuilder.append(WHERE);
            }
            queryBuilder.append(NAME_DESCRIPTION_PATTERN);
        }
    }

    private void appendSortSection(StringBuilder queryBuilder) {
        if (filtersConfig.hasSortParameters()) {
            Map<SortColumn, SortDirection> sortParameters = filtersConfig.getSortParameters();
            queryBuilder.append(ORDER_BY);
            for (Map.Entry<SortColumn, SortDirection> parameter : sortParameters.entrySet()) {
                queryBuilder.append(" ");
                queryBuilder.append(parameter.getKey().getColumnName());
                queryBuilder.append(" ");
                queryBuilder.append(parameter.getValue().name());
                queryBuilder.append(",");
            }
            queryBuilder.deleteCharAt(queryBuilder.lastIndexOf(","));
        }
    }
}
