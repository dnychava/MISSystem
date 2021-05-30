package com.ikaustubh.missystem.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class FMRReportFilterDTO {

	
	private long unitGroupRid;
	
	private long unitRid;
	
	private String year;
	
	private long mainProgramHeadRid;
	
	private int reportingMonth;
	
	public FMRReportFilterDTO() {
		
	}

	/**
	 * @return the unitGroupRid
	 */
	public long getUnitGroupRid() {
		return unitGroupRid;
	}

	/**
	 * @param unitGroupRid the unitGroupRid to set
	 */
	public void setUnitGroupRid(long unitGroupRid) {
		this.unitGroupRid = unitGroupRid;
	}

	/**
	 * @return the unitRid
	 */
	public long getUnitRid() {
		return unitRid;
	}

	/**
	 * @param unitRid the unitRid to set
	 */
	public void setUnitRid(long unitRid) {
		this.unitRid = unitRid;
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
	 * @return the mainProgramHeadRid
	 */
	public long getMainProgramHeadRid() {
		return mainProgramHeadRid;
	}

	/**
	 * @param mainProgramHeadRid the mainProgramHeadRid to set
	 */
	public void setMainProgramHeadRid(long mainProgramHeadRid) {
		this.mainProgramHeadRid = mainProgramHeadRid;
	}

	/**
	 * @return the reportingMonth
	 */
	public int getReportingMonth() {
		return reportingMonth;
	}

	/**
	 * @param reportingMonth the reportingMonth to set
	 */
	public void setReportingMonth(int reportingMonth) {
		this.reportingMonth = reportingMonth;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FMRReportFilterDTO [unitGroupRid=" + unitGroupRid + ", unitRid=" + unitRid + ", year=" + year
				+ ", mainProgramHeadRid=" + mainProgramHeadRid + ", reportingMonth=" + reportingMonth + "]";
	}
	
}
