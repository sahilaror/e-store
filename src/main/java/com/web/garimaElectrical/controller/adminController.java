package com.web.garimaElectrical.controller;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import com.web.garimaElectrical.Dto.productDTO;
import com.web.garimaElectrical.model.categoryModel;
import com.web.garimaElectrical.model.productModel;
import com.web.garimaElectrical.service.categoryService;
import com.web.garimaElectrical.service.productService;



@Controller
@RequestMapping("/admin")
public class adminController {
	
	public static String uploadDir=System.getProperty("user.dir")+"/src/main/resources/static/productImages";

	@Autowired
	categoryService categoryService;
	
	@Autowired
	productService productService;
	
	@GetMapping("/home")
	public ModelAndView admin() {
		
		return new ModelAndView("admin/adminHome");
	}
	@GetMapping("/category")
	public ModelAndView category(Model model) {
		model.addAttribute("categories", categoryService.show());
		return new ModelAndView("admin/category");
	}
	@GetMapping("/category/add")
	public ModelAndView addCategory(Model model) {
		model.addAttribute("category", new categoryModel());
		return new ModelAndView("admin/categoryAdd");
	}
	@PostMapping("/category/add")
	public ModelAndView postAddCategory(@ModelAttribute("category") categoryModel categoryModel,Model model) {
		categoryService.add(categoryModel);
		RedirectView rd=new RedirectView();
		rd.setUrl("/admin/category");
		return new ModelAndView(rd);
	}
	@GetMapping("/category/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Integer id) {
		categoryService.delete(id);
		RedirectView rd=new RedirectView();
		rd.setUrl("/admin/category");
		return new ModelAndView(rd);
	}
	@GetMapping("/category/update/{id}")
	public ModelAndView update(@PathVariable("id") Integer id,Model model) {
		Optional<categoryModel> category=categoryService.update(id);
		model.addAttribute("category",category.get());
		return new ModelAndView("admin/categoryAdd");
	}
	
	
	
	//product controller
	
	
	@GetMapping("/product")
	public ModelAndView showProduct(Model model) {
		model.addAttribute("products",productService.show());
		return new ModelAndView("admin/product");
	}
	
	@GetMapping("/product/add")
	public ModelAndView addProduct(Model model) {
		model.addAttribute("productDTO", new productDTO());
		model.addAttribute("categories", categoryService.show());
		return new ModelAndView("admin/productAdd");
	}
	
	@PostMapping("/product/add")
	public ModelAndView postAddProduct(@ModelAttribute("productDTO") productDTO productDTO, @RequestParam("productImage") MultipartFile multipartFile, @RequestParam("imgName")String imgName) throws IOException {
		productModel product=new productModel();
		product.setPid(productDTO.getPid());
		product.setName(productDTO.getName());
		product.setPrice(productDTO.getPrice());
		product.setWeight(productDTO.getWeight());
		product.setDescription(productDTO.getDescription());
		product.setCategory(categoryService.update(productDTO.getCategoryId()).get());
		String uui;
		if (!multipartFile.isEmpty()) {
		    uui=multipartFile.getOriginalFilename();
			Path path=Paths.get(uploadDir, uui);
			Files.write(path,multipartFile.getBytes());
		}
		else {
			uui=imgName;
		}
		product.setImageName(uui);
		productService.add(product);
		RedirectView rd=new RedirectView();
		rd.setUrl("/admin/product");
		return new ModelAndView(rd);
	}
	
	@GetMapping("/product/delete/{id}")
	public ModelAndView deleteProduct(@PathVariable("id") Integer id) {
		System.out.println("product id is:-"+id);
		productService.delete(id);
		RedirectView rd=new RedirectView();
		rd.setUrl("/admin/product");
		return new ModelAndView(rd);
	}
	
	@GetMapping("/product/update/{id}")
	public ModelAndView updateProduct(@PathVariable("id") Integer id,Model model) {
		productModel product=productService.update(id).get();
		productDTO productDTO=new productDTO();
		productDTO.setPid(product.getPid());
		productDTO.setCategoryId(product.getCategory().getId());
		productDTO.setDescription(product.getDescription());
		productDTO.setImageName(product.getImageName());
		productDTO.setPrice(product.getPrice());
		productDTO.setWeight(product.getWeight());
		productDTO.setName(product.getName());
		model.addAttribute("productDTO",productDTO);
		model.addAttribute("categories",categoryService.show());
		return new ModelAndView("admin/productAdd");
	}
	
	
}
