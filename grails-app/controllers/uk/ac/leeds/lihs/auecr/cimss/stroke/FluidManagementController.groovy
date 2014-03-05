package uk.ac.leeds.lihs.auecr.cimss.stroke

import org.codehaus.groovy.grails.web.json.JSONObject;

import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.InadequateFluidReasonType;
import grails.converters.JSON;
import groovy.time.TimeCategory;
import groovy.time.TimeDuration;

class FluidManagementController extends StrokeBaseController {
	
	def getFluidManagementPage = {
		log.debug "in getFluidManagementPage"
		def careActivity = CareActivity.get(params.id)
		render(template:'/assessment/fluid',model:['careActivityInstance':careActivity])
	}

	def getFluidManagement = {
		log.debug "in getFluidManagement"
		def careActivity = CareActivity.get(params.id);
		renderFluidManagement(careActivity);
	}
	
	
		
	
	def updateFluidManagement = {
		log.info "in updateFluidManagement -> ${request.JSON.FluidManagement}"
		def careActivity = CareActivity.get(params.id);
		def errors = [:]
		def data = request.JSON.FluidManagement
		if(changedSinceRetrieval(careActivity, data)){
			data.versions = getVersions(careActivity)
			data.skipBruteForce = true
			careActivity.errors.rejectValue('version', 'version.changed.fluid.management')
			careActivity.discard()
			renderFluidManagementWithErrors(data, careActivity, errors);
		}else{
			updateData(careActivity, data, errors)
			if(careActivity.validate() & !errors){
					careActivity.save(flush:true)
					renderFluidManagement(careActivity);
			}else{
					careActivity.discard()
					renderFluidManagementWithErrors(data, careActivity, errors);
			}
		}
	}
	
	
	private def changedSinceRetrieval = { careActivity, data->
		if(careActivity?.fluidManagement?.version!=null && data.versions.fluidManagement == JSONObject.NULL){
			return true
		}
		if(careActivity?.fluidManagement?.version && careActivity?.fluidManagement?.version >(long)data.versions.fluidManagement){
			return true
		}
		
		if(!data.originalData.skipBruteForce){
			// now do a brute force comparison of the JSON objects	
			def original = new JSONObject(data.originalData.toString())
			original.remove('hoursSinceAdmission')
			
			def persistedData = new JSONObject(getFluidManagementAsJSON(careActivity).toString())
			persistedData.remove('hoursSinceAdmission')
			return !original.equals(persistedData)
		}else{
			return false	
		}

	}
	
	private def getVersions = { careActivity ->
		return [careActivity:careActivity?.version
			,fluidManagement:careActivity?.fluidManagement?.version]		
	}
	
	
	private def updateData = {careActivity, data, errors ->
		def fluidManagement  = careActivity.fluidManagement;
		if(!fluidManagement){
			careActivity.fluidManagement = new FluidManagement();
			fluidManagement = careActivity.fluidManagement
		}
		
		fluidManagement?.litrePlusAt24 = getValueFromString(data.litrePlusAt24)
		if(data.litrePlusAt24=="no"){
			if(getValueFromString(data.inadequateAt24Reason)){
				fluidManagement?.inadequateAt24FluidReasonType = InadequateFluidReasonType.findByDescription(data.inadequateAt24Reason)
				if(fluidManagement?.inadequateAt24FluidReasonType?.description == "Nasogastric tube not used - other"
					|| fluidManagement?.inadequateAt24FluidReasonType?.description == "Other" ){
					fluidManagement?.inadequateAt24ReasonOther=getValueFromString(data.inadequateAt24ReasonOther)
				}else{
					fluidManagement?.inadequateAt24ReasonOther=null
				}
			}else{
				fluidManagement?.inadequateAt24FluidReasonType = null
				fluidManagement?.inadequateAt24ReasonOther=null
			}
		}else{
			fluidManagement?.inadequateAt24FluidReasonType = null
			fluidManagement?.inadequateAt24ReasonOther=null
		}
		
		
		
		fluidManagement?.litrePlusAt48 = getValueFromString(data.litrePlusAt48)
		if(data.litrePlusAt48=="no"){
			if(getValueFromString(data.inadequateAt48Reason)){
				fluidManagement?.inadequateAt48FluidReasonType = InadequateFluidReasonType.findByDescription(data.inadequateAt48Reason)
				if(fluidManagement?.inadequateAt48FluidReasonType?.description == "Nasogastric tube not used - other"
					|| fluidManagement?.inadequateAt48FluidReasonType?.description == "Other" ){
					fluidManagement?.inadequateAt48ReasonOther=getValueFromString(data.inadequateAt48ReasonOther)
				}else{
					fluidManagement?.inadequateAt48ReasonOther=null
				}
			}else{
				fluidManagement?.inadequateAt48FluidReasonType = null
				fluidManagement?.inadequateAt48ReasonOther=null
			}
		}else{
			fluidManagement?.inadequateAt48FluidReasonType = null
			fluidManagement?.inadequateAt48ReasonOther=null
		}
		
		
		fluidManagement?.litrePlusAt72 = getValueFromString(data.litrePlusAt72)
		if(data.litrePlusAt72=="no"){
			if(getValueFromString(data.inadequateAt72Reason)){
				fluidManagement?.inadequateAt72FluidReasonType = InadequateFluidReasonType.findByDescription(data.inadequateAt72Reason)
				if(fluidManagement?.inadequateAt72FluidReasonType?.description == "Nasogastric tube not used - other"
					|| fluidManagement?.inadequateAt72FluidReasonType?.description == "Other" ){
					fluidManagement?.inadequateAt72ReasonOther=getValueFromString(data.inadequateAt72ReasonOther)
				}else{
					fluidManagement?.inadequateAt72ReasonOther=null
				}
			}else{
				fluidManagement?.inadequateAt72FluidReasonType = null
				fluidManagement?.inadequateAt72ReasonOther=null
			}
		}else{
			fluidManagement?.inadequateAt72FluidReasonType = null
			fluidManagement?.inadequateAt72ReasonOther=null
		}
		
	}
		
	private def getFluidManagementAsJSON =  {careActivity  ->
		def admission = ((Date)careActivity.startDate).toCalendar();
		if(careActivity.startTime){
			admission.set( Calendar.HOUR_OF_DAY, (int)careActivity.startTime / 60)
			admission.set( Calendar.MINUTE ,careActivity.startTime % 60	)
		}else{
			admission.set( Calendar.HOUR_OF_DAY, 0)		
		}
		TimeDuration duration = TimeCategory.minus( new Date(), admission.time )
		def hoursSinceAdmission = (duration.days * 24) + (duration.hours) + (duration.minutes/60) 		
		def fluid = [id:careActivity?.fluidManagement?.id
					, versions:new JSONObject(getVersions(careActivity))
					, litrePlusAt24:careActivity?.fluidManagement?.litrePlusAt24
					, inadequateAt24Reason:careActivity?.fluidManagement?.inadequateAt24FluidReasonType?.description
					, inadequateAt24ReasonOther:careActivity?.fluidManagement?.inadequateAt24ReasonOther
					, litrePlusAt48:careActivity?.fluidManagement?.litrePlusAt48
					, inadequateAt48Reason:careActivity?.fluidManagement?.inadequateAt48FluidReasonType?.description
					, inadequateAt48ReasonOther:careActivity?.fluidManagement?.inadequateAt48ReasonOther
					, litrePlusAt72:careActivity?.fluidManagement?.litrePlusAt72
					, inadequateAt72Reason:careActivity?.fluidManagement?.inadequateAt72FluidReasonType?.description
					, inadequateAt72ReasonOther:careActivity?.fluidManagement?.inadequateAt72ReasonOther
					, hoursSinceAdmission:hoursSinceAdmission
					, periodSinceAdmission:getPeriod(duration)]
		return new JSONObject(fluid)	
	}	

		
	private def renderFluidManagement = { careActivity  ->
		
		def fluid = [id:careActivity?.fluidManagement?.id
			, versions:getVersions(careActivity)
			, litrePlusAt24:careActivity?.fluidManagement?.litrePlusAt24
			, inadequateAt24Reason:careActivity?.fluidManagement?.inadequateAt24FluidReasonType?.description
			, inadequateAt24ReasonOther:careActivity?.fluidManagement?.inadequateAt24ReasonOther
			, litrePlusAt48:careActivity?.fluidManagement?.litrePlusAt48
			, inadequateAt48Reason:careActivity?.fluidManagement?.inadequateAt48FluidReasonType?.description
			, inadequateAt48ReasonOther:careActivity?.fluidManagement?.inadequateAt48ReasonOther
			, litrePlusAt72:careActivity?.fluidManagement?.litrePlusAt72
			, inadequateAt72Reason:careActivity?.fluidManagement?.inadequateAt72FluidReasonType?.description
			, inadequateAt72ReasonOther:careActivity?.fluidManagement?.inadequateAt72ReasonOther
			, hoursSinceAdmission:null
			, periodSinceAdmission: null]
		
		if ( careActivity.startDate != null) {
			def admission = ((Date)careActivity.startDate).toCalendar();
			if(careActivity.startTime){
				admission.set( Calendar.HOUR_OF_DAY, (int)careActivity.startTime / 60)
				admission.set( Calendar.MINUTE ,careActivity.startTime % 60	)
			}else{
				admission.set( Calendar.HOUR_OF_DAY, 0)		
			}
			TimeDuration duration = TimeCategory.minus( new Date(), admission.time )
			def hoursSinceAdmission = (duration.days * 24) + (duration.hours) + (duration.minutes/60) 		
			fluid = [id:careActivity?.fluidManagement?.id
						, versions:getVersions(careActivity)
						, litrePlusAt24:careActivity?.fluidManagement?.litrePlusAt24
						, inadequateAt24Reason:careActivity?.fluidManagement?.inadequateAt24FluidReasonType?.description
						, inadequateAt24ReasonOther:careActivity?.fluidManagement?.inadequateAt24ReasonOther
						, litrePlusAt48:careActivity?.fluidManagement?.litrePlusAt48
						, inadequateAt48Reason:careActivity?.fluidManagement?.inadequateAt48FluidReasonType?.description
						, inadequateAt48ReasonOther:careActivity?.fluidManagement?.inadequateAt48ReasonOther
						, litrePlusAt72:careActivity?.fluidManagement?.litrePlusAt72
						, inadequateAt72Reason:careActivity?.fluidManagement?.inadequateAt72FluidReasonType?.description
						, inadequateAt72ReasonOther:careActivity?.fluidManagement?.inadequateAt72ReasonOther
						, hoursSinceAdmission:hoursSinceAdmission
						, periodSinceAdmission:getPeriod(duration)]
		}
		def result = [FluidManagement:fluid, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		render result as JSON		
		}
	
	
	private def renderFluidManagementWithErrors = { data, careActivity, errors  ->
		log.debug "In renderFluidManagementWithErrors"
		data.remove('originalData')
		errors.each{key, value ->
			log.debug "${key} :: ${value}"
			careActivity.errors.rejectValue(key, value, "Custom validation failed for ${key} in the controller")
		}
		def result = [FluidManagement:data, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
		}
}
