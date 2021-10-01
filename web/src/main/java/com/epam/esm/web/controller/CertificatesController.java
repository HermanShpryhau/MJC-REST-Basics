package com.epam.esm.web.controller;

import com.epam.esm.domain.Tag;
import com.epam.esm.domain.dto.GiftCertificateDto;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificates")
public class CertificatesController {
    private final GiftCertificateService certificateService;

    @Autowired
    public CertificatesController(GiftCertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping
    public List<GiftCertificateDto> getAllCertificates() {
        return certificateService.fetchAllCertificates();
    }

    @GetMapping("/{id}")
    public GiftCertificateDto getById(@PathVariable long id) {
        return certificateService.fetchCertificateById(id);
    }

    @GetMapping("/{id}/tags")
    public List<Tag> getAssociatedTags(@PathVariable long id) {
        return certificateService.fetchAssociatedTags(id);
    }

    @PostMapping
    public GiftCertificateDto addCertificate(@RequestBody GiftCertificateDto certificate) {
        return certificateService.addCertificate(certificate);
    }

    @PatchMapping
    public GiftCertificateDto updateCertificate(@RequestBody GiftCertificateDto certificate) {
        return certificateService.updateCertificate(certificate);
    }

    @DeleteMapping("/{id}")
    public void deleteCertificate(@PathVariable long id) {
        certificateService.deleteCertificate(id);
    }
}
