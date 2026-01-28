/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Teodora Lazarevic
 */
@Embeddable
public class OcenaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "id_korisnika")
    private int idKorisnika;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_audio")
    private int idAudio;

    public OcenaPK() {
    }

    public OcenaPK(int idKorisnika, int idAudio) {
        this.idKorisnika = idKorisnika;
        this.idAudio = idAudio;
    }

    public int getIdKorisnika() {
        return idKorisnika;
    }

    public void setIdKorisnika(int idKorisnika) {
        this.idKorisnika = idKorisnika;
    }

    public int getIdAudio() {
        return idAudio;
    }

    public void setIdAudio(int idAudio) {
        this.idAudio = idAudio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idKorisnika;
        hash += (int) idAudio;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OcenaPK)) {
            return false;
        }
        OcenaPK other = (OcenaPK) object;
        if (this.idKorisnika != other.idKorisnika) {
            return false;
        }
        if (this.idAudio != other.idAudio) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.OcenaPK[ idKorisnika=" + idKorisnika + ", idAudio=" + idAudio + " ]";
    }
    
}
