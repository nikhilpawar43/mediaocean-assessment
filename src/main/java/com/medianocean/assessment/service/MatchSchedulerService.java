package com.medianocean.assessment.service;

public interface MatchSchedulerService {
	
	int MATCHES_TO_PLAY_PER_DAY = 2;
	
	int calculateTotalMatchesToBePlayed(int totalTeams);
	
	void prepareSchedule(int[] matrix, int totalMatches);
	
	void shuffleTeamsInMatrix(int[] matrix);
}
