package com.tema.forms;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SignUpForm {
	@Size(min = 1, max = 10, message = "1-10 Characters")
	private String name;
	@Size(min = 8, max = 15, message = "8-15 Characters")
	private String password;
	private String comfirmedPassword;
	@NotBlank(message = "REQUIRE")
	@Email(message = "error in your email format")
	private String email;
	@Size(min = 11, max = 11, message = "11 Characters")
	private String phone;
	private short gender;
	private Date birth;
	private String captcha;
	private String sentCaptcha;
}
