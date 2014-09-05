/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package giveme.common.beans;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author marion
 */
@Entity
@Table(name = "User")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
    @NamedQuery(name = "User.findByLogin", query = "SELECT u FROM User u WHERE u.login = :login"),
    @NamedQuery(name = "User.findByIsAdmin", query = "SELECT u FROM User u WHERE u.isAdmin = :isAdmin"),
    @NamedQuery(name = "User.findByInviteCode", query = "SELECT u FROM User u WHERE u.inviteCode = :inviteCode"),
    @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
    @NamedQuery(name = "User.findByTimeSpent", query = "SELECT u FROM User u WHERE u.timeSpent = :timeSpent"),
    @NamedQuery(name = "User.findByUseSubtitles", query = "SELECT u FROM User u WHERE u.useSubtitles = :useSubtitles"),
    @NamedQuery(name = "User.findByConfirmed", query = "SELECT u FROM User u WHERE u.confirmed = :confirmed"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email")})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "login")
    private String login;
    @Basic(optional = false)
    @Column(name = "is_admin")
    private boolean isAdmin;
    @Basic(optional = false)
    @Column(name = "invite_code")
    private String inviteCode;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "time_spent")
    private long timeSpent;
    @Basic(optional = false)
    @Column(name = "use_subtitles")
    private boolean useSubtitles;
    @Basic(optional = false)
    @Column(name = "confirmed")
    private boolean confirmed;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @JoinColumn(name = "sub_default_lang", referencedColumnName = "lang_iso")
    @ManyToOne(optional = false)
    private LangIso subDefaultLang;
    @JoinColumn(name = "default_lang", referencedColumnName = "lang_iso")
    @ManyToOne(optional = false)
    private LangIso defaultLang;

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

    public User(Integer id, String login, boolean isAdmin, String inviteCode, String password, long timeSpent, boolean useSubtitles, boolean confirmed, String email) {
        this.id = id;
        this.login = login;
        this.isAdmin = isAdmin;
        this.inviteCode = inviteCode;
        this.password = password;
        this.timeSpent = timeSpent;
        this.useSubtitles = useSubtitles;
        this.confirmed = confirmed;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(long timeSpent) {
        this.timeSpent = timeSpent;
    }

    public boolean getUseSubtitles() {
        return useSubtitles;
    }

    public void setUseSubtitles(boolean useSubtitles) {
        this.useSubtitles = useSubtitles;
    }

    public boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LangIso getSubDefaultLang() {
        return subDefaultLang;
    }

    public void setSubDefaultLang(LangIso subDefaultLang) {
        this.subDefaultLang = subDefaultLang;
    }

    public LangIso getDefaultLang() {
        return defaultLang;
    }

    public void setDefaultLang(LangIso defaultLang) {
        this.defaultLang = defaultLang;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "giveme.common.beans.User[ id=" + id + " ]";
    }
    
}
