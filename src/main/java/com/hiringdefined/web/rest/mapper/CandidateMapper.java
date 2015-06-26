package com.hiringdefined.web.rest.mapper;

import com.hiringdefined.domain.*;
import com.hiringdefined.web.rest.dto.CandidateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Candidate and its DTO CandidateDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CandidateMapper {

    CandidateDTO candidateToCandidateDTO(Candidate candidate);

    Candidate candidateDTOToCandidate(CandidateDTO candidateDTO);
}
