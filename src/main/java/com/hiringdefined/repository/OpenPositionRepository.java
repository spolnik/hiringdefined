package com.hiringdefined.repository;

import com.hiringdefined.domain.OpenPosition;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the OpenPosition entity.
 */
public interface OpenPositionRepository extends MongoRepository<OpenPosition,String> {

}
