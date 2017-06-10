package com.sceptra.domain.requirement;

public class Description {
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "description{" +
                "word='" + word + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public void setDescription(String description) {
        this.description = description;
    }

    String word;
    String description;

}
