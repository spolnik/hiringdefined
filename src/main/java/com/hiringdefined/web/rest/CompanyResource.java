package com.hiringdefined.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hiringdefined.domain.Company;
import com.hiringdefined.repository.CompanyRepository;
import com.hiringdefined.web.rest.dto.CompanyDTO;
import com.hiringdefined.web.rest.mapper.CompanyMapper;
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
 * REST controller for managing Company.
 */
@RestController
@RequestMapping("/api")
public class CompanyResource {

    private final Logger log = LoggerFactory.getLogger(CompanyResource.class);

    @Inject
    private CompanyRepository companyRepository;

    @Inject
    private CompanyMapper companyMapper;

    /**
     * POST  /companies -> Create a new company.
     */
    @RequestMapping(value = "/companies",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody CompanyDTO companyDTO) throws URISyntaxException {
        log.debug("REST request to save Company : {}", companyDTO);
        if (companyDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new company cannot already have an ID").build();
        }
        Company company = companyMapper.companyDTOToCompany(companyDTO);
        companyRepository.save(company);
        return ResponseEntity.created(new URI("/api/companies/" + companyDTO.getId())).build();
    }

    /**
     * PUT  /companies -> Updates an existing company.
     */
    @RequestMapping(value = "/companies",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody CompanyDTO companyDTO) throws URISyntaxException {
        log.debug("REST request to update Company : {}", companyDTO);
        if (companyDTO.getId() == null) {
            return create(companyDTO);
        }
        Company company = companyMapper.companyDTOToCompany(companyDTO);
        companyRepository.save(company);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /companies -> get all the companies.
     */
    @RequestMapping(value = "/companies",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<CompanyDTO> getAll() {
        log.debug("REST request to get all Companies");
        return companyRepository.findAll().stream()
            .map(company -> companyMapper.companyToCompanyDTO(company))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * GET  /companies/:id -> get the "id" company.
     */
    @RequestMapping(value = "/companies/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CompanyDTO> get(@PathVariable String id) {
        log.debug("REST request to get Company : {}", id);
        return Optional.ofNullable(companyRepository.findOne(id))
            .map(companyMapper::companyToCompanyDTO)
            .map(companyDTO -> new ResponseEntity<>(
                companyDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /companies/:id -> delete the "id" company.
     */
    @RequestMapping(value = "/companies/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable String id) {
        log.debug("REST request to delete Company : {}", id);
        companyRepository.delete(id);
    }
}
