package com.sceptra.domain.technology;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TechnologyPackage {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String technologyName;

    public Integer getId() {
        return id;
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

    public String getTechnologyPackage() {
        return technologyPackage;
    }

    @Override
    public String toString() {
        return "TechnologyPackage{" +
                "id=" + id +
                ", technologyName='" + technologyName + '\'' +
                ", technologyPackage='" + technologyPackage + '\'' +
                '}';
    }

    public void setTechnologyPackage(String technologyPackage) {
        this.technologyPackage = technologyPackage;
    }

    private String technologyPackage;

}
