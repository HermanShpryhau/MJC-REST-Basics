package com.epam.esm.web.hateoas.processor;

import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.web.controller.OrdersController;
import com.epam.esm.web.controller.UsersController;
import com.epam.esm.web.hateoas.model.OrderModel;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelProcessor implements RepresentationModelProcessor<OrderModel> {

    public CollectionModel<OrderModel> process(Page<OrderDto> page, Integer size,
                                               CollectionModel<OrderModel> collectionModel) {
        List<Link> paginationLinks = new ArrayList<>();

        if (page.hasPrevious()) {
            int previousPage = page.getNumber() - 1;
            Link previousPageLink = linkTo(getAllOrdersMethod(previousPage, size))
                    .withRel("prev")
                    .expand();
            paginationLinks.add(previousPageLink);
        }

        if (page.hasNext()) {
            int nextPage = page.getNumber() + 1;
            Link nextPageLink = linkTo(getAllOrdersMethod(nextPage, size))
                    .withRel("next")
                    .expand();
            paginationLinks.add(nextPageLink);
        }

        Link firstPageLink = linkTo(getAllOrdersMethod(0, size))
                .withRel("first")
                .expand();
        paginationLinks.add(firstPageLink);

        int lastPage = page.getTotalPages();
        Link lastPageLink = linkTo(getAllOrdersMethod(lastPage, size))
                .withRel("last")
                .expand();
        paginationLinks.add(lastPageLink);

        return collectionModel.add(paginationLinks);
    }

    private CollectionModel<OrderModel> getAllOrdersMethod(Integer page, Integer size) {
        return methodOn(OrdersController.class).getAllOrders(page, size);
    }

    public CollectionModel<OrderModel> process(Long userId, Page<OrderDto> page, Integer size,
                                               CollectionModel<OrderModel> collectionModel) {
        List<Link> paginationLinks = new ArrayList<>();

        if (page.hasPrevious()) {
            int previousPage = page.getNumber() - 1;
            Link previousPageLink = linkTo(getUserOrdersMethod(userId, previousPage, size))
                    .withRel("prev")
                    .expand();
            paginationLinks.add(previousPageLink);
        }

        if (page.hasNext()) {
            int nextPage = page.getNumber() + 1;
            Link nextPageLink = linkTo(getUserOrdersMethod(userId, nextPage, size))
                    .withRel("next")
                    .expand();
            paginationLinks.add(nextPageLink);
        }

        Link firstPageLink = linkTo(getUserOrdersMethod(userId, 0, size))
                .withRel("first")
                .expand();
        paginationLinks.add(firstPageLink);

        int lastPage = page.getTotalPages();
        Link lastPageLink = linkTo(getUserOrdersMethod(userId, lastPage, size))
                .withRel("last")
                .expand();
        paginationLinks.add(lastPageLink);

        return collectionModel.add(paginationLinks);
    }

    private CollectionModel<OrderModel> getUserOrdersMethod(Long userId, Integer page, Integer size) {
        return methodOn(UsersController.class).getUserOrders(userId, page, size);
    }

    @Override
    public OrderModel process(OrderModel model) {
        return model;
    }
}
