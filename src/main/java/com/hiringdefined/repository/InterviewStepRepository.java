package com.hiringdefined.repository;

import com.hiringdefined.domain.InterviewStep;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InterviewStep entity.
 */
public interface InterviewStepRepository extends JpaRepository<InterviewStep,Long> {

}
