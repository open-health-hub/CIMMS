/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.lihs.auecr.ssnapexporter.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author AjasinA
 */
public class ValidationRuleSet {
    
    private List<ValidationRule> rules = new ArrayList<ValidationRule>();
    private RuleLogicalCombination logicalCombination;
            
    public ValidationRuleSet(RuleLogicalCombination combination) {
        this.logicalCombination = combination;
    } 
    
    public boolean validate(String value, Map<String,Object> context) {
        boolean valid = true;
        for ( ValidationRule rule: rules) {            
            if ( ! rule.validate(value, context) ) {
                valid = false;
            }
            if ( valid && logicalCombination.equals(RuleLogicalCombination.OR) ) {
                break;
            }
        }
        return valid;
    }

    void addRule(ValidationRule rule) {
        this.rules.add(rule);
    }
    
}
