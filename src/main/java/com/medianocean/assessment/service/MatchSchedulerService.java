package com.medianocean.assessment.service;

import java.util.List;

import com.medianocean.assessment.model.Match;

public interface MatchSchedulerService {
	
	int MATCHES_TO_PLAY_PER_DAY = 2;
	
	int calculateTotalMatchesToBePlayed(int totalTeams);
	
	List<Match> prepareSchedule(int[] matrix, int totalMatches);
	
	int scheduleMatchesPerRound(int[] matrix, int day, boolean opponent1HomeGround, List<Match> matches);
	
	void shuffleTeamsInMatrix(int[] matrix);
}
