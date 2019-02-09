package com.ikaustubh.missystem.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 
 * @author Dnyaneshwar
 * @since 7-Nov-2018 
 */
public class ExpenditureDTO implements Comparable<ExpenditureDTO> {

	private long expenditureRid;
	
	private String year;
	
	private String reportingMonthStr;

	private String transactionMode;
	
	private long unitRid;
	
	private String unitName;
	
	private long mainSubActivityRid;
	
	private String newCode;
	
	private String oldCode;
	
	private String activityName;
	
	private BigDecimal budgetAmtInLakh;
	
	private long expenditureDtlRid;
	
	private BigDecimal prograsiveAmtInLakh;
	
	private BigDecimal expenditureAmtInLakh;

	/**
	 * @return the expenditureRid
	 */
	public long getExpenditureRid() {
		return expenditureRid;
	}

	/**
	 * @param expenditureRid the expenditureRid to set
	 */
	public void setExpenditureRid(long expenditureRid) {
		this.expenditureRid = expenditureRid;
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
	 * @return the reportingMonthStr
	 */
	public String getReportingMonthStr() {
		return reportingMonthStr;
	}

	/**
	 * @param reportingMonth the reportingMonth to set
	 */
	public void setReportingMonthStr(String reportingMonthStr) {
		this.reportingMonthStr = reportingMonthStr;
	}

	/**
	 * @return the transactionMode
	 */
	public String getTransactionMode() {
		return transactionMode;
	}

	/**
	 * @param transactionMode the transactionMode to set
	 */
	public void setTransactionMode(String transactionMode) {
		this.transactionMode = transactionMode;
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
	 * @return the unitName
	 */
	public String getUnitName() {
		return unitName;
	}

	/**
	 * @param unitName the unitName to set
	 */
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	/**
	 * @return the mainSubActivityRid
	 */
	public long getMainSubActivityRid() {
		return mainSubActivityRid;
	}

	/**
	 * @param mainSubActivityRid the mainSubActivityRid to set
	 */
	public void setMainSubActivityRid(long mainSubActivityRid) {
		this.mainSubActivityRid = mainSubActivityRid;
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
	 * @return the activityName
	 */
	public String getActivityName() {
		return activityName;
	}

	/**
	 * @param activityName the activityName to set
	 */
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	/**
	 * @return the budgetAmt
	 */
	public BigDecimal getBudgetAmtInLakh() {
		return budgetAmtInLakh;
	}

	/**
	 * @param budgetAmt the budgetAmt to set
	 */
	public void setBudgetAmtInLakh(BigDecimal budgetAmt) {
		this.budgetAmtInLakh = budgetAmt;
	}

	/**
	 * @return the expenditureDtlRid
	 */
	public long getExpenditureDtlRid() {
		return expenditureDtlRid;
	}

	/**
	 * @param expenditureDtlRid the expenditureDtlRid to set
	 */
	public void setExpenditureDtlRid(long expenditureDtlRid) {
		this.expenditureDtlRid = expenditureDtlRid;
	}

	/**
	 * @return the prograsiveAmtInLakh
	 */
	public BigDecimal getPrograsiveAmtInLakh() {
		return prograsiveAmtInLakh;
	}

	/**
	 * @param prograsiveAmtInLakh the prograsiveAmtInLakh to set
	 */
	public void setPrograsiveAmtInLakh(BigDecimal prograsiveAmtInLakh) {
		this.prograsiveAmtInLakh = prograsiveAmtInLakh;
	}

	/**
	 * @return the expenditureAmtInLakh
	 */
	public BigDecimal getExpenditureAmtInLakh() {
		return expenditureAmtInLakh;
	}

	/**
	 * @param expenditureAmtInLakh the expenditureAmtInLakh to set
	 */
	public void setExpenditureAmtInLakh(BigDecimal expenditureAmtInLakh) {
		this.expenditureAmtInLakh = expenditureAmtInLakh;
	}

	@Override
	public int compareTo(ExpenditureDTO obj) {
		
		return this.newCode.compareTo(obj.getNewCode());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ExpenditureDTO [expenditureRid=" + expenditureRid + ", year=" + year + ", reportingMonthStr="
				+ reportingMonthStr + ", transactionMode=" + transactionMode + ", unitRid=" + unitRid + ", unitName="
				+ unitName + ", mainSubActivityRid=" + mainSubActivityRid + ", newCode=" + newCode + ", oldCode="
				+ oldCode + ", activityName=" + activityName + ", budgetAmtInLakh=" + budgetAmtInLakh
				+ ", expenditureDtlRid=" + expenditureDtlRid + ", prograsiveAmtInLakh=" + prograsiveAmtInLakh
				+ ", expenditureAmtInLakh=" + expenditureAmtInLakh + "]";
	}	
}
