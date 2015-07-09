package com.hiringdefined.repository;

import com.hiringdefined.domain.Interview;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Interview entity.
 */
public interface InterviewRepository extends JpaRepository<Interview,Long> {

    @Query("select interview from Interview interview where interview.user.login = ?#{principal.username}")
    List<Interview> findAllForCurrentUser();

}
