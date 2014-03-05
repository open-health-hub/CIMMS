package uk.ac.leeds.lihs.auecr.cimss.stroke


import grails.converters.JSON;
import org.codehaus.groovy.grails.web.json.JSONObject;

import groovy.time.TimeCategory;
import groovy.time.TimeDuration;

import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.RehabGoalsNotSetReasonType;


class NurseLedTherapyController  extends  StrokeBaseController{

    def getNurseLedTherapyPage = {
		log.debug "in getNurseLedTherapyPage"
		def careActivity = CareActivity.get(params.id)
		render(template:'/nurseLedTherapy/nurseLedTherapy',model:['careActivityInstance':careActivity])
	}

	def getNurseLedTherapy = {
		log.debug "in getNurseLedTherapy"
		def careActivity = CareActivity.get(params.id)
		renderNurseLedTherapy(careActivity);
	}
		
	def updateNurseLedTherapy = {
		log.info "in updateNurseLedTherapy -> ${request.JSON.therapyManagement}"
		def careActivity = CareActivity.get(params.id);
		def errors=[:]
		
		def data = request.JSON.therapyManagement
		
		
		if(changedSinceRetrieval(careActivity, data)){
			data.versions = getVersions(careActivity)
			careActivity.errors.rejectValue('version', 'version.changed.therapy')
			careActivity.discard()
			renderNurseLedTherapyWithErrors(data, careActivity, errors);
		}else{
			updateData(careActivity, data, errors)
			if(!errors & careActivity.validate()){
				careActivity.save(flush:true)
				renderNurseLedTherapy(careActivity);
			}else{
				careActivity.discard()
				renderNurseLedTherapyWithErrors(data, careActivity, errors);
			}
		}
		
		
		
		
		
	}
		
	private def updateData = {careActivity, data, errors ->
		if(!careActivity.therapyManagement){
			careActivity.therapyManagement = new TherapyManagement()
			careActivity.therapyManagement.careActivity = careActivity
		}
		updateNurseLedTherapyManagement(careActivity, data, errors)		
	}
	
	private def updateNurseLedTherapyManagement = { careActivity, data, errors ->
		
		careActivity.therapyManagement.rehabGoalsSet = getBooleanFromString(data.rehabGoalsSet)
		if(careActivity.therapyManagement.rehabGoalsSet == Boolean.TRUE){
			careActivity.therapyManagement.rehabGoalsSetDate = DisplayUtils.getDate(data.rehabGoalsSetDate , errors, "therapyManagement.rehabGoalsSetDate", "rehab.goals.date.invalid.format")
			careActivity.therapyManagement.rehabGoalsSetTime = DisplayUtils.getTime(data.rehabGoalsSetTime , errors, "therapyManagement.rehabGoalsSetTime", "rehab.goals.time.invalid.format")
			
			
			
		
			
			if(careActivity?.therapyManagement?.rehabGoalsSetDate){
			 
				def hoursTaken = careActivity.hoursSinceAdmission(
							careActivity.therapyManagement.rehabGoalsSetDate
						, careActivity.therapyManagement.rehabGoalsSetTime)
				
					
				if(hoursTaken > 72 ){
					if(data.rehabGoalsNotSetReason !="null"  && data.rehabGoalsNotSetReason!= JSONObject.NULL){
						careActivity.therapyManagement.rehabGoalsNotSetReasonType = RehabGoalsNotSetReasonType.findByDescription(data.rehabGoalsNotSetReason)
					}else{
						careActivity.therapyManagement.rehabGoalsNotSetReasonType = null;
					}
				} else if (careActivity.isBeforeAdmissionOrOnset(careActivity.therapyManagement.rehabGoalsSetDate
						, careActivity.therapyManagement.rehabGoalsSetTime)) {
					errors.put( "therapyManagement.rehabGoalsSetDate", "rehab.goals.date.before.onset.arrival")
				} else{
				
					careActivity.therapyManagement.rehabGoalsNotSetReasonType = null;
				}
			}
		
			
			
			
			
			
		}else if (careActivity.therapyManagement.rehabGoalsSet == Boolean.FALSE){
			if(data.rehabGoalsNotSetReason !="null"  && data.rehabGoalsNotSetReason!= JSONObject.NULL){
				careActivity.therapyManagement.rehabGoalsNotSetReasonType = RehabGoalsNotSetReasonType.findByDescription(data.rehabGoalsNotSetReason)
			}else{
				careActivity.therapyManagement.rehabGoalsNotSetReasonType = null;
			}
			careActivity.therapyManagement.rehabGoalsSetDate = null
			careActivity.therapyManagement.rehabGoalsSetTime = null
		}else{
			careActivity.therapyManagement.rehabGoalsNotSetReasonType = null;
			careActivity.therapyManagement.rehabGoalsSetDate = null
			careActivity.therapyManagement.rehabGoalsSetTime = null
		}
		
	}
	
	
	def private  changedSinceRetrieval = { careActivity, data ->
		if(careActivity?.therapyManagement?.version
			&& getValueFromString(data.versions.therapyManagement) !=null
			&& careActivity?.therapyManagement?.version >(long)data.versions.therapyManagement){
			if(careActivity.therapyManagement.rehabGoalsSet != getBooleanFromString(data.rehabGoalsSet)){
				return true;
			}else{
				if(careActivity.therapyManagement.rehabGoalsSet == Boolean.TRUE){
					if(careActivity.therapyManagement.rehabGoalsSetDate !=  DisplayUtils.getDate(data.rehabGoalsSetDate, [:], "", "")
					|| careActivity.therapyManagement.rehabGoalsSetTime != DisplayUtils.getTime(data.rehabGoalsSetTime, [:], "", "")){
						return true
					}
				}else{
					if(careActivity.therapyManagement?.rehabGoalsNotSetReasonType?.description != getValueFromString(data.rehabGoalsNotSetReason)){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	
	private def getVersions = { careActivity ->
		
		return ['careActivity':careActivity?.version
			,'therapyManagement':careActivity?.therapyManagement?.version
			,'occupationalTherapyManagement':careActivity?.therapyManagement?.occupationalTherapyManagement?.version
			,'speechAndLanguageTherapyManagement':careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.version
			,'physiotherapyManagement':careActivity?.therapyManagement?.physiotherapyManagement?.version
			,'assessmentManagement':careActivity?.therapyManagement?.assessmentManagement?.version
			,'modifiedRankin':careActivity?.therapyManagement?.assessmentManagement?.findModifiedRankinByStage("Baseline")?.version
			,'barthel':careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Baseline")?.version
			]
	}
	

	
	private def renderNurseLedTherapy = {careActivity ->
		
		
		
			
		def therapyManagement = [id:careActivity?.therapyManagement?.id
			, versions:getVersions(careActivity)
			, admissionDate:DisplayUtils.displayDate(careActivity?.startDate)
			, admissionTime:DisplayUtils.displayTime(careActivity?.startTime)
			, rehabGoalsSet:careActivity?.therapyManagement?.rehabGoalsSet.toString()
			, rehabGoalsSetDate:DisplayUtils.displayDate(careActivity?.therapyManagement?.rehabGoalsSetDate)
			, rehabGoalsSetTime:DisplayUtils.displayTime(careActivity?.therapyManagement?.rehabGoalsSetTime)
			, rehabGoalsNotSetReason: careActivity?.therapyManagement?.rehabGoalsNotSetReasonType?.description]
		def result = [therapyManagement:therapyManagement, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
	}
	
	private	def renderNurseLedTherapyWithErrors = {data, careActivity, errors ->
		log.debug "In renderNurseLedTherapyWithErrors"
		errors.each{key, value ->
			log.debug "Adding non domain error --> ${key} :: ${value}"
			careActivity.errors.reject(value,[key]as Object[], "Custom validation failed for ${key} in the controller".toString())
		}
		def result = [therapyManagement:data, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
	}
	

}
