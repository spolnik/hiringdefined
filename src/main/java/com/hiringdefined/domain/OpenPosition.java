package com.hiringdefined.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A OpenPosition.
 */
@Document(collection = "OPENPOSITION")
public class OpenPosition implements Serializable {

    @Id
    private String id;

    @NotNull
    @Field("company_name")
    private String companyName;

    @NotNull
    @Field("position")
    private String position;

    @NotNull
    @Field("seniority")
    private String seniority;

    @NotNull
    @Field("location")
    private String location;

    @NotNull
    @Field("description")
    private String description;

    @NotNull
    @Field("requirements")
    private String requirements;

    @NotNull
    @Field("state")
    private String state;

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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSeniority() {
        return seniority;
    }

    public void setSeniority(String seniority) {
        this.seniority = seniority;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OpenPosition openPosition = (OpenPosition) o;

        if ( ! Objects.equals(id, openPosition.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OpenPosition{" +
                "id=" + id +
                ", companyName='" + companyName + "'" +
                ", position='" + position + "'" +
                ", seniority='" + seniority + "'" +
                ", location='" + location + "'" +
                ", description='" + description + "'" +
                ", requirements='" + requirements + "'" +
                ", state='" + state + "'" +
                '}';
    }
}
