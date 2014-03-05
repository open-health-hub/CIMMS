package uk.ac.leeds.lihs.auecr.cimss.stroke

import uk.ac.leeds.lihs.auecr.cimss.stroke.therapy.AssessmentManagement;
import grails.converters.JSON;
import org.codehaus.groovy.grails.web.json.JSONObject;

class PostDischargeController extends StrokeBaseController{

	def getPostDischargePage = {
		def careActivity = CareActivity.get(params.id)
		render(template:'/discharge/postDischarge',model:['careActivityInstance':careActivity])
	}
	
	def getPostDischarge = {
		def careActivity = CareActivity.get(params.id)
		renderPostDischarge(careActivity);
	}
		
	def updatePostDischarge = {
		log.info "in updatePostDischarge -> ${request.JSON.Discharge}"
		def careActivity = CareActivity.get(params.id)
		def errors = [:]
		def data = request.JSON.Discharge
		
		if(changedSinceRetrieval(careActivity, data)){
			data.versions = getVersions(careActivity)
			careActivity.errors.rejectValue('version', 'version.changed.discharge')
			careActivity.discard()
			renderPostDischargeWithErrors(data, errors, careActivity);
		}else{
			updateData(careActivity, data, errors)
			if(!errors & careActivity.validate()){
				careActivity.save(flush:true)
				renderPostDischarge(careActivity);
			}else{
				careActivity.discard()
				renderPostDischargeWithErrors(data, errors, careActivity);
			}
		}
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
			
	private def getVersions = { careActivity ->
		return ['careActivity':careActivity?.version
			,'postDischargeCare':careActivity?.postDischargeCare?.version]
	}
		
	private def updateData = {careActivity, data, errors ->
		updatePostDischargeCare(careActivity, data, errors)
	}
		
	
	private def updatePostDischargeCare = {careActivity, data, errors ->
		def postDischargeCare = careActivity.postDischargeCare
		
		if(!postDischargeCare){
			postDischargeCare = new PostDischargeCare();
			careActivity.postDischargeCare = postDischargeCare
			postDischargeCare.careActivity = careActivity;
		}
		
		def medicalHistory  = careActivity.medicalHistory;
		if(!medicalHistory){
			careActivity.medicalHistory = new MedicalHistory();
			medicalHistory = careActivity.medicalHistory
			medicalHistory.careActivity=careActivity
		}
		
		def hasNone		
		if (data.postDischargeSupport) {
			processPostDischargeSupportData(postDischargeCare, data, hasNone)
		}else {
			processDischargeDestination(postDischargeCare, data, errors)
			processDischargeDates(postDischargeCare, data, errors)
		}	
		
		postDischargeCare.ssnapParticipationConsent = getValueFromString(data.ssnapParticipationConsent);
		postDischargeCare.newCareTeam = getValueFromString(data.newCareTeam);
		// --
		
		postDischargeCare?.palliativeCare = getValueFromString(data.palliativeCare)
		if(postDischargeCare?.palliativeCare.equals('yes')){
			postDischargeCare?.palliativeCareDate = DisplayUtils.getDate(data.palliativeCareDate , errors, "clinicalSummary.palliativeCareDate", "paliiative.care.date.invalid.format")
			postDischargeCare?.endOfLifePathway = getValueFromString(data.endOfLifePathway)
		}else{
			postDischargeCare?.palliativeCareDate = null;
			postDischargeCare?.endOfLifePathway = null;
		}
	}

	private processDischargeDates(PostDischargeCare postDischargeCare, data, errors) {
		postDischargeCare.dischargedTo = getValueFromString(data.dischargedTo)
		if(postDischargeCare.dischargedTo=="died" && getBooleanFromString(data.strokeUnitDeath) ){
			postDischargeCare?.careActivity.medicalHistory.strokeWardDischargeDate = null
			postDischargeCare?.careActivity.medicalHistory.strokeWardDischargeTime = null
			postDischargeCare?.careActivity.medicalHistory.hospitalDischargeDate = null
			postDischargeCare?.careActivity.medicalHistory.hospitalDischargeTime = null
		} else {
			try {
				if ( data.strokeWardDischargeDate == null ) {
					// errors.put("medicalHistory.strokeWardDischargeDate", "medical.history.stokeward.discharge.date.required") 
				} else {
					postDischargeCare?.careActivity.medicalHistory.strokeWardDischargeDate = DisplayUtils.getDate(data.strokeWardDischargeDate)
				}
			} catch(IllegalArgumentException iae){
				errors.put("medicalHistory.strokeWardDischargeDate", "medical.history.stokeward.discharge.date.invalid.format")
			}
			try {
				if ( data.strokeWardDischargeTime == null ) {
					// errors.put("medicalHistory.strokeWardDischargeTime", "medical.history.stokeward.discharge.time.required")
				} else {
					postDischargeCare?.careActivity.medicalHistory.strokeWardDischargeTime = DisplayUtils.getTime(data.strokeWardDischargeTime)
				}				
			} catch(IllegalArgumentException iae){
				errors.put("medicalHistory.strokeWardDischargeTime", "medical.history.stokeward.discharge.time.invalid.format")
			}
			validateStrokeWardDischargeDate(postDischargeCare?.careActivity, data, errors)
			try {
				postDischargeCare?.careActivity.medicalHistory.hospitalDischargeDate = DisplayUtils.getDate(data.hospitalDischargeDate)
			} catch(IllegalArgumentException iae){
				errors.put("medicalHistory.hospitalDischargeDate", "medical.history.hospital.discharge.date.invalid.format")
			}
			try {
				postDischargeCare?.careActivity.medicalHistory.hospitalDischargeTime = DisplayUtils.getTime(data.hospitalDischargeTime)
			} catch(IllegalArgumentException iae){
				errors.put("medicalHistory.hospitalDischargeTime", "medical.history.hospital.discharge.time.invalid.format")
			}
		}
	}
	
	private processDischargeDestination(PostDischargeCare postDischargeCare, data, errors) {
		postDischargeCare.careActivity.medicalHistory.strokeUnitDeath = Boolean.FALSE
		
		postDischargeCare.dischargedTo = getValueFromString(data.dischargedTo)
		if(postDischargeCare.dischargedTo=="intermediateCare"){			
			clearDischargeToHome(postDischargeCare)
			clearDischargeToResidentialCareHome(postDischargeCare)
			clearDischargeToNursingCareHome(postDischargeCare)			
			postDischargeCare.strokeSpecialist = getBooleanFromString(data.strokeSpecialist)
		}else if(postDischargeCare.dischargedTo=="home"){
			clearDischargeToIntermediateCare(postDischargeCare)
			clearDischargeToResidentialCareHome(postDischargeCare)
			clearDischargeToNursingCareHome(postDischargeCare)	
			
			if(getValueFromString(data.alonePostDischarge)== "unknown"){
				postDischargeCare.alonePostDischargeUnknown = Boolean.TRUE
			}else{
				postDischargeCare.alonePostDischargeUnknown = null
			}
				
			if(postDischargeCare.alonePostDischargeUnknown==Boolean.TRUE){
				postDischargeCare.alonePostDischarge = null;
			}else{
				postDischargeCare.alonePostDischarge = getBooleanFromString(data.alonePostDischarge)
			}
			
			postDischargeCare.shelteredAccommodation = getBooleanFromString(data.shelteredAccommodation)
				
		}else if(postDischargeCare.dischargedTo=="residentialCareHome" || postDischargeCare.dischargedTo=="nursingCareHome"){
			clearDischargeToIntermediateCare(postDischargeCare)
			clearDischargeToHome(postDischargeCare)
			clearDischargeToNursingCareHome(postDischargeCare)
			postDischargeCare.patientPreviouslyResident = getBooleanFromString(data.patientPreviouslyResident)
			if(postDischargeCare.patientPreviouslyResident==Boolean.TRUE){
				postDischargeCare.temporaryOrPermanent = null
			}else{
				postDischargeCare.temporaryOrPermanent = getValueFromString(data.temporaryOrPermanent)
			}
		} else if (postDischargeCare.dischargedTo=="died") {
			log.debug("Date of death: "+getValueFromString(data.deathDate))
			if ( validateDeathDate( postDischargeCare.careActivity, data, errors ) ) {
				postDischargeCare.careActivity.medicalHistory.strokeUnitDeath =  getBooleanFromString(data.strokeUnitDeath)
				postDischargeCare.careActivity.patient.dateOfDeath = DisplayUtils.getDate(data.deathDate)
				setDeathRankin(postDischargeCare.careActivity.therapyManagement)
			}
			
		} else {
			clearDischargeToIntermediateCare(postDischargeCare)
			clearDischargeToHome(postDischargeCare)
			clearDischargeToResidentialCareHome(postDischargeCare)
			clearDischargeToNursingCareHome(postDischargeCare)	

		}
	}

	private setDeathRankin(therapyManagement) {
		ensureAssessmentManagementExists(therapyManagement)
		def baselineModifiedRankin = therapyManagement.assessmentManagement.findModifiedRankinByStage("Discharge");
		if(!baselineModifiedRankin) {
			baselineModifiedRankin = therapyManagement.assessmentManagement.addModifiedRankinByStage("Discharge")
		}
		baselineModifiedRankin.modifiedRankinScore = 6
	}
	
	private ensureAssessmentManagementExists(therapyManagement) {
		if(!therapyManagement?.assessmentManagement){
			therapyManagement.assessmentManagement = new AssessmentManagement()
			therapyManagement.assessmentManagement.therapyManagement = therapyManagement
		}
	}
	private validateStrokeWardDischargeDate(CareActivity careActivity, data, errors) {
		def status = Boolean.TRUE
		def dateFormatOK = Boolean.FALSE
		
		if ( careActivity.medicalHistory.didNotStayInStrokeWard != Boolean.TRUE && data.dischargedTo != null && !(data.dischargedTo == "died" && getBooleanFromString(data.strokeUnitDeath) ) ) {
			try {
				careActivity.medicalHistory.strokeWardDischargeTime = DisplayUtils.getTime(data.strokeWardDischargeTime)
//				if ( careActivity.medicalHistory.strokeWardDischargeTime == null ) {
//					errors.put("medicalHistory.strokeWardDischargeTime", "strokeward.discharge.time.missing")
//					status = Boolean.FALSE
//				}
				dateFormatOK = Boolean.TRUE
				
				careActivity.medicalHistory.strokeWardDischargeDate = DisplayUtils.getDate(data.strokeWardDischargeDate)
				if ( careActivity.medicalHistory.strokeWardDischargeDate == null ) {
//					errors.put("medicalHistory.strokeWardDischargeDate", "strokeward.discharge.date.missing")
//					status = Boolean.FALSE
				}								
				else if (careActivity.beforeBirth(careActivity.medicalHistory.strokeWardDischargeDate,careActivity.medicalHistory.strokeWardDischargeTime)) {
					errors.put("medicalHistory.strokeWardDischargeDate", "strokeward.discharge.date.before.birth")
					status = Boolean.FALSE
				}
				else if (careActivity.medicalHistory.strokeWardDischargeDate > new Date()) {
					errors.put("medicalHistory.strokeWardDischargeDate", "strokeward.discharge.date.in.future")
					status = Boolean.FALSE
				}
				else if (careActivity.beforeAdmission(careActivity.medicalHistory.strokeWardDischargeDate,careActivity.medicalHistory.strokeWardDischargeTime)) {
					errors.put("medicalHistory.strokeWardDischargeDate", "strokeward.discharge.date.before.admission")
					status = Boolean.FALSE
				}

			} catch(IllegalArgumentException iae){
				if (dateFormatOK) {
					errors.put("medicalHistory.strokeWardDischargeDate", "strokeward.discharge.date.invalid")
				} else {
					errors.put("medicalHistory.strokeWardDischargeTime", "strokeward.discharge.time.invalid")
				}
				status = Boolean.FALSE
			}
		}
		return status
	}

	private validateDeathDate(CareActivity careActivity, data, errors) {
		def status = Boolean.TRUE
		
		if ( data.dischargedTo == "died" ) {
			try {
				careActivity.patient.dateOfDeath = DisplayUtils.getDate(data.deathDate)
				if ( careActivity.patient.dateOfDeath == null ) {
					errors.put("patient.dateOfDeath", "postdischarge.death.date.missing")
					status = Boolean.FALSE
				}
				else if (careActivity.beforeBirth(careActivity.patient.dateOfDeath,0)) {
					errors.put("patient.dateOfDeath", "postdischarge.death.date.before.birth")
					status = Boolean.FALSE
				}
				else if (careActivity.patient.dateOfDeath > new Date()) {
					errors.put("patient.dateOfDeath", "postdischarge.death.date.in.future")
					status = Boolean.FALSE
				}
				else if (careActivity.beforeAdmission(careActivity.patient.dateOfDeath,0)) {
					errors.put("patient.dateOfDeath", "postdischarge.death.date.before.admission")
					status = Boolean.FALSE
				}

			} catch(IllegalArgumentException iae){
				errors.put("patient.dateOfDeath", "postdischarge.death.date.invalid")
				status = Boolean.FALSE
			}
			if ( data.strokeUnitDeath == null ) {
				error.put("medicalHistory.strokeUnitDeath", "postdischarge.stroke.unit.death.missing")
				status = Boolean.FALSE
			} 
		} 
		return status
	}
	
	private clearDischargeToIntermediateCare(PostDischargeCare postDischargeCare) {		
		postDischargeCare.strokeSpecialist = null		
	}

	private clearDischargeToHome(PostDischargeCare postDischargeCare) {
		postDischargeCare.alonePostDischarge = null
		postDischargeCare.alonePostDischargeUnknown = null
		postDischargeCare.shelteredAccommodation = null
	}

	private clearDischargeToResidentialCareHome(PostDischargeCare postDischargeCare) {
		postDischargeCare.patientPreviouslyResident = null
		postDischargeCare.temporaryOrPermanent = null
	}
	
	private clearDischargeToNursingCareHome(PostDischargeCare postDischargeCare) {
		postDischargeCare.patientPreviouslyResident = null
		postDischargeCare.temporaryOrPermanent = null
	}
	
	
	
	private getPostDischargeSupportMap(postDischargeCare) {
		def map = null
		if (postDischargeCare) {
			map = [:]
			map.put("socialServices",postDischargeCare.hasSupport("Social Services"))
			map.put("informalCarers",postDischargeCare.hasSupport("Informal Carers"))
			//map.put("family",postDischargeCare.hasSupport("Family"))
			map.put("palliativeCare",postDischargeCare.hasSupport("Palliative Care"))
			//map.put("none",postDischargeCare.hasSupport("None"))
			map.put("socialServicesUnavailable",postDischargeCare.hasSupport("Social Services Unavailable"))
			map.put("patientRefused",postDischargeCare.hasSupport("Patient Refused"))
			map.put("strokeNeurologySpecificEsd",postDischargeCare.hasSupport("Stroke/neurology specific ESD"))
			map.put("nonSpecialistEsd",postDischargeCare.hasSupport("Non specialist ESD"))
			map.put("strokeNeurologySpecificRehabilitation",postDischargeCare.hasSupport("Stroke/neurology specific community rehabilitation team"))
			map.put("nonSpecialistRehabilitation",postDischargeCare.hasSupport("Non specialist community rehabilitation team"))
		}
		return map
	}

	
	private def renderPostDischarge = {careActivity ->
		
		
		def discharge =[versions:getVersions(careActivity)
			, inRandomisedTrial:careActivity.findCareActivityProperty("inRandomisedTrial")
			, fitForDischargeDate:DisplayUtils.displayDate(careActivity.fitForDischargeDate)
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
			,strokeSpecialist: getBooleanAsString(careActivity.postDischargeCare?.strokeSpecialist)
			,alonePostDischarge: careActivity.postDischargeCare?.alonePostDischargeUnknown == Boolean.TRUE ? "unknown" : getBooleanAsString(careActivity.postDischargeCare?.alonePostDischarge)
			,shelteredAccommodation: getBooleanAsString(careActivity.postDischargeCare?.shelteredAccommodation)
			,patientPreviouslyResident: getBooleanAsString(careActivity.postDischargeCare?.patientPreviouslyResident)
			,temporaryOrPermanent: careActivity.postDischargeCare?.temporaryOrPermanent
			,postDischargeTherapy: getPostDischargeTherapyMap(careActivity.postDischargeCare)
			,postDischargeSupport: getPostDischargeSupportMap(careActivity.postDischargeCare)			
			,palliativeCare72:careActivity?.clinicalSummary?.palliativeCare
			,palliativeCare:careActivity?.postDischargeCare?.palliativeCare
			,palliativeCareDate72:DisplayUtils.displayDate(careActivity?.clinicalSummary?.palliativeCareDate)
			,palliativeCareDate:DisplayUtils.displayDate(careActivity?.postDischargeCare?.palliativeCareDate)
			,endOfLifePathway:careActivity?.postDischargeCare?.endOfLifePathway
			,endOfLifePathway72:careActivity?.clinicalSummary?.endOfLifePathway
			,ssnapParticipationConsent:careActivity.postDischargeCare?.ssnapParticipationConsent
			,newCareTeam:careActivity.postDischargeCare?.newCareTeam
			,strokeUnitDeath: getBooleanAsString(careActivity.medicalHistory?.strokeUnitDeath)
			,deathDate: DisplayUtils.displayDate(careActivity.patient.dateOfDeath)	
			,strokeWardDischargeDate: DisplayUtils.displayDate(careActivity.medicalHistory?.strokeWardDischargeDate)
			,strokeWardDischargeTime: DisplayUtils.displayTime(careActivity.medicalHistory?.strokeWardDischargeTime)
			,hospitalDischargeDate:DisplayUtils.displayDate(careActivity.medicalHistory?.hospitalDischargeDate)
			,hospitalDischargeTime:DisplayUtils.displayTime(careActivity.medicalHistory?.hospitalDischargeTime)
			,didNotStayInStrokeUnit:getBooleanAsString(careActivity.medicalHistory?.didNotStayInStrokeWard)
			]
		def result = [Discharge:discharge, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
	}
	
	private def renderPostDischargeWithErrors = { data, errors, careActivity  ->
		log.debug "In renderDischargeWithErrors"
		data.remove('originalData')
		errors.each{key, value ->
			log.debug "${key} :: ${value}"
			careActivity.errors.rejectValue(key, value, "Custom validation failed for ${key} in the controller")
		}
		def result = [Discharge:data, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
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
	
		
}
