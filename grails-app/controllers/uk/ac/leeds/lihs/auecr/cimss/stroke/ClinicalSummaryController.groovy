package uk.ac.leeds.lihs.auecr.cimss.stroke

import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.ClinicalClassificationType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.LevelOfConsciousness;

import org.codehaus.groovy.grails.web.json.JSONObject;
import grails.converters.JSON;

class ClinicalSummaryController extends  StrokeBaseController{

    def getClinicalSummaryPage = {
		log.debug "in getClinicalSummaryPage"
		def careActivity = CareActivity.get(params.id)
		render(template:'/clinicalSummary/clinicalSummary',model:['careActivityInstance':careActivity])
	}

    def getClinicalSummary = {
		log.debug "in getClinicalSummary"
		def careActivity = CareActivity.get(params.id)
		renderClinicalSummary(careActivity);
	}
	
	
	def updateClinicalSummary = {
		log.info "in updateClinicalSummary -> ${request.JSON.clinicalSummary}"
		def careActivity = CareActivity.get(params.id)
		def errors = [:]		
		def data = request.JSON.clinicalSummary
		
		
		if(changedSinceRetrieval(careActivity, data)){
			data.versions = getVersions(careActivity)
			careActivity.errors.rejectValue('version', 'version.changed.clinical.summary')
			careActivity.discard()
			renderClinicalSummaryWithErrors( data, careActivity, errors)
		}else{
			updateData(careActivity, data, errors)
			if(!errors & careActivity.validate()){
				careActivity.save(flush:true)
				renderClinicalSummary(careActivity)
			}else{
				careActivity.discard()
				renderClinicalSummaryWithErrors( data, careActivity, errors)
			}
		}
		
		
		
		
	}	
	
	private def getVersions = { careActivity ->
		return ['careActivity':careActivity?.version,
				'clinicalSummary':careActivity?.clinicalSummary?.version]
	}
	
	private def changedSinceRetrieval = { careActivity, data ->		
		if(careActivity?.clinicalSummary?.version!=null && data.versions.clinicalSummary == JSONObject.NULL){
			return true
		}	
		if(careActivity?.clinicalSummary?.version && careActivity.clinicalSummary?.version >(long)data.versions.clinicalSummary){
			return true	
		}
		return false
	}
	
	private def updateData = {careActivity, data, errors ->
		def clinicalSummary = ensureClinicalSummaryExists(careActivity)	
		if(getValueFromString(data.worstLevelOfConsciousness)){
			clinicalSummary?.worstLevelOfConsciousness = LevelOfConsciousness.findByDescription(getValueFromString(data.worstLevelOfConsciousness))
			
		}else{
			clinicalSummary?.worstLevelOfConsciousness = null
		}
		
		clinicalSummary?.urinaryTractInfection = getValueFromString(data.urinaryTractInfection)
		clinicalSummary?.newPneumonia = getValueFromString(data.newPneumonia)
		
		clinicalSummary?.palliativeCare = getValueFromString(data.palliativeCare)
		if(clinicalSummary?.palliativeCare.equals('yes')){
		
			def palliativeCareDate = DisplayUtils.getDate(data.palliativeCareDate , errors, "clinicalSummary.palliativeCareDate", "paliiative.care.date.invalid.format")
			
			if ( palliativeCareDate && careActivity.medicalHistory.inpatientAtOnset && careActivity.hoursSinceOnset(palliativeCareDate,0)>72) {
				errors.put("clinicalSummary.palliativeCareDate", "palliative.care.date.cannot.be.72.hrs.after.onset")
			} else if (palliativeCareDate && !careActivity.medicalHistory.inpatientAtOnset && careActivity.hoursSinceAdmission(palliativeCareDate,0)>72) {
				errors.put("clinicalSummary.palliativeCareDate", "palliative.care.date.cannot.be.72.hrs.after.admission")
			} else {			
				clinicalSummary?.palliativeCareDate = palliativeCareDate
			}
			clinicalSummary?.endOfLifePathway = getValueFromString(data.endOfLifePathway)
		}else{
			clinicalSummary?.palliativeCareDate = null;
			clinicalSummary?.endOfLifePathway = null;
		}
		
		if(!careActivity.clinicalAssessment){
			careActivity.clinicalAssessment = new ClinicalAssessment()
			careActivity.clinicalAssessment.careActivity = careActivity
		}
		if(getValueFromString(data.classification)){
			
			careActivity.clinicalAssessment.classification = ClinicalClassificationType.findByDescription(data.classification)
		}else{
			careActivity.clinicalAssessment.classification= null;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	private ClinicalSummary ensureClinicalSummaryExists(careActivity) {
		def clinicalSummary  = careActivity.clinicalSummary;
		if(!clinicalSummary){
			careActivity.clinicalSummary = new ClinicalSummary();
			clinicalSummary = careActivity.clinicalSummary
			clinicalSummary.careActivity = careActivity
		}
		return clinicalSummary
	}
	
	private def renderClinicalSummary = {careActivity ->
		def clinicalSummary = [id:careActivity?.clinicalSummary?.id,
								versions:getVersions(careActivity),
								classification:careActivity?.clinicalAssessment?.classification?.description,
								worstLevelOfConsciousness:careActivity?.clinicalSummary?.worstLevelOfConsciousness?.description,
								urinaryTractInfection:careActivity?.clinicalSummary?.urinaryTractInfection,
								newPneumonia:careActivity?.clinicalSummary?.newPneumonia,
								palliativeCare:careActivity?.clinicalSummary?.palliativeCare,
								palliativeCareDate:DisplayUtils.displayDate(careActivity?.clinicalSummary?.palliativeCareDate),
								endOfLifePathway:careActivity?.clinicalSummary?.endOfLifePathway]
		  
		def result = [clinicalSummary:clinicalSummary, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
	}
	
	private def renderClinicalSummaryWithErrors = {data, careActivity, errors ->
		log.debug "In renderClinicalSummaryWithErrors"
		errors.each{key, value ->
			log.debug "Adding non domain error --> ${key} :: ${value}"
			careActivity.errors.reject(value,[key]as Object[], "Custom validation failed for ${key} in the controller".toString())
		}
		def result = [clinicalSummary:data, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
	}
	
	
}
