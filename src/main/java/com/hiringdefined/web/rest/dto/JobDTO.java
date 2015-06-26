package com.hiringdefined.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Job entity.
 */
public class JobDTO implements Serializable {

    private String id;

    @NotNull
    private String companyName;

    @NotNull
    private String jobTitle;

    @NotNull
    private String jobCategory;

    @NotNull
    private String location;

    @NotNull
    private String description;

    @NotNull
    private String requirements;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }


    public String getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(String jobCategory) {
        this.jobCategory = jobCategory;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JobDTO jobDTO = (JobDTO) o;

        if ( ! Objects.equals(id, jobDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "JobDTO{" +
                "id=" + id +
                ", companyName='" + companyName + "'" +
                ", jobTitle='" + jobTitle + "'" +
                ", jobCategory='" + jobCategory + "'" +
                ", location='" + location + "'" +
                ", description='" + description + "'" +
                ", requirements='" + requirements + "'" +
                '}';
    }
}
