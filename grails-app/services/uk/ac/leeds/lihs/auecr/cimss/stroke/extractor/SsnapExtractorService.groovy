package uk.ac.leeds.lihs.auecr.cimss.stroke.extractor

import java.util.Map;

import java.util.Date;

import uk.ac.leeds.lihs.auecr.cimss.stroke.CareActivity;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.ComorbidityType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.MedicationType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.StaffRole;

class SsnapExtractorService {
	
	def grailsApplication
	def sessionFactory
	def propertyInstanceMap = org.codehaus.groovy.grails.plugins.DomainClassGrailsPlugin.PROPERTY_INSTANCE_MAP

    static transactional = true

	static Map therapyMissingMessage = ['Formal Communication':'Formal communication assessment details are missing'
		, 'Formal Swallowing':'Formal swallowing assessment details are missing'
		, 'Occupational Therapy':'Occupational therapy assessment details are missing'
		, 'Physiotherapy':'Physiotherapy assessment details are missing'
		, 'Swallowing Screen':'Swallow screening details are missing'
		, 'Mood Assessment':'Mood assessment details are missing'
		, 'Cognitive Assessment':'Cognitive assessment details are missing'
		, 'Documented MDT Goals':'Documented MDT goal details are missing']

	static Map therapyFieldStems = ['Formal Communication':'formalCommunication'
		, 'Formal Swallowing':'formalSwallowing'
		, 'Occupational Therapy':'occupationalTherapy'
		, 'Physiotherapy':'physiotherapy'
		, 'Swallowing Screen':'swallowingScreen'
		, 'Mood Assessment':'mood'
		, 'Cognitive Assessment':'cognitive'
		, 'Documented MDT Goals':'documentedMDTGoals']
	
	static Map therapyErrorTypes = ['Formal Communication':'formal communication'
		, 'Formal Swallowing':'formal swallowing'
		, 'Occupational Therapy':'occupational therapy'
		, 'Physiotherapy':'physiotherapy'
		, 'Swallowing Screen':'swallowing screening'
		, 'Mood Assessment':'mood assessment'
		, 'Cognitive Assessment':'cognitive assessment'
		, 'Documented MDT Goals':'documented mdt goals']

	
	def extractDataForAllCareActivity() {
		def ssnapExtractList = []
		Long lastId = 0
		def careActivityList = getCareActivityList(lastId)
		while (careActivityList) {
			careActivityList.each { careActivity ->
				lastId = careActivity.id
				ssnapExtractList.add(extractData(careActivity))
			}
			sessionFactory.currentSession.clear()
			propertyInstanceMap.get().clear()
			careActivityList = getCareActivityList(lastId)
		}
		return ssnapExtractList
	}
	
	private getCareActivityList(lastId) {
		return CareActivity.createCriteria().list {
			gt("id",lastId)
			maxResults(50)
			order("id","asc")
		}
	}
	
	def extract72HrData (CareActivity careActivity) {
		Boolean _72hr_ = Boolean.TRUE;
		SsnapExtract ssnapExtract  = new SsnapExtract()
		if(careActivity){
		   processArrivalData(ssnapExtract, careActivity)
		   processOnsetAdmissionData(ssnapExtract, careActivity, _72hr_)
		   processAdmissionAssessmentData(ssnapExtract, careActivity, _72hr_)
		   if (! _72hr_ ) {
			   processTherapySummaryData(ssnapExtract, careActivity, _72hr_)
		   }
		   processImagingData(ssnapExtract, careActivity)
		   processThrombolysisData(ssnapExtract, careActivity)
		   processCareSummaryData(ssnapExtract, careActivity, _72hr_)
		   if (! _72hr_ ) {
			   processDischargeData(ssnapExtract, careActivity)
		   }
		   		
		}
		printOut(ssnapExtract)
		return ssnapExtract
	}
	
    def extractData (CareActivity careActivity) {
		Boolean _72hr_ = Boolean.FALSE;
		 SsnapExtract ssnapExtract  = new SsnapExtract()
		 if(careActivity){	
			processArrivalData(ssnapExtract, careActivity)
			processOnsetAdmissionData(ssnapExtract, careActivity, _72hr_)
			processAdmissionAssessmentData(ssnapExtract, careActivity, _72hr_)
			if ( !_72hr_ ) {
				processTherapySummaryData(ssnapExtract, careActivity, _72hr_);
			}
			processImagingData(ssnapExtract, careActivity)
			processThrombolysisData(ssnapExtract, careActivity)
			processCareSummaryData(ssnapExtract, careActivity, _72hr_)
			processDischargeData(ssnapExtract, careActivity)
			
		 
		 }
		 printOut(ssnapExtract)
		 return ssnapExtract
		 
	 }
	 
	 def formatDate = {date ->
		 if(date){
			 return date.format("dd/MM/yyyy")	 
		 }else {
		 	return ""	
		 }
	 }
	 
	 def formatTime = { time ->
		 if(time){
			String format = String.format("%%0%dd", 2);
			return "${String.format(format,(int)time/60)}/${String.format(format,time%60)}"
		}else{
			return ""
		}
	 }
	 
	 private String formatDateTime(Date date, Integer time, String notPresent){
		 def value = notPresent
		 if(date){
			 value = date.format("dd/MM/yyyy")
			 if(time){
				 String format = String.format("%%0%dd", 2);
				 value =  value + " " + "${String.format(format,(int)time/60)}:${String.format(format,time%60)}"
			 }
		 }else {
			 return value
		 }
		 
	 }
	 
	 
	 private String formatInteger(Integer integer){
		 formatInteger(integer,"")
	 }
	 
	 private String formatInteger(Integer integer, String notPresent){
		 if(integer != null){
			return integer.toString()
		}else{
			return notPresent
		}
	 }
	 
	 def asYesNo = { value ->
		 if(value == Boolean.TRUE){
			return "Yes"
		}else if(value == Boolean.FALSE){
			return "No"
		}else{
			return ""
		}
	 }
	 
	 
	 
	 
	 
	 
	 def printOut(SsnapExtract ssnapExtract){
		 
		 def output = new StringBuffer();		 
		 output.append("\"").append(formatDate(ssnapExtract.dateOfOnset)).append("\"")
		 output.append(",\"").append(ssnapExtract.theDateOfOnsetAccuracy()).append("\"")
		 output.append(",\"").append(formatTime(ssnapExtract.timeOfOnset)).append("\"")
		 output.append(",\"").append(ssnapExtract.theTimeOfOnsetAccuracy()).append("\"")	
		 output.append(",\"").append(asYesNo(ssnapExtract.hadCongestiveHeartFailure)).append("\"")
		 output.append(",\"").append(asYesNo(ssnapExtract.hadHypertension)).append("\"")
		 output.append(",\"").append(asYesNo(ssnapExtract.hadAtrialFibrillation)).append("\"")
		 output.append(",\"").append(asYesNo(ssnapExtract.hadDiabetes)).append("\"")
		 output.append(",\"").append(asYesNo(ssnapExtract.hadStrokeOrTIA())).append("\"")
		 output.append(",\"").append(asYesNo(ssnapExtract.onAntiplateletBefore)).append("\"")
		 output.append(",\"").append(asYesNo(ssnapExtract.onAnticoagulantBefore)).append("\"")
		 output.append(",\"").append(formatInteger(ssnapExtract.preMorbidMRS)).append("\"")
		 //output.append(",\"").append(/*"***Calculated NIHSS***"*/).append("\"")
		 output.append(",\"").append(formatInteger( ssnapExtract.locStimulationScore , "Not known")).append("\"")
		 output.append(",\"").append(formatInteger( ssnapExtract.locQuestionsScore , "Not known")).append("\"")
		 output.append(",\"").append(formatInteger( ssnapExtract.locTasksScore , "Not known")).append("\"")
		 output.append(",\"").append(formatInteger( ssnapExtract.bestVisionScore , "Not known")).append("\"")
		 //if (ssnapExtract.visual == 3 || ssnapExtract.visual == null)
		 //	output.append(",\"").append("Not known").append("\"")
		 //else
		 //	output.append(",\"").append(/*"***Visual***"*/ssnapExtract.visual).append("\"")
		 output.append(",\"").append(formatInteger(/*"***Visual***"*/ ssnapExtract.visual , "Not known")).append("\"")
		 output.append(",\"").append(formatInteger( ssnapExtract.facialPalsyScore , "Not known")).append("\"")
		 output.append(",\"").append(formatInteger(ssnapExtract.motorArmLeft, "Not known")).append("\"")
		 output.append(",\"").append(formatInteger(ssnapExtract.motorArmRight, "Not known")).append("\"")
		 output.append(",\"").append(formatInteger(ssnapExtract.motorLegLeft, "Not known")).append("\"")
		 output.append(",\"").append(formatInteger(ssnapExtract.motorLegRight,"Not known")).append("\"")
		 output.append(",\"").append(formatInteger( ssnapExtract.limbAtaxiaScore , "Not known")).append("\"")
		 //output.append(",\"").append(/*"***Sensory***"*/ssnapExtract.sensory).append("\"")
		 output.append(",\"").append(formatInteger(ssnapExtract.sensory , "Not known")).append("\"")
		 output.append(",\"").append(formatInteger(ssnapExtract.bestLanguage , "Not known")).append("\"")		 
		 output.append(",\"").append(formatInteger( ssnapExtract.dysarthriaScore , "Not known")).append("\"")
		 output.append(",\"").append(formatInteger( ssnapExtract.inattentionScore , "Not known")).append("\"")
		 output.append(",\"").append(formatDateTime(ssnapExtract.imagingDate, ssnapExtract.imagingTime, "Not Imaged")).append("\"")
		 output.append(",\"").append(ssnapExtract.givenThrombolysis).append("\"")
		 output.append(",\"").append(ssnapExtract.noThrombolysisReason).append("\"")
		 output.append(",\"").append(ssnapExtract.noThrombolysisReasonHaemorrhagic).append("\"")
		 output.append(",\"").append(ssnapExtract.noThrombolysisReasonOutsideWindow).append("\"")
		 output.append(",\"").append(ssnapExtract.noThrombolysisReasonTooMildOrSevere).append("\"")
		 output.append(",\"").append(ssnapExtract.noThrombolysisReasonContraindicatedMedicine).append("\"")
		 output.append(",\"").append(ssnapExtract.noThrombolysisReasonOnsetUnknown).append("\"")
		 output.append(",\"").append(ssnapExtract.noThrombolysisReasonImproving).append("\"")
		 output.append(",\"").append(ssnapExtract.noThrombolysisReasonAge).append("\"")
		 output.append(",\"").append(ssnapExtract.noThrombolysisReasonComorbidity).append("\"")
		 output.append(",\"").append(ssnapExtract.noThrombolysisReasonRefusal).append("\"")
		 output.append(",\"").append(formatDateTime(ssnapExtract.dateOfThrombolysis, ssnapExtract.timeOfThrombolysis, "")).append("\"")
		 output.append(",\"").append(ssnapExtract.complicationsFromThrombolysis).append("\"")
		 output.append(",\"").append(ssnapExtract.complicationsIntraCranial).append("\"")
		 output.append(",\"").append(ssnapExtract.complicationsAngioOedema).append("\"")
		 output.append(",\"").append(ssnapExtract.complicationsExtraCranial).append("\"")
		 output.append(",\"").append(ssnapExtract.complicationsOther).append("\"")
		 output.append(",\"").append(ssnapExtract.complicationsOtherText).append("\"")
		 output.append(",\"").append(ssnapExtract.nihssAt24).append("\"")
		 
		 ssnapExtract.outputText = output.toString()
		 //println output
	 }
		 
		 
	
	
	
	 Integer inattention
	 
	 
	 
	 
	 private def isComprehensive  = {
		return grailsApplication.config.ssnap.level.equals('comprehensive')
	 }
	 
	 
	  private processMedication(SsnapExtract ssnapExtract, CareActivity careActivity, String type, String message, String field){ 
		 if(careActivity?.medicalHistory?.getMedication(MedicationType.findByDescription(type))){
			 def value = careActivity?.medicalHistory?.getMedication(MedicationType.findByDescription(type)).value
			 ssnapExtract."${field}" = (value == "true" ? Boolean.TRUE : Boolean.FALSE)
		 }else{
			 ssnapExtract.addError(
				 new MissingItem("Onset/Admission : Patient History"
				 , "Whether the patient had " + message + " as a medication on admission is missing"));
		 }
	 }
	  
	  

  
  
	 
	 private processComorbidity(SsnapExtract ssnapExtract, CareActivity careActivity, String type, String message, String field){ 
		 if(careActivity?.medicalHistory?.getComorbidity(ComorbidityType.findByDescription(type))){ 
			 def value = careActivity?.medicalHistory?.getComorbidity(ComorbidityType.findByDescription(type)).value
			 ssnapExtract."${field}" = (value == "true" ? Boolean.TRUE : Boolean.FALSE)
		 }else{
		 	ssnapExtract.addError(
				 new MissingItem("Onset/Admission : Patient History"
				 , "Whether the patient had " + message + " as a comorbidity is missing"));
		 }
	 }

	private processOnsetAdmissionData(SsnapExtract ssnapExtract, CareActivity careActivity, Boolean _72hr_) {
		

		if( careActivity?.medicalHistory?.admissionWard == null || careActivity?.medicalHistory?.admissionWard == "null" ) {

			ssnapExtract.addError(
				new MissingItem("Onset/Admission : Stroke Onset"
				, "You must specify first admission ward"));
		} 
		
		if(careActivity?.medicalHistory?.inpatientAtOnset == null){
			ssnapExtract.addError(
				new MissingItem("Onset/Admission : Stroke Onset"
				, "You must specify if patient was an inpatient at the time of stroke"));
		}
		
		if (careActivity?.medicalHistory?.didNotStayInStrokeWard != Boolean.TRUE) { 
			if(! careActivity?.medicalHistory?.strokeWardAdmissionDate){
				ssnapExtract.addError(
					new MissingItem("Onset/Admission : Stroke Onset"
					, "You must specify date arrived on Stroke Unit"));
			}
			if(! careActivity?.medicalHistory?.strokeWardAdmissionTime){
				ssnapExtract.addError(
					new MissingItem("Onset/Admission : Stroke Onset"
					, "You must specify time arrived on Stroke Unit"));
			}
		}
		
		if(careActivity?.medicalHistory?.previousStroke){
			if(careActivity?.medicalHistory?.previousStroke=="yes"){
				ssnapExtract.previousStroke = Boolean.TRUE
			}else if (careActivity?.medicalHistory?.previousStroke=="no"){
				ssnapExtract.previousStroke = Boolean.FALSE
			}
		}else{
			ssnapExtract.addError(
					new MissingItem("Onset/Admission : Patient History"
					, "Whether the patient has had a previous stroke is missing"));
		}
		
		
		
		if(careActivity?.medicalHistory?.previousTia){
			if(careActivity?.medicalHistory?.previousTia=="yes" ||careActivity?.medicalHistory?.previousTia =="yesWithinMonth"){
				ssnapExtract.previousTIA = Boolean.TRUE
			}else if (careActivity?.medicalHistory?.previousTia=="no"){
				ssnapExtract.previousTIA = Boolean.FALSE
			}
			if(isComprehensive()){
				if(careActivity?.medicalHistory?.previousTia=='yesWithinMonth'){
					ssnapExtract.assessedInVascularClinic  = careActivity?.medicalHistory?.assessedInVascularClinic
					if(ssnapExtract.assessedInVascularClinic == null){
						ssnapExtract.addError(
							new MissingItem("Onset/Admission : Patient History"
							, "Whether the patient had their TIA dignosed in a vascular clinic is missing"));
					}
				}
			}
		}else{
			ssnapExtract.addError(
					new MissingItem("Onset/Admission : Patient History"
					, "Whether the patient has had a previous tia is missing"));
		}
		

		
		processComorbidity(ssnapExtract, careActivity, "diabetes", "diabetes" , "hadDiabetes");
		processComorbidity(ssnapExtract, careActivity, "atrial fibrillation", "atrial fibrillation" , "hadAtrialFibrillation");
		processComorbidity(ssnapExtract, careActivity, "hypertension", "hypertension" , "hadHypertension");
		processComorbidity(ssnapExtract, careActivity, "congestive heart failure", "congestive heart failure" , "hadCongestiveHeartFailure");
		
		processMedication(ssnapExtract, careActivity, "warfarin", "anticoagulation","onAnticoagulantBefore");
		processMedication(ssnapExtract, careActivity, "antiplatelet", "antiplatelet","onAntiplateletBefore" );
		
		
		
		
	

		if(isComprehensive()){
			if((!careActivity?.therapyManagement?.assessmentManagement?.preAdmissionBarthelNotKnown)
				&& !(careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Pre-admission"))){
				ssnapExtract.addError(
					new MissingItem("Onset/Admission : Pre-Morbid Assessments"
					, "You must state the pre morbid Barthel assessment"));
			}
		}
			
			
		if((!careActivity?.therapyManagement?.assessmentManagement?.preAdmissionModifiedRankinNotKnown==Boolean.TRUE)
				&& !(careActivity?.therapyManagement?.assessmentManagement?.findModifiedRankinByStage("Pre-admission"))){
				ssnapExtract.addError(
					new MissingItem("Onset/Admission : Pre-Morbid Assessments"
					, "You must state the pre morbid Modified Rankin Score"));
		}else{
			ssnapExtract.preMorbidMRS = careActivity?.therapyManagement?.assessmentManagement?.findModifiedRankinByStage("Pre-admission")?.modifiedRankinScore
		
		}
		
		
		
		
		
		
		if(careActivity?.medicalHistory?.onsetDate 
			|| careActivity?.medicalHistory?.onsetDateUnknown == Boolean.TRUE){
			ssnapExtract.hasDateOfOnset = Boolean.TRUE	
			ssnapExtract.dateOfOnset = careActivity?.medicalHistory?.onsetDate
			ssnapExtract.dateOfOnsetEstimated =  careActivity?.medicalHistory?.onsetDateEstimated
		}else{
			ssnapExtract.addError(
				new MissingItem("Onset/Admission : Stroke Onset"
				, "You must specify the date of symptom onset"));
		}
		
		if(careActivity?.medicalHistory?.duringSleep == Boolean.TRUE){
			ssnapExtract.onsetDuringSleep = Boolean.TRUE
		} else if ( careActivity?.medicalHistory?.duringSleep != Boolean.FALSE){
			ssnapExtract.addError(
				new MissingItem("Onset/Admission : Stroke Onset"
					, "You must specify if stroke onset occured during sleep"));

		}
		
	 
		
		
		
		if(careActivity?.medicalHistory?.onsetTime !=null
			|| careActivity?.medicalHistory?.onsetTimeUnknown == Boolean.TRUE){
			ssnapExtract.hasTimeOfOnset = Boolean.TRUE
			ssnapExtract.timeOfOnset = careActivity?.medicalHistory?.onsetTime
			ssnapExtract.timeOfOnsetEstimated = careActivity?.medicalHistory?.onsetTimeEstimated
			
		}else{
			ssnapExtract.addError(
				new MissingItem("Onset/Admission : Stroke Onset"
				, "You must specify the time of symptom onset"));
		}
		
		
		
		def evaluation  = careActivity.getEvaluation(StaffRole.findByDescription("Stroke consultant") )
		if(!evaluation){
			def notSeen = careActivity.findCareActivityProperty("Stroke consultant not seen") 
			if(!notSeen){
				ssnapExtract.addError(
					new MissingItem("Onset/Admission : Stroke Onset"
					, "You must specify whether patient has been assessed by stroke consultant"));
			}
		}
		
		evaluation  = careActivity.getEvaluation(StaffRole.findByDescription("Ward based nurse") )
		if(!evaluation){
			def notSeen = careActivity.findCareActivityProperty("Ward based nurse not seen")
			if(!notSeen){
				ssnapExtract.addError(
					new MissingItem("Onset/Admission : Stroke Onset"
					, "You must specify whether patient has been assessed by a nurse trained in stroke management"));
			}
		}
		
	}

	private processAdmissionAssessmentData(SsnapExtract ssnapExtract, CareActivity careActivity, Boolean _72hr_) {
		
		processClinicalAssessmentData(ssnapExtract, careActivity, _72hr_)
		if (! _72hr_ ) {
			processContinenceManagementData(ssnapExtract, careActivity)
		
			processNutritionManagementData(ssnapExtract, careActivity)
		}
		processTherapyData(ssnapExtract, careActivity, _72hr_);
	}
	
	
	private processClinicalAssessmentData(SsnapExtract ssnapExtract, CareActivity careActivity, Boolean _72hr_) {
		

		if(careActivity?.clinicalAssessment?.facialPalsy){
			ssnapExtract.facialPalsyScore  = careActivity?.clinicalAssessment?.calculateNihssScore('facialPalsy')
		}else{
			ssnapExtract.addError(
				new MissingItem("Admission Assessment : Sensory/Motor Features"
				, "The facial palsy test result is missing"));

		}
		
		
	

		if(careActivity?.clinicalAssessment?.sensoryLoss){
			//ssnapExtract.sensoryLossInFace = careActivity?.clinicalAssessment?.faceSensoryLoss			
			switch(careActivity?.clinicalAssessment?.sensoryLoss) {
				case "normal":
					ssnapExtract.sensory = 0;
					break;
				case "mild":
					ssnapExtract.sensory = 1;
					break;
				case "severe":
					ssnapExtract.sensory = 2;
					break;				
			}
		}else{
			ssnapExtract.addError(
					new MissingItem("Admission Assessment : Sensory/Motor Features"
					, "Whether there was sensory loss on admission is missing"));

		}


		if(careActivity?.clinicalAssessment?.leftArmMrcScale != null){
			//cimssExtract.powerArm =careActivity?.clinicalAssessment?.armMrcScale
			ssnapExtract.motorArmLeft=careActivity?.clinicalAssessment?.leftArmMrcScale
		}else{
			ssnapExtract.addError(
					new MissingItem("Admission Assessment : Sensory/Motor Features"
					, "The MRC score for the left arm is missing"));
		}
		
		if(careActivity?.clinicalAssessment?.rightArmMrcScale != null){
		//cimssExtract.powerArm =careActivity?.clinicalAssessment?.armMrcScale
			ssnapExtract.motorArmRight=careActivity?.clinicalAssessment?.rightArmMrcScale
		}else{
			ssnapExtract.addError(
					new MissingItem("Admission Assessment : Sensory/Motor Features"
					, "The MRC score for the right arm is missing"));
		}


		if(careActivity?.clinicalAssessment?.leftLegMrcScale != null){
			//cimssExtract.powerLeg =careActivity?.clinicalAssessment?.legMrcScale
			ssnapExtract.motorLegLeft=careActivity?.clinicalAssessment?.leftLegMrcScale
		}else{
			ssnapExtract.addError(
					new MissingItem("Admission Assessment : Sensory/Motor Features"
					, "The MRC score for the left leg is missing"));
		}

		if(careActivity?.clinicalAssessment?.rightLegMrcScale != null){
			//cimssExtract.powerLeg =careActivity?.clinicalAssessment?.legMrcScale
			ssnapExtract.motorLegRight=careActivity?.clinicalAssessment?.rightLegMrcScale
		}else{
			ssnapExtract.addError(
					new MissingItem("Admission Assessment : Sensory/Motor Features"
					, "The MRC score for the right leg is missing"));
		}


		processOtherFeatures(ssnapExtract, careActivity)
		processSwallowScreening(careActivity, ssnapExtract)
		
		
		
		
		
		
		
		
		


	}

	private processSwallowScreening(CareActivity careActivity, SsnapExtract ssnapExtract) {
		def theDetail = careActivity?.clinicalAssessment?.swallowingScreenDetail()

		if(theDetail){

			
			checkTherapyItem(ssnapExtract
					, "Admission Assessment : Swallow Screening", "Swallowing Screen"
					,theDetail , true, false)
			
			if(theDetail.hoursSinceAdmission > 4 && !careActivity?.clinicalAssessment?.noSwallowScreenPerformedReasonAt4Hours  ){
				ssnapExtract.addError(
						new MissingItem("Admission Assessment : Swallow Screening"
						, "The reason the first Swallow Screening assessment was not performed within 4 hours is missing"));
			}
			if(theDetail.hoursSinceAdmission > 72 && !careActivity?.clinicalAssessment?.noSwallowScreenPerformedReason  ){
				ssnapExtract.addError(
						new MissingItem("Admission Assessment : Swallow Screening"
						, "The reason the first Swallow Screening assessment was not performed within 72 hours is missing"));
			}
		}else{
			ssnapExtract.addError(
					new MissingItem("Admission Assessment : Swallow Screening"
					, "Swallow screening details are missing"));
		}
	}

	private processOtherFeatures(SsnapExtract ssnapExtract, CareActivity careActivity) {
		if(careActivity?.clinicalAssessment?.bestGaze){


			ssnapExtract.bestVisionScore  = careActivity?.clinicalAssessment?.calculateNihssScore('bestGaze')
		}else{
			ssnapExtract.addError(
					new MissingItem("Admission Assessment : Other Features"
					, "Best gaze is missing"));

		}

		if(careActivity?.clinicalAssessment?.dysarthria){
			ssnapExtract.dysarthriaScore  = careActivity?.clinicalAssessment?.calculateNihssScore('dysarthria')
		}else{
			ssnapExtract.addError(
					new MissingItem("Admission Assessment : Other Features"
					, "Dysarthria is missing"));

		}


		if(careActivity?.clinicalAssessment?.hemianopia){
			//ssnapExtract.sensoryLossInFace = careActivity?.clinicalAssessment?.faceSensoryLoss
			switch(careActivity?.clinicalAssessment?.hemianopia) {
				case "unable":
					ssnapExtract.visual = 0;
					break;
				case "partial":
					ssnapExtract.visual = 1;
					break;
				case "complete":
					ssnapExtract.visual = 2;
					break;				
			}
			
		}else{
			ssnapExtract.addError(
					new MissingItem("Admission Assessment : Other Features"
					, "Hemianopia is missing"));

		}

		if(careActivity?.clinicalAssessment?.inattention){
			ssnapExtract.inattentionScore  = careActivity?.clinicalAssessment?.calculateNihssScore('inattention')
		}else{
			ssnapExtract.addError(
					new MissingItem("Admission Assessment : Other Features"
					, "Inattention is missing"));

		}


		if(careActivity?.clinicalAssessment?.limbAtaxia){
			ssnapExtract.limbAtaxiaScore  = careActivity?.clinicalAssessment?.calculateNihssScore('limbAtaxia')
		}else{
			ssnapExtract.addError(
					new MissingItem("Admission Assessment : Other Features"
					, "Limb ataxia is missing"));

		}



		if(careActivity?.clinicalAssessment?.locStimulation){
			ssnapExtract.locStimulationScore  = careActivity?.clinicalAssessment?.calculateNihssScore('locStimulation')
		}else{
			ssnapExtract.addError(
					new MissingItem("Admission Assessment : Other Features"
					, "Level of consciousness stimulation test results is missing"));

		}


		if(careActivity?.clinicalAssessment?.locQuestions){
			ssnapExtract.locQuestionsScore  = careActivity?.clinicalAssessment?.calculateNihssScore('locQuestions')
		}else{
			ssnapExtract.addError(
					new MissingItem("Admission Assessment : Other Features"
					, "Level of consciousness questions test results is missing"));

		}


		if(careActivity?.clinicalAssessment?.locTasks){
			ssnapExtract.locTasksScore  = careActivity?.clinicalAssessment?.calculateNihssScore('locTasks')
		}else{
			ssnapExtract.addError(
					new MissingItem("Admission Assessment : Other Features"
					, "Level of consciousness task test results is missing"));

		}


		if(careActivity?.clinicalAssessment?.aphasia){
			//ssnapExtract.sensoryLossInFace = careActivity?.clinicalAssessment?.faceSensoryLoss			
			switch(careActivity?.clinicalAssessment?.aphasia) {
				case "normal":
					ssnapExtract.bestLanguage = 0;
					break;
				case "mild":
					ssnapExtract.bestLanguage = 1;
					break;
				case "severe":
					ssnapExtract.bestLanguage = 2;
					break;
				case "global":
					ssnapExtract.bestLanguage = 3;
					break;
			}
		}else{
			ssnapExtract.addError(
					new MissingItem("Admission Assessment : Other Features"
					, "Aphasia is missing"));

		}
		ssnapExtract.nihss = ssnapExtract.nihss(); 
	}
	
	private processArrivalData(SsnapExtract ssnapExtract, CareActivity careActivity) {
		
		
		ssnapExtract.ambulanceTrust = careActivity?.medicalHistory?.arrival?.ambulanceTrust?.code
		ssnapExtract.transferredFromAnotherHospital = careActivity?.medicalHistory?.arrival?.transferredFromAnotherHospital
		ssnapExtract.thisHospitalArrivalDate = careActivity?.medicalHistory?.arrival?.thisHospitalArrivalDate
		ssnapExtract.thisHospitalArrivalTime = careActivity?.medicalHistory?.arrival?.thisHospitalArrivalTime		
		ssnapExtract.arrivalByAmbulance = careActivity?.medicalHistory?.arrival?.arriveByAmbulance
		ssnapExtract.cadNumber = careActivity?.medicalHistory?.arrival?.cadNumber
		ssnapExtract.cadNumberUnknown = careActivity?.medicalHistory?.arrival?.cadNumberUnknown?.toString()
		ssnapExtract.outcomeQuestionnairOptOut = careActivity?.medicalHistory?.arrival?.outcomeQuestionnairOptOut?.toString()
		
		if (ssnapExtract.thisHospitalArrivalDate == null ) {
			ssnapExtract.addError(
				new MissingItem("Onset/Admission : Patient History"
				, "You must enter the date of arrival of this patient to this hospital"));
		}
		if (ssnapExtract.thisHospitalArrivalTime == null ) {
			ssnapExtract.addError(
				new MissingItem("Onset/Admission : Patient History"
				, "You must enter the time of arrival of this patient to this hospital"));
		}
		if(ssnapExtract.transferredFromAnotherHospital == null ){
			ssnapExtract.addError(
				new MissingItem("Onset/Admission : Patient History"
				, "You must state whether the patient was transferred from another hospital"));
		}

		if(ssnapExtract.arrivalByAmbulance == null ){
			ssnapExtract.addError(
				new MissingItem("Onset/Admission : Patient History"
				, "You must state whether the patient arrived by ambulance"));
		} else if ( ssnapExtract.arrivalByAmbulance == Boolean.TRUE ) {
			if (ssnapExtract.ambulanceTrust == null) {
				ssnapExtract.addError(
					new MissingItem("Onset/Admission : Patient History"
					, "You must state the ambulance trust"));	
			}
			if (ssnapExtract.cadNumber == null && ssnapExtract.cadNumberUnknown == null) {
				ssnapExtract.addError(
					new MissingItem("Onset/Admission : Patient History"
					, "You must enter the CAD (Computer Aided Despatch)/Ambulance number"));
			}
		}
		
	}
	
	private processContinenceManagementData(SsnapExtract ssnapExtract, CareActivity careActivity) {
		
		ssnapExtract.inappropriateForContinencePlan = careActivity?.continenceManagement?.inappropriateForContinencePlan
		ssnapExtract.hasContinencePlan = careActivity?.continenceManagement?.hasContinencePlan
		
		if(ssnapExtract.inappropriateForContinencePlan != Boolean.TRUE 
			&& ssnapExtract.hasContinencePlan == null ){		
			ssnapExtract.addError(
				new MissingItem("Admission Assessment : Continence"
				, "Whether the patient has had a continence plan is missing"));
		}
		
	}
	
	private processNutritionManagementData(SsnapExtract ssnapExtract, CareActivity careActivity) {
		
		if(careActivity?.nutritionManagement?.unableToScreen!=Boolean.TRUE){
			if (careActivity?.nutritionManagement?.mustScore >= 2){
				ssnapExtract.nutritionalScreenHighRisk = Boolean.TRUE
			}else if(careActivity?.nutritionManagement?.mustScore != null  &&  careActivity?.nutritionManagement?.mustScore < 2){
				ssnapExtract.nutritionalScreenHighRisk = Boolean.FALSE
			}else{
				ssnapExtract.addError(
					new MissingItem("Admission Assessment : Nutrition"
					, "Whether the patient has had a nutritional screening is missing"));
			}
			
			if (ssnapExtract.nutritionalScreenHighRisk == Boolean.TRUE){
				
				
				if(careActivity?.nutritionManagement?.dietitianReferralDate){
					ssnapExtract.dietitianSeen = Boolean.TRUE
				}else{
					ssnapExtract.dietitianSeen = Boolean.FALSE
				}
				
				
				if(careActivity?.nutritionManagement?.dietitianNotSeen != Boolean.TRUE && ssnapExtract.dietitianSeen!=Boolean.TRUE){
					ssnapExtract.addError(
						new MissingItem("Admission Assessment : Nutrition"
						, "For high risk patients the dietician referral details must be provided"));
				}
				
				
				
				
				
			}
			
			
		}
		
		
	}

	private processTherapyData(SsnapExtract ssnapExtract, CareActivity careActivity, Boolean _72hr_) {
		checkTherapyItem(ssnapExtract, "Therapy : Occupational Therapy", "Occupational Therapy"
				,careActivity?.therapyManagement?.occupationalTherapyManagement?.occupationalTherapyDetail() , true, _72hr_)

		if(careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Baseline")?.score() !=null){
			ssnapExtract.baselineBarthel = careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Baseline").score();
		}else{
			if(careActivity?.therapyManagement?.assessmentManagement?.baselineBarthelNotKnown == Boolean.TRUE){
				ssnapExtract.baselineBarthelUnknown = Boolean.TRUE;
			}else{
				/*ssnapExtract.addError(
						new MissingItem("Therapy : Baseline Assessments"
						, "The baseline Barthel score is missing"));*/
			}
		}

		if(careActivity?.therapyManagement?.assessmentManagement?.findModifiedRankinByStage("Baseline")){
			ssnapExtract.baselineMrs = careActivity?.therapyManagement?.assessmentManagement?.findModifiedRankinByStage("Baseline")?.modifiedRankinScore;
		}else{
			if(careActivity?.therapyManagement?.assessmentManagement?.baselineModifiedRankinNotKnown == Boolean.TRUE){
				ssnapExtract.baselineMrsUnknown = Boolean.TRUE;
			}else{
				/*ssnapExtract.addError(
						new MissingItem("Therapy : Baseline Assessments"
						, "The baseline modified rankin score is missing"));*/
			}
		}


		checkTherapyItem(ssnapExtract, "Therapy : Speech And Language Therapy", "Formal Communication"
				,careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.formalCommunicationTherapyDetail() ,  true, _72hr_);

		checkTherapyItem(ssnapExtract, "Therapy : Speech And Language Therapy", "Formal Swallowing"
				,careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.formalSwallowingTherapyDetail() ,  true, _72hr_);



		checkTherapyItem(ssnapExtract, "Therapy : Physiotherapy", "Physiotherapy"
				,careActivity?.therapyManagement?.physiotherapyManagement?.physiotherapyDetail() ,  true, _72hr_)
		
		if(!_72hr_) {
		checkTherapyItem(ssnapExtract, "Therapy : Occupational Therapy", "Mood Assessment"
			,careActivity?.therapyManagement?.occupationalTherapyManagement?.moodAssessmentDetail() , false, false)
		
		checkTherapyItem(ssnapExtract, "Therapy : Occupational Therapy", "Cognitive Assessment"
			,careActivity?.therapyManagement?.cognitiveAssessmentDetail(), false, false )
		
		
		checkTherapyItem(ssnapExtract, "Therapy : MDT Goals", "Documented MDT Goals"
 			,careActivity?.therapyManagement?.mdtRehabilitationGoalsDetail(),false, false)
 		
		}
	}
	
	
	
	
	private processTherapySummaryData(SsnapExtract ssnapExtract, CareActivity careActivity, Boolean _72hr_) {
		
		if(careActivity?.therapyManagement?.physiotherapyManagement?.therapyRequired  == null ){
			ssnapExtract.addError(
				new MissingItem("Therapy : Therapy Summary"
				, "Whether the patient required physiotherapy at any point during this admission is missing"));
		}
		
		if(careActivity?.therapyManagement?.occupationalTherapyManagement?.therapyRequired  == null ){
			ssnapExtract.addError(
				new MissingItem("Therapy : Therapy Summary"
				, "Whether the patient required occupational therapy at any point during this admission is missing"));
		}
		
		if(careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.therapyRequired  == null ){
			ssnapExtract.addError(
				new MissingItem("Therapy : Therapy Summary"
				, "Whether the patient required speech and language therapy at any point during this admission is missing"));
		}
		
		if(careActivity?.therapyManagement?.pyschologyTherapyRequired  == null ){
			ssnapExtract.addError(
				new MissingItem("Therapy : Therapy Summary"
				, "Whether the patient required psychology at any point during this admission is missing"));
		}
		
		
		
		ssnapExtract.daysOfPhysiotherapy = careActivity?.therapyManagement?.physiotherapyManagement?.daysOfTherapy
		ssnapExtract.daysOfOccupationalTherapy = careActivity?.therapyManagement?.occupationalTherapyManagement?.daysOfTherapy
		ssnapExtract.daysOfSpeechAndLanguageTherapy = careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.daysOfTherapy
		ssnapExtract.daysOfPsychology = careActivity?.therapyManagement?.pyschologyDaysOfTherapy
		
		ssnapExtract.minutesOfPhysiotherapy = careActivity?.therapyManagement?.physiotherapyManagement?.minutesOfTherapy
		ssnapExtract.minutesOfOccupationalTherapy = careActivity?.therapyManagement?.occupationalTherapyManagement?.minutesOfTherapy
		ssnapExtract.minutesOfSpeechAndLanguageTherapy = careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.minutesOfTherapy
		ssnapExtract.minutesOfPsychology = careActivity?.therapyManagement?.pyschologyMinutesOfTherapy
		
		
		
		if(ssnapExtract.daysOfPhysiotherapy  == null ){
			ssnapExtract.addError(
				new MissingItem("Therapy : Therapy Summary"
				, "The number of days of physiotherapy must be provided"));
		}
		
		if(ssnapExtract.daysOfOccupationalTherapy  == null ){
			ssnapExtract.addError(
				new MissingItem("Therapy : Therapy Summary"
				, "The number of days of occupational therapy must be provided"));
		}
		
		if(ssnapExtract.daysOfSpeechAndLanguageTherapy  == null ){
			ssnapExtract.addError(
				new MissingItem("Therapy : Therapy Summary"
				, "The number of days of speech and language therapy must be provided"));
		}
		
		if(ssnapExtract.daysOfPsychology  == null ){
			ssnapExtract.addError(
				new MissingItem("Therapy : Therapy Summary"
				, "The number of days of psychology must be provided"));
		}
		
		
		
		
		
		if(ssnapExtract.minutesOfPhysiotherapy  == null ){
			ssnapExtract.addError(
				new MissingItem("Therapy : Therapy Summary"
				, "The number of minutes of physiotherapy must be provided"));
		}
		
		if(ssnapExtract.minutesOfOccupationalTherapy  == null ){
			ssnapExtract.addError(
				new MissingItem("Therapy : Therapy Summary"
				, "The number of minutes of occupational therapy must be provided"));
		}
		
		if(ssnapExtract.minutesOfSpeechAndLanguageTherapy  == null ){
			ssnapExtract.addError(
				new MissingItem("Therapy : Therapy Summary"
				, "The number of minutes of speech and language therapy must be provided"));
		}
		
		if(ssnapExtract.minutesOfPsychology  == null ){
			ssnapExtract.addError(
				new MissingItem("Therapy : Therapy Summary"
				, "The number of minutes of psychology must be provided"));
		}
		
		
		
		
		
		
		
		
		
		
	}
	
	
	private processImagingData(SsnapExtract ssnapExtract, CareActivity careActivity) {
		
		if(careActivity.imaging){
			if(careActivity.imaging?.scanPostStroke!="null"){
				if(careActivity.imaging?.scanPostStroke=="yes"){
					if(careActivity?.imaging?.scan?.takenDate){
						ssnapExtract.imagingDate = careActivity?.imaging?.scan?.takenDate
					}else{
						ssnapExtract.addError(
							new MissingItem("Imaging"
								, "The date the initial imaging was taken is missing"));
					}
					if(careActivity?.imaging?.scan?.takenTime != null){
						ssnapExtract.imagingTime = careActivity?.imaging?.scan?.takenTime
					}else{
						ssnapExtract.addError(
							new MissingItem("Imaging"
								, "The time the initial imaging was taken is missing"));
					}
					
					if ( careActivity?.medicalHistory?.previousTia =="yesWithinMonth" ){
						if(careActivity?.imaging?.scan?.imageType){
							ssnapExtract.typeOfImage = careActivity?.imaging?.scan?.imageType?.description
						}else{
							if(isComprehensive()){
								ssnapExtract.addError(
								new MissingItem("Imaging"
									, "The type of the initial image is missing"));
							}
						}
					}
					if(careActivity.imaging.scan.diagnosisCategory) {
						
						if(careActivity.imaging.scan.diagnosisCategory.description == "Stroke") {
							if(careActivity?.imaging?.scan?.diagnosisType){
								ssnapExtract.typeOfStroke = careActivity?.imaging?.scan?.diagnosisType?.description
							}else{
								ssnapExtract.addError(
									new MissingItem("Imaging"
										, "The type of stroke is missing"));
							}
						}
					} else {
						ssnapExtract.addError(
							new MissingItem("Imaging"
							, "Please state diagnosis type"));

					}
					
				}
			}else{
				ssnapExtract.addError(
							new MissingItem("Imaging"
								, "The date the initial imaging was taken is missing"));
				ssnapExtract.addError(
							new MissingItem("Imaging"
								, "The time the initial imaging was taken is missing"));
				ssnapExtract.addError(
							new MissingItem("Imaging"
								, "The type of stroke is missing"));
			}
			
			
		}else{
				ssnapExtract.addError(
							new MissingItem("Imaging"
								, "The date the initial imaging was taken is missing"));
				ssnapExtract.addError(
							new MissingItem("Imaging"
								, "The time the initial imaging was taken is missing"));
				ssnapExtract.addError(
							new MissingItem("Imaging"
								, "The type of stroke is missing"));
		
		}
		
	}
	
	private processThrombolysisData(SsnapExtract ssnapExtract, CareActivity careActivity) {
		
		if(careActivity.careActivityProperties.thrombolysed){
			if(careActivity.thrombolysis){
				ssnapExtract.thrombolysisGiven = Boolean.TRUE
				ssnapExtract.givenThrombolysis = "Yes"
				
				if (careActivity.thrombolysis.complications) {
					if ( !careActivity.thrombolysis.complicationTypeBleed
						&& !careActivity.thrombolysis.complicationTypeHaemorrhage
						&& !careActivity.thrombolysis.complicationTypeOedema
						&& !careActivity.thrombolysis.complicationTypeOther) {
						ssnapExtract.addError(
							new MissingItem("Thrombolysis"
								, "You must state which thrombolysis complications"));
					}
					else if ( careActivity.thrombolysis.complicationTypeOther 
						&& (!complicationTypeOtherText || complicationTypeOtherText.isAllWhitespace() ) ){
						ssnapExtract.addError(
							new MissingItem("Thrombolysis"
								, "You must state which Other thrombolysis complication"));
							
					}
				}
				
				if(careActivity.thrombolysis.nihssScoreAt24Hours != null){
					ssnapExtract.nihssScoreAt24Hours = careActivity.thrombolysis.nihssScoreAt24Hours
				}else if(!careActivity.thrombolysis.nihssScoreAt24HoursUnknown== Boolean.TRUE){
					ssnapExtract.addError(
				new MissingItem("Thrombolysis"
					, "The NIHSS Score at 24 hours must be provided"));
				}
				
				
				
			}else{
				ssnapExtract.thrombolysisGiven = Boolean.FALSE
				def reasons = careActivity.getReasonsNotThrombolysed()
				if(reasons.noThrombolysisNotAvailable==true 
					|| reasons.noThrombolysisOutsideHours==true 
						|| reasons.noThrombolysisScanLate==true ){
					ssnapExtract.givenThrombolysis = "No"	
					if(reasons.noThrombolysisNotAvailable){
						ssnapExtract.noThrombolysisReason="Thrombolysis not available at hospital at all"
					}else if (reasons.noThrombolysisOutsideHours){
						ssnapExtract.noThrombolysisReason="Outside thrombolysis service hours"
					}else{
						ssnapExtract.noThrombolysisReason="Unable to scan quickly enough"
					}
						
				}else {
					ssnapExtract.givenThrombolysis = "No but"
				}
				
				
					
				reasons.each{
					//println "$it"	
				}
				
				//println "*** ${reasons.noThrombolysisOutsideHours}"
				
				
				
			}
		}else{
			ssnapExtract.addError(
				new MissingItem("Thrombolysis"
					, "Whether the patient received thrombolysis is missing"));
		}
		
		
		/*String givenThrombolysis
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
		Integer nihssAt24*/
		
		
		
	}
	
	
	
	
	private processCareSummaryData(SsnapExtract ssnapExtract, CareActivity careActivity, Boolean _72hr_) {

		if ( !_72hr_) { 
			if(careActivity?.clinicalSummary?.urinaryTractInfection){
				
			}else{
				ssnapExtract.addError(
						new MissingItem("Clinical summary"
						, "Did the patient have a urinary tract infection in the first 7 days is missing"));
			}
			if(careActivity?.clinicalSummary?.worstLevelOfConsciousness){
			
			}else{
				ssnapExtract.addError(
						new MissingItem("Clinical summary"
						, "The worst level of consciousness in the first 7 days is missing"));
			}
			if(careActivity?.clinicalSummary?.newPneumonia){
			
			}else{
				ssnapExtract.addError(
						new MissingItem("Clinical summary"
						, "Did the patient develop pneumonia in the first 7 days is missing"));
			}
		}

		if ( _72hr_) {
		if(careActivity?.clinicalSummary?.palliativeCare != null){
			if(careActivity?.clinicalSummary?.palliativeCare.equals('yes')){
				if(careActivity?.clinicalSummary?.palliativeCareDate){
				}else{
					ssnapExtract.addError(
						new MissingItem("Clinical summary"
						, "The date the patient was started on palliative care is missing"));
				}
				if(careActivity?.clinicalSummary?.endOfLifePathway){
				}else{
					ssnapExtract.addError(
						new MissingItem("Clinical summary"
						, "If the patient was on the end of life pathway is missing"));
				}
			
			}
		} else {
			ssnapExtract.addError(
				new MissingItem("Clinical summary"
					, "Was the patient on palliative care is missing: "));
		}
		}
		
		if ( !_72hr_) {
		
		if (careActivity.postDischargeCare?.dischargedTo!="died") { 
		if ( ! careActivity?.clinicalSummary?.palliativeCare.equals('yes') ) {
			if(careActivity?.postDischargeCare?.palliativeCare) { 
				if(careActivity?.postDischargeCare?.palliativeCare.equals('yes')) {
					if(careActivity?.postDischargeCare?.palliativeCareDate){
					}else{
						ssnapExtract.addError(
							new MissingItem("Discharge : On discharge"
							, "The date the patient was started on palliative care is missing"));
					}
					if(careActivity?.postDischargeCare?.endOfLifePathway){
					} else {
						ssnapExtract.addError(
							new MissingItem("Discharge : On discharge"
							, "If the patient was on the end of life pathway is missing"));
					}
				}		
			} else {
					ssnapExtract.addError(
						new MissingItem("Discharge : On discharge"
							, "Palliative care decision by discharge is missing"));
			}
		}
		}
		}
	}
	
	
	
	
	
		
	private processDischargeData(SsnapExtract ssnapExtract, CareActivity careActivity) {
		processPreDischargeData(ssnapExtract, careActivity)
		processDischargeAssessmentData(ssnapExtract, careActivity)
		processPostDischargeData(ssnapExtract, careActivity)
	}

	private processPostDischargeData(SsnapExtract ssnapExtract, CareActivity careActivity) {
		if(! careActivity.postDischargeCare?.ssnapParticipationConsent && isComprehensive()) {
			ssnapExtract.addError(
				new MissingItem("Discharge : On discharge"
				, "Patient consent details missing"));
		}

		
		if(! careActivity.medicalHistory?.strokeUnitDeath && ! careActivity.medicalHistory?.didNotStayInStrokeWard){
			if( careActivity.medicalHistory?.strokeWardDischargeTime == null ) {
				ssnapExtract.addError(
					new MissingItem("Discharge : On discharge"
					, "Stroke Ward discharge time is missing"));
			}
			if( careActivity.medicalHistory?.strokeWardDischargeDate == null ) {
				ssnapExtract.addError(
					new MissingItem("Discharge : On discharge"
					, "Stroke Ward discharge date is missing"));
			}
		}
		if(careActivity.postDischargeCare?.dischargedTo!="died" ){
			if( careActivity.medicalHistory?.hospitalDischargeTime == null ) {
				ssnapExtract.addError(
					new MissingItem("Discharge : On discharge"
					, "Hospital discharge time is missing"));
			}
			if( careActivity.medicalHistory?.hospitalDischargeDate == null ) {
				ssnapExtract.addError(
					new MissingItem("Discharge : On discharge"
					, "Hospital discharge date is missing"));
			}
		}
		
		if (careActivity.postDischargeCare?.dischargedTo=="died") {
			return
		}
		
		if(careActivity.postDischargeCare?.dischargedTo){
			ssnapExtract.patientDischargedTo = careActivity.postDischargeCare?.dischargedTo
			if(careActivity.postDischargeCare?.dischargedTo=="intermediateCare"){
				if(careActivity.postDischargeCare?.strokeSpecialist != null){
					ssnapExtract.dischargeStrokeSpecialist = careActivity.postDischargeCare?.strokeSpecialist
				}
				if(careActivity.postDischargeCare?.newCareTeam == null){
					ssnapExtract.addError(
							new MissingItem("Discharge : On discharge"
							, "You must state community team code if transferred to an ESD/Community team"));
				}
			}
			else if(careActivity.postDischargeCare?.dischargedTo=="otherHospital"){
				if(careActivity.postDischargeCare?.newCareTeam == null){
					ssnapExtract.addError(
							new MissingItem("Discharge : On discharge"
							, "You must state team code if transferred to an inpatient care team"));
				}
			}
			/*else if(careActivity.postDischargeCare?.dischargedTo=="somewhere"){
				if(careActivity.postDischargeCare?.newCareTeam == null){
					ssnapExtract.addError(
							new MissingItem("Discharge : On discharge"
							, "You must state hospital code if discharged somewhere else"));
				}
			}*/
			else if(careActivity.postDischargeCare?.dischargedTo=="home"){
				if(careActivity.postDischargeCare?.alonePostDischargeUnknown == Boolean.TRUE || careActivity.postDischargeCare?.alonePostDischarge != null){
				}
				else{
					ssnapExtract.addError(
							new MissingItem("Discharge : On discharge"
							, "You must state if the patient is living alone after discharge"));
				}
			}
			else if(careActivity.postDischargeCare?.dischargedTo=="residentialCareHome"
					|| careActivity.postDischargeCare?.dischargedTo=="nursingCareHome"){
				if(careActivity.postDischargeCare?.patientPreviouslyResident != null){
					ssnapExtract.dischargePreviouslyResident = careActivity.postDischargeCare?.patientPreviouslyResident
					if(careActivity.postDischargeCare?.patientPreviouslyResident == Boolean.FALSE){
						if(careActivity.postDischargeCare?.temporaryOrPermanent){
							ssnapExtract.dischargeTemporaryOrPerm = careActivity.postDischargeCare?.temporaryOrPermanent
						}else{
							ssnapExtract.addError(
									new MissingItem("Discharge : On discharge"
									, "You must state if the new arrangement is temporary or permanent"));
						}
					}
				}else{
					ssnapExtract.addError(
							new MissingItem("Discharge : On discharge"
							, "You must state if the patient was previously a resident"));
				}
				
			}
		}else{
			ssnapExtract.addError(
					new MissingItem("Discharge : On discharge"
					, "You must state where the patient was discharged to"));
		}
	}

	private processDischargeAssessmentData(SsnapExtract ssnapExtract, CareActivity careActivity) {
		if (careActivity.postDischargeCare?.dischargedTo=="died") {
			return
		}
		if((!careActivity?.therapyManagement?.assessmentManagement?.dischargeModifiedRankinNotKnown)
			&& !(careActivity?.therapyManagement?.assessmentManagement?.findModifiedRankinByStage("Discharge"))){
			ssnapExtract.addError(
				new MissingItem("Discharge : Discharge assessments"
				, "You must state the Modified Rankin Score at discharge"));
			
		
		}
		if (careActivity.postDischargeCare?.dischargedTo=="otherHospital") {
			return
		}
			
		if(!careActivity.inAfOnDischarge){
			ssnapExtract.addError(
				new MissingItem("Discharge : Discharge assessments"
				, "You must state if there is documented evidence that the patient is in atrial fibrilation on discharge"));
		}else if(careActivity.inAfOnDischarge == "Yes"){
			if(!careActivity.onAnticoagulantAtDischarge){
				ssnapExtract.addError(
					new MissingItem("Discharge : Discharge assessments"
					, "If patient in AF are they taking anticoagulation on discharge or discharged with a plan to start anticoagulation within the next month (not antiplatelet agent)"));
			}
		}
		
		
		if(isComprehensive()){
			if (careActivity.postDischargeCare?.dischargedTo!="otherHospital" && careActivity.postDischargeCare?.dischargedTo!="died" && careActivity.postDischargeCare?.dischargedTo!="intermediateCare") {
				if((!careActivity?.therapyManagement?.assessmentManagement?.dischargeBarthelNotKnown)
					&& !(careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Discharge"))){
					ssnapExtract.addError(
						new MissingItem("Discharge : Discharge assessments"
						, "You must state the Barthel assessment at discharge"));
				}
			}
		}
			

		
	}
	
	private processPreDischargeData(SsnapExtract ssnapExtract, CareActivity careActivity) {
		
		if (careActivity.postDischargeCare?.dischargedTo=="otherHospital" || careActivity.postDischargeCare?.dischargedTo=="died") {
			return
		}
		
		if(careActivity.postDischargeCare?.dischargedTo!="intermediateCare"){
			if(!careActivity.fitForDischargeDate && careActivity.fitForDischargeDateUnknown != Boolean.TRUE) {
				ssnapExtract.addError(
					new MissingItem("Discharge : Pre discharge"
					, "Patient considered by multidisciplinary team to no longer require inpatient rehabilitation is missing"));
			}
		}
		
		if(careActivity.postDischargeCare?.documentedEvidence){
			
		}else{
			ssnapExtract.addError(
					new MissingItem("Discharge : Pre discharge"
					, "Documented evidence of joint care for post discharge management is missing"));
		}
		if(careActivity.postDischargeCare?.documentationPostDischarge != null){
			
		}else{
			ssnapExtract.addError(
					new MissingItem("Discharge : Pre discharge"
					, "Documentation of a named person of the patient post discharge is missing"));
		}
		
		if(careActivity?.postDischargeCare?.hasSupport("Social Services")){
			
			if(careActivity.postDischargeCare?.numberOfSocialServiceVisits){
				
			}
			else if (careActivity.postDischargeCare?.numberOfSocialServiceVisitsUnknown){
				ssnapExtract.postDischargeVisitUnknown = Boolean.TRUE
			}else{
				ssnapExtract.addError(
					new MissingItem("Discharge : Pre discharge"
								, "How many social service visits provided each week is missing"));
			}
		}
		
		
		def supportTypeKnown = Boolean.FALSE
		if(careActivity?.postDischargeCare?.hasSupport("Social Services")){
			ssnapExtract.hasSocialServices = Boolean.TRUE
			supportTypeKnown = Boolean.TRUE
		}
		if(careActivity?.postDischargeCare?.hasSupport("Informal Carers")){
			supportTypeKnown = Boolean.TRUE
			ssnapExtract.hasInformalCarers = Boolean.TRUE
		}
//		if(careActivity?.postDischargeCare?.hasSupport("Palliative Care")){
//			supportTypeKnown = Boolean.TRUE
//			ssnapExtract.hasPalliativeCare = Boolean.TRUE
//		}
		if(careActivity?.postDischargeCare?.hasSupport("Social Services Unavailable")
			|| careActivity?.postDischargeCare?.hasSupport("Patient Refused")){
			supportTypeKnown = Boolean.TRUE
		}
		
		
		
		def esdStatusKnown = Boolean.FALSE;
		if(careActivity?.postDischargeCare?.hasSupport("Stroke/neurology specific ESD")){
			ssnapExtract.hasStrokeSpecificESD = Boolean.TRUE
			esdStatusKnown =  Boolean.TRUE
		}
		if(careActivity?.postDischargeCare?.hasSupport("Non specialist ESD")){
			ssnapExtract.hasNonSpecificESD = Boolean.TRUE
			esdStatusKnown =  Boolean.TRUE
		}
		if(careActivity?.postDischargeCare?.hasSupport("No ESD")){
			esdStatusKnown =  Boolean.TRUE
		}
		
		def rehabStatusKnown = Boolean.FALSE
		if(careActivity?.postDischargeCare?.hasSupport("Stroke/neurology specific community rehabilitation team")){
			ssnapExtract.hasStrokeSpecificCommunityRehabilitation = Boolean.TRUE
			rehabStatusKnown = Boolean.TRUE
		}
		if(careActivity?.postDischargeCare?.hasSupport("Non specialist community rehabilitation team")){
			ssnapExtract.hasNonSpecificCommunityRehabilitation = Boolean.TRUE
			rehabStatusKnown = Boolean.TRUE
		}
		if(careActivity?.postDischargeCare?.hasSupport("No rehabilitation")){
			rehabStatusKnown = Boolean.TRUE
		}		
		
		
		if(careActivity?.postDischargeCare?.supportOnDischargeNeeded == "Yes"){

			if ( supportTypeKnown == Boolean.FALSE ) {
				ssnapExtract.addError(
					new MissingItem("Discharge : Pre discharge"
					, "You must state what support was received"));
			}
		}else {
			if(careActivity?.postDischargeCare?.supportOnDischargeNeeded == null){
				ssnapExtract.addError(
					new MissingItem("Discharge : Pre discharge"
					, "Support required post discharge details are missing"));
				
			}
		}
		
		if ( esdStatusKnown == Boolean.FALSE ) {
			ssnapExtract.addError(
				new MissingItem("Discharge : Pre discharge"
				, "Planned ESD discharge details are missing"));
		}
		if ( rehabStatusKnown == Boolean.FALSE ) {
			ssnapExtract.addError(
				new MissingItem("Discharge : Pre discharge"
				, "Planned rehabilitation discharge details are missing"));
		}
		
//		if(careActivity.postDischargeCare?.dischargedTo){
//			if(careActivity.postDischargeCare?.dischargedTo=="home" 
//					|| careActivity.postDischargeCare?.dischargedTo=="residentialCareHome" 
//					  || careActivity.postDischargeCare?.dischargedTo=="nursingCareHome" ){
//				if(careActivity?.postDischargeCare?.hasSupport("Social Services Unavailable") 
//					|| careActivity?.postDischargeCare?.hasSupport("Patient Refused")){
//					
//				}else{
//					if(!ssnapExtract.hasPlannedSupport()){
//						ssnapExtract.addError(
//								new MissingItem("Discharge : Pre discharge"
//								, "Planned support from discharge details are missing"));
//					}
//					if(!ssnapExtract.hasPlannedTherapy()){
//						def msg =
//							new MissingItem("Discharge : Pre discharge", "Planned therapy from discharge details are missing.")
//						ssnapExtract.addError(msg);						
//					}
//				}	
//			}
//			
//			
//		}
//		
//		
//		if(careActivity?.postDischargeCare?.hasSupport("Stroke/neurology specific ESD")){
//			//cimssExtract.esdReferral = Boolean.TRUE
//			if(careActivity.postDischargeCare.esdReferralDate){
//				//cimssExtract.dateReferredToEsd = careActivity.postDischargeCare.esdReferralDate
//			}else{
//				if(careActivity.postDischargeCare.esdReferralDateUnknown == Boolean.TRUE){
//					//cimssExtract.dateReferredToEsdUnknown = Boolean.TRUE
//				}else{
//					/*ssnapExtract.addError(
//							new MissingItem("Discharge : Pre discharge"
//							, "The date referred to ESD is missing"));*/
//				}
//			}
//		}else{
//			//cimssExtract.esdReferral = Boolean.FALSE
//		}
		
		
		
	}

	

	private def checkTherapyItem = { ssnapExtract, group, type ,therapyDetail, includeTime, _72hr_  -> 
		if ( _72hr_ ) {
			checkTherapyItemFor72HrReport(ssnapExtract, group, type ,therapyDetail, includeTime)
		} else {
			checkTherapyItemByDischarge(ssnapExtract, group, type ,therapyDetail, includeTime)
		}
	}
	
	private def checkTherapyItemByDischarge = { ssnapExtract, group, type ,therapyDetail, includeTime ->
		if(therapyDetail){
		
			def fieldStem = therapyFieldStems."${therapyDetail.type}"
			def errorType = therapyErrorTypes."${therapyDetail.type}"
				
			if(therapyDetail.wasPerformedIn72Hrs) {
			
				if(therapyDetail.performedDate == null){
					ssnapExtract.addError(
						new MissingItem(group
						, "The date the first ${errorType} assessment was performed in 72hours is missing"));
				}
				
				if(includeTime && therapyDetail.performedTime == null){
					ssnapExtract.addError(
						new MissingItem(group
						, "The time the first ${errorType} assessment was performed in 72 hours is missing"));
				}
				
			} else if(therapyDetail.wasPerformed == null){
				if(therapyDetail.reasonNotPerformedIn72Hrs != "noproblem"){
					ssnapExtract.addError(
						new MissingItem(group
							, therapyMissingMessage."${therapyDetail.type}"));
				}
			} else if (!therapyDetail.wasPerformed && therapyDetail.reasonNotPerformed == null && therapyDetail.reasonNotPerformedIn72Hrs != "passedswallowscreen") {
			
				ssnapExtract.addError(
					new MissingItem(group
					, "The reason the first ${errorType} assessment was not performed is missing"));
				
			} else if(therapyDetail.wasPerformed) {
			
				if(therapyDetail.performedDate != null){
					ssnapExtract."${fieldStem}AssessmentDate" = therapyDetail.performedDate
				} else{
					ssnapExtract.addError(
						new MissingItem(group
						, "The date the first ${errorType} assessment was performed is missing"));
				}
				
				if(includeTime && therapyDetail.performedTime == null){								
					ssnapExtract.addError(
						new MissingItem(group
						, "The time the first ${errorType} assessment was performed is missing"));
				}
			}

		}
	}
	
	private def checkTherapyItemFor72HrReport = { ssnapExtract, group, type ,therapyDetail,  includeTime ->
		if(therapyDetail){
		
			def fieldStem = therapyFieldStems."${therapyDetail.type}"
			def errorType = therapyErrorTypes."${therapyDetail.type}"
			
	
						
				if(therapyDetail.wasPerformedIn72Hrs !=null){
						if(therapyDetail.wasPerformedIn72Hrs  == Boolean.TRUE){
							if(therapyDetail.performedDate){
								ssnapExtract."${fieldStem}AssessmentDate" = therapyDetail.performedDate
							}else{
									ssnapExtract.addError(
										new MissingItem(group
										, "The date (within 72 hours) the first ${errorType} assessment was performed is missing"));
							}
							if(includeTime){
								if(therapyDetail.performedTime != null){
									ssnapExtract."${fieldStem}AssessmentTime" = therapyDetail.performedTime
								}else{
									ssnapExtract.addError(
										new MissingItem(group
										, "The time (within 72 hours) the first ${errorType} assessment was performed is missing"));
								}
							}
							
						}else{
							if(therapyDetail.reasonNotPerformedIn72Hrs){
								ssnapExtract."${fieldStem}NotPerfomedReason"  = therapyDetail.reasonNotPerformedIn72Hrs
							}else{
								ssnapExtract.addError(
									new MissingItem(group
									, "The reason the first ${errorType} assessment was not performed in 72 hours is missing"));
							}
						}
					}else{
						ssnapExtract.addError(
							new MissingItem(group
							, therapyMissingMessage."${therapyDetail.type}"));
					}
					
			}else{
						ssnapExtract.addError(
							new MissingItem(group
							, therapyMissingMessage."${type}"));
			}
		}

	
	
	
	
	
}
