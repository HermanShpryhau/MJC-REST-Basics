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

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagModelProcessor implements RepresentationModelProcessor<TagModel> {

    public CollectionModel<TagModel> process(Page<TagDto> page, int size, CollectionModel<TagModel> collectionModel) {
        List<Link> paginationLinks = new ArrayList<>();

        if (page.hasPrevious()) {
            int previousPage = page.getPreviousPageIndex();
            Link previousPageLink = linkTo(getAllTagsMethod(previousPage, size))
                    .withRel("prev")
                    .expand();
            paginationLinks.add(previousPageLink);
        }

        if (page.hasNext()) {
            int nextPage = page.getNextPageIndex();
            Link nextPageLink = linkTo(getAllTagsMethod(nextPage, size))
                    .withRel("next")
                    .expand();
            paginationLinks.add(nextPageLink);
        }

        Link firstPageLink = linkTo(getAllTagsMethod(Page.FIRST_PAGE, size))
                .withRel("first")
                .expand();
        paginationLinks.add(firstPageLink);

        int lastPage = page.getTotalPages();
        Link lastPageLink = linkTo(getAllTagsMethod(lastPage, size))
                .withRel("last")
                .expand();
        paginationLinks.add(lastPageLink);

        return collectionModel.add(paginationLinks);
    }

    private CollectionModel<TagModel> getAllTagsMethod(int page, int size) {
        return methodOn(TagController.class).getAllTags(page, size);
    }

    public CollectionModel<TagModel> process(long certificateId, Page<TagDto> page, int size,
                                             CollectionModel<TagModel> collectionModel) {
        List<Link> paginationLinks = new ArrayList<>();

        if (page.hasPrevious()) {
            int previousPage = page.getPreviousPageIndex();
            Link previousPageLink = linkTo(getAssociatedTagsMethod(certificateId, previousPage, size))
                    .withRel("prev")
                    .expand();
            paginationLinks.add(previousPageLink);
        }

        if (page.hasNext()) {
            int nextPage = page.getNextPageIndex();
            Link nextPageLink = linkTo(getAssociatedTagsMethod(certificateId, nextPage, size))
                    .withRel("next")
                    .expand();
            paginationLinks.add(nextPageLink);
        }

        Link firstPageLink = linkTo(getAssociatedTagsMethod(certificateId, Page.FIRST_PAGE, size))
                .withRel("first")
                .expand();
        paginationLinks.add(firstPageLink);

        int lastPage = page.getTotalPages();
        Link lastPageLink = linkTo(getAssociatedTagsMethod(certificateId, lastPage, size))
                .withRel("last")
                .expand();
        paginationLinks.add(lastPageLink);

        return collectionModel.add(paginationLinks);
    }

    private CollectionModel<TagModel> getAssociatedTagsMethod(long certificateId, int page, int size) {
        return methodOn(CertificatesController.class).getAssociatedTags(certificateId, page, size);
    }

    @Override
    public TagModel process(TagModel model) {
        return model;
    }
}
