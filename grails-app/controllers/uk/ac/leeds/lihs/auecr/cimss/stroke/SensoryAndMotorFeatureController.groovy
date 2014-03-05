package uk.ac.leeds.lihs.auecr.cimss.stroke

import grails.converters.JSON;
import org.codehaus.groovy.grails.web.json.JSONObject;


class SensoryAndMotorFeatureController extends StrokeBaseController{

   def getSensoryAndMotorFeaturePage = {
		log.debug "in getSensoryAndMotorFeaturePage"
		def careActivity = CareActivity.get(params.id)
		render(template:'/assessment/sensory_and_motor_features',model:['careActivityInstance':careActivity])
	}

	def getSensoryAndMotorFeature = {
		log.debug "in getSensoryAndMotorFeature"
		def careActivity = CareActivity.get(params.id);
		renderSensoryAndMotorFeature(careActivity);
	}
	
	def updateSensoryAndMotorFeature = {
		log.info "in updateSensoryAndMotorFeature -> ${request.JSON.ClinicalAssessment}"
		def careActivity = CareActivity.get(params.id)
		def errors = [:]
		def data = request.JSON.ClinicalAssessment
		 
		if(changedSinceRetrieval(careActivity, data)){
			data.versions = getVersions(careActivity)
			data.skipBruteForce = true
			careActivity.errors.rejectValue('version', 'version.changed.fluid.management')
			careActivity.discard()
			renderSensoryAndMotorFeatureErrors(data, careActivity, errors);
		}else{
			updateData(careActivity, data, errors)
			if(careActivity.validate() & !errors){
					careActivity.save(flush:true)
					renderSensoryAndMotorFeature(careActivity);
			}else{
					careActivity.discard()
					renderSensoryAndMotorFeatureWithErrors(data, careActivity, errors);
			}
		}
	}
	
	private def getVersions = { careActivity ->
		return ['careActivity':careActivity?.version
			,'clinicalAssessment':careActivity?.clinicalAssessment?.version
			,'glasgowComaScore':careActivity?.clinicalAssessment?.glasgowComaScore?.version]
	}
	
	private def changedSinceRetrieval = { careActivity, data ->	
		return false
	}
	
	
	
	private def renderSensoryAndMotorFeature = {careActivity ->
			
			  
			def clinicalAssessment = [id:careActivity?.clinicalAssessment?.id
			  					, versions:getVersions(careActivity)
								, facialWeakness:careActivity?.clinicalAssessment?.facialWeakness?.toString()
								, leftFaceAffected:careActivity?.clinicalAssessment?.leftFaceAffected
								, rightFaceAffected:careActivity?.clinicalAssessment?.rightFaceAffected
								, neitherFaceAffected:careActivity?.clinicalAssessment?.neitherFaceAffected
								, facialPalsy:careActivity?.clinicalAssessment?.facialPalsy?.toString()
								, faceSensoryLoss:careActivity?.clinicalAssessment?.faceSensoryLoss
								, sensoryLoss:careActivity?.clinicalAssessment?.sensoryLoss
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
								, rightArmMrcScale:careActivity?.clinicalAssessment?.rightArmMrcScale?.toString()
								, leftArmMrcScale:careActivity?.clinicalAssessment?.leftArmMrcScale?.toString()
								, leftArmAffected:careActivity?.clinicalAssessment?.leftArmAffected
								, rightArmAffected:careActivity?.clinicalAssessment?.rightArmAffected
								, neitherArmAffected:careActivity?.clinicalAssessment?.neitherArmAffected
								, legSensoryLoss:careActivity?.clinicalAssessment?.legSensoryLoss
								, leftLegMrcScale:careActivity?.clinicalAssessment?.leftLegMrcScale?.toString()
								, rightLegMrcScale:careActivity?.clinicalAssessment?.rightLegMrcScale?.toString()
								, leftLegAffected:careActivity?.clinicalAssessment?.leftLegAffected
								, rightLegAffected:careActivity?.clinicalAssessment?.rightLegAffected
								, neitherLegAffected:careActivity?.clinicalAssessment?.neitherLegAffected
								, leftSideAffected:careActivity?.clinicalAssessment?.leftSideAffected
								, rightSideAffected:careActivity?.clinicalAssessment?.rightSideAffected
								, neitherSideAffected:careActivity?.clinicalAssessment?.neitherSideAffected
								
								
								, walkAtPresentation:careActivity?.clinicalAssessment?.walkAtPresentation.toString()
								, mobilePreStroke:careActivity?.clinicalAssessment?.mobilePreStroke.toString()
								, swallowScreenPerformed:careActivity?.clinicalAssessment?.swallowScreenPerformed.toString()
								, swallowScreenDate:DisplayUtils.displayDate(careActivity?.clinicalAssessment?.swallowScreenDate)
								, swallowScreenTime:DisplayUtils.displayTime(careActivity?.clinicalAssessment?.swallowScreenTime)
								, noSwallowScreenPerformedReason:careActivity?.clinicalAssessment?.noSwallowScreenPerformedReason?.description]
		  
		  def result = [ClinicalAssessment:clinicalAssessment, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		  render result as JSON
			  
			
	}

	private def renderSensoryAndMotorFeatureWithErrors = {data, careActivity, errors ->
		log.debug "In renderSensoryAndMotorFeatureWithErrors"
		errors.each{key, value ->
			log.debug "Adding non domain error --> ${key} :: ${value}"
			careActivity.errors.reject(value,[key]as Object[], "Custom validation failed for ${key} in the controller".toString())
		}
		def result = [ClinicalAssessment:data, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
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
		

		//
		if(data.leftSideAffected && data.leftSideAffected	!=JSONObject.NULL && data.leftSideAffected!="null"){
			careActivity.clinicalAssessment.leftSideAffected = data.leftSideAffected
		}else{
			careActivity.clinicalAssessment.leftSideAffected = null
		}
		
		
		if(data.rightSideAffected && data.rightSideAffected	!=JSONObject.NULL && data.rightSideAffected!="null"){
			careActivity.clinicalAssessment.rightSideAffected = data.rightSideAffected
		}else{
			careActivity.clinicalAssessment.rightSideAffected = null
		}
		
		if(data.neitherSideAffected && data.neitherSideAffected	!=JSONObject.NULL && data.neitherSideAffected!="null"){
			careActivity.clinicalAssessment.neitherSideAffected = data.neitherSideAffected
			careActivity.clinicalAssessment.leftSideAffected = null
			careActivity.clinicalAssessment.rightSideAffected = null
		}else{
			careActivity.clinicalAssessment.neitherSideAffected = null
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		careActivity.clinicalAssessment.sensoryLoss = getValueFromString(data.sensoryLoss)
		
		
		careActivity.clinicalAssessment.dominantHand = getValueFromString(data.dominantHand)
		
		careActivity.clinicalAssessment.faceSensoryLoss = getValueFromString(data.faceSensoryLoss)
		careActivity.clinicalAssessment.facialPalsy = getValueFromString(data.facialPalsy)
	
		
		careActivity.clinicalAssessment.armSensoryLoss = getValueFromString(data.armSensoryLoss)
		careActivity.clinicalAssessment.leftArmMrcScale = getIntegerFromString(data.leftArmMrcScale)
		careActivity.clinicalAssessment.rightArmMrcScale = getIntegerFromString(data.rightArmMrcScale)
		
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
		careActivity.clinicalAssessment.leftLegMrcScale = getIntegerFromString(data.leftLegMrcScale)
		careActivity.clinicalAssessment.rightLegMrcScale = getIntegerFromString(data.rightLegMrcScale)
		
			
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
		
		
		careActivity.clinicalAssessment.walkAtPresentation = getBooleanFromString(data.walkAtPresentation)
		if(careActivity.clinicalAssessment.walkAtPresentation == Boolean.TRUE){
			careActivity.clinicalAssessment.mobilePreStroke= null;
		}else{
			careActivity.clinicalAssessment.mobilePreStroke= getBooleanFromString(data.mobilePreStroke)
		}
		
		
	}
	
	
	
	

	
	
	
}
	

