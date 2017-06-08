package com.sceptra.domain.requirement;

/**
 * Created by chiranz on 6/8/17.
 */
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
