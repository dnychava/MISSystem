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
import javax.persistence.UniqueConstraint;

/**
 * 
 * @author Dnyaneshwar
 *
 */

@Entity
@Table(name = "MAIN_SUB_ACTIVITY", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "MAIN_SUB_ACTIVITY_NAME", "MAIN_SUB_ACTIVITY_NEW_CODE",
				"MAIN_SUB_ACTIVITY_OLD_CODE" }, name = "UK__MAIN_SUB_ACTIVITY__All_MAIN_FIELDS") })
public class MainSubActivityEntity extends AbstractMainProgramEntity implements Comparable<MainSubActivityEntity> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MAIN_SUB_ACTIVITY_RID", length = 10, nullable = false)
	protected long rid;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "MAIN_SUB_ACTIVITY__MAIN_ACTIVITY_RID", nullable = false, referencedColumnName = "MAIN_ACTIVITY_RID")
	private MainActivityEntity mainActivityEntity;

	@Column(name = "MAIN_SUB_ACTIVITY_NAME", nullable = false, length = 200)
	protected String name;

	@Column(name = "MAIN_SUB_ACTIVITY_NEW_CODE", nullable = false, length = 50)
	protected String newCode;

	@Column(name = "MAIN_SUB_ACTIVITY_OLD_CODE", nullable = false, length = 50)
	protected String oldCode;

	@Column(name = "MAIN_SUB_ACTIVITY_CREATED_BY", nullable = true, length = 15)
	private String yCreatedBy;

	@Column(name = "MAIN_SUB_ACTIVITY_CREATED_TIMESTAMP", nullable = true)
	private LocalDateTime yCreatedDateAndTime;

	@Column(name = "MAIN_SUB_ACTIVITY_MODIFIED_BY", nullable = true, length = 15)
	private String zModifiedBy;

	@Column(name = "MAIN_SUB_ACTIVITY_MODIFIED_TIMESTAMP" + "", nullable = true)
	private LocalDateTime zModifiedDateAndTime;

	/**
	 * @return the mainActivityEntity
	 */
	public MainActivityEntity getMainActivityEntity() {
		return mainActivityEntity;
	}

	/**
	 * @param mainActivityEntity the mainActivityEntity to set
	 */
	public void setMainActivityEntity(MainActivityEntity mainActivityEntity) {
		this.mainActivityEntity = mainActivityEntity;
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
		return "MainSubActivityEntity [rid=" + rid + ", name=" + name + ", newCode=" + newCode + ", oldCode=" + oldCode
				+ ", yCreatedBy=" + yCreatedBy + ", yCreatedDateAndTime=" + yCreatedDateAndTime + ", zModifiedBy="
				+ zModifiedBy + ", zModifiedDateAndTime=" + zModifiedDateAndTime + "]";
	}

	@Override
	public int compareTo(MainSubActivityEntity obj) {
		return this.newCode.compareTo(obj.getNewCode());
	}
}
