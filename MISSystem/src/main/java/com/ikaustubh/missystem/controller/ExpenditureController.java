package com.ikaustubh.missystem.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import com.ikaustubh.missystem.entities.ActivityEntity;
import com.ikaustubh.missystem.entities.MainProgramHeadEntity;
import com.ikaustubh.missystem.helper.ExpenditureHelper;
import com.ikaustubh.missystem.repository.ActivityRepo;
import com.ikaustubh.missystem.repository.MainProgramHeadRepository;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping(value = "/expenditure", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExpenditureController {

	@Autowired
	ExpenditureHelper expenditureHelper;

	@Autowired
	ResponseDTO resposeDTO;

	@Autowired
	ActivityRepo mnActRepo;
	
	@Autowired
	MainProgramHeadRepository programHDRepo;
	

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
			@RequestParam("expDataExcelFile") MultipartFile excelFile) {
		try {
			System.out.println(excelFile.getOriginalFilename());
			expenditureHelper.uploadExpenditureDataFile(excelFile);
			resposeDTO.setMessage("File uploaded successully !!");
			return new ResponseEntity<>(resposeDTO, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resposeDTO.setErrors(e.getMessage());
			return new ResponseEntity<>(resposeDTO, HttpStatus.BAD_REQUEST);
			
		}

	}

	private void createMainActivityValue() {
		
		Optional<MainProgramHeadEntity> mnHDPrm = programHDRepo.findById((long)1);
		MainProgramHeadEntity mainProgramEntity= mnHDPrm.isPresent() ? mnHDPrm.get() : null;
		
		// Level 1 mainActivity(1)
		ActivityEntity mainActLvl1 = new ActivityEntity();
		mainActLvl1.setNewCode("1");
		mainActLvl1.setOldCode("");
		mainActLvl1.setName("Service Delivery - Facility Based");
		mainActLvl1.setMainProgramHeadEntity(mainProgramEntity);
			// Level 2(1.1) mainActivity(1)
			ActivityEntity mainActLvl11 = new ActivityEntity();
			mainActLvl11.setNewCode("1.1");
			mainActLvl11.setOldCode("");
			mainActLvl11.setName("Service Delivery");
			mainActLvl11.setMainActivity(mainActLvl1);
			mainActLvl11.setMainProgramHeadEntity(mainProgramEntity);
				// Level 3(1.1.1) mainActivity(1)
				ActivityEntity mainActLvl111 = new ActivityEntity();
				mainActLvl111.setNewCode("1.1.1");
				mainActLvl111.setOldCode("");
				mainActLvl111.setName("Strengthening MH Services");
				mainActLvl111.setMainActivity(mainActLvl11);
				mainActLvl111.setMainProgramHeadEntity(mainProgramEntity);
					// Level 4(1.1.1.1) mainActivity(1)
					ActivityEntity mainActLvl1111 = new ActivityEntity();
					mainActLvl1111.setNewCode("1.1.1.1");
					mainActLvl1111.setOldCode("A.1.5.4");
					mainActLvl1111.setName("PMSMA activities at State/ District level");
					mainActLvl1111.setMainActivity(mainActLvl111);
					mainActLvl1111.setMainProgramHeadEntity(mainProgramEntity);
					// Level 4(1.1.1.2) mainActivity(1)
					ActivityEntity mainActLvl1112 = new ActivityEntity();
					mainActLvl1112.setNewCode("1.1.1.2");
					mainActLvl1112.setOldCode("A.1.6.3");
					mainActLvl1112.setName("Diet services for JSSK Beneficaries (3 da6666ys for Normal Delivery and 7 days for Caesarean)");
					mainActLvl1112.setMainActivity(mainActLvl111);
					mainActLvl1112.setMainProgramHeadEntity(mainProgramEntity);
					// Level 4(1.1.1.3) mainActivity(1)
					ActivityEntity mainActLvl1113 = new ActivityEntity();
					mainActLvl1113.setNewCode("1.1.1.3");
					mainActLvl1113.setOldCode("A.1.6.2");
					mainActLvl1113.setName("Blood Transfusion for JSSK Beneficiaries");
					mainActLvl1113.setMainActivity(mainActLvl111);
					mainActLvl1113.setMainProgramHeadEntity(mainProgramEntity);
					mnActRepo.save(mainActLvl1111);
					mnActRepo.save(mainActLvl1112);
					mnActRepo.save(mainActLvl1113);
					
				// Level 3(1.1.3) mainActivity(1)
				ActivityEntity mainActLvl113 = new ActivityEntity();
				mainActLvl113.setNewCode("1.1.3");
				mainActLvl113.setOldCode("");
				mainActLvl113.setName("Strengthening FP Services");
				mainActLvl113.setMainActivity(mainActLvl11);
				// Level 4(1.1.3.1) mainActivity(1)
				ActivityEntity mainActLvl1131 = new ActivityEntity();
				mainActLvl1131.setNewCode("1.1.3.1");
				mainActLvl1131.setOldCode("A.3.1");
				mainActLvl1131.setName("Terminal/Limiting Methods");
				mainActLvl1131.setMainActivity(mainActLvl113);
					// Level 5(1.1.3.1.1) mainActivity(1)
					ActivityEntity mainActLvl11311 = new ActivityEntity();
					mainActLvl11311.setNewCode("1.1.3.1.1");
					mainActLvl11311.setOldCode("A.3.1.1");
					mainActLvl11311.setName("Female sterilization fixed day services");
					mainActLvl11311.setMainActivity(mainActLvl1131);
					// Level 5(1.1.3.1.2) mainActivity(1)
					ActivityEntity mainActLvl11312 = new ActivityEntity();
					mainActLvl11312.setNewCode("1.1.3.1.2");
					mainActLvl11312.setOldCode("A.3.1.2");
					mainActLvl11312.setName("Male Sterilization fixed day services");
					mainActLvl11312.setMainActivity(mainActLvl1131);
					mnActRepo.save(mainActLvl11311);
					mnActRepo.save(mainActLvl11312);
				
				// Level 4(1.1.3.2) mainActivity(1)
				ActivityEntity mainActLvl1132 = new ActivityEntity();
				mainActLvl1132.setNewCode("1.1.3.2");
				mainActLvl1132.setOldCode("A.3.2");
				mainActLvl1132.setName("Spacing Methods");
				mainActLvl1132.setMainActivity(mainActLvl113);
					// Level 5(1.1.3.2.1) mainActivity(1)
					ActivityEntity mainActLvl11321 = new ActivityEntity();
					mainActLvl11321.setNewCode("1.1.3.2.1");
					mainActLvl11321.setOldCode("A.3.2.1");
					mainActLvl11321.setName("IUCD fixed day services");
					mainActLvl11321.setMainActivity(mainActLvl1132);
					// Level 5(1.1.3.2.2) mainActivity(1)
					ActivityEntity mainActLvl11322 = new ActivityEntity();
					mainActLvl11322.setNewCode("1.1.3.2.2");
					mainActLvl11322.setOldCode("A.3.7.5");
					mainActLvl11322.setName("Other activities (demand generation, strengthening service delivery etc.)");
					mainActLvl11322.setMainActivity(mainActLvl1132);
					mnActRepo.save(mainActLvl11321);
					mnActRepo.save(mainActLvl11322);
					
	

		/*==========================
				2nd
		==========================*/
		// Level 1 mainActivity(2)
		ActivityEntity mainActLvl2 = new ActivityEntity();
		mainActLvl2.setNewCode("2");
		mainActLvl2.setOldCode("");
		mainActLvl2.setName("Service Delivery - Community Based");
			// Level 2(2.1) mainActivity(2)
			ActivityEntity mainActLvl21 = new ActivityEntity();
			mainActLvl21.setNewCode("2.1");
			mainActLvl21.setOldCode("");
			mainActLvl21.setName("Mobile Units");
			mainActLvl21.setMainActivity(mainActLvl2);
				// Level 3(2.1.1) mainActivity(2)
				ActivityEntity mainActLvl211 = new ActivityEntity();
				mainActLvl211.setNewCode("2.1.1");
				mainActLvl211.setOldCode("B11");
				mainActLvl211.setName("National Mobile Medical Units (MMU)");
				mainActLvl211.setMainActivity(mainActLvl21);
					// Level 4(2.1.1.1) mainActivity(2)
					ActivityEntity mainActLvl2111 = new ActivityEntity();
					mainActLvl2111.setNewCode("2.1.1.1");
					mainActLvl2111.setOldCode("B11.1.1");
					mainActLvl2111.setName("Capex");
					mainActLvl2111.setMainActivity(mainActLvl211);
					// Level 4(2.1.1.2) mainActivity(2)
					ActivityEntity mainActLvl2112 = new ActivityEntity();
					mainActLvl2112.setNewCode("2.1.1.2");
					mainActLvl2112.setOldCode("B11.1.2");
					mainActLvl2112.setName("Opex");
					mainActLvl2112.setMainActivity(mainActLvl211);
					
					mnActRepo.save(mainActLvl2111);
					mnActRepo.save(mainActLvl2112);
					
		
	////Fetching data.
	Optional<ActivityEntity> findMayAct = mnActRepo.findById(new Long(568));
	if(findMayAct.isPresent()) {
		ActivityEntity mainLvl1 = findMayAct.get();
		System.out.println("=======1====="+mainLvl1.getNewCode());
		List<ActivityEntity> mainLvl2 = mainLvl1.getMainActivityEntities();
		for (ActivityEntity mainActivityEntityTest : mainLvl2) {
			System.out.println("=======2====="+mainActivityEntityTest.getNewCode());
		}
		
	}


	}

}
