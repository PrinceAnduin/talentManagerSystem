package com.tema.entities;

import lombok.Data;

@Data
public class CompanyRelation {
	int userId;
	int companyId;
	boolean isHR;
	boolean isAdmin;
	

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
