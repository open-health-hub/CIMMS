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
public class RegexpValidationRule implements ValidationRule {

    private String regexp;
    
    public RegexpValidationRule(String regexp) {
        this.regexp = regexp.substring(3);
    }
    
    @Override
    public boolean validate(String value, Map<String,Object> context) {
        return( value.matches(regexp) );
    }
    
    public static boolean canHandle(String ruleText) {
        return (ruleText.startsWith("re:"));
    }
}
