package com.tema.entities;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.solr.core.mapping.SolrDocument;

import lombok.Data;
@SolrDocument(solrCoreName = "new_core")
@Data
public class Job implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//工资， 发布日期， 截止日期，年龄限制，学历要求，发布人
	private int id;
	private int companyId;
	private Company company;
	private String name;
	private	String description;
	private int needsNum;
	private int nowNum;
	
	private int salary;
	private Date endTime;
	private int smallAge;
	private int bigAge;
	private int education;
	private Date startDate;
}
