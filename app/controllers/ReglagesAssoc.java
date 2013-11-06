/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.List;
import models.Annee;
import models.Compte;
import models.ParametreAssoc;
import play.db.jpa.GenericModel;
import play.mvc.Controller;
import services.AssociationService;

/**
 *
 * @author Pluce
 */
public class ReglagesAssoc extends Controller {
    public static void index(){
        ParametreAssoc actuel = AssociationService.assocCourante();
        List<Annee> annees = Annee.find("ORDER BY annee DESC").fetch();
        List<Compte> comptes = Compte.findAll();
        render(actuel,annees,comptes);
    }
    
    public static void enregistrer(String nom, Long compte){
        ParametreAssoc actuel = AssociationService.assocCourante();
        actuel.nom = nom;
        actuel.compteTiersParDefaut = Compte.findById(compte);
        actuel.save();
        index();
    }
}
