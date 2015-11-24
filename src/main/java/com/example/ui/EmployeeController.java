package com.example.ui;

import com.example.Employee;
import com.example.EmployeeRepository;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@RequestScoped
@Named
public class EmployeeController implements Serializable {

	@Inject
	EmployeeRepository employeeRepository;

	@Inject
	EmployeeDatatable employeeDatatable;

	public void remove(Employee employee) {
		employeeRepository.remove(employee);
		employeeDatatable.refresh();
		FacesContext.getCurrentInstance()
				.addMessage(null, new FacesMessage("Successfully deleted employee " + employee.getEmployeeNo()));
	}

}