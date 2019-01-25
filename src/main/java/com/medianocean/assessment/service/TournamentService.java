package com.medianocean.assessment.service;

import com.medianocean.assessment.model.Matches;

public interface TournamentService {

	Matches prepareMatchSchedule();

	int initializeScheduleMatrix();
}
