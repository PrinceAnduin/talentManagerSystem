package com.tema.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tema.entities.Judge;

@Mapper
/**
 * 评价映射类，UserJudge是用户对公司的评价，CompanyJudge是公司对用户的评价
 */
public interface JudgeMapper {
	Judge queryUserJudge(int userId, int companyId);
	
	Judge queryCompanyJudge(int userId, int companyId);
	
	void insertUserJudge(Judge judge);
	
	void insertCompanyJudge(Judge judge);
	
	void updateUserJudge(Judge judge);
	
	void updateCompanyJudge(Judge judge);
	
	List<Judge> getUserJudges(int companyId, int start, int rows);
	
	List<Judge> getCompanyJudges(int userId, int start, int rows);
}
