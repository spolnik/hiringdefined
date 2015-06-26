package com.hiringdefined.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hiringdefined.domain.Candidate;
import com.hiringdefined.repository.CandidateRepository;
import com.hiringdefined.web.rest.util.PaginationUtil;
import com.hiringdefined.web.rest.dto.CandidateDTO;
import com.hiringdefined.web.rest.mapper.CandidateMapper;
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
 * REST controller for managing Candidate.
 */
@RestController
@RequestMapping("/api")
public class CandidateResource {

    private final Logger log = LoggerFactory.getLogger(CandidateResource.class);

    @Inject
    private CandidateRepository candidateRepository;

    @Inject
    private CandidateMapper candidateMapper;

    /**
     * POST  /candidates -> Create a new candidate.
     */
    @RequestMapping(value = "/candidates",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody CandidateDTO candidateDTO) throws URISyntaxException {
        log.debug("REST request to save Candidate : {}", candidateDTO);
        if (candidateDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new candidate cannot already have an ID").build();
        }
        Candidate candidate = candidateMapper.candidateDTOToCandidate(candidateDTO);
        candidateRepository.save(candidate);
        return ResponseEntity.created(new URI("/api/candidates/" + candidateDTO.getId())).build();
    }

    /**
     * PUT  /candidates -> Updates an existing candidate.
     */
    @RequestMapping(value = "/candidates",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody CandidateDTO candidateDTO) throws URISyntaxException {
        log.debug("REST request to update Candidate : {}", candidateDTO);
        if (candidateDTO.getId() == null) {
            return create(candidateDTO);
        }
        Candidate candidate = candidateMapper.candidateDTOToCandidate(candidateDTO);
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
    @Transactional(readOnly = true)
    public ResponseEntity<List<CandidateDTO>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Candidate> page = candidateRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/candidates", offset, limit);
        return new ResponseEntity<>(page.getContent().stream()
            .map(candidateMapper::candidateToCandidateDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /candidates/:id -> get the "id" candidate.
     */
    @RequestMapping(value = "/candidates/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CandidateDTO> get(@PathVariable String id) {
        log.debug("REST request to get Candidate : {}", id);
        return Optional.ofNullable(candidateRepository.findOne(id))
            .map(candidateMapper::candidateToCandidateDTO)
            .map(candidateDTO -> new ResponseEntity<>(
                candidateDTO,
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
    public void delete(@PathVariable String id) {
        log.debug("REST request to delete Candidate : {}", id);
        candidateRepository.delete(id);
    }
}
