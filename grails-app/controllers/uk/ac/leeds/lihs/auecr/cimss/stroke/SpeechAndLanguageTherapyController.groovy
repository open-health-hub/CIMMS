package uk.ac.leeds.lihs.auecr.cimss.stroke

import grails.converters.JSON;
import org.codehaus.groovy.grails.web.json.JSONObject;

import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.CommunicationNoAssessmentReasonType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.SwallowingNoAssessmentReasonType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.therapy.SpeechAndLanguageTherapyManagement;

class SpeechAndLanguageTherapyController extends  StrokeBaseController{

	def getSpeechAndLanguageTherapyPage = {
		log.debug "in getSpeechAndLanguageTherapyPage"
		def careActivity = CareActivity.get(params.id)
		render(template:'/speechAndLanguageTherapy/speechAndLanguageTherapy',model:['careActivityInstance':careActivity])
	}

	def getSpeechAndLanguageTherapy = {
		log.debug "in getSpeechAndLanguageTherapy"
		def careActivity = CareActivity.get(params.id)
		renderSpeechAndLanguageTherapy(careActivity);
	}
		
	def updateSpeechAndLanguageTherapy = {
		log.info "in updateSpeechAndLanguageTherapy -> ${request.JSON.therapyManagement}"
		def careActivity = CareActivity.get(params.id);
		def errors=[:]
		
		def data = request.JSON.therapyManagement
		
		
		
		
		if(changedSinceRetrieval(careActivity, data)){
			data.versions = getVersions(careActivity)
			careActivity.errors.rejectValue('version', 'version.changed.therapy')
			careActivity.discard()
			renderSpeechAndLanguageTherapyWithErrors(data, careActivity, errors);
		}else{
			updateData(careActivity, data, errors)
			if(!errors & careActivity.validate()){
				careActivity.save(flush:true)
				renderSpeechAndLanguageTherapy(careActivity);
			}else{
				careActivity.discard()
				renderSpeechAndLanguageTherapyWithErrors(data, careActivity, errors);
			}
		}
		
		
		
		
		
	}
	
	
	private def updateData = {careActivity, data, errors ->
		if(!careActivity.therapyManagement){
			careActivity.therapyManagement = new TherapyManagement()
			careActivity.therapyManagement.careActivity = careActivity
		}
		updateSpeechAndLanguageTherapyManagement(careActivity.therapyManagement, data.speechAndLanguageTherapyManagement, errors)
	}
	
	private def getSpeechAndLanguageTherapyManagement = {therapyManagement ->
		if(!therapyManagement.speechAndLanguageTherapyManagement){
			therapyManagement.speechAndLanguageTherapyManagement = new SpeechAndLanguageTherapyManagement()
			therapyManagement.speechAndLanguageTherapyManagement.therapyManagement = therapyManagement
		}
		return therapyManagement.speechAndLanguageTherapyManagement
	}
	
	private def updateSpeechAndLanguageTherapyManagement = { therapyManagement, data, errors ->
		def speechAndLanguageTherapyManagement = getSpeechAndLanguageTherapyManagement(therapyManagement);
		
		speechAndLanguageTherapyManagement.communicationAssessmentPerformedIn72Hrs = getBooleanFromString(data.communicationAssessmentPerformedIn72Hrs);
		if ( speechAndLanguageTherapyManagement.communicationAssessmentPerformedIn72Hrs == Boolean.TRUE ) {
			speechAndLanguageTherapyManagement.communicationAssessmentPerformed = Boolean.TRUE;
			speechAndLanguageTherapyManagement.noCommunicationAssessmentReasonType = null;
			speechAndLanguageTherapyManagement.no72HrCommunicationAssessmentReasonType = null;
		}
		else {
			speechAndLanguageTherapyManagement.communicationAssessmentPerformed = getBooleanFromString(data.communicationAssessmentPerformed)
		}
		
		if(speechAndLanguageTherapyManagement.communicationAssessmentPerformed == Boolean.TRUE){
			speechAndLanguageTherapyManagement.communicationAssessmentDate = DisplayUtils.getDate(data.communicationAssessmentDate, errors, "speechAndLanguageTherapyManagement.communicationAssessmentDate", "speech.and.language.communication.assessment.date.invalid.format")
			speechAndLanguageTherapyManagement.communicationAssessmentTime = DisplayUtils.getTime(data.communicationAssessmentTime , errors, "speechAndLanguageTherapyManagement.communicationAssessmentTime", "speech.and.language.communication.assessment.time.invalid.format")
		}
		
		if ( speechAndLanguageTherapyManagement.communicationAssessmentPerformedIn72Hrs == Boolean.FALSE ) {
			if(data.communication72HrNoAssessmentReason !="null"  && data.communication72HrNoAssessmentReason!= JSONObject.NULL && !data.isNull('communication72HrNoAssessmentReason')){
				speechAndLanguageTherapyManagement.no72HrCommunicationAssessmentReasonType  = CommunicationNoAssessmentReasonType.findByDescription(data.communication72HrNoAssessmentReason)
			}else{
					speechAndLanguageTherapyManagement.no72HrSwallowingAssessmentReasonType = null
			}
		}
		if ( speechAndLanguageTherapyManagement.communicationAssessmentPerformed == Boolean.FALSE ) {
			
			if(data.communicationNoAssessmentReason !="null"  && data.communicationNoAssessmentReason!= JSONObject.NULL && !data.isNull('communicationNoAssessmentReason') ){
				speechAndLanguageTherapyManagement.noCommunicationAssessmentReasonType  = CommunicationNoAssessmentReasonType.findByDescription(data.communicationNoAssessmentReason)
			}
			else{
				speechAndLanguageTherapyManagement.noCommunicationAssessmentReasonType = null
			}
		}

		
		// ------
		
		speechAndLanguageTherapyManagement.swallowingAssessmentPerformedIn72Hrs = getBooleanFromString(data.swallowingAssessmentPerformedIn72Hrs);
		if ( speechAndLanguageTherapyManagement.swallowingAssessmentPerformedIn72Hrs == Boolean.TRUE ) {
			speechAndLanguageTherapyManagement.swallowingAssessmentPerformed = Boolean.TRUE;
			speechAndLanguageTherapyManagement.no72HrSwallowingAssessmentReasonType = null;
			speechAndLanguageTherapyManagement.noSwallowingAssessmentReasonType = null;
		}
		else {
			if ( data.swallowing72HrNoAssessmentReason ) {
				speechAndLanguageTherapyManagement.no72HrSwallowingAssessmentReasonType  = SwallowingNoAssessmentReasonType.findByDescription(data.swallowing72HrNoAssessmentReason);
			}
			
			if (speechAndLanguageTherapyManagement.no72HrSwallowingAssessmentReasonType?.description == "passedswallowscreen") {
				speechAndLanguageTherapyManagement.swallowingAssessmentPerformed = Boolean.FALSE;
				speechAndLanguageTherapyManagement.swallowingAssessmentDate = null;
				speechAndLanguageTherapyManagement.swallowingAssessmentTime = null;
				speechAndLanguageTherapyManagement.noSwallowingAssessmentReasonType = null;
				return;
			} else {
				speechAndLanguageTherapyManagement.swallowingAssessmentPerformed = getBooleanFromString(data.swallowingAssessmentPerformed)
			}
		}
		
		if(speechAndLanguageTherapyManagement.swallowingAssessmentPerformed == Boolean.TRUE){
			speechAndLanguageTherapyManagement.swallowingAssessmentDate = DisplayUtils.getDate(data.swallowingAssessmentDate, errors, "speechAndLanguageTherapyManagement.swallowingAssessmentDate", "speech.and.language.swallowing.assessment.date.invalid.format")
			speechAndLanguageTherapyManagement.swallowingAssessmentTime = DisplayUtils.getTime(data.swallowingAssessmentTime, errors, "speechAndLanguageTherapyManagement.swallowingAssessmentTime", "speech.and.language.swallowing.assessment.time.invalid.format")
		}
		
		if ( speechAndLanguageTherapyManagement.swallowingAssessmentPerformedIn72Hrs == Boolean.FALSE ) {
			if(data.swallowing72HrNoAssessmentReason !="null"  && data.swallowing72HrNoAssessmentReason!= JSONObject.NULL && !data.isNull('swallowing72HrNoAssessmentReason')){
				speechAndLanguageTherapyManagement.no72HrSwallowingAssessmentReasonType  = SwallowingNoAssessmentReasonType.findByDescription(data.swallowing72HrNoAssessmentReason)
			}else{
					speechAndLanguageTherapyManagement.no72HrSwallowingAssessmentReasonType = null
			}
		}
		if ( speechAndLanguageTherapyManagement.swallowingAssessmentPerformed == Boolean.FALSE ) {
			
			if(data.swallowingNoAssessmentReason !="null"  && data.swallowingNoAssessmentReason!= JSONObject.NULL && !data.isNull('swallowingNoAssessmentReason') ){
				speechAndLanguageTherapyManagement.noSwallowingAssessmentReasonType  = SwallowingNoAssessmentReasonType.findByDescription(data.swallowingNoAssessmentReason)
			}
			else{
				speechAndLanguageTherapyManagement.noSwallowingAssessmentReasonType = null
			}
		}
		
			
	}
		
	
	
	def private  changedSinceRetrieval = { careActivity, data ->
		if(careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.version!=null
			&& data.versions.speechAndLanguageTherapyManagement == JSONObject.NULL){
			return true
		}
		if(careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.version
				&& careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.version >(long)data.versions.speechAndLanguageTherapyManagement){
			def currentData = getSpeechAndLanguageTherapyManagementDataAsMap(careActivity)
			def isSame = false
			currentData.each{ k, v->
				if(data.speechAndLanguageTherapyManagement[k] != v){
					log.debug "In SpeechAndLanguageTherapyController.changedSinceRetrieval ${k} ${data.speechAndLanguageTherapyManagement[k]} : ${v}"
					isSame = true
				}
			}
			return isSame
		}		
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
	
	
	
	
	
	private def renderSpeechAndLanguageTherapy = {careActivity ->
		
	
		def therapyManagement = [id:careActivity?.therapyManagement?.id
			, versions:getVersions(careActivity)
			, admissionDate:DisplayUtils.displayDate(careActivity?.getEffectiveStartDate())
			, admissionTime:DisplayUtils.displayTime(careActivity?.getEffectiveStartTime())
			, speechAndLanguageTherapyManagement:getSpeechAndLanguageTherapyManagementDataAsMap(careActivity)]
		def result = [therapyManagement:therapyManagement, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
	}

	private Map getSpeechAndLanguageTherapyManagementDataAsMap(careActivity) {
		def speechAndLanguageTherapyManagement = [id:careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.id
					, communicationAssessmentPerformed:careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.communicationAssessmentPerformed.toString()
					, communicationAssessmentPerformedIn72Hrs:careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.communicationAssessmentPerformedIn72Hrs.toString()
					, communicationAssessmentDate:DisplayUtils.displayDate(careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.communicationAssessmentDate)
					, communicationAssessmentTime:DisplayUtils.displayTime(careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.communicationAssessmentTime)
					, communicationNoAssessmentReason:careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.noCommunicationAssessmentReasonType?.description
					, communication72HrNoAssessmentReason:careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.no72HrCommunicationAssessmentReasonType?.description
					, swallowingAssessmentPerformed:careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.swallowingAssessmentPerformed.toString()
					, swallowingAssessmentPerformedIn72Hrs:careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.swallowingAssessmentPerformedIn72Hrs.toString()
					, swallowingAssessmentDate:DisplayUtils.displayDate(careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.swallowingAssessmentDate)
					, swallowingAssessmentTime:DisplayUtils.displayTime(careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.swallowingAssessmentTime)
					, swallowingNoAssessmentReason:careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.noSwallowingAssessmentReasonType?.description
					, swallowing72HrNoAssessmentReason:careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.no72HrSwallowingAssessmentReasonType?.description]
		return speechAndLanguageTherapyManagement
	}
	
	private	def renderSpeechAndLanguageTherapyWithErrors = {data, careActivity, errors ->
		log.debug "In renderSpeechAndLanguageTherapyWithErrors"
		errors.each{key, value ->
			log.debug "Adding non domain error --> ${key} :: ${value}"
			careActivity.errors.reject(value,[key]as Object[], "Custom validation failed for ${key} in the controller".toString())
		}
		def result = [therapyManagement:data, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
	}
	

}
