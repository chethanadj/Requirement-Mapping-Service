package com.sceptra.domain.requirement;

public class Category {
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    String description;

    @Override
    public String toString() {
        return "Category{" +
                "description='" + description + '\'' +
                '}';
    }
}
