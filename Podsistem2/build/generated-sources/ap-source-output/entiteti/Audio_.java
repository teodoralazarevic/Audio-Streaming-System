package entiteti;

import entiteti.Kategorija;
import entiteti.Korisnik;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-08-15T21:15:52")
@StaticMetamodel(Audio.class)
public class Audio_ { 

    public static volatile ListAttribute<Audio, Kategorija> kategorijaList;
    public static volatile SingularAttribute<Audio, Integer> idAudio;
    public static volatile SingularAttribute<Audio, Integer> trajanje;
    public static volatile SingularAttribute<Audio, String> naziv;
    public static volatile SingularAttribute<Audio, Korisnik> idVlasnika;
    public static volatile SingularAttribute<Audio, Date> datumVremePostavljanja;
    public static volatile ListAttribute<Audio, Korisnik> korisnikList;

}