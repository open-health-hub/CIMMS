/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.lihs.auecr.ssnapexporter.metadata;

/**
 *
 * @author AjasinA
 */
public class SsnapFieldValue {
    private Boolean valid = Boolean.FALSE;
    private String value = "";
    
    public Boolean getFieldValid() {
        return valid;
    }

    public Boolean isFieldValid() {
        return valid;
    }

    public void setFieldValid(Boolean fieldValid) {
        this.valid = fieldValid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
