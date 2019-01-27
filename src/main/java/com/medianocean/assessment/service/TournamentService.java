package com.medianocean.assessment.service;

import java.util.List;

import com.medianocean.assessment.model.Match;
import com.medianocean.assessment.model.Matches;

public interface TournamentService {

	Matches generateTournamentSchedule();
	
	List<Match> prepareMatchSchedule();

	int[] initializeScheduleMatrix();
	
}
