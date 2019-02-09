package com.ikaustubh.missystem.helper;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikaustubh.missystem.core.DateFormatterPattern;
import com.ikaustubh.missystem.core.DateUtils;
import com.ikaustubh.missystem.core.TransactionMode;
import com.ikaustubh.missystem.core.TransactionStatus;
import com.ikaustubh.missystem.dto.ExpenditureDTO;
import com.ikaustubh.missystem.entities.BudgetDistributeEntity;
import com.ikaustubh.missystem.entities.ExpenditureDtlEntity;
import com.ikaustubh.missystem.entities.ExpenditureEntity;
import com.ikaustubh.missystem.entities.MainActivityEntity;
import com.ikaustubh.missystem.entities.MainProgramEntity;
import com.ikaustubh.missystem.entities.MainSubActivityEntity;
import com.ikaustubh.missystem.entities.MainSubProgramEntity;
import com.ikaustubh.missystem.entities.RecordStatusEntity;
import com.ikaustubh.missystem.entities.UnitDtlEntity;
import com.ikaustubh.missystem.entities.UnitEntity;
import com.ikaustubh.missystem.entities.UserInfoEntity;
import com.ikaustubh.missystem.repository.BudgetDistributeRepository;
import com.ikaustubh.missystem.repository.ExpenditureRepository;
import com.ikaustubh.missystem.repository.MainProgramRepository;
import com.ikaustubh.missystem.repository.MainSubActivityRepository;
import com.ikaustubh.missystem.repository.RecordStatusRepository;
import com.ikaustubh.missystem.repository.UnitRepository;
import com.ikaustubh.missystem.repository.UserInfoRepository;
import com.ikaustubh.missystem.wrapper.ExpenditureWrapper;

import javassist.NotFoundException;

@Component
public class ExpenditureHelper {

	@Autowired
	private ExpenditureRepository expenditureRepository;

	@Autowired
	private BudgetDistributeRepository budgetDistributeRepository;

	@Autowired
	private MainProgramRepository mainProgramRepository;

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private UnitRepository unitRepository;

	@Autowired
	private RecordStatusRepository recordStatusRepository;

	@Autowired
	private MainSubActivityRepository mainSubActivityRepository;

	private UserInfoEntity userInfoEntity;

	private Authentication auth = null;

	public String getExpenditureByMainProgramRid(long rid, String year) throws JsonProcessingException {

		String returnInJSON = "";
		auth = SecurityContextHolder.getContext().getAuthentication();

		userInfoEntity = userInfoRepository.findByUsername(auth.getName());
		long unitRid = userInfoEntity.getUnitEntity().getRid();
		Set<Long> subActivityRids = getAllMainSubActivitityRids(rid);
		LocalDateTime reportingMonth = LocalDateTime.now().minusMonths(1);

		String reportingMonthStr = DateUtils.convertReportingMonthInString(reportingMonth);
		System.out.println("===============reportingMonthStr[" + reportingMonthStr + "]");
		/*
		 * 1st Condition: Check the Data is exist in Expenditure table with where
		 * current logged user unit rid and select year and all Sub Activity rids. If
		 * not found then get all data from Distributed table where current logged user
		 * unit rid. It should be marked the Transaction Mode is 'Add'.
		 * 
		 * 2nd Condition: Check the Data is exist in Expenditure table with where
		 * current logged user unit rid and select year and reporting month. If not
		 * founded then sum all previous reported month data and It should be marked the
		 * Transaction Mode is 'Add'. Otherwise open the current fetched data in
		 * Transaction Mode is 'Edit'
		 *
		 */
		Set<ExpenditureEntity> expenditureEntities = expenditureRepository.findByUnitRidAndSubActivityRids(unitRid,
				year, subActivityRids);

		Set<BudgetDistributeEntity> budgetDistributeEntities = null;

		if (expenditureEntities.size() == 0) {
			budgetDistributeEntities = budgetDistributeRepository.findByUnitRidAndSubActivityRids(unitRid, year,
					subActivityRids);
			returnInJSON = new ObjectMapper().writeValueAsString(convertBudgetEntityToExpenditureDTO(
					budgetDistributeEntities, TransactionMode.ADD, reportingMonthStr, year));
		} else {
			expenditureEntities = expenditureRepository.findByUnitRidAndSubActivityRids(unitRid, year, reportingMonth,
					subActivityRids);
			/*
			 * If not founded the current reporting month then Should be set transaction
			 * mode in 'Add'
			 */
			if (expenditureEntities.size() == 0) {
				// Transaction Mode = ADD
				budgetDistributeEntities = budgetDistributeRepository.findByUnitRidAndSubActivityRids(unitRid, year,
						subActivityRids);
				Map<Long, ExpenditureWrapper> prograssiveAndBudgetMap = expenditureRepository
						.getPrograssiveAmtAndBudget(unitRid, year, subActivityRids, false);
				returnInJSON = new ObjectMapper()
						.writeValueAsString(convertBudgetEntityToExpenditureDTOADD(budgetDistributeEntities,
								TransactionMode.ADD, reportingMonthStr, year, prograssiveAndBudgetMap));
			}
			/*
			 * If founded current reporting month then Should be set transaction mode in
			 * 'Edit'
			 */
			else {
				Map<Long, ExpenditureWrapper> prograssiveAndBudgetMap = expenditureRepository
						.getPrograssiveAmtAndBudget(unitRid, year, subActivityRids, true);
				returnInJSON = new ObjectMapper().writeValueAsString(convertExpenditureEntityToExpenditureDTO(
						expenditureEntities, TransactionMode.EDIT, prograssiveAndBudgetMap));
			}
		}
		return returnInJSON;
	}

	private Set<Long> getAllMainSubActivitityRids(long rid) {
		Set<Long> subActivityRids = new HashSet<Long>();
		MainProgramEntity mainProgramEntity = mainProgramRepository.getOne(rid);
		Set<MainSubProgramEntity> mainSubProgramEntities = mainProgramEntity.getMainSubProgramEntity();
		for (MainSubProgramEntity mainSubProgramEntity : mainSubProgramEntities) {
			Set<MainActivityEntity> mainActivityEntities = mainSubProgramEntity.getMainActivityEntity();
			for (MainActivityEntity mainActivityEntity : mainActivityEntities) {
				Set<MainSubActivityEntity> mainSubActivityEntities = mainActivityEntity.getMainSubActivityEntity();
				for (MainSubActivityEntity mainSubActivityEntity : mainSubActivityEntities) {
					subActivityRids.add(mainSubActivityEntity.getRid());
				}
			}
		}
		return subActivityRids;
	}
	
	private Set<Long> getAllMainSubActivitityRids(List<MainSubActivityEntity> subActivityList) {
		
		Set<Long> subActivityRids = new HashSet<Long>();
		subActivityList.forEach(mainSubActivityEntity -> subActivityRids.add(mainSubActivityEntity.getRid()));
		return subActivityRids;
	}

	private List<ExpenditureDTO> convertBudgetEntityToExpenditureDTO(
			Set<BudgetDistributeEntity> budgetDistributeEntities, String transactionMode, String reportingMonthStr,
			String year) {

		List<ExpenditureDTO> expenditureDTOs = new ArrayList<ExpenditureDTO>();
		for (BudgetDistributeEntity budgetDistributeEntity : budgetDistributeEntities) {
			ExpenditureDTO expenditureDTO = new ExpenditureDTO();
			expenditureDTO.setTransactionMode(transactionMode);
			expenditureDTO.setReportingMonthStr(reportingMonthStr);
			expenditureDTO.setYear(year);
			expenditureDTO.setUnitRid(budgetDistributeEntity.getUnitEntity().getRid());
			expenditureDTO.setMainSubActivityRid(budgetDistributeEntity.getMainSubActivityEntity().getRid());
			expenditureDTO.setNewCode(budgetDistributeEntity.getMainSubActivityEntity().getNewCode());
			expenditureDTO.setActivityName(budgetDistributeEntity.getMainSubActivityEntity().getName());
			expenditureDTO.setBudgetAmtInLakh(budgetDistributeEntity.getAmtInLakh());
			expenditureDTO.setPrograsiveAmtInLakh(new BigDecimal(0));
			expenditureDTO.setExpenditureAmtInLakh(new BigDecimal(0));
			expenditureDTOs.add(expenditureDTO);
		}
		Collections.sort(expenditureDTOs);
		return expenditureDTOs;
	}

	/**
	 * This method called when the current reporting month not founded <br>
	 * in Expenditure table.
	 * 
	 * @param expenditureEntities
	 * @param transactionMode
	 * @param prograssiveAndBudgetMap
	 * @return the all ExpenditureDTO
	 */
	private List<ExpenditureDTO> convertExpenditureEntityToExpenditureDTO(Set<ExpenditureEntity> expenditureEntities,
			String transactionMode, Map<Long, ExpenditureWrapper> prograssiveAndBudgetMap) {

		List<ExpenditureDTO> expenditureDTOs = new ArrayList<ExpenditureDTO>();
		for (ExpenditureEntity expenditureEntity : expenditureEntities) {
			long unitRid = expenditureEntity.getUnitEntity().getRid();
			long subActivityRid = expenditureEntity.getMainSubActivityEntity().getRid();
			ExpenditureWrapper exWrapper = prograssiveAndBudgetMap.get(subActivityRid);
			BigDecimal prograssiveAmtInLakh = exWrapper.getProgressiveAmtInLakh();
			BigDecimal budgetAmtInLakh = exWrapper.getBudgetAmtInLakh();
			/*
			 * Calculating reporting month from last createdDateAndTime.
			 */
			LocalDateTime createdDateAntTime = expenditureEntity.getyCreatedDateAndTime();
			LocalDate reportingDate = LocalDate.of(createdDateAntTime.getYear(), expenditureEntity.getReportingMonth(),
					createdDateAntTime.getDayOfMonth());
			LocalTime reportingTime = LocalTime.of(createdDateAntTime.getHour(), createdDateAntTime.getMinute(),
					createdDateAntTime.getSecond());
			LocalDateTime reportingMonthAndTime = LocalDateTime.of(reportingDate, reportingTime);
			String reportingMonthStr = DateUtils.convertReportingMonthInString(reportingMonthAndTime);
			/*
			 * Building ExpendditureDTO for render on UI
			 */
			ExpenditureDTO expenditureDTO = new ExpenditureDTO();
			expenditureDTO.setExpenditureRid(expenditureEntity.getRid());
			expenditureDTO.setTransactionMode(transactionMode);
			expenditureDTO.setReportingMonthStr(reportingMonthStr);
			expenditureDTO.setYear(expenditureEntity.getYear());
			expenditureDTO.setUnitRid(unitRid);
			expenditureDTO.setMainSubActivityRid(subActivityRid);
			expenditureDTO.setNewCode(expenditureEntity.getMainSubActivityEntity().getNewCode());
			expenditureDTO.setActivityName(expenditureEntity.getMainSubActivityEntity().getName());
			expenditureDTO.setBudgetAmtInLakh(budgetAmtInLakh);
			expenditureDTO.setPrograsiveAmtInLakh(prograssiveAmtInLakh);
			expenditureDTO.setExpenditureAmtInLakh(expenditureEntity.getTotAmtInLakh());
			expenditureDTOs.add(expenditureDTO);
			exWrapper = null;
		}
		Collections.sort(expenditureDTOs);
		return expenditureDTOs;
	}

	/**
	 * This method called when the current reporting month not founded <br>
	 * in Expenditure table.
	 * 
	 * @param budgetDistributeEntities
	 * @param transactionMode
	 * @param reportingMonthStr
	 * @param year
	 * @param prograssiveAndBudgetMap
	 * @return
	 */
	private List<ExpenditureDTO> convertBudgetEntityToExpenditureDTOADD(
			Set<BudgetDistributeEntity> budgetDistributeEntities, String transactionMode, String reportingMonthStr,
			String year, Map<Long, ExpenditureWrapper> prograssiveAndBudgetMap) {

		List<ExpenditureDTO> expenditureDTOs = new ArrayList<ExpenditureDTO>();
		for (BudgetDistributeEntity budgetDistributeEntity : budgetDistributeEntities) {
			long unitRid = budgetDistributeEntity.getUnitEntity().getRid();
			long subActivityRid = budgetDistributeEntity.getMainSubActivityEntity().getRid();
			BigDecimal prograssiveAmtInLakh = prograssiveAndBudgetMap.get(subActivityRid).getProgressiveAmtInLakh();

			ExpenditureDTO expenditureDTO = new ExpenditureDTO();
			expenditureDTO.setTransactionMode(transactionMode);
			expenditureDTO.setReportingMonthStr(reportingMonthStr);
			expenditureDTO.setYear(year);
			expenditureDTO.setUnitRid(unitRid);
			expenditureDTO.setMainSubActivityRid(subActivityRid);
			expenditureDTO.setNewCode(budgetDistributeEntity.getMainSubActivityEntity().getNewCode());
			expenditureDTO.setActivityName(budgetDistributeEntity.getMainSubActivityEntity().getName());
			expenditureDTO.setBudgetAmtInLakh(budgetDistributeEntity.getAmtInLakh());
			expenditureDTO.setPrograsiveAmtInLakh(prograssiveAmtInLakh);
			expenditureDTO.setExpenditureAmtInLakh(new BigDecimal(0));
			expenditureDTOs.add(expenditureDTO);
		}
		Collections.sort(expenditureDTOs);
		return expenditureDTOs;
	}

	public void saveExpenditure(List<ExpenditureDTO> expenditureDTOs) {

		for (ExpenditureDTO expenditureDTO : expenditureDTOs) {
			if (TransactionMode.ADD.equals(expenditureDTO.getTransactionMode())) {
				saveExpenditure(expenditureDTO);
			}
		}
	}

	public void updateExpenditure(List<ExpenditureDTO> expenditureDTOs) {

		for (ExpenditureDTO expenditureDTO : expenditureDTOs) {
			if (TransactionMode.EDIT.equals(expenditureDTO.getTransactionMode())) {
				updateExpenditure(expenditureDTO);
			}
		}
	}

	/**
	 * This method save the data
	 * 
	 * @param expenditureDTO
	 * @return
	 */
	private ExpenditureEntity saveExpenditure(ExpenditureDTO expenditureDTO) {

		auth = SecurityContextHolder.getContext().getAuthentication();
		LocalDateTime sysDate = LocalDateTime.now();
		UnitEntity unitEntity = unitRepository.getOne(expenditureDTO.getUnitRid());
		RecordStatusEntity recordStatusEntity = recordStatusRepository.findByName(TransactionStatus.SAVED);
		MainSubActivityEntity mainSubActivityEntity = mainSubActivityRepository
				.getOne(expenditureDTO.getMainSubActivityRid());

		BigDecimal calculatedExpAmt = expenditureDTO.getExpenditureAmtInLakh()
				.subtract(expenditureDTO.getPrograsiveAmtInLakh());
		ExpenditureEntity expenditureEntity = new ExpenditureEntity();
		expenditureEntity.setMainSubActivityEntity(mainSubActivityEntity);
		expenditureEntity.setRecordStatusEntity(recordStatusEntity);
		expenditureEntity.setUnitEntity(unitEntity);

		expenditureEntity.setYear(expenditureDTO.getYear());
		expenditureEntity
				.setReportingMonth(DateUtils.convertStringInReportingMonth(expenditureDTO.getReportingMonthStr()));
		expenditureEntity.setTotAmt(calculatedExpAmt.multiply(new BigDecimal(100000)));
		expenditureEntity.setTotAmtInLakh(calculatedExpAmt);
		expenditureEntity.setyCreatedBy(auth.getName());
		expenditureEntity.setyCreatedDateAndTime(sysDate);

		ExpenditureDtlEntity dtlEntity = new ExpenditureDtlEntity();
		dtlEntity.setExpenditureEntity(expenditureEntity);
		dtlEntity.setAmt(calculatedExpAmt.multiply(new BigDecimal(100000)));
		dtlEntity.setAmtInLakh(calculatedExpAmt);
		dtlEntity.setyCreatedBy(auth.getName());
		dtlEntity.setyCreatedDateAndTime(sysDate);

		Set<ExpenditureDtlEntity> dtlEntities = new HashSet<ExpenditureDtlEntity>();
		dtlEntities.add(dtlEntity);

		expenditureEntity.setExpenditureDtlEntity(dtlEntities);
		return expenditureRepository.save(expenditureEntity);
	}

	/**
	 * This method save the data
	 * 
	 * @param expenditureDTO
	 * @return
	 */
	private ExpenditureEntity updateExpenditure(ExpenditureDTO expenditureDTO) {

		ExpenditureEntity expenditureEntity = expenditureRepository.getOne(expenditureDTO.getExpenditureRid());
		if (expenditureEntity.getTotAmtInLakh().compareTo(expenditureDTO.getExpenditureAmtInLakh()) == 0) {
			return null;
		}

		auth = SecurityContextHolder.getContext().getAuthentication();
		LocalDateTime sysDate = LocalDateTime.now();

		BigDecimal calculatedExpAmtInLakh = expenditureDTO.getExpenditureAmtInLakh()
				.subtract(expenditureDTO.getPrograsiveAmtInLakh());
		BigDecimal calculatedExpAmt = calculatedExpAmtInLakh.multiply(new BigDecimal(100000));

		expenditureEntity.setTotAmt(calculatedExpAmt);
		expenditureEntity.setTotAmtInLakh(calculatedExpAmtInLakh);
		expenditureEntity.setzModifiedBy(auth.getName());
		expenditureEntity.setzModifiedDateAndTime(sysDate);

		Optional<ExpenditureDtlEntity> dtlEntityOpt = expenditureEntity.getExpenditureDtlEntity().stream().findFirst();
		ExpenditureDtlEntity dtlEntity = dtlEntityOpt.get();
		dtlEntity.setAmt(calculatedExpAmt);
		dtlEntity.setAmtInLakh(calculatedExpAmtInLakh);
		dtlEntity.setzModifiedBy(auth.getName());
		dtlEntity.setzModifiedDateAndTime(sysDate);

		return expenditureRepository.save(expenditureEntity);
	}

	public void uploadExpenditureDataFile(MultipartFile excelFile)
			throws Exception {

		auth = SecurityContextHolder.getContext().getAuthentication();

		userInfoEntity = userInfoRepository.findByUsername(auth.getName());
		long unitRid = userInfoEntity.getUnitEntity().getRid();
		
		Workbook workbook = WorkbookFactory.create(excelFile.getInputStream());
		Sheet sheet = workbook.getSheetAt(0);
		int lastRow = sheet.getLastRowNum();
		String unitName = "";
		String year = "";
		int reportingMonth = 0;
		LocalDateTime reportingMonthWithDateTime = LocalDateTime.now();
		List<ExpenditureDTO> expenditureDTOs = new LinkedList<ExpenditureDTO>();
		Map<String, String> subActivityInfo = new LinkedHashMap<String, String>();
		for (int rowIndex = 0; rowIndex <= lastRow; rowIndex++) {
			Row row = sheet.getRow(rowIndex);
			int lastCell = row.getLastCellNum();
			ExpenditureDTO expDTO = new ExpenditureDTO();
			for (int cellIndex = 0; cellIndex < lastCell; cellIndex++) {
				Cell cell = row.getCell(cellIndex);
				if (rowIndex == 2) {
					// fetch UnitName
					if (cellIndex == 0) {
						unitName = getStringValFromCell(cell);
						if( unitName == null ) {
							throw new NotFoundException("Unit name should not be blank");
						}else if( !isExistUnitName(unitName, userInfoEntity.getUnitEntity().getUnitDtlsEntity() ) ) {
							throw new IllegalArgumentException( "This Unit name["+unitName+"] not llegal because You logged different unit name" );
						}
					}
					// fetch year
					else if (cellIndex == 1) {
						year = getStringValFromCell(cell);
					}
					// fetch reportingMonth
					else if (cellIndex == 2) {
						reportingMonth = getIntValFromCell(cell);
					} else {
						continue;
					}
				} else if (rowIndex > 3) {
					// fetch newCode
					if (cellIndex == 0) {
						expDTO.setNewCode(getStringValFromCell(cell));
					}
					// fetch oldCode
					else if (cellIndex == 1) {
						expDTO.setOldCode(getStringValFromCell(cell));
					}
					// fetch subactivity name
					else if (cellIndex == 2) {
						expDTO.setActivityName(getStringValFromCell(cell));
					}
					// fetch Budget Amount in Lakh
					else if (cellIndex == 3) {
						expDTO.setBudgetAmtInLakh(getAmtFromCell(cell));
					}
					// fetch Expenditure Amount in Lakh
					else if (cellIndex == 4) {
						expDTO.setExpenditureAmtInLakh(getAmtFromCell(cell));
					}else {
						continue;
					}
					
				} else {
					System.out.println(" row[" + rowIndex + "] col[" + cellIndex + "] ");
				}
			}//End cell
			if (rowIndex > 3) {
				expDTO.setUnitName(unitName);
				expDTO.setYear(year);
				reportingMonthWithDateTime = DateUtils.generateReportingMonth(0, reportingMonth);
				String reportingMonthStr = DateUtils.convertReportingMonthInString(reportingMonthWithDateTime);
				expDTO.setReportingMonthStr(reportingMonthStr);
				expDTO.setPrograsiveAmtInLakh(BigDecimal.ZERO);
				expenditureDTOs.add(expDTO);
				subActivityInfo.put(expDTO.getNewCode(), expDTO.getActivityName());
			}else {
				expDTO = null;
			}
		}//End row
		
		System.out.println(expenditureDTOs);
		
		Set<String> newCodeSet = new HashSet<String>();
		Set<String> subActivityNames = new HashSet<String>();
		subActivityInfo.forEach((k,v)->{
			newCodeSet.add(k);
			subActivityNames.add(v);
		});
		List<MainSubActivityEntity> subActivityList = mainSubActivityRepository.findByRidsAndActivityName(newCodeSet, subActivityNames);
		Set<Long> subActivityRids = new HashSet<Long>();
		subActivityList.forEach(mainSubActivityEntity -> subActivityRids.add(mainSubActivityEntity.getRid()));
		
		/*
		 * 1st Condition: Check the Data is exist in Expenditure table with where
		 * current logged user unit rid and select year and all Sub Activity rids. If
		 * not found then get all data from Distributed table where current logged user
		 * unit rid. It should be marked the Transaction Mode is 'Add'.
		 * 
		 * 2nd Condition: Check the Data is exist in Expenditure table with where
		 * current logged user unit rid and select year and reporting month. If not
		 * founded then sum all previous reported month data and It should be marked the
		 * Transaction Mode is 'Add'. Otherwise open the current fetched data in
		 * Transaction Mode is 'Edit'
		 *
		 */
		
		Set<BudgetDistributeEntity> budgetDistributeEntities = budgetDistributeRepository.findByUnitRidAndSubActivityRids(unitRid, year,
				subActivityRids);
		
		
		Set<ExpenditureEntity> expenditureEntities = expenditureRepository.findByUnitRidAndSubActivityRids(unitRid, year, reportingMonthWithDateTime,
				subActivityRids);
		/*
		 * If not founded the current reporting month then Should be set transaction
		 * mode in 'Add'
		 */
		if (expenditureEntities.size() == 0) {
			// Transaction Mode = ADD
			expenditureDTOs.forEach(expenditureDTO -> {
				saveExpenditure(expenditureDTO);
			});
		}
		/*
		 * If founded current reporting month then Should be set transaction mode in
		 * 'Edit'
		 */
		else {
			/*Map<Long, ExpenditureWrapper> prograssiveAndBudgetMap = expenditureRepository
					.getPrograssiveAmtAndBudget(unitRid, year, subActivityRids, true);
			returnInJSON = new ObjectMapper().writeValueAsString(convertExpenditureEntityToExpenditureDTO(
					expenditureEntities, TransactionMode.EDIT, prograssiveAndBudgetMap));*/
		}
		
		
	}
	
	private boolean isExistUnitName(String unitName, List<UnitDtlEntity> unitDtlList ) {
		UnitDtlEntity unitDtlEntity = unitDtlList.stream().filter(x -> unitName.equals(x.getName())).findAny().orElse(null);
		return unitDtlEntity == null ? false : true;
	}

	private int getIntValFromCell(Cell cell) {
		int returnVal;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC:
			returnVal = (int) cell.getNumericCellValue();
			break;
		default:
			returnVal = 0;
			break;
		}
		return returnVal;
	}
	
	private BigDecimal getAmtFromCell(Cell cell) {
		
		BigDecimal returnVal = BigDecimal.ZERO;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC:
			returnVal =  new BigDecimal(cell.getNumericCellValue());
			break;
		default:
			returnVal = BigDecimal.ZERO;
			break;
		}
		return returnVal;
	}

	private String getStringValFromCell(Cell cell) {
		String returnVal;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			returnVal = cell.getStringCellValue();
			break;
		default:
			returnVal = "";
			break;
		}
		return returnVal;
	}
}
