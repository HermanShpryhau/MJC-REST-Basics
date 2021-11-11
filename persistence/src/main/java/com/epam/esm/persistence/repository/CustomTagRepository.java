package com.epam.esm.persistence.repository;

import com.epam.esm.model.Tag;

import java.util.List;

public interface CustomTagRepository {


    /**
     * Finds most widely used tag(s) of a user with the highest cost of all orders.
     *
     * @return List of tags found
     */
    List<Tag> findMostPopularTag();
}
