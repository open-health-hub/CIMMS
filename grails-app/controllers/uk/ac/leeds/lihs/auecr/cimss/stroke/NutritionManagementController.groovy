package uk.ac.leeds.lihs.auecr.cimss.stroke

import org.codehaus.groovy.grails.web.json.JSONObject;

import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.InadequateNutritionReasonType;
import grails.converters.JSON;
import groovy.time.TimeCategory;
import groovy.time.TimeDuration;

class NutritionManagementController extends StrokeBaseController{
	
	 def getNutritionManagementPage = {
		log.debug "in getNutritionManagementPage"
		def careActivity = CareActivity.get(params.id)
		render(template:'/assessment/nutrition',model:['careActivityInstance':careActivity])
	}

	def getNutritionManagement = {
		log.debug "in getNutritionManagement"
		def careActivity = CareActivity.get(params.id);
		renderNutritionManagement(careActivity);
	}
			
	 def updateNutritionManagement = {
		log.info "in updateNutritionManagement -> ${request.JSON.NutritionManagement}"
		def careActivity = CareActivity.get(params.id);
		def errors = [:]
		def data = request.JSON.NutritionManagement
		
		

		if(changedSinceRetrieval(careActivity, data)){
			data.versions = getVersions(careActivity)
			careActivity.errors.rejectValue('version', 'version.changed.nutrition.management')
			careActivity.discard()
			renderNutritionManagementWithErrors(data, errors, careActivity);
		}else{
			updateData(careActivity, data, errors)
			if(careActivity.validate() & !errors){
					careActivity.save(flush:true)
					renderNutritionManagement(careActivity)
			}else{
					careActivity.discard()
					renderNutritionManagementWithErrors(data, errors, careActivity);
			}
		}		
	}
	
	private def changedSinceRetrieval = { careActivity, data->		
		if(careActivity?.nutritionManagement?.version!=null && data.versions.nutritionManagement == JSONObject.NULL){
			return true
		}
		if(careActivity?.nutritionManagement?.version && careActivity?.nutritionManagement?.version >(long)data.versions.nutritionManagement){
			return true
		}
		return false
	}
	
	private def getVersions = { careActivity ->
		return ['careActivity':careActivity?.version
			,'nutritionManagement':careActivity?.nutritionManagement?.version]
	}
	
	private def updateData = {careActivity, data, errors ->
		def nutritionManagement = ensureNutritionManagementExists(careActivity)
		
		if(data.unableToScreen == true ){
			nutritionManagement?.unableToScreen = Boolean.TRUE
			clearScreeningData(nutritionManagement)
			
		}else{
			processScreeningData(nutritionManagement, data, errors)						
		}	
		
		if(data.dietitianNotSeen == true ){
			nutritionManagement?.dietitianNotSeen = Boolean.TRUE
			clearDieticianData(nutritionManagement)
		}else{
			processDieticianData(nutritionManagement, data, errors)
		}
	
	}

	private clearScreeningData(NutritionManagement nutritionManagement) {
		nutritionManagement.unableToScreen = Boolean.TRUE
		nutritionManagement.dateScreened=null
		nutritionManagement.timeScreened = null
		nutritionManagement.mustScore = null
	}
	
	private clearDieticianData(NutritionManagement nutritionManagement) {	
		nutritionManagement.dietitianReferralDate=null
		nutritionManagement.dietitianReferralTime = null
	}
	
	private processDieticianData(NutritionManagement nutritionManagement, data, errors) {
		if(data.dietitianNotSeen){
			nutritionManagement?.dietitianNotSeen = Boolean.FALSE
		}else{
			nutritionManagement?.dietitianNotSeen = null;
		}
		
		
		try{
			nutritionManagement?.dietitianReferralDate=DisplayUtils.getDate(data.dietitianReferralDate)
		}catch(IllegalArgumentException iae){
			errors.put("nutritionManagement.dietitianReferralDate", "nutrition.dietitian.referral.date.invalid.format")
		}
		try{
			nutritionManagement?.dietitianReferralTime = DisplayUtils.getTime(data.dietitianReferralTime)
		}catch(IllegalArgumentException iae){
			errors.put("nutritionManagement.dietitianReferralTime", "nutrition.dietitian.referral.time.invalid.format")
		}
		
	}


	private processScreeningData(NutritionManagement nutritionManagement, data, errors) {
		if(data.unableToScreen){
			nutritionManagement?.unableToScreen = Boolean.FALSE
		}else{
			nutritionManagement?.unableToScreen = null;
		}
		try{
			nutritionManagement?.dateScreened=DisplayUtils.getDate(data.dateScreened)
		}catch(IllegalArgumentException iae){
			errors.put("nutritionManagement.dateScreened", "nutrition.screen.date.invalid.format")
		}
		try{
			nutritionManagement?.timeScreened = DisplayUtils.getTime(data.timeScreened)
		}catch(IllegalArgumentException iae){
			errors.put("nutritionManagement.timeScreened", "nutrition.screen.time.invalid.format")
		}

		nutritionManagement?.mustScore = getIntegerFromString(data.mustScore)
		
	}

	
	private NutritionManagement ensureNutritionManagementExists(careActivity) {
		def nutritionManagement  = careActivity.nutritionManagement;
		if(!nutritionManagement){
			careActivity.nutritionManagement = new NutritionManagement();
			nutritionManagement = careActivity.nutritionManagement
			nutritionManagement.careActivity = careActivity
		}
		return nutritionManagement
	}
	
		
	private def renderNutritionManagement = { careActivity  ->
		log.trace "In renderNutritionManagement"	
				   def nutrition = [id:careActivity?.nutritionManagement?.id
					   			, versions:getVersions(careActivity)
								, dateScreened:careActivity?.nutritionManagement?.dateScreened?.format("dd/MM/yyyy" )
								, timeScreened:DisplayUtils.displayTime(careActivity?.nutritionManagement?.timeScreened)
								, mustScore:careActivity?.nutritionManagement?.mustScore
								, unableToScreen:careActivity?.nutritionManagement?.unableToScreen
								, dietitianReferralDate:DisplayUtils.displayDate(careActivity?.nutritionManagement?.dietitianReferralDate)
								, dietitianReferralTime:DisplayUtils.displayTime(careActivity?.nutritionManagement?.dietitianReferralTime)
								, dietitianNotSeen:careActivity?.nutritionManagement?.dietitianNotSeen]
				   def result = [NutritionManagement:nutrition, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
				   
				   render result as JSON		
		}
	
	private def renderNutritionManagementWithErrors = { data, errors, careActivity  ->
		log.debug "In renderNutritionManagementWithErrors"
		errors.each{key, value ->
			log.debug "${key} :: ${value}"
			careActivity.errors.rejectValue(key, value, "Custom validation failed for ${key} in the controller")
		}
		def result = [NutritionManagement:data, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]			   
		render result as JSON
		}

}
