package com.medianocean.assessment.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medianocean.assessment.model.Match;
import com.medianocean.assessment.model.Matches;
import com.medianocean.assessment.model.ScheduleMatrix;
import com.medianocean.assessment.model.Team;
import com.medianocean.assessment.repository.MatchRepository;
import com.medianocean.assessment.repository.TeamRepository;
import com.medianocean.assessment.service.TeamService;
import com.medianocean.assessment.service.TournamentService;
import com.medianocean.assessment.service.impl.RoundRobinSchedulerImpl;

@Service("tournamentService")
public class TournamentServiceImpl implements TournamentService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private TeamRepository teamRepository;
	
	@Autowired
	private RoundRobinSchedulerImpl roundRobinSchedulerService;
	
	@Autowired
	private MatchRepository matchRepository;
	
	private ScheduleMatrix scheduleMatrix;
	
	/**
	 *	This function performs the hard lifting. It's responsible for carrying out the following task
	 *	1. Initialize the matrix for scheduling the team's matches as per rounds
	 *	2. Calculate the maximum number of matches that will be played
	 *	3. Prepare round robin schedule for teams
	 *	4. Create a list of matches to be played
	 *	5. return back the matches to the client. 
	 */
	@Override
	public Matches prepareMatchSchedule() {
		
		// 1. Initialize matrix with team Ids.
		int totalTeams = initializeScheduleMatrix();
		
		// 2. Calculate total matches to be played.
		int totalMatches = roundRobinSchedulerService.calculateTotalMatchesToBePlayed(totalTeams);
		logger.info("The total matches to be played are: {}", totalMatches);
		
		// 3. Prepare schedule, and persist in the match table
		roundRobinSchedulerService.prepareSchedule(scheduleMatrix.getMatrix(), totalMatches);
		
		// 4. Retrieve the matches from the table and return to the user.
		List<Match> matchList = matchRepository.findAll();
	
		// 5. Cumulate the result
		Matches matches = new Matches(matchList.size(), matchList);
		return matches;
	}
	
	/**
	 * 	This function prepares an empty array for the teams, and places the teams in the array for round robin.
	 */
	@Override
	public int initializeScheduleMatrix() {
		
		logger.info("The total teams in the tournament are: {}", teamRepository.countTeams());
		int totalTeams = (int) teamRepository.countTeams();
		
		if (totalTeams == 0)
			throw new IllegalArgumentException("There are no teams to schedule matches !");
		
		// If there are odd number of teams, then add a buy (dummy team) to the totalTeams
		if (totalTeams % 2 == 1) {
			createBuyTeam();
			totalTeams++;
		}
		scheduleMatrix = new ScheduleMatrix(totalTeams);
		
		List<Team> teams = teamService.findAll();
		scheduleMatrix.populateTeamsInMatrix(teams);
		
		return teams.size();
	}
	
	/**
	 * 	This function creates and returns a dummy team called bye. 
	 * 	We need this, when we have odd number of teams.
	 * 	@return: bye team
	 */
	private Team createBuyTeam() {
		Team buyTeam = new Team("BYE", "No stadium");
		return teamRepository.addTeam(buyTeam);
	}
}
