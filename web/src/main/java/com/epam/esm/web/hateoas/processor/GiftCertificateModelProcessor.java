package com.epam.esm.web.hateoas.processor;

import com.epam.esm.service.util.PaginationUtil;
import com.epam.esm.web.controller.CertificatesController;
import com.epam.esm.web.controller.TagController;
import com.epam.esm.web.hateoas.model.GiftCertificateModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

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
            Integer page,
            Integer size,
            CollectionModel<GiftCertificateModel> collectionModel
    ) {
        int nextPage = PaginationUtil.nextPage(page, size, collectionModel.getContent()::size);
        int previousPage = PaginationUtil.previousPage(page, size, collectionModel.getContent()::size);
        Link previousPageLink = linkTo(getCertificatesMethod(tagNames, sortTypes, searchPattern, size, previousPage))
                .withRel("prev")
                .expand();
        Link nextPageLink = linkTo(getCertificatesMethod(tagNames, sortTypes, searchPattern, size, nextPage))
                .withRel("next")
                .expand();
        Link firstPageLink = linkTo(getCertificatesMethod(tagNames, sortTypes, searchPattern, size, PaginationUtil.FIRST_PAGE))
                .withRel("first")
                .expand();
        Link lastPageLink = linkTo(getCertificatesMethod(tagNames, sortTypes, searchPattern, size, PaginationUtil.LAST_PAGE))
                .withRel("last")
                .expand();
        return collectionModel.add(previousPageLink, nextPageLink, firstPageLink, lastPageLink);
    }

    private CollectionModel<GiftCertificateModel> getCertificatesMethod(Optional<List<String>> tagNames,
                                                                        Optional<List<String>> sortTypes,
                                                                        Optional<String> searchPattern,
                                                                        Integer size, Integer page) {
        return methodOn(CertificatesController.class).getAllCertificatesWithFilters(
                tagNames, sortTypes, searchPattern, page, size
        );
    }

    public CollectionModel<GiftCertificateModel> process(Long tagId, Integer page, Integer size,
                                                         CollectionModel<GiftCertificateModel> collectionModel) {
        int nextPage = PaginationUtil.nextPage(page, size, collectionModel.getContent()::size);
        int previousPage = PaginationUtil.previousPage(page, size, collectionModel.getContent()::size);
        Link previousPageLink = linkTo(getAssociatedCertificatesMethod(tagId, previousPage, size))
                .withRel("prev")
                .expand();
        Link nextPageLink = linkTo(getAssociatedCertificatesMethod(tagId, nextPage, size))
                .withRel("next")
                .expand();
        Link firstPageLink = linkTo(getAssociatedCertificatesMethod(tagId, PaginationUtil.FIRST_PAGE, size))
                .withRel("first")
                .expand();
        Link lastPageLink = linkTo(getAssociatedCertificatesMethod(tagId, PaginationUtil.LAST_PAGE, size))
                .withRel("last")
                .expand();
        return collectionModel.add(previousPageLink, nextPageLink, firstPageLink, lastPageLink);
    }

    private CollectionModel<GiftCertificateModel> getAssociatedCertificatesMethod(Long tagId, Integer page, Integer size) {
        return methodOn(TagController.class).getAssociatedCertificates(tagId, page, size);
    }

    @Override
    public GiftCertificateModel process(GiftCertificateModel model) {
        return model;
    }
}
