package com.medianocean.assessment.repository;

import java.util.List;

import com.medianocean.assessment.model.Match;

public interface MatchRepository {

	void saveMatch(Match match);
	
	List<Match> findAll();
	
	Match findById(int id);
}
