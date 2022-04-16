package com.example.demo.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Dao.ManufactureDao;
import com.example.demo.Entity.Category;
import com.example.demo.Entity.Manufacture;
import com.example.demo.Model.Categories;
import com.example.demo.Model.ManufactureModel;
import com.example.demo.Service.ParamService;
import com.example.demo.Service.SessionService;
import com.example.demo.Validator.ManufactureValidator;

@Controller
public class ManufactureController {
	@Autowired
	SessionService session;
	
	@Autowired
	ParamService param;
	
	@Autowired
	ManufactureDao dao;
	
	@Autowired
	ManufactureValidator manuValidator;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		Object target = binder.getTarget();
		if (target == null) {
			return;
		}
		if (target.getClass() == ManufactureModel.class) {
			binder.setValidator(manuValidator);
		}
	}
	
	@GetMapping("/admin/manuForm/form")
	public String index(Model model) {
		session.set("nameImage2", "");
		ManufactureModel entity = new ManufactureModel();
		model.addAttribute("manuForm", entity);
		return "manager/manuForm";
	}

	@RequestMapping("/admin/manuForm/list")
	public String index(Model model, @RequestParam("keyword4") Optional<String> name,
			@RequestParam("p") Optional<Integer> p) {
		String findName;
		if (session.get("keyword4") == null) {
			findName = name.orElse("");
		} else {
			findName = name.orElse(session.get("keyword4"));
		}

		session.set("keyword4", findName);

		Pageable pageable = PageRequest.of(p.orElse(0), 5);

		Page<Manufacture> page = dao.fillToTable("%" + findName + "%", pageable);
		model.addAttribute("manuItem", page);

		return "manager/manuTable";
	}
	
	@RequestMapping("/admin/manuForm/list/delete/{id}")
	public String delete(Model model, @PathVariable("id") int id) {
//		Manufacture entity = dao.getById(id);
		Manufacture entity = dao.getOne(id);
		dao.delete(entity);
		return "redirect:/admin/manuForm/list";
	}

	@PostMapping("/admin/manuForm/form")
	public String save(Model model, @ModelAttribute("manuForm") @Validated ManufactureModel entity,
			BindingResult result) {
		if (result.hasErrors()) {
			return "manager/manuForm";
		}

		else {			
			Manufacture manufacture = new Manufacture(entity.getName(), param.save(entity.getImage()), entity.getInformation());
			dao.save(manufacture);
		}
		return "redirect:/admin/manuForm/list";
	}
	
	@RequestMapping("/admin/manuForm/refresh")
	public String refresh(Model model) {
		return "redirect:/admin/manuForm/form";
	}
	
	@GetMapping("/admin/manuForm/list/load/{id}")
	public String load(Model model, @PathVariable("id") int id) {
//		Manufacture manufacture = dao.getById(id);
		Manufacture manufacture = dao.getOne(id);
		ManufactureModel entity = new ManufactureModel();
		entity.setName(manufacture.getName());
		entity.setInformation(manufacture.getInformation());
		model.addAttribute("edit2", true);
		model.addAttribute("manuForm", entity);
		session.set("nameImage2", manufacture.getImage());
		session.set("id2", manufacture.getId());
		return "manager/manuForm";
	}

	@RequestMapping("/admin/manuForm/form/edit/{id}")
	public String edit(Model model, @PathVariable("id") int id, @ModelAttribute("manuForm") ManufactureModel entity,
			BindingResult result) {
		model.addAttribute("edit2", true);
		if (entity.getName().isEmpty()) {
			result.rejectValue("name", "NotBlank.manuForm.name");
		}
		if(entity.getInformation().isEmpty()) {
			result.rejectValue("information", "NotBlank.manuForm.information");
		}
		if(!result.hasErrors()) {
//			Manufacture manufacture = dao.getById(id);
			Manufacture manufacture = dao.getOne(id);
			manufacture.setName(entity.getName());
			manufacture.setInformation(entity.getInformation());
			if (!entity.getImage().isEmpty()) {				
				manufacture.setImage(param.save(entity.getImage()));			
			}
			dao.save(manufacture);
			model.addAttribute("edit2", false);
			session.set("nameImage2", "");
			return "redirect:/admin/manuForm/list";
		}
		
		return "manager/manuForm";
	}
}
