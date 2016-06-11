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
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.referalrecruitement.domain.Application;
import com.referralrecruitement.http.client.ReferralrecritementClient;

@Path("/applicationService")
public class ApplicationService {

	@EJB
	ReferralrecritementClient httpClient;

	public ApplicationService() {

	}

	@POST
	@Path("/createApplication")
	@Consumes(MediaType.APPLICATION_JSON)
	public String createApp(String app) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Application application = new Application();
		try {

			application = mapper.readValue(app, Application.class);

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
			//application.setApplicationID(2);
			application.setEmail("mail.fr");
			app = mapper.writeValueAsString(application);
			System.out.println(app);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (httpClient.post(
				"http://recruitementapi.azurewebsites.net/api/Application",app));

	}

	@GET
	@Path("/getApplication/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getApplication(@PathParam("id") String id) {
		String url = "http://recruitementapi.azurewebsites.net/api/Application/" + id;
		String s = httpClient.Get(url);
		
		return s;
	}

	@GET
	@Path("/getApplications")
	@Produces(MediaType.APPLICATION_JSON)
	public String getApplications() {
		String url = "http://recruitementapi.azurewebsites.net/api/Application/";
		String s = httpClient.Get(url);

		return s;
	}

	@DELETE
	@Path("/deleteApplication/{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	public void deleteApplication(@PathParam("id") String id) {
		httpClient.DELETE("http://recruitementapi.azurewebsites.net/api/Application/"
				+ id);
	}

	@PUT
	@Path("/updateApplication/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateApplication(@PathParam("id") String id, String newAppJson) {
		httpClient.Put("http://recruitementapi.azurewebsites.net/api/Application/"
				+ id, newAppJson);
		return "Application seccessfully updated";
	}
	
	
	public String createApplication(String jApplication){
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
		Application application = new Application();
		
		try{
			application = mapper.readValue(jApplication, Application.class);
		}catch (com.fasterxml.jackson.core.JsonParseException e){
			e.printStackTrace();
		}catch (JsonMappingException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
		try{
			jApplication = mapper.writeValueAsString(application);
			//System.out.println(jApplication);
		} catch (JsonProcessingException e){
			e.printStackTrace();
		}
		return (httpClient.post(
				"http://recruitementapi.azurewebsites.net/api/Application", jApplication));
	}
}

