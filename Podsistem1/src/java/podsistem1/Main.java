/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem1;

import entiteti.Korisnik;
import entiteti.Mesto;
import java.io.Serializable;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

public class Main {

    @Resource(lookup="jms/__defaultConnectionFactory")
    private static ConnectionFactory connFactory;
    @Resource(lookup="topic")
    private static Topic topic;
    
    private static EntityManagerFactory emf=Persistence.createEntityManagerFactory("Podsistem1PU");
    private static EntityManager em;
    
    public static void main(String[] args) {
        em=emf.createEntityManager();
        JMSContext context=connFactory.createContext();
        //context.setClientID("uniqueID1");
        JMSConsumer consumer=context.createConsumer(topic, "podsistem=1"); //non durable consumer
        JMSProducer producer=context.createProducer();
        
        while(true){
            try {
                Message msg=(Message)consumer.receive();
                TextMessage txt=(TextMessage)msg;
                int funkcija=txt.getIntProperty("funkcija");
                switch(funkcija){
                    case 1:
                        kreiranjeGrada(context, producer, txt);
                        break;
                    case 2:
                        kreiranjeKorisnika(context, producer, txt);
                        break;
                    case 3:
                        promenaEmaila(context, producer, txt);
                        break;
                    case 4:
                        promenaMesta(context, producer, txt);
                        break;
                    case 18:
                        dohvatanjeMesta(context, producer, txt);
                        break;
                    case 19:
                        dohvatanjeKorisnika(context, producer, txt);
                        break;
                }
            } catch (JMSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static boolean daLiJeJedinstveno(String tabela, String kolona, String vrednostKolone){
        TypedQuery<Long> upit=em.createQuery("SELECT COUNT(x) FROM "+tabela+" x WHERE x."+kolona+"=:prom", Long.class);
        upit.setParameter("prom", vrednostKolone);
        long broj=upit.getSingleResult();
        return broj==0;
    }
    
    private static void kreiranjeGrada(JMSContext context, JMSProducer producer, TextMessage txt) {
        try {
            em.getTransaction().begin();        
            String url=txt.getStringProperty("fulURL");
            String tokens[]=url.split("/");
            String nazivGrada=tokens[tokens.length-1];       
            nazivGrada=nazivGrada.replaceAll("%20", " ");
            TextMessage msg=context.createTextMessage();
            System.out.println(nazivGrada);
            if(daLiJeJedinstveno("Mesto", "naziv", nazivGrada)){
                Mesto mesto=new Mesto();
                mesto.setNaziv(nazivGrada);
                System.out.println("check1");
                //em.getTransaction().begin();
                em.persist(mesto);
                //em.getTransaction().commit();
                msg.setStringProperty("ishod", "Uspesno dodavanje grada "+nazivGrada);
                System.out.println("check2");
            }
            else
                msg.setStringProperty("ishod", "Neuspesno dodavanje grada "+nazivGrada+". "
                        + "Grad vec postoji!");
            
            msg.setIntProperty("podsistem", 0);
            producer.send(topic, msg);
            em.getTransaction().commit();
        } catch (JMSException ex) {
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void kreiranjeKorisnika(JMSContext context, JMSProducer producer, TextMessage txt) {
        TextMessage msg=context.createTextMessage();
        try {
            //em.clear();
            em.getTransaction().begin();
           
            String url=txt.getStringProperty("fulURL");
            String tokens[]=url.split("/");
            String nazivGrada=tokens[tokens.length-1];
            String pol=tokens[tokens.length-2];
            int godiste=Integer.parseInt(tokens[tokens.length-3]);
            String email=tokens[tokens.length-4];
            String ime=tokens[tokens.length-5];
            System.out.println("ckechpoint1");
            //ako ne postoji grad u bazi, dodajemo ga
            nazivGrada=nazivGrada.replace("%20", " ");
            ime=ime.replace("%20", " ");
            TypedQuery<Long> upit=em.createQuery("SELECT COUNT(m) FROM Mesto m WHERE m.naziv=:pom", Long.class);
            upit.setParameter("pom", nazivGrada);
            long brojac=upit.getSingleResult();
            System.out.println("ckechpoint2");
           //TextMessage msg=context.createTextMessage();
            Mesto mesto;
            if(brojac==0){
                mesto=new Mesto();               
                mesto.setNaziv(nazivGrada);
                //em.getTransaction().begin();
                em.persist(mesto);
                //em.getTransaction().commit();
            }
            else{
                mesto=em.createQuery("SELECT m FROM Mesto m WHERE m.naziv=:nazivGrada", Mesto.class).setParameter("nazivGrada", nazivGrada).
                        getSingleResult();
            }
            System.out.println("ckechpoint3");
            if(daLiJeJedinstveno("Korisnik", "email", email)){
                System.out.println("ckechpoint4");
                Korisnik k=new Korisnik();
                k.setIme(ime);
                k.setEmail(email);
                k.setGodiste(godiste);
                k.setPol(pol);   
                //em.getTransaction().begin();
                if(!em.contains(mesto)){
                    mesto=em.merge(mesto);
                    System.out.println("Usao ovde!");
                }
                k.setIdMesta(mesto);
                System.out.println(k.getIme());
                System.out.println(k.getEmail());
                System.out.println(k.getGodiste());
                System.out.println(k.getPol());
                System.out.println(k.getIdMesta().getNaziv());
                if(!em.isOpen())
                    System.out.println("Entity manager je zatvoren!");
                if(!em.getTransaction().isActive())
                    em.getTransaction().begin();
                em.persist(k);
                //em.getTransaction().commit();
                
                System.out.println("ckechpoint5");
                msg.setStringProperty("ishod", "Uspesno dodavanje korisnika "+ime);
            }
            else{
                msg.setStringProperty("ishod", "Neuspesno dodavanje korisnika. Email adresa vec postoji!");
            }
   
            msg.setIntProperty("podsistem", 0);
            producer.send(topic, msg);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
            ex.printStackTrace();
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void promenaEmaila(JMSContext context, JMSProducer producer, TextMessage txt) {
        try {
            em.getTransaction().begin();
            
            String url=txt.getStringProperty("fulURL");
            String tokens[]=url.split("/");
            String email=tokens[tokens.length-1];    
            int idKorisnika=Integer.parseInt(tokens[tokens.length-2]);
            
            TextMessage msg=context.createTextMessage();
            if(daLiJeJedinstveno("Korisnik", "email", email)){ //da li ce novi email biti jedinstven
                //em.getTransaction().begin();
                TypedQuery<Long> query=em.createQuery("UPDATE Korisnik k SET k.email=:pom WHERE k.idKorisnika="+idKorisnika, Long.class);
                query.setParameter("pom", email);
                int cnt=query.executeUpdate();
                //em.getTransaction().commit();
                msg.setStringProperty("ishod", "Uspesno promenjena email adresa!");
            }
            else
                msg.setStringProperty("ishod", "Neuspesno menjanje email adrese! Email adresa "+email+" je zauzeta!");
            
            msg.setIntProperty("podsistem", 0);
            producer.send(topic, msg);
            em.getTransaction().commit();
        } catch (JMSException ex) {
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void promenaMesta(JMSContext context, JMSProducer producer, TextMessage txt) {
        try {
            em.getTransaction().begin();
            
            String url=txt.getStringProperty("fulURL");
            String tokens[]=url.split("/");
            String naziv_mesta=tokens[tokens.length-1];    
            int idKorisnika=Integer.parseInt(tokens[tokens.length-2]);
            
            TextMessage msg=context.createTextMessage();
            
            naziv_mesta=naziv_mesta.replace("%20", " ");
            
            TypedQuery<Long> upit=em.createQuery("SELECT COUNT(m) FROM Mesto m WHERE m.naziv=:nazivGrada", Long.class);
            upit.setParameter("nazivGrada", naziv_mesta);
            long brojac=upit.getSingleResult();
            
            Mesto mesto;
            if(brojac==0){ //ako to mesto ne postoji
                mesto=new Mesto();
                mesto.setNaziv(naziv_mesta);
                //em.getTransaction().begin();
                em.persist(mesto);
                //em.getTransaction().commit();
            }
            else{
                mesto=em.createQuery("SELECT m FROM Mesto m WHERE m.naziv=:nazivGrada", Mesto.class).setParameter("nazivGrada", naziv_mesta).
                        getSingleResult();
            }
            //em.getTransaction().begin();
            TypedQuery<Long> query=em.createQuery("UPDATE Korisnik k SET k.idMesta=:pom WHERE k.idKorisnika="+idKorisnika, Long.class);
            query.setParameter("pom", mesto);
            int cnt=query.executeUpdate();
            //em.getTransaction().commit();
            msg.setStringProperty("ishod", "Uspesno promenjeno mesto!");
       
            msg.setIntProperty("podsistem", 0);
            producer.send(topic, msg);
            em.getTransaction().commit();
        } catch (JMSException ex) {
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void dohvatanjeMesta(JMSContext context, JMSProducer producer, TextMessage txt) {
        try {
            em.clear();
            TypedQuery<Mesto> upit=em.createQuery("SELECT m FROM Mesto m ORDER BY m.idMesta", Mesto.class);
            List<Mesto> listaMesta=upit.getResultList();
            ObjectMessage objMsg=context.createObjectMessage();
            objMsg.setIntProperty("podsistem", 0);
            objMsg.setObject((Serializable) listaMesta);
            producer.send(topic, objMsg);
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void dohvatanjeKorisnika(JMSContext context, JMSProducer producer, TextMessage txt) {
        try {
            em.clear();
            TypedQuery<Korisnik> upit=em.createQuery("SELECT k FROM Korisnik k ORDER BY k.idKorisnika", Korisnik.class);
            List<Korisnik> listaKorisnika=upit.getResultList();
            ObjectMessage objMsg=context.createObjectMessage();
            objMsg.setIntProperty("podsistem", 0);
            objMsg.setObject((Serializable) listaKorisnika);
            producer.send(topic, objMsg);
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
