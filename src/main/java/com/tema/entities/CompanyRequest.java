package com.tema.entities;

import java.io.Serializable;

import lombok.Data;

@Data
public class CompanyRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private String description;
	private User user;
}
