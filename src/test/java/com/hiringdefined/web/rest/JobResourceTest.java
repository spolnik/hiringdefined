package com.hiringdefined.web.rest;

import com.hiringdefined.Application;
import com.hiringdefined.domain.Job;
import com.hiringdefined.repository.JobRepository;
import com.hiringdefined.web.rest.mapper.JobMapper;

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
 * Test class for the JobResource REST controller.
 *
 * @see JobResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class JobResourceTest {

    private static final String DEFAULT_COMPANY_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_COMPANY_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_JOB_TITLE = "SAMPLE_TEXT";
    private static final String UPDATED_JOB_TITLE = "UPDATED_TEXT";
    private static final String DEFAULT_JOB_CATEGORY = "SAMPLE_TEXT";
    private static final String UPDATED_JOB_CATEGORY = "UPDATED_TEXT";
    private static final String DEFAULT_LOCATION = "SAMPLE_TEXT";
    private static final String UPDATED_LOCATION = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";
    private static final String DEFAULT_REQUIREMENTS = "SAMPLE_TEXT";
    private static final String UPDATED_REQUIREMENTS = "UPDATED_TEXT";

    @Inject
    private JobRepository jobRepository;

    @Inject
    private JobMapper jobMapper;

    private MockMvc restJobMockMvc;

    private Job job;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JobResource jobResource = new JobResource();
        ReflectionTestUtils.setField(jobResource, "jobRepository", jobRepository);
        ReflectionTestUtils.setField(jobResource, "jobMapper", jobMapper);
        this.restJobMockMvc = MockMvcBuilders.standaloneSetup(jobResource).build();
    }

    @Before
    public void initTest() {
        jobRepository.deleteAll();
        job = new Job();
        job.setCompanyName(DEFAULT_COMPANY_NAME);
        job.setJobTitle(DEFAULT_JOB_TITLE);
        job.setJobCategory(DEFAULT_JOB_CATEGORY);
        job.setLocation(DEFAULT_LOCATION);
        job.setDescription(DEFAULT_DESCRIPTION);
        job.setRequirements(DEFAULT_REQUIREMENTS);
    }

    @Test
    public void createJob() throws Exception {
        int databaseSizeBeforeCreate = jobRepository.findAll().size();

        // Create the Job
        restJobMockMvc.perform(post("/api/jobs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(job)))
                .andExpect(status().isCreated());

        // Validate the Job in the database
        List<Job> jobs = jobRepository.findAll();
        assertThat(jobs).hasSize(databaseSizeBeforeCreate + 1);
        Job testJob = jobs.get(jobs.size() - 1);
        assertThat(testJob.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testJob.getJobTitle()).isEqualTo(DEFAULT_JOB_TITLE);
        assertThat(testJob.getJobCategory()).isEqualTo(DEFAULT_JOB_CATEGORY);
        assertThat(testJob.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testJob.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testJob.getRequirements()).isEqualTo(DEFAULT_REQUIREMENTS);
    }

    @Test
    public void checkCompanyNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(jobRepository.findAll()).hasSize(0);
        // set the field null
        job.setCompanyName(null);

        // Create the Job, which fails.
        restJobMockMvc.perform(post("/api/jobs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(job)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Job> jobs = jobRepository.findAll();
        assertThat(jobs).hasSize(0);
    }

    @Test
    public void checkJobTitleIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(jobRepository.findAll()).hasSize(0);
        // set the field null
        job.setJobTitle(null);

        // Create the Job, which fails.
        restJobMockMvc.perform(post("/api/jobs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(job)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Job> jobs = jobRepository.findAll();
        assertThat(jobs).hasSize(0);
    }

    @Test
    public void checkJobCategoryIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(jobRepository.findAll()).hasSize(0);
        // set the field null
        job.setJobCategory(null);

        // Create the Job, which fails.
        restJobMockMvc.perform(post("/api/jobs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(job)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Job> jobs = jobRepository.findAll();
        assertThat(jobs).hasSize(0);
    }

    @Test
    public void checkLocationIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(jobRepository.findAll()).hasSize(0);
        // set the field null
        job.setLocation(null);

        // Create the Job, which fails.
        restJobMockMvc.perform(post("/api/jobs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(job)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Job> jobs = jobRepository.findAll();
        assertThat(jobs).hasSize(0);
    }

    @Test
    public void checkDescriptionIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(jobRepository.findAll()).hasSize(0);
        // set the field null
        job.setDescription(null);

        // Create the Job, which fails.
        restJobMockMvc.perform(post("/api/jobs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(job)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Job> jobs = jobRepository.findAll();
        assertThat(jobs).hasSize(0);
    }

    @Test
    public void checkRequirementsIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(jobRepository.findAll()).hasSize(0);
        // set the field null
        job.setRequirements(null);

        // Create the Job, which fails.
        restJobMockMvc.perform(post("/api/jobs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(job)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Job> jobs = jobRepository.findAll();
        assertThat(jobs).hasSize(0);
    }

    @Test
    public void getAllJobs() throws Exception {
        // Initialize the database
        jobRepository.save(job);

        // Get all the jobs
        restJobMockMvc.perform(get("/api/jobs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(job.getId())))
                .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
                .andExpect(jsonPath("$.[*].jobTitle").value(hasItem(DEFAULT_JOB_TITLE.toString())))
                .andExpect(jsonPath("$.[*].jobCategory").value(hasItem(DEFAULT_JOB_CATEGORY.toString())))
                .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].requirements").value(hasItem(DEFAULT_REQUIREMENTS.toString())));
    }

    @Test
    public void getJob() throws Exception {
        // Initialize the database
        jobRepository.save(job);

        // Get the job
        restJobMockMvc.perform(get("/api/jobs/{id}", job.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(job.getId()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.jobTitle").value(DEFAULT_JOB_TITLE.toString()))
            .andExpect(jsonPath("$.jobCategory").value(DEFAULT_JOB_CATEGORY.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.requirements").value(DEFAULT_REQUIREMENTS.toString()));
    }

    @Test
    public void getNonExistingJob() throws Exception {
        // Get the job
        restJobMockMvc.perform(get("/api/jobs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateJob() throws Exception {
        // Initialize the database
        jobRepository.save(job);

		int databaseSizeBeforeUpdate = jobRepository.findAll().size();

        // Update the job
        job.setCompanyName(UPDATED_COMPANY_NAME);
        job.setJobTitle(UPDATED_JOB_TITLE);
        job.setJobCategory(UPDATED_JOB_CATEGORY);
        job.setLocation(UPDATED_LOCATION);
        job.setDescription(UPDATED_DESCRIPTION);
        job.setRequirements(UPDATED_REQUIREMENTS);
        restJobMockMvc.perform(put("/api/jobs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(job)))
                .andExpect(status().isOk());

        // Validate the Job in the database
        List<Job> jobs = jobRepository.findAll();
        assertThat(jobs).hasSize(databaseSizeBeforeUpdate);
        Job testJob = jobs.get(jobs.size() - 1);
        assertThat(testJob.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testJob.getJobTitle()).isEqualTo(UPDATED_JOB_TITLE);
        assertThat(testJob.getJobCategory()).isEqualTo(UPDATED_JOB_CATEGORY);
        assertThat(testJob.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testJob.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testJob.getRequirements()).isEqualTo(UPDATED_REQUIREMENTS);
    }

    @Test
    public void deleteJob() throws Exception {
        // Initialize the database
        jobRepository.save(job);

		int databaseSizeBeforeDelete = jobRepository.findAll().size();

        // Get the job
        restJobMockMvc.perform(delete("/api/jobs/{id}", job.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Job> jobs = jobRepository.findAll();
        assertThat(jobs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
