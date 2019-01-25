package com.medianocean.assessment.repository;

import java.util.List;

import com.medianocean.assessment.model.Team;

public interface TeamRepository {

	Team addTeam(Team team);

	void addTeams(List<Team> teams);

	List<Team> findAll();
	
	Team findById(int id);
	
	long countTeams();
}
