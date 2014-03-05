package uk.ac.leeds.lihs.auecr.cimss.stroke

import grails.converters.JSON;
import org.codehaus.groovy.grails.web.json.JSONObject;


class OtherFeatureController extends StrokeBaseController{

   def getOtherFeaturePage = {
		log.debug "in getOtherFeaturePage"
		def careActivity = CareActivity.get(params.id)
		render(template:'/assessment/other_features',model:['careActivityInstance':careActivity])
	}

	def getOtherFeature = {
		log.debug "in getOtherFeature"
		def careActivity = CareActivity.get(params.id);
		renderOtherFeature(careActivity);
	}
	
	def updateOtherFeature = {
		log.info "in updateOtherFeature -> ${request.JSON.ClinicalAssessment}"
		def careActivity = CareActivity.get(params.id)
		def errors = [:]
		def data = request.JSON.ClinicalAssessment
		 
		if(changedSinceRetrieval(careActivity, data)){
			data.versions = getVersions(careActivity)
			data.skipBruteForce = true
			careActivity.errors.rejectValue('version', 'version.changed.fluid.management')
			careActivity.discard()
			renderOtherFeatureWithErrors(data, careActivity, errors);
		}else{
			updateData(careActivity, data, errors)
			if(careActivity.validate() & !errors){
					careActivity.save(flush:true)
					renderOtherFeature(careActivity);
			}else{
					careActivity.discard()
					renderOtherFeatureWithErrors(data, careActivity, errors);
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
	
	
	
	private def renderOtherFeature = {careActivity ->
			
			
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
								, noSwallowScreenPerformedReason:careActivity?.clinicalAssessment?.noSwallowScreenPerformedReason?.description]
		  
		  def result = [ClinicalAssessment:clinicalAssessment, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		  render result as JSON
			  
			
	}

	private def renderOtherFeatureWithErrors = {data, careActivity, errors ->
		log.debug "In renderOtherFeatureWithErrors"
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
		
		careActivity.clinicalAssessment.bestGaze = getValueFromString(data.bestGaze)
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
		
		
	}
	
	
}
	

