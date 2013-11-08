/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import play.db.jpa.Model;

/**
 *
 * @author Pluce
 */
@Entity
public class ParametreAssoc extends Model{
    /*@Transient
    public static final Long idActuel = 1L;*/
    
    @OneToOne
    public Annee anneeEnCours;
    
    public String nom;
    public String adresse;
    public String codePostal;
    public String ville;
    public String telephone;
    public String email;
    
    
    
    @OneToOne
    public Compte compteTiersParDefaut;
}

