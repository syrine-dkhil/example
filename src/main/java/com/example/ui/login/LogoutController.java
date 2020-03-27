package com.example.ui.login;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequestScoped
@Named
public class LogoutController {

	/**
	 * Invoked by user to logout of current session.
	 * <p>
	 *
	 * @throws ServletException
	 */
	public String logout() throws ServletException {
		// ensure session is killed
		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
			request.logout();
			HttpSession session = request.getSession();
			if (session != null)
				session.invalidate();
		}
		return "home";
	}
}
