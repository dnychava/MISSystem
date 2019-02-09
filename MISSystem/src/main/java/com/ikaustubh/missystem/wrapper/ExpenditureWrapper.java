package com.ikaustubh.missystem.wrapper;

import java.math.BigDecimal;

public class ExpenditureWrapper {

	private BigDecimal progressiveAmtInLakh = new BigDecimal(0);
	
	private BigDecimal budgetAmtInLakh = new BigDecimal(0);

	/**
	 * @return the progressiveAmtInLakh
	 */
	public BigDecimal getProgressiveAmtInLakh() {
		return progressiveAmtInLakh;
	}

	/**
	 * @param progressiveAmtInLakh the progressiveAmtInLakh to set
	 */
	public void setProgressiveAmtInLakh(BigDecimal progressiveAmtInLakh) {
		this.progressiveAmtInLakh = progressiveAmtInLakh;
	}

	/**
	 * @return the budgetAmtInLakh
	 */
	public BigDecimal getBudgetAmtInLakh() {
		return budgetAmtInLakh;
	}

	/**
	 * @param budgetAmtInLakh the budgetAmtInLakh to set
	 */
	public void setBudgetAmtInLakh(BigDecimal budgetAmtInLakh) {
		this.budgetAmtInLakh = budgetAmtInLakh;
	}
	
}
