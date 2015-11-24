package com.example.ui;

import com.example.Employee;
import com.example.EmployeeRepository;
import org.omnifaces.cdi.ViewScoped;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

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