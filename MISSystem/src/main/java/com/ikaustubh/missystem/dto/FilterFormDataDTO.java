package com.ikaustubh.missystem.dto;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.springframework.stereotype.Component;

/**
 * 
 * @author Dnyaneshwar Chavan
 * @since 08-09-2019
 */
@Component
public class FilterFormDataDTO {

	private LinkedList<FinancialYearDTO> financialYearList;
	private LinkedList<MonthDTO> monthList;
	private LinkedList<UnitDTO> unitGroupList;
	
	public FilterFormDataDTO() {
		
	}

	/**
	 * @return the financialYearList
	 */
	public LinkedList<FinancialYearDTO> getFinancialYearList() {
		return financialYearList;
	}

	/**
	 * @param financialYearList the financialYearList to set
	 */
	public void setFinancialYearList(LinkedList<FinancialYearDTO> financialYearList) {
		this.financialYearList = financialYearList;
	}

	/**
	 * @return the monthList
	 */
	public LinkedList<MonthDTO> getMonthList() {
		return monthList;
	}

	/**
	 * @param monthList the monthList to set
	 */
	public void setMonthList(LinkedList<MonthDTO> monthList) {
		this.monthList = monthList;
	}

	/**
	 * @return the unitGroupList
	 */
	public LinkedList<UnitDTO> getUnitGroupList() {
		return unitGroupList;
	}

	/**
	 * @param unitGroupList the unitGroupList to set
	 */
	public void setUnitGroupList(LinkedList<UnitDTO> unitGroupList) {
		this.unitGroupList = unitGroupList;
	}
	
}

	