package com.medianocean.assessment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medianocean.assessment.model.Matches;
import com.medianocean.assessment.service.TournamentService;

@RestController
@RequestMapping(value="/api/tournaments")
public class TournamentController {

	@Autowired
	private TournamentService tournamentService; 
	
	@GetMapping("generateSchedule")
	public Matches generateSchedule() {
		Matches matches = tournamentService.generateTournamentSchedule();
		return matches;
	}
	
}
