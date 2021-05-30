package com.ikaustubh.missystem.dto;

public class BaseMapDTO {

	private String key;
	
	private String value;
	
	public BaseMapDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BaseMapDTO [key=" + key + ", value=" + value + "]";
	}
	
}
