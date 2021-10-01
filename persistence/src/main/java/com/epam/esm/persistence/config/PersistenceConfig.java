package com.epam.esm.persistence.config;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:db.properties")
public class PersistenceConfig {
    @Bean
    public DataSource dataSource(@Value("${jdbc.username}") String username,
                                 @Value("${jdbc.password}") String password,
                                 @Value("${jdbc.driverClassName}") String className,
                                 @Value("${jdbc.url}") String connectionUrl,
                                 @Value("${jdbc.connections}") Integer connectionsNumber) {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);
        basicDataSource.setDriverClassName(className);
        basicDataSource.setUrl(connectionUrl);
        basicDataSource.setMaxActive(connectionsNumber);
        return basicDataSource;
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
