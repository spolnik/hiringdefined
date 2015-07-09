package com.hiringdefined.web.rest;

import com.hiringdefined.Application;
import com.hiringdefined.domain.OpenPosition;
import com.hiringdefined.repository.OpenPositionRepository;

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
 * Test class for the OpenPositionResource REST controller.
 *
 * @see OpenPositionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OpenPositionResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_DOMAIN = "SAMPLE_TEXT";
    private static final String UPDATED_DOMAIN = "UPDATED_TEXT";
    private static final String DEFAULT_LEVEL = "SAMPLE_TEXT";
    private static final String UPDATED_LEVEL = "UPDATED_TEXT";
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

    private MockMvc restOpenPositionMockMvc;

    private OpenPosition openPosition;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OpenPositionResource openPositionResource = new OpenPositionResource();
        ReflectionTestUtils.setField(openPositionResource, "openPositionRepository", openPositionRepository);
        this.restOpenPositionMockMvc = MockMvcBuilders.standaloneSetup(openPositionResource).build();
    }

    @Before
    public void initTest() {
        openPosition = new OpenPosition();
        openPosition.setName(DEFAULT_NAME);
        openPosition.setDomain(DEFAULT_DOMAIN);
        openPosition.setLevel(DEFAULT_LEVEL);
        openPosition.setLocation(DEFAULT_LOCATION);
        openPosition.setDescription(DEFAULT_DESCRIPTION);
        openPosition.setRequirements(DEFAULT_REQUIREMENTS);
        openPosition.setState(DEFAULT_STATE);
    }

    @Test
    @Transactional
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
        assertThat(testOpenPosition.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOpenPosition.getDomain()).isEqualTo(DEFAULT_DOMAIN);
        assertThat(testOpenPosition.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testOpenPosition.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testOpenPosition.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOpenPosition.getRequirements()).isEqualTo(DEFAULT_REQUIREMENTS);
        assertThat(testOpenPosition.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    public void getAllOpenPositions() throws Exception {
        // Initialize the database
        openPositionRepository.saveAndFlush(openPosition);

        // Get all the openPositions
        restOpenPositionMockMvc.perform(get("/api/openPositions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(openPosition.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].domain").value(hasItem(DEFAULT_DOMAIN.toString())))
                .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.toString())))
                .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].requirements").value(hasItem(DEFAULT_REQUIREMENTS.toString())))
                .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())));
    }

    @Test
    @Transactional
    public void getOpenPosition() throws Exception {
        // Initialize the database
        openPositionRepository.saveAndFlush(openPosition);

        // Get the openPosition
        restOpenPositionMockMvc.perform(get("/api/openPositions/{id}", openPosition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(openPosition.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.domain").value(DEFAULT_DOMAIN.toString()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.requirements").value(DEFAULT_REQUIREMENTS.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOpenPosition() throws Exception {
        // Get the openPosition
        restOpenPositionMockMvc.perform(get("/api/openPositions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOpenPosition() throws Exception {
        // Initialize the database
        openPositionRepository.saveAndFlush(openPosition);

		int databaseSizeBeforeUpdate = openPositionRepository.findAll().size();

        // Update the openPosition
        openPosition.setName(UPDATED_NAME);
        openPosition.setDomain(UPDATED_DOMAIN);
        openPosition.setLevel(UPDATED_LEVEL);
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
        assertThat(testOpenPosition.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOpenPosition.getDomain()).isEqualTo(UPDATED_DOMAIN);
        assertThat(testOpenPosition.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testOpenPosition.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testOpenPosition.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOpenPosition.getRequirements()).isEqualTo(UPDATED_REQUIREMENTS);
        assertThat(testOpenPosition.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    public void deleteOpenPosition() throws Exception {
        // Initialize the database
        openPositionRepository.saveAndFlush(openPosition);

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
