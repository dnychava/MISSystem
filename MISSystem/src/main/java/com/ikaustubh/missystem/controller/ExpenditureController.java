package com.ikaustubh.missystem.controller;

import java.io.IOException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ikaustubh.missystem.core.TransactionMode;
import com.ikaustubh.missystem.dto.ExpenditureDTO;
import com.ikaustubh.missystem.dto.ResponseDTO;
import com.ikaustubh.missystem.helper.ExpenditureHelper;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping(value = "/expenditure", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExpenditureController {

	@Autowired
	ExpenditureHelper expenditureHelper;

	@Autowired
	ResponseDTO resposeDTO;

	@GetMapping(value = "/getExpenditure")
	public String getExpenditureByMainProgramRid(@RequestParam("mainProgramRid") long rid,
			@RequestParam("year") String year) throws JsonProcessingException {
		return expenditureHelper.getExpenditureByMainProgramRid(rid, year);
	}

	@PostMapping(value = "/saveExpenditure")
	public ResponseEntity<ResponseDTO> saveExpenditure(@RequestBody List<ExpenditureDTO> expenditureDTOs) {

		System.out.println("TransactionMode()[" + expenditureDTOs.get(0).getTransactionMode() + "]");
		if (TransactionMode.ADD.equals(expenditureDTOs.get(0).getTransactionMode())) {
			expenditureHelper.saveExpenditure(expenditureDTOs);
			resposeDTO.setMessage("Save Successfully !!");
			resposeDTO.setStatus(HttpStatus.CREATED.name());
			return new ResponseEntity<>(resposeDTO, HttpStatus.CREATED);
		} else if (TransactionMode.EDIT.equals(expenditureDTOs.get(0).getTransactionMode())) {
			expenditureHelper.updateExpenditure(expenditureDTOs);
			resposeDTO.setMessage("Update Successfully !!");
			resposeDTO.setStatus(HttpStatus.OK.name());
			return new ResponseEntity<>(resposeDTO, HttpStatus.OK);
		} else {
			expenditureHelper.saveExpenditure(expenditureDTOs);
			resposeDTO.setErrors("Transaction Mode not found");
			resposeDTO.setStatus(HttpStatus.NOT_FOUND.name());
			return new ResponseEntity<>(resposeDTO, HttpStatus.NO_CONTENT);
		}
	}

	@PutMapping(value = "/updateExpenditure")
	public ResponseEntity<ResponseDTO> updateExpenditure(@RequestBody List<ExpenditureDTO> expenditureDTOs) {
		expenditureHelper.updateExpenditure(expenditureDTOs);
		resposeDTO.setMessage("Updated Successfully !!");
		resposeDTO.setStatus(HttpStatus.OK.name());
		return new ResponseEntity<>(resposeDTO, HttpStatus.OK);
	}

	@PostMapping(value = "/uploadExpenditureDataFile")
	public ResponseEntity<ResponseDTO> uploadExpenditureDataFile(
			@RequestParam("expDataExcelFile") MultipartFile excelFile)
			throws Exception {
		System.out.println(excelFile.getOriginalFilename());
		expenditureHelper.uploadExpenditureDataFile(excelFile);
		resposeDTO.setMessage("Uploaded file !!");
		return new ResponseEntity<>(resposeDTO, HttpStatus.OK);

	}

}
