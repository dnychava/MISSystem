/**
 * 
 */
package com.ikaustubh.missystem.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Dnyaneshwar
 * @since 24-Nov-2018
 *
 */
@Entity
@Table(name = "ROLE_TBL")
@Scope("session")
public class RoleEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ROLE_TBL_RID", nullable = false, length = 10)
	private long roleRID;

	@Column(name = "ROLE_TBL_TYPE_NAME", nullable = false, length = 50)
	private String roleTypeName;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
	private Set<UserInfoEntity> userInfo = new HashSet<UserInfoEntity>();

	@Column(name = "ROLE_TBL_CREATED_BY", nullable = true, length = 15)
	private String yCreatedBy;

	@Column(name = "ROLE_TBL_CREATED_TIMESTAMP", nullable = true)
	private LocalDateTime yCreatedDateAndTime;

	@Column(name = "ROLE_TBL_MODIFIED_BY", nullable = true, length = 15)
	private String zModifiedBy;

	@Column(name = "ROLE_TBL_MODIFIED_TIMESTAMP", nullable = true)
	private LocalDateTime zModifiedDateAndTime;

	/**
	 * @return the roleTypeName
	 */
	public String getRoleTypeName() {
		return roleTypeName;
	}

	/**
	 * @return the userInfo
	 */
	public Set<UserInfoEntity> getUserInfo() {
		return userInfo;
	}

	/**
	 * @param userInfo the userInfo to set
	 */
	public void setUserInfo(Set<UserInfoEntity> userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * @return the yCreatedBy
	 */
	public String getyCreatedBy() {
		return yCreatedBy;
	}

	/**
	 * @param yCreatedBy the yCreatedBy to set
	 */
	public void setyCreatedBy(String yCreatedBy) {
		this.yCreatedBy = yCreatedBy;
	}

	/**
	 * @return the yCreatedDateAndTime
	 */
	public LocalDateTime getyCreatedDateAndTime() {
		return yCreatedDateAndTime;
	}

	/**
	 * @param yCreatedDateAndTime the yCreatedDateAndTime to set
	 */
	public void setyCreatedDateAndTime(LocalDateTime yCreatedDateAndTime) {
		this.yCreatedDateAndTime = yCreatedDateAndTime;
	}

	/**
	 * @return the zModifiedBy
	 */
	public String getzModifiedBy() {
		return zModifiedBy;
	}

	/**
	 * @param zModifiedBy the zModifiedBy to set
	 */
	public void setzModifiedBy(String zModifiedBy) {
		this.zModifiedBy = zModifiedBy;
	}

	/**
	 * @return the zModifiedDateAndTime
	 */
	public LocalDateTime getzModifiedDateAndTime() {
		return zModifiedDateAndTime;
	}

	/**
	 * @param zModifiedDateAndTime the zModifiedDateAndTime to set
	 */
	public void setzModifiedDateAndTime(LocalDateTime zModifiedDateAndTime) {
		this.zModifiedDateAndTime = zModifiedDateAndTime;
	}

	/**
	 * @return the roleRID
	 */
	public long getRoleRID() {
		return roleRID;
	}

	/**
	 * @param roleTypeName the roleTypeName to set
	 */
	public void setRoleTypeName(String roleTypeName) {
		this.roleTypeName = roleTypeName;
	}
	
}
