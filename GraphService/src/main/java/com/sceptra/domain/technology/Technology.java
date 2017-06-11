package com.sceptra.domain.technology;

import java.util.Map;



public class Technology {
    String technologyName;
    Map<String,Double> developerList;
    double usage;

    public String getTechnologyName() {
        return technologyName;
    }

    @Override
    public String toString() {
        return "Technology{" +
                "technologyName='" + technologyName + '\'' +
                ", developerList=" + developerList +
                ", usage=" + usage +
                '}';
    }

    public void setTechnologyName(String technologyName) {
        this.technologyName = technologyName;
    }

    public Map<String, Double> getDeveloperList() {
        return developerList;
    }

    public void setDeveloperList(Map<String, Double> developerList) {
        this.developerList = developerList;
    }

    public double getUsage() {
        return usage;
    }

    public void setUsage(double usage) {
        this.usage = usage;
    }
}