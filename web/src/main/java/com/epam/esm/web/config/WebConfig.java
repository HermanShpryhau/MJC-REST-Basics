package com.epam.esm.web.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
@PropertySource("classpath:/webapp.properties")
public class WebConfig {
    @Bean
    @Qualifier("errorMessageSource")
    public MessageSource errorMessageSource(
            @Value("${app.errorMessagesFilename}") String resourceBundleBaseName,
            @Value("${app.defaultEncoding}") String defaultEncoding) {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename(resourceBundleBaseName);
        source.setDefaultEncoding(defaultEncoding);
        return source;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
