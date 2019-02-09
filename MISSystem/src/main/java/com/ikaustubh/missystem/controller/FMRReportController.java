package com.ikaustubh.missystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ikaustubh.missystem.dto.FMRReportDataDTO;
import com.ikaustubh.missystem.dto.FMRReportFilterDTO;
import com.ikaustubh.missystem.helper.FMRReportHelper;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping(value = "/report", produces = MediaType.APPLICATION_JSON_VALUE)
public class FMRReportController {

	@Autowired
	private FMRReportHelper fmrReportHelper;

	@Autowired
	private FMRReportFilterDTO fmrReportFilterDTO;

	@GetMapping(value = "/fmr")
	public ResponseEntity<List<FMRReportDataDTO>> getFMRReport(@RequestParam("unitRid") String unitRid,
			@RequestParam("year") String year, @RequestParam("mainProgramHeadRid") String mainProgramHeadRid,
			@RequestParam("reportingMonthStr") String reportingMonthStr) {
		fmrReportFilterDTO.setUnitRid(Long.parseLong(unitRid));
		fmrReportFilterDTO.setYear(year);
		fmrReportFilterDTO.setMainProgramHeadRid(Long.parseLong(mainProgramHeadRid));
		fmrReportFilterDTO.setReportingMonth(getMonthInt(reportingMonthStr));
		
		return fmrReportHelper.getFMRReport(fmrReportFilterDTO);
	}
	
	private int getMonthInt(String monthStr) {

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
		case "Sept":
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
