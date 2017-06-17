package com.sceptra.domain.developer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Developer {

    Double overollQuality;
    //    Map<String,Double> technologyList;
    Double presentage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "overollQuality=" + overollQuality +
                ", presentage=" + presentage +
                ", name='" + name + '\'' +
                ", technology='" + technology + '\'' +
                ", id=" + id +
                '}';
    }

    String name;


    public Double getOverollQuality() {
        return overollQuality;
    }

    public void setOverollQuality(Double overollQuality) {
        this.overollQuality = overollQuality;
    }

    public Double getPresentage() {
        return presentage;
    }

    public void setPresentage(Double presentage) {
        this.presentage = presentage;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    String technology;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

 }
