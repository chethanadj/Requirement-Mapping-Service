package com.sceptra.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TechnologyEntity {
    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "TechnologyEntity{" +
                "id=" + id +
                ", technologyName='" + technologyName + '\'' +
                ", technologyUsages='" + technologyUsages + '\'' +
                ", rating=" + rating +
                '}';
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setId(Integer id) {

        this.id = id;
    }

    public String getTechnologyName() {
        return technologyName;
    }

    public void setTechnologyName(String technologyName) {
        this.technologyName = technologyName;
    }

    public String getTechnologyUsages() {
        return technologyUsages;
    }

    public void setTechnologyUsages(String technologyUsages) {
        this.technologyUsages = technologyUsages;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String technologyName;

    private String technologyUsages;

    private Integer rating;

}

