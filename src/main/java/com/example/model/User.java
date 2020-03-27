package com.example.model;


import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Basic user entity.
 */
@Entity
@Table(name = "security_user")
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "first_name", length = 50)
	private String firstName;

	@Column(name = "last_name", length = 50)
	private String lastName;

	@Column(name = "email_address", length = 300)
	@NotNull
	private String emailAddress;

	@Column(name = "user_password", length = 100)
	private String password;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_group_id")
	private SecurityGroup securityGroup;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public SecurityGroup getSecurityGroup() {
		return securityGroup;
	}

	public void setSecurityGroup(SecurityGroup securityGroup) {
		this.securityGroup = securityGroup;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(userId, user.userId) &&
				Objects.equals(emailAddress, user.emailAddress);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, emailAddress);
	}

	@Override
	public String toString() {
		return "User{" +
				"userId=" + userId +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", emailAddress='" + emailAddress + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}
