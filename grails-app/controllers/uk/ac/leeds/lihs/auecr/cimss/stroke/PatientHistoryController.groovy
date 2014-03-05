package uk.ac.leeds.lihs.auecr.cimss.stroke

import grails.converters.JSON;
import org.codehaus.groovy.grails.web.json.JSONObject;

import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.AmbulanceTrustType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.ComorbidityType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.MedicationType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.RiskFactorType;


class PatientHistoryController extends StrokeBaseController{

   def getPatientHistoryPage = {
		log.debug "in getPatientHistoryPage"
		def careActivity = CareActivity.get(params.id)
		render(template:'/admission/patient_history',model:['careActivityInstance':careActivity])
	}

	def getPatientHistory = {
		log.debug "in getPatientHistory"
		def careActivity = CareActivity.get(params.id);
		renderPatientHistory(careActivity);
	}
	
	def updatePatientHistory = {
		log.info "in updatePatientHistory -> ${request.JSON.AdmissionDetails}"
		def careActivity = CareActivity.get(params.id)
		def errors = [:]
		def data = request.JSON.AdmissionDetails
		 
		if(changedSinceRetrieval(careActivity, data)){
			data.versions = getVersions(careActivity)
			data.skipBruteForce = true
			careActivity.errors.rejectValue('version', 'version.changed.onset.admission')
			careActivity.discard()
			renderPatientHistoryWithErrors(data, careActivity, errors);
		}else{
			updateData(careActivity, data, errors)
			if(careActivity.validate() & !errors){
					careActivity.save(flush:true)
					renderPatientHistory(careActivity);
			}else{
					careActivity.discard()
					renderPatientHistoryWithErrors(data, careActivity, errors);
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
	
	
	
	private def renderPatientHistory = {careActivity ->
		log.trace "In renderPatientHistory"
		def getComorbidity =  { type ->
			if (careActivity?.medicalHistory?.getComorbidity(ComorbidityType.findByDescription(type))){
				return Boolean.TRUE
			}else{
				return Boolean.FALSE
			}
		}
		
		def getComorbidityAsString =  { type ->
			if (careActivity?.medicalHistory?.getComorbidity(ComorbidityType.findByDescription(type))){
				return careActivity?.medicalHistory?.getComorbidity(ComorbidityType.findByDescription(type))?.value
			}else{
				return ""
			}
		}
		
		def existing = [previousStroke:careActivity?.medicalHistory?.previousStroke
						, previousTIA:careActivity?.medicalHistory?.previousTia
						, assessedInVascularClinic:getBooleanAsString(careActivity?.medicalHistory?.assessedInVascularClinic)
						, diabetes:getComorbidityAsString('diabetes')
						, atrialFibrillation:getComorbidityAsString('atrial fibrillation')
						, myocardialInfarction:getComorbidityAsString('myocardial infarction')
						, hyperlipidaemia:getComorbidityAsString('hyperlipidaemia')
						, hypertension:getComorbidityAsString('hypertension')
						, valvularHeartDisease:getComorbidityAsString('valvular heart disease')
						, ischaemicHeartDisease:getComorbidityAsString('ischaemic heart disease')
						, congestiveHeartFailure:getComorbidityAsString('congestive heart failure')]
		
		
		
		
		
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
		
		def getMedicationAsString  =  { type ->
			if (careActivity?.medicalHistory?.getMedication(MedicationType.findByDescription(type))){
				careActivity?.medicalHistory?.getMedication(MedicationType.findByDescription(type))?.value
			}else{
				return ""
			}
		}
		
		
		
		def medication = [lipidLowering:getMedicationAsString('lipid lowering')
						, warfarin:getMedicationAsString('warfarin')
						, antiplatelet:getMedicationAsString('antiplatelet')]
		
		
		def arrival = [ambulanceTrust:careActivity?.medicalHistory?.arrival?.ambulanceTrust?.code
		               ,transferredFromAnotherHospital:careActivity?.medicalHistory?.arrival?.transferredFromAnotherHospital?.toString()
		               ,thisHospitalArrivalDate:DisplayUtils.displayDate(careActivity?.medicalHistory?.arrival?.thisHospitalArrivalDate)
		               ,thisHospitalArrivalTime:DisplayUtils.displayTime(careActivity?.medicalHistory?.arrival?.thisHospitalArrivalTime)
		               ,arriveByAmbulance:careActivity?.medicalHistory?.arrival?.arriveByAmbulance?.toString()
					   ,cadNumber:careActivity?.medicalHistory?.arrival?.cadNumber
					   ,cadNumberUnknown:careActivity?.medicalHistory?.arrival?.cadNumberUnknown?.toString()
					   ,outcomeQuestionnairOptOut:careActivity?.medicalHistory?.arrival?.outcomeQuestionnairOptOut?.toString()]
		def admissionDetails = [
			versions:getVersions(careActivity)
			, independent:careActivity?.clinicalAssessment?.independent								
			, existing:existing
			, lifeStyle:lifeStyle
			, riskFactors:riskFactors
			, medication:medication
			, arrival:arrival
			, inpatientAtOnset: careActivity?.medicalHistory?.inpatientAtOnset]
			
		
				
		def result = [AdmissionDetails:admissionDetails
						, FieldsInError: getFieldsInError(careActivity)
					    , ErrorsAsList:getErrorsAsList(careActivity)
					    , InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
			
	}

	

	private def renderPatientHistoryWithErrors = {data, careActivity, errors ->
		log.debug "In renderPatientHistoryWithErrors"
		errors.each{key, value ->
			log.debug "Adding non domain error --> ${key} :: ${value}"
			careActivity.errors.reject(value,[key]as Object[], "Custom validation failed for ${key} in the controller".toString())
		}
		def result = [AdmissionDetails:data, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
	}
		
	private def updateData = {careActivity, data, errors ->
		updateMedicalHistory(careActivity, data, errors)
		updatePatientLifeStyle(careActivity, data, errors)
		
		if(!careActivity.clinicalAssessment){
			careActivity.clinicalAssessment = new ClinicalAssessment()
			careActivity.clinicalAssessment.careActivity = careActivity
		}
		
		careActivity.clinicalAssessment.independent = getValueFromString(data.independent)
		
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
		updateMedicationAsString(careActivity, data, errors)
		updateArrival(careActivity, data, errors)
	}


	
	private def updateArrival = {careActivity, data, errors  ->
		if( !careActivity.medicalHistory.arrival) {
			careActivity.medicalHistory.arrival = new Arrival();
			careActivity.medicalHistory.arrival.ambulanceTrust  = AmbulanceTrustType.findByDescription('UNKNOWN')
		}
		
		if(getValueFromString(data.arrival.transferredFromAnotherHospital)){
			careActivity.medicalHistory.arrival.transferredFromAnotherHospital = Boolean.valueOf(data.arrival.transferredFromAnotherHospital)
		} else {
			careActivity.medicalHistory.arrival.transferredFromAnotherHospital = null
		}
		
		
		if ( getValueFromString(data.arrival.thisHospitalArrivalDate) ) {
			try {
				careActivity.medicalHistory.hospitalAdmissionDate = DisplayUtils.getDate(data.arrival.thisHospitalArrivalDate)
				careActivity.startDate = DisplayUtils.getDate(data.arrival.thisHospitalArrivalDate)
				careActivity.medicalHistory.arrival.thisHospitalArrivalDate = careActivity.startDate
			}catch(IllegalArgumentException iae){
				errors.put("medicalHistory.hospitalArrivalDate", "medical.history.arrival.date.invalid.format")
			}
		} else {
			careActivity.medicalHistory.hospitalAdmissionDate = null
		}
		
		if(getValueFromString(data.arrival.thisHospitalArrivalTime)){
			try {
				careActivity.medicalHistory.hospitalAdmissionTime = DisplayUtils.getTime(data.arrival.thisHospitalArrivalTime)
				careActivity.startTime = DisplayUtils.getTime(data.arrival.thisHospitalArrivalTime)
				careActivity.medicalHistory.arrival.thisHospitalArrivalTime = careActivity.startTime
			}catch(IllegalArgumentException iae){
				errors.put("medicalHistory.hospitalArrivalTime", "medical.history.arrival.time.invalid.format")
			}
			
		} else {
			careActivity.medicalHistory.hospitalAdmissionTime = null
		}
		
		
		
		
		if(getValueFromString(data.arrival.arriveByAmbulance)){
			careActivity.medicalHistory.arrival.arriveByAmbulance = Boolean.valueOf(data.arrival.arriveByAmbulance)
		}else{
			careActivity.medicalHistory.arrival.arriveByAmbulance = null
		}
		if(getValueFromString(data.arrival.ambulanceTrust)){
			careActivity.medicalHistory.arrival.ambulanceTrust = AmbulanceTrustType.findByCode(data.arrival.ambulanceTrust)
		}else{
			careActivity.medicalHistory.arrival.ambulanceTrust = AmbulanceTrustType.findByDescription('UNKNOWN')
		}
		if(getValueFromString(data.arrival.cadNumber)){
			careActivity.medicalHistory.arrival.cadNumber = getValueFromString(data.arrival.cadNumber)
		}else{
			careActivity.medicalHistory.arrival.cadNumber = null
		}
		if(getBooleanFromString(data.arrival.cadNumberUnknown) != null ){
			careActivity.medicalHistory.arrival.cadNumberUnknown = getBooleanFromString(data.arrival.cadNumberUnknown)
		}else{
			careActivity.medicalHistory.arrival.cadNumberUnknown = null
		}
		if(getBooleanFromString(data.arrival.outcomeQuestionnairOptOut) != null){
			careActivity.medicalHistory.arrival.outcomeQuestionnairOptOut = getBooleanFromString(data.arrival.outcomeQuestionnairOptOut)
		}else{
			careActivity.medicalHistory.arrival.outcomeQuestionnairOptOut = Boolean.FALSE
		}
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
		updateComorbidityAsString(careActivity, data.existing.diabetes, ComorbidityType.findByDescription("diabetes"))
		updateComorbidityAsString(careActivity, data.existing.atrialFibrillation, ComorbidityType.findByDescription("atrial fibrillation"))
		updateComorbidityAsString(careActivity, data.existing.myocardialInfarction, ComorbidityType.findByDescription("myocardial infarction"))
		updateComorbidityAsString(careActivity, data.existing.hyperlipidaemia, ComorbidityType.findByDescription("hyperlipidaemia"))
		updateComorbidityAsString(careActivity, data.existing.hypertension, ComorbidityType.findByDescription("hypertension"))
		updateComorbidityAsString(careActivity, data.existing.valvularHeartDisease, ComorbidityType.findByDescription("valvular heart disease"))
		updateComorbidityAsString(careActivity, data.existing.ischaemicHeartDisease, ComorbidityType.findByDescription("ischaemic heart disease"))
		updateComorbidityAsString(careActivity, data.existing.congestiveHeartFailure, ComorbidityType.findByDescription("congestive heart failure"))
	}
		
	private def updateComorbidity = {careActivity, data,type  ->
		if(data == Boolean.TRUE){
			careActivity.medicalHistory.addComorbidity(type)
		}else{
			careActivity.medicalHistory.removeComorbidity(type)
		}
	}
	
	private def updateComorbidityAsString = {careActivity, data,type  ->
		if(data == "true"){
			careActivity.medicalHistory.addComorbidityAsString(type,"true")
		}else if(data == "false"){
			careActivity.medicalHistory.addComorbidityAsString(type,"false")
		}else{
			careActivity.medicalHistory.removeComorbidity(type)
		}
	}
	
	private def updateMedicationAsString = {careActivity, data, errors  ->
		if(data.medication.warfarin == "true"){
			careActivity.medicalHistory.addMedicationAsString(MedicationType.findByDescription("warfarin"), "true")
		}else if(data.medication.warfarin == "false"){
				careActivity.medicalHistory.addMedicationAsString(MedicationType.findByDescription("warfarin"), "false")
		}else{
			careActivity.medicalHistory.removeMedication(MedicationType.findByDescription("warfarin"))
		}
		if(data.medication.lipidLowering == "true"){
			careActivity.medicalHistory.addMedicationAsString(MedicationType.findByDescription("lipid lowering"), "true")
		}else if(data.medication.lipidLowering == "false"){
			careActivity.medicalHistory.addMedicationAsString(MedicationType.findByDescription("lipid lowering"), "false")
		}else{
			careActivity.medicalHistory.removeMedication(MedicationType.findByDescription("lipid lowering"))
		}
		if(data.medication.antiplatelet == "true"){
			careActivity.medicalHistory.addMedicationAsString(MedicationType.findByDescription("antiplatelet"), "true")
		}else if(data.medication.antiplatelet == "false"){
			careActivity.medicalHistory.addMedicationAsString(MedicationType.findByDescription("antiplatelet"), "false")
		}else{
			careActivity.medicalHistory.removeMedication(MedicationType.findByDescription("antiplatelet"))
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
	
	private def check1stHospitalArrivalDateBeforeOnsetDate = { careActivity, data, errors ->
		if ( careActivity.medicalHistory.arrival.transferredFromAnotherHospital == Boolean.FALSE
				&& careActivity.beforeOnset(careActivity.medicalHistory.arrival.thisHospitalArrivalDate,
					careActivity.medicalHistory.arrival.thisHospitalArrivalTime)
				&& careActivity.medicalHistory.inpatientAtOnset == Boolean.FALSE) {
				errors.put("medicalHistory.hospitalArrivalDate", "medical.history.stroke.ward.admission.before.onset")
		}
		
	}
}
	

