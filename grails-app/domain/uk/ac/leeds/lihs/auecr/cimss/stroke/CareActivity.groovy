package uk.ac.leeds.lihs.auecr.cimss.stroke

import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.DiagnosisType
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.InvestigationType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.TreatmentType;

class CareActivity {
	
	String finalDiagnosis
	
	String hospitalStayId
	
	Date startDate = new Date()
	Integer startTime
	Date endDate
	Integer endTime
	
	Date fitForDischargeDate
	Integer fitForDischargeTime
	Boolean fitForDischargeDateUnknown
	
	String inAfOnDischarge;
	String onAnticoagulantAtDischarge
	
	String socialWorkerReferral
	Date socialWorkerReferralDate
	Boolean socialWorkerReferralUnknown
	String socialWorkerAssessment
	Date socialWorkerAssessmentDate
	Boolean socialWorkerAssessmentUnknown
	
	
	
	static belongsTo = [patient:PatientProxy]
	
	
	
	
	static auditable = [ignore:[]]
	
	static hasMany = [observations:Observation
					, treatments: Treatment
					, evaluations:Evaluation]
   
	Map careActivityProperties
	
	
		
	ClinicalAssessment clinicalAssessment
	ClinicalSummary clinicalSummary
	Thrombolysis thrombolysis
	ContinenceManagement continenceManagement
	NutritionManagement nutritionManagement
	FluidManagement fluidManagement
	TherapyManagement therapyManagement
	Imaging imaging
	MedicalHistory medicalHistory
	PatientLifeStyle patientLifeStyle
	PostDischargeCare postDischargeCare
		
    static constraints = {
		hospitalStayId(unique:true)
		finalDiagnosis(nullable:true)
		startDate(nullable:true)
		endDate(nullable:true)
		endTime(nullable:true)
		startTime(nullable:true, validator: { startTime, careActivity ->
			validateStartEndDates(careActivity)
		})
		
		fitForDischargeDate(nullable:true,validator: { fitForDischargeDate, careActivity ->
			validateFitForDischargeDate(fitForDischargeDate, careActivity)
		})
		
		
		fitForDischargeTime(nullable:true)
/*			,validator:{fitForDischargeTime, careActivity ->
				if( careActivity.fitForDischargeDate != null){
					if(careActivity?.fitForDischargeTime == null  ){
						return "care.activity.fit.for.discharge.time.missing"
					}
					
					if( careActivity.fitForDischargeDate == DateTimeHelper.getCurrentDateAtMidnight()){
						if(fitForDischargeTime > DateTimeHelper.getCurrentTimeAsMinutes() ){
							return "care.activity.fit.for.discharge.time.not.in.future"
						}
					}
					
				}
				
				
		}*/
		
		fitForDischargeDateUnknown(nullable:true,validator: { fitForDischargeDateUnknown, careActivity ->
			validateFitForDischargeDateUnknown(fitForDischargeDateUnknown, careActivity)
		})
		socialWorkerReferral(nullable:true,validator: { socialWorkerReferral, careActivity ->
			validateSocialWorkerReferral(socialWorkerReferral,careActivity)
		})
		socialWorkerReferralDate(nullable:true,validator: { socialWorkerReferralDate, careActivity ->
			validateSocialWorkerReferralDate(socialWorkerReferralDate,careActivity)
		})
		socialWorkerReferralUnknown(nullable:true,validator: { socialWorkerReferralUnknown, careActivity ->
			validateSocialWorkerReferralUnknown(socialWorkerReferralUnknown,careActivity)
		})
		
		socialWorkerAssessment(nullable:true
			,validator: { socialWorkerAssessment, careActivity ->
							validateSocialWorkerAssessment(socialWorkerAssessment,careActivity)
		})
		socialWorkerAssessmentDate(nullable:true,validator: { socialWorkerAssessmentDate, careActivity ->
			validateSocialWorkerAssessmentDate(socialWorkerAssessmentDate,careActivity)
		})
		socialWorkerAssessmentUnknown(nullable:true,validator: { socialWorkerAssessmentUnknown, careActivity ->
			validateSocialWorkerAssessmentUnknown(socialWorkerAssessmentUnknown,careActivity)
		})
		
		inAfOnDischarge(nullable:true)
		onAnticoagulantAtDischarge(nullable:true)
		
		
		patientLifeStyle(nullable:true)
		postDischargeCare(nullable:true)
		clinicalAssessment(nullable:true)
		clinicalSummary(nullable:true)
		therapyManagement(nullable:true)
		nutritionManagement(nullable:true)
		fluidManagement(nullable:true)
		continenceManagement(nullable:true)
		medicalHistory(nullable:true)
		imaging(nullable:true)
		thrombolysis(nullable:true
			,validator:{thrombolysis, careActivity ->
				if(!thrombolysis){
					if(careActivity.findCareActivityProperty('thrombolysed') == 'no' && !careActivity.hasReasonsNotThrombolysed()){
						return "thrombolysis.no.must.provide.reason"
					}
					if(careActivity.findCareActivityProperty('thrombolysed') == 'no'
						 && careActivity.getReasonsNotThrombolysed().noThrombolysisOther 
						  && !careActivity.getReasonsNotThrombolysed().noThrombolysisOtherText){
						  return "thrombolysis.no.must.provide.other.text"
					}
				}
			}
		)
    }
	
	private static validateSocialWorkerReferral(socialWorkerReferral,careActivity) {
		if (socialWorkerReferral) {
			if (socialWorkerReferral=="NotRequired" && (careActivity.socialWorkerReferralDate || careActivity.socialWorkerReferralUnknown )) {
				return "careActivity.socialWorkerReferral.data.set.when.socialWorkerReferral.not.required"
			}
		} else if (careActivity.socialWorkerReferralDate || careActivity.socialWorkerReferralUnknown ){
			return "careActivity.socialWorkerReferral.data.set.when.socialWorkerReferral.null"
		}
	}
	
	//TODO any other rules?
	private static validateSocialWorkerReferralDate(socialWorkerReferralDate,careActivity) {
		if (socialWorkerReferralDate) {
			if(socialWorkerReferralDate > new Date()){
				return "social.worker.referral.date.not.in.future"
			}
			if(careActivity.afterDischarge(socialWorkerReferralDate, null)){
				return "social.worker.referral.date.not.after.discharge"
			}
			if(careActivity.beforeAdmission(socialWorkerReferralDate, null)){
				return "social.worker.referral.date.not.before.admission"
			}
		}
	}
	
	private static validateSocialWorkerReferralUnknown(socialWorkerReferralUnknown,careActivity) {
		if (socialWorkerReferralUnknown && careActivity.socialWorkerReferralDate) {
			return "careActivity.socialWorkerReferralUnknown.true.when.socialWorkerReferralDate.set"
		}
	}
	
	
	
	private static validateSocialWorkerAssessment(socialWorkerAssessment,careActivity) {
		if (socialWorkerAssessment) {
			if (socialWorkerAssessment=="NotRequired" && (careActivity.socialWorkerAssessmentDate || careActivity.socialWorkerAssessmentUnknown )) {
				return "careActivity.socialWorkerAssessment.data.set.when.socialWorkerAssessment.not.required"
			}
		} else if (careActivity.socialWorkerAssessmentDate || careActivity.socialWorkerAssessmentUnknown ){
			return "careActivity.socialWorkerAssessment.data.set.when.socialWorkerAssessment.null"
		}
	}
	
	//TODO any other rules?
	private static validateSocialWorkerAssessmentDate(socialWorkerAssessmentDate,careActivity) {
		if (socialWorkerAssessmentDate) {
			if(socialWorkerAssessmentDate > new Date()){
				return "social.worker.assessment.date.not.in.future"
			}
			if(careActivity.afterDischarge(socialWorkerAssessmentDate, null)){
				return "social.worker.assessment.date.not.after.discharge"
			}
			if(careActivity.beforeAdmission(socialWorkerAssessmentDate, null)){
				return "social.worker.assessment.date.not.before.admission"
			}
			if(careActivity.socialWorkerReferralDate != null
				&& careActivity.socialWorkerAssessmentUnknown != Boolean.TRUE
				&& socialWorkerAssessmentDate.before(careActivity.socialWorkerReferralDate)){
				return "social.worker.assessment.date.not.before.referral.date"
			}
		}
	}
	
	private static validateSocialWorkerAssessmentUnknown(socialWorkerAssessmentUnknown,careActivity) {
		if (socialWorkerAssessmentUnknown && careActivity.socialWorkerAssessmentDate) {
			return "careActivity.socialWorkerAssessmentUnknown.true.when.socialWorkerAssessmentDate.set"
		}
	}
	
	
	
	
	private static validateFitForDischargeDate(fitForDischargeDate, careActivity) {
		if (fitForDischargeDate) {
			if(fitForDischargeDate > new Date()){
				return "fit.for.discharge.date.not.in.future"
			}
			if(careActivity.afterDischarge(fitForDischargeDate, null)){
				return "fit.for.discharge.date.not.after.discharge"
			}
			if(careActivity.beforeAdmission(fitForDischargeDate, null)){
				return "fit.for.discharge.date.not.before.admission"
			}
		}
	}
	
	private static validateFitForDischargeDateUnknown(fitForDischargeDateUnknown, obj) {
		if (fitForDischargeDateUnknown && obj.fitForDischargeDate) {
			return "careActivity.fitForDischargeDateUnknown.true.when.fitForDischargeDateUnknown.set"
		}
	}
	
	private static validateStartEndDates(careActivity) {
				
		if (careActivity.startDate != null && careActivity.endDate != null && careActivity.startTime != null && careActivity.endTime != null) {


			if ( DateTimeHelper.isBefore(careActivity.endDate, careActivity.endTime, careActivity.startDate, careActivity.startTime) ) {
				return "uk.ac.leeds.lihs.auecr.cimss.stroke.HospitalStay.startDate.end.before.start"
			}
		}
	}
	
	private def hasReasonsNotThrombolysed = {
		def result = false
		getReasonsNotThrombolysed().each{key, value ->
			if(value==true){
				result = true
			}
		}
		return result
	}
		
	def findPathologicalEvent = {eventType ->
		def result
		pathologicalEvents.each {pathologicalEvent ->
			if(pathologicalEvent.type == eventType){
				result = pathologicalEvent
			}
		 }
		return result
	}
	
	def inAF  = {
		if (findDiagnosis(DiagnosisType.findByDescription("Atrial Fibrillation"))){
			return true
		}
		return false
	}
	
	def findDiagnosis = {diagnosisType ->
		def result
		diagnoses.each {diagnosis ->
			if(diagnosis.type == diagnosisType){
				result = diagnosis
			}
		 }
		return result
	}
	
	def findCtScan = {
		return 	findInvestigation(InvestigationType.findByDescription("CT Scan"))		
	}
	
	def findInvestigation= {investigationType ->
		def result 
		investigations.each{ investigation ->
			if(investigation.type==investigationType){
				result = investigation
			}
		}
		return result;	
	}
	
	def findAntiplateletTreatment = {
		return 	findTreatment(TreatmentType.findByDescription("Antiplatelet"))
	}
	
	def findTreatment= {treatmentType ->
		def result
		treatments.each{ treatment ->
			if(treatment.type==treatmentType){
				result = treatment
			}
		}
		return result;
	}
	
	
	def getReasonsNotThrombolysed = {
		def result = [:]
		def reasonsNotThrombolysed = findCareActivityProperty('reasonsNotThrombolysed')
		if(reasonsNotThrombolysed && reasonsNotThrombolysed!='[:]'){
			reasonsNotThrombolysed = reasonsNotThrombolysed.replaceAll('[\\[\\]]','')
			reasonsNotThrombolysed.tokenize(",").each{item ->
				if(item.trim().tokenize(":")[1] == "true"){
					result.put item.trim().tokenize(":")[0], true
				}else if(item.trim().tokenize(":")[1] == "false"){
					result.put item.trim().tokenize(":")[0], false
				}else{
					result.put item.trim().tokenize(":")[0], item.trim().tokenize(":")[1]
				}
			}
		}
		return result
	}
	
	
	def findCareActivityProperty = { key ->
		return 	careActivityProperties?."${key}"
	}
	
	def setCareActivityProperty = { key, value ->
		careActivityProperties.put key, value
	}
	
	def clearCareActivityProperty = {key ->
		careActivityProperties.remove key
	}

	def before1stArrival = {date, time ->
		if ( medicalHistory.arrival.transferredFromAnotherHospital == Boolean.TRUE ){
			return false;
		}
		return DateTimeHelper.isBefore(date, time, medicalHistory.arrival.thisHospitalArrivalDate, medicalHistory.arrival.thisHospitalArrivalTime)
	}
	
	def isDateTimeWithin = { date, time, duration ->
		def measurementStartDate = getEffectiveStartDate()
		def measurementStartTime = getEffectiveStartTime()	
		return DateTimeHelper.hoursBetween(measurementStartDate, measurementStartTime, date, time) < duration
	}
	
	def getEffectiveStartDate = {
		if ( medicalHistory?.inpatientAtOnset ) {
			if ( medicalHistory.onsetDateUnknown ) {
				return new Date()
			} else {
				return medicalHistory.onsetDate
			}
		} else if ( medicalHistory?.arrival?.transferredFromAnotherHospital == Boolean.FALSE ){
			return medicalHistory.hospitalAdmissionDate
		} else {
			return medicalHistory?.arrival?.thisHospitalArrivalDate
		}
	}
	
	def getEffectiveStartTime = {
		if ( medicalHistory?.inpatientAtOnset ) {
			if ( medicalHistory.onsetTimeUnknown ) {
				return 0
			} else {
				return medicalHistory.onsetTime
			}
		} else if ( medicalHistory?.arrival?.transferredFromAnotherHospital == Boolean.FALSE ){
			return medicalHistory.hospitalAdmissionTime
		} else {
			return medicalHistory?.arrival?.thisHospitalArrivalTime
		}
	}
	
	def isBeforeAdmissionOrOnset = {date, time ->
		if ( medicalHistory?.inpatientAtOnset ) {
			return beforeOnset(date,time)
		} else if ( medicalHistory?.arrival?.transferredFromAnotherHospital == Boolean.FALSE ){
			return DateTimeHelper.isBefore(date, time, medicalHistory.hospitalAdmissionDate, medicalHistory.hospitalAdmissionTime)			
		} else {
		return DateTimeHelper.isBefore(date, time, medicalHistory?.arrival?.thisHospitalArrivalDate, medicalHistory?.arrival?.thisHospitalArrivalTime)
		}
	}

	def beforeOnset = {date, time ->
		if ( medicalHistory.onsetDateUnknown ){
			return false;
		}
		return DateTimeHelper.isBefore(date, time, medicalHistory.onsetDate, medicalHistory.onsetTime)
	}
	def beforeStrokeUnitAdmission = {date, time ->
		return DateTimeHelper.isBefore(date, time, medicalHistory?.strokeWardAdmissionDate, medicalHistory?.strokeWardAdmissionTime)	
	}
	def beforeAdmission = {date, time ->
		return DateTimeHelper.isBefore(date, time, startDate, startTime)		
	}
	def beforeBirth = {date, time ->
		return DateTimeHelper.isBefore(date, time, patient.dateOfBirth, 0)
	}

	def afterDischarge = {date, time ->
		return DateTimeHelper.isAfter(date, time, endDate, endTime)
	}

	def beforeImaging= {date, time ->
		if ( !imaging?.scan?.takenDate || !imaging?.scan?.takenTime ) {
			return false
		}
		return DateTimeHelper.isBefore(date, time, imaging?.scan?.takenDate, imaging?.scan?.takenTime)
	
	}
	
	def hoursSinceAdmission = {date, time->
		return DateTimeHelper.hoursBetween(startDate, startTime, date, time);		
	}
	def hoursSinceOnset = {date, time->
		return DateTimeHelper.hoursBetween(medicalHistory?.onsetDate, medicalHistory?.onsetTime, date, time);
	}
	def hoursSinceArrival= {date, time->
		return DateTimeHelper.hoursBetween(medicalHistory?.arrival?.thisHospitalArrivalDate, medicalHistory?.arrival?.thisHospitalArrivalTime, date, time);
	}
	
	def addEvaluation = {type ->
		def theEvaluation = null;
		evaluations.each { evaluation ->
			if(evaluation.evaluator == type){
				theEvaluation = evaluation
			}
		}
		if(!theEvaluation){
			addToEvaluations(new Evaluation([evaluator:type]))
		
		}
		return getEvaluation(type)
	}
	
	def getEvaluation = { type ->
		def theEvaluation = null;
		evaluations.each { evaluation ->
			if(evaluation.evaluator == type){
				theEvaluation = evaluation
			}
		}
		return theEvaluation
	}
	
	def  removeEvaluation = {type ->
		def theEvaluation = null;
		evaluations.each { evaluation ->
			if(evaluation.evaluator == type){
				theEvaluation = evaluation
			}
		}
		if(theEvaluation){
			removeFromEvaluations(theEvaluation)
			theEvaluation.delete()
		}
	}
	
	
	
	/*
	def onChange = { oldMap,newMap ->
		rintln "Person was changed ..."
		oldMap.each({ key, oldVal ->
			if (key=="careActivityProperties") {
				rintln "${key}"
				rintln "${oldVal}"
				rintln "${newMap[key]}"
			}
			
			if(oldVal != newMap[key]) {
				rintln " * $key changed from $oldVal to " + newMap[key]
			}
		})
	}
	*/
		
	
}
