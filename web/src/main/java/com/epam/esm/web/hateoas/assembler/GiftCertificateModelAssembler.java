package com.epam.esm.web.hateoas.assembler;

import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.web.controller.CertificatesController;
import com.epam.esm.web.hateoas.model.GiftCertificateModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GiftCertificateModelAssembler extends RepresentationModelAssemblerSupport<GiftCertificateDto,
        GiftCertificateModel> {

    public GiftCertificateModelAssembler() {
        super(CertificatesController.class, GiftCertificateModel.class);
    }

    @Override
    public GiftCertificateModel toModel(GiftCertificateDto entity) {
        GiftCertificateModel model = createModelWithId(entity.getId(), entity);
        setModelFields(entity, model);
        addTagsLink(model);
        return model;
    }

    private void setModelFields(GiftCertificateDto entity, GiftCertificateModel model) {
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setDescription(entity.getDescription());
        model.setPrice(entity.getPrice());
        model.setDuration(entity.getDuration());
        model.setCreateDate(entity.getCreateDate());
        model.setLastUpdateDate(entity.getLastUpdateDate());
    }

    private void addTagsLink(GiftCertificateModel model) {
        Link tagsLink = linkTo(methodOn(CertificatesController.class).getAssociatedTags(
                model.getId(), null, null)
        ).withRel("tags").expand();
        model.add(tagsLink);
    }

    @Override
    public CollectionModel<GiftCertificateModel> toCollectionModel(Iterable<? extends GiftCertificateDto> entities) {
        List<GiftCertificateModel> giftCertificateModels = new ArrayList<>();
        entities.forEach(certificateDto -> {
            GiftCertificateModel model = toModel(certificateDto);
            giftCertificateModels.add(model);
        });
        return CollectionModel.of(giftCertificateModels);
    }
}
