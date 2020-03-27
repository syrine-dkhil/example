package com.example.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

/**
 * Good idea to have security groups that encapsulate multiple roles into something recognizable.  Example: Admin
 */
@Entity
@Table(name = "security_group")
public class SecurityGroup implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "group_id")
	private Integer groupId;

	@Column(name = "group_name", length = 100)
	private String groupName;

	@ManyToMany
	@JoinTable(
			name = "group_role",
			joinColumns = @JoinColumn(name = "fk_group_id"),
			inverseJoinColumns = @JoinColumn(name = "fk_role_id"))
	private Set<SecurityRole> roles;

	@Lob
	private String description;

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Set<SecurityRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<SecurityRole> roles) {
		this.roles = roles;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SecurityGroup that = (SecurityGroup) o;
		return Objects.equals(groupId, that.groupId) &&
				Objects.equals(groupName, that.groupName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(groupId, groupName);
	}

	@Override
	public String toString() {
		return "SecurityGroup{" +
				"groupId=" + groupId +
				", groupName='" + groupName + '\'' +
				", roles=" + roles +
				", description='" + description + '\'' +
				'}';
	}
}
