package com.hiringdefined.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hiringdefined.domain.Interview;
import com.hiringdefined.repository.InterviewRepository;
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
 * REST controller for managing Interview.
 */
@RestController
@RequestMapping("/api")
public class InterviewResource {

    private final Logger log = LoggerFactory.getLogger(InterviewResource.class);

    @Inject
    private InterviewRepository interviewRepository;

    /**
     * POST  /interviews -> Create a new interview.
     */
    @RequestMapping(value = "/interviews",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Interview interview) throws URISyntaxException {
        log.debug("REST request to save Interview : {}", interview);
        if (interview.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new interview cannot already have an ID").build();
        }
        interviewRepository.save(interview);
        return ResponseEntity.created(new URI("/api/interviews/" + interview.getId())).build();
    }

    /**
     * PUT  /interviews -> Updates an existing interview.
     */
    @RequestMapping(value = "/interviews",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Interview interview) throws URISyntaxException {
        log.debug("REST request to update Interview : {}", interview);
        if (interview.getId() == null) {
            return create(interview);
        }
        interviewRepository.save(interview);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /interviews -> get all the interviews.
     */
    @RequestMapping(value = "/interviews",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Interview> getAll() {
        log.debug("REST request to get all Interviews");
        return interviewRepository.findAll();
    }

    /**
     * GET  /interviews/:id -> get the "id" interview.
     */
    @RequestMapping(value = "/interviews/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Interview> get(@PathVariable Long id) {
        log.debug("REST request to get Interview : {}", id);
        return Optional.ofNullable(interviewRepository.findOne(id))
            .map(interview -> new ResponseEntity<>(
                interview,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /interviews/:id -> delete the "id" interview.
     */
    @RequestMapping(value = "/interviews/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Interview : {}", id);
        interviewRepository.delete(id);
    }
}
