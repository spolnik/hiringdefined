package com.hiringdefined.repository;

import com.hiringdefined.domain.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Company entity.
 */
public interface CompanyRepository extends MongoRepository<Company,String> {

}
