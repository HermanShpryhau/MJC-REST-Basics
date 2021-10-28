package com.epam.esm.web.hateoas.assembler;

import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.web.controller.CertificatesController;
import com.epam.esm.web.controller.OrdersController;
import com.epam.esm.web.controller.UsersController;
import com.epam.esm.web.hateoas.model.OrderModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler extends RepresentationModelAssemblerSupport<OrderDto, OrderModel> {

    public OrderModelAssembler() {
        super(OrdersController.class, OrderModel.class);
    }

    @Override
    public OrderModel toModel(OrderDto entity) {
        OrderModel model = createModelWithId(entity.getId(), entity);
        setModelFields(entity, model);
        addUserLink(model);
        addCertificateLink(model);
        return model;
    }

    private void setModelFields(OrderDto entity, OrderModel model) {
        model.setId(entity.getId());
        model.setUserId(entity.getUser().getId());
        model.setCertificateId(entity.getGiftCertificate().getId());
        model.setTotalPrice(entity.getTotalPrice());
        model.setSubmissionDate(entity.getSubmissionDate());
    }

    private void addUserLink(OrderModel model) {
        Link userLink = linkTo(methodOn(UsersController.class).getUserById(model.getUserId()))
                .withRel("user");
        model.add(userLink);
    }

    private void addCertificateLink(OrderModel model) {
        Link certificateLink = linkTo(methodOn(CertificatesController.class).getById(model.getCertificateId()))
                .withRel("certificate");
        model.add(certificateLink);
    }

    @Override
    public CollectionModel<OrderModel> toCollectionModel(Iterable<? extends OrderDto> entities) {
        List<OrderModel> orderModels = new ArrayList<>();
        entities.forEach(orderDto -> {
            OrderModel model = toModel(orderDto);
            orderModels.add(model);
        });
        return CollectionModel.of(orderModels);
    }
}
