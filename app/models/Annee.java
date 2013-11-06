/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import play.db.jpa.Model;

/**
 *
 * @author Pluce
 */
@Entity
public class Annee extends Model{
    public Integer annee = 0;
    public Double cotisation;
    public Boolean cloture = false;
    
    @OneToMany
    public List<Mouvement> mouvements = new LinkedList<Mouvement>();
    
    @Override
    public String toString(){
        return annee.toString();
    }
}
