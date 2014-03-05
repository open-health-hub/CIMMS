package uk.ac.leeds.lihs.auecr.cimss.stroke

import org.codehaus.groovy.grails.web.json.JSONObject;

class StrokeBaseController {


	
	protected def  getBooleanAsString = {value ->
		if (value==null) {
			return null
		} else {
			return value.toString()
		}
	}
	
	protected def getCheckedTrue = {value ->
		if(value == Boolean.TRUE){
			return value
		}else {
			return null;
		}
	}
	
	
	protected def getBooleanValue = {value ->
		if(value!="null" && value!= JSONObject.NULL){
			return value
		}
		return null
		
	}
		
	protected def getBooleanFromString = {value ->
		return TypeConversionHelper.getBooleanFromString(value)
	}
	
	protected def getValueFromString = {value ->
		return TypeConversionHelper.getValueFromString(value)
	}
	
	protected def getIntegerFromString = {value ->
		return TypeConversionHelper.getIntegerFromString(value)
	}

	protected def getLongFromString = {value ->
		return TypeConversionHelper.getLongFromString(value)
	}
	
	protected getFieldsInError = {careActivity ->
		def errors = []
		careActivity.errors.getFieldErrors().each { error ->
			if(error.getField()){
				errors.add error.getField()
			}
		}
		careActivity.errors.getGlobalErrors().each { error ->
			errors.add error.arguments[0]
		}
		return errors
	}
	
	protected def getErrorsAsList(careActivity){
		if(getFieldsInError(careActivity)){
			return "<div class=\"errors\">${g:renderErrors()}</div>"
		}
		return ""
	}
	
	protected def getInfoMessage = {careActivity ->
		
		def infoMsg 	= []
		def warnMsg 	= []
		def errMsg 		= []
		def msg = ""		
		
		if(getFieldsInError(careActivity)){
			errMsg.add("Unable to update")						
		}else{
			infoMsg.add("Record updated")
		}
		
		flash.warningMessages?.each { m ->
			warnMsg.add(m)
		}
		flash.infoMessages?.each { m ->
			infoMsg.add(m)
		}

		if ( errMsg.size() > 0 ) {
			msg = msg + "<div class=\"alert alert-error\">"
			errMsg.each { m ->
				msg = msg + "${m}<br>"
			}
			msg = msg + "</div>"
		}
		if ( warnMsg.size() > 0 ) {
			msg = msg + "<div class=\"alert\">"
			warnMsg.each { m ->
				msg = msg + "${m}<br>"
			}
			msg = msg + "</div>"
		}
		if ( infoMsg.size() > 0 ) {
			msg = msg + "<div class=\"alert alert-info\">"
			infoMsg.each { m ->
				msg = msg + "${m}<br>"
			}
			msg = msg + "</div>"
		}

		return msg
	}
	
	protected def addInfoMessage = { mesg -> 
		if ( flash.infoMessages == null ) {
			flash.infoMessages = []
		}
		flash.infoMessages.add(mesg)		 
	}
	
	protected def addWarningMessage = { mesg ->
		if ( flash.warningMessages == null ) {
			flash.warningMessages = []
		}
		flash.warningMessages.add(mesg) 
	}
	
	protected def getPeriod = { duration ->
		def periodSinceAdmission = ""
		if(duration.days > 7){
			periodSinceAdmission = "greater than 7 days"
		}else if(duration.days>1){
			periodSinceAdmission = "${duration.days} days,  ${duration.hours} hours, ${duration.minutes} minutes"
		}else if (duration.days == 1){
			periodSinceAdmission = "${duration.days} day,  ${duration.hours} hours, ${duration.minutes} minutes"
		}else{
			periodSinceAdmission = "${duration.hours} hours, ${duration.minutes} minutes"
		}
		return periodSinceAdmission
			
	}
	
	
	
}
