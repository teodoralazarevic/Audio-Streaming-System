package entiteti;

import entiteti.Korisnik;
import entiteti.Paket;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-08-15T21:15:53")
@StaticMetamodel(Pretplata.class)
public class Pretplata_ { 

    public static volatile SingularAttribute<Pretplata, Integer> idPretplate;
    public static volatile SingularAttribute<Pretplata, Paket> idPaketa;
    public static volatile SingularAttribute<Pretplata, Korisnik> idKorisnika;
    public static volatile SingularAttribute<Pretplata, Integer> cena;
    public static volatile SingularAttribute<Pretplata, Date> datumVremePocetka;

}