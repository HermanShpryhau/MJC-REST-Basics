package com.epam.esm.web.hateoas.processor;

import com.epam.esm.model.dto.UserDto;
import com.epam.esm.service.pagination.Page;
import com.epam.esm.web.controller.UsersController;
import com.epam.esm.web.hateoas.model.UserModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelProcessor implements RepresentationModelProcessor<UserModel> {

    public CollectionModel<UserModel> process(Page<UserDto> page, Integer size,
                                              CollectionModel<UserModel> collectionModel) {

        int nextPage = page.getNextPageIndex();
        int previousPage = page.getPreviousPageIndex();
        int lastPage = page.getTotalPages();
        Link previousPageLink = linkTo(getAllUsersMethod(previousPage, size))
                .withRel("prev")
                .expand();
        Link nextPageLink = linkTo(getAllUsersMethod(nextPage, size))
                .withRel("next")
                .expand();
        Link firstPageLink = linkTo(getAllUsersMethod(Page.FIRST_PAGE, size))
                .withRel("first")
                .expand();
        Link lastPageLink = linkTo(getAllUsersMethod(lastPage, size))
                .withRel("last")
                .expand();
        return collectionModel.add(previousPageLink, nextPageLink, firstPageLink, lastPageLink);
    }

    private CollectionModel<UserModel> getAllUsersMethod(Integer page, Integer size) {
        return methodOn(UsersController.class).getAllUsers(page, size);
    }

    @Override
    public UserModel process(UserModel model) {
        return model;
    }
}
