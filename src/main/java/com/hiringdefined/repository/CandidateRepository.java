package com.hiringdefined.repository;

import com.hiringdefined.domain.Candidate;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Candidate entity.
 */
public interface CandidateRepository extends JpaRepository<Candidate,Long> {

}
