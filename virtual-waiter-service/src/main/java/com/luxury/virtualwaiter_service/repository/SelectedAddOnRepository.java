package com.luxury.virtualwaiter_service.repository;

import com.luxury.virtualwaiter_service.model.SelectedAddOn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectedAddOnRepository extends JpaRepository<SelectedAddOn, Long> {
}
