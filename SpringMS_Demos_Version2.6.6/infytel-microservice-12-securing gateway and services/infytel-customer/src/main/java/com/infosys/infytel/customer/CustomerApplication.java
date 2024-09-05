package com.infosys.infytel.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableCircuitBreaker
@EnableFeignClients

public class CustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerApplication.class, args);
	}

	@Configuration
	@EnableResourceServer

	protected static class ResourceServer extends ResourceServerConfigurerAdapter {

		@Override
		public void configure(HttpSecurity http) throws Exception {

			 http.csrf().disable().authorizeRequests()
             .antMatchers("/oauth/token", "/oauth/authorize**", "/oauth/check_token")
             .permitAll();
			 
			http.requestMatchers()
			.and()
           
            .authorizeRequests()
					
            .anyRequest().authenticated()
           
            .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
		}
		@Override
		public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
			super.configure(resources);
			resources.stateless(false);
		}

	}
}
