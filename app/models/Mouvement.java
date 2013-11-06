/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
public class Mouvement extends Model{
    @Temporal(TemporalType.DATE)
    public Date date;
    
    public String libelle;
    
    @ManyToOne
    public Compte debiteur;
    
    @ManyToOne
    public Compte crediteur;
    
    public Double montant;
    
    public String numCheque;
    
    public String numJustificatif;
    
    public static void supprimerMouvement(Mouvement mv){         
        TypedQuery<Annee> tq = JPA.em().createQuery("SELECT a FROM Annee a WHERE ? MEMBER OF a.mouvements", Annee.class);
        tq.setParameter(1, mv);
        Annee la = tq.getSingleResult();
        
        la.mouvements.remove(mv);
        la.save();
        
        mv.debiteur.mouvements.remove(mv);
        mv.debiteur.archives.remove(mv);
        mv.debiteur.save();
        mv.crediteur.mouvements.remove(mv);
        mv.crediteur.archives.remove(mv);
        mv.crediteur.save();
        mv.delete();
        
        mv.crediteur.consoliderSolde();
        mv.debiteur.consoliderSolde();
    }
    
    public static Mouvement ecrireMouvement(Compte debit, Compte credit, Double montant, String libelle, Date date, String numCheque, String numJustificatif){
        Mouvement mv = new Mouvement();
        mv.debiteur = debit;
        mv.crediteur = credit;
        mv.date = date;
        mv.montant = montant;
        mv.libelle = libelle;
        if(numCheque!=null && !numCheque.isEmpty()){
            mv.numCheque = numCheque;
        }
        if(numJustificatif!=null && !numJustificatif.isEmpty()){
            mv.numJustificatif = numJustificatif;
        }
        mv.save();
        AssociationService.assocCourante().anneeEnCours.mouvements.add(mv);
        AssociationService.assocCourante().anneeEnCours.save();
        mv.debiteur.mouvements.add(mv);
        mv.crediteur.mouvements.add(mv);
        mv.crediteur.consoliderSolde();
        mv.debiteur.consoliderSolde();
        mv.save();
        mv.debiteur.save();
        mv.crediteur.save();
        return mv;
    }
}
