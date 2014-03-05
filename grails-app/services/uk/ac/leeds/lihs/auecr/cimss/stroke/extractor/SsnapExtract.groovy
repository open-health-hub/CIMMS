package uk.ac.leeds.lihs.auecr.cimss.stroke.extractor

import java.util.Date;
import java.util.List;

class SsnapExtract {
	
	Boolean inError = Boolean.FALSE
	List<MissingItem> errorList = []
	
	//Onset admission 
	
	Boolean onsetDuringSleep
	Boolean hasDateOfOnset
	Boolean dateOfOnsetEstimated
	Date dateOfOnset
	
	String outputText
	
		
	Boolean hasTimeOfOnset
	Integer timeOfOnset
	Boolean timeOfOnsetEstimated

	
	
	
	Boolean previousStroke
	Boolean previousTIA
	Boolean assessedInVascularClinic
	
	
	Boolean hadCongestiveHeartFailure
	Boolean hadHypertension
	Boolean hadAtrialFibrillation
	Boolean hadDiabetes
	
	
	
	
	Boolean onAntiplateletBefore
	Boolean onAnticoagulantBefore
	
	
	Integer preMorbidMRS
	
	
	
	Integer facialPalsyScore
	Integer dysarthriaScore
	Integer limbAtaxiaScore
	Integer bestVisionScore
	Integer locStimulationScore
	Integer locQuestionsScore
	Integer locTasksScore
	Integer inattentionScore
	Integer bestLanguage
	// sensory / motor Features
	Integer motorArmLeft
	Integer motorArmRight
	Integer motorLegLeft
	Integer motorLegRight
	Integer sensory
	Integer visual
	Integer nihss
	
	// imaging
	Date imagingDate
	Integer imagingTime
	String typeOfImage
	String typeOfStroke
	
	
	
	
	// thrombolysis
	Boolean thrombolysisGiven
	Integer nihssScoreAt24Hours

	
	String givenThrombolysis
	String noThrombolysisReason
	String noThrombolysisReasonHaemorrhagic
	String noThrombolysisReasonOutsideWindow
	String noThrombolysisReasonTooMildOrSevere
	String noThrombolysisReasonContraindicatedMedicine
	String noThrombolysisReasonOnsetUnknown
	String noThrombolysisReasonImproving
	String noThrombolysisReasonAge
	String noThrombolysisReasonComorbidity
	String noThrombolysisReasonRefusal
	Date dateOfThrombolysis
	Integer timeOfThrombolysis
	String complicationsFromThrombolysis
	String complicationsIntraCranial
	String complicationsAngioOedema
	String complicationsExtraCranial
	String complicationsOther
	String complicationsOtherText
	Integer nihssAt24
	
	
	
	
	// Continence
	Boolean inappropriateForContinencePlan
	Boolean hasContinencePlan
	
	
	// nutrition screening (6.10.1)
	Boolean nutritionalScreenHighRisk
	Boolean nutritionalScreenHighRiskUnknown
	Boolean dietitianSeen
	
	
	// therapy
	Date moodAssessmentDate
	Integer moodAssessmentTime
	String moodNotPerfomedReason
	
	
	Date cognitiveAssessmentDate
	Integer cognitiveAssessmentTime
	String cognitiveNotPerfomedReason
	
	
	
	
	Date formalCommunicationAssessmentDate
	Integer formalCommunicationAssessmentTime
	String formalCommunicationNotPerfomedReason
	
	
	Date swallowingScreenAssessmentDate
	Integer swallowingScreenAssessmentTime
	String swallowingScreenNotPerfomedReason
	
	
	Date occupationalTherapyAssessmentDate
	Integer occupationalTherapyAssessmentTime
	String occupationalTherapyNotPerfomedReason
	
	Date formalSwallowingAssessmentDate
	Integer formalSwallowingAssessmentTime
	String formalSwallowingNotPerfomedReason
	
	Date physiotherapyAssessmentDate
	Integer physiotherapyAssessmentTime
	String physiotherapyNotPerfomedReason
	
	
	Integer baselineBarthel
	Boolean baselineBarthelUnknown
	Integer baselineMrs
	Boolean baselineMrsUnknown

	
	Date documentedMDTGoalsAssessmentDate
	Integer documentedMDTGoalsAssessmentTime
	String documentedMDTGoalsNotPerfomedReason
	
	// therapy summary 
	Integer daysOfPhysiotherapy
	Integer daysOfOccupationalTherapy
	Integer daysOfSpeechAndLanguageTherapy
	Integer daysOfPsychology
	Integer daysOfNurseLedTherapy
	
	Integer minutesOfPhysiotherapy
	Integer minutesOfOccupationalTherapy
	Integer minutesOfSpeechAndLanguageTherapy
	Integer minutesOfPsychology
	Integer minutesOfNurseLedTherapy
	

	
	
	//Discharge details
	Date clinicallyFitForDischargeDate
	Boolean clinicallyFitForDischargeUnknown
	String documentedEvidenceDischarge
	Boolean documentationPostDischarge
	Boolean helpActivitiesDl
	Boolean postDischargeSupport
	Integer postDischargeVisit
	Boolean postDischargeVisitUnknown
	String patientDischargedTo
	Boolean dischargeStrokeSpecialist
	String patientDischargedHome
	//Boolean dischargeEsdReferral
	String patientDischargedChomeWith
	Boolean dischargePreviouslyResident
	String dischargeTemporaryOrPerm
	
	
	Boolean hasStrokeSpecificESD
	Boolean hasNonSpecificESD
	Boolean hasStrokeSpecificCommunityRehabilitation
	Boolean hasNonSpecificCommunityRehabilitation
	
	Boolean hasSocialServices
	Boolean hasInformalCarers
	Boolean hasPalliativeCare
	
	Boolean arrivalByAmbulance
	String ambulanceTrust
	String cadNumber
	Boolean cadNumberUnknown
	Boolean outcomeQuestionnairOptOut
	
	Boolean transferredFromAnotherHospital
	Date thisHospitalArrivalDate
	Integer thisHospitalArrivalTime
	
	public int nihss(){
		if (locStimulationScore && locQuestionsScore && locTasksScore && bestVisionScore && visual && facialPalsyScore && motorArmLeft && motorArmRight && motorLegLeft && motorLegRight && limbAtaxiaScore && sensory && bestLanguage && dysarthriaScore && inattentionScore){
			return locStimulationScore + locQuestionsScore + locTasksScore + bestVisionScore + visual + facialPalsyScore + motorArmLeft + motorArmRight + motorLegLeft + motorLegRight + limbAtaxiaScore + sensory + bestLanguage + dysarthriaScore + inattentionScore;
		}
		else {
			return -1
		}
	}
	
	
	
	public def addError = {message ->
		this.inError = Boolean.TRUE
		this.errorList.add(message);
	}

	public def hasPlannedTherapy = {
		return  (hasStrokeSpecificESD 
				|| hasNonSpecificESD 
				|| hasStrokeSpecificCommunityRehabilitation 
				|| hasNonSpecificCommunityRehabilitation)
	}
	
	public def  hasPlannedSupport = {
		return  (hasSocialServices
				|| hasInformalCarers
				|| hasPalliativeCare)
	}
	
	def theDateOfOnsetAccuracy = {
		if(onsetDuringSleep){
			return "Stroke during sleep"
		}else if (dateOfOnsetEstimated){
			return "Best estimate"
		}else if(dateOfOnset){
			return "Precise"
		}else{
			return ""
		}
	}
	
	def theTimeOfOnsetAccuracy = {
		if (timeOfOnsetEstimated){
			return "Best estimate"
		}else if(timeOfOnset !=null){
			return "Precise"
		}else{
			return ""
		}
	}
	
	def hadStrokeOrTIA = {
		if(previousStroke != null ||previousTIA!=null ){
			return previousStroke || previousTIA
		}
		return ""
	}
}


