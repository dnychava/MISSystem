package com.ikaustubh.missystem.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author Dnyaneshwar
 *
 */

@Entity
@Table(name = "BUDGET_DISTRIBUTE", indexes = {
		@Index(columnList = "BUDGET_DISTRIBUTE__UNIT_RID, BUDGET_DISTRIBUTE__ACTIVITY_RID, BUDGET_DISTRIBUTE_YEAR", name="distribute_index") })
public class BudgetDistributeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BUDGET_DISTRIBUTE_RID", length = 10, nullable = false)
	protected long rid;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "BUDGET_DISTRIBUTE__UNIT_RID", nullable = false, referencedColumnName = "UNIT_RID")
	private UnitEntity unitEntity;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "BUDGET_DISTRIBUTE__ACTIVITY_RID", nullable = true, referencedColumnName = "ACTIVITY_RID")
	private ActivityEntity activityEntity;

	@Column(name = "BUDGET_DISTRIBUTE_YEAR", nullable = false, length = 10)
	private String year;

	@Column(name = "BUDGET_DISTRIBUTE_AMT", nullable = false, precision = 19, scale = 2)
	private BigDecimal amt = new BigDecimal("0.00");

	@Column(name = "BUDGET_DISTRIBUTE_AMT_IN_LAKH", nullable = false, precision = 19, scale = 2)
	private BigDecimal amtInLakh = new BigDecimal("0.00");

	@Column(name = "BUDGET_DISTRIBUTE_CREATED_BY", nullable = true, length = 15)
	private String yCreatedBy;

	@Column(name = "BUDGET_DISTRIBUTE_CREATED_TIMESTAMP", nullable = true)
	private LocalDateTime yCreatedDateAndTime;

	@Column(name = "BUDGET_DISTRIBUTE_MODIFIED_BY", nullable = true, length = 15)
	private String zModifiedBy;

	@Column(name = "BUDGET_DISTRIBUTE_MODIFIED_TIMESTAMP", nullable = true)
	private LocalDateTime zModifiedDateAndTime;

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
	 * @return the activityEntity
	 */
	public ActivityEntity getActivityEntity() {
		return activityEntity;
	}

	/**
	 * @param activityEntity the activityEntity to set
	 */
	public void setActivityEntity(ActivityEntity activityEntity) {
		this.activityEntity = activityEntity;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the amt
	 */
	public BigDecimal getAmt() {
		return amt;
	}

	/**
	 * @param amt the amt to set
	 */
	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	/**
	 * @return the amtInLakh
	 */
	public BigDecimal getAmtInLakh() {
		return amtInLakh;
	}

	/**
	 * @param amtInLakh the amtInLakh to set
	 */
	public void setAmtInLakh(BigDecimal amtInLakh) {
		this.amtInLakh = amtInLakh;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BudgetDistributeEntity [rid=" + rid + ", year=" + year + ", amt=" + amt + ", amtInLakh=" + amtInLakh
				+ ", yCreatedBy=" + yCreatedBy + ", yCreatedDateAndTime=" + yCreatedDateAndTime + ", zModifiedBy="
				+ zModifiedBy + ", zModifiedDateAndTime=" + zModifiedDateAndTime + "]";
	}

}
