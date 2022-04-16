package com.example.demo.Model;

import lombok.Data;

@Data
public class ChangePassword {
	public String email;
	public String fullname;
	private String oldPassword;
	private String newPassword;
	private String confirm;
}
