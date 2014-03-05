package uk.ac.leeds.lihs.auecr.cimss.stroke

import uk.ac.leeds.lihs.auecr.cimss.stroke.clinical.assessment.GlasgowComaScore;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.ClinicalClassificationType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.NoSwallowScreenPerformedReasonType;

class ClinicalAssessment {
	
	GlasgowComaScore glasgowComaScore
	
	Boolean facialWeakness
	String facialPalsy
	String bestGaze
	String dysarthria
	String aphasia
	String hemianopia 
	String inattention 
	String limbAtaxia 
	String other 
	String otherText 
	ClinicalClassificationType classification 
	String independent 

	String faceSensoryLoss
	String armSensoryLoss
	String legSensoryLoss
	
	String sensoryLoss
	
	Integer armMrcScale
	Integer legMrcScale
	
	Integer leftArmMrcScale
	Integer leftLegMrcScale
	
	Integer rightArmMrcScale
	Integer rightLegMrcScale
	
	
	
	
	Boolean leftFaceAffected
	Boolean rightFaceAffected
	Boolean neitherFaceAffected
	
	Boolean leftArmAffected 
	Boolean rightArmAffected 
	Boolean neitherArmAffected
	Boolean leftLegAffected 
	Boolean rightLegAffected 
	Boolean neitherLegAffected
	
	Boolean leftSideAffected
	Boolean rightSideAffected
	Boolean neitherSideAffected
	
	
	String dominantHand
	
	String locStimulation
	String locQuestions
	String locTasks
	
	
	Boolean walkAtPresentation
	Boolean mobilePreStroke
	
	Boolean swallowScreenPerformed
	Date swallowScreenDate
	Integer swallowScreenTime
	NoSwallowScreenPerformedReasonType noSwallowScreenPerformedReason
	NoSwallowScreenPerformedReasonType noSwallowScreenPerformedReasonAt4Hours
	
	
	public TherapyDetail swallowingScreenDetail (){
		return new TherapyDetail ([wasPerformed:swallowScreenPerformed
							, performedDate:swallowScreenDate
							, performedTime:swallowScreenTime
							, reasonNotPerformed:noSwallowScreenPerformedReason?.description
							, type:"Swallowing Screen"
							, hoursSinceAdmission:assessmentHoursSinceAdmission()] )
	}
	
	private Float assessmentHoursSinceAdmission (){
		return careActivity.hoursSinceAdmission(swallowScreenDate,swallowScreenTime );
	}
	
	static auditable = [ignore:[]]
	
	static belongsTo = [careActivity:CareActivity]

    static constraints = {
		facialWeakness(nullable:true)
		facialPalsy(nullable:true)
		bestGaze(nullable:true)
		dysarthria(nullable:true)
		aphasia(nullable:true)
		hemianopia(nullable:true)
		inattention(nullable:true)
		limbAtaxia(nullable:true)
		other(nullable:true)
		otherText(nullable:true
			,validator:{otherText, clinicalAssessment->
				if(clinicalAssessment.other =='yes' && !otherText){
					return 'clinical.assessment.other.text.missing'
				}
					
			 })
		classification(nullable:true)
		independent(nullable:true)
		dominantHand(nullable:true)
		faceSensoryLoss(nullable:true)
		armSensoryLoss(nullable:true)
		legSensoryLoss(nullable:true)
		sensoryLoss(nullable:true)
		
		armMrcScale(nullable:true)
		legMrcScale(nullable:true)
		
		
		leftArmMrcScale(nullable:true)
		leftLegMrcScale(nullable:true)
		
		rightArmMrcScale(nullable:true)
		rightLegMrcScale(nullable:true)
		
		
		leftSideAffected(nullable:true)
		rightSideAffected(nullable:true)
		neitherSideAffected(nullable:true)
		
		
		leftFaceAffected(nullable:true)
		rightFaceAffected(nullable:true)
		neitherFaceAffected(nullable:true)	
		leftArmAffected(nullable:true)
		rightArmAffected(nullable:true)
		neitherArmAffected(nullable:true)
		leftLegAffected(nullable:true)
		rightLegAffected(nullable:true)
		neitherLegAffected(nullable:true)
		glasgowComaScore(nullable:true)
		walkAtPresentation(nullable:true)
		locStimulation(nullable:true)
		locQuestions(nullable:true)
		locTasks(nullable:true)
		
		
		
		mobilePreStroke(nullable:true)
		swallowScreenPerformed(nullable:true
			,validator:{swallowScreenPerformed, clinicalAssessment ->
//				if(swallowScreenPerformed == Boolean.TRUE){
//					if(!clinicalAssessment.swallowScreenDate){
//						return "swallow.screened.date.must.be.provided"
//					}
//					if(clinicalAssessment.swallowScreenTime == null) {
//						return "swallow.screened.time.must.be.provided"
//					}
//				}
				if(swallowScreenPerformed == Boolean.FALSE){
					if(!clinicalAssessment.noSwallowScreenPerformedReason && !clinicalAssessment.noSwallowScreenPerformedReasonAt4Hours){
						return "no.swallow.screened.reason.must.be.provided"
					}
				}
				
				
			}) 
		swallowScreenDate(nullable:true
			,validator:{swallowScreenDate, clinicalAssessment ->
				if(swallowScreenDate){
					if(swallowScreenDate.after(new Date())){
						return "swallow.screened.date.not.in.future"
					}
					/*if(clinicalAssessment.careActivity.beforeAdmission(swallowScreenDate, clinicalAssessment.swallowScreenTime)){
						return "swallow.screened.date.not.before.admission"
					}*/
					if(clinicalAssessment.careActivity.afterDischarge(swallowScreenDate, clinicalAssessment.swallowScreenTime)){
						return "swallow.screened.date.not.after.discharge"
					}
					
				} else if ( clinicalAssessment.swallowScreenPerformed){
					return "swallow.screened.date.must.be.provided"
				}
				
			})
		swallowScreenTime(nullable:true
			,validator:{swallowScreenTime, clinicalAssessment ->
				if( !clinicalAssessment.swallowScreenDate){
					if(swallowScreenTime != null){
						return "swallow.screened.time.without.date"
					}
				}else{
					if( swallowScreenTime && clinicalAssessment.swallowScreenDate == DateTimeHelper.getCurrentDateAtMidnight()){
						if(swallowScreenTime > DateTimeHelper.getCurrentTimeAsMinutes() ){
							return "swallow.screened.time.not.in.future"
						}
					}
				}
				if ( clinicalAssessment.swallowScreenPerformed && clinicalAssessment.swallowScreenTime == null) {
					return "swallow.screened.time.must.be.provided"
				}
				
			})
		noSwallowScreenPerformedReason(nullable:true)
		noSwallowScreenPerformedReasonAt4Hours(nullable:true)
		
	}
	
	private static Map nihssScores = ["locStimulation":['keen':0,'arousal':1,'repeated':2,'responsive':3]
									,"locQuestions":['both':0,'one':1,'neither':2]
									,"locTasks":['both':0,'one':1,'neither':2]
									,"bestGaze":['normal':0,'partial':1,'forced':2]
									,"facialPalsy":['normal':0,'minor':1,'partial':2,'complete':3]
									,"limbAtaxia":['yes':0,'single':1,'two':2]
									,"dysarthria":['normal':0,'mild':1,'severe':2]
									,"inattention":['normal':0,'single':1,'profound':2]]
	
	public def calculateNihssScore = {type ->
		return nihssScores.get(type)?.get(this."${type}")
		
	}
}
