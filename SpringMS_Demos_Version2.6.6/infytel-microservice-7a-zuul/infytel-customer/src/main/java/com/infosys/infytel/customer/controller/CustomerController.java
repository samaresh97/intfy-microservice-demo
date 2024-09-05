package com.infosys.infytel.customer.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.infosys.infytel.customer.dto.CustomerDTO;
import com.infosys.infytel.customer.dto.LoginDTO;
import com.infosys.infytel.customer.dto.PlanDTO;
import com.infosys.infytel.customer.service.CustHystrixService;
import com.infosys.infytel.customer.service.CustomerService;

@RestController
@CrossOrigin
public class CustomerController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CustomerService custService;
	
	@Autowired
	CustHystrixService hystService;

	
	// Create a new customer
	@RequestMapping(value = "/customers", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void createCustomer(@RequestBody CustomerDTO custDTO) {
		logger.info("Creation request for customer {}", custDTO);
		custService.createCustomer(custDTO);
	}

	// Login
	@RequestMapping(value = "/login", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	public boolean login(@RequestBody LoginDTO loginDTO) {
		logger.info("Login request for customer {} with password {}", loginDTO.getPhoneNo(),loginDTO.getPassword());
		return custService.login(loginDTO);
	}

	// Fetches full profile of a specific customer
	@RequestMapping(value = "/customers/{phoneNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomerDTO getCustomerProfile(@PathVariable Long phoneNo) throws InterruptedException, ExecutionException {
		long overAllStart=System.currentTimeMillis();
		logger.info("Profile request for customer {}", phoneNo);
	
		CustomerDTO custDTO=custService.getCustomerProfile(phoneNo);
		long planStart=System.currentTimeMillis();
		Future<PlanDTO> planDTOFuture=hystService.getSpecificPlans(custDTO.getCurrentPlan().getPlanId());
		long planStop=System.currentTimeMillis();
		
		
		
		long friendStart=System.currentTimeMillis();
		Future<List<Long>> friendsFuture=hystService.getFriends(phoneNo);
		long friendStop=System.currentTimeMillis();
		
		long overAllStop=System.currentTimeMillis();
		
		custDTO.setCurrentPlan(planDTOFuture.get());
		custDTO.setFriendAndFamily(friendsFuture.get());
		
		System.out.println("Total time for Plan "+(planStop-planStart));
		System.out.println("Total time for Friend "+(friendStop-friendStart));
		System.out.println("Total Overall time for request "+(overAllStop-overAllStart));
		return custDTO;

	}

	


}
