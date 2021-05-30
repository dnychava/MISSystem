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
@RequestMapping(value = "/master/data", produces = MediaType.APPLICATION_JSON_VALUE)
public class MasterDataController extends BaseController {


	@GetMapping(value = "/allUnitData")
	public ResponseEntity<List<FMRReportDataDTO>> getAllUnitData() {
		
		
		return null;
	}
	

}
