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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.referalrecruitement.domain.Offer;
import com.referalrecruitement.domain.Superuser;
import com.referralrecruitement.http.client.ReferralrecritementClient;

@Path("/jobOfferService")
public class JobOfferService {

	@EJB
	ReferralrecritementClient httpClient;

	public JobOfferService() {

	}

	@POST
	@Path("/createJobOffer")
	@Consumes(MediaType.APPLICATION_JSON)
	public String createJobOffer(String jo) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		Offer jobOffer = new Offer();
		try {

			jobOffer = mapper.readValue(jo, Offer.class);

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
			jo = mapper.writeValueAsString(jobOffer);
			System.out.println(jo);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (httpClient.post(
				"http://recruitementapi.azurewebsites.net/api/Offer", jo));

	}
	@POST
	@Path("/Admin")
	@Consumes(MediaType.TEXT_PLAIN)
	public String Authentif(String chaine) {
		String list=httpClient.Get("http://recruitementapi.azurewebsites.net/api/Superuser");
		System.err.println(list);
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		Superuser user = new Superuser();
		JSONArray array =new  JSONArray();
		JSONObject obj2 = null;
		  Object obj;
		try {
			 JSONParser parser=new JSONParser();
			obj = parser.parse(list);
			array = (JSONArray)obj;
			//obj2 = (JSONObject)array.get(0);
			//user=(Superuser)array.get(0);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String test="";
	       String login=chaine.split(":")[0];
	       String password=chaine.split(":")[1];
	       for (Object object : array) {
	    	   obj2 = (JSONObject)object;
		test+=object.toString();
	       if(login.equals(obj2.get("Email").toString()) && password.equals(obj2.get("Password").toString()))
    	   return "Login True ";
	     }
	       {
	    	   return  "Login True ";
	       }
//		return (httpClient.post(
//				"http://recruitementapi.azurewebsites.net/api/Offer", jo));
		

	}
	@GET
	@Path("/getOffer/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getOfferr(@PathParam("id") String id) {
		String url = "http://recruitementapi.azurewebsites.net/api/Offer/" + id;
		String s = httpClient.Get(url);
		// Offer o=new Offer();
		// o.setDate(new Date());
		// o.setNote("ddd");
		// o.setOfferName("ssss");
		// Reward r=new Reward();
		//
		// r.setHire(200);
		// r.setHRIntrvw(100);
		// r.setMnIntrvw(300);
		// r.setSharing(0);
		// r.setTechIntrvw(300);
		// o.setReward(r);
		//
		// Notification n=new Notification();
		// n.setDate(new Date());
		// n.setNote("fhvgjbk");
		//
		// ObjectMapper mapper = new ObjectMapper();
		// String rec="sss";
		// try {
		// rec = mapper.writeValueAsString(r);
		// } catch (JsonProcessingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		return s;
	}

	@GET
	@Path("/getOffers")
	@Produces(MediaType.APPLICATION_JSON)
	public String getOffer() {
		String url = "http://recruitementapi.azurewebsites.net/api/Offer/";
		String s = httpClient.Get(url);

		return s;
	}

	@DELETE
	@Path("/deleteJobs/{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	public void deleteJobs(@PathParam("id") String id) {
		httpClient.DELETE("http://recruitementapi.azurewebsites.net/api/Offer/"
				+ id);
	}

	@PUT
	@Path("/updateOffer/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateOffer(@PathParam("id") String id, String newofferJson) {
		httpClient.Put("http://recruitementapi.azurewebsites.net/api/Offer/"
				+ id, newofferJson);
		return "Idea seccessfully updated";
	}
	
	
	public Offer getOffer(@PathParam("id") String id) {
		String url = "http://recruitementapi.azurewebsites.net/api/Referral/" + id;
		String s = httpClient.Get(url);
		System.out.println(s);
		Offer offer = new Offer();
		ObjectMapper mapper = new ObjectMapper();
		
		try{
			offer = mapper.readValue(s, Offer.class);
		}catch (com.fasterxml.jackson.core.JsonParseException e){
			e.printStackTrace();
		}catch (JsonMappingException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
		
		return offer;
	}

}
