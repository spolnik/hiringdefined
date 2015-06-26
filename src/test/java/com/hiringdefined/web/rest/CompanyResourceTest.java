package com.hiringdefined.web.rest;

import com.hiringdefined.Application;
import com.hiringdefined.domain.Company;
import com.hiringdefined.repository.CompanyRepository;
import com.hiringdefined.web.rest.mapper.CompanyMapper;

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
 * Test class for the CompanyResource REST controller.
 *
 * @see CompanyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CompanyResourceTest {

    private static final String DEFAULT_COMPANY_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_COMPANY_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_URL = "SAMPLE_TEXT";
    private static final String UPDATED_URL = "UPDATED_TEXT";
    private static final String DEFAULT_CONTACT_PERSON = "SAMPLE_TEXT";
    private static final String UPDATED_CONTACT_PERSON = "UPDATED_TEXT";
    private static final String DEFAULT_CONTACT_EMAIL = "SAMPLE_TEXT";
    private static final String UPDATED_CONTACT_EMAIL = "UPDATED_TEXT";
    private static final String DEFAULT_OWNER = "SAMPLE_TEXT";
    private static final String UPDATED_OWNER = "UPDATED_TEXT";

    @Inject
    private CompanyRepository companyRepository;

    @Inject
    private CompanyMapper companyMapper;

    private MockMvc restCompanyMockMvc;

    private Company company;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CompanyResource companyResource = new CompanyResource();
        ReflectionTestUtils.setField(companyResource, "companyRepository", companyRepository);
        ReflectionTestUtils.setField(companyResource, "companyMapper", companyMapper);
        this.restCompanyMockMvc = MockMvcBuilders.standaloneSetup(companyResource).build();
    }

    @Before
    public void initTest() {
        companyRepository.deleteAll();
        company = new Company();
        company.setCompanyName(DEFAULT_COMPANY_NAME);
        company.setUrl(DEFAULT_URL);
        company.setContactPerson(DEFAULT_CONTACT_PERSON);
        company.setContactEmail(DEFAULT_CONTACT_EMAIL);
        company.setOwner(DEFAULT_OWNER);
    }

    @Test
    public void createCompany() throws Exception {
        int databaseSizeBeforeCreate = companyRepository.findAll().size();

        // Create the Company
        restCompanyMockMvc.perform(post("/api/companys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(company)))
                .andExpect(status().isCreated());

        // Validate the Company in the database
        List<Company> companys = companyRepository.findAll();
        assertThat(companys).hasSize(databaseSizeBeforeCreate + 1);
        Company testCompany = companys.get(companys.size() - 1);
        assertThat(testCompany.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testCompany.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testCompany.getContactPerson()).isEqualTo(DEFAULT_CONTACT_PERSON);
        assertThat(testCompany.getContactEmail()).isEqualTo(DEFAULT_CONTACT_EMAIL);
        assertThat(testCompany.getOwner()).isEqualTo(DEFAULT_OWNER);
    }

    @Test
    public void checkCompanyNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(companyRepository.findAll()).hasSize(0);
        // set the field null
        company.setCompanyName(null);

        // Create the Company, which fails.
        restCompanyMockMvc.perform(post("/api/companys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(company)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Company> companys = companyRepository.findAll();
        assertThat(companys).hasSize(0);
    }

    @Test
    public void checkUrlIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(companyRepository.findAll()).hasSize(0);
        // set the field null
        company.setUrl(null);

        // Create the Company, which fails.
        restCompanyMockMvc.perform(post("/api/companys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(company)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Company> companys = companyRepository.findAll();
        assertThat(companys).hasSize(0);
    }

    @Test
    public void checkContactPersonIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(companyRepository.findAll()).hasSize(0);
        // set the field null
        company.setContactPerson(null);

        // Create the Company, which fails.
        restCompanyMockMvc.perform(post("/api/companys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(company)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Company> companys = companyRepository.findAll();
        assertThat(companys).hasSize(0);
    }

    @Test
    public void checkContactEmailIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(companyRepository.findAll()).hasSize(0);
        // set the field null
        company.setContactEmail(null);

        // Create the Company, which fails.
        restCompanyMockMvc.perform(post("/api/companys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(company)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Company> companys = companyRepository.findAll();
        assertThat(companys).hasSize(0);
    }

    @Test
    public void checkOwnerIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(companyRepository.findAll()).hasSize(0);
        // set the field null
        company.setOwner(null);

        // Create the Company, which fails.
        restCompanyMockMvc.perform(post("/api/companys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(company)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Company> companys = companyRepository.findAll();
        assertThat(companys).hasSize(0);
    }

    @Test
    public void getAllCompanys() throws Exception {
        // Initialize the database
        companyRepository.save(company);

        // Get all the companys
        restCompanyMockMvc.perform(get("/api/companys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(company.getId())))
                .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME.toString())))
                .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
                .andExpect(jsonPath("$.[*].contactPerson").value(hasItem(DEFAULT_CONTACT_PERSON.toString())))
                .andExpect(jsonPath("$.[*].contactEmail").value(hasItem(DEFAULT_CONTACT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER.toString())));
    }

    @Test
    public void getCompany() throws Exception {
        // Initialize the database
        companyRepository.save(company);

        // Get the company
        restCompanyMockMvc.perform(get("/api/companys/{id}", company.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(company.getId()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.contactPerson").value(DEFAULT_CONTACT_PERSON.toString()))
            .andExpect(jsonPath("$.contactEmail").value(DEFAULT_CONTACT_EMAIL.toString()))
            .andExpect(jsonPath("$.owner").value(DEFAULT_OWNER.toString()));
    }

    @Test
    public void getNonExistingCompany() throws Exception {
        // Get the company
        restCompanyMockMvc.perform(get("/api/companys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateCompany() throws Exception {
        // Initialize the database
        companyRepository.save(company);

		int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Update the company
        company.setCompanyName(UPDATED_COMPANY_NAME);
        company.setUrl(UPDATED_URL);
        company.setContactPerson(UPDATED_CONTACT_PERSON);
        company.setContactEmail(UPDATED_CONTACT_EMAIL);
        company.setOwner(UPDATED_OWNER);
        restCompanyMockMvc.perform(put("/api/companys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(company)))
                .andExpect(status().isOk());

        // Validate the Company in the database
        List<Company> companys = companyRepository.findAll();
        assertThat(companys).hasSize(databaseSizeBeforeUpdate);
        Company testCompany = companys.get(companys.size() - 1);
        assertThat(testCompany.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testCompany.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testCompany.getContactPerson()).isEqualTo(UPDATED_CONTACT_PERSON);
        assertThat(testCompany.getContactEmail()).isEqualTo(UPDATED_CONTACT_EMAIL);
        assertThat(testCompany.getOwner()).isEqualTo(UPDATED_OWNER);
    }

    @Test
    public void deleteCompany() throws Exception {
        // Initialize the database
        companyRepository.save(company);

		int databaseSizeBeforeDelete = companyRepository.findAll().size();

        // Get the company
        restCompanyMockMvc.perform(delete("/api/companys/{id}", company.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Company> companys = companyRepository.findAll();
        assertThat(companys).hasSize(databaseSizeBeforeDelete - 1);
    }
}