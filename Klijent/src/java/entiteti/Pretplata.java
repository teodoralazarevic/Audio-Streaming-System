/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Teodora Lazarevic
 */
@Entity
@Table(name = "pretplata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pretplata.findAll", query = "SELECT p FROM Pretplata p"),
    @NamedQuery(name = "Pretplata.findByIdPretplate", query = "SELECT p FROM Pretplata p WHERE p.idPretplate = :idPretplate"),
    @NamedQuery(name = "Pretplata.findByDatumVremePocetka", query = "SELECT p FROM Pretplata p WHERE p.datumVremePocetka = :datumVremePocetka"),
    @NamedQuery(name = "Pretplata.findByCena", query = "SELECT p FROM Pretplata p WHERE p.cena = :cena")})
public class Pretplata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pretplate")
    private Integer idPretplate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datum_vreme_pocetka")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVremePocetka;
    @Column(name = "cena")
    private Integer cena;
    @JoinColumn(name = "id_korisnika", referencedColumnName = "id_korisnika")
    @ManyToOne(optional = false)
    private Korisnik idKorisnika;
    @JoinColumn(name = "id_paketa", referencedColumnName = "id_paketa")
    @ManyToOne(optional = false)
    private Paket idPaketa;

    public Pretplata() {
    }

    public Pretplata(Integer idPretplate) {
        this.idPretplate = idPretplate;
    }

    public Pretplata(Integer idPretplate, Date datumVremePocetka) {
        this.idPretplate = idPretplate;
        this.datumVremePocetka = datumVremePocetka;
    }

    public Integer getIdPretplate() {
        return idPretplate;
    }

    public void setIdPretplate(Integer idPretplate) {
        this.idPretplate = idPretplate;
    }

    public Date getDatumVremePocetka() {
        return datumVremePocetka;
    }

    public void setDatumVremePocetka(Date datumVremePocetka) {
        this.datumVremePocetka = datumVremePocetka;
    }

    public Integer getCena() {
        return cena;
    }

    public void setCena(Integer cena) {
        this.cena = cena;
    }

    public Korisnik getIdKorisnika() {
        return idKorisnika;
    }

    public void setIdKorisnika(Korisnik idKorisnika) {
        this.idKorisnika = idKorisnika;
    }

    public Paket getIdPaketa() {
        return idPaketa;
    }

    public void setIdPaketa(Paket idPaketa) {
        this.idPaketa = idPaketa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPretplate != null ? idPretplate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pretplata)) {
            return false;
        }
        Pretplata other = (Pretplata) object;
        if ((this.idPretplate == null && other.idPretplate != null) || (this.idPretplate != null && !this.idPretplate.equals(other.idPretplate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Pretplata[ idPretplate=" + idPretplate + " ]";
    }
    
}
