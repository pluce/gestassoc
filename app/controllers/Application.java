package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;
import play.test.Fixtures;

public class Application extends Controller {

    public static void index() {
        Mouvement m1 = Mouvement.findById(1L);
        Mouvement m2 = Mouvement.findById(2L);
        Mouvement m3 = Mouvement.findById(3L);
        
        Compte c1 = Compte.findById(1L);
        Compte c2 = Compte.findById(2L);
        
        //c1.archiver();
        
        
        
        Compte c3 = Compte.findById(3L);
        /*c1.type = TypeCompte.TIERS;
        c2.type = TypeCompte.ESPECE;
        c3.type = TypeCompte.BANQUE;
        //ParametreAssoc assoc = ParametreAssoc.findById(1L);
        //System.out.println(assoc);
        /*
        ParametreAssoc pa = new ParametreAssoc();
        pa.anneeEnCours = Annee.findById(1L);
        pa.nom = "Vivre à Tarnac";
        pa.save();*/
        /*
        c1.mouvements.add(m1);
        c1.mouvements.add(m2);
        c2.mouvements.add(m1);
        c2.mouvements.add(m2);
        c2.mouvements.add(m3);
        c3.mouvements.add(m3);
        
        m1.save();
        m2.save();
        m3.save();
        c1.save();
        c2.save();
        c3.save();
        */
        List<Compte> comptes = Compte.findAll();
        for(Compte c: comptes){
            c.consoliderSolde();
        }
        List<Adherent> adherents = Adherent.findAll();
        List<Adherent> retards = Adherent.enRetardCotisation();
        
        
        //Fixtures.deleteDatabase();
        render(comptes,adherents,retards);
    }

}