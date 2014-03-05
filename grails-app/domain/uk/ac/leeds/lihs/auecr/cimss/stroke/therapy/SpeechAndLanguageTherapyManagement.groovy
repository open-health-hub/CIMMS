package uk.ac.leeds.lihs.auecr.cimss.stroke.therapy

import java.util.Date;

import uk.ac.leeds.lihs.auecr.cimss.stroke.DateTimeHelper;
import uk.ac.leeds.lihs.auecr.cimss.stroke.TherapyDetail;
import uk.ac.leeds.lihs.auecr.cimss.stroke.TherapyManagement
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.CommunicationNoAssessmentReasonType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.PhysiotherapyNoAssessmentReasonType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.SwallowingNoAssessmentReasonType;

class SpeechAndLanguageTherapyManagement {
	
	Boolean communicationAssessmentPerformed
	Boolean communicationAssessmentPerformedIn72Hrs
	Date communicationAssessmentDate
	Integer communicationAssessmentTime
	CommunicationNoAssessmentReasonType noCommunicationAssessmentReasonType
	CommunicationNoAssessmentReasonType no72HrCommunicationAssessmentReasonType
	
	Boolean swallowingAssessmentPerformed
	Boolean swallowingAssessmentPerformedIn72Hrs
	Date swallowingAssessmentDate
	Integer swallowingAssessmentTime
	SwallowingNoAssessmentReasonType noSwallowingAssessmentReasonType
	SwallowingNoAssessmentReasonType no72HrSwallowingAssessmentReasonType
	
	Boolean therapyRequired
	Integer daysOfTherapy
	Integer minutesOfTherapy
	
	public TherapyDetail formalCommunicationTherapyDetail (){
		return new TherapyDetail ([wasPerformed:communicationAssessmentPerformed
							, wasPerformedIn72Hrs:communicationAssessmentPerformedIn72Hrs
							, performedDate:communicationAssessmentDate 
							, performedTime:communicationAssessmentTime
							, reasonNotPerformed:noCommunicationAssessmentReasonType?.description
							, reasonNotPerformedIn72Hrs:no72HrCommunicationAssessmentReasonType?.description
							, type:"Formal Communication"
							, hoursSinceAdmission:communicationAssessmentHoursSinceAdmission()])
	}
	
	public TherapyDetail formalSwallowingTherapyDetail (){
		return new TherapyDetail ([wasPerformed:swallowingAssessmentPerformed
							, wasPerformedIn72Hrs:swallowingAssessmentPerformedIn72Hrs
							, performedDate:swallowingAssessmentDate
							, performedTime:swallowingAssessmentTime
							, reasonNotPerformed:noSwallowingAssessmentReasonType?.description
							, reasonNotPerformedIn72Hrs:no72HrSwallowingAssessmentReasonType?.description
							, type:"Formal Swallowing"
							, hoursSinceAdmission:swallowingAssessmentHoursSinceAdmission()])
	}
	
	private Float communicationAssessmentHoursSinceAdmission (){
		return therapyManagement.careActivity.hoursSinceAdmission(communicationAssessmentDate,communicationAssessmentTime );
	}
	
	private Float swallowingAssessmentHoursSinceAdmission (){
		return therapyManagement.careActivity.hoursSinceAdmission(swallowingAssessmentDate,swallowingAssessmentTime );
	}
	
	
	static belongsTo = [therapyManagement:TherapyManagement]
	static auditable = [ignore:[]]
    static constraints = {
		therapyRequired(nullable:true)
		daysOfTherapy(nullable:true)
		minutesOfTherapy(nullable:true)
		communicationAssessmentPerformed(nullable:true)
		communicationAssessmentPerformedIn72Hrs(nullable:true)
		communicationAssessmentDate(nullable:true
			,validator:{communicationAssessmentDate, speechAndLanguageTherapyManagement ->
				if(!speechAndLanguageTherapyManagement?.communicationAssessmentDate && speechAndLanguageTherapyManagement?.communicationAssessmentPerformed == Boolean.TRUE  ){
					return "speech.and.language.therapy.management.yes.communication.date.missing"
				}
				if( speechAndLanguageTherapyManagement?.communicationAssessmentPerformed == Boolean.TRUE){
					if(communicationAssessmentDate > new Date()){
						return "speech.and.language.communication.assessment.date.not.in.future"
					}
					if(speechAndLanguageTherapyManagement.therapyManagement.careActivity.afterDischarge(communicationAssessmentDate, speechAndLanguageTherapyManagement.communicationAssessmentTime)){
						return "speech.and.language.communication.assessment.date.not.after.discharge"
					}
					if(speechAndLanguageTherapyManagement.therapyManagement.careActivity.beforeAdmission(communicationAssessmentDate, speechAndLanguageTherapyManagement.communicationAssessmentTime)){
						return "speech.and.language.communication.assessment.date.not.before.admission"
					}
				}
				
				
		})
		communicationAssessmentTime(nullable:true
			,validator:{communicationAssessmentTime, speechAndLanguageTherapyManagement ->
				if(speechAndLanguageTherapyManagement?.communicationAssessmentTime== null && speechAndLanguageTherapyManagement?.communicationAssessmentPerformed == Boolean.TRUE  ){
					return "speech.and.language.therapy.management.yes.communication.time.missing"
				}
				if( speechAndLanguageTherapyManagement?.communicationAssessmentPerformed == Boolean.TRUE ){
					if( speechAndLanguageTherapyManagement.communicationAssessmentDate == DateTimeHelper.getCurrentDateAtMidnight()){
						if(communicationAssessmentTime > DateTimeHelper.getCurrentTimeAsMinutes() ){
							return "speech.and.language.communication.assessment.time.not.in.future"
						}
					}
				}
		})
		no72HrCommunicationAssessmentReasonType(nullable:true
			,validator:{no72HrCommunicationAssessmentReasonType, therapyManagement ->
				if(therapyManagement?.communicationAssessmentPerformedIn72Hrs == Boolean.FALSE
					&&  !no72HrCommunicationAssessmentReasonType ){
					return "speech.and.language.therapy.management.no.communication72.assesment.reason.missing"
				}
		})
		noCommunicationAssessmentReasonType(nullable:true
			,validator:{noCommunicationAssessmentReasonType, therapyManagement ->
				if(therapyManagement?.communicationAssessmentPerformed == Boolean.FALSE 
					&& !noCommunicationAssessmentReasonType ){
					return "speech.and.language.therapy.management.no.communication.assesment.reason.missing"
				}
		})
		swallowingAssessmentPerformed(nullable:true)
		swallowingAssessmentPerformedIn72Hrs(nullable:true)
		swallowingAssessmentDate(nullable:true
			,validator:{swallowingAssessmentDate, speechAndLanguageTherapyManagement ->
				if(!speechAndLanguageTherapyManagement?.swallowingAssessmentDate && speechAndLanguageTherapyManagement?.swallowingAssessmentPerformed == Boolean.TRUE  ){
					return "speech.and.language.therapy.management.yes.swallowing.date.missing"
				}
				if( speechAndLanguageTherapyManagement?.swallowingAssessmentPerformed == Boolean.TRUE){
					if(swallowingAssessmentDate > new Date()){
						return "speech.and.language.swallowing.assessment.date.not.in.future"
					}
					if(speechAndLanguageTherapyManagement.therapyManagement.careActivity.afterDischarge(swallowingAssessmentDate, speechAndLanguageTherapyManagement.swallowingAssessmentTime)){
						return "speech.and.language.swallowing.assessment.date.not.after.discharge"
					}
					if(speechAndLanguageTherapyManagement.therapyManagement.careActivity.beforeAdmission(swallowingAssessmentDate, speechAndLanguageTherapyManagement.swallowingAssessmentTime)){
						return "speech.and.language.swallowing.assessment.date.not.before.admission"
					}
				}
				
		})
		swallowingAssessmentTime(nullable:true
			,validator:{swallowingAssessmentTime, speechAndLanguageTherapyManagement ->
				if(speechAndLanguageTherapyManagement?.swallowingAssessmentTime == null && speechAndLanguageTherapyManagement?.swallowingAssessmentPerformed == Boolean.TRUE  ){
					return "speech.and.language.therapy.management.yes.swallowing.time.missing"
				}
				if( speechAndLanguageTherapyManagement?.swallowingAssessmentPerformed == Boolean.TRUE ){
					if( speechAndLanguageTherapyManagement.swallowingAssessmentDate == DateTimeHelper.getCurrentDateAtMidnight()){
						if(swallowingAssessmentTime > DateTimeHelper.getCurrentTimeAsMinutes() ){
							return "speech.and.language.swallowing.assessment.time.not.in.future"
						}
					}
				}
		})
		no72HrSwallowingAssessmentReasonType(nullable:true
			,validator:{no72HrSwallowingAssessmentReasonType, therapyManagement ->
				if(therapyManagement?.swallowingAssessmentPerformedIn72Hrs == Boolean.FALSE
					&& !no72HrSwallowingAssessmentReasonType ){
					return "speech.and.language.therapy.management.no.swallowing72.assesment.reason.missing"
				}
		})
		noSwallowingAssessmentReasonType(nullable:true
			,validator:{noSwallowingAssessmentReasonType, therapyManagement ->
				if(therapyManagement?.swallowingAssessmentPerformed == Boolean.FALSE 
					&& !noSwallowingAssessmentReasonType ){
					if ( therapyManagement?.no72HrSwallowingAssessmentReasonType?.description != "passedswallowscreen" ) {
						return "speech.and.language.therapy.management.no.swallowing.assesment.reason.missing"
					}
				}
		})
    }
}
