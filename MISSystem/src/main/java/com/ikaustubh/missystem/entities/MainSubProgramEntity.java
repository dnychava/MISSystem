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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 * @author Dnyaneshwar
 *
 */

@Entity
@Table(name = "MAIN_SUB_PROGRAM", uniqueConstraints = { @UniqueConstraint(columnNames = { "MAIN_SUB_PROGRAM_NAME",
		"MAIN_SUB_PROGRAM_NEW_CODE", "MAIN_SUB_PROGRAM_OLD_CODE" }, name = "UK__MAIN_SUB_PROGRAM__All_MAIN_FIELDS") })
public class MainSubProgramEntity extends AbstractMainProgramEntity implements Comparable<MainSubProgramEntity> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MAIN_SUB_PROGRAM_RID", length = 10, nullable = false)
	protected long rid;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "MAIN_SUB_PROGRAM__MAIN_PROGRAM_RID", nullable = false, referencedColumnName = "MAIN_PROGRAM_RID")
	protected MainProgramEntity mainProgramEntity;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mainSubProgramEntity", cascade = CascadeType.ALL)
	private Set<MainActivityEntity> mainActivityEntity = new HashSet<MainActivityEntity>();

	@Column(name = "MAIN_SUB_PROGRAM_NAME", nullable = false, length = 200)
	protected String name;

	@Column(name = "MAIN_SUB_PROGRAM_NEW_CODE", nullable = false, length = 50)
	protected String newCode;

	@Column(name = "MAIN_SUB_PROGRAM_OLD_CODE", nullable = false, length = 50)
	protected String oldCode;

	@Column(name = "MAIN_SUB_PROGRAM_CREATED_BY", nullable = true, length = 15)
	private String yCreatedBy;

	@Column(name = "MAIN_SUB_PROGRAM_CREATED_TIMESTAMP", nullable = true)
	private LocalDateTime yCreatedDateAndTime;

	@Column(name = "MAIN_SUB_PROGRAM_MODIFIED_BY", nullable = true, length = 15)
	private String zModifiedBy;

	@Column(name = "MAIN_SUB_PROGRAM_MODIFIED_TIMESTAMP", nullable = true)
	private LocalDateTime zModifiedDateAndTime;

	/**
	 * @return the mainProgramEntity
	 */
	public MainProgramEntity getMainProgramEntity() {
		return mainProgramEntity;
	}

	/**
	 * @param mainProgramEntity the mainProgramEntity to set
	 */
	public void setMainProgramEntity(MainProgramEntity mainProgramEntity) {
		this.mainProgramEntity = mainProgramEntity;
	}

	/**
	 * @return the mainActivityEntity
	 */
	public Set<MainActivityEntity> getMainActivityEntity() {
		return mainActivityEntity;
	}

	/**
	 * @param mainActivityEntity the mainActivityEntity to set
	 */
	public void setMainActivityEntity(Set<MainActivityEntity> mainActivityEntity) {
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
		return "MainSubProgramEntity [rid=" + rid + ", name=" + name + ", newCode=" + newCode + ", oldCode=" + oldCode
				+ ", yCreatedBy=" + yCreatedBy + ", yCreatedDateAndTime=" + yCreatedDateAndTime + ", zModifiedBy="
				+ zModifiedBy + ", zModifiedDateAndTime=" + zModifiedDateAndTime + "]";
	}

	@Override
	public int compareTo(MainSubProgramEntity obj) {
		return this.newCode.compareTo(obj.getNewCode());
	}
}
