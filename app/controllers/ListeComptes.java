/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.Date;
import java.util.List;
import javax.persistence.TypedQuery;
import models.Adherent;
import models.Annee;
import models.Compte;
import models.Mouvement;
import models.TypeCompte;
import play.db.jpa.GenericModel;
import play.db.jpa.JPA;
import play.mvc.Controller;

/**
 *
 * @author Pluce
 */
public class ListeComptes extends Controller{
    public static void etatCompte(Long compteId){
        List<Compte> comptes = Compte.findAll();
        Compte cpt = Compte.findById(compteId);
        render(cpt,comptes);
    }
    public static void newDebit(Long vers,Long depuis, Date date, Double montant, String libelle, String numCheque,String numJustificatif){
        Mouvement mv = Mouvement.ecrireMouvement((Compte)Compte.findById(depuis), (Compte)Compte.findById(vers), montant, libelle, date,numCheque,numJustificatif);
        
        
        etatCompte(depuis);
    }
    public static void newCredit(Long vers,Long depuis, Date date, Double montant, String libelle, String numCheque, String numJustificatif){
        Mouvement mv = Mouvement.ecrireMouvement((Compte)Compte.findById(depuis), (Compte)Compte.findById(vers), montant, libelle, date,numCheque,numJustificatif);
        etatCompte(vers);
    }
    public static void supprimerMouvement(Long id,Long redirect){
        Mouvement mv = Mouvement.findById(id);
        
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
        etatCompte(redirect);
    }
    
    public static void gestionComptes(){
        
        List<Compte> comptes = Compte.findAll();
        
        render(comptes);
    }
    
    public static void newCompte(String libelle, TypeCompte type){
        Compte n = new Compte();
        n.libelle = libelle;
        n.type = type;
        n.save();
        gestionComptes();
    }
}
