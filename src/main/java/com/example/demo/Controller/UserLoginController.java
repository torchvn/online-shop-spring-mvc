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

import com.example.demo.Dao.UserDao;
import com.example.demo.Entity.User;
import com.example.demo.Model.UserLogin;
import com.example.demo.Service.SessionService;
import com.example.demo.Validator.UserLoginValidator;

@Controller
public class UserLoginController {
	@Autowired
	UserDao dao;
	
	@Autowired
	UserLoginValidator userLoginValidator;
	
	@Autowired
	private SessionService sessionService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		Object target = binder.getTarget();
		if(target == null) {
			return;
		}
		if(target.getClass()==UserLogin.class) {
			binder.setValidator(userLoginValidator);
		}
	}
	
	@GetMapping("/login")
	public String index(Model model) {
		sessionService.set("code", "Asdsfdsf2=-##2");
		UserLogin entity = new UserLogin();
		model.addAttribute("userLogin", entity);
		return "user/login";
	}
	
	@PostMapping("/login")
	public String login(Model model, @ModelAttribute("userLogin") @Validated UserLogin entity, BindingResult result) {
		if(result.hasErrors()) {
			return "user/login";
		}
		System.out.println("đã đăng nhập");
		//System.out.println(entity.getUsername());
		User user = dao.findByEmail(entity.getUsername());
		sessionService.set("userLogin", user);
		return "user/index";
	}
	
}
