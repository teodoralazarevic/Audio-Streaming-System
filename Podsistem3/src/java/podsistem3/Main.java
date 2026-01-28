/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem3;

import entiteti.Audio;
import entiteti.Korisnik;
import entiteti.Ocena;
import entiteti.OcenaPK;
import entiteti.Paket;
import entiteti.Pretplata;
import entiteti.Slusa;
import java.io.Serializable;
import static java.lang.Math.abs;
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
    
    private static EntityManagerFactory emf=Persistence.createEntityManagerFactory("Podsistem3PU");
    private static EntityManager em;
    
    public static void main(String[] args) {
        em=emf.createEntityManager();
        JMSContext context=connFactory.createContext();
        JMSConsumer consumer=context.createConsumer(topic, "podsistem=3"); //non durable consumer
        JMSProducer producer=context.createProducer();
        
        while(true){
            try {
                Message msg=(Message)consumer.receive();
                TextMessage txt=(TextMessage)msg;
                int funkcija=txt.getIntProperty("funkcija");
                switch(funkcija){
                    case 9:
                        kreiranjePaketa(context, producer, txt);
                        break;
                    case 10:
                        promenaCenePaketa(context, producer, txt);
                        break;
                    case 11:
                        kreiranjePretplate(context, producer, txt);
                        break;
                    case 12:
                        kreiranjeSlusanja(context, producer, txt);
                        break;
                    case 13:
                        dodavanjeUOmiljene(context, producer, txt);
                        break;
                    case 14:
                        kreiranjeOcene(context, producer, txt);
                        break;
                    case 15:
                        menjanjeOcene(context, producer, txt);
                        break;
                    case 16:
                        brisanjeOcene(context, producer, txt);
                        break;
                    case 23:
                        dohvatanjePaketa(context, producer, txt);
                        break;
                    case 24:
                        dohvatanjePretplataZaKorisnika(context, producer, txt);
                        break;
                    case 25:
                        dohvatanjeSlusanjaAudioSnimka(context, producer, txt);
                        break;
                    case 26:
                        dohvatanjeOcenaAudioSnimka(context, producer, txt);
                        break;
                    case 27:
                        dohvatanjeOmiljenihAudioSnimaka(context, producer, txt);
                        break;
                }
            } catch (JMSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static void kreiranjePaketa(JMSContext context, JMSProducer producer, TextMessage txt) {
         try {
            em.getTransaction().begin();
            
            String url=txt.getStringProperty("fulURL");
            String tokens[]=url.split("/");
            int cenaPaketa=Integer.parseInt(tokens[tokens.length-1]);       
            
            TextMessage msg=context.createTextMessage();
            Paket paket=new Paket();
            paket.setVazecaCena(cenaPaketa);
            em.persist(paket);
            msg.setStringProperty("ishod","Uspesno dodavanje paketa!");     
            msg.setIntProperty("podsistem", 0);
            producer.send(topic, msg);
            em.getTransaction().commit();
        } catch (JMSException ex) {
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void promenaCenePaketa(JMSContext context, JMSProducer producer, TextMessage txt) {
        try {
            em.getTransaction().begin();
            
            String url=txt.getStringProperty("fulURL");
            String tokens[]=url.split("/");
            int cena=Integer.parseInt(tokens[tokens.length-1]);    
            int idPaketa=Integer.parseInt(tokens[tokens.length-2]);
            
            TextMessage msg=context.createTextMessage();
            TypedQuery<Long> query=em.createQuery("UPDATE Paket p SET p.vazecaCena=:pom WHERE p.idPaketa="+idPaketa, Long.class);
            query.setParameter("pom", cena);
            int cnt=query.executeUpdate();
            msg.setStringProperty("ishod", "Uspesno promenjena cena paketa!");
            
            msg.setIntProperty("podsistem", 0);
            producer.send(topic, msg);
            em.getTransaction().commit();
        } catch (JMSException ex) {
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void kreiranjePretplate(JMSContext context, JMSProducer producer, TextMessage txt) {
         try {
            em.getTransaction().begin();
            
            String url=txt.getStringProperty("fulURL");
            String tokens[]=url.split("/");
            int idPaketa=Integer.parseInt(tokens[tokens.length-2]); 
            int idKorisnika=Integer.parseInt(tokens[tokens.length-3]);
            String datumVreme=tokens[tokens.length-1];
            
            String[] pom=datumVreme.split("%20");
            String[] datum=pom[0].split("-");
            String[] vreme=pom[1].split(":");
            Calendar calendar=Calendar.getInstance();
                calendar.set(Integer.parseInt(datum[0]), Integer.parseInt(datum[1])-1, Integer.parseInt(datum[2]),
                        Integer.parseInt(vreme[0]), Integer.parseInt(vreme[1]), Integer.parseInt(vreme[2]));
            
            Paket p=em.createQuery("SELECT p FROM Paket p WHERE p.idPaketa="+idPaketa, Paket.class).getSingleResult();
            Korisnik k=em.createQuery("SELECT k FROM Korisnik k WHERE k.idKorisnika="+idKorisnika, Korisnik.class).getSingleResult();
            
            TextMessage msg=context.createTextMessage();
            
            boolean mozePretplata=true;
            //Date datumSada=new Date();                  
            if(p==null){
                msg.setStringProperty("ishod", "Neuspesno kreiranje pretplate, paket ne postoji!");
                mozePretplata=false;
            }
            else if(k==null){
                msg.setStringProperty("ishod", "Neuspesno kreiranje pretplate, korisnik ne postoji!");
                mozePretplata=false;
            }
            else{
                TypedQuery<Pretplata> upit=em.createQuery("SELECT p FROM Pretplata p WHERE p.idKorisnika=:pom", Pretplata.class);
                upit.setParameter("pom", k);
                List<Pretplata> pretplate=upit.getResultList();
                if(pretplate.size()>0){ //ako korisnik ima pretplate, ne smeju da budu aktivne
                    for(Pretplata pr:pretplate){
                        Date datumPretplate=pr.getDatumVremePocetka();
                        long diffMS=calendar.getTime().getTime()-datumPretplate.getTime();
                        long diffS=diffMS/1000;
                        long diffMin=diffS/60;
                        long diffHours=diffMin/60;
                        long diffDays=diffHours/24;
                        if(abs(diffDays)<30){
                            mozePretplata=false;
                            msg.setStringProperty("ishod", "Neuspesno kreiranje pretplate, korisnik vec ima pretplatu!");
                            break;
                        }
                    }
                }
            }
            
            if(mozePretplata){
                Pretplata pretplata=new Pretplata();
                pretplata.setIdPaketa(p);
                pretplata.setIdKorisnika(k);
                pretplata.setDatumVremePocetka(calendar.getTime());
                pretplata.setCena(p.getVazecaCena());
                em.persist(pretplata);
                msg.setStringProperty("ishod","Uspesno dodavanje paketa!");  
                
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

    private static void dohvatanjePaketa(JMSContext context, JMSProducer producer, TextMessage txt) {
        try {
            em.clear();
            TypedQuery<Paket> upit=em.createQuery("SELECT p FROM Paket p ORDER BY p.idPaketa", Paket.class);
            List<Paket> listaPaketa=upit.getResultList();
            ObjectMessage objMsg=context.createObjectMessage();
            objMsg.setIntProperty("podsistem", 0);
            objMsg.setObject((Serializable) listaPaketa);
            producer.send(topic, objMsg);
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void dohvatanjePretplataZaKorisnika(JMSContext context, JMSProducer producer, TextMessage txt) {
         try {
            em.clear();
            String url=txt.getStringProperty("fulURL");
            String tokens[]=url.split("/");    
            int idKorisnika=Integer.parseInt(tokens[tokens.length-1]);        
            Korisnik k=em.find(Korisnik.class, idKorisnika);
            
            TypedQuery<Pretplata> upit=em.createQuery("SELECT p FROM Pretplata p WHERE p.idKorisnika=:pom", Pretplata.class);
            upit.setParameter("pom", k);
            List<Pretplata> listaKat=upit.getResultList();
            
            /*List<Pretplata> listaKat=null;
            em.refresh(k);
            listaKat=k.getPretplataList();*/
            ObjectMessage objMsg=context.createObjectMessage();
            objMsg.setIntProperty("podsistem", 0);
            objMsg.setObject((Serializable) listaKat);
            producer.send(topic, objMsg);
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void kreiranjeSlusanja(JMSContext context, JMSProducer producer, TextMessage txt) {
         try {
            em.getTransaction().begin();
            //{idKorisnika}/{idAudio}/{datumVreme}/{sekundZap}/{sekundOdsl}
            String url=txt.getStringProperty("fulURL");
            String tokens[]=url.split("/");
            int sekundOdsl=Integer.parseInt(tokens[tokens.length-1]);
            int sekundZap=Integer.parseInt(tokens[tokens.length-2]);
            String datumVreme=tokens[tokens.length-3];
            int idAudio=Integer.parseInt(tokens[tokens.length-4]);
            int idKorisnika=Integer.parseInt(tokens[tokens.length-5]);
            
            String[] pom=datumVreme.split("%20");
            String[] datum=pom[0].split("-");
            String[] vreme=pom[1].split(":");
            Calendar calendar=Calendar.getInstance();
                calendar.set(Integer.parseInt(datum[0]), Integer.parseInt(datum[1])-1, Integer.parseInt(datum[2]),
                        Integer.parseInt(vreme[0]), Integer.parseInt(vreme[1]), Integer.parseInt(vreme[2]));

            Korisnik k=em.createQuery("SELECT k FROM Korisnik k WHERE k.idKorisnika="+idKorisnika, Korisnik.class).getSingleResult();
            Audio a=em.createQuery("SELECT a FROM Audio a WHERE a.idAudio="+idAudio, Audio.class).getSingleResult();
            System.out.println("Provera1");
            TextMessage msg=context.createTextMessage();
                
            if(a==null){
                msg.setStringProperty("ishod", "Neuspesno kreiranje slusanja, audio zapis ne postoji!");
            }
            else if(k==null){
                msg.setStringProperty("ishod", "Neuspesno kreiranje slusanja, korisnik ne postoji!");
            }
            else{
                System.out.println("Provera2");
                Slusa slusanje=new Slusa();
                slusanje.setDatumVremePocetka(calendar.getTime());
                System.out.println("Provera3");
                slusanje.setIdAudio(a);
                slusanje.setIdKorisnika(k);
                slusanje.setSekundZapoceto(sekundZap);
                slusanje.setSekundiOdslusano(sekundOdsl);
                em.persist(slusanje);
                System.out.println("Provera4");
                msg.setStringProperty("ishod","Uspesno dodavanje slusanja!");  
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

    private static void dohvatanjeSlusanjaAudioSnimka(JMSContext context, JMSProducer producer, TextMessage txt) {
        try {
            em.clear();
            String url=txt.getStringProperty("fulURL");
            String tokens[]=url.split("/");
            int idAudio=Integer.parseInt(tokens[tokens.length-1]);
            Audio audio=em.find(Audio.class, idAudio);
            
            TypedQuery<Slusa> upit=em.createQuery("SELECT s FROM Slusa s WHERE s.idAudio=:pom", Slusa.class);
            upit.setParameter("pom", audio);
            List<Slusa> listaSlusanja=upit.getResultList();
            
            ObjectMessage objMsg=context.createObjectMessage();
            objMsg.setIntProperty("podsistem", 0);
            objMsg.setObject((Serializable) listaSlusanja);
            producer.send(topic, objMsg);
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void dodavanjeUOmiljene(JMSContext context, JMSProducer producer, TextMessage txt) {
        try {
            em.getTransaction().begin();
            
            String url=txt.getStringProperty("fulURL");
            String tokens[]=url.split("/");
            int idAudio=Integer.parseInt(tokens[tokens.length-2]); 
            int idKorisnika=Integer.parseInt(tokens[tokens.length-1]);
            
            Audio a=em.createQuery("SELECT a FROM Audio a WHERE a.idAudio="+idAudio, Audio.class).getSingleResult();
            Korisnik k=em.createQuery("SELECT k FROM Korisnik k WHERE k.idKorisnika="+idKorisnika, Korisnik.class).getSingleResult();
            
            TextMessage msg=context.createTextMessage();
                 
            if(a==null){
                msg.setStringProperty("ishod", "Neuspesno dodavanje audio zapisa u omiljene, audio zapis ne postoji!");
            }
            else if(k==null){
                msg.setStringProperty("ishod", "Neuspesno dodavanje audio zapisa u omiljene, korisnik ne postoji!");
            }
            else{
               em.refresh(a);
               List<Korisnik> korisnici=a.getKorisnikList(); //korisnici koji imaju taj video kao omiljeni
               if(korisnici.contains(k))
                   msg.setStringProperty("ishod", "Audio zapis je vec u omiljenim zapisima korisnika!");
               else{
                   korisnici.add(k);
                   a.setKorisnikList(korisnici);
                   msg.setStringProperty("ishod", "Uspesno dodavanje audio zapisa u omiljene!");
               }
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

    private static void dohvatanjeOmiljenihAudioSnimaka(JMSContext context, JMSProducer producer, TextMessage txt) {
        try {
            em.clear();
            String url=txt.getStringProperty("fulURL");
            String tokens[]=url.split("/");    
            int idKorisnika=Integer.parseInt(tokens[tokens.length-1]); 
            
            Korisnik k=em.find(Korisnik.class, idKorisnika);
            List<Audio> listaSnimaka=null;
            em.refresh(k);
            listaSnimaka=k.getAudioList();
            //em.refresh(listaSnimaka);
            System.out.println(listaSnimaka.size());
            ObjectMessage objMsg=context.createObjectMessage();
            objMsg.setIntProperty("podsistem", 0);
            objMsg.setObject((Serializable) listaSnimaka);
            producer.send(topic, objMsg);
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void kreiranjeOcene(JMSContext context, JMSProducer producer, TextMessage txt) {
        try {
            em.getTransaction().begin();
            //{idKorisnika}/{idAudio}/{datumVreme}/{ocena}
            String url=txt.getStringProperty("fulURL");
            String tokens[]=url.split("/");
            String datumVreme=tokens[tokens.length-2];
            int idAudio=Integer.parseInt(tokens[tokens.length-3]);
            int idKorisnika=Integer.parseInt(tokens[tokens.length-4]);
            int ocena=Integer.parseInt(tokens[tokens.length-1]);
            
            String[] pom=datumVreme.split("%20");
            String[] datum=pom[0].split("-");
            String[] vreme=pom[1].split(":");
            Calendar calendar=Calendar.getInstance();
                calendar.set(Integer.parseInt(datum[0]), Integer.parseInt(datum[1])-1, Integer.parseInt(datum[2]),
                        Integer.parseInt(vreme[0]), Integer.parseInt(vreme[1]), Integer.parseInt(vreme[2]));

            Korisnik k=em.createQuery("SELECT k FROM Korisnik k WHERE k.idKorisnika="+idKorisnika, Korisnik.class).getSingleResult();
            Audio a=em.createQuery("SELECT a FROM Audio a WHERE a.idAudio="+idAudio, Audio.class).getSingleResult();
            
            TextMessage msg=context.createTextMessage();
                
            if(a==null){
                msg.setStringProperty("ishod", "Neuspesno ocenjivanje, audio zapis ne postoji!");
            }
            else if(k==null){
                msg.setStringProperty("ishod", "Neuspesno ocenjivanje, korisnik ne postoji!");
            }
            else if(ocena<1 || ocena>5){
                msg.setStringProperty("ishod", "Neusepsno ocenjivanje, ocena mora biti u opsegu 1-5!");
            }
            else{
                TypedQuery<Long> upit=em.createQuery("SELECT COUNT(o) FROM Ocena o WHERE o.korisnik=:pom AND o.audio=:pom2", Long.class);
                upit.setParameter("pom", k);
                upit.setParameter("pom2", a);
                long rez=upit.getSingleResult();
                if(rez>0){
                    msg.setStringProperty("ishod","Korisnik je vec ocenio audio snimak!");
                }
                else{               
                    Ocena ocenjivanje=new Ocena();
                    ocenjivanje.setOcenaPK(new OcenaPK(k.getIdKorisnika(), a.getIdAudio()));
                    ocenjivanje.setKorisnik(k);
                    ocenjivanje.setAudio(a);
                    ocenjivanje.setDatumVremeDavanja(calendar.getTime());
                    ocenjivanje.setOcena(ocena);
                    em.persist(ocenjivanje);
                    msg.setStringProperty("ishod","Uspesno ocenjivanje audio zapisa!");
                }
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

    private static void dohvatanjeOcenaAudioSnimka(JMSContext context, JMSProducer producer, TextMessage txt) {
        try {
            em.clear();
            String url=txt.getStringProperty("fulURL");
            String tokens[]=url.split("/");
            int idAudio=Integer.parseInt(tokens[tokens.length-1]);
            Audio audio=em.find(Audio.class, idAudio);
            System.out.println("checkpoint1");
            TypedQuery<Ocena> upit=em.createQuery("SELECT o FROM Ocena o WHERE o.audio=:pom", Ocena.class);
            upit.setParameter("pom", audio);
            System.out.println("checkpoint2");
            List<Ocena> ocene=upit.getResultList();
            System.out.println(ocene.size());
            ObjectMessage objMsg=context.createObjectMessage();
            objMsg.setIntProperty("podsistem", 0);
            objMsg.setObject((Serializable) ocene);
            producer.send(topic, objMsg);
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void menjanjeOcene(JMSContext context, JMSProducer producer, TextMessage txt) {
       try {
            em.getTransaction().begin();
            //{idKorisnika}/{idAudio}/{datumVreme}/{ocena}
            String url=txt.getStringProperty("fulURL");
            String tokens[]=url.split("/");
            String datumVreme=tokens[tokens.length-2];
            int idAudio=Integer.parseInt(tokens[tokens.length-2]);
            int idKorisnika=Integer.parseInt(tokens[tokens.length-3]);
            int ocena=Integer.parseInt(tokens[tokens.length-1]);

            Korisnik k=em.createQuery("SELECT k FROM Korisnik k WHERE k.idKorisnika="+idKorisnika, Korisnik.class).getSingleResult();
            Audio a=em.createQuery("SELECT a FROM Audio a WHERE a.idAudio="+idAudio, Audio.class).getSingleResult();
            
            TextMessage msg=context.createTextMessage();
                
            if(a==null){
                msg.setStringProperty("ishod", "Neuspesno menjanje ocene, audio zapis ne postoji!");
            }
            else if(k==null){
                msg.setStringProperty("ishod", "Neuspesno menjanje ocene, korisnik ne postoji!");
            }
            else if(ocena<1 || ocena>5){
                msg.setStringProperty("ishod", "Neusepsno menjanje ocene, ocena mora biti u opsegu 1-5!");
            }
            else{
                TypedQuery<Ocena> upit=em.createQuery("SELECT o FROM Ocena o WHERE o.korisnik=:pom AND o.audio=:pom2", Ocena.class);
                upit.setParameter("pom", k);
                upit.setParameter("pom2", a);
                List<Ocena> rez=upit.getResultList();
                if(rez.size()==0){
                    msg.setStringProperty("ishod","Korisnik nije ocenio audio snimak!");
                }
                else{    
                    TypedQuery<Long> upit2=em.createQuery("UPDATE Ocena o SET o.ocena=:pom, o.datumVremeDavanja=:pomDate"
                            + " WHERE o.korisnik=:pomKor AND o.audio=:pomAud", Long.class);
                    upit2.setParameter("pomDate", new Date());
                    upit2.setParameter("pom", ocena);
                    upit2.setParameter("pomKor", k);
                    upit2.setParameter("pomAud", a);
                    int x=upit2.executeUpdate();
                    msg.setStringProperty("ishod","Uspesno menjanje ocene audio zapisa!");
                }
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

    private static void brisanjeOcene(JMSContext context, JMSProducer producer, TextMessage txt) {
        try {
            em.getTransaction().begin();
            
            String url=txt.getStringProperty("fulURL");
            String tokens[]=url.split("/");
            
            int idAudia=Integer.parseInt(tokens[tokens.length-2]);
            int idKorisnika=Integer.parseInt(tokens[tokens.length-1]);
            
            Audio audio=em.find(Audio.class, idAudia);
            Korisnik korisnik=em.find(Korisnik.class, idKorisnika);
            TypedQuery<Ocena> upit=em.createQuery("SELECT o FROM Ocena o WHERE o.korisnik=:pom AND o.audio=:pom2", Ocena.class);
            upit.setParameter("pom", korisnik);
            upit.setParameter("pom2", audio);
            List<Ocena> rez=upit.getResultList();
            TextMessage msg=context.createTextMessage();
            if(rez.size()==0)
                msg.setStringProperty("ishod","Korisnik nije ocenio audio snimak!");
            else{
                Ocena o=rez.get(0);
                em.remove(o);
                msg.setStringProperty("ishod", "Uspesno brisanje ocene!");
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
    
}
