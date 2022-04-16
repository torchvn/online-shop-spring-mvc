package com.example.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressModel {
	private String firstname;
	private String lastname;
	private String email;
	private String phone;
	private String provincial;
	private String address;
	private boolean payment = true;
	
	//user
	
//	public String email2;
//	public String fullname;
//	private String oldPassword;
//	private String newPassword;
//	private String confirm;
	
}
