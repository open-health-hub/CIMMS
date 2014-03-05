package uk.ac.leeds.lihs.auecr.cimss.stroke


import java.util.Date;

import uk.ac.leeds.lihs.auecr.cimss.stroke.medical.history.RiskFactor
import uk.ac.leeds.lihs.auecr.cimss.stroke.medical.history.Comorbidity
import uk.ac.leeds.lihs.auecr.cimss.stroke.medical.history.Medication

class MedicalHistory {
		
	Date onsetDate
	Boolean onsetDateEstimated = Boolean.FALSE
	Boolean onsetDateUnknown = Boolean.FALSE
	Integer onsetTime
	Boolean onsetTimeEstimated = Boolean.FALSE
	Boolean onsetTimeUnknown = Boolean.FALSE
	Boolean duringSleep
	Arrival arrival = new Arrival()
	
	String previousStroke;
	String previousTia;
	Boolean assessedInVascularClinic;
	Boolean inpatientAtOnset

	Date hospitalAdmissionDate;
	Integer hospitalAdmissionTime;
	Date hospitalDischargeDate;
	Integer hospitalDischargeTime;
	String admissionWard;
	Date strokeWardAdmissionDate;
	Integer strokeWardAdmissionTime;
	Date strokeWardDischargeDate;
	Integer strokeWardDischargeTime;
	Boolean didNotStayInStrokeWard;
	Boolean strokeUnitDeath;
	
	static auditable = [ignore:[]]
	
	static hasMany = [risks:RiskFactor
					, comorbidities:Comorbidity
					, medications:Medication]
	
	static belongsTo = [careActivity:CareActivity]

    static constraints = {
		inpatientAtOnset(nullable:true)
		arrival(nullable:true)
		assessedInVascularClinic(nullable:true)
		duringSleep(nullable:true)
		previousStroke(nullable:true)
		previousTia(nullable:true)
		onsetDateEstimated(nullable:true
			,validator:{onsetDateEstimated, medicalHistory ->
				if(onsetDateEstimated==Boolean.TRUE  && medicalHistory.onsetDate == null){
					return "medical.history.onset.can.not.estimate.if.no.date.provided"
				}
				
			})

		onsetDate(nullable:true
			,validator:{onsetDate, medicalHistory ->
				if(onsetDate && onsetDate.after(new Date())){
					return "medical.history.onset.date.not.in.future"
				}
				if(medicalHistory.careActivity.afterDischarge(onsetDate, medicalHistory.onsetTime)){
					return "medical.history.onset.date.not.after.discharge"
				}
				/*if(!onsetDate && !medicalHistory?.onsetDateUnknown==Boolean.TRUE    && medicalHistory.onsetTime){
					return "medical.history.onset.date.if.time.provided"
				}*/
				
			})
		onsetTimeEstimated(nullable:true
			,validator:{onsetTimeEstimated, medicalHistory ->
				if(onsetTimeEstimated==Boolean.TRUE  && medicalHistory.onsetTime == null){
					return "medical.history.onset.can.not.estimate.if.no.time.provided"
				}
				
			})
		onsetTime(nullable:true
			,validator:{onsetTime, medicalHistory ->
				if( !medicalHistory.onsetDate){
					if(onsetTime !=null){
						return "medical.history.onset.time.without.date"
					}
				}else{
					if( medicalHistory.onsetDate == DateTimeHelper.getCurrentDateAtMidnight()){
						if(onsetTime > DateTimeHelper.getCurrentTimeAsMinutes() ){
							return "medical.history.onset.time.not.in.future"
						}
					}
				}
				if(onsetTime == null && !medicalHistory?.onsetTimeUnknown==Boolean.TRUE    && medicalHistory.onsetDate){
					return "medical.history.onset.time.if.date.provided"
				}
				
				
			})
		hospitalAdmissionDate(nullable:true)
		hospitalAdmissionTime(nullable:true)
		hospitalDischargeDate(nullable:true)
		hospitalDischargeTime(nullable:true, validator: {hospitalDischargeTime, medicalHistory ->
			if(hospitalDischargeTime == null &&  medicalHistory.hospitalDischargeDate){
				return "medical.history.hospdisch.time.if.date.provided"
			}
		})
		admissionWard(nullable:true)
		strokeWardAdmissionDate(nullable:true)
		strokeWardAdmissionTime(nullable:true)
		strokeWardDischargeDate(nullable:true)
		strokeWardDischargeTime(nullable:true, validator: {strokeWardDischargeTime, medicalHistory ->
			if(strokeWardDischargeTime == null &&  medicalHistory.strokeWardDischargeDate){
				return "medical.history.strokewarddisch.time.if.date.provided"
			}
		})
		strokeUnitDeath(nullable:true)
		didNotStayInStrokeWard(nullable:true)
    }
	
	def addRisk = {type,value ->
		def theRisk = null;
		risks.each { risk ->
			if(risk.type == type){
				risk.value = value
				theRisk = risk
			}	
		}
		if(!theRisk){
			addToRisks(new RiskFactor([type:type, value:value])) 
		}
	}
	
	def getRisk = { type ->
		def theRisk = null;
		risks.each { risk ->
			if(risk.type == type){
				theRisk = risk
			}
		}
		return theRisk
		
	}
	
	def  removeRisk = {type ->
		def theRisk = null;
		risks.each { risk ->
			if(risk.type == type){
				theRisk = risk
			}	
		}
		if(theRisk){
			removeFromRisks(theRisk)
			theRisk.delete() 
		}
	}

	def addMedication = {type ->
		def theMedication = null;
		medications.each { medication ->
			if(medication.type == type){
				theMedication = medication
			}
		}
		if(!theMedication){
			addToMedications(new Medication([type:type]))
		}
	}
	

	
	
	def addMedicationAsString  = {type, value ->
		def theMedication = null;
		medications.each { medication ->
			if(medication.type == type){
				theMedication = medication
			}
		}
		if(!theMedication){
			addToMedications(new Medication([type:type,value:value]))
		}else{
			theMedication.value = value
		}
	}
	
	
	
	
	
	def getMedication = { type ->
		def theMedication = null;
		medications.each { medication ->
			if(medication.type == type){
				theMedication = medication
			}
		}
		return theMedication
		
	}
	
	def  removeMedication = {type ->
		def theMedication = null;
		medications.each { medication ->
			if(medication.type == type){
				theMedication = medication
			}
		}
		if(theMedication){
			removeFromMedications(theMedication)
			theMedication.delete()
		}
	}

	def addComorbidity = {type ->
		def theComorbidity = null;
		comorbidities.each { comorbidity ->
			if(comorbidity.type == type){
				theComorbidity = comorbidity
			}
		}
		if(!theComorbidity){
			addToComorbidities(new Comorbidity([type:type]))
		}
	}
	
	def addComorbidityAsString = {type, value ->
		def theComorbidity = null;
		comorbidities.each { comorbidity ->
			if(comorbidity.type == type){
				theComorbidity = comorbidity
			}
		}
		if(!theComorbidity){
			addToComorbidities(new Comorbidity([type:type,value:value]))
		}else{
			theComorbidity.value = value
		}
	}
	
	def getComorbidity = { type ->
		def theComorbidity = null;
		comorbidities.each { comorbidity ->
			if(comorbidity.type == type){
				theComorbidity = comorbidity
			}
		}
		return theComorbidity		
	}
	
	def  removeComorbidity = {type ->
		def theComorbidity = null;
		comorbidities.each { comorbidity ->
			if(comorbidity.type == type){
				theComorbidity = comorbidity
			}
		}
		if(theComorbidity){
			removeFromComorbidities(theComorbidity)
			theComorbidity.delete()
		}
	}

	boolean isValid(mesgs) {
		checkValid("hospitalAdmissionDate",hospitalAdmissionDate,mesgs)
		checkValid("hospitalAdmissionTime",hospitalAdmissionTime,mesgs)
		checkValid("hospitalDischargeDate",hospitalDischargeDate,mesgs)
		checkValid("hospitalDischargeTime",hospitalDischargeTime,mesgs)
		checkValid("admissionWard",admissionWard,mesgs)
		checkValid("strokeWardAdmissionDate",strokeWardAdmissionDate,mesgs)
		checkValid("strokeWardAdmissionTime",strokeWardAdmissionTime,mesgs)
		checkValid("strokeWardDischargeDate",strokeWardDischargeDate,mesgs)
		checkValid("strokeWardDischargeTime",strokeWardDischargeTime,mesgs)
		checkValid("strokeUnitDeath",strokeUnitDeath,mesgs)
	}
	
	boolean checkValid(paramName, param, mesgs) {
		if ( param == null || param == "" ) {
			mesgs.push("valid '${paramName}' parameter must be supplied")
			return false
		}
		return true
	}
}
