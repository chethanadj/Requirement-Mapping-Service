package com.sceptra.domain.requirement;

import org.neo4j.ogm.annotation.*;



@RelationshipEntity(type="DEFINED")
public class Defined {

    @GraphId
    private Long relationshipId;
    @Property
    private String rel;
    @EndNode
    private KeyWord keyWord;
    @StartNode
    private DefineWord defineWord;

    @Override
    public String toString() {
        return "Defined{" +
                "relationshipId=" + relationshipId +
                ", title='" + rel + '\'' +
                ", keyWord=" + keyWord +
                ", defineWord=" + defineWord +
                '}';
    }

    public Long getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(Long relationshipId) {
        this.relationshipId = relationshipId;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public KeyWord getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(KeyWord keyWord) {
        this.keyWord = keyWord;
    }

    public Defined(KeyWord keyWord, DefineWord defineWord) {
        this.keyWord = keyWord;
        this.defineWord = defineWord;
        this.rel=defineWord.getDescription();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Defined)) return false;

        Defined defined = (Defined) o;

        return rel != null ? rel.equals(defined.rel) : defined.rel == null;

    }

    @Override
    public int hashCode() {
        return rel != null ? rel.hashCode() : 0;
    }

    public Defined() {
    }

    public DefineWord getDefineWord() {
        return defineWord;
    }

    public void setDefineWord(DefineWord defineWord) {
        this.defineWord = defineWord;
    }
}
