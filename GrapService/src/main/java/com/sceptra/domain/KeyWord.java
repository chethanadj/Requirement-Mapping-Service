
package com.sceptra.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class KeyWord {

	@GraphId private Long id;

	@Override
	public String toString() {
		return "KeyWord{" +
				"id=" + id +
				", description='" + description + '\'' +
				'}';
	}

	private String description;

	private KeyWord() {
	};

	public KeyWord(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
