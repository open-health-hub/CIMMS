package uk.ac.leeds.lihs.auecr.cimss.stroke

import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.CognitiveStatusNoAssessmentType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.RehabGoalsNotSetReasonType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.therapy.AssessmentManagement

import uk.ac.leeds.lihs.auecr.cimss.stroke.therapy.OccupationalTherapyManagement;
import uk.ac.leeds.lihs.auecr.cimss.stroke.therapy.SpeechAndLanguageTherapyManagement 
import uk.ac.leeds.lihs.auecr.cimss.stroke.therapy.PhysiotherapyManagement

class TherapyManagement {
	
	OccupationalTherapyManagement occupationalTherapyManagement
	SpeechAndLanguageTherapyManagement speechAndLanguageTherapyManagement  
	PhysiotherapyManagement physiotherapyManagement
	AssessmentManagement assessmentManagement
	
	
	Boolean cognitiveStatusAssessed;
	Date cognitiveStatusAssessmentDate;
	Integer cognitiveStatusAssessmentTime;
	CognitiveStatusNoAssessmentType cognitiveStatusNoAssessmentType   
	
	Boolean rehabGoalsSet
	Date rehabGoalsSetDate
	Integer rehabGoalsSetTime
	RehabGoalsNotSetReasonType rehabGoalsNotSetReasonType
	
	Boolean pyschologyTherapyRequired
	Integer pyschologyDaysOfTherapy
	Integer pyschologyMinutesOfTherapy
	Boolean nurseLedTherapyRequired
	Integer nurseLedTherapyDaysOfTherapy
	Integer nurseLedTherapyMinutesOfTherapy
	
	static auditable = [ignore:[]]
	
	static belongsTo = [careActivity:CareActivity]
	

    static constraints = {
		pyschologyTherapyRequired(nullable:true)
		pyschologyDaysOfTherapy(nullable:true)
		pyschologyMinutesOfTherapy(nullable:true)
		nurseLedTherapyRequired(nullable:true)
		nurseLedTherapyDaysOfTherapy(nullable:true)
		nurseLedTherapyMinutesOfTherapy(nullable:true)
		occupationalTherapyManagement(nullable:true)
		speechAndLanguageTherapyManagement(nullable:true)
		physiotherapyManagement(nullable:true)
		assessmentManagement(nullable:true)
		cognitiveStatusAssessed(nullable:true)
		cognitiveStatusAssessmentDate(nullable:true
			,validator:{cognitiveStatusAssessmentDate, therapyManagement ->
				if(!therapyManagement?.cognitiveStatusAssessmentDate && therapyManagement?.cognitiveStatusAssessed == Boolean.TRUE  ){
					return "therapyManagement.cognitive.status.yes.date.missing"
				}
				if( therapyManagement?.cognitiveStatusAssessed == Boolean.TRUE){
					if(cognitiveStatusAssessmentDate > new Date()){
						return "cognitive.assessment.date.not.in.future"
					}
					if(therapyManagement.careActivity.afterDischarge(cognitiveStatusAssessmentDate, therapyManagement.cognitiveStatusAssessmentTime)){
						return "cognitive.assessment.date.not.after.discharge"
					}
					if(therapyManagement.careActivity.beforeAdmission(cognitiveStatusAssessmentDate, therapyManagement.cognitiveStatusAssessmentTime)){
						return "cognitive.assessment.date.not.before.admission"
					}
				}
		})
		cognitiveStatusAssessmentTime(nullable:true)
		
		cognitiveStatusNoAssessmentType(nullable:true
			,validator:{cognitiveStatusNoAssessmentType, therapyManagement ->
				if(therapyManagement?.cognitiveStatusAssessed == Boolean.FALSE && !cognitiveStatusNoAssessmentType ){
					return "therapyManagement.cognitive.status.no.assesment.reason.missing"
				}
		})
		rehabGoalsSet(nullable:true)
		rehabGoalsSetDate(nullable:true
			,validator:{rehabGoalsSetDate, therapyManagement ->
				if(!therapyManagement?.rehabGoalsSetDate && therapyManagement?.rehabGoalsSet == Boolean.TRUE  ){
					return "therapyManagement.rehab.goals.set.yes.date.missing"
				}
				if( therapyManagement?.rehabGoalsSet == Boolean.TRUE){
					if(rehabGoalsSetDate){
						if(rehabGoalsSetDate > new Date()){
							return "rehab.goals.date.not.in.future"
						}
						if(therapyManagement.careActivity.afterDischarge(rehabGoalsSetDate, therapyManagement.rehabGoalsSetTime)){
							return "rehab.goals.date.not.after.discharge"
						}
						if(therapyManagement.careActivity.beforeAdmission(rehabGoalsSetDate, therapyManagement.rehabGoalsSetTime)){
							return "rehab.goals.date.not.before.admission"
						}
					}
				}
		})
		rehabGoalsSetTime(nullable:true)
			
		rehabGoalsNotSetReasonType(nullable:true
			,validator:{rehabGoalsNotSetReasonType, therapyManagement ->
				if(therapyManagement?.rehabGoalsSet == Boolean.FALSE && !rehabGoalsNotSetReasonType ){
					return "therapyManagement.rehab.goals.no.reason.missing"
				}
		})
		
		
		
		
    }
	
	
	
	
	public TherapyDetail cognitiveAssessmentDetail (){
		return new TherapyDetail ([wasPerformed:cognitiveStatusAssessed
							, performedDate:cognitiveStatusAssessmentDate
							, performedTime:cognitiveStatusAssessmentTime
							, reasonNotPerformed:cognitiveStatusNoAssessmentType?.description
							, type:"Cognitive Assessment"] )
	}
	
	
	public TherapyDetail mdtRehabilitationGoalsDetail (){
		return new TherapyDetail ([wasPerformed:rehabGoalsSet
							, performedDate:rehabGoalsSetDate
							, performedTime:rehabGoalsSetTime
							, reasonNotPerformed:rehabGoalsNotSetReasonType?.description
							, type:"Documented MDT Goals"
							, hoursSinceAdmission: mdtRehabilitationGoalsHoursSinceAdmission()] )
	}
	

	private Float mdtRehabilitationGoalsHoursSinceAdmission (){
		
		careActivity.hoursSinceAdmission(rehabGoalsSetDate,rehabGoalsSetTime );
	}
}


/*

 , occupationalTherapyManagement:occupationalTherapyManagement
 , speechAndLanguageTherapyManagement:speechAndLanguageTherapyManagement
 , physiotherapyManagement:physiotherapyManagement
 , baselineAssessments:baselineAssessments]*/