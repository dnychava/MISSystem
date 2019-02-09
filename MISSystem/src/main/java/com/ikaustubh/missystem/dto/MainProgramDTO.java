package com.ikaustubh.missystem.dto;

public class MainProgramDTO implements Comparable<MainProgramDTO>  {

	private long rid;

	private String name;

	private String newCode;

	private String oldCode;
	
	public MainProgramDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the rid
	 */
	public long getRid() {
		return rid;
	}

	/**
	 * @param rid the rid to set
	 */
	public void setRid(long rid) {
		this.rid = rid;
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

	@Override
	public int compareTo(MainProgramDTO o) {
		return (int) (this.rid - o.getRid());
	}
	
}
