package uk.ac.leeds.lihs.auecr.cimss.stroke

import java.util.Date;

class Thrombolysis{

	
		
	Date thrombolysisDate
	Integer thrombolysisTime
	Integer doorToNeedleTime
	String decisionMakerGrade
	String decisionMakerLocation
	String decisionMakerSpeciality
	String decisionMakerSpecialityOther
	Boolean complications
	String complicationType
	
	Boolean complicationTypeBleed
	Boolean complicationTypeHaemorrhage
	Boolean complicationTypeOedema
	Boolean complicationTypeOther
	
	String complicationTypeOtherText
	Boolean followUpScan
	Date followUpScanDate
	Integer followUpScanTime
	
	Integer nihssScoreAt24Hours
	Boolean nihssScoreAt24HoursUnknown
	
	static belongsTo = [careActivity:CareActivity]
	
	static auditable = [ignore:[]]
    static constraints = {
		thrombolysisDate(validator:{thrombolysisDate, thrombolysis ->
			if(thrombolysisDate){
				if(thrombolysisDate > new Date()){
					return "thrombolysis.date.not.in.future"
				}
				if(thrombolysis.careActivity.afterDischarge(thrombolysisDate, thrombolysis.thrombolysisTime)){
					return "thrombolysis.date.not.after.discharge"
				}
				if(thrombolysis.careActivity.beforeImaging(thrombolysisDate, thrombolysis.thrombolysisTime)){
					return "thrombolysis.date.not.before.imaging"
				}
				/*if(thrombolysis.careActivity.beforeAdmission(thrombolysisDate, thrombolysis.thrombolysisTime)){
					return "thrombolysis.date.not.before.admission"
				}*/
			}
			
	
		})
		thrombolysisTime(validator:{thrombolysisTime, thrombolysis ->
				if( !thrombolysis.thrombolysisDate){
					if(thrombolysisTime){
						return "thrombolysis.time.without.date"
					}
				}else{
					if( thrombolysis.thrombolysisDate == DateTimeHelper.getCurrentDateAtMidnight()){
						if(thrombolysisTime > DateTimeHelper.getCurrentTimeAsMinutes() ){
							return "thrombolysis.time.not.in.future"
						}
					}
				}
	
		})
		
		doorToNeedleTime(nullable:true)
		
		nihssScoreAt24HoursUnknown(nullable:true)
		complicationTypeBleed(nullable:true)
		complicationTypeHaemorrhage(nullable:true)
		complicationTypeOedema(nullable:true)
		complicationTypeOther(nullable:true)
		
		complicationTypeOther(nullable:true
			,validator:{complicationTypeOther, thrombolysis ->
				if(thrombolysis?.complications){
					if(thrombolysis?.complicationTypeOther ==Boolean.TRUE && !thrombolysis?.complicationTypeOtherText){
						return "thrombolysis.yes.complications.type.other.missing"
					}
					
				}
			})
		
		complicationTypeOtherText(nullable:true)
		
		decisionMakerGrade(nullable:true)
		decisionMakerSpeciality(nullable:true)
		decisionMakerSpecialityOther(nullable:true
			,validator:{decisionMakerSpecialityOther, thrombolysis ->
				if(thrombolysis?.decisionMakerSpeciality=="other"){
					if(!decisionMakerSpecialityOther){
						return "thrombolysis.yes.decision.maker.speciality.other"
					}
					
				}
			})
		complicationType(nullable:true)
		
		
		followUpScanDate(nullable:true
			,validator:{followUpScanDate, thrombolysis ->
				if(thrombolysis?.followUpScan){
					if(!followUpScanDate){
						return "thrombolysis.yes.follow.up.date"
					}else if (thrombolysis.followUpScanTime && thrombolysis.thrombolysisDate && thrombolysis.thrombolysisTime){
						if(!thrombolysis.theActualFollowUpScanDate().after(thrombolysis.theActualThrombolysisDate())){
							return "thrombolysis.yes.follow.up.must.be.after.thrombolysis.date"
						}
					}
					if(followUpScanDate){
						if(followUpScanDate > new Date()){
							return "thrombolysis.follow.up.date.not.in.future"
						}
						if(thrombolysis.careActivity.beforeAdmission(followUpScanDate, thrombolysis.followUpScanTime)){
							return "thrombolysis.follow.up.date.not.before.admission"
						}
						if(thrombolysis.careActivity.afterDischarge(followUpScanDate, thrombolysis.followUpScanTime)){
							return "thrombolysis.follow.up.date.not.after.discharge"
						}
					}
					
					
				
					
					
					
				}
			})
		followUpScanTime(nullable:true
			,validator:{followUpScanTime, thrombolysis ->
				if(thrombolysis?.followUpScan){
					if(followUpScanTime == null){
						return "thrombolysis.yes.follow.up.time"
					}else if (thrombolysis.followUpScanDate && thrombolysis.thrombolysisDate && thrombolysis.thrombolysisTime){
						/*if(!thrombolysis.theActualFollowUpScanDate().after(thrombolysis.theActualThrombolysisDate())){
							return "thrombolysis.yes.follow.up.must.be.after.thrombolysis.time"
						}*/
					}
					if( !thrombolysis.followUpScanDate){
						if(followUpScanTime){
							return "thrombolysis.follow.up.time.without.date"
						}
					}else{
						if( thrombolysis.followUpScanDate == DateTimeHelper.getCurrentDateAtMidnight()){
							if(followUpScanTime > DateTimeHelper.getCurrentTimeAsMinutes() ){
								return "thrombolysis.follow.up.time.not.in.future"
							}
						}
					}
					
				}
			})
		nihssScoreAt24Hours(nullable:true, range: 0..42)
		
    }
	
	def parseThrombolysisTime = {timeString ->
		thrombolysisTime = parseTime (timeString)
	}
	
	def parseFollowUpScanTime = {timeString ->
		followUpScanTime = parseTime (timeString)
	}
	
	private def parseTime = {timeString ->
		return DisplayUtils.getTime(timeString)
	} 
	
	String followUpScanTimeAsString (){
		if(followUpScanTime){
			def minutes = followUpScanTime % 60
			def hours = (followUpScanTime - minutes)/60
			return "${hours}:${minutes}"
		}
	}
	
	Date theActualFollowUpScanDate(){
		return new Date(followUpScanDate.time + (followUpScanTime * 60 * 1000))
	}
	
	Date theActualThrombolysisDate(){
		return  new Date(thrombolysisDate.time + (thrombolysisTime * 60 * 1000))
	}
	
	String thrombolysedTimeAsString (){
		if(thrombolysisTime){
			def minutes = thrombolysisTime % 60
			def hours = (thrombolysisTime - minutes)/60
			return "${hours}:${minutes}"
		}
	}
}
