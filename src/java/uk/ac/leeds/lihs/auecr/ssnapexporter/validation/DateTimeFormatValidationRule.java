/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.lihs.auecr.ssnapexporter.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AjasinA
 */
public class DateTimeFormatValidationRule implements ValidationRule {
    
    private SimpleDateFormat dateFormat;
    
    public DateTimeFormatValidationRule(String regexp) {
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
    }
    
    @Override
    public boolean validate(String value, Map<String,Object> context) {

        boolean valid = false;
        try {
            if ( value.length() > 0 ) {
                this.dateFormat.parse(value);
                valid = true;
            }
        } catch (ParseException ex) {
            Logger.getLogger(DateTimeFormatValidationRule.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return valid;
    }
    
    
    public static boolean canHandle(String ruleText) {
        return (ruleText.startsWith("dt:"));
    }    
}
