package com.sceptra.domain.technology;

public class Technology {
    String technologyName;
    double percentage;
    double overallQuality;

    public double getOverallQuality() {
        return overallQuality;
    }

    public void setOverallQuality(double overallQuality) {
        this.overallQuality = overallQuality;
    }

    @Override
    public String toString() {
        return "Technology{" +
                "technologyName='" + technologyName + '\'' +
                ", percentage=" + percentage +
                ", overallQuality=" + overallQuality +
                '}';

    }

    public String getTechnologyName() {
        return technologyName;
    }

    public void setTechnologyName(String technologyName) {
        this.technologyName = technologyName;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
