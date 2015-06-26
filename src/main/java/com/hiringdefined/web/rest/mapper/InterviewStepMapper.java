package com.hiringdefined.web.rest.mapper;

import com.hiringdefined.domain.*;
import com.hiringdefined.web.rest.dto.InterviewStepDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity InterviewStep and its DTO InterviewStepDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InterviewStepMapper {

    InterviewStepDTO interviewStepToInterviewStepDTO(InterviewStep interviewStep);

    InterviewStep interviewStepDTOToInterviewStep(InterviewStepDTO interviewStepDTO);
}
