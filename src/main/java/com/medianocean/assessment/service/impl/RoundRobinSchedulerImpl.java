package com.medianocean.assessment.service.impl;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medianocean.assessment.model.Match;
import com.medianocean.assessment.model.Team;
import com.medianocean.assessment.repository.MatchRepository;
import com.medianocean.assessment.service.MatchSchedulerService;
import com.medianocean.assessment.service.TeamService;

@Service("roundRobinSchedulerService")
public class RoundRobinSchedulerImpl implements MatchSchedulerService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private MatchRepository matchRepository;
	
	@Override
	public int calculateTotalMatchesToBePlayed(int totalTeams) {
		
		int totalMatches = 0;
		
		totalMatches = (totalTeams/2) * (totalTeams-1);
		
		totalMatches = totalMatches * 2;
		return totalMatches;
	}

	@Override
	public void prepareSchedule(int[] matrix, int totalMatches) {
		
		int matchesScheduled = 0;
		int day = 0;
		boolean opponent1HomeGround = true;
		
		// Matches scheduled team-1 home ground
		while (matchesScheduled < totalMatches/2) {
			
			day = calculateMatchDay(matchesScheduled);
			matchesScheduled += scheduleMatchesPerRound(matrix, day, opponent1HomeGround);
			
			shuffleTeamsInMatrix(matrix);
		}
		
		// Matches scheduled team-2 home ground
		opponent1HomeGround = false;
		while (matchesScheduled < totalMatches) {
			
			day = calculateMatchDay(matchesScheduled);
			matchesScheduled += scheduleMatchesPerRound(matrix, day, opponent1HomeGround);
			
			shuffleTeamsInMatrix(matrix);
		}
	}

	private int scheduleMatchesPerRound(int[] matrix, int day, boolean opponent1HomeGround) {
		
		int opponent1Pointer = 0;
		int opponent2Pointer = matrix.length-1;
		
		while (opponent1Pointer < opponent2Pointer) {
			
			Team opponent1 = null;
			Team opponent2 = null;
			String homeGround = null;
			Match match = null;
			
			opponent1 = teamService.findById(matrix[opponent1Pointer]);
			opponent2 = teamService.findById(matrix[opponent2Pointer]);
			
			homeGround = opponent1HomeGround ? opponent1.getHomeGround() : opponent2.getHomeGround();
			
			match = new Match(opponent1, opponent2, day, homeGround);
			
			matchRepository.saveMatch(match);
			
			opponent1Pointer++;
			opponent2Pointer--;
			
			logger.info("The match scheduled is: \n{}", match);
			
			if (opponent1Pointer % MATCHES_TO_PLAY_PER_DAY == 0)
				day++;
			
		}
		
		return opponent1Pointer;
	}
	
	private int calculateMatchDay(int matchesScheduled) {
		return (matchesScheduled / MATCHES_TO_PLAY_PER_DAY) + 1;
	}
	
	
	@Override
	public void shuffleTeamsInMatrix(int[] matrix) {

		int temp = matrix[matrix.length-1];
		
		for (int i=matrix.length-1; i>0; i--) {
			matrix[i] = matrix[i-1];
		}
		
		matrix[1] = temp;
		
		logger.info("The shuffled matrix is: {}", Arrays.toString(matrix));
	}

}
