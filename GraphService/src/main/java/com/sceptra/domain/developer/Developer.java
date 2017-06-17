package com.sceptra.domain.developer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Developer {

    Double overollQuality;
    //    Map<String,Double> technologyList;
    Double percentage;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "overollQuality=" + overollQuality +
                ", percentage=" + percentage +
                ", username='" + username + '\'' +
                ", technology='" + technology + '\'' +
                ", id=" + id +
                '}';
    }

    String username;


    public Double getOverollQuality() {
        return overollQuality;
    }

    public void setOverollQuality(Double overollQuality) {
        this.overollQuality = overollQuality;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
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
