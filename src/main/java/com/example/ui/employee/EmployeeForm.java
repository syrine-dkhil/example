package com.example.ui.employee;

import com.example.Employee;
import org.omnifaces.cdi.ViewScoped;

import javax.inject.Named;
import java.io.Serializable;

/**
 * @author <a href="mailto:mlassiter@lassitercg.com">Mark Lassiter</a>
 */
@ViewScoped
@Named
public class EmployeeForm implements Serializable {

	private Integer employeeId;

	private Employee employee;

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(final Integer employeeId) {
		this.employeeId = employeeId;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(final Employee employee) {
		this.employee = employee;
	}
}
