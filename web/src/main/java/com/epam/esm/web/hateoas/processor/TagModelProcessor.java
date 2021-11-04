package com.epam.esm.web.hateoas.processor;

import com.epam.esm.model.dto.TagDto;
import com.epam.esm.service.pagination.Page;
import com.epam.esm.web.controller.CertificatesController;
import com.epam.esm.web.controller.TagController;
import com.epam.esm.web.hateoas.model.TagModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagModelProcessor implements RepresentationModelProcessor<TagModel> {

    public CollectionModel<TagModel> process(Page<TagDto> page, int size, CollectionModel<TagModel> collectionModel) {
        int nextPage = page.getNextPageIndex();
        int previousPage = page.getPreviousPageIndex();
        int lastPage = page.getTotalPages();
        Link previousPageLink = linkTo(getAllTagsMethod(previousPage, size))
                .withRel("prev")
                .expand();
        Link nextPageLink = linkTo(getAllTagsMethod(nextPage, size))
                .withRel("next")
                .expand();
        Link firstPageLink = linkTo(getAllTagsMethod(Page.FIRST_PAGE, size))
                .withRel("first")
                .expand();
        Link lastPageLink = linkTo(getAllTagsMethod(lastPage, size))
                .withRel("last")
                .expand();
        return collectionModel.add(previousPageLink, nextPageLink, firstPageLink, lastPageLink);
    }

    private CollectionModel<TagModel> getAllTagsMethod(int page, int size) {
        return methodOn(TagController.class).getAllTags(page, size);
    }

    public CollectionModel<TagModel> process(long certificateId, Page<TagDto> page, int size,
                                             CollectionModel<TagModel> collectionModel) {
        int nextPage = page.getNextPageIndex();
        int previousPage = page.getPreviousPageIndex();
        int lastPage = page.getTotalPages();
        Link previousPageLink = linkTo(getAssociatedTagsMethod(certificateId, previousPage, size))
                .withRel("prev")
                .expand();
        Link nextPageLink = linkTo(getAssociatedTagsMethod(certificateId, nextPage, size))
                .withRel("next")
                .expand();
        Link firstPageLink = linkTo(getAssociatedTagsMethod(certificateId, Page.FIRST_PAGE, size))
                .withRel("first")
                .expand();
        Link lastPageLink = linkTo(getAssociatedTagsMethod(certificateId, lastPage, size))
                .withRel("last")
                .expand();
        return collectionModel.add(previousPageLink, nextPageLink, firstPageLink, lastPageLink);
    }

    private CollectionModel<TagModel> getAssociatedTagsMethod(long certificateId, int page, int size) {
        return methodOn(CertificatesController.class).getAssociatedTags(certificateId, page, size);
    }

    @Override
    public TagModel process(TagModel model) {
        return model;
    }
}
