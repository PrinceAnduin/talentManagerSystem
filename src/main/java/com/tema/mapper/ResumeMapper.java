package com.tema.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tema.entities.Resume;

@Mapper
public interface ResumeMapper {
	int count();
	
	int countByUserId(int userId);
	
	List<Resume> findPage(int pageNo,int pageSize);
	
	List<Resume> findPageByUserId(int userId, int pageNo, int pageSize);
	
	Resume findById(int id);
	
	void insert(Resume resume);
}
