package com.medianocean.assessment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medianocean.assessment.model.Team;
import com.medianocean.assessment.repository.TeamRepository;
import com.medianocean.assessment.service.TeamService;

@Service("teamService")
public class TeamServiceImpl implements TeamService {

	@Autowired
	private TeamRepository teamRepository;
	
	@Override
	public Team addTeam(Team team) {
		return teamRepository.addTeam(team);
	}
	
	@Override
	public void addTeams(List<Team> teams) {
		teamRepository.addTeams(teams);
	}

	@Override
	public List<Team> findAll() {
		return teamRepository.findAll();
	}

	@Override
	public Team findById(int id) {
		return teamRepository.findById(id);
	}

}
