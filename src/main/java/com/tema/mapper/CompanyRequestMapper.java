package com.tema.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tema.entities.CompanyRequest;

@Mapper
public interface CompanyRequestMapper {
	/**
	 * 插入一个请求
	 * @param companyRequest
	 */
	void insert(CompanyRequest companyRequest);
	
	/**
	 * 通过请求
	 * @param id
	 */
	void gotRequest(int id);
	
	/**
	 * 所有未阅读得请求
	 * @param companyId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<CompanyRequest> findPage(int pageNo, int pageSize);
	
	/**
	 * 根据id找到公司请求
	 * @param id
	 * @return
	 */
	CompanyRequest findOne(int id);
}
