package com.infosys.infytel.customer.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.infosys.infytel.customer.dto.PlanDTO;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class CustHystrixService {
	
	@Autowired RestTemplate template;
	
	@HystrixCommand(fallbackMethod="getSpecificPlanFallback"  )
	public PlanDTO getSpecificPlan(int planId) {
		System.out.println("calling planms");
			return template.getForObject("http://PLANMS"+"/plans/"+planId, PlanDTO.class);
	}
	public PlanDTO getSpecificPlanFallback(int planId) {
		System.out.println("in plan fallback");
			return new PlanDTO();
	}
	
	@HystrixCommand
		public List<Long> getFriends(long phoneNo){
		return template.getForObject("http://FRIENDFAMILYMS"+"/customers/"+phoneNo+"/friends", List.class);
	}
	public List<Long> getFriendsFallback(long phoneNo){
		List<Long> friends=new ArrayList<Long>();
		friends.add(-1L);
		return friends;
	}
}
