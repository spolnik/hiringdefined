package com.hiringdefined.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the InterviewStep entity.
 */
public class InterviewStepDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private String stageNr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getStageNr() {
        return stageNr;
    }

    public void setStageNr(String stageNr) {
        this.stageNr = stageNr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InterviewStepDTO interviewStepDTO = (InterviewStepDTO) o;

        if ( ! Objects.equals(id, interviewStepDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InterviewStepDTO{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", description='" + description + "'" +
                ", stageNr='" + stageNr + "'" +
                '}';
    }
}
