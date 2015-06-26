package com.hiringdefined.repository;

import com.hiringdefined.domain.Candidate;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Candidate entity.
 */
public interface CandidateRepository extends MongoRepository<Candidate,String> {

}
