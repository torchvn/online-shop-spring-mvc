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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Dao.CategoryDao;
import com.example.demo.Dao.ColorDao;
import com.example.demo.Dao.FillProCateDao;
import com.example.demo.Dao.FillProColorDao;
import com.example.demo.Dao.ProductCateDao;
import com.example.demo.Dao.ProductColorDao;
import com.example.demo.Dao.ProductDao;
import com.example.demo.Entity.Category;
import com.example.demo.Entity.Color;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.ProductCate;
import com.example.demo.Entity.ProductColor;
import com.example.demo.Model.FillProCate;
import com.example.demo.Model.FillProColor;
import com.example.demo.Model.ProductCateModel;
import com.example.demo.Model.ProductColorModel;
import com.example.demo.Service.ParamService;
import com.example.demo.Service.SessionService;

@Controller
public class ProductColorController {
	@Autowired
	ProductColorDao dao;

	@Autowired
	ColorDao colorDao;

	@Autowired
	ProductDao productDao;

	@Autowired
	FillProColorDao procolorDao;

	@Autowired
	SessionService session;
	
	@Autowired
	ParamService param;

	@RequestMapping("/admin/productcolorForm/form")
	public String index(Model model, @RequestParam("keyword9") Optional<String> name,
			@RequestParam("p") Optional<Integer> p2) {
		// Load select category
		Map<Integer, String> map = new HashMap<Integer, String>();
		List<Color> list = colorDao.findAll();
		for (Color c : list) {
			map.put(c.getId(), c.getName());
		}
		ProductColorModel entity = new ProductColorModel();
		model.addAttribute("productcolor", entity);
		model.addAttribute("color", map);

		// Load select product
		List<Product> list2 = productDao.findColor(list.get(0).getId());
		// List<Product> list = productDao.findProduct();
		Map<Integer, String> map2 = new HashMap<Integer, String>();
		for (Product p : list2) {
			map2.put(p.getId(), p.getName());
		}
		model.addAttribute("product", map2);

		// find by name
		String findName;
		if (session.get("keyword9") == null) {
			findName = name.orElse("");
		} else {
			findName = name.orElse(session.get("keyword9"));
		}

		session.set("keyword9", findName);

		Pageable pageable = PageRequest.of(p2.orElse(0), 5);

		Page<FillProColor> page = procolorDao.fillToTable("%" + findName + "%", pageable);
		model.addAttribute("procolorItem", page);

		return "manager/productcolorForm";
	}

	@RequestMapping("/admin/productcolorForm/change/{id}")
	public String change(Model model, @PathVariable("id") String id,
			@ModelAttribute("productcolor") ProductColorModel entity, @RequestParam("keyword9") Optional<String> name,
			@RequestParam("p") Optional<Integer> p2) {
		//System.out.println("đúng");
		//System.out.println(id);

		// System.out.println(entity.getCateId());

		Map<Integer, String> map2 = new HashMap<Integer, String>();
		List<Color> list2 = colorDao.findAll();
		for (Color c : list2) {
			map2.put(c.getId(), c.getName());
		}
		model.addAttribute("color", map2);

		List<Product> list = productDao.findColor(Integer.parseInt(id));
		// List<Product> list = productDao.findProduct();
		Map<Integer, String> map = new HashMap<Integer, String>();
		for (Product p : list) {
			map.put(p.getId(), p.getName());
		}
		// model.addAttribute("product", map);
		model.addAttribute("product", map);

		entity.setColorId(id);

		// find by name
		String findName;
		if (session.get("keyword9") == null) {
			findName = name.orElse("");
		} else {
			findName = name.orElse(session.get("keyword9"));
		}

		session.set("keyword9", findName);

		Pageable pageable = PageRequest.of(p2.orElse(0), 5);

		Page<FillProColor> page = procolorDao.fillToTable("%" + findName + "%", pageable);
		model.addAttribute("procolorItem", page);

		return "manager/productcolorForm";
	}

	@PostMapping("/admin/productcolorForm/save")
	public String save(Model model, @ModelAttribute("productcolor") ProductColorModel entity) {
//		Product product = productDao.getById(Integer.parseInt(entity.getProductId()));
//		Color category = colorDao.getById(Integer.parseInt(entity.getColorId()));
		Product product = productDao.getOne(Integer.parseInt(entity.getProductId()));
		Color category = colorDao.getOne(Integer.parseInt(entity.getColorId()));
		
		ProductColor productcolor = new ProductColor(param.save(entity.getImage()), product, category);
		dao.save(productcolor);
		return "redirect:/admin/productcolorForm/form";
	}
	
	@RequestMapping("/admin/productcolorForm/form/delete/{id}")
	public String delete(Model model, @PathVariable("id") int id) {
//		ProductColor entity = dao.getById(id);
		ProductColor entity = dao.getOne(id);
		dao.delete(entity);
		return "redirect:/admin/productcolorForm/form";
	}
}
