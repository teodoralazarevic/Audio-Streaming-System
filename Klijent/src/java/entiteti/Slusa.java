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
@Table(name = "slusa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Slusa.findAll", query = "SELECT s FROM Slusa s"),
    @NamedQuery(name = "Slusa.findByIdSlusa", query = "SELECT s FROM Slusa s WHERE s.idSlusa = :idSlusa"),
    @NamedQuery(name = "Slusa.findByDatumVremePocetka", query = "SELECT s FROM Slusa s WHERE s.datumVremePocetka = :datumVremePocetka"),
    @NamedQuery(name = "Slusa.findBySekundZapoceto", query = "SELECT s FROM Slusa s WHERE s.sekundZapoceto = :sekundZapoceto"),
    @NamedQuery(name = "Slusa.findBySekundiOdslusano", query = "SELECT s FROM Slusa s WHERE s.sekundiOdslusano = :sekundiOdslusano")})
public class Slusa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_slusa")
    private Integer idSlusa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datum_vreme_pocetka")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVremePocetka;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sekund_zapoceto")
    private int sekundZapoceto;
    @Column(name = "sekundi_odslusano")
    private Integer sekundiOdslusano;
    @JoinColumn(name = "id_audio", referencedColumnName = "id_audio")
    @ManyToOne(optional = false)
    private Audio idAudio;
    @JoinColumn(name = "id_korisnika", referencedColumnName = "id_korisnika")
    @ManyToOne(optional = false)
    private Korisnik idKorisnika;

    public Slusa() {
    }

    public Slusa(Integer idSlusa) {
        this.idSlusa = idSlusa;
    }

    public Slusa(Integer idSlusa, Date datumVremePocetka, int sekundZapoceto) {
        this.idSlusa = idSlusa;
        this.datumVremePocetka = datumVremePocetka;
        this.sekundZapoceto = sekundZapoceto;
    }

    public Integer getIdSlusa() {
        return idSlusa;
    }

    public void setIdSlusa(Integer idSlusa) {
        this.idSlusa = idSlusa;
    }

    public Date getDatumVremePocetka() {
        return datumVremePocetka;
    }

    public void setDatumVremePocetka(Date datumVremePocetka) {
        this.datumVremePocetka = datumVremePocetka;
    }

    public int getSekundZapoceto() {
        return sekundZapoceto;
    }

    public void setSekundZapoceto(int sekundZapoceto) {
        this.sekundZapoceto = sekundZapoceto;
    }

    public Integer getSekundiOdslusano() {
        return sekundiOdslusano;
    }

    public void setSekundiOdslusano(Integer sekundiOdslusano) {
        this.sekundiOdslusano = sekundiOdslusano;
    }

    public Audio getIdAudio() {
        return idAudio;
    }

    public void setIdAudio(Audio idAudio) {
        this.idAudio = idAudio;
    }

    public Korisnik getIdKorisnika() {
        return idKorisnika;
    }

    public void setIdKorisnika(Korisnik idKorisnika) {
        this.idKorisnika = idKorisnika;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSlusa != null ? idSlusa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Slusa)) {
            return false;
        }
        Slusa other = (Slusa) object;
        if ((this.idSlusa == null && other.idSlusa != null) || (this.idSlusa != null && !this.idSlusa.equals(other.idSlusa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Slusa[ idSlusa=" + idSlusa + " ]";
    }
    
}
