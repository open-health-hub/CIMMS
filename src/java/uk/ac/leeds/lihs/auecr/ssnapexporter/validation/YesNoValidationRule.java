package uk.ac.leeds.lihs.auecr.ssnapexporter.validation;

import java.util.Map;

/**
 *
 * @author AjasinA
 */
public class YesNoValidationRule  implements ValidationRule {
    
    private String regexp = null;
    
    public YesNoValidationRule(String regexpSupplement) {
        
        if ( regexpSupplement != null &&  regexpSupplement.trim().length() > 3) {
            
            this.regexp =  "^(Y|N|" + regexpSupplement.substring(3) + ")$";
        } else {
            this.regexp = "^(Y|N)$";
        }            
    }
    
    @Override
    public boolean validate(String value, Map<String,Object> context) {
        return( value.matches(this.regexp) );
    }
    
    public static boolean canHandle(String ruleText) {
        return (ruleText.startsWith("yn:"));
    }
}
