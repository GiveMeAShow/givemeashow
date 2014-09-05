/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package giveme.common.beans;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author marion
 */
@Entity
@Table(name = "lang_iso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LangIso.findAll", query = "SELECT l FROM LangIso l"),
    @NamedQuery(name = "LangIso.findByLangIso", query = "SELECT l FROM LangIso l WHERE l.langIso = :langIso"),
    @NamedQuery(name = "LangIso.findByLangName", query = "SELECT l FROM LangIso l WHERE l.langName = :langName"),
    @NamedQuery(name = "LangIso.findByLangFlagUrl", query = "SELECT l FROM LangIso l WHERE l.langFlagUrl = :langFlagUrl")})
public class LangIso implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subDefaultLang")
    private Collection<User> userCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "defaultLang")
    private Collection<User> userCollection1;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "lang_iso")
    private String langIso;
    @Basic(optional = false)
    @Column(name = "lang_name")
    private String langName;
    @Column(name = "lang_flag_url")
    private String langFlagUrl;
    

    public LangIso() {
    }

    public LangIso(String langIso) {
        this.langIso = langIso;
    }

    public LangIso(String langIso, String langName) {
        this.langIso = langIso;
        this.langName = langName;
    }

    public String getLangIso() {
        return langIso;
    }

    public void setLangIso(String langIso) {
        this.langIso = langIso;
    }

    public String getLangName() {
        return langName;
    }

    public void setLangName(String langName) {
        this.langName = langName;
    }

    public String getLangFlagUrl() {
        return langFlagUrl;
    }

    public void setLangFlagUrl(String langFlagUrl) {
        this.langFlagUrl = langFlagUrl;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<User> getUserCollection1() {
        return userCollection1;
    }

    public void setUserCollection1(Collection<User> userCollection1) {
        this.userCollection1 = userCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (langIso != null ? langIso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LangIso)) {
            return false;
        }
        LangIso other = (LangIso) object;
        if ((this.langIso == null && other.langIso != null) || (this.langIso != null && !this.langIso.equals(other.langIso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "giveme.common.beans.LangIso[ langIso=" + langIso + " ]";
    }    
}
