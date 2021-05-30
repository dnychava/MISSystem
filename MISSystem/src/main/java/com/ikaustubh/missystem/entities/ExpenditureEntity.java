package com.ikaustubh.missystem.entities;

import java.math.BigDecimal;
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
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author Dnyaneshwar
 *
 */

@Entity
@Table(name = "EXPENDITURE", indexes = {
		@Index(columnList = " EXPENDITURE_RID, EXPENDITURE__UNIT_RID, "
				+ "EXPENDITURE__ACTIVITY_RID, EXPENDITURE__RECORD_STATUS_RID, EXPENDITURE_YEAR, EXPENDITURE_REPORTING_MONTH", 
				name = "expenditure_index") })
public class ExpenditureEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EXPENDITURE_RID", length = 10, nullable = false)
	protected long rid;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "EXPENDITURE__UNIT_RID", nullable = false, referencedColumnName = "UNIT_RID")
	private UnitEntity unitEntity;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "EXPENDITURE__ACTIVITY_RID", nullable = true, referencedColumnName = "ACTIVITY_RID")
	private ActivityEntity activityEntity;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "EXPENDITURE__RECORD_STATUS_RID", nullable = false, referencedColumnName = "RECORD_STATUS_RID")
	private RecordStatusEntity recordStatusEntity;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "expenditureEntity", cascade = CascadeType.ALL)
	private Set<ExpenditureDtlEntity> expenditureDtlEntity = new HashSet<ExpenditureDtlEntity>();

	@Column(name = "EXPENDITURE_YEAR", nullable = false, length = 10)
	private String year;

	@Column(name = "EXPENDITURE_REPORTING_MONTH", nullable = false)
	private int reportingMonth;

	@Column(name = "EXPENDITURE_TOT_AMT", nullable = false, precision = 19, scale = 2)
	private BigDecimal totAmt = new BigDecimal("0.00");

	@Column(name = "EXPENDITURE_TOT_AMT_IN_LAKH", nullable = false, precision = 19, scale = 2)
	private BigDecimal totAmtInLakh = new BigDecimal("0.00");

	@Column(name = "EXPENDITURE_CREATED_BY", nullable = true, length = 15)
	private String yCreatedBy;

	@Column(name = "EXPENDITURE_CREATED_TIMESTAMP", nullable = true)
	private LocalDateTime yCreatedDateAndTime;

	@Column(name = "EXPENDITURE_MODIFIED_BY", nullable = true, length = 15)
	private String zModifiedBy;

	@Column(name = "EXPENDITURE_MODIFIED_TIMESTAMP", nullable = true)
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
	 * @return the recordStatusEntity
	 */
	public RecordStatusEntity getRecordStatusEntity() {
		return recordStatusEntity;
	}

	/**
	 * @param recordStatusEntity the recordStatusEntity to set
	 */
	public void setRecordStatusEntity(RecordStatusEntity recordStatusEntity) {
		this.recordStatusEntity = recordStatusEntity;
	}

	/**
	 * @return the expenditureDtlEntity
	 */
	public Set<ExpenditureDtlEntity> getExpenditureDtlEntity() {
		return expenditureDtlEntity;
	}

	/**
	 * @param expenditureDtlEntity the expenditureDtlEntity to set
	 */
	public void setExpenditureDtlEntity(Set<ExpenditureDtlEntity> expenditureDtlEntity) {
		this.expenditureDtlEntity = expenditureDtlEntity;
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
	 * @return the reportingMonth
	 */
	public int getReportingMonth() {
		return reportingMonth;
	}

	/**
	 * @param reportingMonth the reportingMonth to set
	 */
	public void setReportingMonth(int reportingMonth) {
		this.reportingMonth = reportingMonth;
	}

	/**
	 * @return the totAmt
	 */
	public BigDecimal getTotAmt() {
		return totAmt;
	}

	/**
	 * @param totAmt the totAmt to set
	 */
	public void setTotAmt(BigDecimal totAmt) {
		this.totAmt = totAmt;
	}

	/**
	 * @return the totAmtInLakh
	 */
	public BigDecimal getTotAmtInLakh() {
		return totAmtInLakh;
	}

	/**
	 * @param totAmtInLakh the totAmtInLakh to set
	 */
	public void setTotAmtInLakh(BigDecimal totAmtInLakh) {
		this.totAmtInLakh = totAmtInLakh;
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
		return "ExpenditureEntity [rid=" + rid + ", year=" + year + ", reportingMonth=" + reportingMonth + ", totAmt="
				+ totAmt + ", totAmtInLakh=" + totAmtInLakh + ", yCreatedBy=" + yCreatedBy + ", yCreatedDateAndTime="
				+ yCreatedDateAndTime + ", zModifiedBy=" + zModifiedBy + ", zModifiedDateAndTime="
				+ zModifiedDateAndTime + "]";
	}
}
