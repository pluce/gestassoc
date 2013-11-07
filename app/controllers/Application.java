package controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import play.*;
import play.mvc.*;

import java.util.*;

import models.*;
import play.test.Fixtures;

public class Application extends Controller {

    public static void index() {
        List<Compte> comptes = Compte.findAll();
        for(Compte c: comptes){
            c.consoliderSolde();
        }
        List<Adherent> adherents = Adherent.findAll();
        List<Adherent> retards = Adherent.enRetardCotisation();
        
        
        //Fixtures.deleteDatabase();
        render(comptes,adherents,retards);
    }
    /*
    public static void backup() throws IOException{
        File fi = File.createTempFile("back", "up");
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fi));
        
    }*/

    
    public static void install() throws IOException{
        Fixtures.deleteAllModels();
        Annee an1 = new Annee();
        an1.cotisation = 0.0d;
        an1.annee = Calendar.getInstance().get(Calendar.YEAR);
        an1.cloture = false;
        an1.save();
        Compte tiers = new Compte();
        tiers.libelle = "Tiers";
        tiers.type = TypeCompte.TIERS;
        tiers.save();
        ParametreAssoc pa = new ParametreAssoc();
        pa.anneeEnCours = an1;
        pa.nom = "Association";
        pa.compteTiersParDefaut = tiers;
        pa.save();
    }
}