package com.infosys.infytel.zuul;

/*import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

@Configuration
public class ZuulConfigClass {

	@Bean
	public FallbackProvider zuulFallbackProvider() {
		return new FallbackProvider() {
			
			@Override
			public String getRoute() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ClientHttpResponse fallbackResponse() {
				// TODO Auto-generated method stub
				return new ClientHttpResponse() {
					
					@Override
					public HttpHeaders getHeaders() {
						// TODO Auto-generated method stub
						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.TEXT_PLAIN);
						return headers;
					}
					
					@Override
					public InputStream getBody() throws IOException {
						// TODO Auto-generated method stub
						return new ByteArrayInputStream("Sorry. Something went wrong".getBytes());
					}
					
					@Override
					public String getStatusText() throws IOException {
						// TODO Auto-generated method stub
						return "OK";
					}
					
					@Override
					public HttpStatus getStatusCode() throws IOException {
						// TODO Auto-generated method stub
						return HttpStatus.OK;
					}
					
					@Override
					public int getRawStatusCode() throws IOException {
						// TODO Auto-generated method stub
						return 200;
					}
					
					@Override
					public void close() {
						// TODO Auto-generated method stub
						
					}
				};
			}
		};
	}
}*/


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

import com.netflix.hystrix.exception.HystrixTimeoutException;

@Configuration
public class ZuulConfigClass implements FallbackProvider{

	
			
			
			public String getRoute() {
				// TODO Auto-generated method stub
				return null;
			}
			
		  
		    public ClientHttpResponse fallbackResponse(String route, final Throwable cause) {
		    	System.out.println("from zuul"+cause.getMessage());
		    	System.out.println("Class"+cause.getClass());
		    	System.out.println(cause.getLocalizedMessage()+"msg");
		    	
		    	
		        if (cause instanceof HystrixTimeoutException) {
		            return response(HttpStatus.GATEWAY_TIMEOUT);
		        } else {
		            return response(HttpStatus.INTERNAL_SERVER_ERROR);
		        }
		    }
			public ClientHttpResponse response(final HttpStatus status) {
				// TODO Auto-generated method stub
				return new ClientHttpResponse() {
					
					
					public HttpHeaders getHeaders() {
						// TODO Auto-generated method stub
						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.TEXT_PLAIN);
						return headers;
					}
					
					
					public InputStream getBody() throws IOException {
						// TODO Auto-generated method stub
						return new ByteArrayInputStream("Sorry. Something went wrong".getBytes());
					}
					
					
					public String getStatusText() throws IOException {
						// TODO Auto-generated method stub
						return "OK";
					}
					
					
					public HttpStatus getStatusCode() throws IOException {
						// TODO Auto-generated method stub
						return HttpStatus.OK;
					}
					
					
					public int getRawStatusCode() throws IOException {
						// TODO Auto-generated method stub
						return 200;
					}
					
					
					public void close() {
						// TODO Auto-generated method stub
						
					}
				};
			}
	
	}


