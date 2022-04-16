package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.example.demo.Dao.UserDao;
import com.example.demo.Entity.User;
import com.example.demo.Model.AddressModel;
import com.example.demo.Model.ChangePassword;
import com.example.demo.Service.SessionService;
import com.example.demo.Validator.AddressValidator;
import com.example.demo.Validator.ChangePassValidator;

@Controller
public class InforProfileController {
	@Autowired
	ChangePassValidator changepassValidator;
	
	@Autowired
	AddressDao dao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	SessionService sessionService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		Object target = binder.getTarget();
		//Object target2 = binder2.getTarget();
		if (target == null) {
			return;
		}
		if (target.getClass() == ChangePassword.class) {
			binder.setValidator(changepassValidator);
		}
	}
	
	@GetMapping("/shop/profile/user")
	public String index(Model model) {
		ChangePassword entity = new ChangePassword();
		User user = sessionService.get("userLogin");
		entity.setEmail(user.getEmail());
		entity.setFullname(user.getFullname());
		model.addAttribute("user", entity);
		return "user/user";
	}
	
	@PostMapping("/shop/profile/update/user")
	public String save(Model model, @ModelAttribute("user") @Validated ChangePassword entity, BindingResult result) {		
		if (result.hasErrors()) {
			return "user/user";
		} else {
			User user = sessionService.get("userLogin");
			user.setFullname(entity.getFullname());
			if(!entity.getNewPassword().isEmpty()) {
				String password = passwordEncoder.encode(entity.getNewPassword());
				user.setPassword(password);
			}
			userDao.save(user);
			return "redirect:/shop/profile/user";
		}
	}
}
