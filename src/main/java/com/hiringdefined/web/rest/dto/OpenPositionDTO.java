package com.hiringdefined.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the OpenPosition entity.
 */
public class OpenPositionDTO implements Serializable {

    private String id;

    @NotNull
    private String companyName;

    @NotNull
    private String position;

    @NotNull
    private String seniority;

    @NotNull
    private String location;

    @NotNull
    private String description;

    @NotNull
    private String requirements;

    @NotNull
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

        OpenPositionDTO openPositionDTO = (OpenPositionDTO) o;

        if ( ! Objects.equals(id, openPositionDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OpenPositionDTO{" +
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
