package com.epam.esm.web.hateoas.processor;

import com.epam.esm.model.dto.UserDto;
import com.epam.esm.service.pagination.Page;
import com.epam.esm.web.controller.UsersController;
import com.epam.esm.web.hateoas.model.UserModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelProcessor implements RepresentationModelProcessor<UserModel> {

    public CollectionModel<UserModel> process(Page<UserDto> page, Integer size,
                                              CollectionModel<UserModel> collectionModel) {
        List<Link> paginationLinks = new ArrayList<>();

        if (page.hasPrevious()) {
            int previousPage = page.getPreviousPageIndex();
            Link previousPageLink = linkTo(getAllUsersMethod(previousPage, size))
                    .withRel("prev")
                    .expand();
            paginationLinks.add(previousPageLink);
        }

        if (page.hasNext()) {
            int nextPage = page.getNextPageIndex();
            Link nextPageLink = linkTo(getAllUsersMethod(nextPage, size))
                    .withRel("next")
                    .expand();
            paginationLinks.add(nextPageLink);
        }

        Link firstPageLink = linkTo(getAllUsersMethod(Page.FIRST_PAGE, size))
                .withRel("first")
                .expand();
        paginationLinks.add(firstPageLink);

        int lastPage = page.getTotalPages();
        Link lastPageLink = linkTo(getAllUsersMethod(lastPage, size))
                .withRel("last")
                .expand();
        paginationLinks.add(lastPageLink);

        return collectionModel.add(paginationLinks);
    }

    private CollectionModel<UserModel> getAllUsersMethod(Integer page, Integer size) {
        return methodOn(UsersController.class).getAllUsers(page, size);
    }

    @Override
    public UserModel process(UserModel model) {
        return model;
    }
}
