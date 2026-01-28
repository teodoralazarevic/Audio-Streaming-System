/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klijent;

import entiteti.Audio;
import entiteti.Kategorija;
import entiteti.Korisnik;
import entiteti.Mesto;
import entiteti.Ocena;
import entiteti.Paket;
import entiteti.Pretplata;
import entiteti.Slusa;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.jms.Topic;

public class Main {

    private static final String url1="http://localhost:8080/CentralniServer/resources/podsistem1";
    private static final String url2="http://localhost:8080/CentralniServer/resources/podsistem2";
    private static final String url3="http://localhost:8080/CentralniServer/resources/podsistem3";
    
    @Resource(lookup="jms/__defaultConnectionFactory")
    private static ConnectionFactory connFactory;
    @Resource(lookup="topic")
    private static Topic topic;
    
    public static void main(String[] args) {
        JMSContext context=connFactory.createContext();
        //context.setClientID("uniqueID2");
        JMSConsumer consumer=context.createConsumer(topic, "podsistem=0"); //non-durable consumer
        
        System.out.println("START APLIKACIJE\n");
        Scanner scanner=new Scanner(System.in);
        int komanda=1;
        
        while(komanda!=0){
            System.out.println("\nIzaberite jednu od opcija:\n");
            System.out.println("0. IZLAZ");
            System.out.println("1. Kreiranje grada");
            System.out.println("2. Kreiranje korisnika");
            System.out.println("3. Promena email adrese za korisnika");
            System.out.println("4. Promena mesta za korisnika");
            System.out.println("5. Kreiranje kategorije");
            System.out.println("6. Kreiranje audio snimka");
            System.out.println("7. Promena naziva audio snimka");
            System.out.println("8. Dodavanje kategorije audio snimku");
            System.out.println("9. Kreiranje paketa");
            System.out.println("10. Promena mesecne cene za paket");
            System.out.println("11. Kreiranje pretplate korisnika na paket");
            System.out.println("12. Kreiranje slušanja audio snimka od strane korisnika");
            System.out.println("13. Dodavanje audio snimka u omiljene od strane korisnika");
            System.out.println("14. Kreiranje ocene korisnika za audio snimak");
            System.out.println("15. Menjanje ocene korisnika za audio snimak");
            System.out.println("16. Brisanje ocene korisnika za audio snimak");
            System.out.println("17. Brisanje audio snimka od strane korisnika koji ga je kreirao");
            System.out.println("18. Dohvatanje svih mesta");
            System.out.println("19. Dohvatanje svih korisnika");
            System.out.println("20. Dohvatanje svih kategorija");
            System.out.println("21. Dohvatanje svih audio snimaka");
            System.out.println("22. Dohvatanje kategorija za odredjeni audio snimak");
            System.out.println("23. Dohvatanje svih paketa");
            System.out.println("24. Dohvatanje svih pretplata za korisnika");
            System.out.println("25. Dohvatanje svih slušanja za audio snimak");
            System.out.println("26. Dohvatanje svih ocena za audio snimak");
            System.out.println("27. Dohvatanje liste omiljenih audio snimaka za korisnika");
                
            komanda=scanner.nextInt();
            scanner.nextLine();
            if(komanda==0)
                break;
            
            switch(komanda){
                case 1:
                    kreiranjeGrada(consumer, scanner);
                    break;
                case 2:
                    kreiranjeKorisnika(consumer, scanner);
                    break;
                case 3:
                    promenaEmaila(consumer, scanner);
                    break;
                case 4:
                    promenaMesta(consumer, scanner);
                    break;
                case 5:
                    kreiranjeKategorije(consumer, scanner);
                    break;
                case 6:
                    kreiranjeAudio(consumer, scanner);
                    break;
                case 7:
                    promenaNazivaAudio(consumer, scanner);
                    break;
                case 8:
                    dodavanjeKategorijeAudioSnimku(consumer, scanner);
                    break;
                case 9:
                    kreiranjePaketa(consumer, scanner);
                    break;
                case 10:
                    promenaCeneZaPaket(consumer, scanner);
                    break;
                case 11:
                    kreiranjePretplate(consumer, scanner);
                    break;
                case 12:
                    kreiranjeSlusanja(consumer, scanner);
                    break;
                case 13:
                    dodavanjeUOmiljene(consumer, scanner);
                    break;
                case 14:
                    ocenjivanjeAudioSnimka(consumer, scanner);
                    break;
                case 15:
                    menjanjeOcene(consumer, scanner);
                    break;
                case 16:
                    brisanjeOcene(consumer, scanner);
                    break;
                case 17:
                    brisanjeAudioSnimka(consumer, scanner);
                    break;
                case 18:
                    dohvSvihMesta(consumer, scanner);
                    break;
                case 19:
                    dohvSveKorisnike(consumer, scanner);
                    break;
                case 20:
                    dohvSveKategorije(consumer, scanner);
                    break;
                case 21:
                    dohvSveAudioSnimke(consumer, scanner);
                    break;
                case 22:
                    dohvKategorijaZaAudio(consumer, scanner);
                    break;
                case 23:
                    dohvatanjePaketa(consumer, scanner);
                    break;
                case 24:
                    dohvatanjePretplataZaKorisnika(consumer, scanner);
                    break;
                case 25:
                    dohvatanjeSlusanjaAudioSnimka(consumer, scanner);
                    break;
                case 26:
                    dohvatanjeOcenaAudioSnimka(consumer, scanner);
                    break;
                case 27:
                    dohvatanjeOmiljenih(consumer, scanner);
                    break;
            }
        }
    }

    private static void kreiranjeGrada(JMSConsumer consumer, Scanner scanner) {
        try {
            System.out.println("KREIRANJE GRADA...");
            System.out.println("Unesite naziv grada:");
            String naziv=scanner.nextLine();
            naziv=naziv.replaceAll(" ", "%20"); //svi razmaci ce biti + u URL adresi

            URL url=new URL(url1+"/kreiranjeGrada/"+naziv);
            HttpURLConnection konekcija=(HttpURLConnection)url.openConnection();
            konekcija.setRequestMethod("POST");
            konekcija.setDoOutput(true);
            konekcija.setDoInput(true);
            int code=konekcija.getResponseCode();
            System.out.println("Response code: "+code);

            Message msg=consumer.receive();
            TextMessage objMsg=(TextMessage)msg;
            System.out.println(objMsg.getStringProperty("ishod"));
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void kreiranjeKorisnika(JMSConsumer consumer, Scanner scanner) {
        try {
            System.out.println("KREIRANJE KORISNIKA...");
            System.out.println("Unesite ime korisnika:");
            String ime=scanner.nextLine();
            ime=ime.replaceAll(" ", "%20");
            System.out.println("Unesite email korisnika:");
            String email=scanner.nextLine();

            System.out.println("Unesite godiste korisnika:");
            int godiste=scanner.nextInt();
            scanner.nextLine();
            System.out.println("Unesite pol korisnika:");
            String pol=scanner.nextLine();

            System.out.println("Unesite mesto prebivalista korisnika:");
            String mesto=scanner.nextLine();
            mesto=mesto.replaceAll(" ", "%20");
            
            URL url=new URL(url1+"/kreiranjeKorisnika/"+ime+"/"+email+"/"+godiste+"/"+pol+"/"+mesto);
            HttpURLConnection konekcija=(HttpURLConnection)url.openConnection();
            konekcija.setRequestMethod("POST");
            konekcija.setDoOutput(true);
            konekcija.setDoInput(true);
            int code=konekcija.getResponseCode();
            System.out.println("Response code: "+code);

            Message msg=consumer.receive();
            TextMessage objMsg=(TextMessage)msg;
            System.out.println(objMsg.getStringProperty("ishod"));
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void promenaEmaila(JMSConsumer consumer, Scanner scanner) {
        try {
            System.out.println("PROMENA EMAIL-A...");
            System.out.println("Unesite id korisnika:");
            int idKorisnika=scanner.nextInt();
            scanner.nextLine();
            System.out.println("Unesite novi email:");
            String email=scanner.nextLine();

            URL url=new URL(url1+"/promenaEmaila/"+idKorisnika+"/"+email);
            HttpURLConnection konekcija=(HttpURLConnection)url.openConnection();
            konekcija.setRequestMethod("POST");
            konekcija.setDoOutput(true);
            konekcija.setDoInput(true);
            int code=konekcija.getResponseCode();
            System.out.println("Response code: "+code);

            Message msg=consumer.receive();
            TextMessage objMsg=(TextMessage)msg;
            System.out.println(objMsg.getStringProperty("ishod"));
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void promenaMesta(JMSConsumer consumer, Scanner scanner) {
         try {
            System.out.println("PROMENA MESTA KORISNIKA...");
            System.out.println("Unesite id korisnika:");
            int idKorisnika=scanner.nextInt();
            scanner.nextLine();
            System.out.println("Unesite novo mesto:");
            String mesto=scanner.nextLine();

            URL url=new URL(url1+"/promenaMesta/"+idKorisnika+"/"+mesto);
            HttpURLConnection konekcija=(HttpURLConnection)url.openConnection();
            konekcija.setRequestMethod("POST");
            konekcija.setDoOutput(true);
            konekcija.setDoInput(true);
            int code=konekcija.getResponseCode();
            System.out.println("Response code: "+code);

            Message msg=consumer.receive();
            TextMessage objMsg=(TextMessage)msg;
            System.out.println(objMsg.getStringProperty("ishod"));
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void dohvSvihMesta(JMSConsumer consumer, Scanner scanner) {
        try {
            System.out.println("DOHVATANJE SVIH MESTA...");
            URL url=new URL(url1+"/dohvatanjeMesta");
            HttpURLConnection konekcija=(HttpURLConnection)url.openConnection();
            konekcija.setRequestMethod("GET");
            konekcija.setDoOutput(true);
            int code=konekcija.getResponseCode();
            System.out.println("Response code: "+code);

            Message msg=consumer.receive();
            ObjectMessage objMsg=(ObjectMessage)msg;
            List<Mesto> objectList=(List<Mesto>)objMsg.getObject();
            System.out.println("Sva mesta:\n");
            for(Mesto obj:objectList){
                System.out.println(obj.getIdMesta()+": "+obj.getNaziv());
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void dohvSveKorisnike(JMSConsumer consumer, Scanner scanner) {
       try {
            System.out.println("DOHVATANJE SVIH KORISNIKA...");
            URL url=new URL(url1+"/dohvatanjeKorisnika");
            HttpURLConnection konekcija=(HttpURLConnection)url.openConnection();
            konekcija.setRequestMethod("GET");
            konekcija.setDoOutput(true);
            //konekcija.setDoInput(true);
            int code=konekcija.getResponseCode();
            System.out.println("Response code: "+code);

            Message msg=consumer.receive();
            ObjectMessage objMsg=(ObjectMessage)msg;
            List<Korisnik> objectList=(List<Korisnik>)objMsg.getObject();
            System.out.println("Svi korisnici:\n");
            for(Korisnik obj:objectList){
                System.out.println(obj.getIdKorisnika()+": "+obj.getIme()+" | "+obj.getEmail()+" | "+obj.getGodiste()+" | "+obj.getIdMesta().getNaziv());
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void kreiranjeKategorije(JMSConsumer consumer, Scanner scanner) {
        try {
            System.out.println("KREIRANJE KATEGORIJE...");
            System.out.println("Unesite naziv kategorije:");
            String naziv=scanner.nextLine();
            naziv=naziv.replaceAll(" ", "%20"); //svi razmaci ce biti + u URL adresi

            URL url=new URL(url2+"/kreiranjeKategorije/"+naziv);
            HttpURLConnection konekcija=(HttpURLConnection)url.openConnection();
            konekcija.setRequestMethod("POST");
            konekcija.setDoOutput(true);
            konekcija.setDoInput(true);
            int code=konekcija.getResponseCode();
            System.out.println("Response code: "+code);

            Message msg=consumer.receive();
            TextMessage objMsg=(TextMessage)msg;
            System.out.println(objMsg.getStringProperty("ishod"));
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void kreiranjeAudio(JMSConsumer consumer, Scanner scanner) {
        try {
            System.out.println("KREIRANJE AUDIO ZAPISA...");
            System.out.println("Unesite naziv audio snimka:");
            String naziv=scanner.nextLine();
            naziv=naziv.replaceAll(" ", "%20"); //svi razmaci ce biti %20 u URL adresi
            System.out.println("Unesite trajanje audio snimka:");
            System.out.println("Minuti:");
            int minuti=scanner.nextInt();
            scanner.nextLine();
            System.out.println("Sekunde:");
            int sekunde=scanner.nextInt();
            scanner.nextLine();
            System.out.println("Unesite datum/vreme postavljanja (format: yyyy-MM-dd hh:mm:ss):");
            String datumVreme=scanner.nextLine();
            datumVreme=datumVreme.replaceAll(" ", "%20");
            System.out.println("Unesite ID vlasnika:");
            int vlasnik=scanner.nextInt();
                       
            int trajanje=minuti*60+sekunde;
            URL url=new URL(url2+"/kreiranjeAudio/"+naziv+"/"+trajanje+"/"+datumVreme+"/"+vlasnik);
            HttpURLConnection konekcija=(HttpURLConnection)url.openConnection();
            konekcija.setRequestMethod("POST");
            konekcija.setDoOutput(true);
            konekcija.setDoInput(true);
            int code=konekcija.getResponseCode();
            System.out.println("Response code: "+code);

            Message msg=consumer.receive();
            TextMessage objMsg=(TextMessage)msg;
            System.out.println(objMsg.getStringProperty("ishod"));
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void promenaNazivaAudio(JMSConsumer consumer, Scanner scanner) {
        try {
            System.out.println("PROMENA NAZIVA AUDIO SNIMKA...");
            System.out.println("Unesite id audio snimka:");
            int idAudio=scanner.nextInt();
            scanner.nextLine();
            System.out.println("Unesite novi naziv audio snimka:");
            String naziv=scanner.nextLine();
            naziv=naziv.replace(" ", "%20");
            
            URL url=new URL(url2+"/promenaNazivaAudio/"+idAudio+"/"+naziv);
            HttpURLConnection konekcija=(HttpURLConnection)url.openConnection();
            konekcija.setRequestMethod("POST");
            konekcija.setDoOutput(true);
            konekcija.setDoInput(true);
            int code=konekcija.getResponseCode();
            System.out.println("Response code: "+code);

            Message msg=consumer.receive();
            TextMessage objMsg=(TextMessage)msg;
            System.out.println(objMsg.getStringProperty("ishod"));
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void dodavanjeKategorijeAudioSnimku(JMSConsumer consumer, Scanner scanner) {
        try {
            System.out.println("DODAVANJE KATEGORIJE...");
            System.out.println("Unesite naziv kategorije:");
            String naziv=scanner.nextLine();
            naziv=naziv.replaceAll(" ", "%20"); //svi razmaci ce biti + u URL adresi
            System.out.println("Unesite id audio snimka:");
            int idAudio=scanner.nextInt();

            URL url=new URL(url2+"/dodavanjeKategorijeSnimku/"+naziv+"/"+idAudio);
            HttpURLConnection konekcija=(HttpURLConnection)url.openConnection();
            konekcija.setRequestMethod("POST");
            konekcija.setDoOutput(true);
            konekcija.setDoInput(true);
            int code=konekcija.getResponseCode();
            System.out.println("Response code: "+code);

            Message msg=consumer.receive();
            TextMessage objMsg=(TextMessage)msg;
            System.out.println(objMsg.getStringProperty("ishod"));
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void brisanjeAudioSnimka(JMSConsumer consumer, Scanner scanner) {
        try {
            System.out.println("BRISANJE AUDIO SNIMKA...");
            System.out.println("Unesite id audio snimka:");
            int idAudio=scanner.nextInt();
            System.out.println("Unesite id korisnika:");
            int idKorisnika=scanner.nextInt();

            URL url=new URL(url2+"/brisanjeAudio/"+idAudio+"/"+idKorisnika);
            HttpURLConnection konekcija=(HttpURLConnection)url.openConnection();
            konekcija.setRequestMethod("DELETE");
            konekcija.setDoOutput(true);
            konekcija.setDoInput(true);
            int code=konekcija.getResponseCode();
            System.out.println("Response code: "+code);

            Message msg=consumer.receive();
            TextMessage objMsg=(TextMessage)msg;
            System.out.println(objMsg.getStringProperty("ishod"));
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void dohvSveKategorije(JMSConsumer consumer, Scanner scanner) {
        try {
            System.out.println("DOHVATANJE SVIH KATEGORIJA...");
            URL url=new URL(url2+"/dohvatanjeKategorija");
            HttpURLConnection konekcija=(HttpURLConnection)url.openConnection();
            konekcija.setRequestMethod("GET");
            konekcija.setDoOutput(true);
            int code=konekcija.getResponseCode();
            System.out.println("Response code: "+code);

            Message msg=consumer.receive();
            ObjectMessage objMsg=(ObjectMessage)msg;
            List<Kategorija> objectList=(List<Kategorija>)objMsg.getObject();
            System.out.println("Sve kategorije:\n");
            for(Kategorija obj:objectList){
                System.out.println(obj.getIdKategorije()+": "+obj.getNaziv());
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void dohvSveAudioSnimke(JMSConsumer consumer, Scanner scanner) {
         try {
            System.out.println("DOHVATANJE SVIH AUDIO SNIMAKA...");
            URL url=new URL(url2+"/dohvatanjeAudioSnimaka");
            HttpURLConnection konekcija=(HttpURLConnection)url.openConnection();
            konekcija.setRequestMethod("GET");
            konekcija.setDoOutput(true);
            int code=konekcija.getResponseCode();
            System.out.println("Response code: "+code);

            Message msg=consumer.receive();
            ObjectMessage objMsg=(ObjectMessage)msg;
            List<Audio> objectList=(List<Audio>)objMsg.getObject();
            System.out.println("Svi audio snimci:\n");
            for(Audio obj:objectList){
                System.out.println(obj.getIdAudio()+": "+obj.getNaziv()+" | "+obj.getTrajanje()/60+":"+obj.getTrajanje()%60);
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void dohvKategorijaZaAudio(JMSConsumer consumer, Scanner scanner) {
        try {
            System.out.println("DOHVATANJE KATEGORIJA ZA AUDIO SNIMAK...");
            System.out.println("Unesite id audio snimka:");
            int idAudio=scanner.nextInt();
            URL url=new URL(url2+"/dohvatanjeKatZaAudio/"+idAudio);
            HttpURLConnection konekcija=(HttpURLConnection)url.openConnection();
            konekcija.setRequestMethod("GET");
            konekcija.setDoOutput(true);
            int code=konekcija.getResponseCode();
            System.out.println("Response code: "+code);

            Message msg=consumer.receive();
            ObjectMessage objMsg=(ObjectMessage)msg;
            List<Kategorija> objectList=null;
            objectList=(List<Kategorija>)objMsg.getObject();
            System.out.println("Sve kategorije audio snimka:\n");
            for(Kategorija obj:objectList){
                System.out.println(obj.getNaziv());
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void kreiranjePaketa(JMSConsumer consumer, Scanner scanner) {
         try {
            System.out.println("KREIRANJE PAKETA...");
            System.out.println("Unesite cenu paketa:");
            int cena=scanner.nextInt();

            URL url=new URL(url3+"/kreiranjePaketa/"+cena);
            HttpURLConnection konekcija=(HttpURLConnection)url.openConnection();
            konekcija.setRequestMethod("POST");
            konekcija.setDoOutput(true);
            konekcija.setDoInput(true);
            int code=konekcija.getResponseCode();
            System.out.println("Response code: "+code);

            Message msg=consumer.receive();
            TextMessage objMsg=(TextMessage)msg;
            System.out.println(objMsg.getStringProperty("ishod"));
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void promenaCeneZaPaket(JMSConsumer consumer, Scanner scanner) {
        try {
            System.out.println("PROMENA CENE PAKETA...");
            System.out.println("Unesite id paketa:");
            int idPaketa=scanner.nextInt();
            System.out.println("Unesite novu cenu paketa:");
            int cena=scanner.nextInt();
            
            URL url=new URL(url3+"/promenaCenePaketa/"+idPaketa+"/"+cena);
            HttpURLConnection konekcija=(HttpURLConnection)url.openConnection();
            konekcija.setRequestMethod("POST");
            konekcija.setDoOutput(true);
            konekcija.setDoInput(true);
            int code=konekcija.getResponseCode();
            System.out.println("Response code: "+code);

            Message msg=consumer.receive();
            TextMessage objMsg=(TextMessage)msg;
            System.out.println(objMsg.getStringProperty("ishod"));
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void kreiranjePretplate(JMSConsumer consumer, Scanner scanner) {
        try {
            System.out.println("KREIRANJE PRETPLATE NA PAKET...");
            System.out.println("Unesite id korisnika:");
            int idKorisnika=scanner.nextInt();
            System.out.println("Unesite id paketa:");
            int idPaketa=scanner.nextInt();
            scanner.nextLine();
            System.out.println("Unesite datum pocetka pretplate (format: yyyy-MM-dd hh:mm:ss):");
            String datumVreme=scanner.nextLine();
            datumVreme=datumVreme.replace(" ", "%20");

            URL url=new URL(url3+"/kreiranjePretplate/"+idKorisnika+"/"+idPaketa+"/"+datumVreme);
            HttpURLConnection konekcija=(HttpURLConnection)url.openConnection();
            konekcija.setRequestMethod("POST");
            konekcija.setDoOutput(true);
            konekcija.setDoInput(true);
            int code=konekcija.getResponseCode();
            System.out.println("Response code: "+code);

            Message msg=consumer.receive();
            TextMessage objMsg=(TextMessage)msg;
            System.out.println(objMsg.getStringProperty("ishod"));
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void dohvatanjePaketa(JMSConsumer consumer, Scanner scanner) {
        try {
            System.out.println("DOHVATANJE SVIH PAKETA...");
            URL url=new URL(url3+"/dohvatanjePaketa");
            HttpURLConnection konekcija=(HttpURLConnection)url.openConnection();
            konekcija.setRequestMethod("GET");
            konekcija.setDoOutput(true);
            int code=konekcija.getResponseCode();
            System.out.println("Response code: "+code);

            Message msg=consumer.receive();
            ObjectMessage objMsg=(ObjectMessage)msg;
            List<Paket> objectList=(List<Paket>)objMsg.getObject();
            System.out.println("Svi paketi:\n");
            for(int i=1;i<=objectList.size();i++){
                System.out.println("Paket "+objectList.get(i-1).getIdPaketa()+": "+objectList.get(i-1).getVazecaCena());
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void dohvatanjePretplataZaKorisnika(JMSConsumer consumer, Scanner scanner) {
        try {
            System.out.println("DOHVATANJE PRETPLATE ZA KORISNIKA...");
            System.out.println("Unesite id korisnika:");
            int idKorisnika=scanner.nextInt();
            URL url=new URL(url3+"/dohvatanjePretplataZaKorisnika/"+idKorisnika);
            HttpURLConnection konekcija=(HttpURLConnection)url.openConnection();
            konekcija.setRequestMethod("GET");
            konekcija.setDoOutput(true);
            int code=konekcija.getResponseCode();
            System.out.println("Response code: "+code);

            Message msg=consumer.receive();
            ObjectMessage objMsg=(ObjectMessage)msg;
            List<Pretplata> objectList=(List<Pretplata>)objMsg.getObject();
            System.out.println("Sve pretplate korisnika:\n");
            for(Pretplata obj:objectList){
                System.out.println("Pretplata "+obj.getIdPaketa().getIdPaketa()+" | "+obj.getDatumVremePocetka()+" | Cena:"+obj.getCena());
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void kreiranjeSlusanja(JMSConsumer consumer, Scanner scanner) {
        try {
            System.out.println("KREIRANJE SLUSANJA...");
            System.out.println("Unesite id korisnika:");
            int idKorisnika=scanner.nextInt();
            System.out.println("Unesite id audio snimka:");
            int idAudio=scanner.nextInt();
            scanner.nextLine();
            System.out.println("Unesite datum kada je zapoceto slusanje (format: yyyy-MM-dd hh:mm:ss):");
            String datumVreme=scanner.nextLine();
            datumVreme=datumVreme.replace(" ", "%20");
            System.out.println("Unesite sekunde kada je zapocetno slusanje:");
            int sekundZapoceto=scanner.nextInt();
            System.out.println("Unesite koliko je sekundi snimka odslusano:");
            int sekundOdslusano=scanner.nextInt();

            URL url=new URL(url3+"/kreiranjeSlusanja/"+idKorisnika+"/"+idAudio+"/"+datumVreme+"/"+sekundZapoceto+"/"+sekundOdslusano);
            HttpURLConnection konekcija=(HttpURLConnection)url.openConnection();
            konekcija.setRequestMethod("POST");
            konekcija.setDoOutput(true);
            konekcija.setDoInput(true);
            int code=konekcija.getResponseCode();
            System.out.println("Response code: "+code);

            Message msg=consumer.receive();
            TextMessage objMsg=(TextMessage)msg;
            System.out.println(objMsg.getStringProperty("ishod"));
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void dohvatanjeSlusanjaAudioSnimka(JMSConsumer consumer, Scanner scanner) {
        try {
            System.out.println("DOHVATANJE SVIH SLUSANJA AUDIO SNIMKA...");
            System.out.println("Unesite id audio snimka:");
            int idAudio=scanner.nextInt();
            URL url=new URL(url3+"/dohvatanjeSlusanja/"+idAudio);
            HttpURLConnection konekcija=(HttpURLConnection)url.openConnection();
            konekcija.setRequestMethod("GET");
            konekcija.setDoOutput(true);
            int code=konekcija.getResponseCode();
            System.out.println("Response code: "+code);

            Message msg=consumer.receive();
            ObjectMessage objMsg=(ObjectMessage)msg;
            List<Slusa> objectList=(List<Slusa>)objMsg.getObject();
            System.out.println("Sva slusanja audio snimka:\n");
            for(Slusa obj:objectList){
                System.out.println(obj.getIdKorisnika().getIme()+" | "+obj.getDatumVremePocetka());
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void dodavanjeUOmiljene(JMSConsumer consumer, Scanner scanner) {
        try {
            System.out.println("DODAVANJE AUDIO SNIMKA U OMILJENE...");
            System.out.println("Unesite id audio snimka:");
            int idAudio=scanner.nextInt();
            System.out.println("Unesite id korisnika:");
            int idKorisnika=scanner.nextInt();
            URL url=new URL(url3+"/dodavanjeUOmiljene/"+idAudio+"/"+idKorisnika);
            HttpURLConnection konekcija=(HttpURLConnection)url.openConnection();
            konekcija.setRequestMethod("POST");
            konekcija.setDoOutput(true);
            int code=konekcija.getResponseCode();
            System.out.println("Response code: "+code);

            Message msg=consumer.receive();
            TextMessage objMsg=(TextMessage)msg;
            System.out.println(objMsg.getStringProperty("ishod"));
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void dohvatanjeOmiljenih(JMSConsumer consumer, Scanner scanner) {
        try {
            System.out.println("DOHVATANJE OMILJENIH AUDIO SNIMAKA ZA KORISNIKA...");
            System.out.println("Unesite id korisnika:");
            int idKorisnika=scanner.nextInt();
            URL url=new URL(url3+"/dohvatanjeOmiljenihZaKorisnika/"+idKorisnika);
            HttpURLConnection konekcija=(HttpURLConnection)url.openConnection();
            konekcija.setRequestMethod("GET");
            konekcija.setDoOutput(true);
            int code=konekcija.getResponseCode();
            System.out.println("Response code: "+code);

            Message msg=consumer.receive();
            ObjectMessage objMsg=(ObjectMessage)msg;
            List<Audio> objectList=(List<Audio>)objMsg.getObject();
            System.out.println("Omiljeni audio snimci korisnika:\n");
            for(Audio obj:objectList){
                System.out.println(obj.getNaziv());
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void ocenjivanjeAudioSnimka(JMSConsumer consumer, Scanner scanner) {
        try {
            System.out.println("OCENJIVANJE AUDIO SNIMKA...");
            System.out.println("Unesite id korisnika:");
            int idKorisnika=scanner.nextInt();
            System.out.println("Unesite id audio snimka:");
            int idAudio=scanner.nextInt();
            System.out.println("Unesite ocenu audio snimka:");
            int ocena=scanner.nextInt();
            scanner.nextLine();
            System.out.println("Unesite datum kada je snimak ocenjen (format: yyyy-MM-dd hh:mm:ss):");
            String datumVreme=scanner.nextLine();
            datumVreme=datumVreme.replace(" ", "%20");

            URL url=new URL(url3+"/ocenjivanjeAudioSnimka/"+idKorisnika+"/"+idAudio+"/"+datumVreme+"/"+ocena);
            HttpURLConnection konekcija=(HttpURLConnection)url.openConnection();
            konekcija.setRequestMethod("POST");
            konekcija.setDoOutput(true);
            konekcija.setDoInput(true);
            int code=konekcija.getResponseCode();
            System.out.println("Response code: "+code);

            Message msg=consumer.receive();
            TextMessage objMsg=(TextMessage)msg;
            System.out.println(objMsg.getStringProperty("ishod"));
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void dohvatanjeOcenaAudioSnimka(JMSConsumer consumer, Scanner scanner) {
        try {
            System.out.println("DOHVATANJE OCENA AUDIO SNIMKA...");
            System.out.println("Unesite id audio snimka:");
            int idAudio=scanner.nextInt();
            URL url=new URL(url3+"/dohvatanjeOcena/"+idAudio);
            HttpURLConnection konekcija=(HttpURLConnection)url.openConnection();
            konekcija.setRequestMethod("GET");
            konekcija.setDoOutput(true);
            int code=konekcija.getResponseCode();
            System.out.println("Response code: "+code);

            Message msg=consumer.receive();
            ObjectMessage objMsg=(ObjectMessage)msg;
            List<Ocena> objectList=(List<Ocena>)objMsg.getObject();
            System.out.println("Sve ocene audio snimka:\n");
            for(Ocena obj:objectList){
                System.out.println(obj.getKorisnik().getIme()+": "+obj.getOcena());
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void menjanjeOcene(JMSConsumer consumer, Scanner scanner) {
        try {
            System.out.println("MENJANJE OCENE AUDIO SNIMKA...");
            System.out.println("Unesite id korisnika:");
            int idKorisnika=scanner.nextInt();
            System.out.println("Unesite id audio snimka:");
            int idAudio=scanner.nextInt();
            System.out.println("Unesite ocenu audio snimka:");
            int ocena=scanner.nextInt();
            scanner.nextLine();

            URL url=new URL(url3+"/menjanjeOcene/"+idKorisnika+"/"+idAudio+"/"+ocena);
            HttpURLConnection konekcija=(HttpURLConnection)url.openConnection();
            konekcija.setRequestMethod("POST");
            konekcija.setDoOutput(true);
            konekcija.setDoInput(true);
            int code=konekcija.getResponseCode();
            System.out.println("Response code: "+code);

            Message msg=consumer.receive();
            TextMessage objMsg=(TextMessage)msg;
            System.out.println(objMsg.getStringProperty("ishod"));
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void brisanjeOcene(JMSConsumer consumer, Scanner scanner) {
        try {
            System.out.println("BRISANJE OCENE AUDIO SNIMKA...");
            System.out.println("Unesite id audio snimka:");
            int idAudio=scanner.nextInt();
            System.out.println("Unesite id korisnika:");
            int idKorisnika=scanner.nextInt();

            URL url=new URL(url3+"/brisanjeOcene/"+idAudio+"/"+idKorisnika);
            HttpURLConnection konekcija=(HttpURLConnection)url.openConnection();
            konekcija.setRequestMethod("DELETE");
            konekcija.setDoOutput(true);
            konekcija.setDoInput(true);
            int code=konekcija.getResponseCode();
            System.out.println("Response code: "+code);

            Message msg=consumer.receive();
            TextMessage objMsg=(TextMessage)msg;
            System.out.println(objMsg.getStringProperty("ishod"));
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
