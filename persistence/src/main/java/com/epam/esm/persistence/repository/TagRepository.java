package com.epam.esm.persistence.repository;

import com.epam.esm.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>, CustomTagRepository {
    /**
     * Finds tag by name
     *
     * @param name Name of tag to search
     * @return Found tag or {@code null}
     */
    Optional<Tag> findByName(String name);
}
