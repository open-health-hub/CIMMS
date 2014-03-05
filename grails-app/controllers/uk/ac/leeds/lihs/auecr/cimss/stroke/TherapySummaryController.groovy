package uk.ac.leeds.lihs.auecr.cimss.stroke


import org.codehaus.groovy.grails.web.json.JSONObject;

import uk.ac.leeds.lihs.auecr.cimss.stroke.therapy.PhysiotherapyManagement;
import uk.ac.leeds.lihs.auecr.cimss.stroke.therapy.SpeechAndLanguageTherapyManagement;

import uk.ac.leeds.lihs.auecr.cimss.stroke.therapy.OccupationalTherapyManagement;

import grails.converters.JSON;

class TherapySummaryController  extends  StrokeBaseController{

    def getTherapySummaryPage = {
		log.debug "in getTherapySummaryPage"
		def careActivity = CareActivity.get(params.id)
		render(template:'/therapySummary/therapySummary',model:['careActivityInstance':careActivity])
	}

    def getTherapySummary = {
		log.debug "in getTherapySummary"
		def careActivity = CareActivity.get(params.id)
		renderTherapySummary(careActivity);
	}
	
	
	def updateTherapySummary = {
		log.info "in updateTherapySummary -> ${request.JSON.therapySummary}"
		def careActivity = CareActivity.get(params.id)
		def errors = [:]		
		def data = request.JSON.therapySummary
		
		
		if(changedSinceRetrieval(careActivity, data)){
			data.versions = getVersions(careActivity)
			careActivity.errors.rejectValue('version', 'version.changed.therapy.summary')
			careActivity.discard()
			renderTherapySummaryWithErrors( data, careActivity, errors)
		}else{
			updateData(careActivity, data, errors)
			if(!errors & careActivity.validate()){
				careActivity.save(flush:true)
				renderTherapySummary(careActivity)
			}else{
				careActivity.discard()
				renderTherapySummaryWithErrors( data, careActivity, errors)
			}
		}
		
		
		
		
	}	
	
	private def getVersions = { careActivity ->
		return ['careActivity':careActivity?.version
				]
	}
	
	private def changedSinceRetrieval = { careActivity, data ->		
		return false
		/*if(careActivity?.therapyManagement?.version!=null && data.versions.therapyManagement == JSONObject.NULL){
			return true
		}
		
		if(careActivity?.therapyManagement?.version && careActivity?.therapyManagement?.version >(long)data.versions.therapyManagement){

			if(careActivity.therapyManagement.pyschologyTherapyRequired != getBooleanValue(data.requiredTherapies.psychology)){
				return true
			}
			if(careActivity.therapyManagement.pyschologyDaysOfTherapy != getIntegerFromString(data.daysOfTherapy.psychology)){
				return true
			}
			if(careActivity.therapyManagement.pyschologyMinutesOfTherapy != getIntegerFromString(data.minutesOfTherapy.psychology)){
				return true
			}
			
			if(careActivity.therapyManagement.nurseLedTherapyRequired != getBooleanValue(data.requiredTherapies.nurse)){
				return true
			}
			if(careActivity.therapyManagement.nurseLedTherapyDaysOfTherapy != getIntegerFromString(data.daysOfTherapy.nurse)){
				return true
			}
			if(careActivity.therapyManagement.nurseLedTherapyMinutesOfTherapy != getIntegerFromString(data.minutesOfTherapy.nurse)){
				return true
			}
			
		
		
		}*/
		
	}
	
	private def updateData = {careActivity, data, errors ->
		TherapySummaryHelper.ensureTherapyManagementExists(careActivity);
		TherapySummaryHelper.updatePhysiotherapy(careActivity, data)
		TherapySummaryHelper.updateOccupationalTherapy(careActivity, data)
		TherapySummaryHelper.updateSpeechAndLanguageTherapy(careActivity, data)
		TherapySummaryHelper.updatePsychology(careActivity, data)
		TherapySummaryHelper.updateNurseLedTherapy(careActivity, data)		
	}
	

	
	
	private def renderTherapySummary = {careActivity ->
	
	  def daysOfTherapy = [physiotherapy:careActivity?.therapyManagement?.physiotherapyManagement?.daysOfTherapy,
		  					occupational:careActivity?.therapyManagement?.occupationalTherapyManagement?.daysOfTherapy,
							salt:careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.daysOfTherapy,
							psychology:careActivity?.therapyManagement?.pyschologyDaysOfTherapy,
							nurse:careActivity?.therapyManagement?.nurseLedTherapyDaysOfTherapy]
		
	  def minutesOfTherapy = [physiotherapy:careActivity?.therapyManagement?.physiotherapyManagement?.minutesOfTherapy,
		  					occupational:careActivity?.therapyManagement?.occupationalTherapyManagement?.minutesOfTherapy,
							salt:careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.minutesOfTherapy,
							psychology:careActivity?.therapyManagement?.pyschologyMinutesOfTherapy,
							nurse:careActivity?.therapyManagement?.nurseLedTherapyMinutesOfTherapy]
	  
	  def requiredTherapies = [physiotherapy:getBooleanAsString(careActivity?.therapyManagement?.physiotherapyManagement?.therapyRequired),
		  					occupational:getBooleanAsString(careActivity?.therapyManagement?.occupationalTherapyManagement?.therapyRequired),
							salt:getBooleanAsString(careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.therapyRequired),
							psychology:getBooleanAsString(careActivity?.therapyManagement?.pyschologyTherapyRequired),
							nurse:getBooleanAsString(careActivity?.therapyManagement?.nurseLedTherapyRequired)]
	  
	  def therapySummary = [id:1,
		  					versions:getVersions(careActivity),
							  requiredTherapies:requiredTherapies,
							daysOfTherapy:daysOfTherapy,
							minutesOfTherapy:minutesOfTherapy]
		  
		  def result = [therapySummary:therapySummary, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		  render result as JSON
	}
	
	private def renderTherapySummaryWithErrors = {data, careActivity, errors ->
		log.debug "In renderTherapySummaryWithErrors"
		errors.each{key, value ->
			log.debug "Adding non domain error --> ${key} :: ${value}"
			careActivity.errors.reject(value,[key]as Object[], "Custom validation failed for ${key} in the controller".toString())
		}

		def result = [therapySummary:data, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
	}
	
	
}
