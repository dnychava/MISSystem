package com.ikaustubh.missystem.entities;

import java.io.Serializable;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author Dnyaneshwar
 *
 */

@Entity
@Table(name = "HO")
@Scope("global-session")
public class HOEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6146091498609948558L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "HO_RID", length = 10, nullable = false)
	private long rid;

	@Column(name = "HO_NAME", unique = true, nullable = false, length = 50)
	private String name;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "HO_ADDRESS__ADDRESS_ID", nullable = false, referencedColumnName = "ADDRESS_RID")
	private AddressEntity addressEntity;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "hoEntity", cascade = CascadeType.ALL)
	private Set<UnitEntity> unitEntity = new HashSet<UnitEntity>();

	@Column(name = "HO_CREATED_BY", nullable = true, length = 15)
	private String yCreatedBy;

	@Column(name = "HO_CREATED_TIMESTAMP", nullable = true)
	private LocalDateTime yCreatedDateAndTime;

	@Column(name = "HO_MODIFIED_BY", nullable = true, length = 15)
	private String zModifiedBy;

	@Column(name = "HO_MODIFIED_TIMESTAMP", nullable = true)
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
	 * @return the unitEntity
	 */
	public Set<UnitEntity> getUnitEntity() {
		return unitEntity;
	}

	/**
	 * @param unitEntity the unitEntity to set
	 */
	public void setUnitEntity(Set<UnitEntity> unitEntity) {
		this.unitEntity = unitEntity;
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
		return "HOEntity [rid=" + rid + ", name=" + name + ", yCreatedBy=" + yCreatedBy
				+ ", yCreatedDateAndTime=" + yCreatedDateAndTime + ", zModifiedBy=" + zModifiedBy
				+ ", zModifiedDateAndTime=" + zModifiedDateAndTime + "]";
	}
}
