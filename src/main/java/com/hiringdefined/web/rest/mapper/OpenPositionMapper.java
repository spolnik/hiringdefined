package com.hiringdefined.web.rest.mapper;

import com.hiringdefined.domain.*;
import com.hiringdefined.web.rest.dto.OpenPositionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OpenPosition and its DTO OpenPositionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OpenPositionMapper {

    OpenPositionDTO openPositionToOpenPositionDTO(OpenPosition openPosition);

    OpenPosition openPositionDTOToOpenPosition(OpenPositionDTO openPositionDTO);
}
