package com.hiringdefined.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Interview.
 */
@Document(collection = "INTERVIEW")
public class Interview implements Serializable {

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

        Interview interview = (Interview) o;

        if ( ! Objects.equals(id, interview.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Interview{" +
                "id=" + id +
                ", companyName='" + companyName + "'" +
                ", position='" + position + "'" +
                ", seniority='" + seniority + "'" +
                '}';
    }
}
