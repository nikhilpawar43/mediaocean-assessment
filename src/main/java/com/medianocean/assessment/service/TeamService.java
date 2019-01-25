package com.medianocean.assessment.service;

import java.util.List;

import com.medianocean.assessment.model.Team;

public interface TeamService {

	Team addTeam(Team team);

	void addTeams(List<Team> teams);
	
	List<Team> findAll();
	
	Team findById(int id);
}
