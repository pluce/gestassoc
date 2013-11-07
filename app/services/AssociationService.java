package services;

import models.ParametreAssoc;
import play.db.jpa.GenericModel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pluce
 */
public class AssociationService {
    public static ParametreAssoc assocCourante(){
        return ParametreAssoc.find("").first();
    }
}
