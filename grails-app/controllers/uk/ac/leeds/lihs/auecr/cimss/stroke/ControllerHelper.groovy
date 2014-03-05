package uk.ac.leeds.lihs.auecr.cimss.stroke

import org.codehaus.groovy.grails.web.json.JSONObject;

class ControllerHelper {
	
	public static def getDate = { value ->
		if(value){
			return Date.parse('dd/MM/yyyy' , value)
		}
		return ""
	}
	
	public  static def getTime = {value ->
		def components = value.tokenize(":")
		return components[0].toInteger() * 60 + components[1].toInteger();
	}
	
	public  static def getValueFromString = {value ->
		if(value && value!="null" && value!= JSONObject.NULL){
			return value
		}
		return null
	}
	
	public  static def getIntegerFromString = {value ->
		if((value && value!="null" && value!= JSONObject.NULL) || value == 0 ){
			return new Integer(value)
		}
		return null
	}
	
	public  static def getBooleanFromString = {value ->
		if(value =="true"){
			return  Boolean.TRUE
		}else if (value =="false"){
			return Boolean.FALSE
		}else{
			return  null;
		}
	}
	
	
	
	public  static def getFieldsInError = {domainObject ->
		def errors = []
		domainObject.errors.allErrors.each { error ->
			errors.add error.getField()
		}
		return errors
	}
	
	public  static def getErrorsAsList = {domainObject ->
		if(getFieldsInError(domainObject)){
			return "<div class=\"errors\">${g:renderErrors()}</div>"
		}
		return ""
	}
	
	public  static def getInfoMessage = {domainObject ->
		if(getFieldsInError(domainObject)){
			"<div class=\"message\">Unable to update</div>"
		}else{
			"<div class=\"message\">Record updated</div>"
		}
		
	}

}
