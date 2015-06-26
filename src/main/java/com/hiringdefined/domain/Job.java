package com.hiringdefined.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Job.
 */
@Document(collection = "JOB")
public class Job implements Serializable {

    @Id
    private String id;

    @NotNull
    @Field("company_name")
    private String companyName;

    @NotNull
    @Field("job_title")
    private String jobTitle;

    @NotNull
    @Field("job_category")
    private String jobCategory;

    @NotNull
    @Field("location")
    private String location;

    @NotNull
    @Field("description")
    private String description;

    @NotNull
    @Field("requirements")
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

        Job job = (Job) o;

        if ( ! Objects.equals(id, job.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Job{" +
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
