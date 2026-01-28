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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Teodora Lazarevic
 */
@Entity
@Table(name = "paket")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Paket.findAll", query = "SELECT p FROM Paket p"),
    @NamedQuery(name = "Paket.findByIdPaketa", query = "SELECT p FROM Paket p WHERE p.idPaketa = :idPaketa"),
    @NamedQuery(name = "Paket.findByVazecaCena", query = "SELECT p FROM Paket p WHERE p.vazecaCena = :vazecaCena")})
public class Paket implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_paketa")
    private Integer idPaketa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vazeca_cena")
    private int vazecaCena;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPaketa")
    private List<Pretplata> pretplataList;

    public Paket() {
    }

    public Paket(Integer idPaketa) {
        this.idPaketa = idPaketa;
    }

    public Paket(Integer idPaketa, int vazecaCena) {
        this.idPaketa = idPaketa;
        this.vazecaCena = vazecaCena;
    }

    public Integer getIdPaketa() {
        return idPaketa;
    }

    public void setIdPaketa(Integer idPaketa) {
        this.idPaketa = idPaketa;
    }

    public int getVazecaCena() {
        return vazecaCena;
    }

    public void setVazecaCena(int vazecaCena) {
        this.vazecaCena = vazecaCena;
    }

    @XmlTransient
    public List<Pretplata> getPretplataList() {
        return pretplataList;
    }

    public void setPretplataList(List<Pretplata> pretplataList) {
        this.pretplataList = pretplataList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPaketa != null ? idPaketa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paket)) {
            return false;
        }
        Paket other = (Paket) object;
        if ((this.idPaketa == null && other.idPaketa != null) || (this.idPaketa != null && !this.idPaketa.equals(other.idPaketa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Paket[ idPaketa=" + idPaketa + " ]";
    }
    
}
