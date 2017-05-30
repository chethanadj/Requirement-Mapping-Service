package com.sceptra.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by chiranz on 5/30/17.
 */
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
                ", technology_usages='" + technology_usages + '\'' +
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

    public String getTechnology_usages() {
        return technology_usages;
    }

    public void setTechnology_usages(String technology_usages) {
        this.technology_usages = technology_usages;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String technologyName;

    private String technology_usages;

    private Integer rating;

}

