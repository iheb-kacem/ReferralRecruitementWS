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
import com.referalrecruitement.domain.Personal;
import com.referralrecruitement.http.client.ReferralrecritementClient;


	@Path("/SuperuserService")
	public class SuperuserService {

		@EJB
		ReferralrecritementClient httpClient;

		public SuperuserService() {

		}

		@POST
		@Path("/createSuperuser")
		@Consumes(MediaType.APPLICATION_JSON)
		public String createSuperuser(String jo) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			mapper.enable(SerializationFeature.EAGER_SERIALIZER_FETCH);
			Personal jobOffer = new Personal();
			try {

				jobOffer = mapper.readValue(jo, Personal.class);

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
					"http://recruitementapi.azurewebsites.net/api/Superuser/", jo));

		}
		
		@GET
		@Path("/getSuperuser/{id}")
		@Produces(MediaType.APPLICATION_JSON)
		public String getSuperuser(@PathParam("id") String id) {
			String url = "http://recruitementapi.azurewebsites.net/api/Superuser/" + id;
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
		@Path("/getSuperusers")
		@Produces(MediaType.APPLICATION_JSON)
		public String getSuperuser() {
			String url = "http://recruitementapi.azurewebsites.net/api/Superuser/";
			String s = httpClient.Get(url);

			return s;
		}

		@DELETE
		@Path("/deleteSuperuser/{id}")
		@Consumes(MediaType.TEXT_PLAIN)
		public void deleteSuperuser(@PathParam("id") String id) {
			httpClient.DELETE("http://recruitementapi.azurewebsites.net/api/Superuser/"
					+ id);
		}

		@PUT
		@Path("/updateSuperuser/{id}")
		@Consumes(MediaType.APPLICATION_JSON)
		public String updateSuperuser(@PathParam("id") String id, String newSuperuserJson) {
			httpClient.Put("http://recruitementapi.azurewebsites.net/api/Superuser/"
					+ id, newSuperuserJson);
			return "Idea seccessfully updated";
		}

	}


