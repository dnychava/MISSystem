package com.ikaustubh.missystem.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * This entity represent of two type roles.<BR>
 * 1. It self Main unint.<BR>
 * 2. It sub unit has main unit reference.<BR>
 * Eg. Self join, Emp and manager relationship. 
 * 
 * @author Dnyaneshwar Chavan
 * @since 02-Feb-2020
 */

@Entity
@Table(name = "UNIT")
//@Scope("global-session")
public class UnitEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "UNIT_RID", length = 10, nullable = false)
	private long rid;

	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL )
	@JoinColumn(name="UNIT__UNIT_TYPE_RID", nullable=false, referencedColumnName="UNIT_TYPE_RID")
	private UnitTypeEntity unitTypeEntity;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "UNIT__MAIN_UNIT_RID", nullable = true, referencedColumnName = "UNIT_RID")
	private UnitEntity mainUnit;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mainUnit", cascade = CascadeType.ALL)
	private List<UnitEntity> mainUnitEntities = new ArrayList<UnitEntity>();
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "unitEntity", cascade = CascadeType.ALL)
	private List<UserInfoEntity> userInfoEntities = new ArrayList<UserInfoEntity>();
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "unitEntity", cascade = CascadeType.ALL)
	private List<BudgetDistributeEntity> budgetDistributeEntities = new ArrayList<BudgetDistributeEntity>();
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "unitEntity", cascade = CascadeType.ALL)
	private List<ExpenditureEntity> expenditureEntities = new ArrayList<ExpenditureEntity>();
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "UNIT__ADDRESS_RID", nullable = false, referencedColumnName = "ADDRESS_RID")
	private AddressEntity addressEntity;
	
	@Column(name = "UNIT_NAME", unique = true, nullable = false, length = 100)
	private String name;
	
	@Column(name = "UNIT_CREATED_BY", nullable = true, length = 15)
	private String yCreatedBy;

	@Column(name = "UNIT_CREATED_TIMESTAMP", nullable = true)
	private LocalDateTime yCreatedDateAndTime;

	@Column(name = "UNIT_MODIFIED_BY", nullable = true, length = 15)
	private String zModifiedBy;

	@Column(name = "UNIT_MODIFIED_TIMESTAMP", nullable = true)
	private LocalDateTime zModifiedDateAndTime;

	/**
	 * @return the unitTypeEntity
	 */
	public UnitTypeEntity getUnitTypeEntity() {
		return unitTypeEntity;
	}

	/**
	 * @param unitTypeEntity the unitTypeEntity to set
	 */
	public void setUnitTypeEntity(UnitTypeEntity unitTypeEntity) {
		this.unitTypeEntity = unitTypeEntity;
	}

	/**
	 * @return the mainUnit
	 */
	public UnitEntity getMainUnit() {
		return mainUnit;
	}

	/**
	 * @param mainUnit the mainUnit to set
	 */
	public void setMainUnit(UnitEntity mainUnit) {
		this.mainUnit = mainUnit;
	}

	/**
	 * @return the mainUnitEntities
	 */
	public List<UnitEntity> getMainUnitEntities() {
		return mainUnitEntities;
	}

	/**
	 * @param mainUnitEntities the mainUnitEntities to set
	 */
	public void setMainUnitEntities(List<UnitEntity> mainUnitEntities) {
		this.mainUnitEntities = mainUnitEntities;
	}

	/**
	 * @return the userInfoEntities
	 */
	public List<UserInfoEntity> getUserInfoEntities() {
		return userInfoEntities;
	}

	/**
	 * @param userInfoEntities the userInfoEntities to set
	 */
	public void setUserInfoEntities(List<UserInfoEntity> userInfoEntities) {
		this.userInfoEntities = userInfoEntities;
	}

	/**
	 * @return the budgetDistributeEntities
	 */
	public List<BudgetDistributeEntity> getBudgetDistributeEntities() {
		return budgetDistributeEntities;
	}

	/**
	 * @param budgetDistributeEntities the budgetDistributeEntities to set
	 */
	public void setBudgetDistributeEntities(List<BudgetDistributeEntity> budgetDistributeEntities) {
		this.budgetDistributeEntities = budgetDistributeEntities;
	}

	/**
	 * @return the addressEntity
	 */
	public AddressEntity getAddressEntity() {
		return addressEntity;
	}

	/**
	 * @param addressEntity the addressEntity to set
	 */
	public void setAddressEntity(AddressEntity addressEntity) {
		this.addressEntity = addressEntity;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
		return "UnitEntity [rid=" + rid + ", name=" + name + ", yCreatedBy=" + yCreatedBy + ", yCreatedDateAndTime="
				+ yCreatedDateAndTime + ", zModifiedBy=" + zModifiedBy + ", zModifiedDateAndTime="
				+ zModifiedDateAndTime + "]";
	}
	
}
