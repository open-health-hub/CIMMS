package uk.ac.leeds.lihs.auecr.cimss.stroke
import org.codehaus.groovy.grails.web.json.JSONObject;

import uk.ac.leeds.lihs.auecr.cimss.stroke.therapy.AssessmentManagement;

import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.ComorbidityType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.MedicationType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.RiskFactorType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.StaffRole;



import grails.converters.JSON;

class AdmissionController extends StrokeBaseController{

    def getAdmissionDetails = {
		log.debug "in getAdmissionDetails"
		def careActivity = CareActivity.get(params.id)
		renderAdmissionDetails(careActivity);
	}

	def updateAdmissionDetails = {
		log.info "in updateAdmissionDetails -> ${request.JSON.AdmissionDetails}"
		def careActivity = CareActivity.get(params.id)
		def errors = [:]

		def data = request.JSON.AdmissionDetails
		
		if(changedSinceRetrieval(careActivity, data)){
			data.versions = getVersions(careActivity)
			careActivity.errors.rejectValue('version', 'version.changed.onset.admission')
			careActivity.discard()
			renderAdmissionDetailsWithErrors( data, careActivity, errors)
		}else{
			updateData(careActivity, data, errors)
			if(careActivity.validate() & !errors){
					careActivity.save(flush:true)
					renderAdmissionDetails(careActivity);
			}else{
					careActivity.discard()
					renderAdmissionDetailsWithErrors( data, careActivity, errors)
			}
		}	
		
	}
		
	private def changedSinceRetrieval = { careActivity, data->
		if(careActivity.version && careActivity.version >(long)data.versions.careActivity){
			// check strokeBedAvailable
			if(careActivity.findCareActivityProperty("strokeBedAvailable") != getValueFromString(data.originalData.strokeBedAvailable)){
				return true
			}
			// check evaluation not seen
			for( firstSeen in data.originalData.firstSeenByList){
				if(firstSeen.notSeen == Boolean.TRUE){
					if(careActivity.findCareActivityProperty(("${firstSeen.category} not seen".toString())!="true")){
						return true	
					}
				}
			}
			
		}
		// check evaluations
		def hasChanged = false
		careActivity.evaluations.each{evaluation ->
			def isSame = false			
			data.versions.evaluations.each{id, version ->
				if (evaluation.id == id || evaluation.version == version){
					isSame = true
				}
			}
			if(!isSame){
				hasChanged = true
			}
		}
		if(hasChanged){
			return true
		}
		
		
			
		// only this can currently change medical history 
		if(careActivity?.medicalHistory?.version!=null && data.versions.medicalHistory == JSONObject.NULL){
			return true
		}
		if(careActivity?.medicalHistory?.version && careActivity?.medicalHistory?.version >(long)data.versions.medicalHistory){
			return true
		}
		// only this can change patient life style
		if(careActivity?.patientLifeStyle?.version!=null && data.versions.patientLifeStyle == JSONObject.NULL){
			return true
		}
		if(careActivity?.patientLifeStyle?.version && careActivity?.patientLifeStyle?.version >(long)data.versions.patientLifeStyle){
			return true
		}
		return false
	}
	
	private def updateData = {careActivity, data, errors ->
		if(ControllerHelper.getValueFromString(data.strokeBedAvailable)){
			careActivity.setCareActivityProperty("strokeBedAvailable", data.strokeBedAvailable)
		}else{
			careActivity.clearCareActivityProperty("strokeBedAvailable")
		}
		updateMedicalHistory(careActivity, data, errors)
		updatePatientLifeStyle(careActivity, data, errors)
		
		
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
		
		
		if(therapyManagement.assessmentManagement.preAdmissionModifiedRankinNotKnown){
			if(baselineModifiedRankin){
				baselineModifiedRankin.delete()
			}
			therapyManagement.assessmentManagement.deleteModifiedRankinByStage("Pre-admission")
		}else if (getIntegerFromString(data.modifiedRankin.modifiedRankinScore)!=null ){
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
		if(data.modifiedRankinNotKnown && data.modifiedRankinNotKnown!=JSONObject.NULL){
			therapyManagement.assessmentManagement.preAdmissionModifiedRankinNotKnown = data.modifiedRankinNotKnown
		}else{
			therapyManagement.assessmentManagement.preAdmissionModifiedRankinNotKnown = false
		}
	}

	private processBarthel(therapyManagement, data) {
		
		setBarthelNotKnownFlag(therapyManagement, data)
		def baselineBarthel = therapyManagement.assessmentManagement.findBarthelByStage("Pre-admission")
		
		
		if(therapyManagement.assessmentManagement.preAdmissionBarthelNotKnown){
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
			if(!baselineBarthel){
				baselineBarthel = therapyManagement.assessmentManagement.addBarthelByStage("Pre-admission")
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
		
	private def renderAdmissionDetails = {careActivity ->
		log.trace "In renderAdmissionDetails"
		def getComorbidity =  { type ->
			if (careActivity?.medicalHistory?.getComorbidity(ComorbidityType.findByDescription(type))){
				return Boolean.TRUE
			}else{
				return Boolean.FALSE
			}
		}
		
		def existing = [previousStroke:careActivity?.medicalHistory?.previousStroke
						, previousTIA:careActivity?.medicalHistory?.previousTia
						, assessedInVascularClinic:getBooleanAsString(careActivity?.medicalHistory?.assessedInVascularClinic)
						, diabetes:getComorbidity('diabetes')
						, atrialFibrillation:getComorbidity('atrial fibrillation')
						, myocardialInfarction:getComorbidity('myocardial infarction')
						, hyperlipidaemia:getComorbidity('hyperlipidaemia')
						, hypertension:getComorbidity('hypertension')
						, valvularHeartDisease:getComorbidity('valvular heart disease')
						, ischaemicHeartDisease:getComorbidity('ischaemic heart disease')
						, congestiveHeartFailure:getComorbidity('congestive heart failure')]
		
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
		
		
		
		def assessments = [id:careActivity?.therapyManagement?.assessmentManagement?.id
									   , barthelNotKnown:careActivity?.therapyManagement?.assessmentManagement?.preAdmissionBarthelNotKnown
									, modifiedRankinNotKnown:careActivity?.therapyManagement?.assessmentManagement?.preAdmissionModifiedRankinNotKnown
									, barthel:barthel
									, modifiedRankin:modifiedRankin]
		
			
		
		def preMorbidAssessment = [id:careActivity
									 , versions:getVersions(careActivity)
									 , assessments:assessments]
		
		
		
		def  riskFactors = [smoker:careActivity?.medicalHistory?.getRisk(RiskFactorType.findByDescription("Smoker"))?.value
							, alcohol:careActivity?.medicalHistory?.getRisk(RiskFactorType.findByDescription("Alcohol"))?.value]
		
		def lifeStyle = [livedAlone:careActivity?.patientLifeStyle?.livedAlonePreAdmission]
		
		def getMedication =  { type ->
			if (careActivity?.medicalHistory?.getMedication(MedicationType.findByDescription(type))){
				return Boolean.TRUE
			}else{
				return Boolean.FALSE
			}
		}
		
		
		
		def medication = [lipidLowering:getMedication('lipid lowering')
						, warfarin:getMedication('warfarin')
						, antiplatelet:getMedication('antiplatelet')]
		
		def onset = [duringSleep:getBooleanAsString(careActivity?.medicalHistory?.duringSleep)
					 , onsetDate:DisplayUtils.displayDate(careActivity?.medicalHistory?.onsetDate)
					 , onsetDateEstimated:careActivity?.medicalHistory?.onsetDateEstimated
					 , onsetDateUnknown:careActivity?.medicalHistory?.onsetDateUnknown
					 , onsetTime:DisplayUtils.displayTime(careActivity?.medicalHistory?.onsetTime)
					 , onsetTimeEstimated:careActivity?.medicalHistory?.onsetTimeEstimated
					 , onsetTimeUnknown:careActivity?.medicalHistory?.onsetTimeUnknown]
		
		def firstSeenByList = []
		
		
		def processEvaluation = {type, text ->
			def evaluation = careActivity.getEvaluation(StaffRole.findByDescription(type) );
			if(evaluation){
				firstSeenByList << [category:type
					, firstSeenDate:DisplayUtils.displayDate(evaluation.dateEvaluated)
					, firstSeenTime:DisplayUtils.displayTime(evaluation.timeEvaluated)
					, notSeen:false
					, display:text]
				
			}else{
			
				firstSeenByList << [category:type
				, firstSeenDate:""
				, firstSeenTime:""
				, notSeen:careActivity.findCareActivityProperty("${type} not seen".toString())? true:false
				, display:text]
			
			}

			
		}
		
		//processEvaluation("Stroke team member")
		processEvaluation("Ward based nurse", "Nurse trained in stroke management")
		processEvaluation("Stroke consultant", "Stroke specialist consultant physician")
		
		
		
		def admissionDetails = [
			versions:getVersions(careActivity)
			, existing:existing
			, preMorbidAssessment:preMorbidAssessment
			, lifeStyle:lifeStyle
			, riskFactors:riskFactors
			, medication:medication
			, onset:onset
			, firstSeenByList:firstSeenByList
			, strokeBedAvailable:careActivity.findCareActivityProperty("strokeBedAvailable")]
			
		
				
		def result = [AdmissionDetails:admissionDetails
						, FieldsInError: getFieldsInError(careActivity)
					    , ErrorsAsList:getErrorsAsList(careActivity)
					    , InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
	}
	                    
	private def renderAdmissionDetailsWithErrors = {data, careActivity, errors ->
		log.debug "In renderAdmissionDetailsWithErrors"
		data.remove('originalData')
		errors.each{key, value ->
			log.debug "Adding non domain error --> ${key} :: ${value}"
			careActivity.errors.reject(value,[key] as Object[], "Custom validation failed for ${key} in the controller".toString())
		}				
		def result = [AdmissionDetails:data
						, FieldsInError: getFieldsInError(careActivity)
						, ErrorsAsList:getErrorsAsList(careActivity)
						, InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
	}
		
	private def updatePatientLifeStyle = {careActivity, data, errors ->
		def patientLifeStyle  = careActivity.patientLifeStyle;
		if(!patientLifeStyle){
			careActivity.patientLifeStyle = new PatientLifeStyle();
			patientLifeStyle = careActivity.patientLifeStyle
			patientLifeStyle.careActivity=careActivity
		}
		if(getValueFromString(data.lifeStyle.livedAlone)){
			careActivity.patientLifeStyle.livedAlonePreAdmission = getValueFromString(data.lifeStyle.livedAlone)
		}else{
			careActivity.patientLifeStyle.livedAlonePreAdmission=null
		}
    }
	
	private def updateMedicalHistory = {careActivity, data, errors ->
		def medicalHistory  = careActivity.medicalHistory;
		if(!medicalHistory){
			careActivity.medicalHistory = new MedicalHistory();
			medicalHistory = careActivity.medicalHistory
			medicalHistory.careActivity=careActivity
		}
			
		updateRisks(careActivity, data, errors)
		updatePreviousStroke(careActivity, data, errors)
		updatePreviousTIA(careActivity, data, errors)
		updateComorbidities(careActivity, data, errors)
		updateMedication(careActivity, data, errors)
		updateOnset(careActivity, data, errors)
		updateFirstSeenByList(careActivity, data, errors)
	}
	
	private def updatePreviousStroke = {careActivity, data, errors  ->
		if(getValueFromString(data.existing.previousStroke)){
			careActivity.medicalHistory.previousStroke = getValueFromString(data.existing.previousStroke)
		}else{
			careActivity.medicalHistory.previousStroke=null
		}
	}
	
	private def updatePreviousTIA = {careActivity, data, errors  ->
		if(getValueFromString(data.existing.previousTIA)){
			careActivity.medicalHistory.previousTia = getValueFromString(data.existing.previousTIA)
			
			if(careActivity.medicalHistory.previousTia == "yesWithinMonth"){
				careActivity.medicalHistory.assessedInVascularClinic = getBooleanFromString(data.existing.assessedInVascularClinic)
			}else{
				careActivity.medicalHistory.assessedInVascularClinic = null;
			}
			
		}else{
			careActivity.medicalHistory.previousTia=null
			careActivity.medicalHistory.assessedInVascularClinic = null;
		}
		
	}
		
	private def updateFirstSeenByList =  {careActivity, data, errors ->
		for( firstSeen in data.firstSeenByList){
			if(firstSeen.notSeen == Boolean.TRUE){
				careActivity.setCareActivityProperty("${firstSeen.category} not seen".toString(), "true")
				careActivity.removeEvaluation(StaffRole.findByDescription(firstSeen.category));
			}else{
				careActivity.clearCareActivityProperty("${firstSeen.category} not seen".toString())
				def evaluation  = careActivity.getEvaluation(StaffRole.findByDescription(firstSeen.category) );
				if (evaluation){
					if(firstSeen.firstSeenDate || firstSeen.firstSeenTime){	
						try{
							evaluation.dateEvaluated = DisplayUtils.getDate(firstSeen.firstSeenDate)
						}catch(IllegalArgumentException iae){
							errors.put("evaluations.dateEvaluated", "evaluation.date.invalid.format")
						}
						try{
							evaluation.timeEvaluated = DisplayUtils.getTime(firstSeen.firstSeenTime)
						}catch(IllegalArgumentException iae){
							errors.put("evaluations.timeEvaluated", "evaluation.time.invalid.format")
						}
					}else{
						careActivity.removeEvaluation(StaffRole.findByDescription(firstSeen.category));
					}
				}else{	
				   if(firstSeen.firstSeenDate || firstSeen.firstSeenTime){				
						evaluation  = careActivity.addEvaluation(StaffRole.findByDescription(firstSeen.category) );
						try{
							evaluation.dateEvaluated = DisplayUtils.getDate(firstSeen.firstSeenDate)
						}catch(IllegalArgumentException iae){
							errors.put("evaluations.dateEvaluated", "evaluation.date.invalid.format")
						}
						try{
							evaluation.timeEvaluated = DisplayUtils.getTime(firstSeen.firstSeenTime)
						}catch(IllegalArgumentException iae){
							errors.put("evaluations.dateEvaluated.timeEvaluated", "evaluation.time.invalid.format")
						}
				   }
				}
			}
		}
		
	}
	
	private def updateOnset =  {careActivity, data, errors ->
		careActivity.medicalHistory.duringSleep = getBooleanFromString(data.onset.duringSleep)
		
		
		if(data.onset.onsetDateUnknown == Boolean.TRUE){
			careActivity.medicalHistory.onsetDate = null
			careActivity.medicalHistory.onsetDateEstimated = null
			careActivity.medicalHistory.onsetDateUnknown =  data.onset.onsetDateUnknown
		}else {
			try{
				careActivity.medicalHistory.onsetDate =  DisplayUtils.getDate(data.onset.onsetDate)	
				careActivity.medicalHistory.onsetDateEstimated = getCheckedTrue(data.onset.onsetDateEstimated)
			}catch(IllegalArgumentException iae){
				errors.put("medicalHistory.onsetDate", "medical.history.onset.date.invalid.format")
			}
			careActivity.medicalHistory.onsetDateUnknown = Boolean.FALSE
		}
		
		if(data.onset.onsetTimeUnknown == Boolean.TRUE){
			careActivity.medicalHistory.onsetTime = null
			careActivity.medicalHistory.onsetTimeEstimated = null
			careActivity.medicalHistory.onsetTimeUnknown =  data.onset.onsetTimeUnknown
		}else {
			try{
				careActivity.medicalHistory.onsetTime =  DisplayUtils.getTime(data.onset.onsetTime)
				careActivity.medicalHistory.onsetTimeEstimated = getCheckedTrue(data.onset.onsetTimeEstimated)
			}catch(IllegalArgumentException iae){
				errors.put("medicalHistory.onsetTime", "medical.history.onset.time.invalid.format")
			}
			careActivity.medicalHistory.onsetTimeUnknown = Boolean.FALSE
		}
		checkOnsetDateBeforeAdmissionDate(careActivity, data, errors)
    }
	
	private def checkOnsetDateBeforeAdmissionDate = { careActivity, data, errors ->
		log.debug("checking the onset date is not before admission: onsetDateUnknown"+careActivity.medicalHistory.onsetDateUnknown + ",  inpatent@onset = " + careActivity.medicalHistory.inpatientAtOnset)
		if ( !careActivity.medicalHistory.onsetDateUnknown && careActivity.medicalHistory.inpatientAtOnset ) {
			def time = 0 
			if ( careActivity.medicalHistory.onsetTimeUnknown == Boolean.FALSE) {
				time  = careActivity.medicalHistory.onsetTime
			}
			if ( careActivity.beforeAdmission(careActivity.medicalHistory.onsetDate, time) )	{
				errors.put("medicalHistory.onsetDate", "medical.history.onset.time.not.before.admission")
			}
		}
		
	}
	private def updateRisks = {careActivity, data, errors  ->
		def smoker = ControllerHelper.getValueFromString(data.riskFactors.smoker)
		if(smoker){
			careActivity.medicalHistory.addRisk(RiskFactorType.findByDescription("Smoker"), smoker)
		}else{
			careActivity.medicalHistory.removeRisk(RiskFactorType.findByDescription("Smoker"))
		}
		def alcohol = ControllerHelper.getValueFromString(data.riskFactors.alcohol)
		if(alcohol){
			careActivity.medicalHistory.addRisk(RiskFactorType.findByDescription("Alcohol"), alcohol)
		}else{
			careActivity.medicalHistory.removeRisk(RiskFactorType.findByDescription("Alcohol"))
		}
	}
	
	private def updateComorbidities = {careActivity, data, errors  ->
		//updateComorbidity(careActivity, data.existing.previousStroke, ComorbidityType.findByDescription("previous stroke"))
		//updateComorbidity(careActivity, data.existing.previousTIA, ComorbidityType.findByDescription("previous tia"))
		updateComorbidity(careActivity, data.existing.diabetes, ComorbidityType.findByDescription("diabetes"))
		updateComorbidity(careActivity, data.existing.atrialFibrillation, ComorbidityType.findByDescription("atrial fibrillation"))
		updateComorbidity(careActivity, data.existing.myocardialInfarction, ComorbidityType.findByDescription("myocardial infarction"))
		updateComorbidity(careActivity, data.existing.hyperlipidaemia, ComorbidityType.findByDescription("hyperlipidaemia"))
		updateComorbidity(careActivity, data.existing.hypertension, ComorbidityType.findByDescription("hypertension"))
		updateComorbidity(careActivity, data.existing.valvularHeartDisease, ComorbidityType.findByDescription("valvular heart disease"))
		updateComorbidity(careActivity, data.existing.ischaemicHeartDisease, ComorbidityType.findByDescription("ischaemic heart disease"))	
		updateComorbidity(careActivity, data.existing.congestiveHeartFailure, ComorbidityType.findByDescription("congestive heart failure"))
    }
		
	private def updateComorbidity = {careActivity, data,type  ->
		if(data == Boolean.TRUE){
			careActivity.medicalHistory.addComorbidity(type)
		}else{
			careActivity.medicalHistory.removeComorbidity(type)
		}	
	}
			
	private def updateMedication = {careActivity, data, errors  ->
		if(data.medication.warfarin == Boolean.TRUE){
			careActivity.medicalHistory.addMedication(MedicationType.findByDescription("warfarin"))
		}else{
			careActivity.medicalHistory.removeMedication(MedicationType.findByDescription("warfarin"))
		}
		if(data.medication.lipidLowering == Boolean.TRUE){
			careActivity.medicalHistory.addMedication(MedicationType.findByDescription("lipid lowering"))
		}else{
			careActivity.medicalHistory.removeMedication(MedicationType.findByDescription("lipid lowering"))
		}
		if(data.medication.antiplatelet == Boolean.TRUE){
			careActivity.medicalHistory.addMedication(MedicationType.findByDescription("antiplatelet"))
		}else{
			careActivity.medicalHistory.removeMedication(MedicationType.findByDescription("antiplatelet"))
		}
	}
	
	
	
	 
}
