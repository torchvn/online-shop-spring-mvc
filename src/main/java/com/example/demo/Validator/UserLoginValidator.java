package com.example.demo.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.demo.Dao.UserDao;
import com.example.demo.Entity.User;
import com.example.demo.Model.UserLogin;
import com.example.demo.Service.SessionService;

@Component
public class UserLoginValidator implements Validator {
	@Autowired
	private UserDao dao;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz == UserLogin.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		UserLogin entity = (UserLogin) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotBlank.userLogin.username");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotBlank.userLogin.password");

		if (!errors.hasFieldErrors()) {
			User user = dao.findByEmail(entity.getUsername());
			if (user == null) {
				errors.rejectValue("username", "NotFind.userLogin.username");
				errors.rejectValue("password", "NotFind.userLogin.password");
			}
			else {
			//	passwordEncoder.matches(user.getPassword(), entity.getPassword())
//				if(user.getPassword().equals(entity.getPassword()) == false) {
				if(passwordEncoder.matches(entity.getPassword(), user.getPassword()) == false) {
					errors.rejectValue("password", "NotFind.userLogin.password");
				}
				//errors.rejectValue("password", "NotFind.userLogin.password");
			}
		}

	}

}
