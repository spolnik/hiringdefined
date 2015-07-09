package com.hiringdefined.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hiringdefined.domain.OpenPosition;
import com.hiringdefined.repository.OpenPositionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing OpenPosition.
 */
@RestController
@RequestMapping("/api")
public class OpenPositionResource {

    private final Logger log = LoggerFactory.getLogger(OpenPositionResource.class);

    @Inject
    private OpenPositionRepository openPositionRepository;

    /**
     * POST  /openPositions -> Create a new openPosition.
     */
    @RequestMapping(value = "/openPositions",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody OpenPosition openPosition) throws URISyntaxException {
        log.debug("REST request to save OpenPosition : {}", openPosition);
        if (openPosition.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new openPosition cannot already have an ID").build();
        }
        openPositionRepository.save(openPosition);
        return ResponseEntity.created(new URI("/api/openPositions/" + openPosition.getId())).build();
    }

    /**
     * PUT  /openPositions -> Updates an existing openPosition.
     */
    @RequestMapping(value = "/openPositions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody OpenPosition openPosition) throws URISyntaxException {
        log.debug("REST request to update OpenPosition : {}", openPosition);
        if (openPosition.getId() == null) {
            return create(openPosition);
        }
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
    public List<OpenPosition> getAll() {
        log.debug("REST request to get all OpenPositions");
        return openPositionRepository.findAll();
    }

    /**
     * GET  /openPositions/:id -> get the "id" openPosition.
     */
    @RequestMapping(value = "/openPositions/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OpenPosition> get(@PathVariable Long id) {
        log.debug("REST request to get OpenPosition : {}", id);
        return Optional.ofNullable(openPositionRepository.findOne(id))
            .map(openPosition -> new ResponseEntity<>(
                openPosition,
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
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete OpenPosition : {}", id);
        openPositionRepository.delete(id);
    }
}
