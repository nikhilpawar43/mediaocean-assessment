package com.medianocean.assessment.unittesting.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medianocean.assessment.controller.TeamController;
import com.medianocean.assessment.model.Team;
import com.medianocean.assessment.service.TeamService;

@RunWith(SpringRunner.class)
@WebMvcTest(TeamController.class)
public class TeamControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private TeamService teamService;
	
	private static final String API_BASE_URI = "http://localhost:8080/api/teams";
	
	@Test
	public void findAll_positiveTest_retrieveTheListOfAllPlayingTeams() throws Exception {
		
		// 1. GIVEN: List of 8 teams to initialize the schedule matrix.
		List<Team> teams = new ArrayList<>();
		
		teams.add(new Team(1, "Bengal Warriors", "Kolkata"));
		teams.add(new Team(2, "Bengaluru Bulls", "Bengaluru"));
		teams.add(new Team(3, "Dabang Delhi KC", "Delhi"));
		teams.add(new Team(4, "Gujarat Fortune Giants", "Ahmedabad"));
		teams.add(new Team(5, "Haryana Steelers", "Sonipat"));
		teams.add(new Team(6, "Jaipur Pink Panthers", "Jaipur"));
		teams.add(new Team(7, "Patna Pirates", "Patna"));
		teams.add(new Team(8, "Puneri Paltan", "Pune"));
		
		// 2. WHEN: Mock/stub the call to actual findAll() method, to return a dummy list of teams.
		when(teamService.findAll()).thenReturn(teams);
		
		// 3. Prepare a GET request to retrieve the list of all teams playing in the tournament. The response should be in JSON format.
		RequestBuilder request = MockMvcRequestBuilders
									.get(API_BASE_URI + "/getTeams")
									.accept(MediaType.APPLICATION_JSON);
		
		// 4. Sample JSON output to test the result.
		String expectedJsonContent = 
				"[ "
					+ "{ \"id\": 1, \"name\": \"Bengal Warriors\", \"homeGround\": \"Kolkata\" }, "
					+ "{ \"id\": 2, \"name\": \"Bengaluru Bulls\", \"homeGround\": \"Bengaluru\" }, "
					+ "{ \"id\": 3, \"name\": \"Dabang Delhi KC\", \"homeGround\": \"Delhi\" }, "
					+ "{ \"id\": 4, \"name\": \"Gujarat Fortune Giants\", \"homeGround\": \"Ahmedabad\" }, "
					+ "{ \"id\": 5, \"name\": \"Haryana Steelers\", \"homeGround\": \"Sonipat\" }, "
					+ "{ \"id\": 6, \"name\": \"Jaipur Pink Panthers\", \"homeGround\": \"Jaipur\" }, "
					+ "{ \"id\": 7, \"name\": \"Patna Pirates\", \"homeGround\": \"Patna\" }, "
					+ "{ \"id\": 8, \"name\": \"Puneri Paltan\", \"homeGround\": \"Pune\" } "
				+ "]";
		
		// 5. VERIFY: Executing the GET request to verify the returned JSON data, and response HTTP status of 200 OK.
		MvcResult result = 	mockMvc.perform(request)
							.andExpect(status().isOk())
							.andExpect(content().json(expectedJsonContent))
							.andExpect(jsonPath("$", hasSize(8)))
							.andExpect(jsonPath("$[0].id", is(1)))
			                .andExpect(jsonPath("$[0].name", is("Bengal Warriors")))
			                .andExpect(jsonPath("$[0].homeGround", is("Kolkata")))
							.andReturn();
		
	}

	@Test
	public void addTeam_positiveTest_addNewTeamInTheTournament() throws Exception {
		
		// 1. GIVEN: Create a new team of Bengal Warriors, and add it to the tournament.
		Team bengalTeam = new Team("Bengal Warriors", "Kolkata");
		
		// 2. Prepare a POST request to /addTeam and send the Bengal Warriors team to the URI as a part of request body.
		RequestBuilder request = MockMvcRequestBuilders
				.post(API_BASE_URI + "/addTeam")
				.content(new ObjectMapper().writeValueAsString(bengalTeam))
				.contentType(MediaType.APPLICATION_JSON);
		
		// 3. VERIFY: Executing the POST request to verify that the new team is created successfully on the server with HTTP Created (201) status.
		MvcResult result = 	mockMvc.perform(request)
							.andExpect(status().isCreated())
							.andReturn();
	}
	
	@Test
	public void addTeams_positiveTest_addSetOfTeamsInTheTournament() throws Exception {

		// 1. GIVEN: Create a list of 4 teams, and add them to the tournament.
		List<Team> teams = new ArrayList<>();
		teams.add(new Team(1, "Bengal Warriors", "Kolkata"));
		teams.add(new Team(2, "Bengaluru Bulls", "Bengaluru"));
		teams.add(new Team(3, "Dabang Delhi KC", "Delhi"));
		teams.add(new Team(4, "Gujarat Fortune Giants", "Ahmedabad"));
		
		// 2. Prepare a POST request to /addTeams and send the list of teams to the URI as a part of request body.
		RequestBuilder request = MockMvcRequestBuilders
				.post(API_BASE_URI + "/addTeams")
				.content(new ObjectMapper().writeValueAsString(teams))
				.contentType(MediaType.APPLICATION_JSON);
		
		// 3. VERIFY: Executing the POST request to verify that the list of new teams is created successfully on the server with HTTP Created (201) status.
		MvcResult result = 	mockMvc.perform(request)
							.andExpect(status().isCreated())
							.andReturn();
	}
}
