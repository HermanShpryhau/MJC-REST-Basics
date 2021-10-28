package com.epam.esm.web.hateoas.processor;

import com.epam.esm.service.util.PaginationUtil;
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

    public CollectionModel<UserModel> process(Integer page, Integer size,
                                              CollectionModel<UserModel> collectionModel) {

        int nextPage = PaginationUtil.nextPage(page, size, collectionModel.getContent()::size);
        int previousPage = PaginationUtil.previousPage(page, size, collectionModel.getContent()::size);
        Link previousPageLink = linkTo(getAllUsersMethod(previousPage, size))
                .withRel("prev")
                .expand();
        Link nextPageLink = linkTo(getAllUsersMethod(nextPage, size))
                .withRel("next")
                .expand();
        Link firstPageLink = linkTo(getAllUsersMethod(PaginationUtil.FIRST_PAGE, size))
                .withRel("first")
                .expand();
        Link lastPageLink = linkTo(getAllUsersMethod(PaginationUtil.LAST_PAGE, size))
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
