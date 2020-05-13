package com.example.ui;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.example.model.Employee;
import com.example.repository.EmployeeRepository;


@ViewScoped
@Named
public class EmployeeDatatable implements Serializable {

	@Inject
	EmployeeRepository employeeRepository;

	@Inject
	SearchForm searchForm;

	private List<Employee> values;

	public List<Employee> getValues() {
		if (values == null)
			refresh();
		return values;
	}

	public void refresh() {
		if (searchForm.getHiredAfter() == null) {
			values = employeeRepository.queryAll();
		} else {
			values = employeeRepository.queryHiredAfter(searchForm.getHiredAfter());
		}
	}
}
