package com.hiringdefined.repository;

import com.hiringdefined.domain.InterviewStep;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the InterviewStep entity.
 */
public interface InterviewStepRepository extends MongoRepository<InterviewStep,String> {

}
