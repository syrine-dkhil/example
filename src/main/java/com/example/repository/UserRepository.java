package com.example.repository;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.example.model.User;

/**
 * User repo--you can see how using a framework like DeltaSpike or creating your own base classes can help to
 * generalize this.  Just get used to generics if you roll your own. I have a template.
 */
@RequestScoped
public class UserRepository {

	@PersistenceContext
	EntityManager entityManager;

	public List<User> queryAll() {
		TypedQuery<User> query = entityManager.createQuery("select u from User u", User.class);
		return query.getResultList();
	}

	public User find(Integer id) {
		return entityManager.find(User.class, id);
	}

	public User findByEmail(String email) {
		TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.emailAddress = :email",
				User.class);
		query.setParameter("email", email);
		return query.getSingleResult();
	}

	@Transactional(Transactional.TxType.REQUIRED)
	public User merge(User user) {
		return entityManager.merge(user);
	}

	@Transactional(Transactional.TxType.REQUIRED)
	public void persist(User user) {
		entityManager.persist(user);
	}

	@Transactional(Transactional.TxType.REQUIRED)
	public void remove(User user) {
		User attached = find(user.getUserId());
		entityManager.remove(attached);
	}

}
