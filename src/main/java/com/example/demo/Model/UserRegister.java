package com.example.demo.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRegister {
	private String email;
	private String fullname;
	private String code;
	private String password;
	private String confirm;
}