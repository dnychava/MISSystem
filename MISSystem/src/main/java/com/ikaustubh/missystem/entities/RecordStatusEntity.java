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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;

/**
 * This class has five type of record status.<BR><BR>
 * 1. Pending <BR>
 * 2. Submitted <BR>
 * 3. Approved <BR> 
 * 4. Rejected <BR> 
 * 5. Save 
 * 
 * @author Dnyaneshwar Chavan
 * @since 25-Aug-2019
 *
 */

@Entity
@Table(name = "RECORD_STATUS")
@Scope("global-session")
public class RecordStatusEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "RECORD_STATUS_RID", length = 10, nullable = false)
	private long rid;

	/**
	 * These will be like. 1. Pending, 2. Submitted, 3. Approved, 4. Rejected, 5.Save
	 */
	@Column(name = "RECORD_STATUS_NAME", unique = true, nullable = false, length = 100)
	private String name;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "recordStatusEntity", cascade = CascadeType.ALL)
	private List<ExpenditureEntity> expenditureEntities = new ArrayList<ExpenditureEntity>();
	
	@Column(name = "RECORD_STATUS_CREATED_BY", nullable = true, length = 15)
	private String yCreatedBy;

	@Column(name = "RECORD_STATUS_CREATED_TIMESTAMP", nullable = true)
	private LocalDateTime yCreatedDateAndTime;

	@Column(name = "RECORD_STATUS_MODIFIED_BY", nullable = true, length = 15)
	private String zModifiedBy;

	@Column(name = "RECORD_STATUS_MODIFIED_TIMESTAMP", nullable = true)
	private LocalDateTime zModifiedDateAndTime;

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
		return "RecordStatusEntity [rid=" + rid + ", name=" + name + ", yCreatedBy=" + yCreatedBy
				+ ", yCreatedDateAndTime=" + yCreatedDateAndTime + ", zModifiedBy=" + zModifiedBy
				+ ", zModifiedDateAndTime=" + zModifiedDateAndTime + "]";
	}
}
