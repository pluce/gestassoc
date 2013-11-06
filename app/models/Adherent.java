/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import play.db.jpa.JPA;
import play.db.jpa.Model;
import services.AssociationService;

/**
 *
 * @author Pluce
 */
@Entity
public class Adherent extends Model{
    public String nom;
    public String prenom;
    public String adresse;
    public String codePostal;
    public String ville;
    
    public String telephone;
    public String email;
    
    @Temporal(TemporalType.DATE)
    public Date date;
    
    
    @ManyToMany
    public Set<Annee> cotisations = new HashSet<Annee>();
    
    
    @Override
    public String toString(){
        return nom.toString().toUpperCase() + " " + prenom.toString();
    }
    
    public static List<Adherent> enRetardCotisation(){
        ParametreAssoc pa = AssociationService.assocCourante();
        TypedQuery<Adherent> tq = JPA.em().createQuery("SELECT a FROM Adherent a WHERE ? NOT MEMBER OF a.cotisations", Adherent.class);
        tq.setParameter(1, pa.anneeEnCours);
        List<Adherent> la = tq.getResultList();//Adherent.find("? NOT MEMBER OF cotisations", pa.anneeEnCours).fetch();
        return la;
    }
}
