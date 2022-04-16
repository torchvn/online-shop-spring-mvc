package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Dao.AddressDao;
import com.example.demo.Entity.Address;
import com.example.demo.Entity.User;
import com.example.demo.Model.AddressModel;
import com.example.demo.Service.SessionService;
import com.example.demo.Validator.AddressValidator;

@Controller
public class AddressProfileController {
	@Autowired
	AddressValidator addressValidator;
	
	@Autowired
	AddressDao dao;
	
	@Autowired
	SessionService sessionService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		Object target = binder.getTarget();
		//Object target2 = binder2.getTarget();
		if (target == null) {
			return;
		}
		if (target.getClass() == AddressModel.class) {
			binder.setValidator(addressValidator);
		}
	}
	
	@GetMapping("/shop/profile/address")
	public String index(Model model) {
		User user = sessionService.get("userLogin");
		Address address = dao.getAddress(user.getId());
		AddressModel entity = new AddressModel();
		if (address != null) {
			entity.setFirstname(address.getFirstname());
			entity.setLastname(address.getLastname());
			entity.setAddress(address.getAddress());
			entity.setEmail(address.getEmail());
			entity.setPhone(address.getPhone());
			entity.setProvincial(address.getProvincial());
		}
		
		model.addAttribute("address", entity);
		return "user/address";
	}
	
	@PostMapping("/shop/profile/update/address")
	public String save(Model model, @ModelAttribute("address") @Validated AddressModel entity, BindingResult result) {
		if (result.hasErrors()) {
			return "user/address";
		} else {
			User user = sessionService.get("userLogin");
			Address address = dao.getAddress(user.getId());
			if (address == null) {
				address = new Address(entity.getFirstname(), entity.getLastname(), entity.getEmail(),
						entity.getPhone(), entity.getProvincial(), entity.getAddress(), user);
				dao.save(address);
			} else {
				address.setAddress(entity.getAddress());
				address.setEmail(entity.getEmail());
				address.setFirstname(entity.getFirstname());
				address.setLastname(entity.getLastname());
				address.setPhone(entity.getPhone());
				address.setProvincial(entity.getProvincial());
				dao.save(address);
			}
			return "redirect:/shop/profile/address";
		}	
	}
}
