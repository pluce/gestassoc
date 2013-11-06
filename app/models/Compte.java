/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import play.db.jpa.Model;

/**
 *
 * @author Pluce
 */
@Entity
public class Compte extends Model{
    public String libelle;
    
    @ManyToMany
    @OrderBy(value="date asc")
    public List<Mouvement> mouvements = new LinkedList<Mouvement>();
    
    @ManyToMany
    @JoinTable(name="Mouvement_Compte_Archive")
    @OrderBy(value="date asc")
    public List<Mouvement> archives = new LinkedList<Mouvement>();
    
    @Temporal(TemporalType.DATE)
    public Date dateArchive;
    
    public Double montantArchive = 0.0;
    
    public TypeCompte type = TypeCompte.BANQUE;
    
    public Double dernierSoldeConsolide = 0.0;
    
    @Override
    public String toString(){
        return libelle.toString();
    }
    
    public void consoliderSolde(){
        Double montant = montantArchive;
        for(Mouvement mvt : mouvements){
            if(mvt.debiteur.equals(this)){
                montant-=mvt.montant;
            } else {
                montant+=mvt.montant;
            }
        }
        this.dernierSoldeConsolide = montant;
        this.save();
    }
    
    public void archiver(){
        Double montant = montantArchive;
        for(Mouvement mvt : mouvements){
            if(mvt.debiteur.equals(this)){
                montant-=mvt.montant;
            } else {
                montant+=mvt.montant;
            }
            archives.add(mvt);
        }
        mouvements.clear();
        this.montantArchive = montant;
        this.dernierSoldeConsolide = montant;
        this.dateArchive = new Date();
        this.save();
    }
}
