package com.epam.esm.persistence.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

@Configuration
@Profile("prod")
@PropertySource("classpath:db.properties")
public class ProdDataSourceConfig {
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
}
