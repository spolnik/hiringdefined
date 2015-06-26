package com.hiringdefined.web.rest.mapper;

import com.hiringdefined.domain.*;
import com.hiringdefined.web.rest.dto.JobDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Job and its DTO JobDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface JobMapper {

    JobDTO jobToJobDTO(Job job);

    Job jobDTOToJob(JobDTO jobDTO);
}
