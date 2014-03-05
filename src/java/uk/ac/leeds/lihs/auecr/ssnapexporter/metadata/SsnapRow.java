/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.lihs.auecr.ssnapexporter.metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author AjasinA
 */
public class SsnapRow {
    private int rowNumber = 0;
    private List<SsnapField> fieldList = new ArrayList<SsnapField>();
    private Map<String, SsnapFieldValue> fieldValueList = new HashMap<String, SsnapFieldValue>();

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public SsnapRow(SsnapMetadata metadata) {        
        for(SsnapField fld: metadata.getFieldList()) {
            addField(fld);
        }
    }
    
    public void addFieldValue(SsnapField fld, SsnapFieldValue value) {
        
    }
    
    public List<SsnapField> getFieldList() {
        return this.fieldList;
    }
    
    public void addField(SsnapField field) {
        this.fieldList.add(field);
    }
    
    public SsnapFieldValue getFieldValue(String fieldName) {
        return this.fieldValueList.get(fieldName);
    }
    
    public void setFieldValue(String field, SsnapFieldValue value) {
        this.fieldValueList.put(field, value);
    }
    
    public Map<String, SsnapFieldValue> getSsnapValueMap() {
        return this.fieldValueList;
    }
}
