package com.infosys.infytel.customer;



import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


@Configuration
public class CustomerConfig {

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate(getClientHttpRequestFactory());
	}
	
	private HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory() 
	{
	    HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
	                      = new HttpComponentsClientHttpRequestFactory();
	    //Connect timeout
	    clientHttpRequestFactory.setConnectTimeout(10_000);
	     
	    //Read timeout
	    clientHttpRequestFactory.setReadTimeout(10_000);
	    return clientHttpRequestFactory;
	}
}
