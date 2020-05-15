package com.example.repository;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import com.example.model.Employee;

/**
 * Repository for the {@link Employee} entity.
 */
@RequestScoped
public class EmployeeRepository {

	@PersistenceContext
	EntityManager entityManager;

	@Timed(name = "employeeQueryTime",
			tags = {"method=queryAll"},
			absolute = true,
			description = "Time needed to process a query to query all employees")
	@Counted(name = "employeeQueryCount",
			absolute = true,
			description = "Number of times we queried for all employees")
	public List<Employee> queryAll() {
		TypedQuery<Employee> query = entityManager.createQuery("select e from Employee e", Employee.class);
		return query.getResultList();
	}

	public List<Employee> queryHiredAfter(Date hiredAfter) {
		TypedQuery<Employee> query = entityManager
				.createQuery("select e from Employee e where e.hireDate >= :hiredAfter", Employee.class);
		query.setParameter("hiredAfter", hiredAfter);
		return query.getResultList();
	}

	public Employee find(Integer id) {
		return entityManager.find(Employee.class, id);
	}

	@Transactional(Transactional.TxType.REQUIRED)
	public Employee merge(Employee employee) {
		return entityManager.merge(employee);
	}

	@Transactional(Transactional.TxType.REQUIRED)
	public void persist(Employee employee) {
		entityManager.persist(employee);
	}

	@Transactional(Transactional.TxType.REQUIRED)
	public void remove(Employee employee) {
		Employee attached = find(employee.getEmployeeNo());
		entityManager.remove(attached);
	}
}