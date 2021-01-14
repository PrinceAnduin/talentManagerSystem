package com.tema.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;

import com.tema.entities.JobSolr;

public interface JobRepository {
	@Query("*:*")
	Page<JobSolr> findAllWithPageable(Pageable pageable);
}
