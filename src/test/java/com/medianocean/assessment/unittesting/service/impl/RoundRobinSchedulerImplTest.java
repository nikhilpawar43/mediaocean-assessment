package com.medianocean.assessment.unittesting.service.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.medianocean.assessment.model.Match;
import com.medianocean.assessment.model.Team;
import com.medianocean.assessment.repository.MatchRepository;
import com.medianocean.assessment.repository.TeamRepository;
import com.medianocean.assessment.service.MatchSchedulerService;
import com.medianocean.assessment.service.impl.RoundRobinSchedulerImpl;

@RunWith(MockitoJUnitRunner.class)
public class RoundRobinSchedulerImplTest {

	@Mock
	private TeamRepository teamRepository;
	
	@Mock
	private MatchRepository matchRepository;
	
	@InjectMocks
	private MatchSchedulerService matchSchedulerService = new RoundRobinSchedulerImpl();
	
	@Test
	public void calculateTotalMatchesToBePlayed_positiveTest_With12TeamsPlayingInTheTournament() {
		
		assertEquals(132, matchSchedulerService.calculateTotalMatchesToBePlayed(12));
	}
	
	@Test
	public void calculateTotalMatchesToBePlayed_positiveTest_With8TeamsPlayingInTheTournament() {
		
		assertEquals(56, matchSchedulerService.calculateTotalMatchesToBePlayed(8));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void calculateTotalMatchesToBePlayed_NegativeTest_WithNoTeamsToPlay() {
		
		matchSchedulerService.calculateTotalMatchesToBePlayed(0);
	}

	@Test
	public void shuffleTeamsInMatrix_positiveTest_With8TeamsScheduled() {
		
		// 1. Given: The input schedule matrix contains the IDs of the teams playing in the tournament.
		int[] matrix = { 1, 2, 3, 4, 5, 6, 7, 8 };

		// 2. Making the actual test call to shuffleTeamsInMatrix()
		matchSchedulerService.shuffleTeamsInMatrix(matrix);
		
		// 3. Then: Verify the size of matrix remains constant, and right-rotation is performed as expected.
		assertEquals(8, matrix.length);
		
		int[] expectedMatrix = { 1, 8, 2, 3, 4, 5, 6, 7 };
		assertArrayEquals(expectedMatrix, matrix);
	}
	
	@Test
	public void shuffleTeamsInMatrix_positiveTest_With12TeamsScheduled() {
		
		// 1. Given: The input schedule matrix contains the IDs of the teams playing in the tournament.
		int[] matrix = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };

		// 2. Making the actual test call to shuffleTeamsInMatrix()
		matchSchedulerService.shuffleTeamsInMatrix(matrix);
		
		// 3. Then: Verify the size of matrix remains constant, and right-rotation is performed as expected.
		assertEquals(12, matrix.length);
		
		int[] expectedMatrix = { 1, 12, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
		assertArrayEquals(expectedMatrix, matrix);
	}
	
	
	/**
	 * 	No of teams				: 8
	 * 	matrix					: [ 1, 2, 3, 4, 
	 * 								8, 7, 6, 5 ]
	 * 	Maximum matches per day	: 2
	 * 	
	 * 	Description:	In this scenario, we can have 4 matches in this round. (1 vs 8), (2 vs 7), (3 vs 6), (4 vs 5)
	 * 					Thus, this function will return maximum number of matches that can be played, 
	 * 					which is 4 (In 1 round, but 2 days).		
	 * 		
	 */
	@Test
	public void scheduleMatchesPerRound_positiveTest_with8TeamsScheduled_atHomeGround_in1Round() {

		/*
		 * 	1. Given: 
		 * 		The input schedule matrix contains the IDs of the teams playing in the tournament.
		 * 		Set the home ground to opponent-1's stadium.
		 * 		Providing the list of 8 pro-kabaddi teams.
		 */
						
		int[] matrix = { 1, 2, 3, 4, 5, 6, 7, 8 };
		int day = 1;
		boolean opponent1HomeGround = true;
		
		List<Team> teams = new ArrayList<>();
		
		teams.add(new Team(1, "Bengal Warriors", "Kolkata"));
		teams.add(new Team(2, "Bengaluru Bulls", "Bengaluru"));
		teams.add(new Team(3, "Dabang Delhi KC", "Delhi"));
		teams.add(new Team(4, "Gujarat Fortune Giants", "Ahmedabad"));
		teams.add(new Team(5, "Haryana Steelers", "Sonipat"));
		teams.add(new Team(6, "Jaipur Pink Panthers", "Jaipur"));
		teams.add(new Team(7, "Patna Pirates", "Patna"));
		teams.add(new Team(8, "Puneri Paltan", "Pune"));
		
		List<Match> matches = new ArrayList<>();

		// 2. When: Mocking/stubbing the findById() method to return selected team from arraylist. 
		for (int i=1; i<=teams.size(); i++)
			when(teamRepository.findById(i)).thenReturn(teams.get(i-1));
		
		// 3. Then: Verify that 4 matches are scheduled in 2 days (In Round-1).
		int actualMatchesScheduled = matchSchedulerService.scheduleMatchesPerRound(matrix, day, opponent1HomeGround, matches);

		int expectedMatchesScheduled = matches.size();	// 4
		int expectedDays = 2;
		String expectedMatch1_homeGround = "Kolkata";
		
		assertEquals(expectedMatchesScheduled, actualMatchesScheduled);
		assertEquals(expectedDays, matches.get(matches.size()-1).getDay());
		
		// This match is played between [Bengal Warriors vs Puneri Paltan], thus played at Bengal's home ground Kolkata.
		assertEquals(expectedMatch1_homeGround, matches.get(0).getVenue());
	}
	
	@Test
	public void prepareSchedule_positiveTest_extractListOfScheduledMatches() {
		int[] matrix = { 1, 2, 3, 4, 5, 6, 7, 8 };
		int totalMatches = 56;
				
		List<Team> teams = new ArrayList<>();
		
		teams.add(new Team(1, "Bengal Warriors", "Kolkata"));
		teams.add(new Team(2, "Bengaluru Bulls", "Bengaluru"));
		teams.add(new Team(3, "Dabang Delhi KC", "Delhi"));
		teams.add(new Team(4, "Gujarat Fortune Giants", "Ahmedabad"));
		teams.add(new Team(5, "Haryana Steelers", "Sonipat"));
		teams.add(new Team(6, "Jaipur Pink Panthers", "Jaipur"));
		teams.add(new Team(7, "Patna Pirates", "Patna"));
		teams.add(new Team(8, "Puneri Paltan", "Pune"));
		
		

		// 2. When: Mocking/stubbing the findById() method to return selected team from arraylist. 
		for (int i=1; i<=teams.size(); i++)
			when(teamRepository.findById(i)).thenReturn(teams.get(i-1));
		
		// 3. Then: Verify that 4 matches are scheduled in 2 days (In Round-1).
		List<Match> matches = matchSchedulerService.prepareSchedule(matrix, totalMatches);

		int expectedMatchesScheduled = matches.size();	// 4
		int expectedDays = 28;
		
		assertEquals(expectedMatchesScheduled, matches.size());
		assertEquals(expectedDays, matches.get(matches.size()-1).getDay());
		
	}
}
