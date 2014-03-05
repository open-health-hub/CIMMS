/**
 * 
 */
package uk.ac.leeds.lihs.auecr.cimss.stroke

import org.codehaus.groovy.grails.web.json.JSONObject

/**
 * @author AjasinA
 *
 */
class TypeConversionHelper {

	
	public static Boolean getBooleanFromString ( Object value ) {
		if ( value == null ) {
			return null;
		} else if ( value instanceof Boolean ) {
		   return value
		} else if (value == "true"){
			return  Boolean.TRUE
		}else if (value =="false"){
			return Boolean.FALSE
		}else{
			return  null;
		}
	}
	
	public static Object getValueFromString( Object value ) {
		if(value && value!="null" && value!= JSONObject.NULL){
			return value
		}
		return null
	}
		
	public static Integer getIntegerFromString( Object value ) {
		
		def theInteger = null
		
		if ( value == null ) {
			theInteger = null
		} else if ( value instanceof Integer ) {
		   theInteger = value
		} else if ( value instanceof Number ) {
		   theInteger =  ((Number)value).intValue()
		} else if((value && value!="null" && value!= JSONObject.NULL) || value == 0 ){
			theInteger = new Integer(value)
		}

		return theInteger
	}

	public static Long getLongFromString( String value ) {
		if((value && value!="null" && value!= JSONObject.NULL) || value == 0 ){
			return new Long(value)
		}
		return null
	}
}
