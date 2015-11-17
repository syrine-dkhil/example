package com.example.producer;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Produces an {@link EntityManager} for the default persistent unit.
 *
 * @author <a href="mailto:mlassiter@lassitercg.com">Mark Lassiter</a>
 */
public class PersistenceProducer {

	/**
	 * The JPA persistence manager
	 */
	@PersistenceContext
	@Produces
	EntityManager entityManager;

}