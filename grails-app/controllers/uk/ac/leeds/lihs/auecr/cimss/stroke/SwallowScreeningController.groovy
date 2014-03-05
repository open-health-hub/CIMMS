package uk.ac.leeds.lihs.auecr.cimss.stroke

import grails.converters.JSON;
import org.codehaus.groovy.grails.web.json.JSONObject;

import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.NoSwallowScreenPerformedReasonType;


class SwallowScreeningController extends StrokeBaseController{

   def getSwallowScreeningPage = {
		log.debug "in getSwallowScreeningPage"
		def careActivity = CareActivity.get(params.id)
		render(template:'/assessment/swallow_screening',model:['careActivityInstance':careActivity])
	}

	def getSwallowScreening = {
		log.debug "in getSwallowScreening"
		def careActivity = CareActivity.get(params.id);
		renderSwallowScreening(careActivity);
	}
	
	def updateSwallowScreening = {
		log.info "in updateSwallowScreening -> ${request.JSON.ClinicalAssessment}"
		def careActivity = CareActivity.get(params.id)
		def errors = [:]
		def data = request.JSON.ClinicalAssessment
		 
		if(changedSinceRetrieval(careActivity, data)){
			data.versions = getVersions(careActivity)
			data.skipBruteForce = true
			careActivity.errors.rejectValue('version', 'version.changed.fluid.management')
			careActivity.discard()
			renderSwallowScreeningWithErrors(data, careActivity, errors);
		}else{
			updateData(careActivity, data, errors)
			if(careActivity.validate() & !errors){
					careActivity.save(flush:true)
					renderSwallowScreening(careActivity);
			}else{
					careActivity.discard()
					renderSwallowScreeningWithErrors(data, careActivity, errors);
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
	
	
	
	private def renderSwallowScreening = {careActivity ->
			
			  log.debug("careActivity?.getEffectiveStartDate() -> "+careActivity?.getEffectiveStartDate())
			 def clinicalAssessment = [id:careActivity?.clinicalAssessment?.id
			  					, versions:getVersions(careActivity)
								  , admissionDate:DisplayUtils.displayDate(careActivity?.getEffectiveStartDate())
								  , admissionTime:DisplayUtils.displayTime(careActivity?.getEffectiveStartTime())
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
								, noSwallowScreenPerformedReasonAt4Hours:careActivity?.clinicalAssessment?.noSwallowScreenPerformedReasonAt4Hours?.description
								, noSwallowScreenPerformedReason:careActivity?.clinicalAssessment?.noSwallowScreenPerformedReason?.description]
		  
		  def result = [ClinicalAssessment:clinicalAssessment, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		  render result as JSON
			  
			  
			
	}

	private def renderSwallowScreeningWithErrors = {data, careActivity, errors ->
		log.debug "In renderSwallowScreeningWithErrors"
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
			
			if(careActivity.clinicalAssessment.swallowScreenDate){
				
				
				   def hoursTaken = careActivity.hoursSinceAdmission(
							   	careActivity.clinicalAssessment.swallowScreenDate
							   , careActivity.clinicalAssessment.swallowScreenTime)
						   
				   if(!careActivity.isDateTimeWithin(careActivity.clinicalAssessment.swallowScreenDate, careActivity.clinicalAssessment.swallowScreenTime, 72) ){
					   if(getValueFromString(data.noSwallowScreenPerformedReason)){
						   careActivity.clinicalAssessment.noSwallowScreenPerformedReason= NoSwallowScreenPerformedReasonType.findByDescription(data.noSwallowScreenPerformedReason)
					   }else{
					   		careActivity.clinicalAssessment.noSwallowScreenPerformedReason= null
					   }
				   }else{				   
					 careActivity.clinicalAssessment.noSwallowScreenPerformedReason= null;
				   }
				   				   
				   if(!careActivity.isDateTimeWithin(careActivity.clinicalAssessment.swallowScreenDate, careActivity.clinicalAssessment.swallowScreenTime, 4) ){
					   if(getValueFromString(data.noSwallowScreenPerformedReasonAt4Hours)){
						   careActivity.clinicalAssessment.noSwallowScreenPerformedReasonAt4Hours= NoSwallowScreenPerformedReasonType.findByDescription(data.noSwallowScreenPerformedReasonAt4Hours)
					   }else{
							careActivity.clinicalAssessment.noSwallowScreenPerformedReasonAt4Hours= null
					   }
				   }else{				   
					 careActivity.clinicalAssessment.noSwallowScreenPerformedReasonAt4Hours= null;
				   }
			  }
			
			
			
			
			
			
			
		}else if(careActivity.clinicalAssessment.swallowScreenPerformed == Boolean.FALSE ){
				
			if(getValueFromString(data.noSwallowScreenPerformedReason)){
				careActivity.clinicalAssessment.noSwallowScreenPerformedReason= NoSwallowScreenPerformedReasonType.findByDescription(data.noSwallowScreenPerformedReason)
				careActivity.clinicalAssessment.noSwallowScreenPerformedReasonAt4Hours = NoSwallowScreenPerformedReasonType.findByDescription(data.noSwallowScreenPerformedReason)
			}else{
				careActivity.clinicalAssessment.noSwallowScreenPerformedReason= null
				careActivity.clinicalAssessment.noSwallowScreenPerformedReasonAt4Hours = null
			}
			careActivity.clinicalAssessment.swallowScreenDate = null;
			careActivity.clinicalAssessment.swallowScreenTime = null;
			
		}else{
		
		
			careActivity.clinicalAssessment.noSwallowScreenPerformedReason= null
			careActivity.clinicalAssessment.swallowScreenDate = null;
			careActivity.clinicalAssessment.swallowScreenTime = null;
			careActivity.clinicalAssessment.noSwallowScreenPerformedReasonAt4Hours= null;
		}
		
	}
	
	
	
	

	
	
	
}
	

