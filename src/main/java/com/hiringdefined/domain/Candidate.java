package com.hiringdefined.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Candidate.
 */
@Document(collection = "CANDIDATE")
public class Candidate implements Serializable {

    @Id
    private String id;

    @NotNull
    @Field("full_name")
    private String fullName;

    @NotNull
    @Field("email")
    private String email;

    @Field("linked_in")
    private String linkedIn;

    @Field("github")
    private String github;

    @Field("motivation")
    private String motivation;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Candidate candidate = (Candidate) o;

        if ( ! Objects.equals(id, candidate.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + id +
                ", fullName='" + fullName + "'" +
                ", email='" + email + "'" +
                ", linkedIn='" + linkedIn + "'" +
                ", github='" + github + "'" +
                ", motivation='" + motivation + "'" +
                '}';
    }
}
