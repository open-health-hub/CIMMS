/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.lihs.auecr.ssnapexporter.metadata;

import uk.ac.leeds.lihs.auecr.ssnapexporter.validation.ValidationRuleSet;

/**
 *
 * @author AjasinA
 */
public class SsnapField {
    private String name;
    private String number;
    private ValidationRuleSet ruleSet;
    private Boolean fieldPresent = Boolean.FALSE;


   public Boolean getFieldPresent() {
        return fieldPresent;
    }

    public Boolean isFieldPresent() {
        return fieldPresent;
    }

    public void setFieldPresent(Boolean fieldPresent) {
        this.fieldPresent = fieldPresent;
    }


    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ValidationRuleSet getValidationRuleSet() {
        return this.ruleSet;
    }

    public void setValidationRuleSet(ValidationRuleSet ruleSet) {
        this.ruleSet = ruleSet;
    }

}
