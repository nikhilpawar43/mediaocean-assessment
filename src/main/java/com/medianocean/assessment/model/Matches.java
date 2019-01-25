package com.medianocean.assessment.model;

import java.util.List;

public class Matches {

	private int totalMatches;

	private List<Match> matches;

	public Matches() {

	}

	public Matches(int totalMatches, List<Match> matches) {
		this.totalMatches = totalMatches;
		this.matches = matches;
	}

	public List<Match> getMatches() {
		return matches;
	}

	public int getTotalMatches() {
		return totalMatches;
	}

}
