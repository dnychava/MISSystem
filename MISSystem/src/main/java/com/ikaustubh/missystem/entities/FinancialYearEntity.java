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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 
 * @author Dnyaneshwar
 *
 */

@Entity
@Table(name = "FINANCIAL_YEAR", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "FINANCIAL_YEAR_RID", "FINANCIAL_YEAR" }, name = "UK__FINANCIAL_YEAR") })
//@Scope("global-session")
public class FinancialYearEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "FINANCIAL_YEAR_RID", length = 10, nullable = false)
	protected long id;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "financialYearEntity", cascade = CascadeType.ALL)
	private List<MainProgramHeadEntity> mainProgramHeadEntities = new ArrayList<MainProgramHeadEntity>();

	@Column(name = "FINANCIAL_YEAR", unique = true, nullable = false, length = 10)
	private String year;

	@Column(name = "FINANCIAL_YEAR_CREATED_BY", nullable = true, length = 15)
	private String yCreatedBy;

	@Column(name = "FINANCIAL_YEAR_CREATED_TIMESTAMP", nullable = true)
	private LocalDateTime yCreatedDateAndTime;

	@Column(name = "FINANCIAL_YEAR_MODIFIED_BY", nullable = true, length = 15)
	private String zModifiedBy;

	@Column(name = "FINANCIAL_YEAR_MODIFIED_TIMESTAMP", nullable = true)
	private LocalDateTime zModifiedDateAndTime;

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
	 * @return the mainProgramHeadEntities
	 */
	public List<MainProgramHeadEntity> getMainProgramHeadEntities() {
		return mainProgramHeadEntities;
	}

	/**
	 * @param mainProgramHeadEntities the mainProgramHeadEntities to set
	 */
	public void setMainProgramHeadEntities(List<MainProgramHeadEntity> mainProgramHeadEntities) {
		this.mainProgramHeadEntities = mainProgramHeadEntities;
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
		return "FinancialYearEntity [id=" + id + ", year=" + year + ", yCreatedBy=" + yCreatedBy
				+ ", yCreatedDateAndTime=" + yCreatedDateAndTime + ", zModifiedBy=" + zModifiedBy
				+ ", zModifiedDateAndTime=" + zModifiedDateAndTime + "]";
	}	
}
