package uk.ac.leeds.lihs.auecr.cimss.stroke.therapy

import grails.converters.JSON;
import org.codehaus.groovy.grails.web.json.JSONObject;
import uk.ac.leeds.lihs.auecr.cimss.access.User
import uk.ac.leeds.lihs.auecr.cimss.access.Role
import uk.ac.leeds.lihs.auecr.cimss.access.UserRole
import uk.ac.leeds.lihs.auecr.cimss.stroke.CareActivity
import uk.ac.leeds.lihs.auecr.cimss.stroke.therapy.session.TherapySession
import uk.ac.leeds.lihs.auecr.cimss.stroke.TherapySummaryHelper
import uk.ac.leeds.lihs.auecr.cimss.stroke.PatientProxy

class TherapySessionController {

	def patients = {
		
		List patients = PatientProxy.findAllByDateOfDeathIsNull();
		render patients as JSON		 
	}
	
	def therapists = {
		Role therapistRole = Role.findByAuthority("ROLE_THERAPIST")
				
		
		List therapistList = UserRole.findAllByRole(therapistRole)
		
		render therapistList as JSON
	}
	
	def choosePatient = {
		
		PatientProxy patient = PatientProxy.get(params.patient)
		if ( !patient ) {
			flash.error_message = "You must select a valid patient"
			List patients = PatientProxy.findAllByDateOfDeathIsNull()
			render(view:"choosePatient", model: [patientList: patients] )
			return
		}
		List careActivities = patient.careActivities?.sort { it.startDate }
		
			
		Date now = new Date()
		if ( !careActivities || careActivities.size() == 0 
				|| (careActivities[0].endDate != null && careActivities[0].endDate < now)) {
			flash.error_message = "Then patient is not listed are receiving right now"
			List patients = PatientProxy.findAllByDateOfDeathIsNull()
			render(view:"choosePatient", model: [patientList: patients] )
			log.debug("4. Choosing patient: careactivities.endDate - "+careActivities[0].endDate)
			return
		}
						
		TherapySession therapySession = session.getAttribute('therapySession')
		therapySession.patient = patient
		therapySession.careActivity = careActivities[0]
				
    	render(view:"collectTherapyTime")
	}
	
    def therapySession = { 
		
		def data = request.JSON
		
		CareActivity careActivity = CareActivity.get(data.careActivityId);
		
		TherapySession therapySession = new TherapySession();
			
		therapySession.durationMinutes = data.duration
		therapySession.startTime = data.startTime;
		therapySession.endTime = data.endTime;
		therapySession.therapyType = data.therapyType;
		therapySession.careActivity = careActivity;
		

		
		TherapySummaryHelper.ensureTherapyManagementExists(careActivity)
		updateTherapy(careActivity, therapySession)
		
		
		if  ( ! careActivity.save() ) {
			flash.error_message = "Technical error - SA-13"
			render(view:"collectTherapyTime")
			return
		}
		
	}
	
	private def updateTherapy = { careActivity, therapySession ->
		switch (therapySession.therapyType) {
			case "Physiotherapy":
				updatePhysiotherapy(careActivity, therapySession)
				break
			case "Psychotherapy":
				updatePsychology(careActivity, therapySession)
				break
			case "Nurse-led":
				updateNurseLedTherapy(careActivity, therapySession)
				break
			case "Occupational":
				updateOccupationalTherapy(careActivity, therapySession)
				break
			case "Speech-and-language":
				updateSpeechAndLanguageTherapy(careActivity, therapySession)
				break
		}
	}
	
	private def updateSpeechAndLanguageTherapy = { careActivity, therapySession ->
		JSONObject data = new JSONObject()
		data.put("requiredTherapies", [salt: true])
		
		Long therapyAlreadyGivenDurationMillis =
			calculateMillis(careActivity.therapyManagement.speechAndLanguageTherapyManagement?.daysOfTherapy ,
				 careActivity.therapyManagement.speechAndLanguageTherapyManagement?.minutesOfTherapy )
			
		def therapyDays = calculateDays(therapySession.duration + therapyAlreadyGivenDurationMillis)
		def therapyMins = calculateMins(therapySession.duration + therapyAlreadyGivenDurationMillis)
		
		data.put('daysOfTherapy', [salt:  therapyDays])
		data.put('minutesOfTherapy', [salt: therapyMins])
		
		TherapySummaryHelper.updateSpeechAndLanguageTherapy(careActivity, data)
	}
	
	private def updateOccupationalTherapy = { careActivity, therapySession ->
		JSONObject data = new JSONObject()
		data.put("requiredTherapies", [occupational: true])
		
		Long therapyAlreadyGivenDurationMillis =
			calculateMillis(careActivity.therapyManagement.occupationalTherapyManagement?.daysOfTherapy ,
				 careActivity.therapyManagement.occupationalTherapyManagement?.minutesOfTherapy )
			
		def therapyDays = calculateDays(therapySession.duration + therapyAlreadyGivenDurationMillis)
		def therapyMins = calculateMins(therapySession.duration + therapyAlreadyGivenDurationMillis)
		
		data.put('daysOfTherapy', [occupational:  therapyDays])
		data.put('minutesOfTherapy', [occupational: therapyMins])
		
		TherapySummaryHelper.updateOccupationalTherapy(careActivity, data)
	}
	
	private def updateNurseLedTherapy = { careActivity, therapySession ->
		JSONObject data = new JSONObject()
		data.put("requiredTherapies", [nurse: true])
		
		Long therapyAlreadyGivenDurationMillis =
			calculateMillis(careActivity.therapyManagement.nurseLedTherapyDaysOfTherapy,
				 careActivity.therapyManagement.nurseLedTherapyMinutesOfTherapy)
			
		def therapyDays = calculateDays(therapySession.duration + therapyAlreadyGivenDurationMillis)
		def therapyMins = calculateMins(therapySession.duration + therapyAlreadyGivenDurationMillis)
		
		data.put('daysOfTherapy', [nurse:  therapyDays])
		data.put('minutesOfTherapy', [nurse: therapyMins])
		
		TherapySummaryHelper.updateNurseLedTherapy(careActivity, data)
	}
	
	private def updatePsychology = { careActivity, therapySession ->
		JSONObject data = new JSONObject()
		data.put("requiredTherapies", [psychology: true])
		
		Long therapyAlreadyGivenDurationMillis =
			calculateMillis(careActivity.therapyManagement.pyschologyDaysOfTherapy,
				 careActivity.therapyManagement.pyschologyMinutesOfTherapy)
			
		def therapyDays = calculateDays(therapySession.duration + therapyAlreadyGivenDurationMillis)
		def therapyMins = calculateMins(therapySession.duration + therapyAlreadyGivenDurationMillis)
		
		data.put('daysOfTherapy', [psychology:  therapyDays])
		data.put('minutesOfTherapy', [psychology: therapyMins])
		
		TherapySummaryHelper.updatePsychology(careActivity, data)
	}
	
	private def updatePhysiotherapy = { careActivity, therapySession ->
		JSONObject data = new JSONObject()
		data.put("requiredTherapies", [physiotherapy: true])
				
		Long therapyAlreadyGivenDurationMillis =
			calculateMillis(careActivity.therapyManagement.physiotherapyManagement?.daysOfTherapy,
				 careActivity.therapyManagement.physiotherapyManagement?.minutesOfTherapy)
			
		def therapyDays = calculateDays(therapySession.duration + therapyAlreadyGivenDurationMillis)
		def therapyMins = calculateMins(therapySession.duration + therapyAlreadyGivenDurationMillis)
		
		data.put('daysOfTherapy', [physiotherapy:  therapyDays])
		data.put('minutesOfTherapy', [physiotherapy: therapyMins])
		
		TherapySummaryHelper.updatePhysiotherapy(careActivity, data)
	}
	
	protected def getIntegerFromString = {value ->
		if((value && value!="null" && value!= JSONObject.NULL) || value == 0 ){
			return new Integer(value)
		}
		return null
	}

	protected def getLongFromString = {value ->
		if((value && value!="null" && value!= JSONObject.NULL) || value == 0 ){
			return new Long(value)
		}
		return null
	}
	
	private def calculateDays = { msec ->
		Calendar cal = GregorianCalendar.getInstance()
		cal.setTimeInMillis(msec)
		return cal.get(Calendar.DAY_OF_YEAR) - 1
	}
	
	private def calculateMins = { msec ->
		Calendar cal = GregorianCalendar.getInstance()
		cal.setTimeInMillis(msec)
		return cal.get(Calendar.HOUR_OF_DAY) + cal.get(Calendar.MINUTE)
	}
	
	private def calculateMillis = { days, mins ->
		def _days = days?days:0
		def _mins = mins?mins:0
		return (_days * 86400000) + (_mins * 60000)
	}
}
