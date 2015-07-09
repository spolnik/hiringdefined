package com.hiringdefined.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hiringdefined.domain.InterviewStep;
import com.hiringdefined.repository.InterviewStepRepository;
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
 * REST controller for managing InterviewStep.
 */
@RestController
@RequestMapping("/api")
public class InterviewStepResource {

    private final Logger log = LoggerFactory.getLogger(InterviewStepResource.class);

    @Inject
    private InterviewStepRepository interviewStepRepository;

    /**
     * POST  /interviewSteps -> Create a new interviewStep.
     */
    @RequestMapping(value = "/interviewSteps",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody InterviewStep interviewStep) throws URISyntaxException {
        log.debug("REST request to save InterviewStep : {}", interviewStep);
        if (interviewStep.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new interviewStep cannot already have an ID").build();
        }
        interviewStepRepository.save(interviewStep);
        return ResponseEntity.created(new URI("/api/interviewSteps/" + interviewStep.getId())).build();
    }

    /**
     * PUT  /interviewSteps -> Updates an existing interviewStep.
     */
    @RequestMapping(value = "/interviewSteps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody InterviewStep interviewStep) throws URISyntaxException {
        log.debug("REST request to update InterviewStep : {}", interviewStep);
        if (interviewStep.getId() == null) {
            return create(interviewStep);
        }
        interviewStepRepository.save(interviewStep);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /interviewSteps -> get all the interviewSteps.
     */
    @RequestMapping(value = "/interviewSteps",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InterviewStep> getAll() {
        log.debug("REST request to get all InterviewSteps");
        return interviewStepRepository.findAll();
    }

    /**
     * GET  /interviewSteps/:id -> get the "id" interviewStep.
     */
    @RequestMapping(value = "/interviewSteps/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InterviewStep> get(@PathVariable Long id) {
        log.debug("REST request to get InterviewStep : {}", id);
        return Optional.ofNullable(interviewStepRepository.findOne(id))
            .map(interviewStep -> new ResponseEntity<>(
                interviewStep,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /interviewSteps/:id -> delete the "id" interviewStep.
     */
    @RequestMapping(value = "/interviewSteps/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete InterviewStep : {}", id);
        interviewStepRepository.delete(id);
    }
}
