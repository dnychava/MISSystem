package com.ikaustubh.missystem.helper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikaustubh.missystem.core.TransactionMode;
import com.ikaustubh.missystem.core.TransactionStatus;
import com.ikaustubh.missystem.custom.exceptions.DupblicateException;
import com.ikaustubh.missystem.dto.ExpenditureDTO;
import com.ikaustubh.missystem.entities.ActivityEntity;
import com.ikaustubh.missystem.entities.BudgetDistributeEntity;
import com.ikaustubh.missystem.entities.ExpenditureDtlEntity;
import com.ikaustubh.missystem.entities.ExpenditureEntity;
import com.ikaustubh.missystem.entities.RecordStatusEntity;
import com.ikaustubh.missystem.entities.UnitEntity;
import com.ikaustubh.missystem.entities.UserInfoEntity;
import com.ikaustubh.missystem.repository.ActivityRepo;
import com.ikaustubh.missystem.repository.RecordStatusRepository;
import com.ikaustubh.missystem.repository.UnitRepository;
import com.ikaustubh.missystem.repository.UserInfoRepository;
import com.ikaustubh.missystem.services.BudgetDistributeService;
import com.ikaustubh.missystem.services.ExpenditureService;
import com.ikaustubh.missystem.wrapper.ExpenditureWrapper;

import javassist.NotFoundException;

@Component
public class ExpenditureHelper {

	Logger logger = LoggerFactory.getLogger(ExpenditureHelper.class);

	@Autowired
	private ExpenditureService expenditureService;

	@Autowired
	private BudgetDistributeService budgetDistributeService;

	@Autowired
	private ActivityRepo mainProgramRepository;

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private UnitRepository unitRepository;

	@Autowired
	private RecordStatusRepository recordStatusRepository;

	private UserInfoEntity userInfoEntity;

	private Authentication auth = null;
	
	public ExpenditureHelper() {
		auth = SecurityContextHolder.getContext().getAuthentication();
	}

	public String getExpenditureByMainProgramRid(long rid, String year) throws JsonProcessingException {

		String returnInJSON = "";
		auth = SecurityContextHolder.getContext().getAuthentication();

		userInfoEntity = userInfoRepository.findByUsername(auth.getName());
		long unitRid = userInfoEntity.getUnitEntity().getRid();
		Set<Long> subActivityRids = getAllMainSubActivitityRids(rid);
		LocalDateTime reportingMonth = LocalDateTime.now().minusMonths(1);

		String reportingMonthStr = null ;//DateUtils.convertReportingMonthInString(reportingMonth);
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
		Set<ExpenditureEntity> expenditureEntities = expenditureService.findByUnitRidAndSubActivityRids(unitRid, year,
				subActivityRids);

		Set<BudgetDistributeEntity> budgetDistributeEntities = null;

		if (expenditureEntities.size() == 0) {
			budgetDistributeEntities = budgetDistributeService.findByUnitRidAndSubActivityRids(unitRid, year,
					subActivityRids);
			returnInJSON = new ObjectMapper().writeValueAsString(convertBudgetEntityToExpenditureDTO(
					budgetDistributeEntities, TransactionMode.ADD, reportingMonthStr, year));
		} else {
			expenditureEntities = expenditureService.findByUnitRidAndSubActivityRids(unitRid, year, reportingMonth,
					subActivityRids);
			/*
			 * If not founded the current reporting month then Should be set transaction
			 * mode in 'Add'
			 */
			if (expenditureEntities.size() == 0) {
				// Transaction Mode = ADD
				budgetDistributeEntities = budgetDistributeService.findByUnitRidAndSubActivityRids(unitRid, year,
						subActivityRids);
				Map<Long, ExpenditureWrapper> prograssiveAndBudgetMap = expenditureService
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
				Map<Long, ExpenditureWrapper> prograssiveAndBudgetMap = expenditureService
						.getPrograssiveAmtAndBudget(unitRid, year, subActivityRids, true);
				returnInJSON = new ObjectMapper().writeValueAsString(convertExpenditureEntityToExpenditureDTO(
						expenditureEntities, TransactionMode.EDIT, prograssiveAndBudgetMap));
			}
		}
		return returnInJSON;
	}

	private Set<Long> getAllMainSubActivitityRids(long rid) {
		Set<Long> subActivityRids = new HashSet<Long>();
		/*
		 * MainProgramEntity mainProgramEntity = mainProgramRepository.getOne(rid);
		 * Set<MainSubProgramEntity> mainSubProgramEntities =
		 * mainProgramEntity.getMainSubProgramEntity(); for (MainSubProgramEntity
		 * mainSubProgramEntity : mainSubProgramEntities) { Set<MainActivityEntity>
		 * mainActivityEntities = mainSubProgramEntity.getMainActivityEntity(); for
		 * (MainActivityEntity mainActivityEntity : mainActivityEntities) {
		 * Set<MainSubActivityEntity> mainSubActivityEntities =
		 * mainActivityEntity.getMainSubActivityEntity(); for (MainSubActivityEntity
		 * mainSubActivityEntity : mainSubActivityEntities) {
		 * subActivityRids.add(mainSubActivityEntity.getRid()); } } }
		 */
		return subActivityRids;
	}

	/*
	 * private Set<Long> getAllMainSubActivitityRids(List<MainSubActivityEntity>
	 * subActivityList) {
	 * 
	 * Set<Long> subActivityRids = new HashSet<Long>();
	 * subActivityList.forEach(mainSubActivityEntity ->
	 * subActivityRids.add(mainSubActivityEntity.getRid())); return subActivityRids;
	 * }
	 */

	private List<ExpenditureDTO> convertBudgetEntityToExpenditureDTO(
			Set<BudgetDistributeEntity> budgetDistributeEntities, String transactionMode, String reportingMonthStr,
			String year) {

		List<ExpenditureDTO> expenditureDTOs = new ArrayList<ExpenditureDTO>();
		for (BudgetDistributeEntity budgetDistributeEntity : budgetDistributeEntities) {
			ExpenditureDTO expenditureDTO = new ExpenditureDTO();
			expenditureDTO.setTransactionMode(transactionMode);
			// expenditureDTO.setReportingMonthStr(reportingMonthStr);
			expenditureDTO.setYear(year);
			// expenditureDTO.setUnitRid(budgetDistributeEntity.getUnitEntity().getRid());
			/*
			 * expenditureDTO.setMainSubActivityRid(budgetDistributeEntity.
			 * getMainSubActivityEntity().getRid());
			 * expenditureDTO.setNewCode(budgetDistributeEntity.getMainSubActivityEntity().
			 * getNewCode()); expenditureDTO.setActivityName(budgetDistributeEntity.
			 * getMainSubActivityEntity().getName());
			 */
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
			long unitRid; // = expenditureEntity.getUnitEntity().getRid();
			long subActivityRid = 0;
			// long subActivityRid = expenditureEntity.getMainSubActivityEntity().getRid();
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
			String reportingMonthStr = null;//DateUtils.convertReportingMonthInString(reportingMonthAndTime);
			/*
			 * Building ExpendditureDTO for render on UI
			 */
			ExpenditureDTO expenditureDTO = new ExpenditureDTO();
			expenditureDTO.setExpenditureRid(expenditureEntity.getRid());
			expenditureDTO.setTransactionMode(transactionMode);
			// expenditureDTO.setReportingMonthStr(reportingMonthStr);
			expenditureDTO.setYear(expenditureEntity.getYear());
			// expenditureDTO.setUnitRid(unitRid);
			expenditureDTO.setMainSubActivityRid(subActivityRid);
			/*
			 * expenditureDTO.setNewCode(expenditureEntity.getMainSubActivityEntity().
			 * getNewCode());
			 * expenditureDTO.setActivityName(expenditureEntity.getMainSubActivityEntity().
			 * getName());
			 */
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
			long unitRid;// = budgetDistributeEntity.getUnitEntity().getRid();
			long subActivityRid = 0;
			// long subActivityRid =
			// budgetDistributeEntity.getMainSubActivityEntity().getRid();
			BigDecimal prograssiveAmtInLakh = prograssiveAndBudgetMap.get(subActivityRid).getProgressiveAmtInLakh();

			ExpenditureDTO expenditureDTO = new ExpenditureDTO();
			expenditureDTO.setTransactionMode(transactionMode);
			// expenditureDTO.setReportingMonthStr(reportingMonthStr);
			expenditureDTO.setYear(year);
			// expenditureDTO.setUnitRid(unitRid);
			expenditureDTO.setMainSubActivityRid(subActivityRid);
			/*
			 * expenditureDTO.setNewCode(budgetDistributeEntity.getMainSubActivityEntity().
			 * getNewCode()); expenditureDTO.setActivityName(budgetDistributeEntity.
			 * getMainSubActivityEntity().getName());
			 */
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
		/*
		 * MainSubActivityEntity mainSubActivityEntity = mainSubActivityRepository
		 * .getOne(expenditureDTO.getMainSubActivityRid());
		 */

		BigDecimal calculatedExpAmt = expenditureDTO.getExpenditureAmtInLakh()
				.subtract(expenditureDTO.getPrograsiveAmtInLakh());
		ExpenditureEntity expenditureEntity = new ExpenditureEntity();
		/* expenditureEntity.setMainSubActivityEntity(mainSubActivityEntity); */
		expenditureEntity.setRecordStatusEntity(recordStatusEntity);
		// expenditureEntity.setUnitEntity(unitEntity);

		expenditureEntity.setYear(expenditureDTO.getYear());
		// expenditureEntity.setReportingMonth(DateUtils.convertStringInReportingMonth(expenditureDTO.getReportingMonthStr()));
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
		return null;// expenditureService.save(expenditureEntity);
	}

	/**
	 * This method save the data
	 * 
	 * @param expenditureDTO
	 * @return
	 */
	private ExpenditureEntity updateExpenditure(ExpenditureDTO expenditureDTO) {

		ExpenditureEntity expenditureEntity = null;// expenditureService.getOne(expenditureDTO.getExpenditureRid());
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

		return null;// expenditureService.save(expenditureEntity);
	}

	/**
	 * 
	 * @param excelFile
	 * @throws Exception
	 */
	public void uploadExpenditureDataFile(MultipartFile excelFile) throws Exception {

		logger.info("In uploadExpenditureDataFile() method Start[" + LocalDateTime.now() + "]");
		
		auth = SecurityContextHolder.getContext().getAuthentication();
		// Find UnitName, Year and Reporting Month
		List<BudgetDistributeEntity> budgetDistributeList;
		LinkedList<String> newCode = new LinkedList<>();
		/*
		 * This map hold Budget Distributed values. 
		 * <key> is the new FMR value 
		 * <value> is the BudgetDistributeEntity
		 */
		HashMap<String, BudgetDistributeEntity> budgetDistributedMap = new HashMap<>();
		List<ExpenditureEntity> expenditureEntities = new LinkedList<ExpenditureEntity>();

		RecordStatusEntity recordStatusEntity = recordStatusRepository.findByName(TransactionStatus.SAVED);

		try {

			
			  userInfoEntity = userInfoRepository.findByUsername(auth.getName()); 
			  long unitRid = userInfoEntity.getRid();
			 

			Workbook workbook = WorkbookFactory.create(excelFile.getInputStream());
			Sheet sheet = workbook.getSheetAt(0);

			budgetDistributeList = findUnitNameAndYearAndMonth(sheet);
			for (BudgetDistributeEntity budgetDistributeEntity : budgetDistributeList) {
				budgetDistributedMap.put(budgetDistributeEntity.getActivityEntity().getNewCode().trim(),
						budgetDistributeEntity);
			}

			int lastRow = sheet.getLastRowNum();
			String unitName = "";
			String year = "";
			int reportingMonth = 0;

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
							
							if (!isExistUnitName(unitName, userInfoEntity.getUnitEntity())) {
								throw new IllegalArgumentException("This Unit name[" + unitName
										+ "] not llegal because You logged different unit name");
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
							if (!budgetDistributedMap.containsKey(expDTO.getNewCode())) {
								throw new NotFoundException(
										"Budget not found againce the FMR CODE[" + expDTO.getNewCode() + "] AT Row["
												+ (rowIndex + 1) + "] Cell[" + (cellIndex + 1) + "].");
							} else if (newCode.contains(expDTO.getNewCode())) {
								throw new DupblicateException(
										"New FMR CODE shoud not be dupblicate [" + expDTO.getNewCode() + "] AT Row["
												+ (rowIndex + 1) + "] Cell[" + (cellIndex + 1) + "].");
							} else {
								newCode.add(expDTO.getNewCode());
							}
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
							expDTO.setExpenditureAmt(expDTO.getExpenditureAmtInLakh()
									.multiply(new BigDecimal(100000).setScale(2, BigDecimal.ROUND_UP)));
							if (expDTO.getExpenditureAmtInLakh()
									.compareTo(budgetDistributedMap.get(expDTO.getNewCode()).getAmtInLakh()) > 0) {
								throw new IllegalArgumentException(
										"Expenditue amt shoud not be greater than the Budget Shared amt AT Row["
												+ (rowIndex + 1) + "] Cell[" + (cellIndex + 1) + "].");
							}
						} else {
							continue;
						}

					}
				} // End cell
				if (rowIndex > 3) {
					expDTO.setUnitName(unitName);
					expDTO.setYear(year);
					expDTO.setReportingMonth(reportingMonth);
					expDTO.setPrograsiveAmtInLakh(BigDecimal.ZERO);
					buildExpEntity(expDTO, expenditureEntities, budgetDistributedMap, recordStatusEntity);
					expDTO = null;
				} else {
					expDTO = null;
				}
			} // End row
			expenditureService.saveAll(expenditureEntities);
			// expenditureService.saveOrUpdate(expenditureEntities);
			logger.info("In uploadExpenditureDataFile() method End[" + LocalDateTime.now() + "]");

		} catch (Exception e) {
			logger.error("Exception in uploadExpenditureDataFile() method exception[" + e.getMessage() + "]");
			throw e;
		} finally {
			newCode = null;
			budgetDistributedMap = null;
			expenditureEntities = null;
			budgetDistributeList = null;
			recordStatusEntity = null;
			logger.info("Finally block in uploadExpenditureDataFile() method");
		}

	}

	/**
	 * This method build the <code>ExpenditureEntity</code> for save it.
	 * 
	 * @param expDTO               the
	 *                             <code>ExpenditureDTO<code> which is builded after read the one bye one excel row
	 * &#64;param expenditureEntities the List
	 * @param budgetDistributedMap the hold DB value with Key <code>New FRM code</code>and
	 *                             value is <code>BudgetSharedEntity</code>
	 * @param recordStatusEntity   the RecordStatusEntity;
	 * 
	 */
	private void buildExpEntity(ExpenditureDTO expDTO, List<ExpenditureEntity> expenditureEntities,
			HashMap<String, BudgetDistributeEntity> budgetDistributedMap, RecordStatusEntity recordStatusEntity) {
		try {
			logger.info("In buildExpEntity() method Start");
			logger.info("expDTO[" + expDTO + "]");
			auth = SecurityContextHolder.getContext().getAuthentication();
			
			// Building the UnitDtlEntity
			UnitEntity unitEntity = budgetDistributedMap.get(expDTO.getNewCode()).getUnitEntity();

			// Building the ActivityEntity
			ActivityEntity activityEntity = budgetDistributedMap.get(expDTO.getNewCode()).getActivityEntity();
			List<ExpenditureEntity> existExpntities = activityEntity.getExpenditureEntities();
			logger.info("existExpntities size=[" + existExpntities.size() + "]");
			ExpenditureEntity foundExistExpEntity = existExpntities.stream()
					.filter(existExpEntity -> (expDTO.getYear().equals(existExpEntity.getYear())
							&& expDTO.getReportingMonth() == existExpEntity.getReportingMonth()
							&& expDTO.getNewCode().equals(existExpEntity.getActivityEntity().getNewCode())))
					.findAny().orElse(null);

			if (foundExistExpEntity != null) {
				logger.info("=======Exist the Expenditure===If Block=======");
				// Building the existing ExpenditureEntity
				foundExistExpEntity.setTotAmt(expDTO.getExpenditureAmt());
				foundExistExpEntity.setTotAmtInLakh(expDTO.getExpenditureAmtInLakh());
				foundExistExpEntity.setzModifiedBy(auth.getName());
				foundExistExpEntity.setzModifiedDateAndTime(LocalDateTime.now());
				expenditureEntities.add(foundExistExpEntity);
			} else {
				// Building the new ExpenditureEntity
				logger.info("=======New Expenditure===Else Block=======");
				ExpenditureEntity expenditureEntity = new ExpenditureEntity();
				expenditureEntity.setUnitEntity(unitEntity);
				expenditureEntity.setActivityEntity(activityEntity);
				expenditureEntity.setRecordStatusEntity(recordStatusEntity);
				expenditureEntity.setYear(expDTO.getYear());
				expenditureEntity.setReportingMonth(expDTO.getReportingMonth());
				expenditureEntity.setTotAmt(expDTO.getExpenditureAmt());
				expenditureEntity.setTotAmtInLakh(expDTO.getExpenditureAmtInLakh());
				expenditureEntity.setyCreatedBy(auth.getName());
				expenditureEntity.setyCreatedDateAndTime(LocalDateTime.now());
				expenditureEntities.add(expenditureEntity);
			}
			logger.info("In buildExpEntity() method End");
		} catch (Exception e) {
			logger.error("Exception in buildExpEntity() method exception[" + e.getMessage() + "]");
			throw e;
		}
	}

	private List<BudgetDistributeEntity> findUnitNameAndYearAndMonth(Sheet sheet) throws NotFoundException {
		logger.info("In findUnitNameAndYearAndMonth() method Start");
		String unitName = null;
		String year = null;
		int reportingMonth = 0;
		Row row = sheet.getRow(2);

		int lastCell = row.getLastCellNum();
		for (int cellIndex = 0; cellIndex < lastCell; cellIndex++) {
			Cell cell = row.getCell(cellIndex);
			// fetch UnitName
			if (cellIndex == 0) {
				if(cell == null) {
					throw new NotFoundException(
							"The selected file is not correct format(Template), Please select correct template. ");
				}
				unitName = getStringValFromCell(cell);
				if (unitName == null) {
					throw new NotFoundException(
							"Unit name should not be blank :Row[" + (2 + 1) + "] Cell[" + (cellIndex + 1) + "]");
				}
			}
			// fetch year
			else if (cellIndex == 1) {
				year = getStringValFromCell(cell);
				if (year == null) {
					throw new NotFoundException(
							"Reporting year should not be blank :Row[" + (2 + 1) + "] Cell[" + (cellIndex + 1) + "]");
				}
			}
			// fetch reportingMonth
			else if (cellIndex == 2) {
				reportingMonth = getIntValFromCell(cell);
				if (reportingMonth == 0) {
					throw new NotFoundException(
							"Reporting Month should not be blank :Row[" + (2 + 1) + "] Cell[" + (cellIndex + 1) + "]");
				}
			} else {
				continue;
			}
		} // End for

		logger.info("Getting Budget Shared list Strat time[" + LocalDateTime.now() + "]");
		List<BudgetDistributeEntity> budgetDistributeList = expenditureService.findBudgetList(unitName, year);
		logger.info("Getting Budget Shared list End time[" + LocalDateTime.now() + "]");
		if (budgetDistributeList != null && budgetDistributeList.size() == 0) {
			throw new NotFoundException("No Budget found of UnitName[" + unitName + "] and Year[" + year + "].");
		}

		logger.info("In findUnitNameAndYearAndMonth() method End");
		return budgetDistributeList;

	}

	private boolean isExistUnitName(String unitName, UnitEntity unitEntity) throws NotFoundException {
		/*UnitEntity unitDtlEntity = unitEntity.stream().filter(x -> unitName.equals(x.getName())).findAny()
				.orElse(null);*/
		if(unitName == null ) {
			throw new NotFoundException(" Unit Name should not be null or blank [" + unitName + "] ");
		}
		if (unitEntity == null) {
			throw new NotFoundException(" Unit Name not found in DB. ");
		}
		return ( unitEntity != null || unitName.equalsIgnoreCase(unitEntity.getName())) ? true : false;
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
			returnVal = new BigDecimal(cell.getNumericCellValue()).setScale(2, BigDecimal.ROUND_UP);
			break;
		default:
			returnVal = BigDecimal.ZERO;
			break;
		}
		return returnVal;
	}

	private String getStringValFromCell(Cell cell) {
		String returnVal = "";
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			returnVal = cell.getStringCellValue();
			break;
		default:
			returnVal = "";
			break;
		}
		return returnVal.trim();
	}
}
