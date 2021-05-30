package com.ikaustubh.missystem.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ikaustubh.missystem.dto.MainProgramDTO;
import com.ikaustubh.missystem.entities.ActivityEntity;
import com.ikaustubh.missystem.entities.MainProgramHeadEntity;
import com.ikaustubh.missystem.repository.MainProgramHeadRepository;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping(value = "/master")
public class MainHeadProgramController {

	@Autowired
	MainProgramHeadRepository mainProgramHeadRepository;				

	@GetMapping(value = "/getAllMainPrograms")
	public List<MainProgramDTO> getAllMainProgram(@RequestParam("id") long id, @RequestParam("year") String year ) throws JsonProcessingException {
		System.out.println("id["+id+"] year["+year+"]");
		Optional<MainProgramHeadEntity> mainProgramHeadOption = mainProgramHeadRepository.findById(id);
		String returnJSON = "";
		List<MainProgramDTO> mainProgramDTOs = new ArrayList<MainProgramDTO>();
		if (mainProgramHeadOption.isPresent()) {
			MainProgramHeadEntity mainProgramHeadEntity = mainProgramHeadOption.get();
			for (ActivityEntity mainProgramEntity : mainProgramHeadEntity.getActivityEntities()) {
				MainProgramDTO mainProgramDTO = new MainProgramDTO();
				mainProgramDTO.setRid(mainProgramEntity.getRid());
				mainProgramDTO.setName(mainProgramEntity.getName());
				mainProgramDTO.setNewCode(mainProgramEntity.getNewCode());
				mainProgramDTO.setOldCode(mainProgramEntity.getOldCode());
				mainProgramDTOs.add(mainProgramDTO);
			}
			Collections.sort(mainProgramDTOs);
		}
		return mainProgramDTOs;
	}

}
