package com.hiringdefined.web.rest.mapper;

import com.hiringdefined.domain.*;
import com.hiringdefined.web.rest.dto.CompanyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Company and its DTO CompanyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CompanyMapper {

    CompanyDTO companyToCompanyDTO(Company company);

    Company companyDTOToCompany(CompanyDTO companyDTO);
}
