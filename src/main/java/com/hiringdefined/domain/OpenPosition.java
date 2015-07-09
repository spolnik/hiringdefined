package com.hiringdefined.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A OpenPosition.
 */
@Entity
@Table(name = "OPENPOSITION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OpenPosition implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "domain")
    private String domain;

    @Column(name = "level")
    private String level;

    @Column(name = "location")
    private String location;

    @Column(name = "description")
    private String description;

    @Column(name = "requirements")
    private String requirements;

    @Column(name = "state")
    private String state;

    @ManyToOne
    private Company company;

    @ManyToOne
    private Interview interview;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Interview getInterview() {
        return interview;
    }

    public void setInterview(Interview interview) {
        this.interview = interview;
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
                ", name='" + name + "'" +
                ", domain='" + domain + "'" +
                ", level='" + level + "'" +
                ", location='" + location + "'" +
                ", description='" + description + "'" +
                ", requirements='" + requirements + "'" +
                ", state='" + state + "'" +
                '}';
    }
}
