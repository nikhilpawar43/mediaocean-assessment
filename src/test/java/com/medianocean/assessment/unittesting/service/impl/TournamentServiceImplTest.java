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
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.medianocean.assessment.model.Match;
import com.medianocean.assessment.model.Team;
import com.medianocean.assessment.repository.TeamRepository;
import com.medianocean.assessment.service.MatchSchedulerService;
import com.medianocean.assessment.service.TournamentService;
import com.medianocean.assessment.service.impl.RoundRobinSchedulerImpl;
import com.medianocean.assessment.service.impl.TournamentServiceImpl;


@RunWith(MockitoJUnitRunner.class)
public class TournamentServiceImplTest {

	@Mock
	private TeamRepository teamRepository;
	
	@Spy
	private MatchSchedulerService roundRobinSchedulerService = new RoundRobinSchedulerImpl();
	
	@InjectMocks
	private TournamentService tournamentService = new TournamentServiceImpl();
	
	@Test
	public void prepareMatchSchedule_positiveTest_createMatchScheduleWith8Teams() {
		
		// 1. Given: List of 8 teams to initialize the schedule matrix.
		List<Team> teams = new ArrayList<>();
		
		teams.add(new Team(1, "Bengal Warriors", "Kolkata"));
		teams.add(new Team(2, "Bengaluru Bulls", "Bengaluru"));
		teams.add(new Team(3, "Dabang Delhi KC", "Delhi"));
		teams.add(new Team(4, "Gujarat Fortune Giants", "Ahmedabad"));
		teams.add(new Team(5, "Haryana Steelers", "Sonipat"));
		teams.add(new Team(6, "Jaipur Pink Panthers", "Jaipur"));
		teams.add(new Team(7, "Patna Pirates", "Patna"));
		teams.add(new Team(8, "Puneri Paltan", "Pune"));
		
		// 2. When: Mocking/stubbing the countTeams() and findAll() methods for unit testing.
		when(teamRepository.countTeams()).thenReturn(teams.size());
		when(teamRepository.findAll()).thenReturn(teams);
		// 2. When: Mocking/stubbing the findById() method to return selected team from arraylist. 
		for (int i=1; i<=teams.size(); i++)
			when(teamRepository.findById(i)).thenReturn(teams.get(i-1));
		//when(roundRobinSchedulerService.calculateTotalMatchesToBePlayed(teams.size())).thenReturn(56);
		
		List<Match> matchList = tournamentService.prepareMatchSchedule();
		
		int expectedMatches = 56;
		
		assertEquals(expectedMatches, matchList.size());
	}

	@Test
	public void initializeScheduleMatrix_positiveTest_With8TeamsPlayingInTheTournament() {
		
		// 1. Given: List of 8 teams to initialize the schedule matrix.
		List<Team> teams = new ArrayList<>();
		
		teams.add(new Team(1, "Bengal Warriors", "Kolkata"));
		teams.add(new Team(2, "Bengaluru Bulls", "Bengaluru"));
		teams.add(new Team(3, "Dabang Delhi KC", "Delhi"));
		teams.add(new Team(4, "Gujarat Fortune Giants", "Ahmedabad"));
		teams.add(new Team(5, "Haryana Steelers", "Sonipat"));
		teams.add(new Team(6, "Jaipur Pink Panthers", "Jaipur"));
		teams.add(new Team(7, "Patna Pirates", "Patna"));
		teams.add(new Team(8, "Puneri Paltan", "Pune"));
		
		// 2. When: Mocking/stubbing the countTeams() and findAll() methods for unit testing. 
		when(teamRepository.countTeams()).thenReturn(teams.size());
		when(teamRepository.findAll()).thenReturn(teams);
		
		// 3. Making the actual test call of initializeScheduleMatrix()
		int[] matchScheduleMatrix = tournamentService.initializeScheduleMatrix();
		
		// 4. Then: Verify that the match schedule matrix has been loaded with team's IDs.
		int expectedTeamSize = teams.size();
		int[] expectedMatrix = { 1, 2, 3, 4, 5, 6, 7, 8 };
		
		assertEquals(expectedTeamSize, matchScheduleMatrix.length);
		assertArrayEquals(expectedMatrix, matchScheduleMatrix);
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void initializeScheduleMatrix_negativeTest_WithNoTeamsToPlay() {
		
		// 1. Given: Empty list of teams. 
		List<Team> teams = new ArrayList<>();
		
		// 2. When: Mocking/stubbing the countTeams() and findAll() methods for unit testing. 
		when(teamRepository.countTeams()).thenReturn(teams.size());
		
		// 3. Making the actual test call of initializeScheduleMatrix()
		tournamentService.initializeScheduleMatrix();
	}

}
