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
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Laza
 */
@Path("podsistem1")
public class ServerPodsistem1 {
    @Resource(lookup="jms/__defaultConnectionFactory")
    private ConnectionFactory connFactory;
    @Resource(lookup="topic")
    private Topic topic;
    //private JMSContext context=connFactory.createContext();
    //private JMSProducer producer=context.createProducer();            
    
    @GET
    public Response ping(){
        return Response
                .ok("ping1")
                .build();
    }
    
    @POST
    @Path("kreiranjeGrada/{nazivGrada}")
    public Response kreiranjeGrada(@Context UriInfo uriInfo){
        try {
            String fullURL=uriInfo.getAbsolutePath().toString();
            
            JMSContext context=connFactory.createContext();
            JMSProducer producer=context.createProducer();
            TextMessage txt=context.createTextMessage();
            txt.setIntProperty("podsistem", 1);
            txt.setIntProperty("funkcija", 1);
            txt.setStringProperty("fulURL", fullURL); //odavde cemo da izvlacimo sve parametre

            producer.send(topic, txt);
        } catch (JMSException ex) {
            Logger.getLogger(ServerPodsistem1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().build();
    }
    
    @POST
    @Path("kreiranjeKorisnika/{ime}/{email}/{god}/{pol}/{nazivMesta}")
    public Response kreiranjeKorisnika(@Context UriInfo uriInfo){
        try {
            String fullURL=uriInfo.getAbsolutePath().toString();
            
            JMSContext context=connFactory.createContext();
            JMSProducer producer=context.createProducer();
            TextMessage txt=context.createTextMessage();
            txt.setIntProperty("podsistem", 1);
            txt.setIntProperty("funkcija", 2);
            txt.setStringProperty("fulURL", fullURL); //odavde cemo da izvlacimo sve parametre

            producer.send(topic, txt);
        } catch (JMSException ex) {
            Logger.getLogger(ServerPodsistem1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().build();
    }
    
    @POST
    @Path("promenaEmaila/{idKorisnika}/{email}")
    public Response promenaEmaila(@Context UriInfo uriInfo){
        try {
            String fullURL=uriInfo.getAbsolutePath().toString();
            
            JMSContext context=connFactory.createContext();
            JMSProducer producer=context.createProducer();
            TextMessage txt=context.createTextMessage();
            txt.setIntProperty("podsistem", 1);
            txt.setIntProperty("funkcija", 3);
            txt.setStringProperty("fulURL", fullURL); //odavde cemo da izvlacimo sve parametre

            producer.send(topic, txt);
        } catch (JMSException ex) {
            Logger.getLogger(ServerPodsistem1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().build();
    }
    
    @POST
    @Path("promenaMesta/{idKorisnika}/{mesto}")
    public Response promenaMesta(@Context UriInfo uriInfo){
        try {
            String fullURL=uriInfo.getAbsolutePath().toString();
            
            JMSContext context=connFactory.createContext();
            JMSProducer producer=context.createProducer();
            TextMessage txt=context.createTextMessage();
            txt.setIntProperty("podsistem", 1);
            txt.setIntProperty("funkcija", 4);
            txt.setStringProperty("fulURL", fullURL); //odavde cemo da izvlacimo sve parametre

            producer.send(topic, txt);
        } catch (JMSException ex) {
            Logger.getLogger(ServerPodsistem1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().build();
    }
    
    @GET
    @Path("dohvatanjeMesta")
    public Response dohvatanjeMesta(@Context UriInfo uriInfo){
        try {
            String fullURL=uriInfo.getAbsolutePath().toString();
            
            JMSContext context=connFactory.createContext();
            JMSProducer producer=context.createProducer();
            TextMessage txt=context.createTextMessage();
            txt.setIntProperty("podsistem", 1);
            txt.setIntProperty("funkcija", 18);
            txt.setStringProperty("fulURL", fullURL); //odavde cemo da izvlacimo sve parametre

            producer.send(topic, txt);
        } catch (JMSException ex) {
            Logger.getLogger(ServerPodsistem1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().build();
    }
    
    @GET
    @Path("dohvatanjeKorisnika")
    public Response dohvatanjeKorisnika(@Context UriInfo uriInfo){
        try {
            String fullURL=uriInfo.getAbsolutePath().toString();
            
            JMSContext context=connFactory.createContext();
            JMSProducer producer=context.createProducer();
            TextMessage txt=context.createTextMessage();
            txt.setIntProperty("podsistem", 1);
            txt.setIntProperty("funkcija", 19);
            txt.setStringProperty("fulURL", fullURL); //odavde cemo da izvlacimo sve parametre

            producer.send(topic, txt);
        } catch (JMSException ex) {
            Logger.getLogger(ServerPodsistem1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().build();
    }
    
}
