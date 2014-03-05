package uk.ac.leeds.lihs.auecr.cimss.stroke.therapy

import uk.ac.leeds.lihs.auecr.cimss.stroke.DateTimeHelper;
import uk.ac.leeds.lihs.auecr.cimss.stroke.TherapyDetail;
import uk.ac.leeds.lihs.auecr.cimss.stroke.TherapyManagement;
import uk.ac.leeds.lihs.auecr.cimss.stroke.TherapyTimeValidator;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.PhysiotherapyNoAssessmentReasonType;

class PhysiotherapyManagement {

    Boolean assessmentPerformed
	Boolean assessmentPerformedIn72Hrs
	
	Date assessmentDate
	Integer assessmentTime
	
	PhysiotherapyNoAssessmentReasonType noAssessmentReasonType
	PhysiotherapyNoAssessmentReasonType no72HrAssessmentReasonType
	
	Boolean therapyRequired
	Integer daysOfTherapy
	Integer minutesOfTherapy

	static belongsTo = [therapyManagement:TherapyManagement]
		static auditable = [ignore:[]]
		
		
     
		
    
	
	public TherapyDetail physiotherapyDetail (){
		return new TherapyDetail ([wasPerformed:assessmentPerformed
							, wasPerformedIn72Hrs:assessmentPerformedIn72Hrs
							, performedDate:assessmentDate
							, performedTime:assessmentTime
							, reasonNotPerformed:noAssessmentReasonType?.description
							, reasonNotPerformedIn72Hrs:no72HrAssessmentReasonType?.description
							, type:"Physiotherapy"
							, hoursSinceAdmission:assessmentHoursSinceAdmission()])
	}
	
	private Float assessmentHoursSinceAdmission (){
		return therapyManagement.careActivity.hoursSinceAdmission(assessmentDate,assessmentTime );
	}
	
    static constraints = {
		therapyRequired(nullable:true)
		daysOfTherapy(nullable:true /*, validator:{daysOfTherapy,physiotherapyManagement, errors -> return TherapyTimeValidator.validate(
			physiotherapyManagement, physiotherapyManagement.therapyManagement?.careActivity, "Physiotherapy", errors )}*/)
		minutesOfTherapy(nullable:true)
		assessmentPerformed(nullable:true)
		assessmentPerformedIn72Hrs(nullable:true)
		assessmentDate(nullable:true
			,validator:{assessmentDate, physiotherapyManagement ->
				if(!physiotherapyManagement?.assessmentDate && physiotherapyManagement?.assessmentPerformed == Boolean.TRUE  ){
					return "physiotherapy.management.yes.date.missing"
				}
				if( physiotherapyManagement?.assessmentPerformed == Boolean.TRUE){
					if(assessmentDate > new Date()){
						return "physiotherapy.assessment.date.not.in.future"
					}
					if(physiotherapyManagement.therapyManagement.careActivity.afterDischarge(assessmentDate, physiotherapyManagement.assessmentTime)){
						return "physiotherapy.assessment.date.not.after.discharge"
					}
					if(physiotherapyManagement.therapyManagement.careActivity.beforeAdmission(assessmentDate, physiotherapyManagement.assessmentTime)){
						return "physiotherapy.assessment.date.not.before.admission"
					}
				}
		})
		assessmentTime(nullable:true
			,validator:{assessmentTime, physiotherapyManagement ->
				if(physiotherapyManagement?.assessmentTime == null && physiotherapyManagement?.assessmentPerformed == Boolean.TRUE  ){
					return "physiotherapy.management.yes.time.missing"
				}
				if( physiotherapyManagement?.assessmentPerformed == Boolean.TRUE ){
					if( physiotherapyManagement.assessmentDate == DateTimeHelper.getCurrentDateAtMidnight()){
						if(assessmentTime > DateTimeHelper.getCurrentTimeAsMinutes() ){
							return "physiotherapy.assessment.time.not.in.future"
						}
					}
				}
		})
		noAssessmentReasonType(nullable:true
			,validator:{noAssessmentReasonType, physiotherapyManagement ->
				if(physiotherapyManagement?.assessmentPerformed == Boolean.FALSE 
					&& !noAssessmentReasonType ){
					return "physiotherapy.management.no.assesment.reason.missing"
				}
		})
		no72HrAssessmentReasonType(nullable:true
			,validator:{no72HrAssessmentReasonType, physiotherapyManagement ->
				if(physiotherapyManagement?.assessmentPerformedIn72Hrs == Boolean.FALSE
					&& !no72HrAssessmentReasonType ){
					return "physiotherapy.management.no.72hr.assesment.reason.missing"
				}
		})
	}
}
