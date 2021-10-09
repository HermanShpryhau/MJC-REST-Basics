package com.epam.esm.service.config;

import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.service.GiftCertificateDtoTranslator;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.impl.GiftCertificateDtoTranslatorImpl;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.service.impl.TagServiceImpl;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceTestConfig {
    @Bean
    public TagRepository mockTagRepository() {
        return Mockito.mock(TagRepository.class);
    }

    @Bean
    public GiftCertificateRepository mockGiftCertificateRepository() {
        return Mockito.mock(GiftCertificateRepository.class);
    }

    @Bean
    @Qualifier("mockTranslator")
    public GiftCertificateDtoTranslator mockDtoTranslator() {
        return Mockito.mock(GiftCertificateDtoTranslator.class);
    }

    @Bean
    @Qualifier("realTranslator")
    public GiftCertificateDtoTranslator realDtoTranslator(GiftCertificateRepository repository) {
        return new GiftCertificateDtoTranslatorImpl(repository);
    }

    @Bean
    public GiftCertificateService giftCertificateServiceTestBean(
            GiftCertificateRepository giftCertificateRepository,
            TagRepository tagRepository,
            @Qualifier("mockTranslator") GiftCertificateDtoTranslator dtoTranslator) {
        return new GiftCertificateServiceImpl(giftCertificateRepository, tagRepository, dtoTranslator);
    }

    @Bean
    public TagService tagServiceTestBean(TagRepository tagRepository,
                                         @Qualifier("mockTranslator") GiftCertificateDtoTranslator dtoTranslator) {
        return new TagServiceImpl(tagRepository, dtoTranslator);
    }
}