package com.epam.esm.web.hateoas.processor;

import com.epam.esm.web.controller.OrdersController;
import com.epam.esm.web.controller.UsersController;
import com.epam.esm.web.hateoas.model.OrderModel;
import com.epam.esm.web.hateoas.model.UserModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelProcessor implements RepresentationModelProcessor<UserModel> {
    private static final int FIRST_PAGE = 1;
    private static final int LAST_PAGE = -1;

    public CollectionModel<UserModel> process(Integer page, Integer size,
                                              CollectionModel<UserModel> collectionModel) {

        int nextPage = page + 1;
        int previousPage = page - 1;
        Link previousPageLink = linkTo(getAllUsersMethod(previousPage, size))
                .withRel("prev")
                .expand();
        Link nextPageLink = linkTo(getAllUsersMethod(nextPage, size))
                .withRel("next")
                .expand();
        Link firstPageLink = linkTo(getAllUsersMethod(FIRST_PAGE, size))
                .withRel("first")
                .expand();
        Link lastPageLink = linkTo(getAllUsersMethod(LAST_PAGE, size))
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
