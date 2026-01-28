/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem2;

import entiteti.Audio;
import entiteti.Kategorija;
import entiteti.Korisnik;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import javax.persistence.TypedQuery;

/**
 *
 * @author Laza
 */
public class Main {

    @Resource(lookup="jms/__defaultConnectionFactory")
    private static ConnectionFactory connFactory;
    @Resource(lookup="topic")
    private static Topic topic;
    
    private static EntityManagerFactory emf=Persistence.createEntityManagerFactory("Podsistem2PU");
    private static EntityManager em;
    
    public static void main(String[] args) {
        em=emf.createEntityManager();
        JMSContext context=connFactory.createContext();
        //context.setClientID("uniqueID3");
        JMSConsumer consumer=context.createConsumer(topic, "podsistem=2"); //non durable consumer
        JMSProducer producer=context.createProducer();
        
        while(true){
            try {
                Message msg=(Message)consumer.receive();
                TextMessage txt=(TextMessage)msg;
                int funkcija=txt.getIntProperty("funkcija");
                switch(funkcija){
                    case 5:
                        kreiranjeKategorije(context, producer, txt);
                        break;
                    case 6:
                        kreiranjeAudioSnimka(context, producer, txt);
                        break;
                    case 7:
                        promenaNazivaAudio(context, producer, txt);
                        break;
                    case 8:
                        dodavanjeKatAudioSnimku(context, producer, txt);
                        break;
                    case 17:
                        brisanjeAudioSnimka(context, producer, txt);
                        break;
                    case 20:
                        dohvatanjeKategorija(context, producer, txt);
                        break;
                    case 21:
                        dohvatanjeAudioSnimaka(context, producer, txt);
                        break;
                    case 22:
                        dohvatanjeKatZaAudio(context, producer, txt);
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
        System.out.println(vrednostKolone);
        long broj=upit.getSingleResult();
        return broj==0;
    }
    
    private static void kreiranjeKategorije(JMSContext context, JMSProducer producer, TextMessage txt) {
        try {
            em.getTransaction().begin();
            
            String url=txt.getStringProperty("fulURL");
            String tokens[]=url.split("/");
            String nazivKategorije=tokens[tokens.length-1];       
            nazivKategorije=nazivKategorije.replace("%20", " ");
            TextMessage msg=context.createTextMessage();
            if(daLiJeJedinstveno("Kategorija", "naziv", nazivKategorije)){
                Kategorija kat=new Kategorija();
                kat.setNaziv(nazivKategorije);
                em.persist(kat);
                msg.setStringProperty("ishod", "Uspesno dodavanje kategorije "+nazivKategorije);
            }
            else
                msg.setStringProperty("ishod", "Neuspesno dodavanje kategorije "+nazivKategorije+". "
                        + "Kategorija vec postoji!");
            
            msg.setIntProperty("podsistem", 0);
            producer.send(topic, msg);
            em.getTransaction().commit();
        } catch (JMSException ex) {
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void kreiranjeAudioSnimka(JMSContext context, JMSProducer producer, TextMessage txt) {
        try {
            //kreiranjeAudio/"+naziv+"/"+trajanje+"/"+datumVreme+"/"+vlasnik
            em.getTransaction().begin();
            
            String url=txt.getStringProperty("fulURL");
            String tokens[]=url.split("/");
            int vlasnik=Integer.parseInt(tokens[tokens.length-1]);
            String datumVreme=tokens[tokens.length-2];
            int trajanje=Integer.parseInt(tokens[tokens.length-3]);
            String naziv=tokens[tokens.length-4];
            naziv=naziv.replaceAll("%20", " ");
            
            TextMessage msg=context.createTextMessage();
            
            Korisnik k=em.find(Korisnik.class, vlasnik);
            if(k==null){
                msg.setStringProperty("ishod", "Neuspesno dodavanje audio zapisa. Vlasnik ne postoji u bazi!");
            }
            else{
                Audio audio=new Audio();
                audio.setIdVlasnika(k);
                audio.setNaziv(naziv);
                audio.setTrajanje(trajanje);
                String[] pom=datumVreme.split("%20");
                String[] datum=pom[0].split("-");
                String[] vreme=pom[1].split(":");
                Calendar calendar=Calendar.getInstance();
                calendar.set(Integer.parseInt(datum[0]), Integer.parseInt(datum[1])-1, Integer.parseInt(datum[2]),
                        Integer.parseInt(vreme[0]), Integer.parseInt(vreme[1]), Integer.parseInt(vreme[2]));
                audio.setDatumVremePostavljanja(calendar.getTime());
                if(!em.isOpen())
                    System.out.println("Entity manager je zatvoren!");
                if(!em.getTransaction().isActive())
                    em.getTransaction().begin();
                em.persist(audio);
                msg.setStringProperty("ishod", "Uspesno dodavanje audio snimka!");
            }
            
            msg.setIntProperty("podsistem", 0);
            producer.send(topic, msg);
            em.getTransaction().commit();
        } catch (JMSException ex) {
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void promenaNazivaAudio(JMSContext context, JMSProducer producer, TextMessage txt) {
        try {
            em.getTransaction().begin();
            
            String url=txt.getStringProperty("fulURL");
            String tokens[]=url.split("/");
            String naziv=tokens[tokens.length-1];    
            naziv=naziv.replaceAll("%20", " ");
            int idAudia=Integer.parseInt(tokens[tokens.length-2]);
            
            TextMessage msg=context.createTextMessage();
            TypedQuery<Long> query=em.createQuery("UPDATE Audio a SET a.naziv=:pom WHERE a.idAudio="+idAudia, Long.class);
            query.setParameter("pom", naziv);
            int cnt=query.executeUpdate();
            msg.setStringProperty("ishod", "Uspesno promenjeno ime audio snimka");
            
            msg.setIntProperty("podsistem", 0);
            producer.send(topic, msg);
            em.getTransaction().commit();
        } catch (JMSException ex) {
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void dodavanjeKatAudioSnimku(JMSContext context, JMSProducer producer, TextMessage txt) {
        try {
            em.getTransaction().begin();
            
            String url=txt.getStringProperty("fulURL");
            String tokens[]=url.split("/");
            String nazivKategorije=tokens[tokens.length-2];  
            nazivKategorije=nazivKategorije.replace("%20", " ");
            int idAudia=Integer.parseInt(tokens[tokens.length-1]);
            
            //da li kategorija postoji u sistemu vec
            TypedQuery<Kategorija> upit=em.createQuery("SELECT k FROM Kategorija k WHERE k.naziv=:pom", Kategorija.class);
            upit.setParameter("pom", nazivKategorije);
            List<Kategorija> rez=upit.getResultList();
            
            Kategorija kat;
            if(rez.size()==0){ //kategorija se dodaje u bazu prvo
                kat=new Kategorija();
                kat.setNaziv(nazivKategorije);
                em.persist(kat);
            }
            else
                kat=rez.get(0);
            
            Audio audio=em.find(Audio.class, idAudia);
            //dodavanje kategorije kod objekta audio
            List<Kategorija> listKat=audio.getKategorijaList();
            listKat.add(kat);
            audio.setKategorijaList(listKat);
            //dodavanje audia kod objekta kategorija
            List<Audio> listAudio=kat.getAudioList();
            if(listAudio==null)
                listAudio=new ArrayList<>();
            listAudio.add(audio);
            kat.setAudioList(listAudio);
            
            TextMessage msg=context.createTextMessage();
            msg.setStringProperty("ishod", "Uspesno dodvanje kategorije audio snimku!");
            
            msg.setIntProperty("podsistem", 0);
            producer.send(topic, msg);
            em.getTransaction().commit();
        } catch (JMSException ex) {
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void brisanjeAudioSnimka(JMSContext context, JMSProducer producer, TextMessage txt) {
        try {
            em.getTransaction().begin();
            
            String url=txt.getStringProperty("fulURL");
            String tokens[]=url.split("/");
            
            int idAudia=Integer.parseInt(tokens[tokens.length-2]);
            int idKorisnika=Integer.parseInt(tokens[tokens.length-1]);
            
            Audio audio=em.find(Audio.class, idAudia);
            Korisnik korisnik=em.find(Korisnik.class, idKorisnika);
            
            TextMessage msg=context.createTextMessage();
            if(audio==null){
                msg.setStringProperty("ishod", "Neuspesno brisanje audio snimka, dati audio snimak ne postoji!");
            }     
            else if(audio.getIdVlasnika()==korisnik){
                em.remove(audio);
                msg.setStringProperty("ishod", "Uspesno brisanje audio snimka!");
            }
            else{
                msg.setStringProperty("ishod", "Neuspesno brisanje audio snimka, dati korisnik nije vlasnik!");
            }
     
            msg.setIntProperty("podsistem", 0);
            producer.send(topic, msg);
            em.getTransaction().commit();
        } catch (JMSException ex) {
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void dohvatanjeKategorija(JMSContext context, JMSProducer producer, TextMessage txt) {
        try {
            em.clear();
            TypedQuery<Kategorija> upit=em.createQuery("SELECT k FROM Kategorija k ORDER BY k.idKategorije", Kategorija.class);
            List<Kategorija> listaKat=upit.getResultList();
            ObjectMessage objMsg=context.createObjectMessage();
            objMsg.setIntProperty("podsistem", 0);
            objMsg.setObject((Serializable) listaKat);
            producer.send(topic, objMsg);
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void dohvatanjeAudioSnimaka(JMSContext context, JMSProducer producer, TextMessage txt) {
         try {
            em.clear();
            TypedQuery<Audio> upit=em.createQuery("SELECT a FROM Audio a ORDER BY a.idAudio", Audio.class);
            List<Audio> listaAudio=upit.getResultList();
            ObjectMessage objMsg=context.createObjectMessage();
            objMsg.setIntProperty("podsistem", 0);
            objMsg.setObject((Serializable) listaAudio);
            producer.send(topic, objMsg);
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void dohvatanjeKatZaAudio(JMSContext context, JMSProducer producer, TextMessage txt) {
        try {
           // em.clear();
            String url=txt.getStringProperty("fulURL");
            String tokens[]=url.split("/");    
            int idAudia=Integer.parseInt(tokens[tokens.length-1]);
            Audio a=em.find(Audio.class, idAudia);
            ObjectMessage objMsg=context.createObjectMessage();
            if(a==null){
                objMsg.setStringProperty("ishod", "Audio snimak ne postoji u bazi!");
            }
            else{           
                List<Kategorija> listaKat=null;
                //em.refresh(a);
                listaKat=a.getKategorijaList(); 
                a.getKategorijaList().size();
                objMsg.setObject((Serializable) listaKat);
                objMsg.setIntProperty("podsistem", 0);
                producer.send(topic, objMsg);
            }
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
