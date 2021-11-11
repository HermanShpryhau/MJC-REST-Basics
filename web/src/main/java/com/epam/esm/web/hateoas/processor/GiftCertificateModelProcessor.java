package com.epam.esm.web.hateoas.processor;

import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.web.controller.CertificatesController;
import com.epam.esm.web.controller.TagController;
import com.epam.esm.web.hateoas.model.GiftCertificateModel;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GiftCertificateModelProcessor implements RepresentationModelProcessor<GiftCertificateModel> {

    public CollectionModel<GiftCertificateModel> process(
            Optional<List<String>> tagNames,
            Optional<List<String>> sortTypes,
            Optional<String> searchPattern,
            Integer size,
            Page<GiftCertificateDto> page,
            CollectionModel<GiftCertificateModel> collectionModel
    ) {
        List<Link> paginationLinks = new ArrayList<>();
        if (page.hasPrevious()) {
            int previousPage = page.getNumber() - 1;
            Link previousPageLink = linkTo(getCertificatesMethod(tagNames, sortTypes, searchPattern, size,
                    previousPage))
                    .withRel("prev")
                    .expand();
            paginationLinks.add(previousPageLink);
        }

        if (page.hasNext()) {
            int nextPage = page.getNumber() + 1;
            Link nextPageLink = linkTo(getCertificatesMethod(tagNames, sortTypes, searchPattern, size, nextPage))
                    .withRel("next")
                    .expand();
            paginationLinks.add(nextPageLink);
        }

        Link firstPageLink = linkTo(getCertificatesMethod(tagNames, sortTypes, searchPattern, size,
                0))
                .withRel("first")
                .expand();
        paginationLinks.add(firstPageLink);

        int lastPage = page.getTotalPages();
        Link lastPageLink = linkTo(getCertificatesMethod(tagNames, sortTypes, searchPattern, size,
                lastPage))
                .withRel("last")
                .expand();
        paginationLinks.add(lastPageLink);

        return collectionModel.add(paginationLinks);
    }

    private CollectionModel<GiftCertificateModel> getCertificatesMethod(Optional<List<String>> tagNames,
                                                                        Optional<List<String>> sortTypes,
                                                                        Optional<String> searchPattern,
                                                                        Integer size, Integer page) {
        return methodOn(CertificatesController.class).getAllCertificatesWithFilters(
                tagNames, sortTypes, searchPattern, page, size
        );
    }

    public CollectionModel<GiftCertificateModel> process(Long tagId, Integer size, Page<GiftCertificateDto> page,
                                                         CollectionModel<GiftCertificateModel> collectionModel) {
        List<Link> paginationLinks = new ArrayList<>();

        if (page.hasPrevious()) {
            int previousPage = page.getNumber() - 1;
            Link previousPageLink = linkTo(getAssociatedCertificatesMethod(tagId, previousPage, size))
                    .withRel("prev")
                    .expand();
            paginationLinks.add(previousPageLink);
        }

        if (page.hasNext()) {
            int nextPage = page.getNumber() + 1;
            Link nextPageLink = linkTo(getAssociatedCertificatesMethod(tagId, nextPage, size))
                    .withRel("next")
                    .expand();
            paginationLinks.add(nextPageLink);
        }

        Link firstPageLink = linkTo(getAssociatedCertificatesMethod(tagId, 0, size))
                .withRel("first")
                .expand();
        paginationLinks.add(firstPageLink);

        int lastPage = page.getTotalPages();
        Link lastPageLink = linkTo(getAssociatedCertificatesMethod(tagId, lastPage, size))
                .withRel("last")
                .expand();
        paginationLinks.add(lastPageLink);

        return collectionModel.add(paginationLinks);
    }

    private CollectionModel<GiftCertificateModel> getAssociatedCertificatesMethod(Long tagId, Integer page,
                                                                                  Integer size) {
        return methodOn(TagController.class).getAssociatedCertificates(tagId, page, size);
    }

    @Override
    public GiftCertificateModel process(GiftCertificateModel model) {
        return model;
    }
}
