package com.medianocean.assessment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="teams")
public class Team {

	@Id
	@GeneratedValue
	private int id;
	
	private String name;
	
	@Column(name="homeground")
	private String homeGround;

	public Team() {

	}

	public Team(String name, String homeGround) {
		this.name = name;
		this.homeGround = homeGround;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getHomeGround() {
		return homeGround;
	}

}
