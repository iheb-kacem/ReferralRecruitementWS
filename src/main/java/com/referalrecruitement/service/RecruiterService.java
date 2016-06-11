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
import com.referalrecruitement.domain.Recruiter;

import com.referralrecruitement.http.client.ReferralrecritementClient;

@Path("/RecService")
public class RecruiterService {

	@EJB
	ReferralrecritementClient httpClient;

	public RecruiterService() {

	}

	@POST
	@Path("/createRec")
	@Consumes(MediaType.APPLICATION_JSON)
	public String createRecruiter(String rc) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		Recruiter reco = new Recruiter();
		try {

			reco = mapper.readValue(rc, Recruiter.class);

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
			rc = mapper.writeValueAsString(reco);
			System.out.println(rc);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (httpClient.post(
				"http://recruitementapi.azurewebsites.net/api/Referral", rc));

	}

	@GET
	@Path("/getRecruiter/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getRecruiter(@PathParam("id") String id) {
		String url = "http://recruitementapi.azurewebsites.net/api/Recruiter/" + id;
		String s = httpClient.Get(url);
		
		return s;
	}

	@GET
	@Path("/getRecruiter")
	@Produces(MediaType.APPLICATION_JSON)
	public String getRecruiter() {
//		String url = "http://recruitementapi.azurewebsites.net/api/Recruiter/";
//		String s = httpClient.Get(url);

		return httpClient.Get("http://recruitementapi.azurewebsites.net/api/Recruiter/");
	}

	@DELETE
	@Path("/deleteRecruiter/{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	public void deleteRecruiter(@PathParam("id") String id) {
		httpClient.DELETE("http://recruitementapi.azurewebsites.net/api/Recruiter/"
				+ id);
	}

	@PUT
	@Path("/updateRecruiter/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateRecruiter(@PathParam("id") String id, String newRecruiterJson) {
		httpClient.Put("http://recruitementapi.azurewebsites.net/api/Recruiter/"
				+ id, newRecruiterJson);
		return "Recruiter seccessfully updated";
	}

}
