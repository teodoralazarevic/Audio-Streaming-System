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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "ocena")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ocena.findAll", query = "SELECT o FROM Ocena o"),
    @NamedQuery(name = "Ocena.findByOcena", query = "SELECT o FROM Ocena o WHERE o.ocena = :ocena"),
    @NamedQuery(name = "Ocena.findByDatumVremeDavanja", query = "SELECT o FROM Ocena o WHERE o.datumVremeDavanja = :datumVremeDavanja"),
    @NamedQuery(name = "Ocena.findByIdKorisnika", query = "SELECT o FROM Ocena o WHERE o.ocenaPK.idKorisnika = :idKorisnika"),
    @NamedQuery(name = "Ocena.findByIdAudio", query = "SELECT o FROM Ocena o WHERE o.ocenaPK.idAudio = :idAudio")})
public class Ocena implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OcenaPK ocenaPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ocena")
    private int ocena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datum_vreme_davanja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVremeDavanja;
    @JoinColumn(name = "id_audio", referencedColumnName = "id_audio", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Audio audio;
    @JoinColumn(name = "id_korisnika", referencedColumnName = "id_korisnika", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Korisnik korisnik;

    public Ocena() {
    }

    public Ocena(OcenaPK ocenaPK) {
        this.ocenaPK = ocenaPK;
    }

    public Ocena(OcenaPK ocenaPK, int ocena, Date datumVremeDavanja) {
        this.ocenaPK = ocenaPK;
        this.ocena = ocena;
        this.datumVremeDavanja = datumVremeDavanja;
    }

    public Ocena(int idKorisnika, int idAudio) {
        this.ocenaPK = new OcenaPK(idKorisnika, idAudio);
    }

    public OcenaPK getOcenaPK() {
        return ocenaPK;
    }

    public void setOcenaPK(OcenaPK ocenaPK) {
        this.ocenaPK = ocenaPK;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    public Date getDatumVremeDavanja() {
        return datumVremeDavanja;
    }

    public void setDatumVremeDavanja(Date datumVremeDavanja) {
        this.datumVremeDavanja = datumVremeDavanja;
    }

    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ocenaPK != null ? ocenaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ocena)) {
            return false;
        }
        Ocena other = (Ocena) object;
        if ((this.ocenaPK == null && other.ocenaPK != null) || (this.ocenaPK != null && !this.ocenaPK.equals(other.ocenaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Ocena[ ocenaPK=" + ocenaPK + " ]";
    }
    
}
