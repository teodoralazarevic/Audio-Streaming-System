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
@Path("podsistem3")
public class ServerPodsistem3 {  
    @Resource(lookup="jms/__defaultConnectionFactory")
    private ConnectionFactory connFactory;
    @Resource(lookup="topic")
    private Topic topic;
    //private JMSContext context=connFactory.createContext();
    //private JMSProducer producer=context.createProducer();
    
    @GET
    public Response ping(){
        return Response
                .ok("ping3")
                .build();
    }
    
    @POST
    @Path("kreiranjePaketa/{cena}")
    public Response kreiranjePaketa(@Context UriInfo uriInfo){
        try {
            String fullURL=uriInfo.getAbsolutePath().toString();
            
            JMSContext context=connFactory.createContext();
            JMSProducer producer=context.createProducer();
            TextMessage txt=context.createTextMessage();
            txt.setIntProperty("podsistem", 3);
            txt.setIntProperty("funkcija", 9);
            txt.setStringProperty("fulURL", fullURL); //odavde cemo da izvlacimo sve parametre

            producer.send(topic, txt);
        } catch (JMSException ex) {
            Logger.getLogger(ServerPodsistem1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().build();
    }
    
    @POST
    @Path("promenaCenePaketa/{idPaketa}/{cena}")
    public Response promenaCenePaketa(@Context UriInfo uriInfo){
        try {
            String fullURL=uriInfo.getAbsolutePath().toString();
            
            JMSContext context=connFactory.createContext();
            JMSProducer producer=context.createProducer();
            TextMessage txt=context.createTextMessage();
            txt.setIntProperty("podsistem", 3);
            txt.setIntProperty("funkcija", 10);
            txt.setStringProperty("fulURL", fullURL); //odavde cemo da izvlacimo sve parametre

            producer.send(topic, txt);
        } catch (JMSException ex) {
            Logger.getLogger(ServerPodsistem1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().build();
    }
    
    @POST
    @Path("kreiranjePretplate/{idKorisnika}/{idPaketa}/{datumVreme}")
    public Response kreiranjePretplate(@Context UriInfo uriInfo){
        try {
            String fullURL=uriInfo.getAbsolutePath().toString();
            
            JMSContext context=connFactory.createContext();
            JMSProducer producer=context.createProducer();
            TextMessage txt=context.createTextMessage();
            txt.setIntProperty("podsistem", 3);
            txt.setIntProperty("funkcija", 11);
            txt.setStringProperty("fulURL", fullURL); //odavde cemo da izvlacimo sve parametre

            producer.send(topic, txt);
        } catch (JMSException ex) {
            Logger.getLogger(ServerPodsistem1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().build();
    }
    
    @GET
    @Path("dohvatanjePaketa")
    public Response dohvatanjeKorisnika(@Context UriInfo uriInfo){
        try {
            String fullURL=uriInfo.getAbsolutePath().toString();
            
            JMSContext context=connFactory.createContext();
            JMSProducer producer=context.createProducer();
            TextMessage txt=context.createTextMessage();
            txt.setIntProperty("podsistem", 3);
            txt.setIntProperty("funkcija", 23);
            txt.setStringProperty("fulURL", fullURL); //odavde cemo da izvlacimo sve parametre

            producer.send(topic, txt);
        } catch (JMSException ex) {
            Logger.getLogger(ServerPodsistem1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().build();
    }
    
    @GET
    @Path("dohvatanjePretplataZaKorisnika/{idKorisnika}")
    public Response dohvatanjeKatZaAudio(@Context UriInfo uriInfo){
        try {
            String fullURL=uriInfo.getAbsolutePath().toString();
            
            JMSContext context=connFactory.createContext();
            JMSProducer producer=context.createProducer();
            TextMessage txt=context.createTextMessage();
            txt.setIntProperty("podsistem", 3);
            txt.setIntProperty("funkcija", 24);
            txt.setStringProperty("fulURL", fullURL); //odavde cemo da izvlacimo sve parametre

            producer.send(topic, txt);
        } catch (JMSException ex) {
            Logger.getLogger(ServerPodsistem1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().build();
    }
    
    @POST
    @Path("kreiranjeSlusanja/{idKorisnika}/{idAudio}/{datumVreme}/{sekundZap}/{sekundOdsl}")
    public Response kreiranjeSlusanja(@Context UriInfo uriInfo){
        try {
            String fullURL=uriInfo.getAbsolutePath().toString();
            
            JMSContext context=connFactory.createContext();
            JMSProducer producer=context.createProducer();
            TextMessage txt=context.createTextMessage();
            txt.setIntProperty("podsistem", 3);
            txt.setIntProperty("funkcija", 12);
            txt.setStringProperty("fulURL", fullURL); //odavde cemo da izvlacimo sve parametre

            producer.send(topic, txt);
        } catch (JMSException ex) {
            Logger.getLogger(ServerPodsistem1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().build();
    }
    
    @GET
    @Path("dohvatanjeSlusanja/{idAudio}")
    public Response dohvatanjeSlusanja(@Context UriInfo uriInfo){
        try {
            String fullURL=uriInfo.getAbsolutePath().toString();
            
            JMSContext context=connFactory.createContext();
            JMSProducer producer=context.createProducer();
            TextMessage txt=context.createTextMessage();
            txt.setIntProperty("podsistem", 3);
            txt.setIntProperty("funkcija", 25);
            txt.setStringProperty("fulURL", fullURL); //odavde cemo da izvlacimo sve parametre

            producer.send(topic, txt);
        } catch (JMSException ex) {
            Logger.getLogger(ServerPodsistem1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().build();
    }
    
    @POST
    @Path("dodavanjeUOmiljene/{idAudio}/{idKorisnika}")
    public Response dodavanjeUOmiljene(@Context UriInfo uriInfo){
        try {
            String fullURL=uriInfo.getAbsolutePath().toString();
            
            JMSContext context=connFactory.createContext();
            JMSProducer producer=context.createProducer();
            TextMessage txt=context.createTextMessage();
            txt.setIntProperty("podsistem", 3);
            txt.setIntProperty("funkcija", 13);
            txt.setStringProperty("fulURL", fullURL); //odavde cemo da izvlacimo sve parametre

            producer.send(topic, txt);
        } catch (JMSException ex) {
            Logger.getLogger(ServerPodsistem1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().build();
    }
    
    @GET
    @Path("dohvatanjeOmiljenihZaKorisnika/{idKorisnika}")
    public Response dohvatanjeOmiljenihZaKorisnika(@Context UriInfo uriInfo){
        try {
            String fullURL=uriInfo.getAbsolutePath().toString();
            
            JMSContext context=connFactory.createContext();
            JMSProducer producer=context.createProducer();
            TextMessage txt=context.createTextMessage();
            txt.setIntProperty("podsistem", 3);
            txt.setIntProperty("funkcija", 27);
            txt.setStringProperty("fulURL", fullURL); //odavde cemo da izvlacimo sve parametre

            producer.send(topic, txt);
        } catch (JMSException ex) {
            Logger.getLogger(ServerPodsistem1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().build();
    }
    
    @POST
    @Path("ocenjivanjeAudioSnimka/{idKorisnika}/{idAudio}/{datumVreme}/{ocena}")
    public Response ocenjivanjeAudioSnimka(@Context UriInfo uriInfo){
        try {
            String fullURL=uriInfo.getAbsolutePath().toString();
            
            JMSContext context=connFactory.createContext();
            JMSProducer producer=context.createProducer();
            TextMessage txt=context.createTextMessage();
            txt.setIntProperty("podsistem", 3);
            txt.setIntProperty("funkcija", 14);
            txt.setStringProperty("fulURL", fullURL); //odavde cemo da izvlacimo sve parametre

            producer.send(topic, txt);
        } catch (JMSException ex) {
            Logger.getLogger(ServerPodsistem1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().build();
    }
    
    @GET
    @Path("dohvatanjeOcena/{idAudio}")
    public Response dohvatanjeOcena(@Context UriInfo uriInfo){
        try {
            String fullURL=uriInfo.getAbsolutePath().toString();
            
            JMSContext context=connFactory.createContext();
            JMSProducer producer=context.createProducer();
            TextMessage txt=context.createTextMessage();
            txt.setIntProperty("podsistem", 3);
            txt.setIntProperty("funkcija", 26);
            txt.setStringProperty("fulURL", fullURL); //odavde cemo da izvlacimo sve parametre

            producer.send(topic, txt);
        } catch (JMSException ex) {
            Logger.getLogger(ServerPodsistem1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().build();
    }
    
    @POST
    @Path("menjanjeOcene/{idKorisnika}/{idAudio}/{ocena}")
    public Response menjanjeOcene(@Context UriInfo uriInfo){
        try {
            String fullURL=uriInfo.getAbsolutePath().toString();
            
            JMSContext context=connFactory.createContext();
            JMSProducer producer=context.createProducer();
            TextMessage txt=context.createTextMessage();
            txt.setIntProperty("podsistem", 3);
            txt.setIntProperty("funkcija", 15);
            txt.setStringProperty("fulURL", fullURL); //odavde cemo da izvlacimo sve parametre

            producer.send(topic, txt);
        } catch (JMSException ex) {
            Logger.getLogger(ServerPodsistem1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().build();
    }
    
    @DELETE
    @Path("brisanjeOcene/{idAudio}/{idKorisnika}")
    public Response brisanjeOcene(@Context UriInfo uriInfo){
        try {
            String fullURL=uriInfo.getAbsolutePath().toString();
            
            JMSContext context=connFactory.createContext();
            JMSProducer producer=context.createProducer();
            TextMessage txt=context.createTextMessage();
            txt.setIntProperty("podsistem", 3);
            txt.setIntProperty("funkcija", 16);
            txt.setStringProperty("fulURL", fullURL); //odavde cemo da izvlacimo sve parametre

            producer.send(topic, txt);
        } catch (JMSException ex) {
            Logger.getLogger(ServerPodsistem1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().build();
    }
}
