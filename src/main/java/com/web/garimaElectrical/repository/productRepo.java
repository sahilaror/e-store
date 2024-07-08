package com.web.garimaElectrical.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.web.garimaElectrical.model.productModel;


@Repository
public interface productRepo extends JpaRepository<productModel, Integer>{

	@Query("from productModel as p where p.category.id =:categoryid")
	List<productModel> findAllProductByCtegoryId(@Param("categoryid") Integer id);
}
