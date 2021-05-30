package com.ikaustubh.missystem.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
 * This entity represent of two type roles.<BR>
 * 1. It self Main activity.<BR>
 * 2. It sub activity has main activity reference.<BR>
 * Eg. Self join, Emp and manager relationship. 
 * 
 * @author Dnyaneshwar Chavan
 * @since 25-Aug-2019
 */

@Entity
@Table(name = "ACTIVITY", uniqueConstraints = { @UniqueConstraint(columnNames = { "ACTIVITY_RID", "ACTIVITY_NAME",
		"ACTIVITY_NEW_CODE", "ACTIVITY_OLD_CODE" }, name = "UK__ACTIVITY__All_MAIN_FIELDS") } )
public class ActivityEntity implements Comparable<ActivityEntity>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ACTIVITY_RID", length = 10, nullable = false)
	private long rid;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ACTIVITY__MAIN_ACTIVITY_RID", nullable = true, referencedColumnName = "ACTIVITY_RID")
	private ActivityEntity mainActivity;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mainActivity", cascade = CascadeType.ALL)
	private List<ActivityEntity> mainActivityEntities = new ArrayList<ActivityEntity>();
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ACTIVITY__MAIN_PROGRAM_HEAD_RID", nullable = false, referencedColumnName = "MAIN_PROGRAM_HEAD_RID")
	protected MainProgramHeadEntity mainProgramHeadEntity;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "activityEntity", cascade = CascadeType.ALL)
	private List<BudgetDistributeEntity> budgetDistributeEntities = new ArrayList<BudgetDistributeEntity>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "activityEntity", cascade = CascadeType.ALL)
	private List<ExpenditureEntity> expenditureEntities = new ArrayList<ExpenditureEntity>();

	@Column(name = "ACTIVITY_NAME", nullable = false, length = 800)
	protected String name;

	@Column(name = "ACTIVITY_NEW_CODE", nullable = false, length = 50)
	protected String newCode;

	@Column(name = "ACTIVITY_OLD_CODE", nullable = false, length = 50)
	protected String oldCode;

	@Column(name = "ACTIVITY_CREATED_BY", nullable = true, length = 15)
	private String yCreatedBy;

	@Column(name = "ACTIVITY_CREATED_TIMESTAMP", nullable = true)
	private LocalDateTime yCreatedDateAndTime;

	@Column(name = "ACTIVITY_MODIFIED_BY", nullable = true, length = 15)
	private String zModifiedBy;

	@Column(name = "ACTIVITY_MODIFIED_TIMESTAMP", nullable = true)
	private LocalDateTime zModifiedDateAndTime;
	
	/**
	 * @return the rid
	 */
	public long getRid() {
		return rid;
	}

	/**
	 * @param rid the rid to set
	 */
	public void setRid(long rid) {
		this.rid = rid;
	}

	/**
	 * @return the mainActivity
	 */
	public ActivityEntity getMainActivity() {
		return mainActivity;
	}

	/**
	 * @param mainActivity the mainActivity to set
	 */
	public void setMainActivity(ActivityEntity mainActivity) {
		this.mainActivity = mainActivity;
	}
	
	/**
	 * @return the mainActivityEntities
	 */
	public List<ActivityEntity> getMainActivityEntities() {
		return mainActivityEntities;
	}

	/**
	 * @param mainActivityEntities the mainActivityEntities to set
	 */
	public void setMainActivityEntities(List<ActivityEntity> mainActivityEntities) {
		this.mainActivityEntities = mainActivityEntities;
	}

	/**
	 * @return the mainProgramHeadEntity
	 */
	public MainProgramHeadEntity getMainProgramHeadEntity() {
		return mainProgramHeadEntity;
	}

	/**
	 * @param mainProgramHeadEntity the mainProgramHeadEntity to set
	 */
	public void setMainProgramHeadEntity(MainProgramHeadEntity mainProgramHeadEntity) {
		this.mainProgramHeadEntity = mainProgramHeadEntity;
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
	 * @return the expenditureEntities
	 */
	public List<ExpenditureEntity> getExpenditureEntities() {
		return expenditureEntities;
	}

	/**
	 * @param expenditureEntities the expenditureEntities to set
	 */
	public void setExpenditureEntities(List<ExpenditureEntity> expenditureEntities) {
		this.expenditureEntities = expenditureEntities;
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
	 * @return the oldCode
	 */
	public String getOldCode() {
		return oldCode;
	}

	/**
	 * @param oldCode the oldCode to set
	 */
	public void setOldCode(String oldCode) {
		this.oldCode = oldCode;
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
	
	@Override
	public int compareTo(ActivityEntity obj) {
		return this.newCode.compareTo(obj.getNewCode());
		//return (int) (this.rid - obj.rid);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((newCode == null) ? 0 : newCode.hashCode());
		result = prime * result + (int) (rid ^ (rid >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActivityEntity other = (ActivityEntity) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (newCode == null) {
			if (other.newCode != null)
				return false;
		} else if (!newCode.equals(other.newCode))
			return false;
		if (rid != other.rid)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MainActivityEntityTest [rid=" + rid + ", newCode=" + newCode + ", oldCode=" + oldCode + "]";
	}

}
