package com.example.model;

import java.util.Objects;

import javax.persistence.*;

/**
 * Security role
 */
@Entity
@Table(name = "security_role")
public class SecurityRole {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Integer roleId;

	@Column(name = "role_name", length = 100)
	private String roleName;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SecurityRole securityRole = (SecurityRole) o;
		return Objects.equals(roleId, securityRole.roleId) &&
				Objects.equals(roleName, securityRole.roleName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(roleId, roleName);
	}

	@Override
	public String toString() {
		return "UserRole{" +
				"roleId=" + roleId +
				", roleName='" + roleName + '\'' +
				'}';
	}
}
