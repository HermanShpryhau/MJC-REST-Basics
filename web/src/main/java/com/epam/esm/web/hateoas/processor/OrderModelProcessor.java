package com.epam.esm.web.hateoas.processor;

import com.epam.esm.web.controller.OrdersController;
import com.epam.esm.web.controller.UsersController;
import com.epam.esm.web.hateoas.model.OrderModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelProcessor implements RepresentationModelProcessor<OrderModel> {
    private static final int FIRST_PAGE = 1;
    private static final int LAST_PAGE = -1;

    public CollectionModel<OrderModel> process(Integer page, Integer size,
                                               CollectionModel<OrderModel> collectionModel) {
        int nextPage = page + 1;
        int previousPage = page - 1;
        Link previousPageLink = linkTo(getAllOrdersMethod(previousPage, size))
                .withRel("prev")
                .expand();
        Link nextPageLink = linkTo(getAllOrdersMethod(nextPage, size))
                .withRel("next")
                .expand();
        Link firstPageLink = linkTo(getAllOrdersMethod(FIRST_PAGE, size))
                .withRel("first")
                .expand();
        Link lastPageLink = linkTo(getAllOrdersMethod(LAST_PAGE, size))
                .withRel("last")
                .expand();
        return collectionModel.add(previousPageLink, nextPageLink, firstPageLink, lastPageLink);
    }

    private CollectionModel<OrderModel> getAllOrdersMethod(Integer page, Integer size) {
        return methodOn(OrdersController.class).getAllOrders(page, size);
    }

    public CollectionModel<OrderModel> process(Long userId, Integer page, Integer size,
                                               CollectionModel<OrderModel> collectionModel) {
        int nextPage = page + 1;
        int previousPage = page - 1;
        Link previousPageLink = linkTo(getUserOrdersMethod(userId, previousPage, size))
                .withRel("prev")
                .expand();
        Link nextPageLink = linkTo(getUserOrdersMethod(userId, nextPage, size))
                .withRel("next")
                .expand();
        Link firstPageLink = linkTo(getUserOrdersMethod(userId, FIRST_PAGE, size))
                .withRel("first")
                .expand();
        Link lastPageLink = linkTo(getUserOrdersMethod(userId, LAST_PAGE, size))
                .withRel("last")
                .expand();
        return collectionModel.add(previousPageLink, nextPageLink, firstPageLink, lastPageLink);
    }

    private CollectionModel<OrderModel> getUserOrdersMethod(Long userId, Integer page, Integer size) {
        return methodOn(UsersController.class).getUserOrders(userId, page, size);
    }

    @Override
    public OrderModel process(OrderModel model) {
        return model;
    }
}
