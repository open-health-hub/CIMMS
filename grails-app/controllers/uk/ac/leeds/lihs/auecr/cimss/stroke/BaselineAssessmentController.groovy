package uk.ac.leeds.lihs.auecr.cimss.stroke

import grails.converters.JSON;
import org.codehaus.groovy.grails.web.json.JSONObject;

import uk.ac.leeds.lihs.auecr.cimss.stroke.therapy.AssessmentManagement;


class BaselineAssessmentController extends  StrokeBaseController{

    
    def getBaselineAssessmentPage = {
		log.debug "in getBaselineAssessmentPage"
		def careActivity = CareActivity.get(params.id)
		render(template:'/baselineAssessment/baselineAssessment',model:['careActivityInstance':careActivity])
	}

	def getBaselineAssessment = {
		log.debug "in getBaselineAssessment"
		def careActivity = CareActivity.get(params.id)
		renderBaselineAssessment(careActivity);
	}
		
	def updateBaselineAssessment = {
		log.info "in updateBaselineAssessment -> ${request.JSON.therapyManagement}"
		def careActivity = CareActivity.get(params.id);
		def errors=[:]
		
		def data = request.JSON.therapyManagement
		
		
		if(changedSinceRetrieval(careActivity, data)){
			data.versions = getVersions(careActivity)
			careActivity.errors.rejectValue('version', 'version.changed.therapy')
			careActivity.discard()
			renderBaselineAssessmentWithErrors(data, careActivity, errors);
		}else{
			updateData(careActivity, data, errors)
			if(!errors & careActivity.validate()){
				careActivity.save(flush:true)
				renderBaselineAssessment(careActivity);
			}else{
				careActivity.discard()
				renderBaselineAssessmentWithErrors(data, careActivity, errors);
			}
		}
		
		
		
		
		
	}
		
	private def updateData = {careActivity, data, errors ->
		if(!careActivity.therapyManagement){
			careActivity.therapyManagement = new TherapyManagement()
			careActivity.therapyManagement.careActivity = careActivity
		}
			updateAssessmentManagement(careActivity.therapyManagement, data.assessments, errors)
	
	}
	
	private def updateAssessmentManagement = { therapyManagement, data, errors ->
		ensureAssessmentManagementExists(therapyManagement)
		processBarthel(therapyManagement, data)
		processModifiedRankin(therapyManagement, data)
	}
	
	private ensureAssessmentManagementExists(therapyManagement) {
		if(!therapyManagement.assessmentManagement){
			therapyManagement.assessmentManagement = new AssessmentManagement()
			therapyManagement.assessmentManagement.therapyManagement = therapyManagement
		}
	}
	
	private processModifiedRankin(therapyManagement, data) {
		
		setModifiedRankinNotKnown(therapyManagement, data)
		
		def baselineModifiedRankin = therapyManagement.assessmentManagement.findModifiedRankinByStage("Baseline");
		
		
		if(therapyManagement.assessmentManagement.baselineModifiedRankinNotKnown){
			if(baselineModifiedRankin){
				baselineModifiedRankin.delete()
			}
			therapyManagement.assessmentManagement.deleteModifiedRankinByStage("Baseline")
		}else if (getIntegerFromString(data.modifiedRankin.modifiedRankinScore) !=null ){
			if(!baselineModifiedRankin){
				baselineModifiedRankin = therapyManagement.assessmentManagement.addModifiedRankinByStage("Baseline")
			}
			baselineModifiedRankin.modifiedRankinScore = getIntegerFromString(data.modifiedRankin.modifiedRankinScore)
		}else{
			if(baselineModifiedRankin){
				baselineModifiedRankin.delete()
			}
			therapyManagement.assessmentManagement.deleteModifiedRankinByStage("Baseline")

		}
	}

	private setModifiedRankinNotKnown(therapyManagement, data) {
		if(data.modifiedRankinNotKnown && data.modifiedRankinNotKnown!=JSONObject.NULL){
			therapyManagement.assessmentManagement.baselineModifiedRankinNotKnown = data.modifiedRankinNotKnown
		}else{
			therapyManagement.assessmentManagement.baselineModifiedRankinNotKnown = false
		}
	}

	private processBarthel(therapyManagement, data) {
		
		setBarthelNotKnownFlag(therapyManagement, data)
		def baselineBarthel = therapyManagement.assessmentManagement.findBarthelByStage("Baseline")
		
		
		if(therapyManagement.assessmentManagement.baselineBarthelNotKnown){
			if(baselineBarthel){
				baselineBarthel.delete()
			}
			therapyManagement.assessmentManagement.deleteBarthelByStage("Baseline")
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
			if(!baselineBarthel){
				baselineBarthel = therapyManagement.assessmentManagement.addBarthelByStage("Baseline")
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
			therapyManagement.assessmentManagement.deleteBarthelByStage("Baseline")
		
		}
		
	}

	private setBarthelNotKnownFlag(therapyManagement, data) {
		if(data.barthelNotKnown && data.barthelNotKnown!=JSONObject.NULL){
			therapyManagement.assessmentManagement.baselineBarthelNotKnown = data.barthelNotKnown
		}else{
			therapyManagement.assessmentManagement.baselineBarthelNotKnown = false
		}
	}
	
	
	
	def private  changedSinceRetrieval = { careActivity, data ->
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
	

	
	private def renderBaselineAssessment = {careActivity ->
		
		def modifiedRankin = [id:""
			, modifiedRankinScore:""]
		if(careActivity?.therapyManagement?.assessmentManagement?.findModifiedRankinByStage("Baseline")){
			modifiedRankin.id = careActivity?.therapyManagement?.assessmentManagement?.findModifiedRankinByStage("Baseline").id
			modifiedRankin.modifiedRankinScore =  careActivity?.therapyManagement?.assessmentManagement?.findModifiedRankinByStage("Baseline").modifiedRankinScore
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
		  
		if(careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Baseline")){
			barthel.id = careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Baseline")?.id
			barthel.bowels =  careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Baseline")?.bowels
			barthel.bladder =  careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Baseline")?.bladder
			barthel.grooming =  careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Baseline")?.grooming
			barthel.toilet =  careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Baseline")?.toilet
			barthel.feeding =  careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Baseline")?.feeding
			barthel.transfer =  careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Baseline")?.transfer
			barthel.mobility =  careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Baseline")?.mobility
			barthel.dressing =  careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Baseline")?.dressing
			barthel.stairs =  careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Baseline")?.stairs
			barthel.bathing =  careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Baseline")?.bathing
			barthel.manualTotal =  careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Baseline")?.manualTotal
		}
			
			
		
		def assessments = [id:careActivity?.therapyManagement?.assessmentManagement?.id
								   , barthelNotKnown:careActivity?.therapyManagement?.assessmentManagement?.baselineBarthelNotKnown
								, modifiedRankinNotKnown:careActivity?.therapyManagement?.assessmentManagement?.baselineModifiedRankinNotKnown
								, barthel:barthel
								, modifiedRankin:modifiedRankin]
		
		
			
		def therapyManagement = [id:careActivity?.therapyManagement?.id
			, versions:getVersions(careActivity)
			, assessments:assessments]
		def result = [therapyManagement:therapyManagement, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
	}
	
	private	def renderBaselineAssessmentWithErrors = {data, careActivity, errors ->
		log.debug "In renderBaselineAssessmentWithErrors"
		errors.each{key, value ->
			log.debug "Adding non domain error --> ${key} :: ${value}"
			careActivity.errors.reject(value,[key]as Object[], "Custom validation failed for ${key} in the controller".toString())
		}
		def result = [therapyManagement:data, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
	}
	
}
