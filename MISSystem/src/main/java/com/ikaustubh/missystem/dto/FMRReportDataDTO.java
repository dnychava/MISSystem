package com.ikaustubh.missystem.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 
 * @author Dnyaneshwar Chavan
 * @since 17-Nov-2018
 *
 */
@Component
@Data
public class FMRReportDataDTO {
	
	private String newFMRCode;
	private String oldFMRCode;
	private String name;
	private BigDecimal totBudgetAmtInLakh = new BigDecimal(0.00);
	private BigDecimal totExpenditureAmtInLakh = new BigDecimal(0.00);
	private String bgColor;
	
	public FMRReportDataDTO() {
	}

	/**
	 * @return the newFMRCode
	 */
	public String getNewFMRCode() {
		return newFMRCode;
	}

	/**
	 * @param newFMRCode the newFMRCode to set
	 */
	public void setNewFMRCode(String newFMRCode) {
		this.newFMRCode = newFMRCode;
	}

	/**
	 * @return the oldFMRCode
	 */
	public String getOldFMRCode() {
		return oldFMRCode;
	}

	/**
	 * @param oldFMRCode the oldFMRCode to set
	 */
	public void setOldFMRCode(String oldFMRCode) {
		this.oldFMRCode = oldFMRCode;
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
	 * @return the totBudgetAmtInLakh
	 */
	public BigDecimal getTotBudgetAmtInLakh() {
		return totBudgetAmtInLakh;
	}

	/**
	 * @param totBudgetAmtInLakh the totBudgetAmtInLakh to set
	 */
	public void setTotBudgetAmtInLakh(BigDecimal totBudgetAmtInLakh) {
		this.totBudgetAmtInLakh = totBudgetAmtInLakh;
	}

	/**
	 * @return the totExpenditureAmtInLakh
	 */
	public BigDecimal getTotExpenditureAmtInLakh() {
		return totExpenditureAmtInLakh;
	}

	/**
	 * @param totExpenditureAmtInLakh the totExpenditureAmtInLakh to set
	 */
	public void setTotExpenditureAmtInLakh(BigDecimal totExpenditureAmtInLakh) {
		this.totExpenditureAmtInLakh = totExpenditureAmtInLakh;
	}

	/**
	 * @return the bgColor
	 */
	public String getBgColor() {
		return bgColor;
	}

	/**
	 * @param bgColor the bgColor to set
	 */
	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}
}
