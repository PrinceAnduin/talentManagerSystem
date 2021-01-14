package com.tema.forms;

import java.util.Date;

import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import lombok.Data;

@Data
public class JobForm {
	
	@Size(min = 1, max = 50, message = "1到50个字符")
	private String name;
	@Size(min = 1, max = 200, message = "1到200个字符")
	private	String description;
	@NumberFormat
	private int needsNum;
	@NumberFormat
	private int salary;
	@DateTimeFormat
	private Date endTime;
	@NumberFormat
	private int smallAge;
	@NumberFormat
	private int bigAge;
	private int education;
}
