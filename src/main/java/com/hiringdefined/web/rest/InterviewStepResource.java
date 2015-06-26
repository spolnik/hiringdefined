package com.hiringdefined.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hiringdefined.domain.InterviewStep;
import com.hiringdefined.repository.InterviewStepRepository;
import com.hiringdefined.web.rest.dto.InterviewStepDTO;
import com.hiringdefined.web.rest.mapper.InterviewStepMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing InterviewStep.
 */
@RestController
@RequestMapping("/api")
public class InterviewStepResource {

    private final Logger log = LoggerFactory.getLogger(InterviewStepResource.class);

    @Inject
    private InterviewStepRepository interviewStepRepository;

    @Inject
    private InterviewStepMapper interviewStepMapper;

    /**
     * POST  /interviewSteps -> Create a new interviewStep.
     */
    @RequestMapping(value = "/interviewSteps",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody InterviewStepDTO interviewStepDTO) throws URISyntaxException {
        log.debug("REST request to save InterviewStep : {}", interviewStepDTO);
        if (interviewStepDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new interviewStep cannot already have an ID").build();
        }
        InterviewStep interviewStep = interviewStepMapper.interviewStepDTOToInterviewStep(interviewStepDTO);
        interviewStepRepository.save(interviewStep);
        return ResponseEntity.created(new URI("/api/interviewSteps/" + interviewStepDTO.getId())).build();
    }

    /**
     * PUT  /interviewSteps -> Updates an existing interviewStep.
     */
    @RequestMapping(value = "/interviewSteps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody InterviewStepDTO interviewStepDTO) throws URISyntaxException {
        log.debug("REST request to update InterviewStep : {}", interviewStepDTO);
        if (interviewStepDTO.getId() == null) {
            return create(interviewStepDTO);
        }
        InterviewStep interviewStep = interviewStepMapper.interviewStepDTOToInterviewStep(interviewStepDTO);
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
    @Transactional(readOnly = true)
    public List<InterviewStepDTO> getAll() {
        log.debug("REST request to get all InterviewSteps");
        return interviewStepRepository.findAll().stream()
            .map(interviewStep -> interviewStepMapper.interviewStepToInterviewStepDTO(interviewStep))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * GET  /interviewSteps/:id -> get the "id" interviewStep.
     */
    @RequestMapping(value = "/interviewSteps/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InterviewStepDTO> get(@PathVariable String id) {
        log.debug("REST request to get InterviewStep : {}", id);
        return Optional.ofNullable(interviewStepRepository.findOne(id))
            .map(interviewStepMapper::interviewStepToInterviewStepDTO)
            .map(interviewStepDTO -> new ResponseEntity<>(
                interviewStepDTO,
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
    public void delete(@PathVariable String id) {
        log.debug("REST request to delete InterviewStep : {}", id);
        interviewStepRepository.delete(id);
    }
}
