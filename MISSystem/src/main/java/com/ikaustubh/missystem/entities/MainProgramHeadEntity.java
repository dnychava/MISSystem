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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 * @author Dnyaneshwar
 *
 */

@Entity
@Table(name = "MAIN_PROGRAM_HEAD", uniqueConstraints = { @UniqueConstraint(columnNames = { "MAIN_PROGRAM_HEAD_YEAR",
		"MAIN_PROGRAM_HEAD_NAME", "MAIN_PROGRAM_HEAD_CODE" }, name = "UK__MAIN_PROGRAM_HEAD__All_MAIN_FIELDS") })
//@Scope("global-session")
public class MainProgramHeadEntity extends AbstractMainProgramEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MAIN_PROGRAM_HEAD_RID", length = 10, nullable = false)
	protected long id;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mainProgramHeadEntity", cascade = CascadeType.ALL)
	private Set<MainProgramEntity> mainProgramEntity = new HashSet<MainProgramEntity>();

	@Column(name = "MAIN_PROGRAM_HEAD_YEAR", unique = true, nullable = false, length = 10)
	private String year;

	@Column(name = "MAIN_PROGRAM_HEAD_NAME", unique = true, nullable = false, length = 100)
	protected String name;

	@Column(name = "MAIN_PROGRAM_HEAD_CODE", unique = true, nullable = false, length = 20)
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
	 * @return the mainProgramEntity
	 */
	public Set<MainProgramEntity> getMainProgramEntity() {
		return mainProgramEntity;
	}

	/**
	 * @param mainProgramEntity the mainProgramEntity to set
	 */
	public void setMainProgramEntity(Set<MainProgramEntity> mainProgramEntity) {
		this.mainProgramEntity = mainProgramEntity;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MainProgramHeadEntity [rid=" + id + ", year=" + year + ", name=" + name + ", newCode=" + newCode
				+ ", yCreatedBy=" + yCreatedBy + ", yCreatedDateAndTime=" + yCreatedDateAndTime + ", zModifiedBy="
				+ zModifiedBy + ", zModifiedDateAndTime=" + zModifiedDateAndTime + "]";
	}
}
