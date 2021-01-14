package com.tema.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.tema.entities.Company;

@Mapper
public interface CompanyMapper {
	
	void insert(Company company);
	
	Company getJobCompany(int jobId);
	
	Company getCompany(int id);
}
