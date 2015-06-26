package com.hiringdefined.repository;

import com.hiringdefined.domain.Job;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Job entity.
 */
public interface JobRepository extends MongoRepository<Job,String> {

}
