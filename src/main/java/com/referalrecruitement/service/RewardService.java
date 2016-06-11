package com.referalrecruitement.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import com.referalrecruitement.domain.Offer;
import com.referalrecruitement.domain.Recruiter;
import com.referalrecruitement.domain.Reward;
import com.referralrecruitement.http.client.ReferralrecritementClient;

@Path("/rewardService")
public class RewardService {

	@EJB
	ReferralrecritementClient httpClient;

	public RewardService() {
		super();
	}
	@POST
	@Path("/createReward")
	@Consumes(MediaType.APPLICATION_JSON)
	public String createReward(String re) {
		ObjectMapper mapper = new ObjectMapper();
		//mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		Reward reward = new Reward();
		try {

			reward = mapper.readValue(re, Reward.class);
			
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
			reward.setHire(reward.getHire());
			reward.setHRIntrvw(reward.getHRIntrvw());
			reward.setMnIntrvw(reward.getMnIntrvw());
			reward.setTechIntrvw(reward.getTechIntrvw());
			reward.setSharing(reward.getSharing());
			re = mapper.writeValueAsString(reward);
			System.out.println(re);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (httpClient.post("http://recruitementapi.azurewebsites.net/api/Reward", re));

	}
	
	@GET
	@Path("/getReward/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getOffer(@PathParam("id") String id) {
		String url = "http://recruitementapi.azurewebsites.net/api/Reward/" + id;
		String s = httpClient.Get(url);
		return s;
	}
	
	@GET
	@Path("/getRewards")
	@Produces(MediaType.APPLICATION_JSON)
	public String getReward() {
		String url = "http://recruitementapi.azurewebsites.net/api/Reward/";
		String s = httpClient.Get(url);
		return s;
	}
	
	@DELETE
	@Path("/deleteReward/{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	public void deleteRewards(@PathParam("id") String id) {
		httpClient.DELETE("http://recruitementapi.azurewebsites.net/api/Reward/"+id);
	}
	
	@PUT
	@Path("/updateReward/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateReward(@PathParam("id") String id, String newRewJson) {
		httpClient.Put("http://recruitementapi.azurewebsites.net/api/Reward/"
				+ id, newRewJson);
		return "Reward successfully updated";
	}

	
	
	
//	@PUT
//	@Path("/fixReward/{ido}")
//	@Consumes(MediaType.APPLICATION_JSON)
//	public String updateReward(@PathParam("ido") String id,String re){
//		ObjectMapper mapper = new ObjectMapper();
//		Reward reward = new Reward();
//		Offer o=new Offer();
//		Reward rewa=new Reward();
//		String offer="";
//		String offerJson="";
//		String rewardJson="";
//		try {
//			//desialization
//			reward = mapper.readValue(re, Reward.class);
//			offer=httpClient.Get("http://recruitementapi.azurewebsites.net/api/Offer/"+id);
//			o=mapper.readValue(offer, Offer.class);
//			rewa=mapper.readValue(offer, Reward.class);
//			
//		} catch (com.fasterxml.jackson.core.JsonParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			Reward rewn=rewa;
//			if(rewn==null)
//			{
//				return "No Reward found";
//			}
//			rewn.setHire(reward.getHire());
//			rewn.setHRIntrvw(reward.getHRIntrvw());
//			rewn.setMnIntrvw(reward.getMnIntrvw());
//			rewn.setSharing(reward.getSharing());
//			rewn.setTechIntrvw(reward.getTechIntrvw());
//			offerJson = mapper.writeValueAsString(o);
//			rewardJson=mapper.writeValueAsString(rewn);
//			
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		httpClient.Put("http://recruitementapi.azurewebsites.net/api/Reward", rewardJson);
//		//httpClient.Put("http://recruitementapi.azurewebsites.net/api/Offer/"+id, offerJson);
//		return "True";
//	}
	
	
	/*@GET
	@Path("/getReward")
	@Produces(MediaType.TEXT_PLAIN)
	public String getReward()
	{
		String offer="";
		offer=httpClient.Get("http://recruitementapi.azurewebsites.net/api/Offer/7");
		Reward o=new Reward();
		o.setHire(22);
		o.setHRIntrvw(23);
		o.setMnIntrvw(24);
		o.setSharing(25);
		o.setTechIntrvw(26);
		ObjectMapper mapper = new ObjectMapper();
		String re="";
		try {
			re = mapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return re;
	}*/
	
}
