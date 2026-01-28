package entiteti;

import entiteti.Audio;
import entiteti.Korisnik;
import entiteti.OcenaPK;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-08-15T21:15:53")
@StaticMetamodel(Ocena.class)
public class Ocena_ { 

    public static volatile SingularAttribute<Ocena, OcenaPK> ocenaPK;
    public static volatile SingularAttribute<Ocena, Audio> audio;
    public static volatile SingularAttribute<Ocena, Date> datumVremeDavanja;
    public static volatile SingularAttribute<Ocena, Integer> ocena;
    public static volatile SingularAttribute<Ocena, Korisnik> korisnik;

}