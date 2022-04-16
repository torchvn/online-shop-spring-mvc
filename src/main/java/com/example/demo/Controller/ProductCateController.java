package com.example.demo.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Dao.CategoryDao;
import com.example.demo.Dao.FillProCateDao;
import com.example.demo.Dao.ProductDao;
import com.example.demo.Dao.ProductCateDao;
import com.example.demo.Entity.Category;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.ProductCate;
import com.example.demo.Model.FillProCate;
import com.example.demo.Model.ProductCateModel;
import com.example.demo.Service.SessionService;

@Controller
public class ProductCateController {
	@Autowired
	ProductCateDao dao;

	@Autowired
	CategoryDao categoryDao;

	@Autowired
	ProductDao productDao;

	@Autowired
	FillProCateDao procateDao;

	@Autowired
	SessionService session;

	@RequestMapping("/admin/productcateForm/form")
	public String index(Model model, @RequestParam("keyword5") Optional<String> name,
			@RequestParam("p") Optional<Integer> p2) {
		// Load select category
		Map<Integer, String> map = new HashMap<Integer, String>();
		List<Category> list = categoryDao.findAll();
		for (Category c : list) {
			map.put(c.getId(), c.getName());
		}
		ProductCateModel entity = new ProductCateModel();
		model.addAttribute("productcate", entity);
		model.addAttribute("category", map);

		// Load select product
		List<Product> list2 = productDao.findProduct(list.get(0).getId());
		// List<Product> list = productDao.findProduct();
		Map<Integer, String> map2 = new HashMap<Integer, String>();
		for (Product p : list2) {
			map2.put(p.getId(), p.getName());
		}
		model.addAttribute("product", map2);

		// find by name
		String findName;
		if (session.get("keyword5") == null) {
			findName = name.orElse("");
		} else {
			findName = name.orElse(session.get("keyword5"));
		}

		session.set("keyword5", findName);

		Pageable pageable = PageRequest.of(p2.orElse(0), 5);

		Page<FillProCate> page = procateDao.fillToTable("%" + findName + "%", pageable);
		model.addAttribute("procateItem", page);

		return "manager/productcateForm";
	}

	@RequestMapping("/admin/productcateForm/change/{id}")
	public String change(Model model, @PathVariable("id") String id,
			@ModelAttribute("productcate") ProductCateModel entity, @RequestParam("keyword5") Optional<String> name,
			@RequestParam("p") Optional<Integer> p2) {
		System.out.println("đúng");
		System.out.println(id);

		// System.out.println(entity.getCateId());

		Map<Integer, String> map2 = new HashMap<Integer, String>();
		List<Category> list2 = categoryDao.findAll();
		for (Category c : list2) {
			map2.put(c.getId(), c.getName());
		}
		model.addAttribute("category", map2);

		List<Product> list = productDao.findProduct(Integer.parseInt(id));
		// List<Product> list = productDao.findProduct();
		Map<Integer, String> map = new HashMap<Integer, String>();
		for (Product p : list) {
			map.put(p.getId(), p.getName());
		}
		// model.addAttribute("product", map);
		model.addAttribute("product", map);

		entity.setCateId(id);

		// find by name
		String findName;
		if (session.get("keyword5") == null) {
			findName = name.orElse("");
		} else {
			findName = name.orElse(session.get("keyword5"));
		}

		session.set("keyword5", findName);

		Pageable pageable = PageRequest.of(p2.orElse(0), 5);

		Page<FillProCate> page = procateDao.fillToTable("%" + findName + "%", pageable);
		model.addAttribute("procateItem", page);

		return "manager/productcateForm";
	}

	@PostMapping("/admin/productcateForm/save")
	public String save(Model model, @ModelAttribute("productcate") ProductCateModel entity) {
//		Product product = productDao.getById(Integer.parseInt(entity.getProductId()));
//		Category category = categoryDao.getById(Integer.parseInt(entity.getCateId()));
		Product product = productDao.getOne(Integer.parseInt(entity.getProductId()));
		Category category = categoryDao.getOne(Integer.parseInt(entity.getCateId()));
		
		ProductCate productcate = new ProductCate(product, category);
		dao.save(productcate);
		return "redirect:/admin/productcateForm/form";
	}
	
	@RequestMapping("/admin/productcateForm/form/delete/{id}")
	public String delete(Model model, @PathVariable("id") int id) {
//		ProductCate entity = dao.getById(id);
		ProductCate entity = dao.getOne(id);
		dao.delete(entity);
		return "redirect:/admin/productcateForm/form";
	}
}
