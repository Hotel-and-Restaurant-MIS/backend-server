package com.luxury.virtualwaiter_service.repository;

import com.luxury.virtualwaiter_service.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
