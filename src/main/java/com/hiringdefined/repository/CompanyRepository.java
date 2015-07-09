package com.hiringdefined.repository;

import com.hiringdefined.domain.Company;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Company entity.
 */
public interface CompanyRepository extends JpaRepository<Company,Long> {

    @Query("select company from Company company where company.user.login = ?#{principal.username}")
    List<Company> findAllForCurrentUser();

}
