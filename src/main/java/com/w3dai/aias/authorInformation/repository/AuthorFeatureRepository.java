package com.w3dai.aias.authorInformation.repository;

import com.w3dai.aias.authorInformation.entity.Authorfeature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorFeatureRepository extends JpaRepository<Authorfeature, Long> {
    List<Authorfeature> findByAuthorName(String authorName);
}
