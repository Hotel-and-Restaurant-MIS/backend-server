package com.luxury.virtualwaiter_service.repository;

import com.luxury.virtualwaiter_service.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    Status findByStatusName(String statusName);
}
