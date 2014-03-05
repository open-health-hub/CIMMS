package uk.ac.leeds.lihs.auecr.ssnapexporter.validation;

import java.util.Map;

/**
 *
 * @author AjasinA
 */
public interface ValidationRule {
    
    
    public boolean validate(String value, Map<String,Object> context);
    
}
