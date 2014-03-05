/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.lihs.auecr.ssnapexporter.validation;


/**
 *
 * @author AjasinA
 */
public class ValidationRuleSetFactory {
    
    public static final String RULE_SET_RULE_SEPARATOR_AND = "//";
    public static final String RULE_SET_RULE_SEPARATOR_OR = ">>";

    public static ValidationRuleSet createRuleSet(String ruleSetSpec) {
        
        RuleLogicalCombination combinator = makeCombination(ruleSetSpec);
        ValidationRuleSet ruleSet = new ValidationRuleSet(combinator);
        
        String[] ruleSpecList = decompose(ruleSetSpec, combinator) ;
        
        for ( String ruleSpec : ruleSpecList ) {
            if ( MinValidationRule.canHandle(ruleSpec)) {
                ValidationRule rule = new MinValidationRule(ruleSpec);
                ruleSet.addRule(rule);            
            }
            if ( MaxValidationRule.canHandle(ruleSpec)) {
                ValidationRule rule = new MaxValidationRule(ruleSpec);
                ruleSet.addRule(rule);            
            }
            if ( DateFormatValidationRule.canHandle(ruleSpec)) {
                ValidationRule rule = new DateFormatValidationRule(ruleSpec);
                ruleSet.addRule(rule);            
            }
            if ( DateTimeFormatValidationRule.canHandle(ruleSpec)) {
                ValidationRule rule = new DateTimeFormatValidationRule(ruleSpec);
                ruleSet.addRule(rule);            
            }        
            if ( RegexpValidationRule.canHandle(ruleSpec)) {
                ValidationRule rule = new RegexpValidationRule(ruleSpec);
                ruleSet.addRule(rule);            
            }
            if ( JexlValidationRule.canHandle(ruleSpec)) {
                ValidationRule rule = new JexlValidationRule(ruleSpec);
                ruleSet.addRule(rule);            
            }
            if ( BooleanValidationRule.canHandle(ruleSpec)) {
                ValidationRule rule = new BooleanValidationRule(ruleSpec);
                ruleSet.addRule(rule);            
            }
            if ( YesNoValidationRule.canHandle(ruleSpec)) {
                ValidationRule rule = new YesNoValidationRule(ruleSpec);
                ruleSet.addRule(rule);            
            }

        }
        return ruleSet;
    }

    private static String[] decompose(String ruleSetSpec, RuleLogicalCombination combo ) {
        
        String[] specList ;
        
        if ( combo.equals(RuleLogicalCombination.OR) ) {
            specList = ruleSetSpec.split(RULE_SET_RULE_SEPARATOR_OR);
        } else {
            specList = ruleSetSpec.split(RULE_SET_RULE_SEPARATOR_AND);
        }
        
        return specList;
    }

    private static RuleLogicalCombination makeCombination(String ruleSetSpec) {
        if ( ruleSetSpec.contains(RULE_SET_RULE_SEPARATOR_OR)) {
            return RuleLogicalCombination.OR;
        }
        else {
            return RuleLogicalCombination.AND;
        }
    }
}
