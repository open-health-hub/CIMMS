/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.lihs.auecr.ssnapexporter.metadata;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author AjasinA
 */
public class SsnapMetadata {
    
    private String version = "1.1.4";
    private List<SsnapField> fieldList = new ArrayList<SsnapField>();
    private int actualRowCount = 0;
    private int expectedRowCount= 0;
    
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<SsnapField> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<SsnapField> fieldList) {
        this.fieldList = fieldList;
    }
    
    public SsnapMetadata(final List<SsnapField> fieldList) {
        this.fieldList.clear();
        this.fieldList.addAll(fieldList);
    }
    
    public Boolean getHasMissingColumns() {
        for (SsnapField fld: this.fieldList) {
            if ( ! fld.getFieldPresent() ) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
    
    public int getMissingColumnCount() {
        int numMissingColumns = 0;
        for (SsnapField fld: this.fieldList) {
            if ( ! fld.getFieldPresent() ) {
                numMissingColumns ++;
            }
        }
        return numMissingColumns;
    }
    
    public int getExpectedColumnCount() {
        return this.fieldList.size();
    }

    public int getActualColumnCount() {
        int numPresentColumns = 0;
        for ( SsnapField fld: this.fieldList ) {
            if(fld.isFieldPresent()) {
                numPresentColumns++;
            }
        }
        return numPresentColumns;
    }

    public SsnapField getField(String columnName) {
        for( int i = 0; i < this.fieldList.size() ; i ++) {
            SsnapField fld = this.fieldList.get(i);
            if ( fld.getName().equals(columnName) ) {
                return fld;
            }
        }
        return null;
    }

    public int getActualRowCount() {
        return actualRowCount;
    }

    public int getExpectedRowCount() {
        return expectedRowCount;
    }
    
    public void setExpectedRowCount(int expectedRowCount) {
        this.expectedRowCount = expectedRowCount;
    }

    public void setActualRowCount(int actualRowCount) {
        this.actualRowCount = actualRowCount;
    }
}
