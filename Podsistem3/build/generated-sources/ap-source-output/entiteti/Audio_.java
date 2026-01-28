package entiteti;

import entiteti.Korisnik;
import entiteti.Ocena;
import entiteti.Slusa;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-08-15T21:15:53")
@StaticMetamodel(Audio.class)
public class Audio_ { 

    public static volatile SingularAttribute<Audio, Integer> idAudio;
    public static volatile ListAttribute<Audio, Slusa> slusaList;
    public static volatile SingularAttribute<Audio, Integer> trajanje;
    public static volatile ListAttribute<Audio, Ocena> ocenaList;
    public static volatile SingularAttribute<Audio, String> naziv;
    public static volatile SingularAttribute<Audio, Korisnik> idVlasnika;
    public static volatile SingularAttribute<Audio, Date> datumVremePostavljanja;
    public static volatile ListAttribute<Audio, Korisnik> korisnikList;

}