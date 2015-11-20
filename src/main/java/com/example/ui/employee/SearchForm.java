package com.example.ui.employee;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="mailto:mlassiter@lassitercg.com">Mark Lassiter</a>
 */
@SessionScoped
@Named
public class SearchForm implements Serializable {

	private Date hiredAfter;

	public Date getHiredAfter() {
		return hiredAfter;
	}

	public void setHiredAfter(final Date hiredAfter) {
		this.hiredAfter = hiredAfter;
	}
}