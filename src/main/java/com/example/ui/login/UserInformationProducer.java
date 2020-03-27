package com.example.ui.login;

import java.io.Serializable;
import java.security.Principal;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.example.model.User;
import com.example.repository.UserRepository;

/**
 * Produces the current lo
 */
@SessionScoped
public class UserInformationProducer implements Serializable {

	@Inject
	UserRepository userRepository;

	@Produces
	@Named
	@RequestScoped
	public UserInformation getUserInformation() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Principal userPrincipal = facesContext.getExternalContext().getUserPrincipal();
		if (userPrincipal == null)
			return new UserInformation(null);
		else {
			User user = userRepository.findByEmail(userPrincipal.getName());
			return new UserInformation(user);
		}
	}
}
