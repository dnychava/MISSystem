package com.ikaustubh.missystem.entities;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 
 * @author Dnyaneshwar
 *
 */

@Entity
@Table(name = "DOC_TBL")
public class DocumentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DOC_TBL_RID", length = 10, nullable = false)
	private long rid;

	@Column(name = "DOC_TBL_FILE_NAME", nullable = false, length = 200)
	private String fileName;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "DOC_TBL_CONTENT", nullable = false)
	private byte[] content = "".getBytes();

	@Column(name = "DOC_TBL_DESCRIPTION", nullable = true, length = 225)
	private String description;

	@Column(name = "DOC_TBL_CREATED_BY", nullable = true, length = 15)
	private String yCreatedBy;

	@Column(name = "DOC_TBL_CREATED_DATE", nullable = true)
	private Date yCreatedDate;

	@Column(name = "DOC_TBL_MODIFIED_BY", nullable = true, length = 15)
	private String zModifiedBy;

	@Column(name = "DOC_TBL_MODIFIED_DATE", nullable = true)
	private String zModifiedDate;

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the content
	 */
	public byte[] getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(byte[] content) {
		this.content = content;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return the yCreatedDate
	 */
	public Date getyCreatedDate() {
		return yCreatedDate;
	}

	/**
	 * @param yCreatedDate the yCreatedDate to set
	 */
	public void setyCreatedDate(Date yCreatedDate) {
		this.yCreatedDate = yCreatedDate;
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
	 * @return the zModifiedDate
	 */
	public String getzModifiedDate() {
		return zModifiedDate;
	}

	/**
	 * @param zModifiedDate the zModifiedDate to set
	 */
	public void setzModifiedDate(String zModifiedDate) {
		this.zModifiedDate = zModifiedDate;
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
		return "DocumentEntity [rid=" + rid + ", fileName=" + fileName + ", description=" + description
				+ ", yCreatedBy=" + yCreatedBy + ", yCreatedDate=" + yCreatedDate + ", zModifiedBy=" + zModifiedBy
				+ ", zModifiedDate=" + zModifiedDate + "]";
	}
	
	
}
