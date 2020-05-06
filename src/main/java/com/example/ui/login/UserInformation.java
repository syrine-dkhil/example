package com.example.ui.login;

import java.io.Serializable;
import java.security.Principal;

import com.example.model.User;

/**
 * User login information.  Producers cannot produce null values, so we have a class to hold not only the user
 * information, but whether or not we are logged in at all.
 */
public class UserInformation implements Serializable {

	private final User user;

	public UserInformation(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public boolean isLoggedIn() {
		return user != null;
	}
}
