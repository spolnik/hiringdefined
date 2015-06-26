package com.hiringdefined.web.rest;

import com.hiringdefined.Application;
import com.hiringdefined.domain.InterviewStep;
import com.hiringdefined.repository.InterviewStepRepository;
import com.hiringdefined.web.rest.mapper.InterviewStepMapper;

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
 * Test class for the InterviewStepResource REST controller.
 *
 * @see InterviewStepResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InterviewStepResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";
    private static final String DEFAULT_STAGE_NR = "SAMPLE_TEXT";
    private static final String UPDATED_STAGE_NR = "UPDATED_TEXT";

    @Inject
    private InterviewStepRepository interviewStepRepository;

    @Inject
    private InterviewStepMapper interviewStepMapper;

    private MockMvc restInterviewStepMockMvc;

    private InterviewStep interviewStep;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InterviewStepResource interviewStepResource = new InterviewStepResource();
        ReflectionTestUtils.setField(interviewStepResource, "interviewStepRepository", interviewStepRepository);
        ReflectionTestUtils.setField(interviewStepResource, "interviewStepMapper", interviewStepMapper);
        this.restInterviewStepMockMvc = MockMvcBuilders.standaloneSetup(interviewStepResource).build();
    }

    @Before
    public void initTest() {
        interviewStepRepository.deleteAll();
        interviewStep = new InterviewStep();
        interviewStep.setName(DEFAULT_NAME);
        interviewStep.setDescription(DEFAULT_DESCRIPTION);
        interviewStep.setStageNr(DEFAULT_STAGE_NR);
    }

    @Test
    public void createInterviewStep() throws Exception {
        int databaseSizeBeforeCreate = interviewStepRepository.findAll().size();

        // Create the InterviewStep
        restInterviewStepMockMvc.perform(post("/api/interviewSteps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(interviewStep)))
                .andExpect(status().isCreated());

        // Validate the InterviewStep in the database
        List<InterviewStep> interviewSteps = interviewStepRepository.findAll();
        assertThat(interviewSteps).hasSize(databaseSizeBeforeCreate + 1);
        InterviewStep testInterviewStep = interviewSteps.get(interviewSteps.size() - 1);
        assertThat(testInterviewStep.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInterviewStep.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testInterviewStep.getStageNr()).isEqualTo(DEFAULT_STAGE_NR);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(interviewStepRepository.findAll()).hasSize(0);
        // set the field null
        interviewStep.setName(null);

        // Create the InterviewStep, which fails.
        restInterviewStepMockMvc.perform(post("/api/interviewSteps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(interviewStep)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<InterviewStep> interviewSteps = interviewStepRepository.findAll();
        assertThat(interviewSteps).hasSize(0);
    }

    @Test
    public void checkDescriptionIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(interviewStepRepository.findAll()).hasSize(0);
        // set the field null
        interviewStep.setDescription(null);

        // Create the InterviewStep, which fails.
        restInterviewStepMockMvc.perform(post("/api/interviewSteps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(interviewStep)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<InterviewStep> interviewSteps = interviewStepRepository.findAll();
        assertThat(interviewSteps).hasSize(0);
    }

    @Test
    public void checkStageNrIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(interviewStepRepository.findAll()).hasSize(0);
        // set the field null
        interviewStep.setStageNr(null);

        // Create the InterviewStep, which fails.
        restInterviewStepMockMvc.perform(post("/api/interviewSteps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(interviewStep)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<InterviewStep> interviewSteps = interviewStepRepository.findAll();
        assertThat(interviewSteps).hasSize(0);
    }

    @Test
    public void getAllInterviewSteps() throws Exception {
        // Initialize the database
        interviewStepRepository.save(interviewStep);

        // Get all the interviewSteps
        restInterviewStepMockMvc.perform(get("/api/interviewSteps"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(interviewStep.getId())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].stageNr").value(hasItem(DEFAULT_STAGE_NR.toString())));
    }

    @Test
    public void getInterviewStep() throws Exception {
        // Initialize the database
        interviewStepRepository.save(interviewStep);

        // Get the interviewStep
        restInterviewStepMockMvc.perform(get("/api/interviewSteps/{id}", interviewStep.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(interviewStep.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.stageNr").value(DEFAULT_STAGE_NR.toString()));
    }

    @Test
    public void getNonExistingInterviewStep() throws Exception {
        // Get the interviewStep
        restInterviewStepMockMvc.perform(get("/api/interviewSteps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateInterviewStep() throws Exception {
        // Initialize the database
        interviewStepRepository.save(interviewStep);

		int databaseSizeBeforeUpdate = interviewStepRepository.findAll().size();

        // Update the interviewStep
        interviewStep.setName(UPDATED_NAME);
        interviewStep.setDescription(UPDATED_DESCRIPTION);
        interviewStep.setStageNr(UPDATED_STAGE_NR);
        restInterviewStepMockMvc.perform(put("/api/interviewSteps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(interviewStep)))
                .andExpect(status().isOk());

        // Validate the InterviewStep in the database
        List<InterviewStep> interviewSteps = interviewStepRepository.findAll();
        assertThat(interviewSteps).hasSize(databaseSizeBeforeUpdate);
        InterviewStep testInterviewStep = interviewSteps.get(interviewSteps.size() - 1);
        assertThat(testInterviewStep.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInterviewStep.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testInterviewStep.getStageNr()).isEqualTo(UPDATED_STAGE_NR);
    }

    @Test
    public void deleteInterviewStep() throws Exception {
        // Initialize the database
        interviewStepRepository.save(interviewStep);

		int databaseSizeBeforeDelete = interviewStepRepository.findAll().size();

        // Get the interviewStep
        restInterviewStepMockMvc.perform(delete("/api/interviewSteps/{id}", interviewStep.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<InterviewStep> interviewSteps = interviewStepRepository.findAll();
        assertThat(interviewSteps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
