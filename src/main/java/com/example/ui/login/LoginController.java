package com.example.ui.login;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;


@RequestScoped
@Named
public class LoginController {

	private static final Logger LOG = Logger.getLogger(LoginController.class.getName());

	@Inject
	LoginForm loginForm;

	/**
	 * Attempt a login with the user provided credentials. If successful, user
	 * information is updated and the user session is created. If not, a message
	 * send back to the user indicating a failure.
	 */
	public String login() {

		final String userName = loginForm.getUserName();
		final String password = loginForm.getPassword();

		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		try {
			request.logout();
			request.login(userName, password);
		} catch (ServletException e) {
			LOG.log(Level.WARNING, e.getMessage(), e);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid username or password",
					"Invalid username or password"));
			return null; // stay on same page
		}

		LOG.log(Level.INFO, "User {0} started a new session.", userName);
		return "home";
	}

}
