package uk.ac.leeds.lihs.auecr.ssnapexporter.validation;

import java.util.Map;

/**
 *
 * @author AjasinA
 */
public class MinValidationRule  implements ValidationRule {
    
    private int minimum;
    
    public MinValidationRule(String regexp) {
        String minText = regexp.substring(4);       
        this.minimum = Integer.valueOf(minText);
    }
    
    @Override
    public boolean validate(String value, Map<String,Object> context) {
        try {
            if ( value.length() > 0 ) {
                int toCheck = Integer.valueOf(value);
                return( toCheck >= this.minimum );
            }
        } catch ( NumberFormatException e ) {

        }
        return false;
    }
    
    public static boolean canHandle(String ruleText) {
        return (ruleText.startsWith("min:"));
    }
}
