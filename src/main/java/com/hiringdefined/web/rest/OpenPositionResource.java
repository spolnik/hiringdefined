package com.hiringdefined.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hiringdefined.domain.OpenPosition;
import com.hiringdefined.repository.OpenPositionRepository;
import com.hiringdefined.web.rest.util.PaginationUtil;
import com.hiringdefined.web.rest.dto.OpenPositionDTO;
import com.hiringdefined.web.rest.mapper.OpenPositionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing OpenPosition.
 */
@RestController
@RequestMapping("/api")
public class OpenPositionResource {

    private final Logger log = LoggerFactory.getLogger(OpenPositionResource.class);

    @Inject
    private OpenPositionRepository openPositionRepository;

    @Inject
    private OpenPositionMapper openPositionMapper;

    /**
     * POST  /openPositions -> Create a new openPosition.
     */
    @RequestMapping(value = "/openPositions",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody OpenPositionDTO openPositionDTO) throws URISyntaxException {
        log.debug("REST request to save OpenPosition : {}", openPositionDTO);
        if (openPositionDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new openPosition cannot already have an ID").build();
        }
        OpenPosition openPosition = openPositionMapper.openPositionDTOToOpenPosition(openPositionDTO);
        openPositionRepository.save(openPosition);
        return ResponseEntity.created(new URI("/api/openPositions/" + openPositionDTO.getId())).build();
    }

    /**
     * PUT  /openPositions -> Updates an existing openPosition.
     */
    @RequestMapping(value = "/openPositions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody OpenPositionDTO openPositionDTO) throws URISyntaxException {
        log.debug("REST request to update OpenPosition : {}", openPositionDTO);
        if (openPositionDTO.getId() == null) {
            return create(openPositionDTO);
        }
        OpenPosition openPosition = openPositionMapper.openPositionDTOToOpenPosition(openPositionDTO);
        openPositionRepository.save(openPosition);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /openPositions -> get all the openPositions.
     */
    @RequestMapping(value = "/openPositions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<OpenPositionDTO>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<OpenPosition> page = openPositionRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/openPositions", offset, limit);
        return new ResponseEntity<>(page.getContent().stream()
            .map(openPositionMapper::openPositionToOpenPositionDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /openPositions/:id -> get the "id" openPosition.
     */
    @RequestMapping(value = "/openPositions/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OpenPositionDTO> get(@PathVariable String id) {
        log.debug("REST request to get OpenPosition : {}", id);
        return Optional.ofNullable(openPositionRepository.findOne(id))
            .map(openPositionMapper::openPositionToOpenPositionDTO)
            .map(openPositionDTO -> new ResponseEntity<>(
                openPositionDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /openPositions/:id -> delete the "id" openPosition.
     */
    @RequestMapping(value = "/openPositions/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable String id) {
        log.debug("REST request to delete OpenPosition : {}", id);
        openPositionRepository.delete(id);
    }
}
