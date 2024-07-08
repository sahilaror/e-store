package com.web.garimaElectrical.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.garimaElectrical.model.categoryModel;
import com.web.garimaElectrical.repository.categoryRepo;





@Service
public class categoryService {
	
	@Autowired
	categoryRepo categoryRepo;

	public void add(categoryModel categoryModel) {
		categoryRepo.save(categoryModel);
	}
	public void delete(Integer id) {
		categoryRepo.deleteById(id);
	}
	public List<categoryModel> show(){
		return categoryRepo.findAll();
	}
	public Optional<categoryModel> update(Integer id){
		return categoryRepo.findById(id);
	}
}
