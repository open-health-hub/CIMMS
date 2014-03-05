package uk.ac.leeds.lihs.auecr.cimss.stroke



import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.PostDischargeSupportType
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.PostDischargeTherapyType
import uk.ac.leeds.lihs.auecr.cimss.stroke.post.discharge.PostDischargeSupport;
import uk.ac.leeds.lihs.auecr.cimss.stroke.post.discharge.PostDischargeTherapy


import grails.converters.JSON;

import org.codehaus.groovy.grails.web.json.JSONObject;

class PreDischargeController extends StrokeBaseController{


	def getPreDischargePage = {
		def careActivity = CareActivity.get(params.id)
		render(template:'/discharge/preDischarge',model:['careActivityInstance':careActivity])
	}	
	
	def getPreDischarge = {
		def careActivity = CareActivity.get(params.id)
		renderPreDischarge(careActivity);
	}
		
	def updatePreDischarge = {
		log.info "in updatePreDischarge -> ${request.JSON.Discharge}"
		def careActivity = CareActivity.get(params.id)
		def errors = [:]
		def data = request.JSON.Discharge
		
		if(changedSinceRetrieval(careActivity, data)){
			data.versions = getVersions(careActivity)
			careActivity.errors.rejectValue('version', 'version.changed.discharge')
			careActivity.discard()
			renderPreDischargeWithErrors(data, errors, careActivity);
		}else{
			updateData(careActivity, data, errors)
			if(!errors & careActivity.validate()){
				careActivity.save(flush:true)
				renderPreDischarge(careActivity);
			}else{
				careActivity.discard()
				renderPreDischargeWithErrors(data, errors, careActivity);
			}
		}
	}
	
	private def getVersions = { careActivity ->
		return ['careActivity':careActivity?.version
			,'postDischargeCare':careActivity?.postDischargeCare?.version]
	}
		
	private def changedSinceRetrieval = { careActivity, data ->
		return false;
		
		if(careActivity.version && careActivity.version >(long)data.versions.careActivity){
			if(careActivity.findCareActivityProperty("inRandomisedTrial") != getValueFromString(data.originalData.inRandomisedTrial)){
				return true
			}
			if(careActivity.fitForDischargeDateUnknown != getBooleanFromString(data.originalData.fitForDischargeDateUnknown)){
				return true
			}
			
			
			
			if(careActivity.fitForDischargeDate != DisplayUtils.getDate(data.originalData.fitForDischargeDate)){
				return true
			}
			
			if(careActivity.fitForDischargeTime != DisplayUtils.getDate(data.originalData.fitForDischargeTime)){
				return true
			}
			
			
			if(careActivity.socialWorkerReferral != getValueFromString(data.originalData.socialWorkerReferral)){
					return true
			}
			if(careActivity.socialWorkerReferralDate != DisplayUtils.getDate(data.originalData.socialWorkerReferralDate)){
				return true
			}
			if(careActivity.socialWorkerReferralUnknown != getBooleanFromString(data.originalData.socialWorkerReferralUnknown)){
				return true
			}
			if(careActivity.socialWorkerAssessment != getValueFromString(data.originalData.socialWorkerAssessment)){
				return true
			}
			if(careActivity.socialWorkerAssessmentDate != DisplayUtils.getDate(data.originalData.socialWorkerAssessmentDate)){
				return true
			}
			if(careActivity.socialWorkerAssessmentUnknown != getBooleanFromString(data.originalData.socialWorkerAssessmentUnknown)){
				return true
			}
		}
		
		if(careActivity?.postDischargeCare?.version && careActivity.postDischargeCare?.version >(long)data.versions.postDischargeCare){
			return true
			
		}
		
		return false
	}
		
	private def updateData = {careActivity, data, errors ->
		updateCareActivity(careActivity, data, errors)
		updatePostDischargeCare(careActivity, data, errors)
	}
		
	private def updateCareActivity = {careActivity, data, errors ->
		updateFitForDischarge(careActivity, data, errors)
		updateSocialWorker(careActivity, data, errors)
		updateInRandomisedTrial(careActivity, data, errors)
	}
	
	private def updateFitForDischarge= {careActivity, data, errors ->
		setUnknownField(careActivity, "fitForDischargeDateUnknown", data.fitForDischargeDateUnknown)
		if(careActivity.fitForDischargeDateUnknown==Boolean.TRUE){
			careActivity.fitForDischargeDate = null
			careActivity.fitForDischargeTime = null
		}else{
			careActivity.fitForDischargeDate = DisplayUtils.getDate(data.fitForDischargeDate, errors, "fitForDischargeDate", "fit.for.discharge.date.invalid.format")
			careActivity.fitForDischargeTime = DisplayUtils.getTime(data.fitForDischargeTime, errors, "fitForDischargeTime", "fit.for.discharge.date.time.invalid.format")
		}
	}
			
	private def updateSocialWorker = {careActivity, data, errors ->
		
		careActivity.socialWorkerReferral = getValueFromString(data.socialWorkerReferral)
		if(careActivity.socialWorkerReferral){
			if ((careActivity.socialWorkerReferral=="NotApplicable") || (careActivity.socialWorkerReferral=="No")) {
				careActivity.socialWorkerReferralDate = null
				careActivity.socialWorkerReferralUnknown = null
				careActivity.socialWorkerAssessment = null
				careActivity.socialWorkerAssessmentDate = null
				careActivity.socialWorkerAssessmentUnknown = null
				
				
			} else {
				setUnknownField(careActivity, "socialWorkerReferralUnknown", data.socialWorkerReferralUnknown)
				if(careActivity.socialWorkerReferralUnknown==Boolean.TRUE){
					careActivity.socialWorkerReferralDate = null
				}else{
					careActivity.socialWorkerReferralDate = DisplayUtils.getDate(data.socialWorkerReferralDate, errors, "socialWorkerReferralDate", "social.worker.referral.date.invalid.format")
				}
				
				careActivity.socialWorkerAssessment = getValueFromString(data.socialWorkerAssessment)
				if(careActivity.socialWorkerAssessment){
					if (careActivity.socialWorkerAssessment=="No") {
						careActivity.socialWorkerAssessmentDate = null
						careActivity.socialWorkerAssessmentUnknown = null
					} else {
						setUnknownField(careActivity, "socialWorkerAssessmentUnknown", data.socialWorkerAssessmentUnknown)
						if(careActivity.socialWorkerAssessmentUnknown==Boolean.TRUE){
							careActivity.socialWorkerAssessmentDate = null
						}else{
							careActivity.socialWorkerAssessmentDate = DisplayUtils.getDate(data.socialWorkerAssessmentDate, errors, "socialWorkerAssessmentDate", "social.worker.assessment.date.invalid.format")
						}
					}
				}else{
					careActivity.socialWorkerAssessment = null
					careActivity.socialWorkerAssessmentDate = null
					careActivity.socialWorkerAssessmentUnknown = null
				}
						
			}
		}else{
			careActivity.socialWorkerReferralDate = null
			careActivity.socialWorkerReferralUnknown = null
			careActivity.socialWorkerAssessmentDate = null
			careActivity.socialWorkerAssessmentUnknown = null
		}
		
		
		
		
		
		
	}
		
	private def updateInRandomisedTrial =  {careActivity, data, errors ->
		def inRandomisedTrial = getBooleanFromString(data.inRandomisedTrial)
		if(inRandomisedTrial && inRandomisedTrial == Boolean.TRUE){
			careActivity.setCareActivityProperty("inRandomisedTrial", "true")
		}else{
			if(inRandomisedTrial == Boolean.FALSE){
				careActivity.setCareActivityProperty("inRandomisedTrial", "false")
			}else{
				careActivity.clearCareActivityProperty("inRandomisedTrial")
			}
		}
	}
		
	private def updatePostDischargeCare = {careActivity, data, errors ->
		def postDischargeCare = careActivity.postDischargeCare
		
		if(!postDischargeCare){
			postDischargeCare = new PostDischargeCare();
			careActivity.postDischargeCare = postDischargeCare
		}
					
		
		if (data.postDischargeSupport) {
			processPostDischargeSupportData(postDischargeCare, data, errors)
			
		}
	}

	

	private processPostDischargeSupportData(PostDischargeCare postDischargeCare, data, errors) {
		postDischargeCare.documentedEvidence = getValueFromString(data.documentedEvidence)
		postDischargeCare.documentationPostDischarge = getBooleanFromString(data.documentationPostDischarge)
		
		postDischargeCare.supportOnDischargeNeeded = getValueFromString(data.supportOnDischargeNeeded)
		
		
		processNonTherapyBasedSupport(postDischargeCare, data)
		processTherapyBasedSupport(postDischargeCare, data, errors)
		
		
		
	}

	private processTherapyBasedSupport(PostDischargeCare postDischargeCare, data, errors) {
	
		
		if( data.postDischargeSupport.esdType == "specialist"){
			processPostDischargeSupport(postDischargeCare, "Stroke/neurology specific ESD", true);
			postDischargeCare.deleteSupport("Non specialist ESD")
			postDischargeCare.deleteSupport("No ESD")
		}else if ( data.postDischargeSupport.esdType == "nonSpecialist"){
			processPostDischargeSupport(postDischargeCare, "Non specialist ESD", true);
			postDischargeCare.deleteSupport("Stroke/neurology specific ESD")
			postDischargeCare.deleteSupport("No ESD")
		}else if ( data.postDischargeSupport.esdType == "noESD"){
			processPostDischargeSupport(postDischargeCare, "No ESD", true);
			postDischargeCare.deleteSupport("Stroke/neurology specific ESD")
			postDischargeCare.deleteSupport("Non specialist ESD")
		}else{
			postDischargeCare.deleteSupport("Non specialist ESD")
			postDischargeCare.deleteSupport("Stroke/neurology specific ESD")
			postDischargeCare.deleteSupport("No ESD")
		}
		
		
		if(postDischargeCare != null){
			if(postDischargeCare.hasSupport("Stroke/neurology specific ESD")){
				setUnknownField(postDischargeCare, "esdReferralDateUnknown", data.esdReferralDateUnknown)
				if(postDischargeCare.esdReferralDateUnknown==Boolean.TRUE){
					postDischargeCare.esdReferralDate = null
				}else{
					postDischargeCare.esdReferralDate = DisplayUtils.getDate(data.esdReferralDate, errors, "postDischargeCare.esdReferralDate", "esd.referral.date.invalid.format")
				}
			}else{
				postDischargeCare.esdReferralDate = null
				postDischargeCare.esdReferralDateUnknown = null
			}
		}
		
		
		
		
		//println "***** ${data.postDischargeSupport.rehabilitationType}"
		
		if( data.postDischargeSupport.rehabilitationType == "specific"){
			processPostDischargeSupport(postDischargeCare, "Stroke/neurology specific community rehabilitation team", true);
			postDischargeCare.deleteSupport("Non specialist community rehabilitation team")
			postDischargeCare.deleteSupport("No rehabilitation")
		}else if ( data.postDischargeSupport.rehabilitationType == "general"){
			processPostDischargeSupport(postDischargeCare, "Non specialist community rehabilitation team", true);
			postDischargeCare.deleteSupport("Stroke/neurology specific community rehabilitation team")
			postDischargeCare.deleteSupport("No rehabilitation")
		}else if ( data.postDischargeSupport.rehabilitationType == "noRehabilitation"){
			processPostDischargeSupport(postDischargeCare, "No rehabilitation", true);
			postDischargeCare.deleteSupport("Stroke/neurology specific community rehabilitation team")
			postDischargeCare.deleteSupport("Non specialist community rehabilitation team")
		}else{
			postDischargeCare.deleteSupport("Non specialist community rehabilitation team")
			postDischargeCare.deleteSupport("Stroke/neurology specific community rehabilitation team")
			postDischargeCare.deleteSupport("No rehabilitation")
		}
		
		
		
		
		
		
	}

	private processNonTherapyBasedSupport(PostDischargeCare postDischargeCare, data) {
		if(postDischargeCare.supportOnDischargeNeeded == "Yes"){
			processPostDischargeSupport(postDischargeCare, "Patient Refused", data.postDischargeSupport.patientRefused);
			if(postDischargeCare.hasSupport("Patient Refused")){
				postDischargeCare.deleteSupport("Social Services Unavailable")
				postDischargeCare.deleteSupport("Social Services")
				postDischargeCare.deleteSupport("Informal Carers")
				postDischargeCare.deleteSupport("Palliative Care")
			}else{
				processPostDischargeSupport(postDischargeCare, "Social Services Unavailable", data.postDischargeSupport.socialServicesUnavailable);
				if(postDischargeCare.hasSupport("Social Services Unavailable")){
						postDischargeCare.deleteSupport("Social Services")
				}else {
					processPostDischargeSupport(postDischargeCare, "Social Services", data.postDischargeSupport.socialServices);
				}
				processPostDischargeSupport(postDischargeCare, "Informal Carers", data.postDischargeSupport.informalCarers);
				processPostDischargeSupport(postDischargeCare, "Palliative Care", data.postDischargeSupport.palliativeCare);
			}
			
			
			
			
			
			if (data.postDischargeSupport.socialServicesUnavailable == Boolean.TRUE
											|| data.postDischargeSupport.patientRefused == Boolean.TRUE) {
				postDischargeCare.numberOfSocialServiceVisitsUnknown = null
				postDischargeCare.numberOfSocialServiceVisits = null
			}
			else if(data.postDischargeSupport.socialServices == Boolean.TRUE || data.postDischargeSupport.informalCarers == Boolean.TRUE) {
				setUnknownField(postDischargeCare, "numberOfSocialServiceVisitsUnknown", data.numberOfSocialServiceVisitsUnknown)
				if(postDischargeCare.numberOfSocialServiceVisitsUnknown==Boolean.TRUE) {
					postDischargeCare.numberOfSocialServiceVisits = null
				}
				else {
					postDischargeCare.numberOfSocialServiceVisits = getIntegerFromString(data.numberOfSocialServiceVisits)
				}
			}else {
				postDischargeCare.numberOfSocialServiceVisitsUnknown = null
				postDischargeCare.numberOfSocialServiceVisits = null
			}
		}else{
			postDischargeCare.deleteSupport("Patient Refused")
			postDischargeCare.deleteSupport("Social Services")
			postDischargeCare.deleteSupport("Informal Carers")
			postDischargeCare.deleteSupport("Palliative Care")
			postDischargeCare.deleteSupport("Social Services Unavailable")
			postDischargeCare.numberOfSocialServiceVisitsUnknown = null
			postDischargeCare.numberOfSocialServiceVisits = null
		
		
		}
		
		
		
	}
	
			
	private hasPostDischargeCareData(data) {
		return data.esdReferral || data.esdReferralDate || data.esdReferralDateUnknown || data.postDischargeTherapy || data.postDischargeSupport
	}
	
	private def processPostDischargeTherapy = { postDischargeCare, type, data, hasNone ->
			
		if (data) {
			if (!hasNone && !postDischargeCare.hasTherapy(type) ) {
				def postDischargeTherapy = new PostDischargeTherapy()
				
				postDischargeTherapy.type = PostDischargeTherapyType.findByDescription(type)
				postDischargeTherapy.postDischargeCare = postDischargeCare
				postDischargeCare.addToPostDischargeTherapy(postDischargeTherapy)
			}else if (hasNone){
				postDischargeCare.deleteTherapy(type)
			}
		} else if (postDischargeCare.hasTherapy(type)) {
			postDischargeCare.deleteTherapy(type)
		}
		
	}
	
	private def processPostDischargeSupport = { postDischargeCare, type, data ->
		if (data) {
			if (!postDischargeCare.hasSupport(type) ) {
				def postDischargeSupport = new PostDischargeSupport()
				postDischargeSupport.type = PostDischargeSupportType.findByDescription(type)
				postDischargeSupport.postDischargeCare = postDischargeCare
				postDischargeCare.addToPostDischargeSupport(postDischargeSupport)
			}
		} else if (postDischargeCare.hasSupport(type)) {
			postDischargeCare.deleteSupport(type)
		}
	}
	
	private setUnknownField(domainObject, fieldName, fieldValue) {
		if(fieldValue == Boolean.TRUE){
			domainObject[fieldName] = Boolean.TRUE
		}else{
			domainObject[fieldName] = null;
		}
	}
	
	
	
	private getPostDischargeTherapyMap(postDischargeCare) {
		def map = null
		if (postDischargeCare) {
			map = [:]
			//map.put("ict",postDischargeCare.hasTherapy("ICT"))
			//map.put("esd",postDischargeCare.hasTherapy("ESD"))
			//map.put("residentialRehabFacility",postDischargeCare.hasTherapy("Residential Rehab Facility"))
			//map.put("genericCommunityRehah",postDischargeCare.hasTherapy("Generic Community Rehab"))
			//map.put("none",postDischargeCare.hasTherapy("None"))
			map.put("intermediateCare",postDischargeCare.hasTherapy("Intermediate Care"))
			map.put("home",postDischargeCare.hasTherapy("Home"))
			map.put("careHome",postDischargeCare.hasTherapy("Care Home"))
			map.put("other",postDischargeCare.hasTherapy("Other"))
			
			
			
		} 
		return map
	}
	
	private getPostDischargeSupportMap(postDischargeCare,esdType, rehabilitationType) {
		def map = null
		if (postDischargeCare) {
			map = [:]
			map.put("esdType", esdType)
			map.put("rehabilitationType", rehabilitationType)
			map.put("socialServices",postDischargeCare.hasSupport("Social Services"))
			map.put("informalCarers",postDischargeCare.hasSupport("Informal Carers"))			
			//map.put("family",postDischargeCare.hasSupport("Family"))
			map.put("palliativeCare",postDischargeCare.hasSupport("Palliative Care"))
			map.put("noTherapy",postDischargeCare.hasSupport("No therapy given"))
			map.put("socialServicesUnavailable",postDischargeCare.hasSupport("Social Services Unavailable"))
			map.put("patientRefused",postDischargeCare.hasSupport("Patient Refused"))
			//map.put("strokeNeurologySpecificEsd",postDischargeCare.hasSupport("Stroke/neurology specific ESD"))
			//map.put("nonSpecialistEsd",postDischargeCare.hasSupport("Non specialist ESD"))
			//map.put("strokeNeurologySpecificRehabilitation",postDischargeCare.hasSupport("Stroke/neurology specific community rehabilitation team"))
			//map.put("nonSpecialistRehabilitation",postDischargeCare.hasSupport("Non specialist community rehabilitation team"))
		} 
		return map
	}
	

	
	private def renderPreDischarge = {careActivity ->
		
		def esdType = ""
		if( careActivity?.postDischargeCare?.hasSupport("Stroke/neurology specific ESD")){
			esdType="specialist" 
		}else if ( careActivity?.postDischargeCare?.hasSupport("Non specialist ESD")){
			esdType="nonSpecialist" 
		}else if ( careActivity?.postDischargeCare?.hasSupport("No ESD")){
			esdType="noESD" 
		}
		def rehabilitationType = ""
		if( careActivity?.postDischargeCare?.hasSupport("Stroke/neurology specific community rehabilitation team")){
			rehabilitationType="specific"
		}else if ( careActivity?.postDischargeCare?.hasSupport("Non specialist community rehabilitation team")){
			rehabilitationType="general"
		}else if ( careActivity?.postDischargeCare?.hasSupport("No rehabilitation")){
			rehabilitationType="noRehabilitation" 
		}
		
		
		def discharge =[versions:getVersions(careActivity)
			, inRandomisedTrial:careActivity.findCareActivityProperty("inRandomisedTrial")
			, fitForDischargeDate:DisplayUtils.displayDate(careActivity.fitForDischargeDate)
			, fitForDischargeTime:DisplayUtils.displayTime(careActivity.fitForDischargeTime)
			, fitForDischargeDateUnknown:careActivity.fitForDischargeDateUnknown
			,socialWorkerReferral:careActivity.socialWorkerReferral
			,socialWorkerReferralDate:DisplayUtils.displayDate(careActivity.socialWorkerReferralDate)
			,socialWorkerReferralUnknown:careActivity.socialWorkerReferralUnknown
			,socialWorkerAssessment:careActivity.socialWorkerAssessment
			,socialWorkerAssessmentDate:DisplayUtils.displayDate(careActivity.socialWorkerAssessmentDate)
			,socialWorkerAssessmentUnknown:careActivity.socialWorkerAssessmentUnknown							
			,esdReferralDate: DisplayUtils.displayDate(careActivity.postDischargeCare?.esdReferralDate)
			,esdReferralDateUnknown: careActivity.postDischargeCare?.esdReferralDateUnknown
			,documentedEvidence: careActivity.postDischargeCare?.documentedEvidence
			,documentationPostDischarge: getBooleanAsString(careActivity.postDischargeCare?.documentationPostDischarge)
			,numberOfSocialServiceVisitsUnknown: careActivity.postDischargeCare?.numberOfSocialServiceVisitsUnknown
			,numberOfSocialServiceVisits: careActivity.postDischargeCare?.numberOfSocialServiceVisits			
			,dischargedTo: careActivity.postDischargeCare?.dischargedTo
			,supportOnDischargeNeeded:careActivity?.postDischargeCare?.supportOnDischargeNeeded
			,strokeSpecialist: getBooleanAsString(careActivity.postDischargeCare?.strokeSpecialist)
			,alonePostDischarge: getBooleanAsString(careActivity.postDischargeCare?.alonePostDischarge)
			,shelteredAccommodation: getBooleanAsString(careActivity.postDischargeCare?.shelteredAccommodation)
			,patientPreviouslyResident: getBooleanAsString(careActivity.postDischargeCare?.patientPreviouslyResident)
			,temporaryOrPermanent: careActivity.postDischargeCare?.temporaryOrPermanent
			,postDischargeTherapy: getPostDischargeTherapyMap(careActivity.postDischargeCare)
			,postDischargeSupport: getPostDischargeSupportMap(careActivity.postDischargeCare, esdType, rehabilitationType)			
			]
		def result = [Discharge:discharge, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
	}
		
	private def renderPreDischargeWithErrors = { data, errors, careActivity  ->		
		log.debug "In renderDischargeWithErrors"
		data.remove('originalData')
		errors.each{key, value ->
			log.debug "${key} :: ${value}"
			careActivity.errors.rejectValue(key, value, "Custom validation failed for ${key} in the controller")
		}
		def result = [Discharge:data, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
	}
	
	
	
	
	
}


