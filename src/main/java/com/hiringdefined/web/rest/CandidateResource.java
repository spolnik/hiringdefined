package com.hiringdefined.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hiringdefined.domain.Candidate;
import com.hiringdefined.repository.CandidateRepository;
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
 * REST controller for managing Candidate.
 */
@RestController
@RequestMapping("/api")
public class CandidateResource {

    private final Logger log = LoggerFactory.getLogger(CandidateResource.class);

    @Inject
    private CandidateRepository candidateRepository;

    /**
     * POST  /candidates -> Create a new candidate.
     */
    @RequestMapping(value = "/candidates",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Candidate candidate) throws URISyntaxException {
        log.debug("REST request to save Candidate : {}", candidate);
        if (candidate.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new candidate cannot already have an ID").build();
        }
        candidateRepository.save(candidate);
        return ResponseEntity.created(new URI("/api/candidates/" + candidate.getId())).build();
    }

    /**
     * PUT  /candidates -> Updates an existing candidate.
     */
    @RequestMapping(value = "/candidates",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Candidate candidate) throws URISyntaxException {
        log.debug("REST request to update Candidate : {}", candidate);
        if (candidate.getId() == null) {
            return create(candidate);
        }
        candidateRepository.save(candidate);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /candidates -> get all the candidates.
     */
    @RequestMapping(value = "/candidates",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Candidate> getAll() {
        log.debug("REST request to get all Candidates");
        return candidateRepository.findAll();
    }

    /**
     * GET  /candidates/:id -> get the "id" candidate.
     */
    @RequestMapping(value = "/candidates/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Candidate> get(@PathVariable Long id) {
        log.debug("REST request to get Candidate : {}", id);
        return Optional.ofNullable(candidateRepository.findOne(id))
            .map(candidate -> new ResponseEntity<>(
                candidate,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /candidates/:id -> delete the "id" candidate.
     */
    @RequestMapping(value = "/candidates/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Candidate : {}", id);
        candidateRepository.delete(id);
    }
}
