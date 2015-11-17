package com.example.ui.employee;

import com.example.Employee;
import com.example.repository.EmployeeRepository;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Controller for the Employee UI page
 *
 * @author <a href="mailto:mlassiter@lassitercg.com">Mark Lassiter</a>
 */
@RequestScoped
@Named
public class EmployeeController {

	@Inject
	EmployeeRepository employeeRepository;

	public void remove(Employee employee) {
		employeeRepository.remove(employee);
		FacesContext.getCurrentInstance()
				.addMessage(null, new FacesMessage("Successfully deleted employee " + employee.getEmployeeNo()));
	}

}
