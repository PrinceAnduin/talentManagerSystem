package com.tema.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tema.entities.Job;

@Mapper
public interface JobMapper {
	Job findJob(int jobId);
	
	Job getUserJob(int userId);
	
	void insert(Job job);
	
	List<Job> findPage(int pageNo,int pageSize);
}
