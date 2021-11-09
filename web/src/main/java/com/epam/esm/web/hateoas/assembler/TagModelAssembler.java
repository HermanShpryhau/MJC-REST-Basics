package com.epam.esm.web.hateoas.assembler;

import com.epam.esm.model.dto.TagDto;
import com.epam.esm.web.controller.TagController;
import com.epam.esm.web.hateoas.model.TagModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagModelAssembler extends RepresentationModelAssemblerSupport<TagDto, TagModel> {

    public TagModelAssembler() {
        super(TagController.class, TagModel.class);
    }

    @Override
    public TagModel toModel(TagDto entity) {
        TagModel model = createModelWithId(entity.getId(), entity);
        model.setId(entity.getId());
        model.setName(entity.getName());
        addCertificatesLink(entity, model);
        return model;
    }

    private void addCertificatesLink(TagDto entity, TagModel model) {
        Link certificatesLink = linkTo(methodOn(TagController.class).getAssociatedCertificates(
                entity.getId(), null, null)
        ).withRel("certificates").expand();
        model.add(certificatesLink);
    }

    @Override
    public CollectionModel<TagModel> toCollectionModel(Iterable<? extends TagDto> entities) {
        List<TagModel> tagModels = new ArrayList<>();
        entities.forEach(tagDto -> {
            TagModel model = toModel(tagDto);
            tagModels.add(model);
        });
        return CollectionModel.of(tagModels);
    }
}
