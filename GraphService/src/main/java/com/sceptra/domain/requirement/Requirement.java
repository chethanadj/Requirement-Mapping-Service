package com.sceptra.domain.requirement;


public class Requirement {
    String paragraph;

    @Override
    public String toString() {
        return "Requirement{" +
                "paragraph='" + paragraph + '\'' +
                '}';
    }

    public String getParagraph() {
        return paragraph;
    }

    public void setParagraph(String paragraph) {
        this.paragraph = paragraph;
    }
}
