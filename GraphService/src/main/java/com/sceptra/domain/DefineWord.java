package com.sceptra.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;


@NodeEntity
public class DefineWord {

    @GraphId
    private Long id;

    @Override
    public String toString() {
        return "DefineWord{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DefineWord(String description) {
        this.description = description;
    }

    private String description;

    public DefineWord() {
    }
}
