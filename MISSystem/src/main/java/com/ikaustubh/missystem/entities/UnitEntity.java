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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * 
 * @author Dnyaneshwar
 *
 */

@Entity
@Table(name = "UNIT")
//@Scope("global-session")
public class UnitEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "UNIT_RID", length = 10, nullable = false)
	private long rid;

	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL )
	@JoinColumn(name="UNIT__UNIT_TYPE_RID", nullable=false, referencedColumnName="UNIT_TYPE_RID")
	private UnitTypeEntity unitTypeEntity;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL )
	@JoinColumn(name="UNIT__HO_RID", nullable=false, referencedColumnName="HO_RID")
	private HOEntity hoEntity;

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "unitEntityParent", cascade = CascadeType.ALL)
	private List<UnitDtlEntity> unitDtlsEntity = new ArrayList<UnitDtlEntity>();
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "unitEntity", cascade = CascadeType.ALL)
	private List<UserInfoEntity> userInfoEntity = new ArrayList<UserInfoEntity>();

	@Column(name = "UNIT_CREATED_BY", nullable = true, length = 15)
	private String yCreatedBy;

	@Column(name = "UNIT_CREATED_TIMESTAMP", nullable = true)
	private LocalDateTime yCreatedDateAndTime;

	@Column(name = "UNIT_MODIFIED_BY", nullable = true, length = 15)
	private String zModifiedBy;

	@Column(name = "UNIT_MODIFIED_TIMESTAMP", nullable = true)
	private LocalDateTime zModifiedDateAndTime;
	
	/**
	 * @return the unitTypeEntity
	 */
	public UnitTypeEntity getUnitTypeEntity() {
		return unitTypeEntity;
	}

	/**
	 * @param unitTypeEntity the unitTypeEntity to set
	 */
	public void setUnitTypeEntity(UnitTypeEntity unitTypeEntity) {
		this.unitTypeEntity = unitTypeEntity;
	}

	/**
	 * @return the hoEntity
	 */
	public HOEntity getHoEntity() {
		return hoEntity;
	}

	/**
	 * @param hoEntity the hoEntity to set
	 */
	public void setHoEntity(HOEntity hoEntity) {
		this.hoEntity = hoEntity;
	}

	/**
	 * @return the unitDtlsEntity
	 */
	public List<UnitDtlEntity> getUnitDtlsEntity() {
		return unitDtlsEntity;
	}

	/**
	 * @param unitDtlsEntity the unitDtlsEntity to set
	 */
	public void setUnitDtlsEntity(List<UnitDtlEntity> unitDtlsEntity) {
		this.unitDtlsEntity = unitDtlsEntity;
	}

	/**
	 * @return the userInfoEntity
	 */
	public List<UserInfoEntity> getUserInfoEntity() {
		return userInfoEntity;
	}

	/**
	 * @param userInfoEntity the userInfoEntity to set
	 */
	public void setUserInfoEntity(List<UserInfoEntity> userInfoEntity) {
		this.userInfoEntity = userInfoEntity;
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
		return "UnitEntity [rid=" + rid + ", yCreatedBy=" + yCreatedBy + ", yCreatedDateAndTime=" + yCreatedDateAndTime
				+ ", zModifiedBy=" + zModifiedBy + ", zModifiedDateAndTime=" + zModifiedDateAndTime + "]";
	}
}
