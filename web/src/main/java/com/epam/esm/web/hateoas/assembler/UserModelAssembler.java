package com.epam.esm.web.hateoas.assembler;

import com.epam.esm.model.dto.UserDto;
import com.epam.esm.web.controller.UsersController;
import com.epam.esm.web.hateoas.model.UserModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport<UserDto, UserModel> {

    public UserModelAssembler() {
        super(UsersController.class, UserModel.class);
    }

    @Override
    public UserModel toModel(UserDto entity) {
        UserModel model = createModelWithId(entity.getId(), entity);
        setModelFields(entity, model);
        addOrdersLink(entity, model);
        return model;
    }


    private void setModelFields(UserDto entity, UserModel model) {
        model.setId(entity.getId());
        model.setName(entity.getName());
    }

    private void addOrdersLink(UserDto entity, UserModel model) {
        Link ordersLink = linkTo(methodOn(UsersController.class).getUserOrders(entity.getId(), null, null))
                .withRel("orders")
                .expand();
        model.add(ordersLink);
    }

    @Override
    public CollectionModel<UserModel> toCollectionModel(Iterable<? extends UserDto> entities) {
        List<UserModel> userModels = new ArrayList<>();
        entities.forEach(userDto -> {
            UserModel model = toModel(userDto);
            userModels.add(model);
        });
        return CollectionModel.of(userModels);
    }
}
