package com.tema.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String email;
	private String password;
	private Date birth;
	private String phone;
	private short gender;
	private Company company;
	private boolean isHR;
	private boolean isAdmin;
	private Job job;
	
	public boolean getIsHR() {
		return isHR;
	}
	public void setIsHR(boolean isHR) {
		this.isHR = isHR;
	}
	public boolean getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
}
