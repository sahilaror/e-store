package com.web.garimaElectrical.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.garimaElectrical.model.categoryModel;

@Repository
public interface categoryRepo extends JpaRepository<categoryModel, Integer> {
}
