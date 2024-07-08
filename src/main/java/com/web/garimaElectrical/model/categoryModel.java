package com.web.garimaElectrical.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class categoryModel {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Integer id;
	
	private String name;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "category",cascade = CascadeType.ALL)
	private List<productModel> products=new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public List<productModel> getProducts() {
		return products;
	}

	public void setProducts(List<productModel> products) {
		this.products = products;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
