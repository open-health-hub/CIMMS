/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.lihs.auecr.ssnapexporter.validation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import uk.ac.leeds.lihs.auecr.ssnapexporter.metadata.SsnapField;
import uk.ac.leeds.lihs.auecr.ssnapexporter.metadata.SsnapFieldValue;
import uk.ac.leeds.lihs.auecr.ssnapexporter.metadata.SsnapRow;

/**
 *
 * @author AjasinA
 */
public class RuleValidator {
    public static Map<String, Object> makeValidationContext() {
        
        Map<String,Object> validationContext = new HashMap<String,Object>();
        
        JexlEngine engine = new JexlEngine();
        validationContext.put(JexlValidationRule.JEXL_ENGINE, engine);
        
        return validationContext;
    }
    
    public static boolean validate (List<SsnapRow> rowList, Map<String,Object> validationContext) {
    	boolean valid = true;
    	
    	for ( SsnapRow row: rowList ) {
    		valid = validate(row, validationContext);
    	}
    	
    	return valid;
    }
    
    public static boolean validate (SsnapRow row, Map<String,Object> validationContext) {
        boolean valid = true;
        
        updateValidationContext(validationContext, row);
        
        for( SsnapField field: row.getFieldList() ) {
            ValidationRuleSet ruleset = field.getValidationRuleSet();
            SsnapFieldValue value = row.getFieldValue( field.getName() );
            
            if ( value != null && value.getValue() != null) {
              
              if ( ruleset.validate(value.getValue(), validationContext ) ) {
                value.setFieldValid(true);                
              } else {
                value.setFieldValid(false);
                valid = false;
              }
            } else {
                valid = false;
            }
        }
        
        return valid;
    }
    
    private static void updateValidationContext(Map<String,Object>ctxMap, SsnapRow row) {
        
        JexlContext jexlCtx = new MapContext();
        
        for (SsnapField fld: row.getFieldList()) {
            String name = fld.getName();
            SsnapFieldValue value = row.getFieldValue(name);
            jexlCtx.set(name, value.getValue());
        }
        
        ctxMap.put(JexlValidationRule.JEXL_CONTEXT, jexlCtx);
    }
}
