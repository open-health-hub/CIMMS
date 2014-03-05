package uk.ac.leeds.lihs.auecr.cimss.stroke

import grails.converters.JSON;
import org.codehaus.groovy.grails.web.json.JSONObject;

import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.PhysiotherapyNoAssessmentReasonType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.therapy.PhysiotherapyManagement;

class PhysiotherapyController extends  StrokeBaseController{

	def getPhysiotherapyPage = {
		log.debug "in getPhysiotherapyPage"
		def careActivity = CareActivity.get(params.id)
		render(template:'/physiotherapy/physiotherapy',model:['careActivityInstance':careActivity])
	}

	def getPhysiotherapy = {
		log.debug "in getPhysiotherapy"
		def careActivity = CareActivity.get(params.id)
		renderPhysiotherapy(careActivity);
	}
		
	def updatePhysiotherapy = {
		log.info "in updatePhysiotherapy -> ${request.JSON.therapyManagement}"
		def careActivity = CareActivity.get(params.id);
		def errors=[:]
		
		def data = request.JSON.therapyManagement
		
		
		if(changedSinceRetrieval(careActivity, data)){
			data.versions = getVersions(careActivity)
			careActivity.errors.rejectValue('version', 'version.changed.therapy')
			careActivity.discard()
			renderPhysiotherapyWithErrors(data, careActivity, errors);
		}else{
			updateData(careActivity, data, errors)
			if(!errors & careActivity.validate()){
				careActivity.save(flush:true)
				renderPhysiotherapy(careActivity);
			}else{
				careActivity.discard()
				renderPhysiotherapyWithErrors(data, careActivity, errors);
			}
		}
		
		
		
		
		
	}
		
	private def updateData = {careActivity, data, errors ->
		if(!careActivity.therapyManagement){
			careActivity.therapyManagement = new TherapyManagement()
			careActivity.therapyManagement.careActivity = careActivity
		}
		updatePhysiotherapyManagement(careActivity.therapyManagement, data.physiotherapyManagement, errors)		
	}
	
	private def updatePhysiotherapyManagement = { therapyManagement, data, errors ->
		if(!therapyManagement.physiotherapyManagement){
			therapyManagement.physiotherapyManagement = new PhysiotherapyManagement()
			therapyManagement.physiotherapyManagement.therapyManagement = therapyManagement
		}
		
		therapyManagement.physiotherapyManagement.assessmentPerformedIn72Hrs = getBooleanFromString(data.physioAssessmentPerformedIn72Hrs);
		if ( therapyManagement.physiotherapyManagement.assessmentPerformedIn72Hrs == Boolean.TRUE ) {
			therapyManagement.physiotherapyManagement.assessmentPerformed = Boolean.TRUE;
			therapyManagement.physiotherapyManagement.no72HrAssessmentReasonType = null;
			therapyManagement.physiotherapyManagement.noAssessmentReasonType = null;
		}
		else {
			therapyManagement.physiotherapyManagement.assessmentPerformed = getBooleanFromString(data.physioAssessmentPerformed)
		}
		
		if(therapyManagement.physiotherapyManagement.assessmentPerformed == Boolean.TRUE){
			therapyManagement.physiotherapyManagement.assessmentDate = DisplayUtils.getDate(data.physioAssessmentDate, errors, "physiotherapyManagement.assessmentDate", "assessment.date.invalid.format")
			therapyManagement.physiotherapyManagement.assessmentTime = DisplayUtils.getTime(data.physioAssessmentTime, errors, "physiotherapyManagement.assessmentTime", "assessment.time.invalid.format")
		}
		
		if ( therapyManagement.physiotherapyManagement.assessmentPerformedIn72Hrs == Boolean.FALSE ) {
			if(data.physioNo72HrAssessmentReason !="null"  && data.physioNo72HrAssessmentReason!= JSONObject.NULL && !data.isNull('physioNo72HrAssessmentReason')){
				therapyManagement.physiotherapyManagement.no72HrAssessmentReasonType  = PhysiotherapyNoAssessmentReasonType.findByDescription(data.physioNo72HrAssessmentReason)
			}else{
				therapyManagement.physiotherapyManagement.no72HrAssessmentReasonType = null
			}
		}
		if ( therapyManagement.physiotherapyManagement.assessmentPerformed == Boolean.FALSE ) {
			
			if(data.physioNoAssessmentReason !="null"  && data.physioNoAssessmentReason!= JSONObject.NULL && !data.isNull('physioNoAssessmentReason') ){
				therapyManagement.physiotherapyManagement.noAssessmentReasonType  = PhysiotherapyNoAssessmentReasonType.findByDescription(data.physioNoAssessmentReason)
			}
			else{
				therapyManagement.physiotherapyManagement.noAssessmentReasonType = null
			}
		}
		

	}
			
	def private  changedSinceRetrieval = { careActivity, data ->
		if(careActivity?.therapyManagement?.physiotherapyManagement?.version!=null
			&& data.versions.physiotherapyManagement == JSONObject.NULL){
			return true
		}
				
		if(careActivity?.therapyManagement?.physiotherapyManagement?.version
				&& careActivity?.therapyManagement?.physiotherapyManagement?.version >(long)data.versions.physiotherapyManagement){
			return true;
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
		
	private def renderPhysiotherapy = {careActivity ->
		
		
		
		def physiotherapyManagement = [id:careActivity?.therapyManagement?.physiotherapyManagement?.id
			, physioAssessmentPerformed:careActivity?.therapyManagement?.physiotherapyManagement?.assessmentPerformed.toString()
			, physioAssessmentPerformedIn72Hrs:careActivity?.therapyManagement?.physiotherapyManagement?.assessmentPerformedIn72Hrs.toString()
			, physioAssessmentDate:DisplayUtils.displayDate(careActivity?.therapyManagement?.physiotherapyManagement?.assessmentDate)
			, physioAssessmentTime:DisplayUtils.displayTime(careActivity?.therapyManagement?.physiotherapyManagement?.assessmentTime)
			, physioNoAssessmentReason:careActivity?.therapyManagement?.physiotherapyManagement?.noAssessmentReasonType?.description
			, physioNo72HrAssessmentReason:careActivity?.therapyManagement?.physiotherapyManagement?.no72HrAssessmentReasonType?.description]

		
		def therapyManagement = [id:careActivity?.therapyManagement?.id
			, versions:getVersions(careActivity)
			, admissionDate:DisplayUtils.displayDate(careActivity?.getEffectiveStartDate())
			, admissionTime:DisplayUtils.displayTime(careActivity?.getEffectiveStartTime())
			, physiotherapyManagement:physiotherapyManagement]
		def result = [therapyManagement:therapyManagement, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
	}
	
	private	def renderPhysiotherapyWithErrors = {data, careActivity, errors ->
		log.debug "In renderPhysiotherapyWithErrors"
		errors.each{key, value ->
			log.debug "Adding non domain error --> ${key} :: ${value}"
			careActivity.errors.reject(value,[key]as Object[], "Custom validation failed for ${key} in the controller".toString())
		}
		def result = [therapyManagement:data, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
	}
	
	
}
