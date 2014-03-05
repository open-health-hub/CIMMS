package uk.ac.leeds.lihs.auecr.cimss.stroke

import grails.converters.JSON;

import org.apache.commons.validator.ISBNValidator;
import org.codehaus.groovy.grails.web.json.JSONObject;

import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.ComorbidityType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.therapy.AssessmentManagement;


class PreMorbidAssessmentController extends StrokeBaseController{

   def getPreMorbidAssessmentPage = {
		log.debug "in getPreMorbidAssessmentPage"
		def careActivity = CareActivity.get(params.id)
		render(template:'/admission/pre_morbid_assessment',model:['careActivityInstance':careActivity])
	}

	def getPreMorbidAssessment = {
		log.debug "in getPreMorbidAssessment"
		def careActivity = CareActivity.get(params.id);
		renderPreMorbidAssessment(careActivity);
	}
	
	def updatePreMorbidAssessment = {
		log.info "in updatePreMorbidAssessment -> ${request.JSON.AdmissionDetails}"
		def careActivity = CareActivity.get(params.id)
		def errors = [:]
		def data = request.JSON.AdmissionDetails
		 
		if(changedSinceRetrieval(careActivity, data)){
			data.versions = getVersions(careActivity)
			data.skipBruteForce = true
			careActivity.errors.rejectValue('version', 'version.changed.onset.admission')
			careActivity.discard()
			renderPreMorbidAssessmentWithErrors(data, careActivity, errors);
		}else{
			updateData(careActivity, data, errors)
			if(careActivity.validate() & !errors){
					careActivity.save(flush:true)
					renderPreMorbidAssessment(careActivity);
			}else{
					careActivity.discard()
					renderPreMorbidAssessmentWithErrors(data, careActivity, errors);
			}
		}
	}
	
	private def getVersions = { careActivity ->
		return ['careActivity':careActivity?.version
			,'medicalHistory':careActivity?.medicalHistory?.version
			,'patientLifeStyle':careActivity?.patientLifeStyle?.version
			, 'evaluations':getEvaluationVersions(careActivity)]
		
	}
	
	
	private def getEvaluationVersions = { careActivity ->
		def result = [:]
		careActivity?.evaluations.each {evaluation ->
			result.put(evaluation.id, evaluation.version)
		} 
		return result		
    }
	
	private def changedSinceRetrieval = { careActivity, data ->	
		return false
	}
	
	
	
	private def renderPreMorbidAssessment = {careActivity ->
		log.trace "In renderPreMorbidAssessment"
		
		def modifiedRankin = [id:""
			, modifiedRankinScore:""]
		if(careActivity?.therapyManagement?.assessmentManagement?.findModifiedRankinByStage("Pre-admission")){
			modifiedRankin.id = careActivity?.therapyManagement?.assessmentManagement?.findModifiedRankinByStage("Pre-admission").id
			modifiedRankin.modifiedRankinScore =  careActivity?.therapyManagement?.assessmentManagement?.findModifiedRankinByStage("Pre-admission").modifiedRankinScore
		}
 
		def barthel = [id:null
			, manualTotal:null
			, bowels:null
			, bladder:null
			, grooming:null
			, toilet:null
			, feeding:null
			, transfer:null
			, mobility:null
			, dressing:null
			, stairs:null
			, bathing:null ]
			  
		
		
		if(careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Pre-admission")){
			barthel.id = careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Pre-admission")?.id
			barthel.bowels =  careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Pre-admission")?.bowels
			barthel.bladder =  careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Pre-admission")?.bladder
			barthel.grooming =  careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Pre-admission")?.grooming
			barthel.toilet =  careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Pre-admission")?.toilet
			barthel.feeding =  careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Pre-admission")?.feeding
			barthel.transfer =  careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Pre-admission")?.transfer
			barthel.mobility =  careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Pre-admission")?.mobility
			barthel.dressing =  careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Pre-admission")?.dressing
			barthel.stairs =  careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Pre-admission")?.stairs
			barthel.bathing =  careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Pre-admission")?.bathing
			barthel.manualTotal =  careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Pre-admission")?.manualTotal
		}
		
		def barthelNotKnown = careActivity?.therapyManagement?.assessmentManagement?.preAdmissionBarthelNotKnown
		if ( !isComprehensive() ) {
			barthelNotKnown = 0
		}
		
		def assessments = [id:careActivity?.therapyManagement?.assessmentManagement?.id
									, barthelNotKnown: barthelNotKnown 
									, modifiedRankinNotKnown:false
									, barthel:barthel
									, modifiedRankin:modifiedRankin]
		
			
		
		def preMorbidAssessment = [id:careActivity
									 , versions:getVersions(careActivity)
									 , assessments:assessments]
		
		
		
		
		def admissionDetails = [
			versions:getVersions(careActivity)
			, preMorbidAssessment:preMorbidAssessment]
			
		
				
		def result = [AdmissionDetails:admissionDetails
						, FieldsInError: getFieldsInError(careActivity)
					    , ErrorsAsList:getErrorsAsList(careActivity)
					    , InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
			
	}



	private def renderPreMorbidAssessmentWithErrors = {data, careActivity, errors ->
		log.debug "In renderPreMorbidAssessmentWithErrors"
		errors.each{key, value ->
			log.debug "Adding non domain error --> ${key} :: ${value}"
			careActivity.errors.reject(value,[key]as Object[], "Custom validation failed for ${key} in the controller".toString())
		}
		def result = [AdmissionDetails:data, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
	}
		
	private def updateData = {careActivity, data, errors ->
		if(!careActivity.therapyManagement){
			careActivity.therapyManagement = new TherapyManagement()
			careActivity.therapyManagement.careActivity = careActivity
		}
		
		updateAssessmentManagement(careActivity.therapyManagement, data.preMorbidAssessment.assessments, errors)
		
		
	}
	
	
	private def updateAssessmentManagement = { therapyManagement, data, errors ->
		ensureAssessmentManagementExists(therapyManagement)
		processBarthel(therapyManagement, data)
		processModifiedRankin(therapyManagement, data)
	}

	private ensureAssessmentManagementExists(therapyManagement) {
		
		
		
		if(!therapyManagement?.assessmentManagement){
			therapyManagement.assessmentManagement = new AssessmentManagement()
			therapyManagement.assessmentManagement.therapyManagement = therapyManagement
		}
	}
	
	private processModifiedRankin(therapyManagement, data) {
		
		setModifiedRankinNotKnown(therapyManagement, data)
		
		def baselineModifiedRankin = therapyManagement.assessmentManagement.findModifiedRankinByStage("Pre-admission");
		
		
		/*if(therapyManagement.assessmentManagement.preAdmissionModifiedRankinNotKnown){
			if(baselineModifiedRankin){
				baselineModifiedRankin.delete()
			}
			therapyManagement.assessmentManagement.deleteModifiedRankinByStage("Pre-admission")
		}else */if (getIntegerFromString(data.modifiedRankin.modifiedRankinScore)!=null ){
			if(!baselineModifiedRankin){
				baselineModifiedRankin = therapyManagement.assessmentManagement.addModifiedRankinByStage("Pre-admission")
			}
			baselineModifiedRankin.modifiedRankinScore = getIntegerFromString(data.modifiedRankin.modifiedRankinScore)
		}else{
			if(baselineModifiedRankin){
				baselineModifiedRankin.delete()
			}
			therapyManagement.assessmentManagement.deleteModifiedRankinByStage("Pre-admission")

		}
	}

	private setModifiedRankinNotKnown(therapyManagement, data) {
//		if(data.modifiedRankinNotKnown && data.modifiedRankinNotKnown!=JSONObject.NULL){
//			therapyManagement.assessmentManagement.preAdmissionModifiedRankinNotKnown = data.modifiedRankinNotKnown
//		}else{
			therapyManagement.assessmentManagement.preAdmissionModifiedRankinNotKnown = false
//		}
	}

	private processBarthel(therapyManagement, data) {
		
		setBarthelNotKnownFlag(therapyManagement, data)
		def baselineBarthel = therapyManagement.assessmentManagement.findBarthelByStage("Pre-admission")
		
		
		if(therapyManagement.assessmentManagement.preAdmissionBarthelNotKnown && isComprehensive()){
			if(baselineBarthel){
				baselineBarthel.delete()
			}
			therapyManagement.assessmentManagement.deleteBarthelByStage("Pre-admission")
		}else if(getIntegerFromString(data.barthel.bowels)!=null
					|| getIntegerFromString(data.barthel.bladder)!=null
					|| getIntegerFromString(data.barthel.grooming)!=null
					|| getIntegerFromString(data.barthel.toilet)!=null
					|| getIntegerFromString(data.barthel.feeding)!=null
					|| getIntegerFromString(data.barthel.transfer)!=null
					|| getIntegerFromString(data.barthel.mobility)!=null
					|| getIntegerFromString(data.barthel.dressing)!=null
					|| getIntegerFromString(data.barthel.stairs)!=null
					|| getIntegerFromString(data.barthel.bathing)!=null
					|| getIntegerFromString(data.barthel.manualTotal)!=null ){
			if(null == baselineBarthel){
				baselineBarthel = therapyManagement.assessmentManagement.addBarthelByStage("Pre-admission")
				log.debug("baselineBarthel == "+baselineBarthel)
			}

			baselineBarthel.bowels = getIntegerFromString(data.barthel.bowels)
			baselineBarthel.bladder = getIntegerFromString(data.barthel.bladder)
			baselineBarthel.grooming = getIntegerFromString(data.barthel.grooming)
			baselineBarthel.toilet = getIntegerFromString(data.barthel.toilet)
			baselineBarthel.feeding = getIntegerFromString(data.barthel.feeding)
			baselineBarthel.transfer = getIntegerFromString(data.barthel.transfer)
			baselineBarthel.mobility = getIntegerFromString(data.barthel.mobility)
			baselineBarthel.dressing = getIntegerFromString(data.barthel.dressing)
			baselineBarthel.stairs = getIntegerFromString(data.barthel.stairs)
			baselineBarthel.bathing = getIntegerFromString(data.barthel.bathing)

			if(baselineBarthel.hasDetail()){
				baselineBarthel.manualTotal = null
			}else{
				baselineBarthel.manualTotal = getIntegerFromString(data.barthel.manualTotal)
			}
		}else{
			if(baselineBarthel){
				baselineBarthel.delete()
			}
			therapyManagement.assessmentManagement.deleteBarthelByStage("Pre-admission")
		
		}
		
	}

	private setBarthelNotKnownFlag(therapyManagement, data) {
		if(data.barthelNotKnown && data.barthelNotKnown!=JSONObject.NULL){
			therapyManagement.assessmentManagement.preAdmissionBarthelNotKnown = data.barthelNotKnown
		}else{
			therapyManagement.assessmentManagement.preAdmissionBarthelNotKnown = false
		}
	}
	
	
	private def isComprehensive  = {
		return grailsApplication.config.ssnap.level.equals('comprehensive')
	 }
	
	
	
}
	

