package com.hiringdefined.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Company entity.
 */
public class CompanyDTO implements Serializable {

    private String id;

    @NotNull
    private String companyName;

    @NotNull
    private String url;

    @NotNull
    private String contactPerson;

    @NotNull
    private String contactEmail;

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


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }


    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompanyDTO companyDTO = (CompanyDTO) o;

        if ( ! Objects.equals(id, companyDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CompanyDTO{" +
                "id=" + id +
                ", companyName='" + companyName + "'" +
                ", url='" + url + "'" +
                ", contactPerson='" + contactPerson + "'" +
                ", contactEmail='" + contactEmail + "'" +
                '}';
    }
}
