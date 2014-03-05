package uk.ac.leeds.lihs.auecr.cimss.stroke

import org.codehaus.groovy.grails.web.json.JSONObject;

import uk.ac.leeds.lihs.auecr.cimss.stroke.clinical.assessment.GlasgowComaScore;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.ClinicalClassificationType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.NoSwallowScreenPerformedReasonType;

import grails.converters.JSON;

class ClinicalAssessmentController  extends  StrokeBaseController{
	
	def getClinicalAssessmentPage = {
		log.debug "in getClinicalAssessmentPage"
		def careActivity = CareActivity.get(params.id)
		render(template:'/assessment/clinical',model:['careActivityInstance':careActivity])
	}

    def getClinicalAssessment = {
		log.debug "in getClinicalAssessment"
		def careActivity = CareActivity.get(params.id)
		renderClinicalAssessment(careActivity);
	}
	
	
	def updateClinicalAssessment = {
		log.info "in updateAdmissionDetails -> ${request.JSON.ClinicalAssessment}"
		def careActivity = CareActivity.get(params.id)
		def errors = [:]		
		def data = request.JSON.ClinicalAssessment
		
		
		if(changedSinceRetrieval(careActivity, data)){
			data.versions = getVersions(careActivity)
			careActivity.errors.rejectValue('version', 'version.changed.clinical.assessment')
			careActivity.discard()
			renderClinicalAssessmentWithErrors( data, careActivity, errors)
		}else{
			updateData(careActivity, data, errors)
			if(!errors & careActivity.validate()){
				careActivity.save(flush:true)
				renderClinicalAssessment(careActivity)
			}else{
				careActivity.discard()
				renderClinicalAssessmentWithErrors( data, careActivity, errors)
			}
		}
		
	}	
	
	
	private def getVersions = { careActivity ->
		return ['careActivity':careActivity?.version
			,'clinicalAssessment':careActivity?.clinicalAssessment?.version
			,'glasgowComaScore':careActivity?.clinicalAssessment?.glasgowComaScore?.version]
	}
	
	private def changedSinceRetrieval = { careActivity, data ->
		
		if(careActivity?.clinicalAssessment?.version!=null && data.versions.clinicalAssessment == JSONObject.NULL){
			return true
		}
		
		if(careActivity?.clinicalAssessment?.version && careActivity.clinicalAssessment?.version >(long)data.versions.clinicalAssessment){
			return true
			
		}
		
		
		if(careActivity?.clinicalAssessment?.glasgowComaScore?.version!=null  && data.versions.glasgowComaScore == JSONObject.NULL){
			return true	
		}
		if(careActivity?.clinicalAssessment?.glasgowComaScore?.version && careActivity.clinicalAssessment?.glasgowComaScore?.version >(long)data.versions.glasgowComaScore){
			return true
			
		}
		
		
		return false
	}
	
	
	private def updateData = {careActivity, data, errors ->
		if(!careActivity.clinicalAssessment){
			careActivity.clinicalAssessment = new ClinicalAssessment()
			careActivity.clinicalAssessment.careActivity = careActivity
		}
		
		
		careActivity.clinicalAssessment.facialWeakness = getBooleanFromString(data.facialWeakness)
		
		if(data.leftFaceAffected && data.leftFaceAffected	!=JSONObject.NULL && data.leftFaceAffected!="null"){
			careActivity.clinicalAssessment.leftFaceAffected = data.leftFaceAffected
		}else{
			careActivity.clinicalAssessment.leftFaceAffected = null
		}
		
		
		if(data.rightFaceAffected && data.rightFaceAffected	!=JSONObject.NULL && data.rightFaceAffected!="null"){
			careActivity.clinicalAssessment.rightFaceAffected = data.rightFaceAffected
		}else{
			careActivity.clinicalAssessment.rightFaceAffected = null
		}
		
		if(data.neitherFaceAffected && data.neitherFaceAffected	!=JSONObject.NULL && data.neitherFaceAffected!="null"){
			careActivity.clinicalAssessment.neitherFaceAffected = data.neitherFaceAffected
			careActivity.clinicalAssessment.leftFaceAffected = null
			careActivity.clinicalAssessment.rightFaceAffected = null
		}else{
			careActivity.clinicalAssessment.neitherFaceAffected = null
		}
		
		careActivity.clinicalAssessment.bestGaze = getValueFromString(data.bestGaze)
		
		careActivity.clinicalAssessment.dominantHand = getValueFromString(data.dominantHand)
		
		careActivity.clinicalAssessment.faceSensoryLoss = getValueFromString(data.faceSensoryLoss)
		careActivity.clinicalAssessment.facialPalsy = getValueFromString(data.facialPalsy)
		
		careActivity.clinicalAssessment.dysarthria = getValueFromString(data.dysarthria)
		careActivity.clinicalAssessment.aphasia = getValueFromString(data.aphasia)
		careActivity.clinicalAssessment.hemianopia = getValueFromString(data.hemianopia)
		careActivity.clinicalAssessment.inattention = getValueFromString(data.inattention)
		careActivity.clinicalAssessment.limbAtaxia = getValueFromString(data.limbAtaxia)
		
		careActivity.clinicalAssessment.locStimulation = getValueFromString(data.locStimulation)
		careActivity.clinicalAssessment.locQuestions = getValueFromString(data.locQuestions)
		careActivity.clinicalAssessment.locTasks = getValueFromString(data.locTasks)
		
	
		
		careActivity.clinicalAssessment.other = getValueFromString(data.other)
		
		
		
		
		if(careActivity.clinicalAssessment.other=="yes"){
			careActivity.clinicalAssessment.otherText = getValueFromString(data.otherText)
		}else{
			careActivity.clinicalAssessment.otherText = null
		}
		
		if(getValueFromString(data.classification)){
			careActivity.clinicalAssessment.classification = ClinicalClassificationType.findByDescription(data.classification)
		}else{
			careActivity.clinicalAssessment.classification= null;
		}
		careActivity.clinicalAssessment.independent = getValueFromString(data.independent)
		
		careActivity.clinicalAssessment.armSensoryLoss = getValueFromString(data.armSensoryLoss)
		careActivity.clinicalAssessment.armMrcScale = getIntegerFromString(data.armMrcScale)
		
		
		if(data.leftArmAffected && data.leftArmAffected	!=JSONObject.NULL && data.leftArmAffected!="null"){
			careActivity.clinicalAssessment.leftArmAffected = data.leftArmAffected
		}else{
			careActivity.clinicalAssessment.leftArmAffected = null
		}
		
		if(data.rightArmAffected && data.rightArmAffected	!=JSONObject.NULL && data.rightArmAffected!="null"){
			careActivity.clinicalAssessment.rightArmAffected = data.rightArmAffected
		}else{
			careActivity.clinicalAssessment.rightArmAffected = null
		}
		
		if(data.neitherArmAffected && data.neitherArmAffected	!=JSONObject.NULL && data.neitherArmAffected!="null"){
			careActivity.clinicalAssessment.neitherArmAffected = data.neitherArmAffected
			careActivity.clinicalAssessment.leftArmAffected = null
			careActivity.clinicalAssessment.rightArmAffected = null
		}else{
			careActivity.clinicalAssessment.neitherArmAffected = null
		}
		
		careActivity.clinicalAssessment.legSensoryLoss = getValueFromString(data.legSensoryLoss)
		careActivity.clinicalAssessment.legMrcScale = getIntegerFromString(data.legMrcScale)
		
			
		if(data.leftLegAffected && data.leftLegAffected	!=JSONObject.NULL && data.leftLegAffected!="null"){
			careActivity.clinicalAssessment.leftLegAffected = data.leftLegAffected
		}else{
			careActivity.clinicalAssessment.leftLegAffected = null
		}
		
		
		if(data.rightLegAffected && data.rightLegAffected	!=JSONObject.NULL && data.rightLegAffected!="null"){
			careActivity.clinicalAssessment.rightLegAffected = data.rightLegAffected
		}else{
			careActivity.clinicalAssessment.rightLegAffected = null
		}
		
		if(data.neitherLegAffected && data.neitherLegAffected	!=JSONObject.NULL && data.neitherLegAffected!="null"){
			careActivity.clinicalAssessment.neitherLegAffected = data.neitherLegAffected
			careActivity.clinicalAssessment.leftLegAffected = null
			careActivity.clinicalAssessment.rightLegAffected = null
		}else{
			careActivity.clinicalAssessment.neitherLegAffected = null
		}
		
		/*careActivity.clinicalAssessment.leftLegAffected = data.leftLegAffected
		careActivity.clinicalAssessment.rightLegAffected = data.rightLegAffected
		*/
		careActivity.clinicalAssessment.walkAtPresentation = getBooleanFromString(data.walkAtPresentation)
		if(careActivity.clinicalAssessment.walkAtPresentation == Boolean.TRUE){
			careActivity.clinicalAssessment.mobilePreStroke= null;
		}else{
			careActivity.clinicalAssessment.mobilePreStroke= getBooleanFromString(data.mobilePreStroke)
		}
		
		careActivity.clinicalAssessment.swallowScreenPerformed = getBooleanFromString(data.swallowScreenPerformed)
		if(careActivity.clinicalAssessment.swallowScreenPerformed == Boolean.TRUE){
			try{
				careActivity.clinicalAssessment.swallowScreenDate = DisplayUtils.getDate(data.swallowScreenDate)
			}catch(IllegalArgumentException iae){
				errors.put("clinicalAssessment.swallowScreenDate", "swallow.screened.date.invalid.format")
			}
			try{
				careActivity.clinicalAssessment.swallowScreenTime = DisplayUtils.getTime(data.swallowScreenTime)
			}catch(IllegalArgumentException iae){
				errors.put("clinicalAssessment.swallowScreenTime", "swallow.screened.time.invalid.format")
			}
			careActivity.clinicalAssessment.noSwallowScreenPerformedReason= null;
		}else if(careActivity.clinicalAssessment.swallowScreenPerformed == Boolean.FALSE ){
			if(getValueFromString(data.noSwallowScreenPerformedReason)){
				careActivity.clinicalAssessment.noSwallowScreenPerformedReason= NoSwallowScreenPerformedReasonType.findByDescription(data.noSwallowScreenPerformedReason)
			}else{
				careActivity.clinicalAssessment.noSwallowScreenPerformedReason= null
			}
			careActivity.clinicalAssessment.swallowScreenDate = null;
			careActivity.clinicalAssessment.swallowScreenTime = null;
		}else{
			careActivity.clinicalAssessment.noSwallowScreenPerformedReason= null
			careActivity.clinicalAssessment.swallowScreenDate = null;
			careActivity.clinicalAssessment.swallowScreenTime = null;
		}

		updateGlasgowComaScore(careActivity.clinicalAssessment , data.glasgowComaScore, errors)
		
	}
	
	
		
		
		
	
		
	private def renderClinicalAssessment = {careActivity ->
		
		  def glasgowComaScore = [id:careActivity?.clinicalAssessment?.glasgowComaScore?.id
								   , dateAssessed:DisplayUtils.displayDate(careActivity?.clinicalAssessment?.glasgowComaScore?.dateAssessed)
								, timeAssessed:DisplayUtils.displayTime(careActivity?.clinicalAssessment?.glasgowComaScore?.timeAssessed)
								, motorScore:careActivity?.clinicalAssessment?.glasgowComaScore?.motorScore
								, eyeScore:careActivity?.clinicalAssessment?.glasgowComaScore?.eyeScore
								, verbalScore:careActivity?.clinicalAssessment?.glasgowComaScore?.verbalScore]
		  
		  def clinicalAssessment = [id:careActivity?.clinicalAssessment?.id
			  					, versions:getVersions(careActivity)
								, facialWeakness:careActivity?.clinicalAssessment?.facialWeakness?.toString()
								, leftFaceAffected:careActivity?.clinicalAssessment?.leftFaceAffected
								, rightFaceAffected:careActivity?.clinicalAssessment?.rightFaceAffected
								, neitherFaceAffected:careActivity?.clinicalAssessment?.neitherFaceAffected
								, facialPalsy:careActivity?.clinicalAssessment?.facialPalsy?.toString()
								, faceSensoryLoss:careActivity?.clinicalAssessment?.faceSensoryLoss
								, dominantHand:careActivity?.clinicalAssessment?.dominantHand
								, dysarthria:careActivity?.clinicalAssessment?.dysarthria
								, bestGaze:careActivity?.clinicalAssessment?.bestGaze
								, locStimulation:careActivity?.clinicalAssessment?.locStimulation
								, locQuestions:careActivity?.clinicalAssessment?.locQuestions
								, locTasks:careActivity?.clinicalAssessment?.locTasks
								, aphasia:careActivity?.clinicalAssessment?.aphasia
								, hemianopia:careActivity?.clinicalAssessment?.hemianopia
								, inattention:careActivity?.clinicalAssessment?.inattention
								, limbAtaxia:careActivity?.clinicalAssessment?.limbAtaxia
								, other:careActivity?.clinicalAssessment?.other
								, otherText:careActivity?.clinicalAssessment?.otherText
								, classification:careActivity?.clinicalAssessment?.classification?.description
								, independent:careActivity?.clinicalAssessment?.independent
								, armSensoryLoss:careActivity?.clinicalAssessment?.armSensoryLoss
								, armMrcScale:careActivity?.clinicalAssessment?.armMrcScale?.toString()
								, leftArmAffected:careActivity?.clinicalAssessment?.leftArmAffected
								, rightArmAffected:careActivity?.clinicalAssessment?.rightArmAffected
								, neitherArmAffected:careActivity?.clinicalAssessment?.neitherArmAffected
								, legSensoryLoss:careActivity?.clinicalAssessment?.legSensoryLoss
								, legMrcScale:careActivity?.clinicalAssessment?.legMrcScale?.toString()
								, leftLegAffected:careActivity?.clinicalAssessment?.leftLegAffected
								, rightLegAffected:careActivity?.clinicalAssessment?.rightLegAffected
								, neitherLegAffected:careActivity?.clinicalAssessment?.neitherLegAffected
								, walkAtPresentation:careActivity?.clinicalAssessment?.walkAtPresentation.toString()
								, mobilePreStroke:careActivity?.clinicalAssessment?.mobilePreStroke.toString()
								, swallowScreenPerformed:careActivity?.clinicalAssessment?.swallowScreenPerformed.toString()
								, swallowScreenDate:DisplayUtils.displayDate(careActivity?.clinicalAssessment?.swallowScreenDate)
								, swallowScreenTime:DisplayUtils.displayTime(careActivity?.clinicalAssessment?.swallowScreenTime)
								, noSwallowScreenPerformedReason:careActivity?.clinicalAssessment?.noSwallowScreenPerformedReason?.description
								, glasgowComaScore:glasgowComaScore]
		  
		  def result = [ClinicalAssessment:clinicalAssessment, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		  render result as JSON
		  
		
	}
	
	private def renderClinicalAssessmentWithErrors = {data, careActivity, errors ->
		log.debug "In renderClinicalAssessmentWithErrors"
		errors.each{key, value ->
			log.debug "Adding non domain error --> ${key} :: ${value}"
			careActivity.errors.reject(value,[key]as Object[], "Custom validation failed for ${key} in the controller".toString())
		}
		def result = [ClinicalAssessment:data, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
	}
		
	private def updateGlasgowComaScore = {clinicalAssessment, glasgowComaScore , errors ->
		if(hasGlasgowComaScoreData(glasgowComaScore)){
			if(!clinicalAssessment.glasgowComaScore){
				clinicalAssessment.glasgowComaScore = new GlasgowComaScore()
				clinicalAssessment.glasgowComaScore.clinicalAssessment = clinicalAssessment
			}
			try{
				clinicalAssessment.glasgowComaScore.dateAssessed = DisplayUtils.getDate(glasgowComaScore.dateAssessed)
			}catch(IllegalArgumentException iae){
				errors.put("clinicalAssessment.glasgowComaScore.dateAssessed", "glasgow.coma.score.date.invalid.format")
			}
			try{
				clinicalAssessment.glasgowComaScore.timeAssessed = DisplayUtils.getTime(glasgowComaScore.timeAssessed) 
			}catch(IllegalArgumentException iae){
				errors.put("clinicalAssessment.glasgowComaScore.timeAssessed", "glasgow.coma.score.time.invalid.format")
			}
			
			
			
			clinicalAssessment.glasgowComaScore.motorScore = getIntegerFromString(glasgowComaScore.motorScore) 
			clinicalAssessment.glasgowComaScore.eyeScore = getIntegerFromString(glasgowComaScore.eyeScore)
			clinicalAssessment.glasgowComaScore.verbalScore = getIntegerFromString(glasgowComaScore.verbalScore) 
		}else{
			if(clinicalAssessment.glasgowComaScore){
				clinicalAssessment.glasgowComaScore.delete();
				clinicalAssessment.glasgowComaScore = null;
			}
		}
	}
	
	private def hasGlasgowComaScoreData(glasgowComaScore){
		return (glasgowComaScore.dateAssessed 
				|| glasgowComaScore.timeAssessed 
				|| glasgowComaScore.motorScore
				|| glasgowComaScore.eyeScore
				|| glasgowComaScore.verbalScore)
	}
	
	
}
