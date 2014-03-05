package uk.ac.leeds.lihs.auecr.cimss.stroke

import java.util.Date;




class PatientController extends StrokeBaseController{

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	/*
	* This  will export patient data for debug purposes
	*
	*/
	def export = {
		log.info "Patient controller : export  ---> ${params}"
		def careActivityInstance = CareActivity.get(params.id)
		
		def patientDetails = "Patient details are - Hospital number: " +  careActivityInstance.patient.hospitalNumber
		patientDetails = patientDetails  + " Admission date: ${DisplayUtils.displayDate(careActivityInstance.startDate)}"
		patientDetails = patientDetails  + " Admission time: ${DisplayUtils.displayTime(careActivityInstance.startTime)}"
		patientDetails = patientDetails  + " Discharge date: ${DisplayUtils.displayDate(careActivityInstance.endDate)}"
		patientDetails = patientDetails  + " Discharge time: ${DisplayUtils.displayTime(careActivityInstance.endTime)}"
		
		
		render patientDetails
	}
	
	
	/*
	 * This  will find the patient via one or more identifier
	 * 	
	 */
	def edit = { 
		log.info "Patient controller : edit  ---> ${params}"
		flash.clear()
		if(!params.userId){
			render "No User Id provided"
		}		
		if(params.hsi){
			processHsi(getParameterMap(params))
		}else{
			render "No HSI provided"
		}
	}
	
	private def hasPatientIdentifier = {parameters ->
		return parameters.hospitalNumber || parameters.nhsNumber || parameters.districtNumber
    }
	
	private def getPatientInstance = { parameters ->
		if(parameters.hospitalNumber){
			return PatientProxy.findByHospitalNumber(parameters.hospitalNumber)
		}else if(parameters.nhsNumber){
			return PatientProxy.findByNhsNumber(parameters.nhsNumber)
		}else if(parameters.districtNumber){
			return PatientProxy.findByDistrictNumber(parameters.districtNumber)
		}
		return null
	}
	
	private def processHsi = { parameters ->
		def newPatientCreated
		def patientInstance
		def careActivityInstance
		if(hasPatientIdentifier(parameters)){
			
			log.debug "Processing HSI params"
			
			careActivityInstance = CareActivity.findByHospitalStayId(parameters.hsi)
			if(careActivityInstance){
				log.debug "HSI found"
				patientInstance = careActivityInstance.patient
				if(isPatientValid(patientInstance, parameters)){
					if(isCareActivityValid(careActivityInstance, parameters)){
						updateCareActivity(careActivityInstance, parameters );
					}else{
						render "There is a discrepancy in the care activity data"
					}
				}else{
					render "There is a discrepancy in the patient data"
				}
				
			}else{
				patientInstance = getPatientInstance(parameters)
				if(patientInstance){
					log.debug "Patient found"
					if(isPatientValid(patientInstance, parameters)){
						log.debug "Creating a new care activity"
						careActivityInstance = new CareActivity(['hospitalStayId':parameters.hsi, 'startDate':parameters.hospitalAdmissionDate]);
						updateCareActivity(careActivityInstance, parameters );
						patientInstance.addToCareActivities(careActivityInstance);
					}else{
						render "There is a discrepancy in the patient data"
					}
				}else{
				log.debug "Patient NOT found - create new"
					patientInstance = new PatientProxy(['hospitalNumber':parameters.hospitalNumber
						,'nhsNumber':parameters.nhsNumber
						,'districtNumber':parameters.districtNumber
						,'surname':parameters.surname
						,'forename':parameters.forename
						,'gender':parameters.gender
						,'ethnicity':parameters.ethnicity
						,'postcode':parameters.postcode
						,'dateOfBirth':parameters.dateOfBirth
//						,'dateOfDeath':parameters.dateOfDeath
//						,'hospitalAdmissionDate':parameters.hospitalAdmissionDate
//						,'hospitalAdmissionTime':parameters.hospitalAdmissionTime
//						,'hospitalDischargeDate':parameters.hospitalDischargeDate
//						,'hospitalDischargeTime':parameters.hospitalDischargeTime
//						,'admissionWard':parameters.admissionWard
//						,'strokeWardAdmissionDate':parameters.strokeWardAdmissionDate
//						,'strokeWardAdmissionTime':parameters.strokeWardAdmissionTime
//						,'strokeWardDischargeDate':parameters.strokeWardDischargeDate
//						,'strokeWardDischargeTime':parameters.strokeWardDischargeTime
//						,'strokeUnitDeath':parameters.strokeUnitDeath
						])
					log.debug("dateOfBirth ----> "+patientInstance.dateOfBirth)
					newPatientCreated = true
//					careActivityInstance = new CareActivity(['hospitalStayId':parameters.hsi, 'startDate':parameters.hospitalAdmissionDate]);
					careActivityInstance = new CareActivity(['hospitalStayId':parameters.hsi]);
					updateCareActivity(careActivityInstance, parameters );
					patientInstance.addToCareActivities(careActivityInstance);
				}
				
			}
			log.debug "careActivy valid? = " + careActivityInstance.validate()
			List errMsgs = []
			def patientInstanceValid = patientInstance.isValid(errMsgs)
			def careActivityValid = careActivityInstance.validate()
			
			if((newPatientCreated && !patientInstanceValid) || !patientInstance.validate() || !careActivityValid ){
				patientInstance.discard();
				careActivityInstance.discard();
				
				log.debug("newPatientCreated = ${newPatientCreated},  patientInstanceValid = ${patientInstanceValid}, careActivityValid = ${careActivityValid}, patientInstance.validate() = "+patientInstance.validate())
				def dataDiscrepancy = getDataDiscrepancy(careActivityInstance)
				render(view: "invalidInput" , model: [patientInstance:patientInstance, careActivityInstance:careActivityInstance, dataDiscrepancy:dataDiscrepancy, errMsgs:errMsgs])
			} else {			
				patientInstance.save(deepValidate :false, flush:true)
						
				return getMapForView(patientInstance, careActivityInstance, new PatientInfo(['registeredGP':parameters.registeredGP,
				'hospitalAdmissionDate':parameters.hospitalAdmissionDate,
				'currentWard':parameters.currentWard,
				'currentWardAdmissionDate':parameters.currentWardAdmissionDate]))
			}
		}else{
			render "No hospital number / nhsNumber / districtNumber provided"
		}
	}
	
	private def getDataDiscrepancy = { careActivityInstance ->
		
		def dataDiscrepancy  = new DataDiscrepancy()
		dataDiscrepancy.after = "Admission date: ${DisplayUtils.displayDate(careActivityInstance.startDate)}"
//		dataDiscrepancy.after = dataDiscrepancy.after  + " Admission time: ${DisplayUtils.displayTime(careActivityInstance?.startTime)}"
//		dataDiscrepancy.after = dataDiscrepancy.after  + " Discharge date: ${DisplayUtils.displayDate(careActivityInstance?.endDate)}"
//		dataDiscrepancy.after = dataDiscrepancy.after  + " Discharge time: ${DisplayUtils.displayTime(careActivityInstance?.endTime)}"
		
		def actualCareActivity = CareActivity.findByHospitalStayId(careActivityInstance?.hospitalStayId);
		dataDiscrepancy.before = "Admission date: ${DisplayUtils.displayDate(actualCareActivity?.startDate)}"
//		dataDiscrepancy.before = dataDiscrepancy.before  + " Admission time: ${DisplayUtils.displayTime(actualCareActivity?.startTime)}"
//		dataDiscrepancy.before = dataDiscrepancy.before  + " Discharge date: ${DisplayUtils.displayDate(actualCareActivity?.endDate)}"
//		dataDiscrepancy.before = dataDiscrepancy.before  + " Discharge time: ${DisplayUtils.displayTime(actualCareActivity?.endTime)}"
		
		return dataDiscrepancy
	}
	
	private def getParameterMap  =  { params->
		def parameters = [:]
		def undefined = []
		for ( param in params ) {
			if (param.value == 'undefined'){
				undefined.add(param.key)
				param.value = ""
			}
		}
		
		parameters.put("hsi",params.hsi)
		parameters.put("hospitalNumber",params.hospitalNumber)
		parameters.put("nhsNumber",params.nhsNumber)
		parameters.put("districtNumber",params.districtNumber)
//		parameters.put("hospitalAdmissionDate",params.hospitalAdmissionDate ? getDate(params.hospitalAdmissionDate ) : "")
//		parameters.put("hospitalAdmissionTime",params.hospitalAdmissionTime ? DisplayUtils.getTime(params.hospitalAdmissionTime) : null)
//		parameters.put("hospitalDischargeDate",params.hospitalDischargeDate ? getDate(params.hospitalDischargeDate ) : "")
//		parameters.put("hospitalDischargeTime",params.hospitalDischargeTime ? DisplayUtils.getTime(params.hospitalDischargeTime) : null)
		parameters.put("registeredGP",params.registeredGP)
		parameters.put("currentWard",params.currentWard)
		parameters.put("currentWardAdmissionDate",params.currentWardAdmissionDate ? Date.parse('dd-MM-yyyy',params.currentWardAdmissionDate ) : "")
		parameters.put("surname",params.surname)
		parameters.put("forename",params.forename)
		parameters.put("gender",params.gender)
		parameters.put("ethnicity",params.ethnicity)
		parameters.put("postcode",params.postcode)
		parameters.put("dateOfBirth",params.dateOfBirth ? getDate(params.dateOfBirth) : null)
//		parameters.put("dateOfDeath",params.dateOfDeath ? getDate(params.dateOfDeath) : null)
//		parameters.put("admissionWard",params.admissionWard)
//		parameters.put("strokeWardAdmissionDate",params.strokeWardAdmissionDate ? getDate(params.strokeWardAdmissionDate ) : "")
//		parameters.put("strokeWardAdmissionTime",params.strokeWardAdmissionTime ? DisplayUtils.getTime(params.strokeWardAdmissionTime) : null)
//		parameters.put("strokeWardDischargeDate",params.strokeWardDischargeDate ? getDate(params.strokeWardDischargeDate ) : "")
//		parameters.put("strokeWardDischargeTime",params.strokeWardDischargeTime ? DisplayUtils.getTime(params.strokeWardDischargeTime) : null)
//		parameters.put("strokeUnitDeath",params.strokeUnitDeath)
		
		return parameters		
	}
	
	private def updateCareActivity = { careActivityInstance, parameters ->
		if(parameters.hospitalAdmissionDate){
			careActivityInstance.startDate = parameters.hospitalAdmissionDate
		}
		if(parameters.hospitalAdmissionTime != null){
			careActivityInstance.startTime = parameters.hospitalAdmissionTime
		}
		if(parameters.hospitalDischargeDate){
			careActivityInstance.endDate = parameters.hospitalDischargeDate
		}
		if(parameters.hospitalDischargeTime != null ){
			careActivityInstance.endTime = parameters.hospitalDischargeTime
		}
		
		if ( ! careActivityInstance.therapyManagement ) {
			careActivityInstance.therapyManagement = new TherapyManagement()
			careActivityInstance.therapyManagement.careActivity = careActivityInstance
		}
		
	}
				
	private def isPatientValid = { patient, parameters ->
		
		
		log.debug("patient.hospitalNumber = '${patient.hospitalNumber}'")
		log.debug("parameters.hospitalNumber = '${parameters.hospitalNumber}'")
			
		if(patient.hospitalNumber && parameters.hospitalNumber){
			if(patient.hospitalNumber == parameters.hospitalNumber ){
				return true
			}
		}

		log.debug("patient.nhsNumber = ${patient.nhsNumber}")
		log.debug("parameters.nhsNumber = ${parameters.nhsNumber}")

		if(patient.nhsNumber && parameters.nhsNumber){
			if(patient.nhsNumber == parameters.nhsNumber ){
				return true
			}
		}
		
		log.debug("patient.districtNumber = ${patient.districtNumber}")
		log.debug("parameters.districtNumber = ${parameters.districtNumber}")
		if(patient.districtNumber && parameters.districtNumber){
			if(patient.districtNumber == parameters.districtNumber ){
				return true
			}
		}
		
		log.debug("isPatientValid == false")
		return false
	}
	
	private def isCareActivityValid = { careActivity, parameters ->
		return true
	}
		    
	private def getMapForView = {patient, careActivity, patientInfo ->
		return [patientInstance: patient
							,careActivityInstance:careActivity
							,registeredGP:patientInfo.registeredGP
							,hospitalAdmissionDate:patientInfo.hospitalAdmissionDate
							,currentWard:patientInfo.currentWard 
							,currentWardAdmissionDate:patientInfo.currentWardAdmissionDate 
							,errorList:[:]]
	}
	
	private  getDate(Object value){
			def parts = value.split("-");
			if ( parts.size() < 3 ) {
				parts = value.split("/");
				if ( parts.size() < 3 ) {
					return null	
				}
			}
			
			def c= new GregorianCalendar()
			c.lenient = false
			c.set(Calendar.YEAR,  new Integer(parts[2]))
			c.set(Calendar.MONTH,  new Integer(parts[1])-1)
			c.set(Calendar.DAY_OF_MONTH,  new Integer(parts[0]))
			c.set(Calendar.HOUR_OF_DAY, 0)
			c.set(Calendar.MINUTE,0)
			c.set(Calendar.SECOND,0)
			c.set(Calendar.MILLISECOND,0)
			return c.time
		
		
	}
		  
}

class DataDiscrepancy{
	String before ="Before"
	String after ="After"
}
class PatientInfo {
	String  registeredGP
	String  hospitalAdmissionDate
	String  currentWard
	String  currentWardAdmissionDate
	String careStart
	String dob
	String onset
	Boolean inpatientAtOnset
	String arrival
}
