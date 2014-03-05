package uk.ac.leeds.lihs.auecr.cimss.stroke.therapy

import uk.ac.leeds.lihs.auecr.cimss.access.User
import uk.ac.leeds.lihs.auecr.cimss.access.Role
import uk.ac.leeds.lihs.auecr.cimss.access.UserRole
import uk.ac.leeds.lihs.auecr.cimss.stroke.CareActivity
import uk.ac.leeds.lihs.auecr.cimss.stroke.PatientProxy
import uk.ac.leeds.lihs.auecr.cimss.stroke.TherapySummaryHelper
import uk.ac.leeds.lihs.auecr.cimss.stroke.therapy.session.TherapySession

import org.codehaus.groovy.grails.web.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.GregorianCalendar
import grails.converters.JSON;


class TherapyController {

	def index = {
		redirect(action:showTherapies)
	}
	
	def patients = {
		
		List patients = PatientProxy.findAllByDateOfDeathIsNull();
		render patients as JSON
	}
	
	def therapists = {
		Role therapistRole = Role.findByAuthority("ROLE_THERAPIST")
				
		
		List therapistList = UserRole.findAllByRole(therapistRole)
		
		render therapistList as JSON
	}
	
  	
	def therapysessions = {
		
		def data = request.JSON
		
		CareActivity careActivity = CareActivity.get(data.careActivityId);
		
		TherapySession therapySession = new TherapySession();
			
		therapySession.durationMinutes = data.duration
		therapySession.duration = therapySession.durationMinutes * 60000
		therapySession.startTime = dateConvert(data.startTime)
		therapySession.endTime = dateConvert(data.endTime)
		therapySession.therapyType = data.therapyType;
		therapySession.careActivity = careActivity;
		

		
		TherapySummaryHelper.ensureTherapyManagementExists(careActivity)
		updateTherapy(careActivity, therapySession)
		
		
		if  ( ! careActivity.save() ) {
			response.status = 400			
			render([error: 'Technical error - SA-13'] as JSON)			
		} 
				
		render {} as JSON
	}
	
	private Date dateConvert ( String dateString ) {
		
		DateFormat fmt = new  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		return fmt.parse( dateString.replaceAll("Z\$", "+0000") );
	}
			
	def showTherapies = {
		if ( ! session ) {
			session = request.getSession(true)
		}
		log.debug("Sessionid = "+session.id)
		log.debug("therapySession[1] ==  null " + (null == session.getAttribute('therapySession')))
		TherapySession therapySession = session.getAttribute('therapySession')
		if ( !therapySession ) {
			therapySession = new TherapySession()
			session.setAttribute('therapySession' , therapySession)
			
			log.debug("therapySession[2] ==  null " + (null == session.getAttribute('therapySession')))
		}
		render(view: "showTherapies")
	}
	
	def chooseTherapy  = {
		log.debug("Sessionid[2] = "+session.id)
		if ( null == session.getAttribute('therapySession') ) {
			log.debug("Choosing therapy: session expired")
			flash.error_message = "Login session expired"
			showTherapies()
			return
		}
		
		def therapy = params.therapy

		if ( !therapy ) {
			log.debug("Choosing therapy: chosen therapy null or invalid")
			flash.error_message = "No such therapy"
			redirect(action:index)
			return
		}
		
		TherapySession therapySession = session.getAttribute('therapySession')
		therapySession.therapyType = therapy
		
		List therapists = getTherapists()
		render(view:"chooseTherapists", model: [therapistList: therapists] )
	}

	private def getTherapists = {
		Role therapistRole = Role.findByAuthority("ROLE_THERAPIST")
		return therapistRole ? UserRole.findAllByRole(therapistRole) : []
	}

	def chooseTherapists  = {
		if ( ! session.getAttribute('therapySession') ) {
			flash.error_message = "Session expired"
			redirect(action:index)
			return
		}
		
		List allTherapists = getTherapists()
		
		if ( !params.therapists ) {
			flash.error_message = "You must select a therapist"									
			render(view:"chooseTherapists", model: [therapistList: allTherapists] )
			return
		}
		
		List therapistIds  = (params.therapists)?[params.therapists].flatten():null
		List therapistList = []
		
		therapistIds.each {
			User therapistUser = User.get(it)
			Role therapistRole = Role.findByAuthority("ROLE_THERAPIST") 
			UserRole therapist = therapistUser ? 
				UserRole.findByUserAndRole(therapistUser,therapistRole) : null
			if ( !therapist ) {
				flash.error_message = "You must select a therapist"
				render(view:"chooseTherapists", model: [therapistList: allTherapists] )
			}  else {
				therapistList.push(therapist)
			}
		}
		
		TherapySession therapySession = session.getAttribute('therapySession')
		therapySession.therapistList.clear()
		therapySession.therapistList.addAll(therapistList)
		
		Date now = new Date();
				
		List patients = PatientProxy.findAllByDateOfDeathIsNull() 						
		render(view:"choosePatient", model: [patientList: patients] )
	}
	
	def choosePatient = {
		
		if ( !session.getAttribute('therapySession') ) {
			flash.error_message = "Session expired"
			redirect(action:index)
			return
		}
		
		if ( !params.patient ) {
			flash.error_message = "You must select a patient"
			List patients = PatientProxy.findAllByDateOfDeathIsNull()
			render(view:"choosePatient", model: [patientList: patients] )
			return
		}
		
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
	
	def therapyFinished = {
		
		if ( !session.getAttribute('therapySession') ) {
			flash.error_message = "Session expired"
			redirect(action:index)
			return
		}
		
		Long reportedTherapyMillis = getLongFromString(params.therapyDuration)
		if ( !params.therapyDuration || !reportedTherapyMillis ) {
			flash.error_message = "You must enter duration fo therapy"
			render(view:"collectTherapyTime")
			return
		}
				
		TherapySession therapySession = session.getAttribute('therapySession')
		CareActivity careActivity = therapySession.careActivity
		if ( !careActivity ) {
			flash.error_message = "Technical error - SS-10" 
			render(view:"collectTherapyTime")
			return
		}
		therapySession.duration = reportedTherapyMillis
		therapySession.durationMinutes = reportedTherapyMillis / 60000
		
		careActivity = CareActivity.get(careActivity.id)
		therapySession.careActivity = careActivity
		
		TherapySummaryHelper.ensureTherapyManagementExists(careActivity)
		updateTherapy(careActivity, therapySession)
		
		
		if  ( ! careActivity.save() ) {
			flash.error_message = "~Technical error - SA-13"
			render(view:"collectTherapyTime")
			return
		}
		
		
		render(view:"showTherapySessionSummary", model: [therapySession: therapySession])

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
		
		Long therapyAlreadyGivenDuration = careActivity.therapyManagement.speechAndLanguageTherapyManagement?.minutesOfTherapy
			
		def therapyDays = add(careActivity.therapyManagement.speechAndLanguageTherapyManagement?.daysOfTherapy , 1)
		def therapyMins = add(therapySession.durationMinutes , therapyAlreadyGivenDuration)
		
		data.put('daysOfTherapy', [salt:  therapyDays])
		data.put('minutesOfTherapy', [salt: therapyMins])
		
		TherapySummaryHelper.updateSpeechAndLanguageTherapy(careActivity, data)
	}
	
	private def updateOccupationalTherapy = { careActivity, therapySession ->
		JSONObject data = new JSONObject()
		data.put("requiredTherapies", [occupational: true])
		
		Long therapyAlreadyGivenDuration =
				 careActivity.therapyManagement.occupationalTherapyManagement?.minutesOfTherapy 
			
		def therapyDays = add(careActivity.therapyManagement.occupationalTherapyManagement?.daysOfTherapy , 1)
		def therapyMins = add(therapySession.durationMinutes , therapyAlreadyGivenDuration)
		
		data.put('daysOfTherapy', [occupational:  therapyDays])
		data.put('minutesOfTherapy', [occupational: therapyMins])

		TherapySummaryHelper.updateOccupationalTherapy(careActivity, data)
	}
	
	private def updateNurseLedTherapy = { careActivity, therapySession ->
		JSONObject data = new JSONObject()
		data.put("requiredTherapies", [nurse: true])
		
		Long therapyAlreadyGivenDuration = careActivity.therapyManagement.nurseLedTherapyMinutesOfTherapy
			
		def therapyDays = add(careActivity.therapyManagement.nurseLedTherapyDaysOfTherapy , 1)
		def therapyMins = add(therapySession.durationMinutes , therapyAlreadyGivenDuration)
		
		data.put('daysOfTherapy', [nurse:  therapyDays])
		data.put('minutesOfTherapy', [nurse: therapyMins])
		
		TherapySummaryHelper.updateNurseLedTherapy(careActivity, data)
	}
	
	private def updatePsychology = { careActivity, therapySession ->
		JSONObject data = new JSONObject()
		data.put("requiredTherapies", [psychology: true])
		
		Long therapyAlreadyGivenDuration = careActivity.therapyManagement.pyschologyMinutesOfTherapy
			
		def therapyDays = add(careActivity.therapyManagement.pyschologyDaysOfTherapy , 1)
		def therapyMins = add(therapySession.durationMinutes , therapyAlreadyGivenDuration)
		
		data.put('daysOfTherapy', [psychology:  therapyDays])
		data.put('minutesOfTherapy', [psychology: therapyMins])
		
		TherapySummaryHelper.updatePsychology(careActivity, data)
    }
	
	private def updatePhysiotherapy = { careActivity, therapySession ->
		JSONObject data = new JSONObject()
		data.put("requiredTherapies", [physiotherapy: true])
				
		Long therapyAlreadyGivenDuration = careActivity.therapyManagement.physiotherapyManagement?.minutesOfTherapy
			
		def therapyDays = add(careActivity.therapyManagement.physiotherapyManagement?.daysOfTherapy , 1)
		def therapyMins = add(therapySession.durationMinutes , therapyAlreadyGivenDuration)
		
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
	
//	private def calculateDays = { msec ->
//		Calendar cal = GregorianCalendar.getInstance()
//		cal.setTimeInMillis(msec)
//		return cal.get(Calendar.DAY_OF_YEAR) - 1
//	}
//	
//	private def calculateMins = { msec ->
//		Calendar cal = GregorianCalendar.getInstance()
//		cal.setTimeInMillis(msec)
//		return cal.get(Calendar.HOUR_OF_DAY) + cal.get(Calendar.MINUTE) 
//	}
//	
//	private def calculateMsec =  {mins ->
//		def _mins = mins?mins:0
//		return (_mins * 60000)
//	}
	
//	private def calculateMillis = { days, mins ->
//		def _days = days?days:0
//		def _mins = mins?mins:0
//		return (_days * 86400000) + (_mins * 60000)
//	}
	
	private int add ( Number operand1, Number operand2 ) {
		
		return (
			((operand1 != null) ? operand1.intValue() : 0)
			  + ((operand2 != null) ? operand2.intValue() : 0 ) )
	}
}
