package com.sceptra.domain.requirement;


public class RequirementHistoryScore {

    String oldRequirement;
    String newRequirement;

    @Override
    public String toString() {
        return "RequirementHistoryScore{" +
                "oldRequirement='" + oldRequirement + '\'' +
                ", newRequirement='" + newRequirement + '\'' +
                ", distance=" + distance +
                '}';
    }

    public String getOldRequirement() {
        return oldRequirement;
    }

    public void setOldRequirement(String oldRequirement) {
        this.oldRequirement = oldRequirement;
    }

    public String getNewRequirement() {
        return newRequirement;
    }

    public void setNewRequirement(String newRequirement) {
        this.newRequirement = newRequirement;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    Integer distance;
}
