package com.tema.forms;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ChangePasswordForm {
	@Size(min = 8, max = 15, message = "8-15 Characters")
	private String oldPassword;
	@Size(min = 8, max = 15, message = "8-15 Characters")
	private String password;
	private String comfirmedPassword;
}
