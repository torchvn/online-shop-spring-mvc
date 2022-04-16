package com.example.demo.Model;

import javax.mail.internet.InternetAddress;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailInfo {
	String from;
	String to;
	String[] cc;
	String[] bcc;
	String subject;
	String body;
	String[] attachment;
	public MailInfo(String to, String subject, String body) {
		this.from = "Test Application send code to email";
		this.to = to;
		this.subject = subject;
		this.body = body;
	}
}