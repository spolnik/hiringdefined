package com.hiringdefined.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Interview entity.
 */
public class InterviewDTO implements Serializable {

    private String id;

    @NotNull
    private String companyName;

    @NotNull
    private String position;

    @NotNull
    private String seniority;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InterviewDTO interviewDTO = (InterviewDTO) o;

        if ( ! Objects.equals(id, interviewDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InterviewDTO{" +
                "id=" + id +
                ", companyName='" + companyName + "'" +
                ", position='" + position + "'" +
                ", seniority='" + seniority + "'" +
                '}';
    }
}
