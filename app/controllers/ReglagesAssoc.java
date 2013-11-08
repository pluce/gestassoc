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
    
    public static void enregistrer(String nom, Long compte, 
            String email,
            String telephone,
            String adresse,
            String codePostal,
            String ville){
        ParametreAssoc actuel = AssociationService.assocCourante();
        actuel.nom = nom;
        actuel.compteTiersParDefaut = Compte.findById(compte);
        actuel.email = email;
        actuel.telephone = telephone;
        actuel.adresse = adresse;
        actuel.codePostal = codePostal;
        actuel.ville = ville;
        actuel.save();
        index();
    }
}
