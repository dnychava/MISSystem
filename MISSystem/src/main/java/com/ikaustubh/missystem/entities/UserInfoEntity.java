/**
 * 
 */
package com.ikaustubh.missystem.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * @author Dnyaneshwar
 *
 */
@Data
@Entity
@Table(name = "USER_INFO")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserInfoEntity  { 

	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="USER_INFO_RID", length=10)
    private long rid;
	
    @Column(name="USER_INFO_USERNAME", unique=true, nullable=false, length=15)
    private String username;
    
    @JsonIgnore
    @Column(name="USER_INFO_PASSWORD", nullable=false , length=61)
    private String password;
    
    @Column(name="USER_INFO_ACTIVE", nullable=false)	
    private short active;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="USER_ROLE", 
    		joinColumns = {@JoinColumn(name="USER_ROLE__USER_INFO_RID", nullable=false, referencedColumnName="USER_INFO_RID")},
    		inverseJoinColumns = {@JoinColumn(name="USER_ROLE__ROLE_RID", nullable=false, referencedColumnName="ROLE_RID")}
    )
    private Set<RoleEntity> roles = new HashSet<RoleEntity>();
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_INFO__UNIT_RID", nullable = false, referencedColumnName = "UNIT_RID")
	private UnitEntity unitEntity;
    
    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, mappedBy="useInfoEntity", cascade = CascadeType.ALL)
    private EmployeeEntity employeeEntity;
    
    @Column(name="USER_INFO_CREATED_BY", nullable=true, length=15)
    private String yCreatedBy;
    
    @Column(name="USER_INFO_CREATED_TIMESTAMP", nullable=true)
    private LocalDateTime yCreatedDateAndTime;

    @Column(name="USER_INFO_MODIFIED_BY", nullable=true, length=15)
    private String zModifiedBy;
    
    @Column(name="USER_INFO_MODIFIED_TIMESTAMP", nullable=true)
    private LocalDateTime zModifiedDateAndTime;


	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the active
	 */
	public short getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(short active) {
		this.active = active;
	}
	
	/**
	 * @return the unitEntity
	 */
	public UnitEntity getUnitEntity() {
		return unitEntity;
	}

	/**
	 * @param unitEntity the unitEntity to set
	 */
	public void setUnitEntity(UnitEntity unitEntity) {
		this.unitEntity = unitEntity;
	}

	/**
	 * @return the employeeEntity
	 */
	public EmployeeEntity getEmployeeEntity() {
		return employeeEntity;
	}

	/**
	 * @param employeeEntity the employeeEntity to set
	 */
	public void setEmployeeEntity(EmployeeEntity employeeEntity) {
		this.employeeEntity = employeeEntity;
	}

	/**
	 * @return the roles
	 */
	public Set<RoleEntity> getRoles() {
		return roles;
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
	 * @return the rid
	 */
	public long getRid() {
		return rid;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserInfoEntity [rid=" + rid + ", active=" + active + ", yCreatedBy=" + yCreatedBy
				+ ", yCreatedDateAndTime=" + yCreatedDateAndTime + ", zModifiedBy=" + zModifiedBy
				+ ", zModifiedDateAndTime=" + zModifiedDateAndTime + "]";
	}
}