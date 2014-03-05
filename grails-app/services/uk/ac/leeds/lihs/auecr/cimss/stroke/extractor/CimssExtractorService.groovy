package uk.ac.leeds.lihs.auecr.cimss.stroke.extractor

import java.util.Date;

import uk.ac.leeds.lihs.auecr.cimss.stroke.CareActivity;
import uk.ac.leeds.lihs.auecr.cimss.stroke.TherapyDetail;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.ComorbidityType;

class CimssExtractorService {

    static transactional = true
	
	static Map therapyMissingMessage = ['Formal Communication':'Formal communication assessment details are missing'
									, 'Formal Swallowing':'Formal swallowing assessment details are missing'
									, 'Occupational Therapy':'Occupational therapy assessment details are missing'
									, 'Physiotherapy':'Physiotherapy assessment details are missing'
									, 'Swallowing Screen':'Swallowing screening details are missing']
	
	static Map therapyFieldStems = ['Formal Communication':'formalCommunication'
									, 'Formal Swallowing':'formalSwallowing'
									, 'Occupational Therapy':'occupationalTherapy'
									, 'Physiotherapy':'physiotherapy'
									, 'Swallowing Screen':'swallowingScreen']
	
	static Map therapyErrorTypes = ['Formal Communication':'formal communication'
		, 'Formal Swallowing':'formal swallowing'
		, 'Occupational Therapy':'occupational therapy'
		, 'Physiotherapy':'physiotherapy'
		, 'Swallowing Screen':'swallowing screening']
	
	
	

    def extractData (CareActivity careActivity) {
		CimssExtract cimssExtract  = new CimssExtract();
		if(careActivity){
			processOnsetAdmissionData(cimssExtract, careActivity)
			processAdmissionAssessmentData(cimssExtract, careActivity)			
			processImagingData(cimssExtract, careActivity)
			processTreatmentData(cimssExtract, careActivity)
			processThrombolysisData(cimssExtract, careActivity)			
			processDischargeData(cimssExtract, careActivity)			
		}
		return cimssExtract
    }

	private processOnsetAdmissionData(CimssExtract cimssExtract, CareActivity careActivity) {
		if(careActivity?.medicalHistory?.previousStroke){
			cimssExtract.previousStroke = Boolean.TRUE
		}else{
			cimssExtract.addError(
					new MissingItem("Onset/Admission : Patient History"
					, "Whether the patient has had a previous stroke is missing"));
		}


		if(careActivity.findCareActivityProperty("strokeBedAvailable")){
			if(careActivity.findCareActivityProperty("strokeBedAvailable") == "true"){
				cimssExtract.strokeBedAvailableAtPresentation=Boolean.TRUE
			}else{
				cimssExtract.strokeBedAvailableAtPresentation=Boolean.FALSE
			}
		}else{
			cimssExtract.addError(
					new MissingItem("Onset/Admission : Stroke Onset"
					, "Whether a stroke bed was available on presentation is missing"));
		}

		if(careActivity?.patientLifeStyle?.livedAlonePreAdmission){
			cimssExtract.livedAlonePreAdmission=Boolean.TRUE
		}else{
			cimssExtract.addError(
					new MissingItem("Onset/Admission : Patient History"
					, "Whether the patient lived alone before admission is missing"));
		}
		
		if(careActivity?.clinicalAssessment?.independent){
			cimssExtract.independentInADLPriorToAdmission = careActivity?.clinicalAssessment?.independent;
		}else{
			cimssExtract.addError(
					new MissingItem("Onset/Admission : Patient History"
					, "Whether the patient was independent in everyday activities before the stroke is missing"));
		}
		
	}

	private processAdmissionAssessmentData(CimssExtract cimssExtract, CareActivity careActivity) {
		processClinicalAssessmentData(cimssExtract, careActivity)
		processContinenceManagementData(cimssExtract, careActivity)
		processTherapyData(cimssExtract, careActivity);
}
		
	private processClinicalAssessmentData(CimssExtract cimssExtract, CareActivity careActivity) {
		processGlasgowComaScore(cimssExtract, careActivity)



		


		if(careActivity?.clinicalAssessment?.leftSideAffected || careActivity?.clinicalAssessment?.rightSideAffected ){
			if(careActivity?.clinicalAssessment?.leftSideAffected == Boolean.TRUE
			&& careActivity?.clinicalAssessment?.rightSideAffected == Boolean.TRUE){
				cimssExtract.sideAffected = "both"
			}else if (careActivity?.clinicalAssessment?.leftSideAffected == Boolean.TRUE){
				cimssExtract.sideAffected = "left"
			}else if (careActivity?.clinicalAssessment?.rightSideAffected == Boolean.TRUE){
				cimssExtract.sideAffected = "right"
			}
		}else{
			if (careActivity?.clinicalAssessment?.neitherSideAffected == Boolean.TRUE){
				cimssExtract.sideAffected = "neither"
			}else{
				cimssExtract.addError(
						new MissingItem("Admission Assessment : Sensory/Motor Features"
						, "The side affected is missing"));
			}
		}





		if(careActivity?.clinicalAssessment?.leftArmMrcScale != null){
			cimssExtract.powerLeftArm =careActivity?.clinicalAssessment?.leftArmMrcScale
		}else{
			cimssExtract.addError(
					new MissingItem("Admission Assessment : Sensory/Motor Features"
					, "The MRC score for the left arm is missing"));
		}
		
		if(careActivity?.clinicalAssessment?.rightArmMrcScale != null){
			cimssExtract.powerRightArm =careActivity?.clinicalAssessment?.rightArmMrcScale
		}else{
			cimssExtract.addError(
					new MissingItem("Admission Assessment : Sensory/Motor Features"
					, "The MRC score for the right arm is missing"));
		}
		
		
		
		
		

		if(careActivity?.clinicalAssessment?.sensoryLoss){
			cimssExtract.sensoryLoss = careActivity?.clinicalAssessment?.sensoryLoss
		}else{
			cimssExtract.addError(
					new MissingItem("Admission Assessment : Sensory/Motor Features"
					, "Whether there was sensory loss on admission is missing"));
		}


		
		



		if(careActivity?.clinicalAssessment?.dominantHand){
			cimssExtract.dominantHand = careActivity?.clinicalAssessment?.dominantHand
		}else{
			cimssExtract.addError(
					new MissingItem("Admission Assessment : Sensory/Motor Features"
					, "The dominant hand is missing"));
		}



		if(careActivity?.clinicalAssessment?.leftLegMrcScale != null){
			cimssExtract.powerLeftLeg =careActivity?.clinicalAssessment?.leftLegMrcScale
		}else{
			cimssExtract.addError(
					new MissingItem("Admission Assessment : Sensory/Motor Features"
					, "The MRC score for the left leg is missing"));
		}
		
		if(careActivity?.clinicalAssessment?.rightLegMrcScale != null){
			cimssExtract.powerRightLeg =careActivity?.clinicalAssessment?.rightLegMrcScale
		}else{
			cimssExtract.addError(
					new MissingItem("Admission Assessment : Sensory/Motor Features"
					, "The MRC score for the right leg is missing"));
		}

		

		if(careActivity?.clinicalAssessment?.walkAtPresentation != null){
			cimssExtract.ableToWalkWithoutAssistanceOnPresentation=careActivity.clinicalAssessment.walkAtPresentation
			if(careActivity.clinicalAssessment.walkAtPresentation==true){
				cimssExtract.mobilePreStroke=Boolean.TRUE
			}else{
				if(careActivity?.clinicalAssessment?.mobilePreStroke != null ){
					cimssExtract.mobilePreStroke=careActivity.clinicalAssessment.mobilePreStroke
				}else{
					cimssExtract.addError(
							new MissingItem("Admission Assessment : Sensory/Motor Features"
							, "Whether the patient could walk without assistance before their stroke is missing"));
				}

			}

		}else{
			cimssExtract.addError(
					new MissingItem("Admission Assessment : Sensory/Motor Features"
					, "Whether the patient walked without assistance on presentation is missing"));
		}

		
		checkTherapyItem(cimssExtract, "Admission Assessment : Swallow Screening", "Swallowing Screen"
				,careActivity?.clinicalAssessment?.swallowingScreenDetail() );








		if(careActivity?.clinicalAssessment?.classification){
			cimssExtract.OCSP = careActivity?.clinicalAssessment?.classification?.description;
		}else{
			cimssExtract.addError(
					new MissingItem("Clinical Summary"
					, "The Oxfordshire Community Stroke Project (OCSP) classification is missing"));
		}


		if(careActivity?.clinicalAssessment?.independent){
			if(careActivity.clinicalAssessment.independent=="yes"){
				cimssExtract.independentInADLPriorToAdmission="yes"
			}else{
				cimssExtract.independentInADLPriorToAdmission=careActivity?.clinicalAssessment?.independent
			}
		}


	
	}

	private processGlasgowComaScore(CimssExtract cimssExtract, CareActivity careActivity) {
		if(careActivity?.clinicalAssessment?.glasgowComaScore?.dateAssessed){
			cimssExtract.GCSDate =careActivity.clinicalAssessment.glasgowComaScore.dateAssessed
		}else{
			cimssExtract.addError(
					new MissingItem("Admission Assessment : Glasgow Coma Score"
					, "The date that the Glasgow Coma Score was recorded is missing"));
		}


		if(careActivity?.clinicalAssessment?.glasgowComaScore?.timeAssessed !=null){
			cimssExtract.GCSTime =careActivity.clinicalAssessment.glasgowComaScore.timeAssessed
		}else{
			cimssExtract.addError(
					new MissingItem("Admission Assessment : Glasgow Coma Score"
					, "The time that the Glasgow Coma Score was recorded is missing"));
		}


		if(careActivity?.clinicalAssessment?.glasgowComaScore?.motorScore){
			cimssExtract.GCSAtPresentation =careActivity.clinicalAssessment.glasgowComaScore.motorScore
			cimssExtract.GCSAtPresentation+=careActivity.clinicalAssessment.glasgowComaScore.eyeScore
			cimssExtract.GCSAtPresentation+=careActivity.clinicalAssessment.glasgowComaScore.verbalScore
		}else{
			cimssExtract.addError(
					new MissingItem("Admission Assessment : Glasgow Coma Score"
					, "The Glasgow Coma Score is missing"));

		}
	}
	
	private processContinenceManagementData(CimssExtract cimssExtract, CareActivity careActivity) {
		if(careActivity?.continenceManagement?.newlyIncontinent != null){
			if(careActivity?.continenceManagement?.newlyIncontinent==Boolean.TRUE){
				cimssExtract.newIncontinence=Boolean.TRUE
			}else{
				cimssExtract.newIncontinence=Boolean.FALSE
			}
		}else{
			cimssExtract.addError(
					new MissingItem("Admission Assessment : Continence"
					, "Whether the patient was newly incontinent is missing"));
		}
	}

	private processTherapyData(CimssExtract cimssExtract, CareActivity careActivity) {
		checkTherapyItem(cimssExtract, "Therapy : Occupational Therapy", "Occupational Therapy"
				,careActivity?.therapyManagement?.occupationalTherapyManagement?.occupationalTherapyDetail() );

		if(careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Baseline")?.score() !=null){
			cimssExtract.baselineBarthel = careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Baseline").score();
		}else{
			if(careActivity?.therapyManagement?.assessmentManagement?.baselineBarthelNotKnown == Boolean.TRUE){
				cimssExtract.baselineBarthelUnknown = Boolean.TRUE;
			}else{
				cimssExtract.addError(
						new MissingItem("Therapy : Baseline Assessments"
						, "The baseline Barthel score is missing"));
			}
		}

		if(careActivity?.therapyManagement?.assessmentManagement?.findModifiedRankinByStage("Baseline")){
			cimssExtract.baselineMrs = careActivity?.therapyManagement?.assessmentManagement?.findModifiedRankinByStage("Baseline")?.modifiedRankinScore;
		}else{
			if(careActivity?.therapyManagement?.assessmentManagement?.baselineModifiedRankinNotKnown == Boolean.TRUE){
				cimssExtract.baselineMrsUnknown = Boolean.TRUE;
			}else{
				cimssExtract.addError(
						new MissingItem("Therapy : Baseline Assessments"
						, "The baseline modified rankin score is missing"));
			}
		}


		checkTherapyItem(cimssExtract, "Therapy : Speech And Language Therapy", "Formal Communication"
				,careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.formalCommunicationTherapyDetail() );

		checkTherapyItem(cimssExtract, "Therapy : Speech And Language Therapy", "Formal Swallowing"
				,careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.formalSwallowingTherapyDetail() );



		checkTherapyItem(cimssExtract, "Therapy : Physiotherapy", "Physiotherapy"
				,careActivity?.therapyManagement?.physiotherapyManagement?.physiotherapyDetail() )
	}

	private processImagingData(CimssExtract cimssExtract, CareActivity careActivity) {
		if(careActivity.imaging){
			if(careActivity.imaging?.scanPostStroke!="null"){
				if(careActivity.imaging?.scanPostStroke=="yes"){
					if(careActivity?.imaging?.scan?.takenDate){
						cimssExtract.imagingDate = careActivity?.imaging?.scan?.takenDate
					}else{
						cimssExtract.addError(
								new MissingItem("Imaging"
								, "The date imaging was taken is missing"));
					}
					if(careActivity?.imaging?.scan?.takenTime !=null){
						cimssExtract.imagingTime = careActivity?.imaging?.scan?.takenTime
					}else{
						cimssExtract.addError(
								new MissingItem("Imaging"
								, "The time imaging was taken is missing"));
					}
					if(careActivity?.imaging?.scan?.diagnosisType){
						cimssExtract.typeOfStroke = careActivity?.imaging?.scan?.diagnosisType?.description
					}else{
						cimssExtract.addError(
								new MissingItem("Imaging"
								, "The type of stroke is missing"));
					}
				}
			}else{
				cimssExtract.addError(
						new MissingItem("Imaging"
						, "The date imaging was taken is missing"));
				cimssExtract.addError(
						new MissingItem("Imaging"
						, "The time imaging was taken is missing"));
				cimssExtract.addError(
						new MissingItem("Imaging"
						, "The type of stroke is missing"));
			}


		}else{
			cimssExtract.addError(
					new MissingItem("Imaging"
					, "The date imaging was taken is missing"));
			cimssExtract.addError(
					new MissingItem("Imaging"
					, "The time imaging was taken is missing"));
			cimssExtract.addError(
					new MissingItem("Imaging"
					, "The type of stroke is missing"));

		}
	}

	private processTreatmentData(CimssExtract cimssExtract,CareActivity careActivity) {
		def antiplatelet
		careActivity.treatments.each{treatment ->
			if (treatment?.type?.description=="Antiplatelet"){
				antiplatelet=treatment
			}
		}
		if(antiplatelet){
			if(antiplatelet.startDate){
				cimssExtract.antiplateletStartDate= antiplatelet.startDate
			}else{
				cimssExtract.addError(
						new MissingItem("Treatments"
						, "Antiplatelet administration start date is missing"));

			}
			if(antiplatelet.startTime){
				cimssExtract.antiplateletStartTime= antiplatelet.startTime
			}else{
				cimssExtract.addError(
						new MissingItem("Treatments"
						, "Antiplatelet administration start time is missing"));
			}

		}else{
			if(careActivity.findCareActivityProperty("AntiplateletContra")){
				cimssExtract.antiplateletContraIndicated = Boolean.TRUE
			}else{
				cimssExtract.addError(
						new MissingItem("Treatments"
						, "Antiplatelet administration details are missing"));
			}
		}
	}

	private processThrombolysisData(CimssExtract cimssExtract,CareActivity careActivity) {
		if(careActivity.careActivityProperties.thrombolysed){
			if(careActivity.thrombolysis){
				cimssExtract.thrombolysisGiven = Boolean.TRUE
			}else{
				cimssExtract.thrombolysisGiven = Boolean.FALSE
			}
		}else{
			cimssExtract.addError(
					new MissingItem("Thrombolysis"
					, "Whether the patient received thrombolysis is missing"));
		}
	}

	private processDischargeData(CimssExtract cimssExtract, CareActivity careActivity) {
//		if(careActivity.fitForDischargeDate){
//			cimssExtract.clinicallyFitForDischargeDate = careActivity.fitForDischargeDate
//		}else{
//			if (careActivity.fitForDischargeDateUnknown == Boolean.TRUE){
//				cimssExtract.clinicallyFitForDischargeUnknown = Boolean.TRUE
//			}else{
//				cimssExtract.addError(
//						new MissingItem("Discharge : Pre discharge"
//						, "Patient considered by multidisciplinary team to no longer require inpatient rehabilitation is missing"));
//			}
//		}
		
		
	
			if(careActivity.socialWorkerReferral){
//				if(careActivity.socialWorkerReferral == 'Yes'){
//					cimssExtract.socialWorkerRequired = Boolean.TRUE
//					if(careActivity.socialWorkerReferralDate){
//						cimssExtract.socialWorkerReferralDate = careActivity.socialWorkerReferralDate
//					}else{
//						if(careActivity.socialWorkerReferralUnknown == Boolean.TRUE){
//							cimssExtract.socialWorkerReferralUnknown = Boolean.TRUE
//						}else{
//							cimssExtract.addError(
//									new MissingItem("Discharge : Pre discharge"
//									, "The date referred to a hospital social worker is missing"));
//						}
//					}
//					if(careActivity.socialWorkerAssessment){
//						if(careActivity.socialWorkerAssessment == 'Yes'){
//							cimssExtract.socialWorkerRequired = Boolean.TRUE
//							if(careActivity.socialWorkerAssessmentDate){
//								cimssExtract.socialWorkerAssessmentDate = careActivity.socialWorkerAssessmentDate
//							}else{
//								if(careActivity.socialWorkerAssessmentUnknown == Boolean.TRUE){
//									cimssExtract.socialWorkerAssessmentUnknown = Boolean.TRUE
//								}else{
//									cimssExtract.addError(
//											new MissingItem("Discharge : Pre discharge"
//											, "The date a hospital social worker carried out the assessment is missing"));
//								}
//							}
//						}else{
//							cimssExtract.socialWorkerRequired = Boolean.FALSE
//						}
//					}else{
//						cimssExtract.addError(
//								new MissingItem("Discharge : Pre discharge"
//								, "Hospital social worker assessment details are missing"));
//					}
//				}else{
//					cimssExtract.socialWorkerRequired = Boolean.FALSE
//				}
			}else if (careActivity.postDischargeCare?.dischargedTo!="died"){
				cimssExtract.addError(
						new MissingItem("Discharge : Pre discharge"
						, "Hospital social worker details are missing"));
			}
		
			
					
		
		
		
		/*

	


	
	
			if(careActivity?.postDischargeCare?.hasSupport("Stroke/neurology specific ESD")){
				cimssExtract.esdReferral = Boolean.TRUE
				if(careActivity.postDischargeCare.esdReferralDate){
					cimssExtract.dateReferredToEsd = careActivity.postDischargeCare.esdReferralDate
				}else{
					if(careActivity.postDischargeCare.esdReferralDateUnknown == Boolean.TRUE){
						cimssExtract.dateReferredToEsdUnknown = Boolean.TRUE
					}else{
						cimssExtract.addError(
								new MissingItem("Discharge : Pre discharge"
								, "The date referred to ESD is missing"));
					}
				}
			}else{
				cimssExtract.esdReferral = Boolean.FALSE
			}
		

		
		
		if(careActivity?.postDischargeCare?.hasSupport("Stroke/neurology specific ESD")){
			cimssExtract.hasStrokeSpecificESD = Boolean.TRUE
		}
		if(careActivity?.postDischargeCare?.hasSupport("Non specialist ESD")){
			cimssExtract.hasNonSpecificESD = Boolean.TRUE
		}
		if(careActivity?.postDischargeCare?.hasSupport("Stroke/neurology specific community rehabilitation team")){
			cimssExtract.hasStrokeSpecificCommunityRehabilitation = Boolean.TRUE
		}
		if(careActivity?.postDischargeCare?.hasSupport("Non specialist community rehabilitation team")){
			cimssExtract.hasNonSpecificCommunityRehabilitation = Boolean.TRUE
		}
		



		if(careActivity?.postDischargeCare?.hasTherapy("ICT")){
			cimssExtract.hasICT = Boolean.TRUE
		}

		if(careActivity?.postDischargeCare?.hasTherapy("ESD")){
			cimssExtract.hasESD = Boolean.TRUE
		}

		if(careActivity?.postDischargeCare?.hasTherapy("Residential Rehab Facility")){
			cimssExtract.hasResidentialRehabilitation = Boolean.TRUE
		}

		if(careActivity?.postDischargeCare?.hasTherapy("Generic Community Rehab")){
			cimssExtract.hasGenericCommunityRehabilitation = Boolean.TRUE
		}


		if(careActivity?.postDischargeCare?.hasSupport("No rehabilitation")){
			cimssExtract.hasNoPlannedTherapy = Boolean.TRUE
		}else if (careActivity?.postDischargeCare?.hasSupport("No ESD")){
			cimssExtract.hasNoPlannedTherapy = Boolean.TRUE
		}else{
			if(!cimssExtract.hasPlannedTherapy()){

				if(cimssExtract.hasGenericCommunityRehabilitation == Boolean.TRUE){
					cimssExtract.hasNoSupport = Boolean.TRUE
				}else{
					cimssExtract.addError(
							new MissingItem("Discharge : Pre discharge"
							, "Planned therapy post discharge details are missing"));
				}

			}
		}


		if ( !(careActivity?.postDischargeCare?.hasSupport("Stroke/neurology specific community rehabilitation team") 
			|| careActivity?.postDischargeCare?.hasSupport("Non specialist community rehabilitation team") 
			|| careActivity?.postDischargeCare?.hasSupport("No rehabilitation")) ) {
			cimssExtract.addError(
				new MissingItem("Discharge : Pre discharge"
				, "Planned rehabilitation discharge details are missing"));

		}


		if(careActivity?.postDischargeCare?.hasSupport("Social Services")){
			cimssExtract.hasSocialServiceSupport = Boolean.TRUE
		}

		if(careActivity?.postDischargeCare?.hasSupport("Informal Carers")){
			cimssExtract.hasInformalCarer = Boolean.TRUE
		}
		if(careActivity?.postDischargeCare?.hasSupport("Homecare package")){
			cimssExtract.hasHomeCarePackage = Boolean.TRUE
		}

		if(careActivity?.postDischargeCare?.hasSupport("Palliative Care")){
			cimssExtract.hasPalliativeCare = Boolean.TRUE
		}


		
		if(careActivity?.postDischargeCare?.supportOnDischargeNeeded == "Yes"){
			if(careActivity?.postDischargeCare?.hasSupport("Social Services Unavailable")
				|| careActivity?.postDischargeCare?.hasSupport("Patient Refused")){
				cimssExtract.hasNoSupport = Boolean.TRUE
			}else{
				if(!cimssExtract.hasPlannedSupport()){
					if(cimssExtract.hasSocialServiceSupport == Boolean.TRUE){
						cimssExtract.hasNoSupport = Boolean.TRUE
					}else{
						cimssExtract.addError(
								new MissingItem("Discharge : Pre discharge"
								, "Support required post discharge details are missing"));
					}
				}
	
			}
			
		}else {
			if(careActivity?.postDischargeCare?.supportOnDischargeNeeded == null){
				cimssExtract.addError(
					new MissingItem("Discharge : Pre discharge"
					, "Support required post discharge details are missing"));
				
			}
		}

		*/

		
		
		
		if(careActivity.postDischargeCare?.dischargedTo){
//			if(careActivity.postDischargeCare?.dischargedTo=="home"){
//				if(careActivity.postDischargeCare?.shelteredAccommodation != null){
//					cimssExtract.shelteredAccommodation = careActivity.postDischargeCare?.shelteredAccommodation
//				}
//				else{
//					cimssExtract.addError(
//							new MissingItem("Discharge : On discharge"
//							, "You must state if the patient is in sheltered or warden controlled accommodation"));
//				}
//			}
			
			
		}else{
			cimssExtract.addError(
				new MissingItem("Discharge : On discharge"
					, "Where the patient was discharged to is missing"));
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	private def checkTherapyItem = { cimssExtract, group, type ,therapyDetail -> 
		if(therapyDetail){
		
			def fieldStem = therapyFieldStems."${therapyDetail.type}"
			def errorType = therapyErrorTypes."${therapyDetail.type}"
			
			if(therapyDetail.wasPerformed !=null){
					if(therapyDetail.wasPerformed  == Boolean.TRUE){
						if(therapyDetail.performedDate){
							cimssExtract."${fieldStem}AssessmentDate" = therapyDetail.performedDate
						}else{
								cimssExtract.addError(
									new MissingItem(group
									, "The date the first ${errorType} assessment was performed is missing"));
						}
						if(therapyDetail.performedTime != null){
							cimssExtract."${fieldStem}AssessmentTime" = therapyDetail.performedTime
						}else{
							cimssExtract.addError(
								new MissingItem(group
								, "The time the first ${errorType} assessment was performed is missing"));
						}
					}else{
						if(therapyDetail.reasonNotPerformed){
							cimssExtract."${fieldStem}NotPerfomedReason"  = therapyDetail.reasonNotPerformed
						}else{
							if (therapyDetail.reasonNotPerformedIn72Hrs != "noproblem" && therapyDetail.reasonNotPerformedIn72Hrs != "passedswallowscreen") {
								cimssExtract.addError(
									new MissingItem(group
										, "The reason the first ${errorType} assessment was not performed is missing"));
							}
						}
					}
				}else{
					if(therapyDetail.reasonNotPerformedIn72Hrs != "noproblem" && therapyDetail.reasonNotPerformedIn72Hrs != "passedswallowscreen"){
						cimssExtract.addError(
							new MissingItem(group
								, therapyMissingMessage."${therapyDetail.type}"));
					}
				}
				
		}else{
					cimssExtract.addError(
						new MissingItem(group
						, therapyMissingMessage."${type}"));
		}
		
	}
		
}

