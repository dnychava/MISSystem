package com.ikaustubh.missystem.core;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 
 * @author Dnyaneshwar
 * @since 28-Nov-2018
 *
 */
public class DateUtils {

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
}
