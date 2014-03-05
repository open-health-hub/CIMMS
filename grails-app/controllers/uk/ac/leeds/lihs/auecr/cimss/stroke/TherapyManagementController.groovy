package uk.ac.leeds.lihs.auecr.cimss.stroke

import java.util.Date;

import org.codehaus.groovy.grails.web.json.JSONObject;

import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.CognitiveStatusNoAssessmentType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.CommunicationNoAssessmentReasonType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.OccupationalTherapyNoAssessmentReasonType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.PhysiotherapyNoAssessmentReasonType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.RehabGoalsNotSetReasonType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.SwallowingNoAssessmentReasonType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.therapy.AssessmentManagement;
import uk.ac.leeds.lihs.auecr.cimss.stroke.therapy.OccupationalTherapyManagement;
import uk.ac.leeds.lihs.auecr.cimss.stroke.therapy.PhysiotherapyManagement;
import uk.ac.leeds.lihs.auecr.cimss.stroke.therapy.SpeechAndLanguageTherapyManagement;
import uk.ac.leeds.lihs.auecr.cimss.stroke.therapy.assessment.Barthel;
import uk.ac.leeds.lihs.auecr.cimss.stroke.therapy.assessment.ModifiedRankin;
import grails.converters.JSON;

class TherapyManagementController extends StrokeBaseController{
	
	
	def getTherapyManagementPage = {
		log.debug "in getTherapyManagementPage"
		def careActivity = CareActivity.get(params.id)
		render(template:'/assessment/therapy',model:['careActivityInstance':careActivity])
	}

	def getTherapyManagement = {
		log.debug "in getTherapyManagement"
		def careActivity = CareActivity.get(params.id);
		renderTherapyManagement(careActivity);
	}
		
	def updateTherapyManagement = {
		log.info "in updateTherapyManagement -> ${request.JSON.TherapyManagement}"
		def careActivity = CareActivity.get(params.id);
		def errors=[:]
		
		def data = request.JSON.TherapyManagement
		
		
		if(changedSinceRetrieval(careActivity, data)){
			data.versions = getVersions(careActivity)
			careActivity.errors.rejectValue('version', 'version.changed.therapy')
			careActivity.discard()
			renderTherapyManagementWithErrors(data, careActivity, errors);
		}else{
			updateData(careActivity, data, errors)
			if(!errors & careActivity.validate()){
				careActivity.save(flush:true)
				renderTherapyManagement(careActivity);
			}else{
				careActivity.discard()
				renderTherapyManagementWithErrors(data, careActivity, errors);
			}
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
		
	private def changedSinceRetrieval = { careActivity, data ->
		
		
		if(careActivity?.therapyManagement?.version!=null && data.versions.therapyManagement == JSONObject.NULL){
			return true
		}
		
		if(careActivity?.therapyManagement?.version && careActivity?.therapyManagement?.version >(long)data.versions.therapyManagement){
			return true;
		}
		
		
		if(careActivity?.therapyManagement?.occupationalTherapyManagement?.version!=null 
			&& data.versions.occupationalTherapyManagement == JSONObject.NULL){
			return true
		}
		
		if(careActivity?.therapyManagement?.occupationalTherapyManagement?.version 
				&& careActivity?.therapyManagement?.occupationalTherapyManagement?.version >(long)data.versions.occupationalTherapyManagement){
			 return true	
		}
		
		if(careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.version!=null
			&& data.versions.speechAndLanguageTherapyManagement == JSONObject.NULL){
			return true
		}
			
				
				
		if(careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.version
				&& careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.version >(long)data.versions.speechAndLanguageTherapyManagement){
			return true;
		}
				
				
		if(careActivity?.therapyManagement?.physiotherapyManagement?.version!=null
			&& data.versions.physiotherapyManagement == JSONObject.NULL){
			return true
		}
				
		if(careActivity?.therapyManagement?.physiotherapyManagement?.version
				&& careActivity?.therapyManagement?.physiotherapyManagement?.version >(long)data.versions.physiotherapyManagement){
			return true;
		}
			

	    		
								
		if(careActivity?.therapyManagement?.assessmentManagement?.version!=null
			&& data.versions.assessmentManagement == JSONObject.NULL){
				return true
		}
			
			
					
		if(careActivity?.therapyManagement?.assessmentManagement?.version
				&& careActivity?.therapyManagement?.assessmentManagement?.version >(long)data.versions.assessmentManagement){
				return true;
		}
				
		
				
				
				
		if(careActivity?.therapyManagement?.assessmentManagement?.findModifiedRankinByStage("Baseline")?.version!=null
			&& data.versions.modifiedRankin == JSONObject.NULL){
			return true
		}
						
		if(careActivity?.therapyManagement?.assessmentManagement?.findModifiedRankinByStage("Baseline")?.version
			&& careActivity?.therapyManagement?.assessmentManagement?.findModifiedRankinByStage("Baseline")?.version >(long)data.versions.modifiedRankin){

			return true;
		}
				
		
							
		if(careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Baseline")?.version!=null
			&& data.versions.barthel == JSONObject.NULL){
		
			return true
		}
		if(careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Baseline")?.version
			&& careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Baseline")?.version >(long)data.versions.barthel){
			return true;
		}
		
		return false
	}
		
	private def updateData = {careActivity, data, errors ->
		if(!careActivity.therapyManagement){
			careActivity.therapyManagement = new TherapyManagement()
			careActivity.therapyManagement.careActivity = careActivity
		}
		updateBaseData(careActivity, data, errors)
		updateOccupationalTherapy(careActivity.therapyManagement, data.occupationalTherapyManagement, errors)
		updateSpeechAndLanguageTherapy(careActivity.therapyManagement, data.speechAndLanguageTherapyManagement, errors)
		updatePhysiotherapy(careActivity.therapyManagement, data.physiotherapyManagement, errors)
		updateAssessmentManagement(careActivity.therapyManagement, data.assessments, errors)
		
		
		
		
	}	
				
	private def updateBaseData = { careActivity, data, errors ->
		careActivity.therapyManagement.cognitiveStatusAssessed = getBooleanFromString(data.cognitiveStatusAssessed)
		if(careActivity.therapyManagement.cognitiveStatusAssessed == Boolean.TRUE){
			careActivity.therapyManagement.cognitiveStatusAssessmentDate = DisplayUtils.getDate(data.cognitiveStatusAssessmentDate, errors, "therapyManagement.cognitiveStatusAssessmentDate", "cognitive.assessment.date.invalid.format")
			careActivity.therapyManagement.cognitiveStatusAssessmentTime = DisplayUtils.getTime(data.cognitiveStatusAssessmentTime, errors, "therapyManagement.cognitiveStatusAssessmentTime", "cognitive.assessment.time.invalid.format")
			careActivity.therapyManagement.cognitiveStatusNoAssessmentType = null;
		}else if (careActivity.therapyManagement.cognitiveStatusAssessed == Boolean.FALSE){
			if(data.cognitiveStatusNoAssessmentReason !="null" & data.cognitiveStatusNoAssessmentReason!= JSONObject.NULL){
				careActivity.therapyManagement.cognitiveStatusNoAssessmentType = CognitiveStatusNoAssessmentType.findByDescription(data.cognitiveStatusNoAssessmentReason)
			}else{
				careActivity.therapyManagement.cognitiveStatusNoAssessmentType = null;
			}
			careActivity.therapyManagement.cognitiveStatusAssessmentDate = null
			careActivity.therapyManagement.cognitiveStatusAssessmentTime = null
		}else{
			careActivity.therapyManagement.cognitiveStatusNoAssessmentType = null;
			careActivity.therapyManagement.cognitiveStatusAssessmentDate = null
			careActivity.therapyManagement.cognitiveStatusAssessmentTime = null
			
		}
		careActivity.therapyManagement.rehabGoalsSet = getBooleanFromString(data.rehabGoalsSet)
		if(careActivity.therapyManagement.rehabGoalsSet == Boolean.TRUE){
			careActivity.therapyManagement.rehabGoalsSetDate = DisplayUtils.getDate(data.rehabGoalsSetDate , errors, "therapyManagement.rehabGoalsSetDate", "rehab.goals.date.invalid.format")
			careActivity.therapyManagement.rehabGoalsSetTime = DisplayUtils.getTime(data.rehabGoalsSetTime , errors, "therapyManagement.rehabGoalsSetTime", "rehab.goals.time.invalid.format")
			careActivity.therapyManagement.rehabGoalsNotSetReasonType = null;
		}else if (careActivity.therapyManagement.rehabGoalsSet == Boolean.FALSE){
			if(data.rehabGoalsNotSetReason !="null"  && data.rehabGoalsNotSetReason!= JSONObject.NULL){
				careActivity.therapyManagement.rehabGoalsNotSetReasonType = RehabGoalsNotSetReasonType.findByDescription(data.rehabGoalsNotSetReason)
			}else{
				careActivity.therapyManagement.rehabGoalsNotSetReasonType = null;
			}
			careActivity.therapyManagement.rehabGoalsSetDate = null
			careActivity.therapyManagement.rehabGoalsSetTime = null
		}else{
			careActivity.therapyManagement.rehabGoalsNotSetReasonType = null;
			careActivity.therapyManagement.rehabGoalsSetDate = null
			careActivity.therapyManagement.rehabGoalsSetTime = null
		}
		
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
	
	
	private def updateOccupationalTherapy = { therapyManagement, data ,errors ->
		if(!therapyManagement.occupationalTherapyManagement){
			therapyManagement.occupationalTherapyManagement = new OccupationalTherapyManagement()
			therapyManagement.occupationalTherapyManagement.therapyManagement = therapyManagement
		}
		therapyManagement.occupationalTherapyManagement.assessmentPerformed = getBooleanFromString(data.assessmentPerformed)
		if(therapyManagement.occupationalTherapyManagement.assessmentPerformed == Boolean.TRUE){
			therapyManagement.occupationalTherapyManagement.assessmentDate = DisplayUtils.getDate(data.assessmentDate, errors, "occupationalTherapyManagement.assessmentDate", "assessment.date.invalid.format")
			therapyManagement.occupationalTherapyManagement.assessmentTime = DisplayUtils.getTime(data.assessmentTime, errors, "occupationalTherapyManagement.assessmentTime", "assessment.time.invalid.format")
			therapyManagement.occupationalTherapyManagement.noAssessmentReasonType = null
		}else if(therapyManagement.occupationalTherapyManagement.assessmentPerformed == Boolean.FALSE){
			if(data.otTherapyNoAssessmentReason !="null"  && data.otTherapyNoAssessmentReason!= JSONObject.NULL){
				therapyManagement.occupationalTherapyManagement.noAssessmentReasonType  = OccupationalTherapyNoAssessmentReasonType.findByDescription(data.otTherapyNoAssessmentReason)
			}else{
				therapyManagement.occupationalTherapyManagement.noAssessmentReasonType  = null;
			}
			therapyManagement.occupationalTherapyManagement.assessmentDate = null
			therapyManagement.occupationalTherapyManagement.assessmentTime = null
		}else{
			therapyManagement.occupationalTherapyManagement.noAssessmentReasonType  = null;
			therapyManagement.occupationalTherapyManagement.assessmentDate = null
			therapyManagement.occupationalTherapyManagement.assessmentTime = null
		}
		
		therapyManagement.occupationalTherapyManagement.moodAssessmentPerformed  = getBooleanFromString(data.moodAssessmentPerformed)
		if(therapyManagement.occupationalTherapyManagement.moodAssessmentPerformed == Boolean.TRUE){
			therapyManagement.occupationalTherapyManagement.moodAssessmentDate = DisplayUtils.getDate(data.moodAssessmentDate, errors, "occupationalTherapyManagement.moodAssessmentDate", "mood.assessment.date.invalid.format")
			therapyManagement.occupationalTherapyManagement.moodAssessmentTime = DisplayUtils.getTime(data.moodAssessmentTime, errors, "occupationalTherapyManagement.moodAssessmentTime", "mood.assessment.time.invalid.format")
		}else{
			therapyManagement.occupationalTherapyManagement.moodAssessmentDate = null
			therapyManagement.occupationalTherapyManagement.moodAssessmentTime = null
		}
	}
	
	private def updatePhysiotherapy = { therapyManagement, data, errors ->
		if(!therapyManagement.physiotherapyManagement){
			therapyManagement.physiotherapyManagement = new PhysiotherapyManagement()
			therapyManagement.physiotherapyManagement.therapyManagement = therapyManagement
		}
		therapyManagement.physiotherapyManagement.assessmentPerformed = getBooleanFromString(data.physioAssessmentPerformed)
		if(therapyManagement.physiotherapyManagement.assessmentPerformed == Boolean.TRUE){
			therapyManagement.physiotherapyManagement.assessmentDate = DisplayUtils.getDate(data.physioAssessmentDate, errors, "physiotherapyManagement.assessmentDate", "physiotherapy.assessment.date.invalid.format")
			therapyManagement.physiotherapyManagement.assessmentTime = DisplayUtils.getTime(data.physioAssessmentTime, errors, "physiotherapyManagement.assessmentTime", "physiotherapy.assessment.time.invalid.format")
			therapyManagement.physiotherapyManagement.noAssessmentReasonType = null
		}else if (therapyManagement.physiotherapyManagement.assessmentPerformed == Boolean.FALSE){
			if(data.physioNoAssessmentReason !="null"  && data.physioNoAssessmentReason!= JSONObject.NULL){
				therapyManagement.physiotherapyManagement.noAssessmentReasonType  = PhysiotherapyNoAssessmentReasonType.findByDescription(data.physioNoAssessmentReason)
			}else{
				therapyManagement.physiotherapyManagement.noAssessmentReasonType  = null;
			}
			therapyManagement.physiotherapyManagement.assessmentDate = null
			therapyManagement.physiotherapyManagement.assessmentTime = null
		}else{
			therapyManagement.physiotherapyManagement.assessmentDate = null
			therapyManagement.physiotherapyManagement.assessmentTime = null
			therapyManagement.physiotherapyManagement.noAssessmentReasonType  = null;
		}
	}
		
	private def getSpeechAndLanguageTherapyManagement = {therapyManagement ->
		if(!therapyManagement.speechAndLanguageTherapyManagement){
			therapyManagement.speechAndLanguageTherapyManagement = new SpeechAndLanguageTherapyManagement()
			therapyManagement.speechAndLanguageTherapyManagement.therapyManagement = therapyManagement
		}
		return therapyManagement.speechAndLanguageTherapyManagement
	}
		
	private def updateSpeechAndLanguageTherapy = { therapyManagement, data, errors ->
		def speechAndLanguageTherapyManagement = getSpeechAndLanguageTherapyManagement(therapyManagement);
		speechAndLanguageTherapyManagement.communicationAssessmentPerformed = getBooleanFromString(data.communicationAssessmentPerformed)
		if(speechAndLanguageTherapyManagement.communicationAssessmentPerformed == Boolean.TRUE){
			speechAndLanguageTherapyManagement.communicationAssessmentDate = DisplayUtils.getDate(data.communicationAssessmentDate, errors, "speechAndLanguageTherapyManagement.communicationAssessmentDate", "speech.and.language.communication.assessment.date.invalid.format")
			speechAndLanguageTherapyManagement.communicationAssessmentTime = DisplayUtils.getTime(data.communicationAssessmentTime , errors, "speechAndLanguageTherapyManagement.communicationAssessmentTime", "speech.and.language.communication.assessment.time.invalid.format")
			speechAndLanguageTherapyManagement.noCommunicationAssessmentReasonType = null
		}else if (speechAndLanguageTherapyManagement.communicationAssessmentPerformed == Boolean.FALSE){
			if(data.communicationNoAssessmentReason && data.communicationNoAssessmentReason !="null"  && data.communicationNoAssessmentReason!= JSONObject.NULL){
				speechAndLanguageTherapyManagement.noCommunicationAssessmentReasonType  = CommunicationNoAssessmentReasonType.findByDescription(data.communicationNoAssessmentReason)
			}else{
				speechAndLanguageTherapyManagement.noCommunicationAssessmentReasonType  = null;
			}
			speechAndLanguageTherapyManagement.communicationAssessmentDate = null
			speechAndLanguageTherapyManagement.communicationAssessmentTime = null
		}else{
			speechAndLanguageTherapyManagement.noCommunicationAssessmentReasonType  = null;
			speechAndLanguageTherapyManagement.communicationAssessmentDate = null
			speechAndLanguageTherapyManagement.communicationAssessmentTime = null
		}
		speechAndLanguageTherapyManagement.swallowingAssessmentPerformed = getBooleanFromString(data.swallowingAssessmentPerformed)
		if(speechAndLanguageTherapyManagement.swallowingAssessmentPerformed == Boolean.TRUE){
			speechAndLanguageTherapyManagement.swallowingAssessmentDate = DisplayUtils.getDate(data.swallowingAssessmentDate, errors, "speechAndLanguageTherapyManagement.swallowingAssessmentDate", "speech.and.language.swallowing.assessment.date.invalid.format")
			speechAndLanguageTherapyManagement.swallowingAssessmentTime = DisplayUtils.getTime(data.swallowingAssessmentTime, errors, "speechAndLanguageTherapyManagement.swallowingAssessmentTime", "speech.and.language.swallowing.assessment.time.invalid.format")
			speechAndLanguageTherapyManagement.noSwallowingAssessmentReasonType = null
		}else if (speechAndLanguageTherapyManagement.swallowingAssessmentPerformed == Boolean.FALSE){
			if(data.swallowingNoAssessmentReason  && data.swallowingNoAssessmentReason !="null"  && data.swallowingNoAssessmentReason!= JSONObject.NULL){
				speechAndLanguageTherapyManagement.noSwallowingAssessmentReasonType  = SwallowingNoAssessmentReasonType.findByDescription(data.swallowingNoAssessmentReason)
			}else{
				speechAndLanguageTherapyManagement.noSwallowingAssessmentReasonType  = null;
			}
			speechAndLanguageTherapyManagement.swallowingAssessmentDate = null
			speechAndLanguageTherapyManagement.swallowingAssessmentTime = null
		}else{
			speechAndLanguageTherapyManagement.noSwallowingAssessmentReasonType  = null;
			speechAndLanguageTherapyManagement.swallowingAssessmentDate = null
			speechAndLanguageTherapyManagement.swallowingAssessmentTime = null
		}	
			
	}

	private	def renderTherapyManagement = {careActivity ->
		
		def occupationalTherapyManagement = [id:careActivity?.therapyManagement?.occupationalTherapyManagement?.id
			   						, assessmentPerformed:careActivity?.therapyManagement?.occupationalTherapyManagement?.assessmentPerformed.toString()    
									, assessmentDate:DisplayUtils.displayDate(careActivity?.therapyManagement?.occupationalTherapyManagement?.assessmentDate)
									, assessmentTime:DisplayUtils.displayTime(careActivity?.therapyManagement?.occupationalTherapyManagement?.assessmentTime)
									, otTherapyNoAssessmentReason:careActivity?.therapyManagement?.occupationalTherapyManagement?.noAssessmentReasonType?.description
									, moodAssessmentPerformed:careActivity?.therapyManagement?.occupationalTherapyManagement?.moodAssessmentPerformed?.toString()
									, moodAssessmentDate:DisplayUtils.displayDate(careActivity?.therapyManagement?.occupationalTherapyManagement?.moodAssessmentDate)
									, moodAssessmentTime:DisplayUtils.displayTime(careActivity?.therapyManagement?.occupationalTherapyManagement?.moodAssessmentTime)]
		   
		   

		   
		   def speechAndLanguageTherapyManagement = [id:careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.id
								   	, communicationAssessmentPerformed:careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.communicationAssessmentPerformed.toString()
									, communicationAssessmentDate:DisplayUtils.displayDate(careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.communicationAssessmentDate)
									, communicationAssessmentTime:DisplayUtils.displayTime(careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.communicationAssessmentTime)
									, communicationNoAssessmentReason:careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.noCommunicationAssessmentReasonType?.description
									, swallowingAssessmentPerformed:careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.swallowingAssessmentPerformed.toString()
									, swallowingAssessmentDate:DisplayUtils.displayDate(careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.swallowingAssessmentDate)
									, swallowingAssessmentTime:DisplayUtils.displayTime(careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.swallowingAssessmentTime)
									, swallowingNoAssessmentReason:careActivity?.therapyManagement?.speechAndLanguageTherapyManagement?.noSwallowingAssessmentReasonType?.description]
		   
		   def physiotherapyManagement = [id:careActivity?.therapyManagement?.physiotherapyManagement?.id
								   , physioAssessmentPerformed:careActivity?.therapyManagement?.physiotherapyManagement?.assessmentPerformed.toString()
								   , physioAssessmentDate:DisplayUtils.displayDate(careActivity?.therapyManagement?.physiotherapyManagement?.assessmentDate)
								   , physioAssessmentTime:DisplayUtils.displayTime(careActivity?.therapyManagement?.physiotherapyManagement?.assessmentTime)
								   , physioNoAssessmentReason:careActivity?.therapyManagement?.physiotherapyManagement?.noAssessmentReasonType?.description]
		
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
			   					, cognitiveStatusAssessed:careActivity?.therapyManagement?.cognitiveStatusAssessed.toString()
								, cognitiveStatusAssessmentDate:DisplayUtils.displayDate(careActivity?.therapyManagement?.cognitiveStatusAssessmentDate)
								, cognitiveStatusAssessmentTime:DisplayUtils.displayTime(careActivity?.therapyManagement?.cognitiveStatusAssessmentTime)
								, cognitiveStatusNoAssessmentReason:careActivity?.therapyManagement?.cognitiveStatusNoAssessmentType?.description
								, rehabGoalsSet:careActivity?.therapyManagement?.rehabGoalsSet.toString()
								, rehabGoalsSetDate:DisplayUtils.displayDate(careActivity?.therapyManagement?.rehabGoalsSetDate)
								, rehabGoalsSetTime:DisplayUtils.displayTime(careActivity?.therapyManagement?.rehabGoalsSetTime)
								, rehabGoalsNotSetReason: careActivity?.therapyManagement?.rehabGoalsNotSetReasonType?.description
								, occupationalTherapyManagement:occupationalTherapyManagement
								, speechAndLanguageTherapyManagement:speechAndLanguageTherapyManagement
								, physiotherapyManagement:physiotherapyManagement
								, assessments:assessments]
		   def result = [therapyManagement:therapyManagement, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		   render result as JSON		
	}
	
	private	def renderTherapyManagementWithErrors = {data, careActivity, errors ->
		log.debug "In renderTherapyManagementWithErrors"
		errors.each{key, value ->
			log.debug "Adding non domain error --> ${key} :: ${value}"
			careActivity.errors.reject(value,[key]as Object[], "Custom validation failed for ${key} in the controller".toString())
		}
		def result = [TherapyManagement:data, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
	}
	
}
