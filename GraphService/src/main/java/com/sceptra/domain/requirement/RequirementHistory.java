package com.sceptra.domain.requirement;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class RequirementHistory {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Length(max = 65000)
    private String requirement;

    @Override
    public String toString() {
        return "RequirementHistory{" +
                "id=" + id +
                ", requirement='" + requirement + '\'' +
                ", requirementStems='" + requirementStems + '\'' +
                ", keyword='" + keyword + '\'' +
                ", keywordScore=" + keywordScore +
                ", acceptance=" + acceptance +
                '}';
    }

    @Length(max = 65000)
    private String requirementStems;

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

    public String getRequirementStems() {
        return requirementStems;
    }

    public void setRequirementStems(String requirementStems) {
        this.requirementStems = requirementStems;
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
