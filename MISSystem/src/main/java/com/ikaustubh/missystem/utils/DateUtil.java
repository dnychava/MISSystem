package com.ikaustubh.missystem.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.ikaustubh.missystem.core.DateFormatterPattern;

/**
 * 
 * @author Dnyaneshwar
 * @since 28-Nov-2018
 *
 */
public class DateUtil {

	/**
	 * This method convert given the <code>reportingMonth</code> with display date format into string.
	 * 
	 * @param reportingMonth as LocalDateTime
	 * @return converted value
	 */
	public static String convertReportingMonthInString( LocalDateTime reportingMonth ) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateFormatterPattern.DISPLAY_DATE_TIME_PATTERN);
		return reportingMonth.format(formatter);
	}
	
	/**
	 * This method convert given the <code>reportingMonthStr</code> into LocalDateTime.
	 * 
	 * @param reportingMonth as String
	 * @return converted value 
	 */
	public static int convertStringInReportingMonth( String reportingMonthStr ) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateFormatterPattern.DISPLAY_DATE_TIME_PATTERN);
		LocalDateTime ld = LocalDateTime.parse(reportingMonthStr,formatter);
		return ld.getMonthValue();
	}
	
	/**
	 * This method generate the reporting month with given param.
	 * 
	 * @param year
	 * @param month
	 * @return the LocalDateTime object which converted it.
	 */
	public static LocalDateTime generateReportingMonth(int year, int month) {
		LocalDateTime returnVal = LocalDateTime.now();
		LocalDate localDate = LocalDate.of(returnVal.getYear(), month, returnVal.getDayOfMonth() );
		returnVal = LocalDateTime.of(localDate, LocalTime.now() );
		return returnVal;
	}
	
	/**
	 * 
	 * @param monthStr
	 * @return the int value of month
	 */
	public static int getMonthInt(String monthStr) {

		int returnVal;
		switch (monthStr) {
		case "Jan":
			returnVal = 01;
			break;
		case "Feb":
			returnVal = 02;
			break;
		case "Mar":
			returnVal = 03;
			break;
		case "Apr":
			returnVal = 04;
			break;
		case "May":
			returnVal = 05;
			break;
		case "Jun":
			returnVal = 06;
			break;
		case "Jul":
			returnVal = 07;
			break;
		case "Aug":
			returnVal = Integer.parseInt("08");
			break;
		case "Sep":
			returnVal = Integer.parseInt("09");
			break;
		case "Oct":
			returnVal = 10;
			break;
		case "Nov":
			returnVal = 11;
			break;
		case "Dec":
			returnVal = 12;
			break;

		default:
			returnVal = -1;
			break;
		}
		return returnVal;
	}
}
