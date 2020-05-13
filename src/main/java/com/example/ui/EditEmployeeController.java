package com.example.ui;

import com.example.model.Employee;
import com.example.repository.EmployeeRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named
public class EditEmployeeController {

	@Inject
	EmployeeForm employeeForm;

	@Inject
	EmployeeRepository employeeRepository;

	public String save() {
		employeeRepository.merge(employeeForm.getEmployee());
		return "save";
	}

	public void preRenderViewEvent() {
		if (employeeForm.getEmployee() == null) {
			initializeEmployee();
		}
	}

	private void initializeEmployee() {
		if (employeeForm.getEmployeeId() == null) {
			employeeForm.setEmployee(new Employee());
			return;
		}
		Employee employee = employeeRepository.find(employeeForm.getEmployeeId());
		employeeForm.setEmployee(employee);
	}
}