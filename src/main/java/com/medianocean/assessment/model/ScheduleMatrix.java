package com.medianocean.assessment.model;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduleMatrix {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private int[] matrix;
	
	public ScheduleMatrix(int totalTeams) {
		matrix = new int[totalTeams];
	}
	
	public int[] populateTeamsInMatrix(List<Team> teams) {

		logger.info("Populating team Ids inside the matrix.");
		
		for (int i=0; i<teams.size(); i++) {
			matrix[i] = teams.get(i).getId();
		}
		
		logger.info("The initialized matrix is: {}", Arrays.toString(matrix));
		return matrix;
	}

	public int[] getMatrix() {
		return matrix;
	}
}
