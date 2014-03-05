package uk.ac.leeds.lihs.auecr.cimss.stroke

import grails.converters.JSON;
import org.codehaus.groovy.grails.web.json.JSONObject;

import uk.ac.leeds.lihs.auecr.cimss.stroke.clinical.assessment.GlasgowComaScore;

class GlasgowComaScoreController extends StrokeBaseController{

   def getGlasgowComaScorePage = {
		log.debug "in getGlasgowComaScorePage"
		def careActivity = CareActivity.get(params.id)
		render(template:'/assessment/glasgowComaScore',model:['careActivityInstance':careActivity])
	}

	def getGlasgowComaScore = {
		log.debug "in getGlasgowComaScore"
		def careActivity = CareActivity.get(params.id);
		renderGlasgowComaScore(careActivity);
	}
	
	def updateGlasgowComaScore = {
		log.info "in updateGlasgowComaScore -> ${request.JSON.ClinicalAssessment}"
		def careActivity = CareActivity.get(params.id)
		def errors = [:]
		def data = request.JSON.ClinicalAssessment
		 
		if(changedSinceRetrieval(careActivity, data)){
			data.versions = getVersions(careActivity)
			data.skipBruteForce = true
			careActivity.errors.rejectValue('version', 'version.changed.fluid.management')
			careActivity.discard()
			renderGlasgowComaScoreWithErrors(data, careActivity, errors);
		}else{
			updateData(careActivity, data, errors)
			if(careActivity.validate() & !errors){
					careActivity.save(flush:true)
					renderGlasgowComaScore(careActivity);
			}else{
					careActivity.discard()
					renderGlasgowComaScoreWithErrors(data, careActivity, errors);
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
	
	
	
	private def renderGlasgowComaScore = {careActivity ->
			
			  def glasgowComaScore = [id:careActivity?.clinicalAssessment?.glasgowComaScore?.id
									   , dateAssessed:DisplayUtils.displayDate(careActivity?.clinicalAssessment?.glasgowComaScore?.dateAssessed)
									, timeAssessed:DisplayUtils.displayTime(careActivity?.clinicalAssessment?.glasgowComaScore?.timeAssessed)
									, motorScore:careActivity?.clinicalAssessment?.glasgowComaScore?.motorScore
									, eyeScore:careActivity?.clinicalAssessment?.glasgowComaScore?.eyeScore
									, verbalScore:careActivity?.clinicalAssessment?.glasgowComaScore?.verbalScore]
			  
			  def clinicalAssessment = [id:careActivity?.clinicalAssessment?.id
									  , versions:getVersions(careActivity)
									  , glasgowComaScore:glasgowComaScore]
			  
			  def result = [ClinicalAssessment:clinicalAssessment, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
			  render result as JSON
			  
			
	}

	private def renderGlasgowComaScoreWithErrors = {data, careActivity, errors ->
		log.debug "In renderGlasgowComaScoreWithErrors"
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
		updateTheGlasgowComaScore(careActivity.clinicalAssessment , data.glasgowComaScore, errors)		
	}
	
	
	private def updateTheGlasgowComaScore = {clinicalAssessment, glasgowComaScore , errors ->
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
	

