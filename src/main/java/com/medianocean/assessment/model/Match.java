package com.medianocean.assessment.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@Table(name="matches")
@JsonPropertyOrder({ "day", "id", "venue", "opponent1", "opponent2" })
public class Match {

	@Id
	@GeneratedValue
	@JsonProperty(value="match_id")
	private int id;
	
	@OneToOne
	private Team opponent1;
	
	@OneToOne
	private Team opponent2;
	
	private int day;
	
	private String venue;

	public Match(Team opponent1, Team opponent2, int day, String venue) {
		super();
		this.opponent1 = opponent1;
		this.opponent2 = opponent2;
		this.day = day;
		this.venue = venue;
	}

	public int getId() {
		return id;
	}

	public Team getOpponent1() {
		return opponent1;
	}

	public Team getOpponent2() {
		return opponent2;
	}

	public int getDay() {
		return day;
	}

	public String getVenue() {
		return venue;
	}

	@Override
	public String toString() {
		return "Match [id=" + id + ", opponent1=" + opponent1.getName() + ", opponent2=" + opponent2.getName() + ", day=" + day + ", venue="
				+ venue + "]";
	}
	
}
