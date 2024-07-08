package com.web.garimaElectrical.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.web.garimaElectrical.model.productModel;
import com.web.garimaElectrical.repository.productRepo;

@Service
public class productService {

	@Autowired
	productRepo productRepo;
	
	public void add(productModel productModel) {
		productRepo.save(productModel);
	}
	public void delete(Integer id) {
		productRepo.deleteById(id);
	}
	public List<productModel> show(){
		return productRepo.findAll();
	}
	public Optional<productModel> update(Integer id){
		return productRepo.findById(id);
	}
	public List<productModel> getAllProductByCategory(Integer id){
		return productRepo.findAllProductByCtegoryId(id);
	}
}
