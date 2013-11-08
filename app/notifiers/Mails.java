/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package notifiers;

import java.util.Arrays;
import java.util.List;
import models.Adherent;
import models.ParametreAssoc;
import play.mvc.Mailer;
import services.AssociationService;

/**
 *
 * @author Pluce
 */
public class Mails extends Mailer{
    public static void newsletter(Adherent dest,String objet, String corps, String style){
        setSubject(objet);
        addRecipient(dest.email);
        ParametreAssoc assoc = AssociationService.assocCourante();
        setFrom(assoc.nom+"<"+assoc.email+">");
        List<String> paragraphes = null;
        if(corps != null){
            paragraphes = Arrays.asList(corps.split("\n"));
        }
        send(assoc,dest,objet,paragraphes,style);
    }
}
