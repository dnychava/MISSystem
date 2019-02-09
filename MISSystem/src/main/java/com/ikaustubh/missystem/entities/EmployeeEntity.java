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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author Dnyaneshwar
 *
 */

@Entity
@Table(name = "EMP_TBL")
public class EmployeeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EMP_TBL_EMP_RID", length = 10, nullable = false)
	private long rid;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "EMP_TBL__USER_INFO_RID", nullable = false, referencedColumnName = "USER_INFO_RID")
	private UserInfoEntity useInfoEntity;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "EMP_TBL__DOC_TBL_RID", nullable = true, referencedColumnName = "DOC_TBL_RID")
	private DocumentEntity documentEntity;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "EMP_TBL__ADDRESS_RID", nullable = true, referencedColumnName = "ADDRESS_RID")
	private AddressEntity addressEntity;

	@Column(name = "EMP_TBL_FIRST_NAME", nullable = false, length = 20)
	private String firstName;

	@Column(name = "EMP_TBL_MIDLE_NAME", nullable = false, length = 20)
	private String midleName;

	@Column(name = "EMP_TBL_LAST_NAME", nullable = false, length = 20)
	private String lastName;

	@Column(name = "EMP_TBL_GENDER", nullable = false, length = 10)
	private String gender;

	@Column(name = "EMP_TBL_DOB", nullable = false)
	private LocalDateTime dateOfBirth;

	@Column(name = "EMP_TBL_EMAIL_ID", nullable = true, length = 100)
	private String emailId;

	@Column(name = "EMP_TBL_CREATED_BY", nullable = true, length = 15)
	private String yCreatedBy;

	@Column(name = "EMP_TBL_CREATED_TIMESTAMP", nullable = true)
	private LocalDateTime yCreatedDateAndTime;

	@Column(name = "EMP_TBL_MODIFIED_BY", nullable = true, length = 15)
	private String zModifiedBy;

	@Column(name = "EMP_TBL_MODIFIED_TIMESTAMP", nullable = true)
	private LocalDateTime zModifiedDateAndTime;

	/**
	 * @return the useInfoEntity
	 */
	public UserInfoEntity getUseInfoEntity() {
		return useInfoEntity;
	}

	/**
	 * @param useInfoEntity the useInfoEntity to set
	 */
	public void setUseInfoEntity(UserInfoEntity useInfoEntity) {
		this.useInfoEntity = useInfoEntity;
	}

	/**
	 * @return the documentEntity
	 */
	public DocumentEntity getDocumentEntity() {
		return documentEntity;
	}

	/**
	 * @param documentEntity the documentEntity to set
	 */
	public void setDocumentEntity(DocumentEntity documentEntity) {
		this.documentEntity = documentEntity;
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
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the midleName
	 */
	public String getMidleName() {
		return midleName;
	}

	/**
	 * @param midleName the midleName to set
	 */
	public void setMidleName(String midleName) {
		this.midleName = midleName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the dateOfBirth
	 */
	public LocalDateTime getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(LocalDateTime dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
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
		return "EmployeeEntity [rid=" + rid + ", firstName=" + firstName + ", midleName=" + midleName + ", lastName="
				+ lastName + ", gender=" + gender + ", dateOfBirth=" + dateOfBirth + ", emailId=" + emailId
				+ ", yCreatedBy=" + yCreatedBy + ", yCreatedDateAndTime=" + yCreatedDateAndTime + ", zModifiedBy="
				+ zModifiedBy + ", zModifiedDateAndTime=" + zModifiedDateAndTime + "]";
	}
}
