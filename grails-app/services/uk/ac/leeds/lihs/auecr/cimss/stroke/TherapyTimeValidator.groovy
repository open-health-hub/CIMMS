
package uk.ac.leeds.lihs.auecr.cimss.stroke

import uk.ac.leeds.lihs.auecr.cimss.stroke.therapy.session.TimedTherapy
import static java.util.Calendar.*

class TherapyTimeValidator {

	def static validate(timedTherapy, careActivity, errorType, errors ) {
		if ( ! timedTherapy.therapyRequired ) {  
			return true
		} else if ( timedTherapy.daysOfTherapy != null && timedTherapy.minutesOfTherapy != null && ! checkInconsistentTherapyMinutes(timedTherapy.daysOfTherapy, timedTherapy.minutesOfTherapy) ) {
		    println("${errorType} daysOfTherapy[${timedTherapy.daysOfTherapy}] minutesOfTherapy [${timedTherapy.daysOfTherapy}] inconsistent");			
			errors.rejectValue("daysOfTherapy", "${errorType}.daysOfTherapy.minutesOfTherapy.inconsistent")
			errors.rejectValue("minutesOfTherapy", "${errorType}.daysOfTherapy.minutesOfTherapy.inconsistent")
		} else if ( timedTherapy.daysOfTherapy != null && ! checkMaxDays (careActivity, timedTherapy.daysOfTherapy) ){
			println("${errorType} daysOfTherapy has errors");
			
		    errors.rejectValue("daysOfTherapy",
					"${errorType}.daysOfTherapy".toString(), 
					[timedTherapy.daysOfTherapy] as Object[], 
					"Therapy days {0} must not exceed total hospital stay")
		} else if ( timedTherapy.daysOfTherapy != null && timedTherapy.minutesOfTherapy != null && ! checkDailyLimit(timedTherapy.daysOfTherapy, timedTherapy.minutesOfTherapy)) {
			println("${errorType} minutesOfTherapy has errors")
			errors.rejectValue("minutesOfTherapy", 
				"${errorType}.minutesOfTherapy".toString(),
				[timedTherapy.minutesOfTherapy] as Object[], 
				"Minutes of physiotherapy, {0}, exceeds 300 minutes per day for the hospital stay")
		}
		return false
	}
	
	def static checkMaxDays( careActivity, days ) {
		
		def adm = new GregorianCalendar()
		def end = new GregorianCalendar()
		
		if ( careActivity?.medicalHistory?.arrival?.transferredFromAnotherHospital == Boolean.FALSE ){
			if ( careActivity?.medicalHistory?.hospitalAdmissionDate != null ) {				
				adm.setTime(careActivity?.medicalHistory?.hospitalAdmissionDate)
			}			
		} else {
			if ( careActivity?.medicalHistory?.arrival?.thisHospitalArrivalDate != null ) {
				adm.setTime( careActivity?.medicalHistory?.arrival.thisHospitalArrivalDate )
			} 
		}
		if ( careActivity?.medicalHistory?.hospitalDischargeDate !=  null ) {
			end.setTime(careActivity?.medicalHistory?.hospitalDischargeDate)
		}
		
		end.add(Calendar.DAY_OF_MONTH, (0 - days))
		
		def therapyStart = end
		println("therapy_start: " + therapyStart.get(Calendar.DAY_OF_MONTH)+"/"+therapyStart.get(Calendar.MONTH+1)+"/"+therapyStart.get(Calendar.YEAR))
		println("admission: " + adm.get(Calendar.DAY_OF_MONTH)+"/"+adm.get(Calendar.MONTH+1)+"/"+adm.get(Calendar.YEAR))
		
		return !therapyStart.before(adm)
	}
	
	def static checkDailyLimit(days, minutes) {
		if ( days != 0 && minutes != 0) {
			if ( minutes / days > 300) {
				return false
			}
		}
		return true
	}
	
	def static checkInconsistentTherapyMinutes(days, minutes) {
		 if ( days == 0 && minutes !=  0) {
			return false
		}
		return true
	} 
}
