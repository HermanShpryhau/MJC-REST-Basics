package com.epam.esm.web.hateoas.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Data
@NoArgsConstructor
@Relation(itemRelation = "tag", collectionRelation = "tags")
public class TagModel extends RepresentationModel<TagModel> {
    private Long id;
    private String name;
}
