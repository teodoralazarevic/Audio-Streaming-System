/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Teodora Lazarevic
 */
@Entity
@Table(name = "korisnik")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Korisnik.findAll", query = "SELECT k FROM Korisnik k"),
    @NamedQuery(name = "Korisnik.findByIdKorisnika", query = "SELECT k FROM Korisnik k WHERE k.idKorisnika = :idKorisnika"),
    @NamedQuery(name = "Korisnik.findByIme", query = "SELECT k FROM Korisnik k WHERE k.ime = :ime"),
    @NamedQuery(name = "Korisnik.findByEmail", query = "SELECT k FROM Korisnik k WHERE k.email = :email"),
    @NamedQuery(name = "Korisnik.findByGodiste", query = "SELECT k FROM Korisnik k WHERE k.godiste = :godiste"),
    @NamedQuery(name = "Korisnik.findByPol", query = "SELECT k FROM Korisnik k WHERE k.pol = :pol")})
public class Korisnik implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_korisnika")
    private Integer idKorisnika;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "ime")
    private String ime;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 45)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Column(name = "godiste")
    private int godiste;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "pol")
    private String pol;
    @ManyToMany(mappedBy = "korisnikList")
    private List<Audio> audioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVlasnika")
    private List<Audio> audioList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idKorisnika")
    private List<Pretplata> pretplataList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idKorisnika")
    private List<Slusa> slusaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "korisnik")
    private List<Ocena> ocenaList;
    @JoinColumn(name = "id_mesta", referencedColumnName = "id_mesta")
    @ManyToOne(optional = false)
    private Mesto idMesta;

    public Korisnik() {
    }

    public Korisnik(Integer idKorisnika) {
        this.idKorisnika = idKorisnika;
    }

    public Korisnik(Integer idKorisnika, String ime, int godiste, String pol) {
        this.idKorisnika = idKorisnika;
        this.ime = ime;
        this.godiste = godiste;
        this.pol = pol;
    }

    public Integer getIdKorisnika() {
        return idKorisnika;
    }

    public void setIdKorisnika(Integer idKorisnika) {
        this.idKorisnika = idKorisnika;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGodiste() {
        return godiste;
    }

    public void setGodiste(int godiste) {
        this.godiste = godiste;
    }

    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    @XmlTransient
    public List<Audio> getAudioList() {
        return audioList;
    }

    public void setAudioList(List<Audio> audioList) {
        this.audioList = audioList;
    }

    @XmlTransient
    public List<Audio> getAudioList1() {
        return audioList1;
    }

    public void setAudioList1(List<Audio> audioList1) {
        this.audioList1 = audioList1;
    }

    @XmlTransient
    public List<Pretplata> getPretplataList() {
        return pretplataList;
    }

    public void setPretplataList(List<Pretplata> pretplataList) {
        this.pretplataList = pretplataList;
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

    public Mesto getIdMesta() {
        return idMesta;
    }

    public void setIdMesta(Mesto idMesta) {
        this.idMesta = idMesta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKorisnika != null ? idKorisnika.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Korisnik)) {
            return false;
        }
        Korisnik other = (Korisnik) object;
        if ((this.idKorisnika == null && other.idKorisnika != null) || (this.idKorisnika != null && !this.idKorisnika.equals(other.idKorisnika))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Korisnik[ idKorisnika=" + idKorisnika + " ]";
    }
    
}
