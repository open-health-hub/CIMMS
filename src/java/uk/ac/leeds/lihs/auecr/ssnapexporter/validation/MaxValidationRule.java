/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.lihs.auecr.ssnapexporter.validation;

import java.util.Map;

/**
 *
 * @author AjasinA
 */
public class MaxValidationRule  implements ValidationRule {
    
    private int maximum;
    
    public MaxValidationRule(String maxRuleSpec) {
        String maxText = maxRuleSpec.substring(4);       
        this.maximum = Integer.valueOf(maxText);
    }
    
    @Override
    public boolean validate(String value, Map<String,Object> context) {
        try { 
            if ( value.length() > 0 ) {
                int toCheck = Integer.valueOf(value);
                return( toCheck <= this.maximum );
            }
        } catch ( NumberFormatException e ) {
            
        }
        return false;
    }
        
    public static boolean canHandle(String ruleText) {
        return (ruleText.startsWith("max:"));
    }
}
