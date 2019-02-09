package com.ikaustubh.missystem.entities;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author Dnyaneshwar
 *
 */

@Entity
@Table(name = "APPROVED_REJECTED")
public class ApprovedRejectedEntity {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "APPROVED_REJECTED_RID", length = 10, nullable = false)
	private long rid;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "APPROVED_REJECTED__EXPENDITURE_RID", nullable = false, referencedColumnName = "EXPENDITURE_RID")
	private ExpenditureEntity expenditureEntity;

	@Column(name = "APPROVED_REJECTED_BY", nullable = false, length = 15)
	private String approvedRejectedBy;

	@Column(name = "APPROVED_REJECTED_DATE", nullable = false)
	private LocalDateTime approvedRejectedDate;

	@Column(name = "APPROVED_REJECTED_COMMENT", nullable = false, length = 500)
	private String comment;

	@Column(name = "APPROVED_REJECTED_CREATED_BY", nullable = true, length = 15)
	private String yCreatedBy;

	@Column(name = "APPROVED_REJECTED_CREATED_TIMESTAMP", nullable = true)
	private LocalDateTime yCreatedDateAndTime;

	@Column(name = "APPROVED_REJECTED_MODIFIED_BY", nullable = true, length = 15)
	private String zModifiedBy;

	@Column(name = "APPROVED_REJECTED_MODIFIED_TIMESTAMP", nullable = true)
	private LocalDateTime zModifiedDateAndTime;

	/**
	 * @return the expenditureEntity
	 */
	public ExpenditureEntity getExpenditureEntity() {
		return expenditureEntity;
	}

	/**
	 * @param expenditureEntity the expenditureEntity to set
	 */
	public void setExpenditureEntity(ExpenditureEntity expenditureEntity) {
		this.expenditureEntity = expenditureEntity;
	}

	/**
	 * @return the approvedRejectedBy
	 */
	public String getApprovedRejectedBy() {
		return approvedRejectedBy;
	}

	/**
	 * @param approvedRejectedBy the approvedRejectedBy to set
	 */
	public void setApprovedRejectedBy(String approvedRejectedBy) {
		this.approvedRejectedBy = approvedRejectedBy;
	}

	/**
	 * @return the approvedRejectedDate
	 */
	public LocalDateTime getApprovedRejectedDate() {
		return approvedRejectedDate;
	}

	/**
	 * @param approvedRejectedDate the approvedRejectedDate to set
	 */
	public void setApprovedRejectedDate(LocalDateTime approvedRejectedDate) {
		this.approvedRejectedDate = approvedRejectedDate;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
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
		return "ApprovedRejectedEntity [rid=" + rid + ", approvedRejectedBy=" + approvedRejectedBy
				+ ", approvedRejectedDate=" + approvedRejectedDate + ", comment=" + comment + ", yCreatedBy="
				+ yCreatedBy + ", yCreatedDateAndTime=" + yCreatedDateAndTime + ", zModifiedBy=" + zModifiedBy
				+ ", zModifiedDateAndTime=" + zModifiedDateAndTime + "]";
	}
	
	
}
