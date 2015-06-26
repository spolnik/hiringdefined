package com.hiringdefined.web.rest;

import com.hiringdefined.Application;
import com.hiringdefined.domain.Interview;
import com.hiringdefined.repository.InterviewRepository;
import com.hiringdefined.web.rest.mapper.InterviewMapper;

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

    private static final String DEFAULT_COMPANY_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_COMPANY_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_POSITION = "SAMPLE_TEXT";
    private static final String UPDATED_POSITION = "UPDATED_TEXT";
    private static final String DEFAULT_SENIORITY = "SAMPLE_TEXT";
    private static final String UPDATED_SENIORITY = "UPDATED_TEXT";

    @Inject
    private InterviewRepository interviewRepository;

    @Inject
    private InterviewMapper interviewMapper;

    private MockMvc restInterviewMockMvc;

    private Interview interview;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InterviewResource interviewResource = new InterviewResource();
        ReflectionTestUtils.setField(interviewResource, "interviewRepository", interviewRepository);
        ReflectionTestUtils.setField(interviewResource, "interviewMapper", interviewMapper);
        this.restInterviewMockMvc = MockMvcBuilders.standaloneSetup(interviewResource).build();
    }

    @Before
    public void initTest() {
        interviewRepository.deleteAll();
        interview = new Interview();
        interview.setCompanyName(DEFAULT_COMPANY_NAME);
        interview.setPosition(DEFAULT_POSITION);
        interview.setSeniority(DEFAULT_SENIORITY);
    }

    @Test
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
        assertThat(testInterview.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testInterview.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testInterview.getSeniority()).isEqualTo(DEFAULT_SENIORITY);
    }

    @Test
    public void checkCompanyNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(interviewRepository.findAll()).hasSize(0);
        // set the field null
        interview.setCompanyName(null);

        // Create the Interview, which fails.
        restInterviewMockMvc.perform(post("/api/interviews")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(interview)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Interview> interviews = interviewRepository.findAll();
        assertThat(interviews).hasSize(0);
    }

    @Test
    public void checkPositionIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(interviewRepository.findAll()).hasSize(0);
        // set the field null
        interview.setPosition(null);

        // Create the Interview, which fails.
        restInterviewMockMvc.perform(post("/api/interviews")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(interview)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Interview> interviews = interviewRepository.findAll();
        assertThat(interviews).hasSize(0);
    }

    @Test
    public void checkSeniorityIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(interviewRepository.findAll()).hasSize(0);
        // set the field null
        interview.setSeniority(null);

        // Create the Interview, which fails.
        restInterviewMockMvc.perform(post("/api/interviews")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(interview)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Interview> interviews = interviewRepository.findAll();
        assertThat(interviews).hasSize(0);
    }

    @Test
    public void getAllInterviews() throws Exception {
        // Initialize the database
        interviewRepository.save(interview);

        // Get all the interviews
        restInterviewMockMvc.perform(get("/api/interviews"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(interview.getId())))
                .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
                .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.toString())))
                .andExpect(jsonPath("$.[*].seniority").value(hasItem(DEFAULT_SENIORITY.toString())));
    }

    @Test
    public void getInterview() throws Exception {
        // Initialize the database
        interviewRepository.save(interview);

        // Get the interview
        restInterviewMockMvc.perform(get("/api/interviews/{id}", interview.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(interview.getId()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION.toString()))
            .andExpect(jsonPath("$.seniority").value(DEFAULT_SENIORITY.toString()));
    }

    @Test
    public void getNonExistingInterview() throws Exception {
        // Get the interview
        restInterviewMockMvc.perform(get("/api/interviews/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateInterview() throws Exception {
        // Initialize the database
        interviewRepository.save(interview);

		int databaseSizeBeforeUpdate = interviewRepository.findAll().size();

        // Update the interview
        interview.setCompanyName(UPDATED_COMPANY_NAME);
        interview.setPosition(UPDATED_POSITION);
        interview.setSeniority(UPDATED_SENIORITY);
        restInterviewMockMvc.perform(put("/api/interviews")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(interview)))
                .andExpect(status().isOk());

        // Validate the Interview in the database
        List<Interview> interviews = interviewRepository.findAll();
        assertThat(interviews).hasSize(databaseSizeBeforeUpdate);
        Interview testInterview = interviews.get(interviews.size() - 1);
        assertThat(testInterview.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testInterview.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testInterview.getSeniority()).isEqualTo(UPDATED_SENIORITY);
    }

    @Test
    public void deleteInterview() throws Exception {
        // Initialize the database
        interviewRepository.save(interview);

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
