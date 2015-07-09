package com.hiringdefined.web.rest;

import com.hiringdefined.Application;
import com.hiringdefined.domain.Interview;
import com.hiringdefined.repository.InterviewRepository;

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
 * Test class for the InterviewResource REST controller.
 *
 * @see InterviewResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InterviewResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_DOMAIN = "SAMPLE_TEXT";
    private static final String UPDATED_DOMAIN = "UPDATED_TEXT";
    private static final String DEFAULT_LEVEL = "SAMPLE_TEXT";
    private static final String UPDATED_LEVEL = "UPDATED_TEXT";

    @Inject
    private InterviewRepository interviewRepository;

    private MockMvc restInterviewMockMvc;

    private Interview interview;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InterviewResource interviewResource = new InterviewResource();
        ReflectionTestUtils.setField(interviewResource, "interviewRepository", interviewRepository);
        this.restInterviewMockMvc = MockMvcBuilders.standaloneSetup(interviewResource).build();
    }

    @Before
    public void initTest() {
        interview = new Interview();
        interview.setName(DEFAULT_NAME);
        interview.setDomain(DEFAULT_DOMAIN);
        interview.setLevel(DEFAULT_LEVEL);
    }

    @Test
    @Transactional
    public void createInterview() throws Exception {
        int databaseSizeBeforeCreate = interviewRepository.findAll().size();

        // Create the Interview
        restInterviewMockMvc.perform(post("/api/interviews")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(interview)))
                .andExpect(status().isCreated());

        // Validate the Interview in the database
        List<Interview> interviews = interviewRepository.findAll();
        assertThat(interviews).hasSize(databaseSizeBeforeCreate + 1);
        Interview testInterview = interviews.get(interviews.size() - 1);
        assertThat(testInterview.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInterview.getDomain()).isEqualTo(DEFAULT_DOMAIN);
        assertThat(testInterview.getLevel()).isEqualTo(DEFAULT_LEVEL);
    }

    @Test
    @Transactional
    public void getAllInterviews() throws Exception {
        // Initialize the database
        interviewRepository.saveAndFlush(interview);

        // Get all the interviews
        restInterviewMockMvc.perform(get("/api/interviews"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(interview.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].domain").value(hasItem(DEFAULT_DOMAIN.toString())))
                .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.toString())));
    }

    @Test
    @Transactional
    public void getInterview() throws Exception {
        // Initialize the database
        interviewRepository.saveAndFlush(interview);

        // Get the interview
        restInterviewMockMvc.perform(get("/api/interviews/{id}", interview.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(interview.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.domain").value(DEFAULT_DOMAIN.toString()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInterview() throws Exception {
        // Get the interview
        restInterviewMockMvc.perform(get("/api/interviews/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInterview() throws Exception {
        // Initialize the database
        interviewRepository.saveAndFlush(interview);

		int databaseSizeBeforeUpdate = interviewRepository.findAll().size();

        // Update the interview
        interview.setName(UPDATED_NAME);
        interview.setDomain(UPDATED_DOMAIN);
        interview.setLevel(UPDATED_LEVEL);
        restInterviewMockMvc.perform(put("/api/interviews")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(interview)))
                .andExpect(status().isOk());

        // Validate the Interview in the database
        List<Interview> interviews = interviewRepository.findAll();
        assertThat(interviews).hasSize(databaseSizeBeforeUpdate);
        Interview testInterview = interviews.get(interviews.size() - 1);
        assertThat(testInterview.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInterview.getDomain()).isEqualTo(UPDATED_DOMAIN);
        assertThat(testInterview.getLevel()).isEqualTo(UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void deleteInterview() throws Exception {
        // Initialize the database
        interviewRepository.saveAndFlush(interview);

		int databaseSizeBeforeDelete = interviewRepository.findAll().size();

        // Get the interview
        restInterviewMockMvc.perform(delete("/api/interviews/{id}", interview.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Interview> interviews = interviewRepository.findAll();
        assertThat(interviews).hasSize(databaseSizeBeforeDelete - 1);
    }
}
