package com.medianocean.assessment.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.medianocean.assessment.model.Team;
import com.medianocean.assessment.repository.TeamRepository;

@Repository("teamRepository")
@Transactional
public class TeamRepositoryImpl implements TeamRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Team addTeam(Team team) {
		entityManager.persist(team);
		entityManager.flush();
		return team;
	}

	@Override
	public void addTeams(List<Team> teams) {
		
		for (Team team : teams) {
			entityManager.persist(team);
		}
		entityManager.flush();
	}

	@Override
	public List<Team> findAll() {
		TypedQuery<Team> query = entityManager.createQuery("Select t from Team t", Team.class);
		List<Team> teams = query.getResultList();
		return teams;
	}

	@Override
	public int countTeams() {
		Query query = entityManager.createQuery("Select count(t) from Team t");
		Long teamCount = (Long) query.getSingleResult(); 
		return teamCount.intValue();
	}

	@Override
	public Team findById(int id) {
		TypedQuery<Team> query = entityManager.createQuery("Select t from Team t where t.id = :id", Team.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}

}
