package com.ikaustubh.missystem.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.ikaustubh.missystem.dto.FMRReportDataDTO;
import com.ikaustubh.missystem.dto.FMRReportFilterDTO;
import com.ikaustubh.missystem.dto.ResponseDTO;
import com.ikaustubh.missystem.entities.MainActivityEntity;
import com.ikaustubh.missystem.entities.MainProgramEntity;
import com.ikaustubh.missystem.entities.MainProgramHeadEntity;
import com.ikaustubh.missystem.entities.MainSubActivityEntity;
import com.ikaustubh.missystem.entities.MainSubProgramEntity;
import com.ikaustubh.missystem.entities.UnitEntity;
import com.ikaustubh.missystem.repository.BudgetDistributeRepository;
import com.ikaustubh.missystem.repository.ExpenditureRepository;
import com.ikaustubh.missystem.repository.MainProgramHeadRepository;
import com.ikaustubh.missystem.repository.MainSubActivityRepository;
import com.ikaustubh.missystem.repository.RecordStatusRepository;
import com.ikaustubh.missystem.repository.UnitRepository;
import com.ikaustubh.missystem.wrapper.ExpenditureWrapper;

@Component
public class FMRReportHelper {

	@Autowired
	private ExpenditureRepository expenditureRepository;

	@Autowired
	private BudgetDistributeRepository budgetDistributeRepository;

	@Autowired
	private UnitRepository unitRepository;

	@Autowired
	private RecordStatusRepository recordStatusRepository;

	@Autowired
	private MainSubActivityRepository mainSubActivityRepository;

	@Autowired
	private MainProgramHeadRepository mainProgramHeadRepository;

	@Autowired
	ResponseDTO responseDTO;

	public ResponseEntity<List<FMRReportDataDTO>> getFMRReport(FMRReportFilterDTO fmrReportFilterDTO) {

		MainProgramHeadEntity mainProgramHeadEntity = mainProgramHeadRepository
				.findByIdAndYear(fmrReportFilterDTO.getMainProgramHeadRid(), fmrReportFilterDTO.getYear());

		List<FMRReportDataDTO> frmReportDataDTOList = new ArrayList<FMRReportDataDTO>();
		if (mainProgramHeadEntity != null) {
			List<UnitEntity> unitEntities;
			if (fmrReportFilterDTO.getUnitRid() == 0) {
				unitEntities = unitRepository.findAll();
			} else {
				unitEntities = new ArrayList<UnitEntity>();
				Optional<UnitEntity> unitEntity = unitRepository.findById(fmrReportFilterDTO.getUnitRid());
				unitEntities.add(unitEntity.get());
			}

			FMRReportDataDTO mainHead = new FMRReportDataDTO();
			mainHead.setName(mainProgramHeadEntity.getName());
			mainHead.setBgColor("#ffcccc");

			frmReportDataDTOList.add(mainHead);

			/*
			 * Calculate totBudgetAmtInLakh and totBudgetAmtInLakh for <code>Unit</code>
			 */
			BigDecimal totExpAmtInLakhOfU = BigDecimal.ZERO;
			BigDecimal totBudgetAmtInLakhOfU = BigDecimal.ZERO;

			for (UnitEntity unitEntity : unitEntities) {// For each for Unit
				fmrReportFilterDTO.setUnitRid(unitEntity.getRid());
				/*
				 * Building UnitDTO.
				 */

				FMRReportDataDTO unit = new FMRReportDataDTO();
				unit.setName(unitEntity.getUnitDtlsEntity().get(0).getName());
				unit.setBgColor("#c2c2a3");
				frmReportDataDTOList.add(unit);
				/*
				 * Calculate totBudgetAmtInLakh and totBudgetAmtInLakh for
				 * <code>MainProgram</code>
				 */
				BigDecimal totExpAmtInLakhOfMP = BigDecimal.ZERO;
				BigDecimal totBudgetAmtInLakhOfMP = BigDecimal.ZERO;
				/*
				 * Building MainProgramReportDTO
				 */
				List<MainProgramEntity> mainPrgmEntities = new ArrayList<MainProgramEntity>(
						mainProgramHeadEntity.getMainProgramEntity());
				Collections.sort(mainPrgmEntities);
				for (MainProgramEntity mainProgramEntity : mainPrgmEntities) {
					/*
					 * Building MainProgramReportDTO
					 */

					FMRReportDataDTO mainProgram = new FMRReportDataDTO();
					mainProgram.setName(mainProgramEntity.getName());
					mainProgram.setNewFMRCode(mainProgramEntity.getNewCode());
					mainProgram.setOldFMRCode(mainProgramEntity.getOldCode());
					mainProgram.setBgColor("#8080ff");
					frmReportDataDTOList.add(mainProgram);
					/*
					 * Calculate totBudgetAmtInLakh and totBudgetAmtInLakh for
					 * <code>SubProgram</code>
					 */
					BigDecimal totExpAmtInLakhOfSP = BigDecimal.ZERO;
					BigDecimal totBudgetAmtInLakhOfSP = BigDecimal.ZERO;

					List<MainSubProgramEntity> subPrgmEntities = new ArrayList<MainSubProgramEntity>(
							mainProgramEntity.getMainSubProgramEntity());
					Collections.sort(subPrgmEntities);
					for (MainSubProgramEntity subProgramEntity : subPrgmEntities) {
						/*
						 * Building MainSubProgramReportDTO
						 */

						FMRReportDataDTO subProgram = new FMRReportDataDTO();
						subProgram.setName(subProgramEntity.getName());
						subProgram.setNewFMRCode(subProgramEntity.getNewCode());
						subProgram.setOldFMRCode(subProgramEntity.getOldCode());
						subProgram.setBgColor("#b3ccff");

						frmReportDataDTOList.add(subProgram);
						/*
						 * Calculate totBudgetAmtInLakh and totBudgetAmtInLakh for
						 * <code>SubActivity</code>
						 */
						BigDecimal totExpAmtInLakhOfMA = BigDecimal.ZERO;
						BigDecimal totBudgetAmtInLakhOfMA = BigDecimal.ZERO;

						List<MainActivityEntity> mainActivityEntities = new ArrayList<MainActivityEntity>(
								subProgramEntity.getMainActivityEntity());
						Collections.sort(mainActivityEntities);
						for (MainActivityEntity mainActivityEntity : mainActivityEntities) {
							/*
							 * Building MainSubProgramReportDTO
							 */

							FMRReportDataDTO mainActivity = new FMRReportDataDTO();
							mainActivity.setName(mainActivityEntity.getName());
							mainActivity.setNewFMRCode(mainActivityEntity.getNewCode());
							mainActivity.setOldFMRCode(mainActivityEntity.getOldCode());
							frmReportDataDTOList.add(mainActivity);

							/*
							 * Calculate totBudgetAmtInLakh and totBudgetAmtInLakh for
							 * <code>SubActivity</code>
							 */
							BigDecimal totExpAmtInLakhOfSA = BigDecimal.ZERO;
							BigDecimal totBudgetAmtInLakhOfSA = BigDecimal.ZERO;

							Set<MainSubActivityEntity> subActivityEntitiesHS = mainActivityEntity
									.getMainSubActivityEntity();
							List<MainSubActivityEntity> subActivityEntities = new ArrayList<MainSubActivityEntity>(
									subActivityEntitiesHS);
							Collections.sort(subActivityEntities);
							Map<Long, ExpenditureWrapper> expenditureDataMap = expenditureRepository
									.getPrograssiveAmtAndBudget(unitEntity.getRid(), fmrReportFilterDTO.getYear(),
											getAllSubActivityRids(subActivityEntitiesHS), true, fmrReportFilterDTO.getReportingMonth() );
							for (MainSubActivityEntity subActivityEntity : subActivityEntities) {
								/*
								 * Building MainSubProgramReportDTO
								 */

								FMRReportDataDTO subActivity = new FMRReportDataDTO();
								subActivity.setName(subActivityEntity.getName());
								subActivity.setNewFMRCode(subActivityEntity.getNewCode());
								subActivity.setOldFMRCode(subActivityEntity.getOldCode());
								frmReportDataDTOList.add(subActivity);

								ExpenditureWrapper expenditureWrapper = expenditureDataMap
										.get(subActivityEntity.getRid());
								if (expenditureWrapper != null) {

									subActivity
											.setTotExpenditureAmtInLakh(expenditureWrapper.getProgressiveAmtInLakh());
									subActivity.setTotBudgetAmtInLakh(expenditureWrapper.getBudgetAmtInLakh());

								} else {
									subActivity.setTotExpenditureAmtInLakh(BigDecimal.ZERO);
									subActivity.setTotBudgetAmtInLakh(BigDecimal.ZERO);
									
								}

								/*
								 * Calculating <code>SubActivity</code>
								 */
								totExpAmtInLakhOfSA = totExpAmtInLakhOfSA
										.add(subActivity.getTotExpenditureAmtInLakh());
								totBudgetAmtInLakhOfSA = totBudgetAmtInLakhOfSA
										.add(subActivity.getTotBudgetAmtInLakh());

							} // End for of subActivityEntity

							/*
							 * Calculating <code>MainActivity</code>
							 */
							totBudgetAmtInLakhOfMA = totBudgetAmtInLakhOfMA.add(totBudgetAmtInLakhOfSA);
							totExpAmtInLakhOfMA = totExpAmtInLakhOfMA.add(totExpAmtInLakhOfSA);

							mainActivity.setTotBudgetAmtInLakh(totBudgetAmtInLakhOfSA);
							mainActivity.setTotExpenditureAmtInLakh(totExpAmtInLakhOfSA);

						} // End for of mainActivityEntity

						/*
						 * Calculating <code>SubProgram</code>
						 */
						totExpAmtInLakhOfSP = totExpAmtInLakhOfSP.add(totExpAmtInLakhOfMA);
						totBudgetAmtInLakhOfSP = totBudgetAmtInLakhOfSP.add(totBudgetAmtInLakhOfMA);

						subProgram.setTotBudgetAmtInLakh(totBudgetAmtInLakhOfMA);
						subProgram.setTotExpenditureAmtInLakh(totExpAmtInLakhOfMA);

					} // End for of MainSubProgramEntity

					/*
					 * Calculating <code>MainProgram</code>
					 */
					totExpAmtInLakhOfMP = totExpAmtInLakhOfMP.add(totExpAmtInLakhOfSP);
					totBudgetAmtInLakhOfMP = totBudgetAmtInLakhOfMP.add(totBudgetAmtInLakhOfSP);

					mainProgram.setTotExpenditureAmtInLakh(totExpAmtInLakhOfSP);
					mainProgram.setTotBudgetAmtInLakh(totBudgetAmtInLakhOfSP);

				} // End for of MainProgramEntity

				/*
				 * Calculating <code>Unit</code>
				 */
				totExpAmtInLakhOfU = totExpAmtInLakhOfU.add(totExpAmtInLakhOfMP);
				totBudgetAmtInLakhOfU = totBudgetAmtInLakhOfU.add(totBudgetAmtInLakhOfMP);

				//
				unit.setTotBudgetAmtInLakh(totBudgetAmtInLakhOfMP);
				unit.setTotExpenditureAmtInLakh(totExpAmtInLakhOfMP);
			} // End for of UnitEntity

			
			mainHead.setTotBudgetAmtInLakh(totBudgetAmtInLakhOfU);
			mainHead.setTotExpenditureAmtInLakh(totExpAmtInLakhOfU);
		}
		return new ResponseEntity<List<FMRReportDataDTO>>(frmReportDataDTOList, HttpStatus.OK);
	}

	private Set<Long> getAllSubActivityRids(Set<MainSubActivityEntity> subActivityEntitiesHS) {

		Set<Long> returnRids = new HashSet<Long>();
		for (MainSubActivityEntity mainSubActivityEntity : subActivityEntitiesHS) {
			returnRids.add(mainSubActivityEntity.getRid());
		}
		return returnRids;
	}

}
