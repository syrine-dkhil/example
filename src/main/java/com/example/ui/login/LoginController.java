package com.example.ui.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;


@RequestScoped
@Named
public class LoginController {

	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

	@Inject
	LoginForm loginForm;

	@Inject
	private SecurityContext securityContext;

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
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		try {
			request.logout();
		} catch (ServletException e) {
			LOG.warn("Logout failed: " + e.getMessage(), e);
		}

		Credential credential = new UsernamePasswordCredential(
				userName, new Password(password));
		AuthenticationStatus status = securityContext
				.authenticate(
						request,
						response,
						withParams().credential(credential));
		if (status == AuthenticationStatus.SUCCESS) {
			LOG.info("User {0} started a new session.", userName);
			return "home";
		} else {
			return null;
		}
	}

}
