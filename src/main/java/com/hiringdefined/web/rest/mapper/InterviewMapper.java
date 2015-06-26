package com.hiringdefined.web.rest.mapper;

import com.hiringdefined.domain.*;
import com.hiringdefined.web.rest.dto.InterviewDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Interview and its DTO InterviewDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InterviewMapper {

    InterviewDTO interviewToInterviewDTO(Interview interview);

    Interview interviewDTOToInterview(InterviewDTO interviewDTO);
}
