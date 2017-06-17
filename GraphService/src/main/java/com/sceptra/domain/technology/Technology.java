package com.sceptra.domain.technology;

public class Technology {
    String technologyName;
    double presentage;
    double overollQuality;

    public double getOverollQuality() {
        return overollQuality;
    }

    public void setOverollQuality(double overollQuality) {
        this.overollQuality = overollQuality;
    }

    @Override
    public String toString() {
        return "Technology{" +
                "technologyName='" + technologyName + '\'' +
                ", presentage=" + presentage +
                ", overollQuality=" + overollQuality +
                '}';

    }

    public String getTechnologyName() {
        return technologyName;
    }

    public void setTechnologyName(String technologyName) {
        this.technologyName = technologyName;
    }

    public double getPresentage() {
        return presentage;
    }

    public void setPresentage(double presentage) {
        this.presentage = presentage;
    }
}
