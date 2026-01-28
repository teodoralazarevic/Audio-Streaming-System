package entiteti;

import entiteti.Audio;
import entiteti.Korisnik;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-08-15T21:15:53")
@StaticMetamodel(Slusa.class)
public class Slusa_ { 

    public static volatile SingularAttribute<Slusa, Integer> sekundiOdslusano;
    public static volatile SingularAttribute<Slusa, Audio> idAudio;
    public static volatile SingularAttribute<Slusa, Integer> sekundZapoceto;
    public static volatile SingularAttribute<Slusa, Integer> idSlusa;
    public static volatile SingularAttribute<Slusa, Korisnik> idKorisnika;
    public static volatile SingularAttribute<Slusa, Date> datumVremePocetka;

}