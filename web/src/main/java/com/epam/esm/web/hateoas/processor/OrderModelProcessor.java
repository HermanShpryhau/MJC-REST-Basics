package com.epam.esm.web.hateoas.processor;

import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.service.pagination.Page;
import com.epam.esm.service.pagination.PaginationUtil;
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

    public CollectionModel<OrderModel> process(Page<OrderDto> page, Integer size,
                                               CollectionModel<OrderModel> collectionModel) {
        int nextPage = page.getNextPageIndex();
        int previousPage = page.getPreviousPageIndex();
        int lastPage = page.getTotalPages();
        Link previousPageLink = linkTo(getAllOrdersMethod(previousPage, size))
                .withRel("prev")
                .expand();
        Link nextPageLink = linkTo(getAllOrdersMethod(nextPage, size))
                .withRel("next")
                .expand();
        Link firstPageLink = linkTo(getAllOrdersMethod(Page.FIRST_PAGE, size))
                .withRel("first")
                .expand();
        Link lastPageLink = linkTo(getAllOrdersMethod(lastPage, size))
                .withRel("last")
                .expand();
        return collectionModel.add(previousPageLink, nextPageLink, firstPageLink, lastPageLink);
    }

    private CollectionModel<OrderModel> getAllOrdersMethod(Integer page, Integer size) {
        return methodOn(OrdersController.class).getAllOrders(page, size);
    }

    public CollectionModel<OrderModel> process(Long userId, Integer page, Integer size,
                                               CollectionModel<OrderModel> collectionModel) {
        int nextPage = PaginationUtil.nextPage(page, size, collectionModel.getContent()::size);
        int previousPage = PaginationUtil.previousPage(page, size, collectionModel.getContent()::size);
        Link previousPageLink = linkTo(getUserOrdersMethod(userId, previousPage, size))
                .withRel("prev")
                .expand();
        Link nextPageLink = linkTo(getUserOrdersMethod(userId, nextPage, size))
                .withRel("next")
                .expand();
        Link firstPageLink = linkTo(getUserOrdersMethod(userId, PaginationUtil.FIRST_PAGE, size))
                .withRel("first")
                .expand();
        Link lastPageLink = linkTo(getUserOrdersMethod(userId, PaginationUtil.LAST_PAGE, size))
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
