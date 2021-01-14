package com.tema.entities;

import java.io.Serializable;

import lombok.Data;

@Data
public class Resume implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int userId;
	private User user;
	private String address;
	private String remark;
	
	public Resume(int id, int userId, String address, String remark) {
		super();
		this.id = id;
		this.userId = userId;
		this.user = null;
		this.address = address;
		this.remark = remark;
	}
}
