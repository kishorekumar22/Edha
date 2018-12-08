package com.edhaorganics.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edhaorganics.backend.beans.DiscountType;

@Repository
public interface DiscountTypeRepository extends JpaRepository<DiscountType, Long> {

}