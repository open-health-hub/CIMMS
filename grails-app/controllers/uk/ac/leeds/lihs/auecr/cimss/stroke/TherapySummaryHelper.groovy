package uk.ac.leeds.lihs.auecr.cimss.stroke

import uk.ac.leeds.lihs.auecr.cimss.stroke.therapy.PhysiotherapyManagement;
import uk.ac.leeds.lihs.auecr.cimss.stroke.therapy.SpeechAndLanguageTherapyManagement;
import uk.ac.leeds.lihs.auecr.cimss.stroke.therapy.OccupationalTherapyManagement;
import uk.ac.leeds.lihs.auecr.cimss.stroke.TherapyManagement
import uk.ac.leeds.lihs.auecr.cimss.stroke.CareActivity
import org.codehaus.groovy.grails.web.json.JSONObject

class TherapySummaryHelper {

	public static void ensureTherapyManagementExists(CareActivity careActivity) {
		if(!careActivity.therapyManagement){
			careActivity.therapyManagement = new TherapyManagement()
			careActivity.therapyManagement.careActivity = careActivity
		}
	}
	
	
	public static void ensureOccupationalTherapyManagementExists(TherapyManagement therapyManagement) {
		if(!therapyManagement.occupationalTherapyManagement){
			therapyManagement.occupationalTherapyManagement = new OccupationalTherapyManagement()
			therapyManagement.occupationalTherapyManagement.therapyManagement = therapyManagement
		}
	}
	
	public static void ensurePhysiotherapyManagementExists(TherapyManagement therapyManagement) {
		if(!therapyManagement.physiotherapyManagement){
			therapyManagement.physiotherapyManagement = new PhysiotherapyManagement()
			therapyManagement.physiotherapyManagement.therapyManagement = therapyManagement
		}
	}
	
	public static void ensureSpeechAndLanguageTherapyManagementExists(TherapyManagement therapyManagement) {
		if(!therapyManagement.speechAndLanguageTherapyManagement){
			therapyManagement.speechAndLanguageTherapyManagement = new SpeechAndLanguageTherapyManagement()
			therapyManagement.speechAndLanguageTherapyManagement.therapyManagement = therapyManagement
		}
	}
	
	
	public static def updateSpeechAndLanguageTherapy = {careActivity, data ->
		ensureSpeechAndLanguageTherapyManagementExists(careActivity.therapyManagement);
		careActivity.therapyManagement.speechAndLanguageTherapyManagement.therapyRequired = TypeConversionHelper.getBooleanFromString(data.requiredTherapies.salt);
		careActivity.therapyManagement.speechAndLanguageTherapyManagement.daysOfTherapy = TypeConversionHelper.getIntegerFromString(data.daysOfTherapy.salt);
		careActivity.therapyManagement.speechAndLanguageTherapyManagement.minutesOfTherapy = TypeConversionHelper.getIntegerFromString(data.minutesOfTherapy.salt);
	}
	
	
	public static def updateOccupationalTherapy = {careActivity, data ->
		ensureOccupationalTherapyManagementExists(careActivity.therapyManagement);
		careActivity.therapyManagement.occupationalTherapyManagement.therapyRequired = TypeConversionHelper.getBooleanFromString(data.requiredTherapies.occupational);
		careActivity.therapyManagement.occupationalTherapyManagement.daysOfTherapy = TypeConversionHelper.getIntegerFromString(data.daysOfTherapy.occupational);
		careActivity.therapyManagement.occupationalTherapyManagement.minutesOfTherapy = TypeConversionHelper.getIntegerFromString(data.minutesOfTherapy.occupational);	
	}
	
	public static def updateNurseLedTherapy = {careActivity, data ->
		careActivity.therapyManagement.nurseLedTherapyRequired = TypeConversionHelper.getBooleanFromString(data.requiredTherapies.nurse);
		careActivity.therapyManagement.nurseLedTherapyDaysOfTherapy = TypeConversionHelper.getIntegerFromString(data.daysOfTherapy.nurse);
		careActivity.therapyManagement.nurseLedTherapyMinutesOfTherapy = TypeConversionHelper.getIntegerFromString(data.minutesOfTherapy.nurse);
	}
	
	public static def updatePsychology = {careActivity, data ->
		careActivity.therapyManagement.pyschologyTherapyRequired = TypeConversionHelper.getBooleanFromString(data.requiredTherapies.psychology);
		careActivity.therapyManagement.pyschologyDaysOfTherapy = TypeConversionHelper.getIntegerFromString(data.daysOfTherapy.psychology);
		careActivity.therapyManagement.pyschologyMinutesOfTherapy = TypeConversionHelper.getIntegerFromString(data.minutesOfTherapy.psychology);
	}
	
	
	public static void updatePhysiotherapy (CareActivity careActivity, JSONObject data) {
		TherapySummaryHelper.ensurePhysiotherapyManagementExists(careActivity.therapyManagement);
		careActivity.therapyManagement.physiotherapyManagement.therapyRequired = TypeConversionHelper.getBooleanFromString(data.requiredTherapies.physiotherapy);
		careActivity.therapyManagement.physiotherapyManagement.daysOfTherapy = TypeConversionHelper.getIntegerFromString(data.daysOfTherapy.physiotherapy);
		careActivity.therapyManagement.physiotherapyManagement.minutesOfTherapy = TypeConversionHelper.getIntegerFromString(data.minutesOfTherapy.physiotherapy);
	}
	
}
