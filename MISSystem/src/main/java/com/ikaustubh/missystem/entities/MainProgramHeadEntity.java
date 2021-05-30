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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * This class has two type of <code>MAIN_PROGRAM_HEAD</code>.<BR>
 * 1. NHM <BR>
 * 2. NUHM
 * 
 * @author Dnyaneshwar Chavan
 * @since 25-Aug-2019
 *
 */

@Entity
@Table(name = "MAIN_PROGRAM_HEAD")
//@Scope("global-session")
public class MainProgramHeadEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MAIN_PROGRAM_HEAD_RID", length = 10, nullable = false)
	protected long id;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mainProgramHeadEntity", cascade = CascadeType.ALL)
	private List<ActivityEntity> activityEntities = new ArrayList<ActivityEntity>();

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "MAIN_PROGRAM_HEAD__FINANCIAL_YEAR_RID", nullable = false, referencedColumnName = "FINANCIAL_YEAR_RID")
	private FinancialYearEntity financialYearEntity;

	@Column(name = "MAIN_PROGRAM_HEAD_NAME", nullable = false, length = 100)
	protected String name;

	@Column(name = "MAIN_PROGRAM_HEAD_CODE", nullable = false, length = 20)
	protected String newCode;

	@Column(name = "MAIN_PROGRAM_HEAD_CREATED_BY", nullable = true, length = 15)
	private String yCreatedBy;

	@Column(name = "MAIN_PROGRAM_HEAD_CREATED_TIMESTAMP", nullable = true)
	private LocalDateTime yCreatedDateAndTime;

	@Column(name = "MAIN_PROGRAM_HEAD_MODIFIED_BY", nullable = true, length = 15)
	private String zModifiedBy;

	@Column(name = "MAIN_PROGRAM_HEAD_MODIFIED_TIMESTAMP", nullable = true)
	private LocalDateTime zModifiedDateAndTime;

	/**
	 * @return the activityEntities
	 */
	public List<ActivityEntity> getActivityEntities() {
		return activityEntities;
	}

	/**
	 * @param activityEntities the activityEntities to set
	 */
	public void setActivityEntities(List<ActivityEntity> activityEntities) {
		this.activityEntities = activityEntities;
	}

	/**
	 * @return the financialYearEntity
	 */
	public FinancialYearEntity getFinancialYearEntity() {
		return financialYearEntity;
	}

	/**
	 * @param financialYearEntity the financialYearEntity to set
	 */
	public void setFinancialYearEntity(FinancialYearEntity financialYearEntity) {
		this.financialYearEntity = financialYearEntity;
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
	 * @return the newCode
	 */
	public String getNewCode() {
		return newCode;
	}

	/**
	 * @param newCode the newCode to set
	 */
	public void setNewCode(String newCode) {
		this.newCode = newCode;
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
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MainProgramHeadEntity [id=" + id + ", name=" + name + ", newCode=" + newCode + ", yCreatedBy="
				+ yCreatedBy + ", yCreatedDateAndTime=" + yCreatedDateAndTime + ", zModifiedBy=" + zModifiedBy
				+ ", zModifiedDateAndTime=" + zModifiedDateAndTime + "]";
	}
}
