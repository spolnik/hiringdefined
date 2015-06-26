package com.hiringdefined.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Candidate entity.
 */
public class CandidateDTO implements Serializable {

    private String id;

    @NotNull
    private String fullName;

    @NotNull
    private String email;

    private String linkedIn;

    private String github;

    private String motivation;

    private String owner;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }


    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }


    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }


    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CandidateDTO candidateDTO = (CandidateDTO) o;

        if ( ! Objects.equals(id, candidateDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CandidateDTO{" +
                "id=" + id +
                ", fullName='" + fullName + "'" +
                ", email='" + email + "'" +
                ", linkedIn='" + linkedIn + "'" +
                ", github='" + github + "'" +
                ", motivation='" + motivation + "'" +
                ", owner='" + owner + "'" +
                '}';
    }
}
