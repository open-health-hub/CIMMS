package uk.ac.leeds.lihs.auecr.ssnapexporter.validation;

import java.util.Map;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.Expression;

/**
 *
 * @author AjasinA
 */
public class JexlValidationRule  implements ValidationRule {
    
    public static final String JEXL_ENGINE = "Jexl.Engine";
    public static final String JEXL_CONTEXT = "Jexl.Context";
    
    private String jexlExprText = null;
    private Expression jexlExpr = null;
    
    public JexlValidationRule(String jexlRuleSpec) {                
        this.jexlExprText = jexlRuleSpec.substring(5);
    }
    
    @Override
    public boolean validate(String value, Map<String,Object> context) {
                
        if ( jexlExpr == null ) {
            JexlEngine engine = (JexlEngine) context.get(JEXL_ENGINE);
            this.jexlExpr = engine.createExpression(this.jexlExprText);
        }
        
        //System.out.println("JexlValidationRule validate: value='"+value+"', jexlExpr="+jexlExprText);
        JexlContext jexlCtx = (JexlContext) context.get(JEXL_CONTEXT);
        Object result = this.jexlExpr.evaluate(jexlCtx);
        
        if ( result != null && result instanceof Boolean)  {
            Boolean booleanResult = (Boolean) result;
            return  booleanResult.booleanValue();
        }
        return false;
    }
        
    public static boolean canHandle(String ruleText) {
        return (ruleText.startsWith("jexl:"));
    }
}
