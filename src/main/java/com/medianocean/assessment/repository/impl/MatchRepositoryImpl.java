package com.medianocean.assessment.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.medianocean.assessment.model.Match;
import com.medianocean.assessment.repository.MatchRepository;

@Repository("matchRepository")
@Transactional
public class MatchRepositoryImpl implements MatchRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void saveMatch(Match match) {
		entityManager.persist(match);
		
	}

	@Override
	public List<Match> findAll() {
		TypedQuery<Match> query = entityManager.createQuery("Select m from Match m", Match.class);
		return query.getResultList();
	}

	@Override
	public Match findById(int id) {
		TypedQuery<Match> query = entityManager.createQuery("Select m from Match m where m.id=:id", Match.class);
		query.setParameter("id", id);
		return query.getSingleResult();
	}

}
