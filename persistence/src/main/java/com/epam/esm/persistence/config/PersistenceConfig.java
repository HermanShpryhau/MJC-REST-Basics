package com.epam.esm.persistence.config;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@Import({ProdDataSourceConfig.class, DevDataSourceConfig.class})
public class PersistenceConfig {
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public RowMapper<GiftCertificate> giftCertificateRowMapper() {
        return new BeanPropertyRowMapper<>(GiftCertificate.class);
    }

    @Bean
    public RowMapper<Tag> tagRowMapper() {
        return new BeanPropertyRowMapper<>(Tag.class);
    }

    @Bean
    @Qualifier("GiftCertificateJdbcInsert")
    public SimpleJdbcInsert giftCertificateJdbcInsert(JdbcTemplate jdbcTemplate) {
        return new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Gift_certificate")
                .usingGeneratedKeyColumns("id")
                .usingColumns("name", "description", "price", "duration");
    }

    @Bean
    @Qualifier("TagJdbcInsert")
    public SimpleJdbcInsert tagJdbcInsert(JdbcTemplate jdbcTemplate) {
        return new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Tag")
                .usingGeneratedKeyColumns("id");
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
