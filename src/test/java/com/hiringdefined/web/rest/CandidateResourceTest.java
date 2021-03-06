package com.hiringdefined.web.rest;

import com.hiringdefined.Application;
import com.hiringdefined.domain.Candidate;
import com.hiringdefined.repository.CandidateRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CandidateResource REST controller.
 *
 * @see CandidateResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CandidateResourceTest {

    private static final String DEFAULT_FULL_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_FULL_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_EMAIL = "SAMPLE_TEXT";
    private static final String UPDATED_EMAIL = "UPDATED_TEXT";
    private static final String DEFAULT_LINKEDIN = "SAMPLE_TEXT";
    private static final String UPDATED_LINKEDIN = "UPDATED_TEXT";
    private static final String DEFAULT_GITHUB = "SAMPLE_TEXT";
    private static final String UPDATED_GITHUB = "UPDATED_TEXT";
    private static final String DEFAULT_MOTIVATION = "SAMPLE_TEXT";
    private static final String UPDATED_MOTIVATION = "UPDATED_TEXT";

    @Inject
    private CandidateRepository candidateRepository;

    private MockMvc restCandidateMockMvc;

    private Candidate candidate;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CandidateResource candidateResource = new CandidateResource();
        ReflectionTestUtils.setField(candidateResource, "candidateRepository", candidateRepository);
        this.restCandidateMockMvc = MockMvcBuilders.standaloneSetup(candidateResource).build();
    }

    @Before
    public void initTest() {
        candidate = new Candidate();
        candidate.setFullName(DEFAULT_FULL_NAME);
        candidate.setEmail(DEFAULT_EMAIL);
        candidate.setLinkedin(DEFAULT_LINKEDIN);
        candidate.setGithub(DEFAULT_GITHUB);
        candidate.setMotivation(DEFAULT_MOTIVATION);
    }

    @Test
    @Transactional
    public void createCandidate() throws Exception {
        int databaseSizeBeforeCreate = candidateRepository.findAll().size();

        // Create the Candidate
        restCandidateMockMvc.perform(post("/api/candidates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(candidate)))
                .andExpect(status().isCreated());

        // Validate the Candidate in the database
        List<Candidate> candidates = candidateRepository.findAll();
        assertThat(candidates).hasSize(databaseSizeBeforeCreate + 1);
        Candidate testCandidate = candidates.get(candidates.size() - 1);
        assertThat(testCandidate.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testCandidate.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCandidate.getLinkedin()).isEqualTo(DEFAULT_LINKEDIN);
        assertThat(testCandidate.getGithub()).isEqualTo(DEFAULT_GITHUB);
        assertThat(testCandidate.getMotivation()).isEqualTo(DEFAULT_MOTIVATION);
    }

    @Test
    @Transactional
    public void getAllCandidates() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get all the candidates
        restCandidateMockMvc.perform(get("/api/candidates"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(candidate.getId().intValue())))
                .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].linkedin").value(hasItem(DEFAULT_LINKEDIN.toString())))
                .andExpect(jsonPath("$.[*].github").value(hasItem(DEFAULT_GITHUB.toString())))
                .andExpect(jsonPath("$.[*].motivation").value(hasItem(DEFAULT_MOTIVATION.toString())));
    }

    @Test
    @Transactional
    public void getCandidate() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get the candidate
        restCandidateMockMvc.perform(get("/api/candidates/{id}", candidate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(candidate.getId().intValue()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.linkedin").value(DEFAULT_LINKEDIN.toString()))
            .andExpect(jsonPath("$.github").value(DEFAULT_GITHUB.toString()))
            .andExpect(jsonPath("$.motivation").value(DEFAULT_MOTIVATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCandidate() throws Exception {
        // Get the candidate
        restCandidateMockMvc.perform(get("/api/candidates/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCandidate() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

		int databaseSizeBeforeUpdate = candidateRepository.findAll().size();

        // Update the candidate
        candidate.setFullName(UPDATED_FULL_NAME);
        candidate.setEmail(UPDATED_EMAIL);
        candidate.setLinkedin(UPDATED_LINKEDIN);
        candidate.setGithub(UPDATED_GITHUB);
        candidate.setMotivation(UPDATED_MOTIVATION);
        restCandidateMockMvc.perform(put("/api/candidates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(candidate)))
                .andExpect(status().isOk());

        // Validate the Candidate in the database
        List<Candidate> candidates = candidateRepository.findAll();
        assertThat(candidates).hasSize(databaseSizeBeforeUpdate);
        Candidate testCandidate = candidates.get(candidates.size() - 1);
        assertThat(testCandidate.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testCandidate.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCandidate.getLinkedin()).isEqualTo(UPDATED_LINKEDIN);
        assertThat(testCandidate.getGithub()).isEqualTo(UPDATED_GITHUB);
        assertThat(testCandidate.getMotivation()).isEqualTo(UPDATED_MOTIVATION);
    }

    @Test
    @Transactional
    public void deleteCandidate() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

		int databaseSizeBeforeDelete = candidateRepository.findAll().size();

        // Get the candidate
        restCandidateMockMvc.perform(delete("/api/candidates/{id}", candidate.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Candidate> candidates = candidateRepository.findAll();
        assertThat(candidates).hasSize(databaseSizeBeforeDelete - 1);
    }
}
