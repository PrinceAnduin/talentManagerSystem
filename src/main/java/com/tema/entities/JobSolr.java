package com.tema.entities;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

import lombok.Data;

@SolrDocument(solrCoreName = "new_core")
@Data
public class JobSolr {
	@Field("id")
	int id;
	@Field("name")
	String name;
	@Field("description")
	String description;
}
