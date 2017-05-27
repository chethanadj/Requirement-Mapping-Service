package com.sceptra.domain.developer;

import java.util.Map;

/**
 * Created by chiranz on 5/27/17.
 */
public class Developer {

    String id;
    double overollQuality;
    Map<String,Double> technologyList;

    @Override
    public String toString() {
        return "developer{" +
                "id='" + id + '\'' +
                ", overollQuality=" + overollQuality +
                ", technologyList=" + technologyList +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getOverollQuality() {
        return overollQuality;
    }

    public void setOverollQuality(Double overollQuality) {
        this.overollQuality = overollQuality;
    }

    public Map<String, Double> getTechnologyList() {
        return technologyList;
    }

    public void setTechnologyList(Map<String, Double> technologyList) {
        this.technologyList = technologyList;
    }
}
