package com.medianocean.assessment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medianocean.assessment.model.Team;
import com.medianocean.assessment.service.TeamService;

@RestController
@RequestMapping(value="/api/teams")
public class TeamController {

	@Autowired
	private TeamService teamService;
	
	@PostMapping("addTeam")
	public ResponseEntity<String> addTeam(@RequestBody Team team) {
		teamService.addTeam(team);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PostMapping("addTeams")
	public ResponseEntity<String> addTeams(@RequestBody List<Team> teams) {
		teamService.addTeams(teams);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping("getTeams")
	public List<Team> findAll() {
		return teamService.findAll();
	}
}
