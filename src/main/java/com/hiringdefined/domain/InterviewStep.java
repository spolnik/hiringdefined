package com.hiringdefined.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A InterviewStep.
 */
@Document(collection = "INTERVIEWSTEP")
public class InterviewStep implements Serializable {

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("description")
    private String description;

    @NotNull
    @Field("stage_nr")
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

        InterviewStep interviewStep = (InterviewStep) o;

        if ( ! Objects.equals(id, interviewStep.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InterviewStep{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", description='" + description + "'" +
                ", stageNr='" + stageNr + "'" +
                '}';
    }
}
