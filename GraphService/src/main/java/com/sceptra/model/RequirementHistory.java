package com.sceptra.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class RequirementHistory {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String requirement;

    @Override
    public String toString() {
        return "RequirementHistory{" +
                "id=" + id +
                ", requirement='" + requirement + '\'' +
                ", requiremetStems='" + requiremetStems + '\'' +
                ", keyword='" + keyword + '\'' +
                ", keywordScore=" + keywordScore +
                ", acceptance=" + acceptance +
                '}';
    }

    private String requiremetStems;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getRequiremetStems() {
        return requiremetStems;
    }

    public void setRequiremetStems(String requiremetStems) {
        this.requiremetStems = requiremetStems;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Double getKeywordScore() {
        return keywordScore;
    }

    public void setKeywordScore(Double keywordScore) {
        this.keywordScore = keywordScore;
    }

    public Integer getAcceptance() {
        return acceptance;
    }

    public void setAcceptance(Integer acceptance) {
        this.acceptance = acceptance;
    }

    private String keyword;
    private Double keywordScore;
    private Integer acceptance;
}