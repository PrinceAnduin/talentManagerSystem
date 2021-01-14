package com.tema.entities;

import java.io.Serializable;

import lombok.Data;

@Data
public class JobRequest implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private Job job;
	private User user;
	private Resume resume;
	private boolean delete;
	private boolean acceptance;
	private boolean finalAcceptance;
	
	
}
