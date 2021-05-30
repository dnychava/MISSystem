package com.ikaustubh.missystem.helper;

import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.ikaustubh.missystem.dto.FMRReportDataDTO;
import com.ikaustubh.missystem.dto.FMRReportFilterDTO;
import com.ikaustubh.missystem.dto.FilterFormDataDTO;
import com.ikaustubh.missystem.dto.FinancialYearDTO;
import com.ikaustubh.missystem.dto.MonthDTO;
import com.ikaustubh.missystem.dto.ResponseDTO;
import com.ikaustubh.missystem.dto.UnitDTO;
import com.ikaustubh.missystem.dto.UnitDtlDTO;
import com.ikaustubh.missystem.entities.ActivityEntity;
import com.ikaustubh.missystem.entities.BudgetDistributeEntity;
import com.ikaustubh.missystem.entities.ExpenditureEntity;
import com.ikaustubh.missystem.entities.FinancialYearEntity;
import com.ikaustubh.missystem.entities.MainProgramHeadEntity;
import com.ikaustubh.missystem.entities.UnitEntity;
import com.ikaustubh.missystem.entities.UnitTypeEntity;
import com.ikaustubh.missystem.entities.UserInfoEntity;
import com.ikaustubh.missystem.repository.ActivityRepo;
import com.ikaustubh.missystem.repository.BudgetDistributeRepository;
import com.ikaustubh.missystem.repository.FinancialYearRepository;
import com.ikaustubh.missystem.repository.MainProgramHeadRepository;
import com.ikaustubh.missystem.repository.RecordStatusRepository;
import com.ikaustubh.missystem.repository.UnitTypeRepository;
import com.ikaustubh.missystem.repository.UserInfoRepository;
import com.ikaustubh.missystem.services.ExpenditureService;
import com.ikaustubh.missystem.utils.DateUtil;

@Component
public class FMRReportHelper {

	Logger logger = LoggerFactory.getLogger(FMRReportHelper.class);

	@Autowired
	private ExpenditureService expenditureServ;

	@Autowired
	private BudgetDistributeRepository budgetDistributeRepository;

	@Autowired
	private UnitTypeRepository unitTypeRepository;

	@Autowired
	private RecordStatusRepository recordStatusRepository;

	@Autowired
	private ActivityRepo activityRepository;

	@Autowired
	private FinancialYearRepository finalcialYearRepo;
	
	@Autowired
	private MainProgramHeadRepository mainPrgrHeadRepo;

	@Autowired
	private ResponseDTO responseDTO;

	@Autowired
	private FilterFormDataDTO filterFormDataDTO;
	
	private Authentication auth = null;
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	private UserInfoEntity userInfoEntity = null;

	private final String BG_COLOR_LEVEL_0 = "#ffcccc";
	private final String BG_COLOR_LEVEL_1 = "#8080ff";
	private final String BG_COLOR_LEVEL_2 = "#b3ccff";
	private final String BG_COLOR_LEVEL_3 = "#6c8ccf";
	private final String BG_COLOR_LEVEL_4 = "white";
	private final String BG_COLOR_LEVEL_5 = "white";

	public ResponseEntity<List<FMRReportDataDTO>> getFMRReport(FMRReportFilterDTO fmrReportFilterDTO) {

		logger.info(fmrReportFilterDTO.toString());
		List<FMRReportDataDTO> frmReportDataDTOList = new LinkedList<>();
		List<ActivityEntity> activityList = expenditureServ
				.getAllParentActivity(fmrReportFilterDTO.getMainProgramHeadRid(), fmrReportFilterDTO.getYear());
		
		logger.info(" activityList["+activityList.size()+"]");
		if(!activityList.isEmpty()) {
			Optional<MainProgramHeadEntity> mPHE = mainPrgrHeadRepo.findById(fmrReportFilterDTO.getMainProgramHeadRid());
			BigDecimal totExpInLakhLvl0 = new BigDecimal(0.00);
			BigDecimal totBudgetLvl0 = new BigDecimal(0.00);

			FMRReportDataDTO reportDataLvl0 = new FMRReportDataDTO();
			reportDataLvl0.setName(mPHE.get().getName());
			reportDataLvl0.setBgColor(BG_COLOR_LEVEL_0);
			frmReportDataDTOList.add(reportDataLvl0);
				
			Collections.sort(activityList);
			for (ActivityEntity actLvl1 : activityList) {// 1 level
				logger.info("=== Level 1 ===");
				logger.info("new code[" + actLvl1.getNewCode() + "]");
				BigDecimal totExpInLakhLvl1 = new BigDecimal(0.00);
				BigDecimal totBudgetLvl1 = new BigDecimal(0.00);

				// Building the <code>FMRReportDataDTO</code>
				FMRReportDataDTO reportDataLvl1 = new FMRReportDataDTO();
				buildFRMReportData(reportDataLvl1, actLvl1, BG_COLOR_LEVEL_1);
				frmReportDataDTOList.add(reportDataLvl1);
				
				List<ActivityEntity> childActList1 = actLvl1.getMainActivityEntities();
				Collections.sort( childActList1);
				if (!childActList1.isEmpty()) {
					for (ActivityEntity actLvl2 : childActList1) {// 2 level
						logger.info("=== Level 2 ===");
						logger.info("new code[" + actLvl2.getNewCode() + "]");
						
						// Building the <code>FMRReportDataDTO</code>
						FMRReportDataDTO reportDataLvl2 = new FMRReportDataDTO();
						buildFRMReportData(reportDataLvl2, actLvl2, BG_COLOR_LEVEL_2);
						BigDecimal totExpInLakhLvl2 = new BigDecimal(0.00);
						BigDecimal totBudgetLvl2 = new BigDecimal(0.00);
						frmReportDataDTOList.add(reportDataLvl2);
						
						List<ActivityEntity> childActList2 = actLvl2.getMainActivityEntities();
						Collections.sort( childActList2);
						if (!childActList2.isEmpty()) {
							for (ActivityEntity actLvl3 : childActList2) {// 3 level
								logger.info("=== Level 3 ===");
								logger.info("new code[" + actLvl3.getNewCode() + "]");
								
								// Building the <code>FMRReportDataDTO</code>
								FMRReportDataDTO reportDataLvl3 = new FMRReportDataDTO();
								buildFRMReportData(reportDataLvl3, actLvl3, BG_COLOR_LEVEL_3);
								BigDecimal totExpInLakhLvl3 = new BigDecimal(0.00);
								BigDecimal totBudgetLvl3 = new BigDecimal(0.00);
								frmReportDataDTOList.add(reportDataLvl3);
								
								List<ActivityEntity> childActList3 = actLvl3.getMainActivityEntities();
								Collections.sort( childActList3);
								if (!childActList3.isEmpty()) {
									//Fetch the expenditure Data;
									Map<String, BigDecimal> expenditureDataHM = getExpenditureData(childActList3, fmrReportFilterDTO);
									
									//Fetch the budget shared Data;
									Map<String, BigDecimal> budgetDataHM = getBudgetData(childActList3, fmrReportFilterDTO);
									
									for (ActivityEntity actLvl4 : childActList3) {// 4 level
										logger.info("=== Level 4 ===");
										logger.info("new code[" + actLvl4.getNewCode() + "]");
										
										BigDecimal totExpInLakhLvl4 = new BigDecimal(0.00);
										BigDecimal totBudgetLvl4 = new BigDecimal(0.00);
										
										//Expenditure amount
										BigDecimal expAmt = expenditureDataHM.get(actLvl4.getNewCode()) == null ? BigDecimal.ZERO : expenditureDataHM.get(actLvl4.getNewCode());
										
										//Expenditure amount
										BigDecimal budgetAmt = budgetDataHM.get(actLvl4.getNewCode()) == null ? BigDecimal.ZERO : budgetDataHM.get(actLvl4.getNewCode());
										
										// Building the <code>FMRReportDataDTO</code>
										FMRReportDataDTO reportDataLvl4 = new FMRReportDataDTO();
										buildFRMReportData(reportDataLvl4, actLvl4, BG_COLOR_LEVEL_4);
										reportDataLvl4.setTotExpenditureAmtInLakh(expAmt);
										reportDataLvl4.setTotBudgetAmtInLakh(budgetAmt);
										
										frmReportDataDTOList.add(reportDataLvl4);
										
										List<ActivityEntity> childActList4 = actLvl4.getMainActivityEntities();
										Collections.sort(childActList4);
										if (!childActList4.isEmpty()) {
											//Fetch the expenditure Data;
											Map<String, BigDecimal> expenditureDataHM5 = getExpenditureData(childActList4, fmrReportFilterDTO);
											
											//Fetch the budget shared Data;
											Map<String, BigDecimal> budgetDataHM5 = getBudgetData(childActList4, fmrReportFilterDTO);
											
											for (ActivityEntity actLvl5 : childActList4) {// 5 level
												logger.info("=== Level 5 ===");
												logger.info("new code[" + actLvl5.getNewCode() + "]");
												BigDecimal totExpInLakhLvl5 = new BigDecimal(0.00);
												BigDecimal totBudgetLvl5 = new BigDecimal(0.00);
												
												//Expenditure amount
												BigDecimal expAmt5 = expenditureDataHM5.get(actLvl5.getNewCode()) == null ? BigDecimal.ZERO : expenditureDataHM5.get(actLvl5.getNewCode());
												
												//Budget amount
												BigDecimal budgetAmt5 = budgetDataHM5.get(actLvl5.getNewCode()) == null ? BigDecimal.ZERO : budgetDataHM5.get(actLvl5.getNewCode());
												
												// Building the <code>FMRReportDataDTO</code>
												FMRReportDataDTO reportDataLvl5 = new FMRReportDataDTO();
												buildFRMReportData(reportDataLvl5, actLvl5, BG_COLOR_LEVEL_5);
												reportDataLvl5.setTotExpenditureAmtInLakh(expAmt5);
												reportDataLvl5.setTotBudgetAmtInLakh(budgetAmt5);
												frmReportDataDTOList.add(reportDataLvl5);
												
												//calculating total amt
												totExpInLakhLvl4 = totExpInLakhLvl4.add(reportDataLvl5.getTotExpenditureAmtInLakh());
												totBudgetLvl4 = totBudgetLvl4.add(reportDataLvl5.getTotBudgetAmtInLakh());
												
												List<ActivityEntity> childActList5 = actLvl5.getMainActivityEntities();
												if (childActList5.isEmpty()) {
													logger.info("=== Child list is empty ===");
												}
											}//end for level 5
											
											//Setting total calculated amount
											reportDataLvl4.setTotExpenditureAmtInLakh(totExpInLakhLvl4);
											reportDataLvl4.setTotBudgetAmtInLakh(totBudgetLvl4);
										}
										totExpInLakhLvl3 = totExpInLakhLvl3.add(reportDataLvl4.getTotExpenditureAmtInLakh());
										totBudgetLvl3 = totBudgetLvl3.add(reportDataLvl4.getTotBudgetAmtInLakh());
										
									}//end for level 4
								}
								reportDataLvl3.setTotExpenditureAmtInLakh(totExpInLakhLvl3);
								reportDataLvl3.setTotBudgetAmtInLakh(totBudgetLvl3);
								totExpInLakhLvl2 = totExpInLakhLvl2.add(totExpInLakhLvl3);
								totBudgetLvl2 = totBudgetLvl2.add(totBudgetLvl3);
							}//end for level 3
						}
						reportDataLvl2.setTotExpenditureAmtInLakh(totExpInLakhLvl2);
						reportDataLvl2.setTotBudgetAmtInLakh(totBudgetLvl2);
						
						totExpInLakhLvl1 = totExpInLakhLvl1.add(totExpInLakhLvl2);
						totBudgetLvl1 = totBudgetLvl1.add(totBudgetLvl2);
					}//end for level 2
				}
				reportDataLvl1.setTotExpenditureAmtInLakh(totExpInLakhLvl1);
				reportDataLvl1.setTotBudgetAmtInLakh(totBudgetLvl1);
				
				totExpInLakhLvl0 = totExpInLakhLvl0.add(totExpInLakhLvl1);
				totBudgetLvl0 = totBudgetLvl0.add(totBudgetLvl1);
			} // end for level 1
			reportDataLvl0.setTotExpenditureAmtInLakh(totExpInLakhLvl0);
			reportDataLvl0.setTotBudgetAmtInLakh(totBudgetLvl0);
		}

		/*
		 * 
		 * MainProgramHeadEntity mainProgramHeadEntity = mainProgramHeadRepository
		 * .findByIdAndYear(fmrReportFilterDTO.getMainProgramHeadRid(),
		 * fmrReportFilterDTO.getYear());
		 * 
		 * List<FMRReportDataDTO> frmReportDataDTOList = new
		 * ArrayList<FMRReportDataDTO>(); if (mainProgramHeadEntity != null) {
		 * List<UnitEntity> unitEntities; if (fmrReportFilterDTO.getUnitRid() == 0) {
		 * unitEntities = unitRepository.findAll(); } else { unitEntities = new
		 * ArrayList<UnitEntity>(); Optional<UnitEntity> unitEntity =
		 * unitRepository.findById(fmrReportFilterDTO.getUnitRid());
		 * unitEntities.add(unitEntity.get()); }
		 * 
		 * FMRReportDataDTO mainHead = new FMRReportDataDTO();
		 * mainHead.setName(mainProgramHeadEntity.getName());
		 * mainHead.setBgColor("#ffcccc");
		 * 
		 * frmReportDataDTOList.add(mainHead);
		 * 
		 * 
		 * Calculate totBudgetAmtInLakh and totBudgetAmtInLakh for <code>Unit</code>
		 * 
		 * BigDecimal totExpAmtInLakhOfU = BigDecimal.ZERO; BigDecimal
		 * totBudgetAmtInLakhOfU = BigDecimal.ZERO;
		 * 
		 * for (UnitEntity unitEntity : unitEntities) {// For each for Unit
		 * fmrReportFilterDTO.setUnitRid(unitEntity.getRid());
		 * 
		 * Building UnitDTO.
		 * 
		 * 
		 * FMRReportDataDTO unit = new FMRReportDataDTO();
		 * unit.setName(unitEntity.getUnitDtlsEntity().get(0).getName());
		 * unit.setBgColor("#c2c2a3"); frmReportDataDTOList.add(unit);
		 * 
		 * Calculate totBudgetAmtInLakh and totBudgetAmtInLakh for
		 * <code>MainProgram</code>
		 * 
		 * BigDecimal totExpAmtInLakhOfMP = BigDecimal.ZERO; BigDecimal
		 * totBudgetAmtInLakhOfMP = BigDecimal.ZERO;
		 * 
		 * Building MainProgramReportDTO
		 * 
		 * List<MainProgramEntity> mainPrgmEntities = new ArrayList<MainProgramEntity>(
		 * mainProgramHeadEntity.getMainProgramEntity());
		 * Collections.sort(mainPrgmEntities); for (MainProgramEntity mainProgramEntity
		 * : mainPrgmEntities) {
		 * 
		 * Building MainProgramReportDTO
		 * 
		 * 
		 * FMRReportDataDTO mainProgram = new FMRReportDataDTO();
		 * mainProgram.setName(mainProgramEntity.getName());
		 * mainProgram.setNewFMRCode(mainProgramEntity.getNewCode());
		 * mainProgram.setOldFMRCode(mainProgramEntity.getOldCode());
		 * mainProgram.setBgColor("#8080ff"); frmReportDataDTOList.add(mainProgram);
		 * 
		 * Calculate totBudgetAmtInLakh and totBudgetAmtInLakh for
		 * <code>SubProgram</code>
		 * 
		 * BigDecimal totExpAmtInLakhOfSP = BigDecimal.ZERO; BigDecimal
		 * totBudgetAmtInLakhOfSP = BigDecimal.ZERO;
		 * 
		 * List<MainSubProgramEntity> subPrgmEntities = new
		 * ArrayList<MainSubProgramEntity>(
		 * mainProgramEntity.getMainSubProgramEntity());
		 * Collections.sort(subPrgmEntities); for (MainSubProgramEntity subProgramEntity
		 * : subPrgmEntities) {
		 * 
		 * Building MainSubProgramReportDTO
		 * 
		 * 
		 * FMRReportDataDTO subProgram = new FMRReportDataDTO();
		 * subProgram.setName(subProgramEntity.getName());
		 * subProgram.setNewFMRCode(subProgramEntity.getNewCode());
		 * subProgram.setOldFMRCode(subProgramEntity.getOldCode());
		 * subProgram.setBgColor("#b3ccff");
		 * 
		 * frmReportDataDTOList.add(subProgram);
		 * 
		 * Calculate totBudgetAmtInLakh and totBudgetAmtInLakh for
		 * <code>SubActivity</code>
		 * 
		 * BigDecimal totExpAmtInLakhOfMA = BigDecimal.ZERO; BigDecimal
		 * totBudgetAmtInLakhOfMA = BigDecimal.ZERO;
		 * 
		 * List<MainActivityEntity> mainActivityEntities = new
		 * ArrayList<MainActivityEntity>( subProgramEntity.getMainActivityEntity());
		 * Collections.sort(mainActivityEntities); for (MainActivityEntity
		 * mainActivityEntity : mainActivityEntities) {
		 * 
		 * Building MainSubProgramReportDTO
		 * 
		 * 
		 * FMRReportDataDTO mainActivity = new FMRReportDataDTO();
		 * mainActivity.setName(mainActivityEntity.getName());
		 * mainActivity.setNewFMRCode(mainActivityEntity.getNewCode());
		 * mainActivity.setOldFMRCode(mainActivityEntity.getOldCode());
		 * frmReportDataDTOList.add(mainActivity);
		 * 
		 * 
		 * Calculate totBudgetAmtInLakh and totBudgetAmtInLakh for
		 * <code>SubActivity</code>
		 * 
		 * BigDecimal totExpAmtInLakhOfSA = BigDecimal.ZERO; BigDecimal
		 * totBudgetAmtInLakhOfSA = BigDecimal.ZERO;
		 * 
		 * Set<MainSubActivityEntity> subActivityEntitiesHS = mainActivityEntity
		 * .getMainSubActivityEntity(); List<MainSubActivityEntity> subActivityEntities
		 * = new ArrayList<MainSubActivityEntity>( subActivityEntitiesHS);
		 * Collections.sort(subActivityEntities); Map<Long, ExpenditureWrapper>
		 * expenditureDataMap = expenditureRepository
		 * .getPrograssiveAmtAndBudget(unitEntity.getRid(),
		 * fmrReportFilterDTO.getYear(), getAllSubActivityRids(subActivityEntitiesHS),
		 * true, fmrReportFilterDTO.getReportingMonth() ); for (MainSubActivityEntity
		 * subActivityEntity : subActivityEntities) {
		 * 
		 * Building MainSubProgramReportDTO
		 * 
		 * 
		 * FMRReportDataDTO subActivity = new FMRReportDataDTO();
		 * subActivity.setName(subActivityEntity.getName());
		 * subActivity.setNewFMRCode(subActivityEntity.getNewCode());
		 * subActivity.setOldFMRCode(subActivityEntity.getOldCode());
		 * frmReportDataDTOList.add(subActivity);
		 * 
		 * ExpenditureWrapper expenditureWrapper = expenditureDataMap
		 * .get(subActivityEntity.getRid()); if (expenditureWrapper != null) {
		 * 
		 * subActivity
		 * .setTotExpenditureAmtInLakh(expenditureWrapper.getProgressiveAmtInLakh());
		 * subActivity.setTotBudgetAmtInLakh(expenditureWrapper.getBudgetAmtInLakh());
		 * 
		 * } else { subActivity.setTotExpenditureAmtInLakh(BigDecimal.ZERO);
		 * subActivity.setTotBudgetAmtInLakh(BigDecimal.ZERO);
		 * 
		 * }
		 * 
		 * 
		 * Calculating <code>SubActivity</code>
		 * 
		 * totExpAmtInLakhOfSA = totExpAmtInLakhOfSA
		 * .add(subActivity.getTotExpenditureAmtInLakh()); totBudgetAmtInLakhOfSA =
		 * totBudgetAmtInLakhOfSA .add(subActivity.getTotBudgetAmtInLakh());
		 * 
		 * } // End for of subActivityEntity
		 * 
		 * 
		 * Calculating <code>MainActivity</code>
		 * 
		 * totBudgetAmtInLakhOfMA = totBudgetAmtInLakhOfMA.add(totBudgetAmtInLakhOfSA);
		 * totExpAmtInLakhOfMA = totExpAmtInLakhOfMA.add(totExpAmtInLakhOfSA);
		 * 
		 * mainActivity.setTotBudgetAmtInLakh(totBudgetAmtInLakhOfSA);
		 * mainActivity.setTotExpenditureAmtInLakh(totExpAmtInLakhOfSA);
		 * 
		 * } // End for of mainActivityEntity
		 * 
		 * 
		 * Calculating <code>SubProgram</code>
		 * 
		 * totExpAmtInLakhOfSP = totExpAmtInLakhOfSP.add(totExpAmtInLakhOfMA);
		 * totBudgetAmtInLakhOfSP = totBudgetAmtInLakhOfSP.add(totBudgetAmtInLakhOfMA);
		 * 
		 * subProgram.setTotBudgetAmtInLakh(totBudgetAmtInLakhOfMA);
		 * subProgram.setTotExpenditureAmtInLakh(totExpAmtInLakhOfMA);
		 * 
		 * } // End for of MainSubProgramEntity
		 * 
		 * 
		 * Calculating <code>MainProgram</code>
		 * 
		 * totExpAmtInLakhOfMP = totExpAmtInLakhOfMP.add(totExpAmtInLakhOfSP);
		 * totBudgetAmtInLakhOfMP = totBudgetAmtInLakhOfMP.add(totBudgetAmtInLakhOfSP);
		 * 
		 * mainProgram.setTotExpenditureAmtInLakh(totExpAmtInLakhOfSP);
		 * mainProgram.setTotBudgetAmtInLakh(totBudgetAmtInLakhOfSP);
		 * 
		 * } // End for of MainProgramEntity
		 * 
		 * 
		 * Calculating <code>Unit</code>
		 * 
		 * totExpAmtInLakhOfU = totExpAmtInLakhOfU.add(totExpAmtInLakhOfMP);
		 * totBudgetAmtInLakhOfU = totBudgetAmtInLakhOfU.add(totBudgetAmtInLakhOfMP);
		 * 
		 * // unit.setTotBudgetAmtInLakh(totBudgetAmtInLakhOfMP);
		 * unit.setTotExpenditureAmtInLakh(totExpAmtInLakhOfMP); } // End for of
		 * UnitEntity
		 * 
		 * 
		 * mainHead.setTotBudgetAmtInLakh(totBudgetAmtInLakhOfU);
		 * mainHead.setTotExpenditureAmtInLakh(totExpAmtInLakhOfU) } return new
		 * ResponseEntity<List<FMRReportDataDTO>>(frmReportDataDTOList, HttpStatus.OK);
		 */
		return new ResponseEntity<List<FMRReportDataDTO>>(frmReportDataDTOList, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param childActList
	 * @param fmrReportFilterDTO
	 * @return
	 */
	private Map<String, BigDecimal> getBudgetData(List<ActivityEntity> childActList, FMRReportFilterDTO fmrReportFilterDTO) {
		Map<String, BigDecimal> newCodeAndExpAmt = new HashMap<String, BigDecimal>(); 
		List<Long> activityRidList = new LinkedList<>();
		for (ActivityEntity activityEntity : childActList) {
			activityRidList.add( activityEntity.getRid() );
		}
		List<BudgetDistributeEntity> expResultList =  expenditureServ.getAllBudgetSharedUsingFilter(fmrReportFilterDTO, activityRidList);
		for (BudgetDistributeEntity budgetDistributeEntity : expResultList) {
			newCodeAndExpAmt.put(budgetDistributeEntity.getActivityEntity().getNewCode(), budgetDistributeEntity.getAmtInLakh());
		}
		return newCodeAndExpAmt;
	}
	
	/**
	 * 
	 * @param childActList
	 * @param fmrReportFilterDTO
	 * @return
	 */
	private Map<String, BigDecimal> getExpenditureData(List<ActivityEntity> childActList, FMRReportFilterDTO fmrReportFilterDTO) {
		Map<String, BigDecimal> newCodeAndExpAmt = new HashMap<String, BigDecimal>(); 
		List<Long> activityRidList = new LinkedList<>();
		for (ActivityEntity activityEntity : childActList) {
			activityRidList.add( activityEntity.getRid() );
		}
		List<ExpenditureEntity> expResultList =  expenditureServ.getAllExpenditureUsingFilter(fmrReportFilterDTO, activityRidList);
		for (ExpenditureEntity expenditureEntity : expResultList) {
			newCodeAndExpAmt.put(expenditureEntity.getActivityEntity().getNewCode(), expenditureEntity.getTotAmtInLakh());
		}
		return newCodeAndExpAmt;
	}

	public void buildFRMReportData(FMRReportDataDTO reportDataLvl1, ActivityEntity activity, String color) {
		reportDataLvl1.setName(activity.getName());
		reportDataLvl1.setNewFMRCode(activity.getNewCode());
		reportDataLvl1.setOldFMRCode(activity.getOldCode());
		reportDataLvl1.setBgColor(color);
	}

	public FilterFormDataDTO getFillterFormData() {
		try {
			this.auth = SecurityContextHolder.getContext().getAuthentication();
			this.userInfoEntity = userInfoRepository.findByUsername(auth.getName());
			logger.info("In getFillterFormData() method");
			LinkedList<FinancialYearDTO> financialYearList = new LinkedList<>();
			LinkedList<MonthDTO> monthList = new LinkedList<>();
			LinkedList<UnitDTO> unitGroupList = new LinkedList<UnitDTO>();

			// Building financial DTO
			List<FinancialYearEntity> finalYears = finalcialYearRepo.findAll();
			for (FinancialYearEntity financialYearEntity : finalYears) {
				FinancialYearDTO financialYearDTO = new FinancialYearDTO();
				financialYearDTO.setKey(financialYearEntity.getYear());
				financialYearDTO.setValue(financialYearEntity.getYear());
				financialYearList.add(financialYearDTO);
			}

			// Building month DTO
			String[] months = new DateFormatSymbols().getShortMonths();
			for (String monthStr : months) {
				logger.info("monthStr[" + monthStr + "]");
				MonthDTO monthDTO = new MonthDTO();
				monthDTO.setKey("" + DateUtil.getMonthInt(monthStr));
				monthDTO.setValue(monthStr);
				monthList.add(monthDTO);
			}

			// Building Unit list
			
			
			buildUnit(unitGroupList, this.userInfoEntity.getUnitEntity());
			logger.info("unitGroupList ["+unitGroupList+"]");
			/*List<UnitTypeEntity> unitEntityList = unitTypeRepository.findAll();
			for (UnitTypeEntity unitTypeEntity : unitEntityList) {
				UnitDTO unitDTO = new UnitDTO();
				unitDTO.setKey("" + unitTypeEntity.getRid());
				unitDTO.setValue(unitTypeEntity.getName());

				LinkedList<UnitDTO> unitDtlList = new LinkedList<UnitDTO>();
				Set<UnitEntity> unitEntitySet = unitTypeEntity.getUnitEntity();
				for (UnitEntity unitEntity : unitEntitySet) {
					UnitDTO unitDtlDTO = new UnitDTO();
					unitDtlDTO.setKey("" + unitEntity.getRid());
					unitDtlDTO.setValue(unitEntity.getName());
					unitDtlList.add(unitDtlDTO);
				}
				unitDTO.setUnitList(unitDtlList);
				unitGroupList.add(unitDTO);
			}*/

			filterFormDataDTO.setFinancialYearList(financialYearList);
			filterFormDataDTO.setMonthList(monthList);
			filterFormDataDTO.setUnitGroupList(unitGroupList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
		return filterFormDataDTO;
	}
	
	/**
	 * 
	 * @param unitGroupList
	 * @param unitEntity
	 */
	public void buildUnit(LinkedList<UnitDTO> unitGroupList, UnitEntity unitEntity) {
		if(unitEntity != null ) {
			logger.info("in buildUnit method");
			//Level 1.
			logger.info("1st unitName["+unitEntity.getName()+"] unitType["+unitEntity.getUnitTypeEntity().getName()+"]" );
			UnitDTO unitDto = new UnitDTO();
			unitDto.setKey(""+unitEntity.getRid());
			unitDto.setValue(unitEntity.getName());
			//Level 2.
			if( unitEntity.getMainUnitEntities() != null && unitEntity.getMainUnitEntities().size() != 0 ) {
				LinkedList<UnitDTO> unitList2 = new LinkedList<UnitDTO>();
				for (UnitEntity childUnit2 : unitEntity.getMainUnitEntities()) {
					logger.info("    |---- 2nd unitName["+childUnit2.getName()+"] unitType["+childUnit2.getUnitTypeEntity().getName()+"]" );
					UnitDTO unitDto2 = new UnitDTO();
					unitDto2.setKey(""+childUnit2.getRid());
					unitDto2.setValue(childUnit2.getName());
					unitList2.add(unitDto2);
					//Level3
					if( childUnit2.getMainUnitEntities() != null && childUnit2.getMainUnitEntities().size() != 0 ) {
						LinkedList<UnitDTO> unitList3 = new LinkedList<UnitDTO>();
						for (UnitEntity childUnit3 : childUnit2.getMainUnitEntities()) {
							logger.info("        |---- 3rd unitName["+childUnit3.getName()+"] unitType["+childUnit3.getUnitTypeEntity().getName()+"]" );
							UnitDTO unitDto3 = new UnitDTO();
							unitDto3.setKey(""+childUnit3.getRid());
							unitDto3.setValue(childUnit3.getName());
							unitList3.add(unitDto3);
							//Level4
							if( childUnit3.getMainUnitEntities() != null && childUnit3.getMainUnitEntities().size() != 0 ) {
								LinkedList<UnitDTO> unitList4 = new LinkedList<UnitDTO>();
								for (UnitEntity childUnit4 : childUnit3.getMainUnitEntities()) {
									logger.info("            |---- 4th unitName["+childUnit4.getName()+"] unitType["+childUnit4.getUnitTypeEntity().getName()+"]" );
									UnitDTO unitDto4 = new UnitDTO();
									unitDto4.setKey(""+childUnit4.getRid());
									unitDto4.setValue(childUnit4.getName());
									unitList4.add(unitDto4);
								}
								unitDto3.setUnitList(unitList4);
							}//End 4th level if
						}//end 3rd level for
						unitDto2.setUnitList(unitList3);
					}//End 3rd level if
				}//End 2nd level for
				unitDto.setUnitList(unitList2);
			}//End 2nd level if
			unitGroupList.add(unitDto);
			logger.info(" final unit list ["+unitGroupList+"]" );
			
		}else {
			System.out.println("Not in child object");
		}
		 
	}

}