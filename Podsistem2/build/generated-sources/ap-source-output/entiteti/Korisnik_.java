package entiteti;

import entiteti.Audio;
import entiteti.Mesto;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-08-15T21:15:52")
@StaticMetamodel(Korisnik.class)
public class Korisnik_ { 

    public static volatile SingularAttribute<Korisnik, String> ime;
    public static volatile SingularAttribute<Korisnik, Mesto> idMesta;
    public static volatile ListAttribute<Korisnik, Audio> audioList1;
    public static volatile ListAttribute<Korisnik, Audio> audioList;
    public static volatile SingularAttribute<Korisnik, Integer> idKorisnika;
    public static volatile SingularAttribute<Korisnik, Integer> godiste;
    public static volatile SingularAttribute<Korisnik, String> pol;
    public static volatile SingularAttribute<Korisnik, String> email;

}