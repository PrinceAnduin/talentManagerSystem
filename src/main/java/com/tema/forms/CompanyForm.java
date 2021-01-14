package com.tema.forms;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CompanyForm {
	@Size(min = 1, max = 50, message = "字数限制：1到50个字")
	private String name;
	@Size(min = 1, max = 1000, message = "字数限制：1到1000个字")
	private String description;
}
