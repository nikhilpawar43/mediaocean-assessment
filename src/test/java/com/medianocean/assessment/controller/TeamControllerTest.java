package com.medianocean.assessment.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.medianocean.assessment.model.Team;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeamControllerTest {

	private static final String APP_RESOURCE_BASE_URL = "http://localhost:8888/api/teams";

	@Test
	@DirtiesContext
	public void testAddTeam() {
		
		RestTemplate restTemplate = new RestTemplate();
	
		Team bengalWarriors = new Team("Bengal Warriors", "Kolkata");
		HttpEntity<Team> postData = new HttpEntity<>(bengalWarriors);
		
		Team team= restTemplate.postForObject(APP_RESOURCE_BASE_URL + "/addTeam", postData, Team.class);
		
		assertNotNull(team);
		assertTrue(team.getId() > 0);
	}
	
	@Test
	@DirtiesContext
	public void testAddTeams() {
		
		RestTemplate restTemplate = new RestTemplate();
		
		List<Team> teams = new ArrayList<>();
		
		teams.add(new Team("Bengal Warriors", "Kolkata"));
		teams.add(new Team("Bengaluru Bulls", "Bengaluru"));
		teams.add(new Team("Dabang Delhi KC", "Delhi"));
		teams.add(new Team("Gujarat Fortune Giants", "Ahmedabad"));
		teams.add(new Team("Haryana Steelers", "Sonipat"));
		teams.add(new Team("Jaipur Pink Panthers", "Jaipur"));
		teams.add(new Team("Patna Pirates", "Patna"));
		teams.add(new Team("Puneri Paltan", "Pune"));
		
		ResponseEntity<String> response = restTemplate.postForEntity(APP_RESOURCE_BASE_URL + "/addTeams", teams, String.class);
		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
	}
	
	@Test
	@DirtiesContext
	public void getAllTeams() {

		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<List<Team>> response = restTemplate.exchange(APP_RESOURCE_BASE_URL + "/getTeams", HttpMethod.GET, null, new ParameterizedTypeReference<List<Team>>() { });
		
		List<Team> resultingTeams = response.getBody();
		
		assertEquals(9, resultingTeams.size());
	}
}
