/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.centralniserver.resources;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Laza
 */
@Path("podsistem2")
public class ServerPodsistem2 {
    @Resource(lookup="jms/__defaultConnectionFactory")
    private ConnectionFactory connFactory;
    @Resource(lookup="topic")
    private Topic topic;
    //private JMSContext context=connFactory.createContext();
    //private JMSProducer producer=context.createProducer();
    
    @GET
    public Response ping(){
        return Response
                .ok("ping2")
                .build();
    }
    
    @POST
    @Path("kreiranjeKategorije/{naziv}")
    public Response kreiranjeGrada(@Context UriInfo uriInfo){
        try {
            String fullURL=uriInfo.getAbsolutePath().toString();
            
            JMSContext context=connFactory.createContext();
            JMSProducer producer=context.createProducer();
            TextMessage txt=context.createTextMessage();
            txt.setIntProperty("podsistem", 2);
            txt.setIntProperty("funkcija", 5);
            txt.setStringProperty("fulURL", fullURL); //odavde cemo da izvlacimo sve parametre

            producer.send(topic, txt);
        } catch (JMSException ex) {
            Logger.getLogger(ServerPodsistem1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().build();
    }
    
    
    @POST
    @Path("kreiranjeAudio/{naziv}/{trajanje}/{datumVreme}/{idVlasnika}")
    public Response kreiranjeAudio(@Context UriInfo uriInfo){
        try {
            String fullURL=uriInfo.getAbsolutePath().toString();
            
            JMSContext context=connFactory.createContext();
            JMSProducer producer=context.createProducer();
            TextMessage txt=context.createTextMessage();
            txt.setIntProperty("podsistem", 2);
            txt.setIntProperty("funkcija", 6);
            txt.setStringProperty("fulURL", fullURL); //odavde cemo da izvlacimo sve parametre

            producer.send(topic, txt);
        } catch (JMSException ex) {
            Logger.getLogger(ServerPodsistem1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().build();
    }
    
    @POST
    @Path("promenaNazivaAudio/{idAudio}/{naziv}")
    public Response promenaNazivaAudio(@Context UriInfo uriInfo){
        try {
            String fullURL=uriInfo.getAbsolutePath().toString();
            
            JMSContext context=connFactory.createContext();
            JMSProducer producer=context.createProducer();
            TextMessage txt=context.createTextMessage();
            txt.setIntProperty("podsistem", 2);
            txt.setIntProperty("funkcija", 7);
            txt.setStringProperty("fulURL", fullURL); //odavde cemo da izvlacimo sve parametre

            producer.send(topic, txt);
        } catch (JMSException ex) {
            Logger.getLogger(ServerPodsistem1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().build();
    }
    
    @POST
    @Path("dodavanjeKategorijeSnimku/{nazivKat}/{idAudio}")
    public Response dodavanjeKategorijeAudio(@Context UriInfo uriInfo){
        try {
            String fullURL=uriInfo.getAbsolutePath().toString();
            
            JMSContext context=connFactory.createContext();
            JMSProducer producer=context.createProducer();
            TextMessage txt=context.createTextMessage();
            txt.setIntProperty("podsistem", 2);
            txt.setIntProperty("funkcija", 8);
            txt.setStringProperty("fulURL", fullURL); //odavde cemo da izvlacimo sve parametre

            producer.send(topic, txt);
        } catch (JMSException ex) {
            Logger.getLogger(ServerPodsistem1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().build();
    }
    
    @DELETE
    @Path("brisanjeAudio/{idAudio}/{idKorisnika}")
    public Response brisanjeAudio(@Context UriInfo uriInfo){
        try {
            String fullURL=uriInfo.getAbsolutePath().toString();
            
            JMSContext context=connFactory.createContext();
            JMSProducer producer=context.createProducer();
            TextMessage txt=context.createTextMessage();
            txt.setIntProperty("podsistem", 2);
            txt.setIntProperty("funkcija", 17);
            txt.setStringProperty("fulURL", fullURL); //odavde cemo da izvlacimo sve parametre

            producer.send(topic, txt);
        } catch (JMSException ex) {
            Logger.getLogger(ServerPodsistem1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().build();
    }
    
    @GET
    @Path("dohvatanjeKategorija")
    public Response dohvatanjeKorisnika(@Context UriInfo uriInfo){
        try {
            String fullURL=uriInfo.getAbsolutePath().toString();
            
            JMSContext context=connFactory.createContext();
            JMSProducer producer=context.createProducer();
            TextMessage txt=context.createTextMessage();
            txt.setIntProperty("podsistem", 2);
            txt.setIntProperty("funkcija", 20);
            txt.setStringProperty("fulURL", fullURL); //odavde cemo da izvlacimo sve parametre

            producer.send(topic, txt);
        } catch (JMSException ex) {
            Logger.getLogger(ServerPodsistem1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().build();
    }
    
    @GET
    @Path("dohvatanjeAudioSnimaka")
    public Response dohvatanjeAudioSnimaka(@Context UriInfo uriInfo){
        try {
            String fullURL=uriInfo.getAbsolutePath().toString();
            
            JMSContext context=connFactory.createContext();
            JMSProducer producer=context.createProducer();
            TextMessage txt=context.createTextMessage();
            txt.setIntProperty("podsistem", 2);
            txt.setIntProperty("funkcija", 21);
            txt.setStringProperty("fulURL", fullURL); //odavde cemo da izvlacimo sve parametre

            producer.send(topic, txt);
        } catch (JMSException ex) {
            Logger.getLogger(ServerPodsistem1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().build();
    }
    
    @GET
    @Path("dohvatanjeKatZaAudio/{idAudio}")
    public Response dohvatanjeKatZaAudio(@Context UriInfo uriInfo){
        try {
            String fullURL=uriInfo.getAbsolutePath().toString();
            
            JMSContext context=connFactory.createContext();
            JMSProducer producer=context.createProducer();
            TextMessage txt=context.createTextMessage();
            txt.setIntProperty("podsistem", 2);
            txt.setIntProperty("funkcija", 22);
            txt.setStringProperty("fulURL", fullURL); //odavde cemo da izvlacimo sve parametre

            producer.send(topic, txt);
        } catch (JMSException ex) {
            Logger.getLogger(ServerPodsistem1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().build();
    }
}
