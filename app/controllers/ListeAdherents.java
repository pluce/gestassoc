/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.Date;
import java.util.List;
import models.Adherent;
import models.Annee;
import models.Compte;
import models.Mouvement;
import models.ParametreAssoc;
import models.TypeCompte;
import play.db.jpa.GenericModel;
import play.modules.pdf.PDF;
import play.mvc.Controller;
import services.AssociationService;

/**
 *
 * @author Pluce
 */
public class ListeAdherents extends Controller {
    public static void etatAdherents(){
        List<Adherent> listeAdherents = Adherent.findAll();
        List<Adherent> listeRetards = Adherent.enRetardCotisation();
        render(listeAdherents,listeRetards);
    }
    
    public static void etatRetards(){
        List<Adherent> listeRetards = Adherent.enRetardCotisation();
        List<Adherent> listeAdherents = listeRetards;
        render("ListeAdherents/etatAdherents.html",listeAdherents,listeRetards);        
    }
    
    public static void supprimerAdherent(Long id){
        Adherent adh = Adherent.findById(id);
        adh.delete();
        etatAdherents();
    }
    
    public static void newAdherent(
            String nom,
            String prenom,
            Date date,
            String email,
            String telephone,
            String adresse,
            String codePostal,
            String ville
            ){
        Adherent adh = new Adherent();
        adh.nom = nom;
        adh.prenom = prenom;
        adh.date = date;
        adh.email = email;
        adh.telephone = telephone;
        adh.adresse = adresse;
        adh.codePostal = codePostal;
        adh.ville = ville;
        adh.save();
        etatAdherents();
    }
    
    public static void editAdherent(
            String nom,
            String prenom,
            Date date,
            String email,
            String telephone,
            String adresse,
            String codePostal,
            String ville,
            Long id
            ){
        Adherent adh = Adherent.findById(id);
        adh.nom = nom;
        adh.prenom = prenom;
        adh.date = date;
        adh.email = email;
        adh.telephone = telephone;
        adh.adresse = adresse;
        adh.codePostal = codePostal;
        adh.ville = ville;
        adh.save();
        etatAdherents();
    }
    
    public static void ficheEditAdherent(Long id){
        Adherent adh = Adherent.findById(id);
        render(adh);
    }
    
    public static void nouvelleCotisation(Long id){
        Adherent adh = Adherent.findById(id);
        List<Annee> allAnnee = Annee.find("annee >= ? AND cloture != true ORDER BY annee ASC",adh.date.getYear()).fetch();
        
        List<Compte> comptes = Compte.find("type != ?",TypeCompte.TIERS).fetch();
        render(adh,allAnnee,comptes);
    }
    public static void enregistrerCotisation(Long id,Long annee,Long compte,Double montant,String numCheque){
        Adherent adh = Adherent.findById(id);
        Annee an = Annee.findById(annee);
        Compte cred = Compte.findById(compte);
        Compte debit = AssociationService.assocCourante().compteTiersParDefaut;
        Mouvement.ecrireMouvement(debit, cred, montant, "Cotisation "+adh.prenom+" "+adh.nom+" "+an.annee, new Date(),numCheque,null);
        adh.cotisations.add(an);
        adh.save();
        etatAdherents();
    }
    
    public static void exportCSV(){
       List<Adherent> la = Adherent.findAll();
       StringBuilder sb = new StringBuilder();
       sb.append("\uFEFF");
       sb.append("id;nom;prenom;adresse;code postal;ville;telephone;email;date adhesion");
       for(Adherent a: la){
           sb.append("\n");
           sb.append(a.id);
           sb.append(";");
           sb.append(a.nom);
           sb.append(";");
           sb.append(a.prenom);
           sb.append(";");
           sb.append(a.adresse);
           sb.append(";");
           sb.append(a.codePostal);
           sb.append(";");
           sb.append(a.ville);
           sb.append(";");
           sb.append(a.telephone);
           sb.append(";");
           sb.append(a.email);
           sb.append(";");
           sb.append(a.date);
       }
       
       
       response.setHeader("Content-Disposition", "attachment; filename=adherents.csv");
       response.setHeader("Content-Type", "text/csv; charset=utf-8");
       renderText(sb.toString());
    }
    
    public static void mailingPDF(){
        List<Adherent> la = Adherent.findAll();
        PDF.MultiPDFDocuments mul = new PDF.MultiPDFDocuments("Mailing.pdf");
        
        for(Adherent dest: la){            
            PDF.PDFDocument doc = new PDF.PDFDocument("mailing.html", null, dest);
            mul.add(doc);
        }
        PDF.renderPDF(mul);
    }
    
}
