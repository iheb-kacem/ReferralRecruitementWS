package com.referalrecruitement.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.referalrecruitement.domain.Application;
import com.referalrecruitement.domain.Offer;
import com.referalrecruitement.domain.Referral;
import com.referalrecruitement.service.ApplicationService;
import com.referalrecruitement.service.JobOfferService;
import com.referalrecruitement.service.ReferralService;
import com.referralrecruitement.http.client.ReferralrecritementClient;

@Path("/candidateService")
public class ReferCandidate {
	
	@EJB
	ReferralrecritementClient httpClient;
	ReferralService referralService;
	JobOfferService offerService;
	ApplicationService applicationService;
	
	@POST
	@Path("/candidate")
	@Consumes(MediaType.APPLICATION_JSON)
	public String referCandidate(@QueryParam("referral") String referral
			,@QueryParam("offer") String offer, String jApp){
		Application app = new Application();
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
		try{
			app = mapper.readValue(jApp, Application.class);
		}catch (com.fasterxml.jackson.core.JsonParseException e){
			e.printStackTrace();
		}catch (JsonMappingException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
		
		Referral ref = referralService.getReferral(referral);
		app.setReferral(ref);
		Offer of = offerService.getOffer(offer);
		app.setOffer(of);
		
		try {
			jApp = mapper.writeValueAsString(app);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(jApp);
		return applicationService.createApplication(jApp);
//		return (httpClient.post(
//				"http://recruitementapi.azurewebsites.net/api/Application", jApp));
	}
	
	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	public String test(){
		Application app = new Application();
		app.setFirstName("amrou");
		app.setLastName("dhiab");
		app.setEmail("ad@c.c");
		DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
		Date d = new Date();
		String formattedDate= dateFormat.format(d);
		
		app.setDate(d);
		app.setPhoneNumber("2222222222");
		app.setStatus("en attente");
		app.setCV(new byte[4]);
		
		String jApp="";
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		try {
			jApp = mapper.writeValueAsString(app);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jApp;
	}
}
