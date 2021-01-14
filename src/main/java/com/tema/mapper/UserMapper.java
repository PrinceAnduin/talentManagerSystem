package com.tema.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tema.entities.CompanyRelation;
import com.tema.entities.User;
@Mapper
public interface UserMapper {
	List<User> getAll();
	
	User getOne(int id);
	
	User getOneByEmail(String email);
 
	void insert(User user);
 
	void update(User user);
	
	CompanyRelation getCompany(int id);
	
	/**
	 * 更新用户公司，以插入的形式
	 * @param id
	 * @param companyId
	 */
	void addCompany(int id, int companyId);
	
	/**
	 * 更新用户公司，以更新的形式
	 * @param id
	 * @param companyId
	 */
	void setCompany(int id, int companyId);
	
	/**
	 * 用户设置为管理员
	 * @param id
	 */
	void setAdmin(int id);
	
	/**
	 * 用户设置为HR
	 * @param id
	 */
	void setHR(int id);
	
	/**
	 * 根据用户id查找界面
	 * @param companyId
	 * @return
	 */
	
	/**
	 * 设置用户工作
	 * @param id
	 */
	void setJob(int userId, int jobId);
	
	List<Integer> findPageByCompany(int companyId);
	
	List<String> getSearchHistory(int userId);
	
	void addHistory(int userId, String history);
}
