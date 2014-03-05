package uk.ac.leeds.lihs.auecr.cimss.stroke.therapy

import java.util.Date;

import uk.ac.leeds.lihs.auecr.cimss.stroke.DateTimeHelper;
import uk.ac.leeds.lihs.auecr.cimss.stroke.TherapyDetail;
import uk.ac.leeds.lihs.auecr.cimss.stroke.TherapyManagement
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.OccupationalTherapyNoAssessmentReasonType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.PhysiotherapyNoAssessmentReasonType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.TherapyTimeValidator;
import uk.ac.leeds.lihs.auecr.cimss.stroke.therapy.session.TimedTherapy;

class OccupationalTherapyManagement  {
	
	Boolean assessmentPerformed
	Boolean assessmentPerformedIn72Hrs
	Date assessmentDate
	Integer assessmentTime
	
	
	Boolean therapyRequired
	Integer daysOfTherapy
	Integer minutesOfTherapy
	
	
	Boolean moodAssessmentPerformed
	Date moodAssessmentDate
	Integer moodAssessmentTime

	OccupationalTherapyNoAssessmentReasonType noAssessmentReasonType
	OccupationalTherapyNoAssessmentReasonType no72HrAssessmentReasonType
	
	OccupationalTherapyNoAssessmentReasonType noMoodAssessmentReasonType
	
	
	static auditable = [ignore:[]]
	static belongsTo = [therapyManagement:TherapyManagement]
	
	public TherapyDetail occupationalTherapyDetail (){
		return new TherapyDetail ([wasPerformed:assessmentPerformed
							, wasPerformedIn72Hrs:assessmentPerformedIn72Hrs
							, performedDate:assessmentDate
							, performedTime:assessmentTime
							, reasonNotPerformed:noAssessmentReasonType?.description
							, reasonNotPerformedIn72Hrs:no72HrAssessmentReasonType?.description
							, type:"Occupational Therapy"
							, hoursSinceAdmission:assessmentHoursSinceAdmission()] )
	}
	

	private Float assessmentHoursSinceAdmission (){
		return therapyManagement.careActivity.hoursSinceAdmission(assessmentDate,assessmentTime );
	}
	
	public TherapyDetail moodAssessmentDetail (){
		
		return new TherapyDetail ([wasPerformed:moodAssessmentPerformed
							, performedDate:moodAssessmentDate
							, performedTime:moodAssessmentTime
							, reasonNotPerformed:noMoodAssessmentReasonType?.description
							, type:"Mood Assessment"])

	}
	
	

	
	
    static constraints = {
		therapyRequired(nullable:true)
		daysOfTherapy(nullable:true)
		minutesOfTherapy(nullable:true)
		assessmentPerformed(nullable:true)
		assessmentPerformedIn72Hrs(nullable:true)
		assessmentDate(nullable:true
			,validator:{assessmentDate, occupationalTherapyManagement ->
				if(!occupationalTherapyManagement?.assessmentDate && occupationalTherapyManagement?.assessmentPerformed == Boolean.TRUE  ){
					return "occupational.therapy.management.yes.date.missing"
				}
				if( occupationalTherapyManagement?.assessmentPerformed == Boolean.TRUE ){
					if(assessmentDate > new Date()){
						return "assessment.date.not.in.future"
					}
					if(occupationalTherapyManagement.therapyManagement.careActivity.afterDischarge(assessmentDate, occupationalTherapyManagement.assessmentTime)){
						return "assessment.date.not.after.discharge"
					}
					if(occupationalTherapyManagement.therapyManagement.careActivity.beforeAdmission(assessmentDate, occupationalTherapyManagement.assessmentTime)){
						return "assessment.date.not.before.admission"
					}
				}
				
				
		})
		assessmentTime(nullable:true
			,validator:{assessmentTime, occupationalTherapyManagement ->
				if(occupationalTherapyManagement?.assessmentTime == null && occupationalTherapyManagement?.assessmentPerformed == Boolean.TRUE  ){
					return "occupational.therapy.management.yes.time.missing"
				}
				if( occupationalTherapyManagement?.assessmentPerformed == Boolean.TRUE ){
					if( occupationalTherapyManagement.assessmentDate == DateTimeHelper.getCurrentDateAtMidnight()){
						if(assessmentTime > DateTimeHelper.getCurrentTimeAsMinutes() ){
							return "assessment.time.not.in.future"
						}
					}
				}
		})
		noAssessmentReasonType(nullable:true
			,validator:{noAssessmentReasonType, occupationalTherapyManagement ->
				if(occupationalTherapyManagement?.assessmentPerformed == Boolean.FALSE
					&& !noAssessmentReasonType ){
					return "occupational.therapy.management.no.assesment.reason.missing"
				}
		})
		no72HrAssessmentReasonType(nullable:true
			,validator:{no72HrAssessmentReasonType, occupationalTherapyManagement ->
				if(occupationalTherapyManagement?.assessmentPerformedIn72Hrs == Boolean.FALSE
					&& !no72HrAssessmentReasonType ){
					return "occupational.therapy.management.no.72hr.assesment.reason.missing"
				}
		})
		moodAssessmentPerformed(nullable:true)
		moodAssessmentDate(nullable:true
			, validator:{moodAssessmentDate, occupationalTherapyManagement ->
				if(!moodAssessmentDate && occupationalTherapyManagement?.moodAssessmentPerformed == Boolean.TRUE  ){
					return "occupational.therapy.management.yes.mood.assessment.date.missing"
				}
				if( occupationalTherapyManagement?.moodAssessmentPerformed == Boolean.TRUE){
					if(moodAssessmentDate > new Date()){
						return "mood.assessment.date.not.in.future"
					}
					if(occupationalTherapyManagement.therapyManagement.careActivity.afterDischarge(moodAssessmentDate, occupationalTherapyManagement.moodAssessmentTime)){
						return "mood.assessment.date.not.after.discharge"
					}
					if(occupationalTherapyManagement.therapyManagement.careActivity.beforeAdmission(moodAssessmentDate, occupationalTherapyManagement.moodAssessmentTime)){
						return "mood.assessment.date.not.before.admission"
					}
				}
			}
		)
		moodAssessmentTime(nullable:true)
		
		noMoodAssessmentReasonType(nullable:true
			,validator:{noMoodAssessmentReasonType, occupationalTherapyManagement ->
				if(occupationalTherapyManagement?.moodAssessmentPerformed == Boolean.FALSE
					&& !noMoodAssessmentReasonType ){
					return "occupational.therapy.management.no.mood.assesment.reason.missing"
				}
		})
		
		
		
	}
}
