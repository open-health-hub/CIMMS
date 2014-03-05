package uk.ac.leeds.lihs.auecr.cimss.stroke.extractor

import java.util.List;

class CimssExtract {
	
	Boolean inError = Boolean.FALSE
	List<MissingItem> errorList = []
	
	
	
	//Date dateOfBirth
	Boolean previousStroke
	
	// Admission / discharge data from PAS
	//Date admissionToHospitalDate
	//Integer admissionToHospitalTime
	//String addressOnAdmission
	//TODO what is this field
	//String wardType
	//Date wardAdmissionDate
	//Integer wardAdmissionTime
	//Date wardDischargeDate
	//Integer wardDischargeTime
	//Boolean strokeCodedFCE
	//String addressOnDischarge
	//Date dischargeFromHospitalDate
	//Integer dischargeFromHospitalTime
	//TODO Need to look at this
	//String dischargeResidenceType
	
	
	Date imagingDate
	Integer imagingTime
	
	String typeOfStroke
	
	Boolean thrombolysisGiven

	
	
	//Assessments
	
	String independentInADLPriorToAdmission
	Boolean ableToWalkWithoutAssistanceOnPresentation
	Boolean mobilePreStroke
	
	
	Integer GCSAtPresentation
	Date GCSDate
	Integer GCSTime
	
	
	
	Integer powerLeftArm
	Integer powerLeftLeg
	
	Integer powerRightArm
	Integer powerRightLeg
	
	String sideAffected
	
	String sensoryLoss

	
	
	String dominantHand
	
	Boolean strokeBedAvailableAtPresentation	
	Boolean livedAlonePreAdmission
	
	Boolean newIncontinence
	String OCSP
	
	
	Date antiplateletStartDate
	Integer antiplateletStartTime
	Boolean antiplateletContraIndicated
	
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
	
	Boolean esdReferral
	Boolean dateReferredToEsdUnknown
	Date dateReferredToEsd
	
	
	Integer baselineBarthel
	Boolean baselineBarthelUnknown
	Integer baselineMrs
	Boolean baselineMrsUnknown
		
	
	
	
	
	//Discharge details
	Date clinicallyFitForDischargeDate
	Boolean clinicallyFitForDischargeUnknown
	Date referredToESDDate
	Boolean referredToESDDateUnknown
	
	
	Boolean hasNoPlannedTherapy
	Boolean hasICT
	Boolean hasESD
	Boolean hasResidentialRehabilitation
	Boolean hasGenericCommunityRehabilitation
	
	Boolean hasStrokeSpecificESD
	Boolean hasNonSpecificESD
	Boolean hasStrokeSpecificCommunityRehabilitation
	Boolean hasNonSpecificCommunityRehabilitation
	
	
	
	Boolean hasPlannedTherapy(){
		return  (hasStrokeSpecificESD 
				|| hasNonSpecificESD 
				|| hasStrokeSpecificCommunityRehabilitation 
				|| hasNonSpecificCommunityRehabilitation)
	}
	
	Boolean hasPlannedSupport(){
		return hasInformalCarer || hasHomeCarePackage || hasPalliativeCare
	}
	
	Boolean hasNoSupport
	Boolean hasSocialServiceSupport
	Boolean hasInformalCarer
	Boolean hasHomeCarePackage
	//Boolean hasFamilySupport
	Boolean hasPalliativeCare
	
	//Social Worker interactions
	Date socialWorkerReferralDate
	Boolean socialWorkerReferralUnknown
	
	
	Boolean socialWorkerRequired
	
	Boolean socialWorkerAssessment
	Date socialWorkerAssessmentDate
	Boolean socialWorkerAssessmentUnknown
	
	Boolean shelteredAccommodation
	
	
	
	
	public def addError = {message ->
		this.inError = Boolean.TRUE
		this.errorList.add(message);
	}
	
	
}

public class MissingItem {
	
	String section
	String message
	
	MissingItem(String section, String message){
		this.section = section
		this.message = message
	}
	

}

