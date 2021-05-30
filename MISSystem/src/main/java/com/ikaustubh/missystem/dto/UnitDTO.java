/**
 * 
 */
package com.ikaustubh.missystem.dto;

import java.util.LinkedList;

/**
 * @author Dnyaneshwar
 * @since 08-Sept-2019
 *
 */
public class UnitDTO extends BaseMapDTO {

	
	private LinkedList<UnitDTO> unitList = new LinkedList<UnitDTO>();
	
	/**
	 * 
	 */
	public UnitDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the unitList
	 */
	public LinkedList<UnitDTO> getUnitList() {
		return unitList;
	}

	/**
	 * @param unitList the unitList to set
	 */
	public void setUnitList(LinkedList<UnitDTO> unitList) {
		this.unitList = unitList;
	}	
	
	
}
