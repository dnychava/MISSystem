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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;

/**
 * 
 * @author Dnyaneshwar
 *
 */

@Entity
@Table(name = "UNIT_DTL")
//@Scope("global-session")
public class UnitDtlEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "UNIT_DTL_RID", length = 10, nullable = false)
	private long rid;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "UNIT_DTL__UNIT_RID", nullable = false, referencedColumnName = "UNIT_RID")
	private UnitEntity unitEntityParent;

	@Column(name = "UNIT_DTL_NAME", unique = true, nullable = false, length = 100)
	private String name;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "UNIT_DTL__ADDRESS_RID", nullable = false, referencedColumnName = "ADDRESS_RID")
	private AddressEntity addressEntity;

	@Column(name = "UNIT_DTL_CREATED_BY", nullable = true, length = 15)
	private String yCreatedBy;

	@Column(name = "UNIT_DTL_CREATED_TIMESTAMP", nullable = true)
	private LocalDateTime yCreatedDateAndTime;

	@Column(name = "UNIT_DTL_MODIFIED_BY", nullable = true, length = 15)
	private String zModifiedBy;

	@Column(name = "UNIT_DTL_MODIFIED_TIMESTAMP", nullable = true)
	private LocalDateTime zModifiedDateAndTime;

	/**
	 * @return the unitEntity
	 */
	public UnitEntity getUnitEntity() {
		return unitEntityParent;
	}

	/**
	 * @param unitEntity the unitEntity to set
	 */
	public void setUnitEntity(UnitEntity unitEntity) {
		this.unitEntityParent = unitEntity;
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
	 * @return the addressEntity
	 */
	public AddressEntity getAddressEntity() {
		return addressEntity;
	}

	/**
	 * @param addressEntity the addressEntity to set
	 */
	public void setAddressEntity(AddressEntity addressEntity) {
		this.addressEntity = addressEntity;
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
		return "UnitDtlEntity [rid=" + rid + ", name=" + name + ", yCreatedBy=" + yCreatedBy + ", yCreatedDateAndTime="
				+ yCreatedDateAndTime + ", zModifiedBy=" + zModifiedBy + ", zModifiedDateAndTime="
				+ zModifiedDateAndTime + "]";
	}
}
