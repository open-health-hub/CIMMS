package uk.ac.leeds.lihs.auecr.ssnapexporter.validation;

import java.util.Map;

/**
 *
 * @author AjasinA
 */
public class BooleanValidationRule  implements ValidationRule {
    
    public static final String REGEXP = "^(1|0|TRUE|FALSE|)$";
    
    public BooleanValidationRule(String regexp) {
    }
    
    @Override
    public boolean validate(String value, Map<String,Object> context) {
        return( value.matches(REGEXP) );
    }
    
    public static boolean canHandle(String ruleText) {
        return (ruleText.startsWith("bool:"));
    }
}
