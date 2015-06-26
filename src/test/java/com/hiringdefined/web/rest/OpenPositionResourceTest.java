package com.hiringdefined.web.rest;

import com.hiringdefined.Application;
import com.hiringdefined.domain.OpenPosition;
import com.hiringdefined.repository.OpenPositionRepository;
import com.hiringdefined.web.rest.mapper.OpenPositionMapper;

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
 * Test class for the OpenPositionResource REST controller.
 *
 * @see OpenPositionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OpenPositionResourceTest {

    private static final String DEFAULT_COMPANY_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_COMPANY_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_POSITION = "SAMPLE_TEXT";
    private static final String UPDATED_POSITION = "UPDATED_TEXT";
    private static final String DEFAULT_SENIORITY = "SAMPLE_TEXT";
    private static final String UPDATED_SENIORITY = "UPDATED_TEXT";
    private static final String DEFAULT_LOCATION = "SAMPLE_TEXT";
    private static final String UPDATED_LOCATION = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";
    private static final String DEFAULT_REQUIREMENTS = "SAMPLE_TEXT";
    private static final String UPDATED_REQUIREMENTS = "UPDATED_TEXT";
    private static final String DEFAULT_STATE = "SAMPLE_TEXT";
    private static final String UPDATED_STATE = "UPDATED_TEXT";

    @Inject
    private OpenPositionRepository openPositionRepository;

    @Inject
    private OpenPositionMapper openPositionMapper;

    private MockMvc restOpenPositionMockMvc;

    private OpenPosition openPosition;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OpenPositionResource openPositionResource = new OpenPositionResource();
        ReflectionTestUtils.setField(openPositionResource, "openPositionRepository", openPositionRepository);
        ReflectionTestUtils.setField(openPositionResource, "openPositionMapper", openPositionMapper);
        this.restOpenPositionMockMvc = MockMvcBuilders.standaloneSetup(openPositionResource).build();
    }

    @Before
    public void initTest() {
        openPositionRepository.deleteAll();
        openPosition = new OpenPosition();
        openPosition.setCompanyName(DEFAULT_COMPANY_NAME);
        openPosition.setPosition(DEFAULT_POSITION);
        openPosition.setSeniority(DEFAULT_SENIORITY);
        openPosition.setLocation(DEFAULT_LOCATION);
        openPosition.setDescription(DEFAULT_DESCRIPTION);
        openPosition.setRequirements(DEFAULT_REQUIREMENTS);
        openPosition.setState(DEFAULT_STATE);
    }

    @Test
    public void createOpenPosition() throws Exception {
        int databaseSizeBeforeCreate = openPositionRepository.findAll().size();

        // Create the OpenPosition
        restOpenPositionMockMvc.perform(post("/api/openPositions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(openPosition)))
                .andExpect(status().isCreated());

        // Validate the OpenPosition in the database
        List<OpenPosition> openPositions = openPositionRepository.findAll();
        assertThat(openPositions).hasSize(databaseSizeBeforeCreate + 1);
        OpenPosition testOpenPosition = openPositions.get(openPositions.size() - 1);
        assertThat(testOpenPosition.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testOpenPosition.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testOpenPosition.getSeniority()).isEqualTo(DEFAULT_SENIORITY);
        assertThat(testOpenPosition.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testOpenPosition.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOpenPosition.getRequirements()).isEqualTo(DEFAULT_REQUIREMENTS);
        assertThat(testOpenPosition.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    public void checkCompanyNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(openPositionRepository.findAll()).hasSize(0);
        // set the field null
        openPosition.setCompanyName(null);

        // Create the OpenPosition, which fails.
        restOpenPositionMockMvc.perform(post("/api/openPositions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(openPosition)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<OpenPosition> openPositions = openPositionRepository.findAll();
        assertThat(openPositions).hasSize(0);
    }

    @Test
    public void checkPositionIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(openPositionRepository.findAll()).hasSize(0);
        // set the field null
        openPosition.setPosition(null);

        // Create the OpenPosition, which fails.
        restOpenPositionMockMvc.perform(post("/api/openPositions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(openPosition)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<OpenPosition> openPositions = openPositionRepository.findAll();
        assertThat(openPositions).hasSize(0);
    }

    @Test
    public void checkSeniorityIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(openPositionRepository.findAll()).hasSize(0);
        // set the field null
        openPosition.setSeniority(null);

        // Create the OpenPosition, which fails.
        restOpenPositionMockMvc.perform(post("/api/openPositions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(openPosition)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<OpenPosition> openPositions = openPositionRepository.findAll();
        assertThat(openPositions).hasSize(0);
    }

    @Test
    public void checkLocationIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(openPositionRepository.findAll()).hasSize(0);
        // set the field null
        openPosition.setLocation(null);

        // Create the OpenPosition, which fails.
        restOpenPositionMockMvc.perform(post("/api/openPositions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(openPosition)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<OpenPosition> openPositions = openPositionRepository.findAll();
        assertThat(openPositions).hasSize(0);
    }

    @Test
    public void checkDescriptionIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(openPositionRepository.findAll()).hasSize(0);
        // set the field null
        openPosition.setDescription(null);

        // Create the OpenPosition, which fails.
        restOpenPositionMockMvc.perform(post("/api/openPositions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(openPosition)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<OpenPosition> openPositions = openPositionRepository.findAll();
        assertThat(openPositions).hasSize(0);
    }

    @Test
    public void checkRequirementsIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(openPositionRepository.findAll()).hasSize(0);
        // set the field null
        openPosition.setRequirements(null);

        // Create the OpenPosition, which fails.
        restOpenPositionMockMvc.perform(post("/api/openPositions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(openPosition)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<OpenPosition> openPositions = openPositionRepository.findAll();
        assertThat(openPositions).hasSize(0);
    }

    @Test
    public void checkStateIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(openPositionRepository.findAll()).hasSize(0);
        // set the field null
        openPosition.setState(null);

        // Create the OpenPosition, which fails.
        restOpenPositionMockMvc.perform(post("/api/openPositions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(openPosition)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<OpenPosition> openPositions = openPositionRepository.findAll();
        assertThat(openPositions).hasSize(0);
    }

    @Test
    public void getAllOpenPositions() throws Exception {
        // Initialize the database
        openPositionRepository.save(openPosition);

        // Get all the openPositions
        restOpenPositionMockMvc.perform(get("/api/openPositions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(openPosition.getId())))
                .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
                .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.toString())))
                .andExpect(jsonPath("$.[*].seniority").value(hasItem(DEFAULT_SENIORITY.toString())))
                .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].requirements").value(hasItem(DEFAULT_REQUIREMENTS.toString())))
                .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())));
    }

    @Test
    public void getOpenPosition() throws Exception {
        // Initialize the database
        openPositionRepository.save(openPosition);

        // Get the openPosition
        restOpenPositionMockMvc.perform(get("/api/openPositions/{id}", openPosition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(openPosition.getId()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION.toString()))
            .andExpect(jsonPath("$.seniority").value(DEFAULT_SENIORITY.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.requirements").value(DEFAULT_REQUIREMENTS.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()));
    }

    @Test
    public void getNonExistingOpenPosition() throws Exception {
        // Get the openPosition
        restOpenPositionMockMvc.perform(get("/api/openPositions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateOpenPosition() throws Exception {
        // Initialize the database
        openPositionRepository.save(openPosition);

		int databaseSizeBeforeUpdate = openPositionRepository.findAll().size();

        // Update the openPosition
        openPosition.setCompanyName(UPDATED_COMPANY_NAME);
        openPosition.setPosition(UPDATED_POSITION);
        openPosition.setSeniority(UPDATED_SENIORITY);
        openPosition.setLocation(UPDATED_LOCATION);
        openPosition.setDescription(UPDATED_DESCRIPTION);
        openPosition.setRequirements(UPDATED_REQUIREMENTS);
        openPosition.setState(UPDATED_STATE);
        restOpenPositionMockMvc.perform(put("/api/openPositions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(openPosition)))
                .andExpect(status().isOk());

        // Validate the OpenPosition in the database
        List<OpenPosition> openPositions = openPositionRepository.findAll();
        assertThat(openPositions).hasSize(databaseSizeBeforeUpdate);
        OpenPosition testOpenPosition = openPositions.get(openPositions.size() - 1);
        assertThat(testOpenPosition.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testOpenPosition.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testOpenPosition.getSeniority()).isEqualTo(UPDATED_SENIORITY);
        assertThat(testOpenPosition.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testOpenPosition.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOpenPosition.getRequirements()).isEqualTo(UPDATED_REQUIREMENTS);
        assertThat(testOpenPosition.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    public void deleteOpenPosition() throws Exception {
        // Initialize the database
        openPositionRepository.save(openPosition);

		int databaseSizeBeforeDelete = openPositionRepository.findAll().size();

        // Get the openPosition
        restOpenPositionMockMvc.perform(delete("/api/openPositions/{id}", openPosition.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<OpenPosition> openPositions = openPositionRepository.findAll();
        assertThat(openPositions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
