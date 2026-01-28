/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Teodora Lazarevic
 */
@Entity
@Table(name = "audio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Audio.findAll", query = "SELECT a FROM Audio a"),
    @NamedQuery(name = "Audio.findByIdAudio", query = "SELECT a FROM Audio a WHERE a.idAudio = :idAudio"),
    @NamedQuery(name = "Audio.findByNaziv", query = "SELECT a FROM Audio a WHERE a.naziv = :naziv"),
    @NamedQuery(name = "Audio.findByTrajanje", query = "SELECT a FROM Audio a WHERE a.trajanje = :trajanje"),
    @NamedQuery(name = "Audio.findByDatumVremePostavljanja", query = "SELECT a FROM Audio a WHERE a.datumVremePostavljanja = :datumVremePostavljanja")})
public class Audio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_audio")
    private Integer idAudio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "naziv")
    private String naziv;
    @Basic(optional = false)
    @NotNull
    @Column(name = "trajanje")
    private int trajanje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datum_vreme_postavljanja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVremePostavljanja;
    @JoinTable(name = "omiljeni", joinColumns = {
        @JoinColumn(name = "id_audio", referencedColumnName = "id_audio")}, inverseJoinColumns = {
        @JoinColumn(name = "id_korisnika", referencedColumnName = "id_korisnika")})
    @ManyToMany
    private List<Korisnik> korisnikList;
    @JoinColumn(name = "id_vlasnika", referencedColumnName = "id_korisnika")
    @ManyToOne(optional = false)
    private Korisnik idVlasnika;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAudio")
    private List<Slusa> slusaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "audio")
    private List<Ocena> ocenaList;

    public Audio() {
    }

    public Audio(Integer idAudio) {
        this.idAudio = idAudio;
    }

    public Audio(Integer idAudio, String naziv, int trajanje, Date datumVremePostavljanja) {
        this.idAudio = idAudio;
        this.naziv = naziv;
        this.trajanje = trajanje;
        this.datumVremePostavljanja = datumVremePostavljanja;
    }

    public Integer getIdAudio() {
        return idAudio;
    }

    public void setIdAudio(Integer idAudio) {
        this.idAudio = idAudio;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    public Date getDatumVremePostavljanja() {
        return datumVremePostavljanja;
    }

    public void setDatumVremePostavljanja(Date datumVremePostavljanja) {
        this.datumVremePostavljanja = datumVremePostavljanja;
    }

    @XmlTransient
    public List<Korisnik> getKorisnikList() {
        return korisnikList;
    }

    public void setKorisnikList(List<Korisnik> korisnikList) {
        this.korisnikList = korisnikList;
    }

    public Korisnik getIdVlasnika() {
        return idVlasnika;
    }

    public void setIdVlasnika(Korisnik idVlasnika) {
        this.idVlasnika = idVlasnika;
    }

    @XmlTransient
    public List<Slusa> getSlusaList() {
        return slusaList;
    }

    public void setSlusaList(List<Slusa> slusaList) {
        this.slusaList = slusaList;
    }

    @XmlTransient
    public List<Ocena> getOcenaList() {
        return ocenaList;
    }

    public void setOcenaList(List<Ocena> ocenaList) {
        this.ocenaList = ocenaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAudio != null ? idAudio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Audio)) {
            return false;
        }
        Audio other = (Audio) object;
        if ((this.idAudio == null && other.idAudio != null) || (this.idAudio != null && !this.idAudio.equals(other.idAudio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Audio[ idAudio=" + idAudio + " ]";
    }
    
}
