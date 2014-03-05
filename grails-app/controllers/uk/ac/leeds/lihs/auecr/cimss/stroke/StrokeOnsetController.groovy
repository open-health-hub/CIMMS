package uk.ac.leeds.lihs.auecr.cimss.stroke

import grails.converters.JSON;
import org.codehaus.groovy.grails.web.json.JSONObject;

import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.ComorbidityType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.StaffRole;


class StrokeOnsetController extends StrokeBaseController{

   def getStrokeOnsetPage = {
		log.debug "in getStrokeOnsetPage"
		def careActivity = CareActivity.get(params.id)
		render(template:'/admission/stroke_onset',model:['careActivityInstance':careActivity])
	}

	def getStrokeOnset = {
		log.debug "in getStrokeOnset"
		def careActivity = CareActivity.get(params.id);
		renderStrokeOnset(careActivity);
	}
	
	def updateStrokeOnset = {
		log.info "in updateStrokeOnset -> ${request.JSON.AdmissionDetails}"
		def careActivity = CareActivity.get(params.id)
		def errors = [:]
		def data = request.JSON.AdmissionDetails
		 
		if(changedSinceRetrieval(careActivity, data)){
			data.versions = getVersions(careActivity)
			data.skipBruteForce = true
			careActivity.errors.rejectValue('version', 'version.changed.onset.admission')
			careActivity.discard()
			renderStrokeOnsetWithErrors(data, careActivity, errors);
		}else{
			updateData(careActivity, data, errors)
			if(careActivity.validate() & !errors){
					careActivity.save(flush:true)
					renderStrokeOnset(careActivity);
			}else{
					careActivity.discard()
					renderStrokeOnsetWithErrors(data, careActivity, errors);
			}
		}
	}
	
	private def getVersions = { careActivity ->
		return ['careActivity':careActivity?.version
			,'medicalHistory':careActivity?.medicalHistory?.version
			,'patientLifeStyle':careActivity?.patientLifeStyle?.version
			, 'evaluations':getEvaluationVersions(careActivity)]
		
	}
	
	
	private def getEvaluationVersions = { careActivity ->
		def result = [:]
		careActivity?.evaluations.each {evaluation ->
			result.put(evaluation.id, evaluation.version)
		} 
		return result		
    }
	
	private def changedSinceRetrieval = { careActivity, data ->	
		return false
	}
	
	
	
	private def renderStrokeOnset = {careActivity ->
		log.trace "In renderStrokeOnset"
		
		
		def onset = [duringSleep:getBooleanAsString(careActivity?.medicalHistory?.duringSleep)
					 , onsetDate:DisplayUtils.displayDate(careActivity?.medicalHistory?.onsetDate)
					 , onsetDateEstimated:"${careActivity?.medicalHistory?.onsetDateEstimated}"
					 , onsetDateUnknown:careActivity?.medicalHistory?.onsetDateUnknown
					 , onsetTime:DisplayUtils.displayTime(careActivity?.medicalHistory?.onsetTime)
					 , onsetTimeEstimated:"${careActivity?.medicalHistory?.onsetTimeEstimated}"
					 , onsetTimeUnknown:careActivity?.medicalHistory?.onsetTimeUnknown
					 , inpatientAtOnset:careActivity?.medicalHistory?.inpatientAtOnset
					 , strokeWardAdmissionDate:DisplayUtils.displayDate(careActivity?.medicalHistory?.strokeWardAdmissionDate)
					 , strokeWardAdmissionTime:DisplayUtils.displayTime(careActivity?.medicalHistory?.strokeWardAdmissionTime)
					 
					 , strokeWardDischargeDate:DisplayUtils.displayDate(careActivity?.medicalHistory?.strokeWardDischargeDate)
					 , strokeWardDischargeTime:DisplayUtils.displayTime(careActivity?.medicalHistory?.strokeWardDischargeTime)
					 , hospitalDischargeDate:DisplayUtils.displayDate(careActivity?.medicalHistory?.hospitalDischargeDate)
					 , hospitalDischargeTime:DisplayUtils.displayTime(careActivity?.medicalHistory?.hospitalDischargeTime)

					 , didNotStayInStrokeWard:careActivity?.medicalHistory?.didNotStayInStrokeWard
					 ]
		
		def firstSeenByList = []
		
		
		def processEvaluation = {type, text ->
			def evaluation = careActivity.getEvaluation(StaffRole.findByDescription(type) );
			if(evaluation){
				firstSeenByList << [category:type
					, firstSeenDate:DisplayUtils.displayDate(evaluation.dateEvaluated)
					, firstSeenTime:DisplayUtils.displayTime(evaluation.timeEvaluated)
					, notSeen:false
					, display:text]
				
			}else{
			
				firstSeenByList << [category:type
				, firstSeenDate:""
				, firstSeenTime:""
				, notSeen:careActivity.findCareActivityProperty("${type} not seen".toString())? true:false
				, display:text]
			
			}

			
		}
		
		//processEvaluation("Stroke team member")
		processEvaluation("Ward based nurse", "Nurse trained in stroke management (within 72 hrs or not seen)")
		processEvaluation("Stroke consultant", "Stroke specialist consultant physician (within 72 hrs or not seen)")
		
		
		
		def admissionDetails = [
			versions:getVersions(careActivity)
			, onset:onset
			, firstSeenByList:firstSeenByList
			, strokeBedAvailable:careActivity.findCareActivityProperty("strokeBedAvailable")
			, inpatientAtOnset: careActivity.medicalHistory?.inpatientAtOnset.toString()
			, arriveByAmbulance: careActivity.medicalHistory?.arrival?.arriveByAmbulance.toString()
			, admissionWard: careActivity.medicalHistory?.admissionWard
			]
							
				
		def result = [AdmissionDetails:admissionDetails
						, FieldsInError: getFieldsInError(careActivity)
					    , ErrorsAsList:getErrorsAsList(careActivity)
					    , InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
			
	}

	private def renderStrokeOnsetWithErrors = {data, careActivity, errors ->
		log.debug "In renderStrokeOnsetWithErrors"
		errors.each{key, value ->
			log.debug "Adding non domain error --> ${key} :: ${value}"
			careActivity.errors.reject(value,[key]as Object[], "Custom validation failed for ${key} in the controller".toString())
		}
		def result = [AdmissionDetails:data, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
	}
		
	private def updateData = {careActivity, data, errors ->
		if(ControllerHelper.getValueFromString(data.strokeBedAvailable)){
			careActivity.setCareActivityProperty("strokeBedAvailable", data.strokeBedAvailable)
		}else{
			careActivity.clearCareActivityProperty("strokeBedAvailable")
		}
		updateMedicalHistory(careActivity, data, errors)
	}
	
	
	
	private def updateMedicalHistory = {careActivity, data, errors ->
		def medicalHistory  = careActivity.medicalHistory;
		if(!medicalHistory){
			careActivity.medicalHistory = new MedicalHistory();
			medicalHistory = careActivity.medicalHistory
			medicalHistory.careActivity=careActivity
		}

		careActivity.medicalHistory.admissionWard = data.admissionWard
		
		updateInpatientAtOnset(careActivity, data, errors)
		updateOnset(careActivity, data, errors)
		updateFirstSeenByList(careActivity, data, errors)
	}
	
	private def updateInpatientAtOnset = {careActivity, data, errors  ->
		
		if(getBooleanFromString(data.inpatientAtOnset) != null){
			careActivity.medicalHistory.inpatientAtOnset = getBooleanFromString(data.inpatientAtOnset)
			if ( careActivity.medicalHistory.inpatientAtOnset ) {
				careActivity.medicalHistory.arrival?.arriveByAmbulance = Boolean.FALSE
				log.debug("Reset arriveByAmbulance to false")
			}
		}else{
			careActivity.medicalHistory.inpatientAtOnset=null
		}
	}
	
	private def updateOnset =  {careActivity, data, errors ->
		careActivity.medicalHistory.duringSleep = getBooleanFromString(data.onset.duringSleep)
		
		
		if(data.onset.onsetDateUnknown == Boolean.TRUE){
			careActivity.medicalHistory.onsetDate = null
			careActivity.medicalHistory.onsetDateEstimated = null
			careActivity.medicalHistory.onsetDateUnknown =  data.onset.onsetDateUnknown
		}else {
			try{
				careActivity.medicalHistory.onsetDate =  DisplayUtils.getDate(data.onset.onsetDate)
				careActivity.medicalHistory.onsetDateEstimated = getBooleanFromString(data.onset.onsetDateEstimated)
			}catch(IllegalArgumentException iae){
				errors.put("medicalHistory.onsetDate", "medical.history.onset.date.invalid.format")
			}
			careActivity.medicalHistory.onsetDateUnknown = Boolean.FALSE
		}
		
		if(data.onset.onsetTimeUnknown == Boolean.TRUE){
			careActivity.medicalHistory.onsetTime = null
			careActivity.medicalHistory.onsetTimeEstimated = null
			careActivity.medicalHistory.onsetTimeUnknown =  data.onset.onsetTimeUnknown
		}else {
			try{
				careActivity.medicalHistory.onsetTime =  DisplayUtils.getTime(data.onset.onsetTime)
				careActivity.medicalHistory.onsetTimeEstimated = getBooleanFromString(data.onset.onsetTimeEstimated)
			}catch(IllegalArgumentException iae){
				errors.put("medicalHistory.onsetTime", "medical.history.onset.time.invalid.format")
			}
			careActivity.medicalHistory.onsetTimeUnknown = Boolean.FALSE
		}
		checkOnsetDateBeforeAdmissionDate(careActivity, data, errors)
		checkOnsetDateAfterAdmissionDate(careActivity, data, errors)
		
		log.debug("Did not stay in stroke ward : "+data.onset.didNotStayInStrokeWard)
		log.debug("data.onset.strokeWardAdmissionDate : "+data.onset.strokeWardAdmissionDate)
		log.debug("data.onset.strokeWardAdmissionTime : "+data.onset.strokeWardAdmissionTime)
		if(data.onset.didNotStayInStrokeWard != Boolean.TRUE){
			if (data.onset.didNotStayInStrokeWard == Boolean.FALSE) {
				careActivity.medicalHistory.didNotStayInStrokeWard = Boolean.FALSE
			}
			def mangledSuAdmissionDate = Boolean.TRUE
			try {
				Date strokeWardAdmissionDate = DisplayUtils.getDate(data.onset.strokeWardAdmissionDate)
//				if ( careActivity.medicalHistory.inpatientAtOnset )
				careActivity.medicalHistory.strokeWardAdmissionDate = strokeWardAdmissionDate
				mangledSuAdmissionDate = Boolean.FALSE
			} catch(IllegalArgumentException iae){
				errors.put("medicalHistory.strokeWardAdmissionDate", "medical.history.stokeward.arrival.date.invalid.format")
			}
			
			try {
				careActivity.medicalHistory.strokeWardAdmissionTime = DisplayUtils.getTime(data.onset.strokeWardAdmissionTime)
				if ( ! mangledSuAdmissionDate ) {
					checkStrokeWardAdmissionDateBeforeArrivalDate(careActivity, data, errors)
				}
			} catch(IllegalArgumentException iae){
				errors.put("medicalHistory.strokeWardAdmissionTime", "medical.history.stokeward.arrival.time.invalid.format")
			}
			

			def mangledSuDischargeDate = Boolean.TRUE
			try {
				Date strokeWardDischargeDate = DisplayUtils.getDate(data.onset.strokeWardDischargeDate)
				if ( careActivity.medicalHistory.inpatientAtOnset )
				careActivity.medicalHistory.strokeWardDischargeDate = strokeWardDischargeDate
				mangledSuDischargeDate = Boolean.FALSE
			} catch(IllegalArgumentException iae){
				errors.put("medicalHistory.strokeWardDischargeDate", "medical.history.stokeward.discharge.date.invalid.format")
			}
			
			try {
				careActivity.medicalHistory.strokeWardDischargeTime = DisplayUtils.getTime(data.onset.strokeWardDischargeTime)
				if ( ! mangledSuDischargeDate ) {
					checkStrokeWardDischargeDateBeforeAdmissionDate(careActivity, data, errors)
				}
			} catch(IllegalArgumentException iae){
				errors.put("medicalHistory.strokeWardDischargeTime", "medical.history.stokeward.discharge.time.invalid.format")
			}

			
			
						
		} else {
			careActivity.medicalHistory.didNotStayInStrokeWard = Boolean.TRUE
			careActivity.medicalHistory.strokeWardAdmissionDate = null
			careActivity.medicalHistory.strokeWardAdmissionTime = null
			careActivity.medicalHistory.strokeWardDischargeDate = null
			careActivity.medicalHistory.strokeWardDischargeTime = null
		}
	}

	private def checkStrokeWardDischargeDateBeforeAdmissionDate = { careActivity, data, errors ->
		
		if ( careActivity.beforeStrokeUnitAdmission(careActivity.medicalHistory.strokeWardDischargeDate, careActivity.medicalHistory.strokeWardDischargeTime) )	{
			errors.put("medicalHistory.strokeWardDischargeTime", "medical.history.strokeunit.discharge.date.not.before.admission")
		}
	
	}

	private def checkOnsetDateBeforeAdmissionDate = { careActivity, data, errors ->
		log.debug("checking the onset date is not before admission: onsetDateUnknown "+careActivity.medicalHistory.onsetDateUnknown + ",  inpatent@onset = " + careActivity.medicalHistory.inpatientAtOnset)
		if ( !careActivity.medicalHistory.onsetDateUnknown && careActivity.medicalHistory.inpatientAtOnset ) {
			def time = 0
			if ( careActivity.medicalHistory.onsetTimeUnknown == Boolean.FALSE) {
				time  = careActivity.medicalHistory.onsetTime
			}
			log.debug("... (2) checking the onset date is not before admission : beforeAdmission = "+ careActivity.beforeAdmission(careActivity.medicalHistory.onsetDate, time))
			if ( careActivity.beforeAdmission(careActivity.medicalHistory.onsetDate, time) )	{
				errors.put("medicalHistory.onsetDate", "medical.history.onset.time.not.before.admission")
			}
		}		
	}

	private def checkOnsetDateAfterAdmissionDate = { careActivity, data, errors ->
		log.debug("checking the onset date is not after admission: onsetDateUnknown "+careActivity.medicalHistory.onsetDateUnknown + ",  inpatent@onset = " + careActivity.medicalHistory.inpatientAtOnset)
		if ( !careActivity.medicalHistory.onsetDateUnknown && !careActivity.medicalHistory.inpatientAtOnset ) {
			def time = 0
			if ( careActivity.medicalHistory.onsetTimeUnknown == Boolean.FALSE) {
				time  = careActivity.medicalHistory.onsetTime
			}
			log.debug("... (2) checking the onset date is not after admission : beforeAdmission = "+ careActivity.beforeAdmission(careActivity.medicalHistory.onsetDate, time))
			if ( !careActivity.beforeAdmission(careActivity.medicalHistory.onsetDate, time) )	{
				errors.put("medicalHistory.onsetDate", "medical.history.onset.time.not.after.admission")
			}
		}
	}
	
	private def checkStrokeWardAdmissionDateBeforeArrivalDate = { careActivity, data, errors ->
		
		log.debug("checkStrokeWardAdmissionDateBeforeArrivalDate: date: ${careActivity.medicalHistory.strokeWardAdmissionDate}"
					+" time: ${careActivity.medicalHistory.strokeWardAdmissionTime}")
		
		if(careActivity.medicalHistory.strokeWardAdmissionDate != null 
					&& careActivity.medicalHistory.strokeWardAdmissionTime  == null) {
			errors.put("medicalHistory.strokeWardAdmissionTime", "medical.history.stroke.ward.admission.date.no.time")
		} else if ( 
				careActivity.before1stArrival(careActivity.medicalHistory.strokeWardAdmissionDate,
					careActivity.medicalHistory.strokeWardAdmissionTime)) {
			errors.put("medicalHistory.strokeWardAdmissionDate", "medical.history.stroke.ward.admission.before.1st.arrival")
		}
		
	}
	

	private def updateFirstSeenByList =  {careActivity, data, errors ->
		for( firstSeen in data.firstSeenByList){
			if(firstSeen.notSeen == Boolean.TRUE){
				careActivity.setCareActivityProperty("${firstSeen.category} not seen".toString(), "true")
				careActivity.removeEvaluation(StaffRole.findByDescription(firstSeen.category));
			}else{
				careActivity.clearCareActivityProperty("${firstSeen.category} not seen".toString())
				def evaluation  = careActivity.getEvaluation(StaffRole.findByDescription(firstSeen.category) );
				if ( ! evaluation ) {
					evaluation  = careActivity.addEvaluation(StaffRole.findByDescription(firstSeen.category) );
				}
				if (evaluation){
					if(firstSeen.firstSeenDate || firstSeen.firstSeenTime){
						try{
							evaluation.dateEvaluated = DisplayUtils.getDate(firstSeen.firstSeenDate)
						}catch(IllegalArgumentException iae){
							errors.put("evaluations.dateEvaluated", "evaluation.date.invalid.format")
						}
						try{
							evaluation.timeEvaluated = DisplayUtils.getTime(firstSeen.firstSeenTime)
						}catch(IllegalArgumentException iae){
							errors.put("evaluations.timeEvaluated", "evaluation.time.invalid.format")
						}
						if ( careActivity.medicalHistory.inpatientAtOnset && careActivity.hoursSinceOnset(evaluation.dateEvaluated,evaluation.timeEvaluated)>72) {
							errors.put("evaluations.dateEvaluated", "evaluation.date.cannot.be.72.hrs.after.onset")
							errors.put("evaluations.timeEvaluated", "evaluation.time.cannot.be.72.hrs.after.onset")
						} else if (!careActivity.medicalHistory.inpatientAtOnset && careActivity.hoursSinceAdmission(evaluation.dateEvaluated,evaluation.timeEvaluated)>72) {
							errors.put("evaluations.dateEvaluated", "evaluation.date.cannot.be.72.hrs.after.admission")
							errors.put("evaluations.timeEvaluated", "evaluation.time.cannot.be.72.hrs.after.admission")
						}
					}else{
						careActivity.removeEvaluation(StaffRole.findByDescription(firstSeen.category));
					}
				}/*else{
				   if(firstSeen.firstSeenDate || firstSeen.firstSeenTime){
						evaluation  = careActivity.addEvaluation(StaffRole.findByDescription(firstSeen.category) );
						try{
							evaluation.dateEvaluated = DisplayUtils.getDate(firstSeen.firstSeenDate)
						}catch(IllegalArgumentException iae){
							errors.put("evaluations.dateEvaluated", "evaluation.date.invalid.format")
						}
						try{
							evaluation.timeEvaluated = DisplayUtils.getTime(firstSeen.firstSeenTime)
						}catch(IllegalArgumentException iae){
							errors.put("evaluations.dateEvaluated.timeEvaluated", "evaluation.time.invalid.format")
						}
				   }
				}*/
			}
		}
		
	}
	
	
}
	

