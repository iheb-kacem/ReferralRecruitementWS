package com.referalrecruitement.service;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.referalrecruitement.domain.Referral;
import com.referralrecruitement.http.client.ReferralrecritementClient;

@Path("/RefService")
public class ReferralService {

	@EJB
	ReferralrecritementClient httpClient;

	public ReferralService() {

	}

	@POST
	@Path("/createRef")
	@Consumes(MediaType.APPLICATION_JSON)
	public String createReferral(String rf) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		Referral refo = new Referral();
		try {

			refo = mapper.readValue(rf, Referral.class);

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
			rf = mapper.writeValueAsString(refo);
			System.out.println(rf);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (httpClient.post(
				"http://recruitementapi.azurewebsites.net/api/Referral", rf));

	}

	@GET
	@Path("/getReferral/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getRef(@PathParam("id") String id) {
		String url = "http://recruitementapi.azurewebsites.net/api/Referral/" + id;
		String s = httpClient.Get(url);
		
		return s;
	}

	@GET
	@Path("/getReferrals")
	@Produces(MediaType.APPLICATION_JSON)
	public String getReferral() {
//		String url = "http://recruitementapi.azurewebsites.net/api/Referral/";
//		String s = httpClient.Get(url);

		return httpClient.Get("http://recruitementapi.azurewebsites.net/api/Referral/");
	}

	@DELETE
	@Path("/deleteReferral/{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	public void deleteReferral(@PathParam("id") String id) {
		httpClient.DELETE("http://recruitementapi.azurewebsites.net/api/Referral/"
				+ id);
	}

	@PUT
	@Path("/updateReferral/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateReferral(@PathParam("id") String id, String newReferralJson) {
		httpClient.Put("http://recruitementapi.azurewebsites.net/api/Referral/"
				+ id, newReferralJson);
		return "Referral seccessfully updated";
	}
	
	public Referral getReferral(String id) {
		String url = "http://recruitementapi.azurewebsites.net/api/Referral/" + id;
		String s = httpClient.Get(url);
		System.out.println(s);
		Referral referral = new Referral();
		ObjectMapper mapper = new ObjectMapper();
		
		try{
			referral = mapper.readValue(s, Referral.class);
		}catch (com.fasterxml.jackson.core.JsonParseException e){
			e.printStackTrace();
		}catch (JsonMappingException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
		
		return referral;
	}

}