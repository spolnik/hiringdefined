package com.hiringdefined.repository;

import com.hiringdefined.domain.Interview;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Interview entity.
 */
public interface InterviewRepository extends MongoRepository<Interview,String> {

}
