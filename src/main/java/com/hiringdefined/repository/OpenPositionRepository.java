package com.hiringdefined.repository;

import com.hiringdefined.domain.OpenPosition;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OpenPosition entity.
 */
public interface OpenPositionRepository extends JpaRepository<OpenPosition,Long> {

}
