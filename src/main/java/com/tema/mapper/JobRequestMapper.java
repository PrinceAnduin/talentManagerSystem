package com.tema.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tema.entities.JobRequest;
@Mapper
public interface JobRequestMapper {
	/**
	 * 插入一个申请
	 * @param juRequest
	 */
	void insert(JobRequest juRequest);
	
	/**
	 * 找到所有本公司收到的未通过初审申请
	 * @param companyId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<JobRequest> findReceviePageByAcctance(int companyId, int pageNo, int pageSize);
	
	/**
	 * 找到所有本公司收到的未通过终审申请
	 * @param companyId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<JobRequest> findReceviePageByFinalAcctance(int companyId, int pageNo, int pageSize);
	
	/**
	 * 找到所有本公司收到的申请
	 * @param companyId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<JobRequest> findReceviePage(int companyId, int pageNo, int pageSize);
	
	/**
	 * 查询所有发出的申请
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<JobRequest> findSendPage(int userId, int pageNo, int pageSize);
	
	/**
	 * 设置申请的初次通过
	 * @param id
	 * @param acceptance
	 */
	void setRequestAcceptcance(int id, boolean acceptance);
	
	/**
	 * 设置申请的最终通过
	 * @param id
	 * @param acceptance
	 */
	void setFinalAcceptcance(int id, boolean finalAcceptance);
	
	/**
	 * 设置申请的否决
	 * @param id
	 * @param delete
	 */
	void setDelete(int id, boolean delete);
	
	/**
	 * 根据申请id找到申请者id
	 * @param id
	 * @return
	 */
	int getUserId(int id);
	
	/**
	 * 根据申请id找到公司id
	 * @param id
	 * @return
	 */
	int getCompanyId(int id);
	
	/**
	 * 将除本id的其他用户申请id均设为无效
	 */
	void setAllDelete(int id);
}
