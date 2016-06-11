package com.referalrecruitement.service;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.ejb.EJB;
import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.referalrecruitement.domain.Notification;
import com.referalrecruitement.domain.Recruiter;
import com.referalrecruitement.domain.Reward;
import com.referalrecruitement.domain.Tenancy;
import com.referralrecruitement.http.client.ReferralrecritementClient;


@Path("/tenancyService")
public class TenancyService {

	
	@EJB
	ReferralrecritementClient httpClient;

	public TenancyService() {
		
	}
	
	 @POST
		@Path("/createTenancy")
		@Consumes(MediaType.APPLICATION_JSON)
	 public String createTenancy(String tn){
		 ObjectMapper mapper = new ObjectMapper();
		 mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		 Tenancy tenant = new Tenancy();
			try {
				
				tenant = mapper.readValue(tn, Tenancy.class);
				
			} catch (com.fasterxml.jackson.core.JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				tn = mapper.writeValueAsString(tenant);
				System.out.println(tn);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return(httpClient.post("http://recruitementapi.azurewebsites.net/api/Tenancy",tn));
		   
			
	 }
	 
	 
	 @GET
		@Path("/getTenancy/{id}")
		@Produces(MediaType.APPLICATION_JSON)
		public String getOffer(@PathParam("id") String id) {
			String url = "http://recruitementapi.azurewebsites.net/api/Tenancy/" + id;
			String s = httpClient.Get(url);
			
			return s;
		}

		@GET
		@Path("/getTenancies")
		@Produces(MediaType.APPLICATION_JSON)
		public String getTenancies() {
			String url = "http://recruitementapi.azurewebsites.net/api/Tenancy/";
			String s = httpClient.Get(url);

			return s;
		}

		@DELETE
		@Path("/deleteTenancy/{id}")
		@Consumes(MediaType.TEXT_PLAIN)
		public void deleteJobs(@PathParam("id") String id) {
			httpClient.DELETE("http://recruitementapi.azurewebsites.net/api/Tenancy/"
					+ id);
		}

		@PUT
		@Path("/updateTenancy/{id}")
		@Consumes(MediaType.APPLICATION_JSON)
		public String updateIdea(@PathParam("id") String id, String newTenancyJson) {
			httpClient.Put("http://recruitementapi.azurewebsites.net/api/Tenancy/"
					+ id, newTenancyJson);
			return "Idea seccessfully updated";
		}

	}
