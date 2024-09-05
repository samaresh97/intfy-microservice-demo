package com.infosys.infytel.plan.controller;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.infosys.infytel.plan.dto.PlanDTO;
import com.infosys.infytel.plan.service.PlanService;

@RestController

public class PlanController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PlanService planService;

	// Fetches all plan details
	@RequestMapping(value = "/plans", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PlanDTO> getAllPlans() {
		logger.info("Fetching all plans");
		return planService.getAllPlans();
	}

	// Fetch plan details of a specific plan
	@RequestMapping(value = "/plans/{planId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public PlanDTO getSpecificPlans(HttpServletRequest request ,  @PathVariable Integer planId) {
		System.out.println("PLAN REQUEST IS ======="+request);
		Enumeration<String> en=request.getHeaderNames();
		List<String> l=Collections.list(en);
		System.out.println("HEADERS ARE ======="+l);
		System.out.println("Authorization IS  ======="+request.getHeader("Authorization"));
		logger.info("Fetching details of plan {}",planId);
		System.out.println("==== PLAN IS ====="+planService.getSpecificPlan(planId));
		return planService.getSpecificPlan(planId);
	}

}
