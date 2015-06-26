package com.hiringdefined.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hiringdefined.domain.Interview;
import com.hiringdefined.repository.InterviewRepository;
import com.hiringdefined.web.rest.dto.InterviewDTO;
import com.hiringdefined.web.rest.mapper.InterviewMapper;
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
 * REST controller for managing Interview.
 */
@RestController
@RequestMapping("/api")
public class InterviewResource {

    private final Logger log = LoggerFactory.getLogger(InterviewResource.class);

    @Inject
    private InterviewRepository interviewRepository;

    @Inject
    private InterviewMapper interviewMapper;

    /**
     * POST  /interviews -> Create a new interview.
     */
    @RequestMapping(value = "/interviews",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody InterviewDTO interviewDTO) throws URISyntaxException {
        log.debug("REST request to save Interview : {}", interviewDTO);
        if (interviewDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new interview cannot already have an ID").build();
        }
        Interview interview = interviewMapper.interviewDTOToInterview(interviewDTO);
        interviewRepository.save(interview);
        return ResponseEntity.created(new URI("/api/interviews/" + interviewDTO.getId())).build();
    }

    /**
     * PUT  /interviews -> Updates an existing interview.
     */
    @RequestMapping(value = "/interviews",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody InterviewDTO interviewDTO) throws URISyntaxException {
        log.debug("REST request to update Interview : {}", interviewDTO);
        if (interviewDTO.getId() == null) {
            return create(interviewDTO);
        }
        Interview interview = interviewMapper.interviewDTOToInterview(interviewDTO);
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
    @Transactional(readOnly = true)
    public List<InterviewDTO> getAll() {
        log.debug("REST request to get all Interviews");
        return interviewRepository.findAll().stream()
            .map(interview -> interviewMapper.interviewToInterviewDTO(interview))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * GET  /interviews/:id -> get the "id" interview.
     */
    @RequestMapping(value = "/interviews/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InterviewDTO> get(@PathVariable String id) {
        log.debug("REST request to get Interview : {}", id);
        return Optional.ofNullable(interviewRepository.findOne(id))
            .map(interviewMapper::interviewToInterviewDTO)
            .map(interviewDTO -> new ResponseEntity<>(
                interviewDTO,
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
    public void delete(@PathVariable String id) {
        log.debug("REST request to delete Interview : {}", id);
        interviewRepository.delete(id);
    }
}
