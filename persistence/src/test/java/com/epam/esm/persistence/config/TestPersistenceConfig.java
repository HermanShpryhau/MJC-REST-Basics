package com.epam.esm.persistence.config;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.epam.esm")
public class TestPersistenceConfig {
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("create_db.sql")
                .build();
    }

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
}
