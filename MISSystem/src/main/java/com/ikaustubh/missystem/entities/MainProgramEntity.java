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
@Table(name = "MAIN_PROGRAM", uniqueConstraints = { @UniqueConstraint(columnNames = { "MAIN_PROGRAM_NAME",
		"MAIN_PROGRAM_NEW_CODE", "MAIN_PROGRAM_OLD_CODE" }, name = "UK__MAIN_PROGRAM__All_MAIN_FIELDS") })
public class MainProgramEntity extends AbstractMainProgramEntity implements Comparable<MainProgramEntity> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MAIN_PROGRAM_RID", length = 10, nullable = false)
	protected long rid;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "MAIN_PROGRAM__MAIN_PROGRAM_HEAD_RID", nullable = false, referencedColumnName = "MAIN_PROGRAM_HEAD_RID")
	protected MainProgramHeadEntity mainProgramHeadEntity;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mainProgramEntity", cascade = CascadeType.ALL)
	private Set<MainSubProgramEntity> mainSubProgramEntity = new HashSet<MainSubProgramEntity>();

	@Column(name = "MAIN_PROGRAM_NAME", nullable = false, length = 200)
	protected String name;

	@Column(name = "MAIN_PROGRAM_NEW_CODE", nullable = false, length = 50)
	protected String newCode;

	@Column(name = "MAIN_PROGRAM_OLD_CODE", nullable = false, length = 50)
	protected String oldCode;

	@Column(name = "MAIN_PROGRAM_CREATED_BY", nullable = true, length = 15)
	private String yCreatedBy;

	@Column(name = "MAIN_PROGRAM_CREATED_TIMESTAMP", nullable = true)
	private LocalDateTime yCreatedDateAndTime;

	@Column(name = "MAIN_PROGRAM_MODIFIED_BY", nullable = true, length = 15)
	private String zModifiedBy;

	@Column(name = "MAIN_PROGRAM_MODIFIED_TIMESTAMP", nullable = true)
	private LocalDateTime zModifiedDateAndTime;

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
	 * @return the mainSubProgramEntity
	 */
	public Set<MainSubProgramEntity> getMainSubProgramEntity() {
		return mainSubProgramEntity;
	}

	/**
	 * @param mainSubProgramEntity the mainSubProgramEntity to set
	 */
	public void setMainSubProgramEntity(Set<MainSubProgramEntity> mainSubProgramEntity) {
		this.mainSubProgramEntity = mainSubProgramEntity;
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
		return "MainProgramEntity [rid=" + rid + ", name=" + name + ", newCode=" + newCode + ", oldCode=" + oldCode
				+ ", yCreatedBy=" + yCreatedBy + ", yCreatedDateAndTime=" + yCreatedDateAndTime + ", zModifiedBy="
				+ zModifiedBy + ", zModifiedDateAndTime=" + zModifiedDateAndTime + "]";
	}

	@Override
	public int compareTo(MainProgramEntity obj) {
		return this.newCode.compareTo(obj.getNewCode());
	}
}
