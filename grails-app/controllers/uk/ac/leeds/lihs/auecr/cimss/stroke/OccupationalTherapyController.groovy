package uk.ac.leeds.lihs.auecr.cimss.stroke

import grails.converters.JSON;
import org.codehaus.groovy.grails.web.json.JSONObject;

import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.CognitiveStatusNoAssessmentType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.OccupationalTherapyNoAssessmentReasonType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.therapy.OccupationalTherapyManagement;

class OccupationalTherapyController extends  StrokeBaseController{

   
	def getOccupationalTherapyPage = {
		log.debug "in getOccupationalTherapyPage"
		def careActivity = CareActivity.get(params.id)
		render(template:'/occupationalTherapy/occupationalTherapy',model:['careActivityInstance':careActivity])
	}

	def getOccupationalTherapy = {
		log.debug "in getOccupationalTherapy"
		def careActivity = CareActivity.get(params.id)
		renderOccupationalTherapy(careActivity);
	}
	
	
	def updateOccupationalTherapy = {
		log.info "in updateOccupationalTherapy -> ${request.JSON.therapyManagement}"
		def careActivity = CareActivity.get(params.id);
		def errors=[:]
		
		def data = request.JSON.therapyManagement
		
		
		if(changedSinceRetrieval(careActivity, data)){
			data.versions = getVersions(careActivity)
			careActivity.errors.rejectValue('version', 'version.changed.therapy')
			careActivity.discard()
			renderOccupationalTherapyWithErrors(data, careActivity, errors);
		}else{
			updateData(careActivity, data, errors)
			if(!errors & careActivity.validate()){
				careActivity.save(flush:true)
				renderOccupationalTherapy(careActivity);
			}else{
				careActivity.discard()
				renderOccupationalTherapyWithErrors(data, careActivity, errors);
			}
		}
		
		
		
		
		
	}
	
	
	private def updateData = {careActivity, data, errors ->
		if(!careActivity.therapyManagement){
			careActivity.therapyManagement = new TherapyManagement()
			careActivity.therapyManagement.careActivity = careActivity
		}
		updateOccupationalTherapyManagement(careActivity.therapyManagement, data.occupationalTherapyManagement, errors)
		updateCognitiveStatusData(careActivity, data, errors)
		
	}
	
	private def updateOccupationalTherapyManagement = { therapyManagement, data, errors ->
		if(!therapyManagement.occupationalTherapyManagement){
			therapyManagement.occupationalTherapyManagement = new OccupationalTherapyManagement()
			therapyManagement.occupationalTherapyManagement.therapyManagement = therapyManagement
		}
		
		therapyManagement.occupationalTherapyManagement.assessmentPerformedIn72Hrs = getBooleanFromString(data.assessmentPerformedIn72Hrs);
		if ( therapyManagement.occupationalTherapyManagement.assessmentPerformedIn72Hrs == Boolean.TRUE ) {
			therapyManagement.occupationalTherapyManagement.assessmentPerformed = Boolean.TRUE;
			therapyManagement.occupationalTherapyManagement.no72HrAssessmentReasonType = null;
			therapyManagement.occupationalTherapyManagement.noAssessmentReasonType = null;
		}
		else {
			therapyManagement.occupationalTherapyManagement.assessmentPerformed = getBooleanFromString(data.assessmentPerformed)
		}
		
		if(therapyManagement.occupationalTherapyManagement.assessmentPerformed == Boolean.TRUE){
			therapyManagement.occupationalTherapyManagement.assessmentDate = DisplayUtils.getDate(data.assessmentDate, errors, "occupationalTherapyManagement.assessmentDate", "assessment.date.invalid.format")
			therapyManagement.occupationalTherapyManagement.assessmentTime = DisplayUtils.getTime(data.assessmentTime, errors, "occupationalTherapyManagement.assessmentTime", "assessment.time.invalid.format")
		}
		
		if ( therapyManagement.occupationalTherapyManagement.assessmentPerformedIn72Hrs == Boolean.FALSE ) {
			if(data.otTherapyNo72HrAssessmentReason !="null"  && data.otTherapyNo72HrAssessmentReason!= JSONObject.NULL && !data.isNull('otTherapyNo72HrAssessmentReason')){
				therapyManagement.occupationalTherapyManagement.no72HrAssessmentReasonType  = OccupationalTherapyNoAssessmentReasonType.findByDescription(data.otTherapyNo72HrAssessmentReason)
			}else{
				therapyManagement.occupationalTherapyManagement.no72HrAssessmentReasonType = null
			}
		}
		if ( therapyManagement.occupationalTherapyManagement.assessmentPerformed == Boolean.FALSE ) {
			
			if(data.otTherapyNoAssessmentReason !="null"  && data.otTherapyNoAssessmentReason!= JSONObject.NULL && !data.isNull('otTherapyNoAssessmentReason') ){
				therapyManagement.occupationalTherapyManagement.noAssessmentReasonType  = OccupationalTherapyNoAssessmentReasonType.findByDescription(data.otTherapyNoAssessmentReason)
			}
			else{
				therapyManagement.occupationalTherapyManagement.noAssessmentReasonType = null
			}
		}
		// ---

		
		// --
		therapyManagement.occupationalTherapyManagement.moodAssessmentPerformed  = getBooleanFromString(data.moodAssessmentPerformed)
		if(therapyManagement.occupationalTherapyManagement.moodAssessmentPerformed == Boolean.TRUE){
			therapyManagement.occupationalTherapyManagement.moodAssessmentDate = DisplayUtils.getDate(data.moodAssessmentDate, errors, "occupationalTherapyManagement.moodAssessmentDate", "mood.assessment.date.invalid.format")
			therapyManagement.occupationalTherapyManagement.moodAssessmentTime = DisplayUtils.getTime(data.moodAssessmentTime, errors, "occupationalTherapyManagement.moodAssessmentTime", "mood.assessment.time.invalid.format")
			therapyManagement.occupationalTherapyManagement.noMoodAssessmentReasonType = null
			
		}else{
			
			if(data.noMoodAssessmentReason !="null"  && data.noMoodAssessmentReason!= JSONObject.NULL){
				therapyManagement.occupationalTherapyManagement.noMoodAssessmentReasonType  = OccupationalTherapyNoAssessmentReasonType.findByDescription(data.noMoodAssessmentReason)
			}else{
				therapyManagement.occupationalTherapyManagement.noMoodAssessmentReasonType  = null;
			}
			therapyManagement.occupationalTherapyManagement.moodAssessmentDate = null
			therapyManagement.occupationalTherapyManagement.moodAssessmentTime = null
		}
	}
		
	private def updateCognitiveStatusData = { careActivity, data, errors ->
		careActivity.therapyManagement.cognitiveStatusAssessed = getBooleanFromString(data.cognitiveStatusAssessed)
		if(careActivity.therapyManagement.cognitiveStatusAssessed == Boolean.TRUE){
			careActivity.therapyManagement.cognitiveStatusAssessmentDate = DisplayUtils.getDate(data.cognitiveStatusAssessmentDate, errors, "therapyManagement.cognitiveStatusAssessmentDate", "cognitive.assessment.date.invalid.format")
			careActivity.therapyManagement.cognitiveStatusAssessmentTime = DisplayUtils.getTime(data.cognitiveStatusAssessmentTime, errors, "therapyManagement.cognitiveStatusAssessmentTime", "cognitive.assessment.time.invalid.format")
			careActivity.therapyManagement.cognitiveStatusNoAssessmentType = null;
		}else if (careActivity.therapyManagement.cognitiveStatusAssessed == Boolean.FALSE){
			if(data.cognitiveStatusNoAssessmentReason !="null" & data.cognitiveStatusNoAssessmentReason!= JSONObject.NULL){
				careActivity.therapyManagement.cognitiveStatusNoAssessmentType = CognitiveStatusNoAssessmentType.findByDescription(data.cognitiveStatusNoAssessmentReason)
			}else{
				careActivity.therapyManagement.cognitiveStatusNoAssessmentType = null;
			}
			careActivity.therapyManagement.cognitiveStatusAssessmentDate = null
			careActivity.therapyManagement.cognitiveStatusAssessmentTime = null
		}else{
			careActivity.therapyManagement.cognitiveStatusNoAssessmentType = null;
			careActivity.therapyManagement.cognitiveStatusAssessmentDate = null
			careActivity.therapyManagement.cognitiveStatusAssessmentTime = null
			
		}
		
		
	}
	
	
	def private  changedSinceRetrieval = { careActivity, data ->
		return occupationalTherapyManagementHasChanged(careActivity, data ) || 
				cognitiveStatusAssessmentHasChanged(careActivity, data );
	}

	def private cognitiveStatusAssessmentHasChanged = {careActivity, data ->
		if(careActivity?.therapyManagement?.version
			&& careActivity?.therapyManagement?.version >(long)data.versions.therapyManagement){
			if(careActivity.therapyManagement.cognitiveStatusAssessed != getBooleanFromString(data.cognitiveStatusAssessed)){
				return true;
			}else{
				if(careActivity.therapyManagement.cognitiveStatusAssessed == Boolean.TRUE){
					if(careActivity.therapyManagement.cognitiveStatusAssessmentDate !=  DisplayUtils.getDate(data.cognitiveStatusAssessmentDate, [:], "", "")
					|| careActivity.therapyManagement.cognitiveStatusAssessmentTime != DisplayUtils.getTime(data.cognitiveStatusAssessmentTime, [:], "", "")){
						return true
					}
				}else{
					if(careActivity.therapyManagement?.cognitiveStatusNoAssessmentType?.description != getValueFromString(data.cognitiveStatusNoAssessmentReason)){
						return true;
					}
				}
			}
		}
		return false;
	}

	def private  occupationalTherapyManagementHasChanged = {careActivity, data ->
		
		if(careActivity?.therapyManagement?.occupationalTherapyManagement?.version!=null
		&& data.versions.occupationalTherapyManagement == JSONObject.NULL){
			return true
		}

		if(careActivity?.therapyManagement?.occupationalTherapyManagement?.version
		&& careActivity?.therapyManagement?.occupationalTherapyManagement?.version >(long)data.versions.occupationalTherapyManagement){
			return true
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
	
	
	
	
	
	private def renderOccupationalTherapy = {careActivity ->
		
		
		
		def occupationalTherapyManagement = [id:careActivity?.therapyManagement?.occupationalTherapyManagement?.id
		   						, assessmentPerformed:careActivity?.therapyManagement?.occupationalTherapyManagement?.assessmentPerformed.toString()
								, assessmentPerformedIn72Hrs:careActivity?.therapyManagement?.occupationalTherapyManagement?.assessmentPerformedIn72Hrs.toString()
								, assessmentDate:DisplayUtils.displayDate(careActivity?.therapyManagement?.occupationalTherapyManagement?.assessmentDate)
								, assessmentTime:DisplayUtils.displayTime(careActivity?.therapyManagement?.occupationalTherapyManagement?.assessmentTime)
								, otTherapyNoAssessmentReason:careActivity?.therapyManagement?.occupationalTherapyManagement?.noAssessmentReasonType?.description
								, otTherapyNo72HrAssessmentReason:careActivity?.therapyManagement?.occupationalTherapyManagement?.no72HrAssessmentReasonType?.description
								, moodAssessmentPerformed:careActivity?.therapyManagement?.occupationalTherapyManagement?.moodAssessmentPerformed?.toString()
								, moodAssessmentDate:DisplayUtils.displayDate(careActivity?.therapyManagement?.occupationalTherapyManagement?.moodAssessmentDate)
								, moodAssessmentTime:DisplayUtils.displayTime(careActivity?.therapyManagement?.occupationalTherapyManagement?.moodAssessmentTime)
								, noMoodAssessmentReason:careActivity?.therapyManagement?.occupationalTherapyManagement?.noMoodAssessmentReasonType?.description]


		
		def therapyManagement = [id:careActivity?.therapyManagement?.id
			, versions:getVersions(careActivity)
			, admissionDate:DisplayUtils.displayDate(careActivity?.getEffectiveStartDate())
			, admissionTime:DisplayUtils.displayTime(careActivity?.getEffectiveStartTime())
			, occupationalTherapyManagement:occupationalTherapyManagement
			, cognitiveStatusAssessed:careActivity?.therapyManagement?.cognitiveStatusAssessed.toString()
			, cognitiveStatusAssessmentDate:DisplayUtils.displayDate(careActivity?.therapyManagement?.cognitiveStatusAssessmentDate)
			, cognitiveStatusAssessmentTime:DisplayUtils.displayTime(careActivity?.therapyManagement?.cognitiveStatusAssessmentTime)
			, cognitiveStatusNoAssessmentReason:careActivity?.therapyManagement?.cognitiveStatusNoAssessmentType?.description]
		def result = [therapyManagement:therapyManagement, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
	}
	
	private	def renderOccupationalTherapyWithErrors = {data, careActivity, errors ->
		log.debug "In renderOccupationalTherapyWithErrors"
		errors.each{key, value ->
			log.debug "Adding non domain error --> ${key} :: ${value}"
			careActivity.errors.reject(value,[key]as Object[], "Custom validation failed for ${key} in the controller".toString())
		}
		def result = [therapyManagement:data, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
	}
	
}
