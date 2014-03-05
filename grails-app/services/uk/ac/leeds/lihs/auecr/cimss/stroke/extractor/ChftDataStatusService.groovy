package uk.ac.leeds.lihs.auecr.cimss.stroke.extractor

import groovy.time.TimeCategory;
import groovy.time.TimeDuration;
import uk.ac.leeds.lihs.auecr.cimss.stroke.CareActivity;

class ChftDataStatusService {

    static transactional = true

    def getDataStatus(CareActivity careActivity) {
		
		ChftDataStatus chftDataStatus = new ChftDataStatus();
		if(careActivity?.nutritionManagement?.dateScreened){
			if(hoursSinceAdmission(careActivity.startDate, careActivity.startTime
					 							, careActivity?.nutritionManagement?.dateScreened
												, careActivity?.nutritionManagement?.timeScreened) > 48 ){
				chftDataStatus.lateMustScreen=Boolean.TRUE
				chftDataStatus.inError = Boolean.TRUE
				chftDataStatus.errorList.add("MUST evaluation should be carried out within 48 hours of admission");
				
			}else{
				chftDataStatus.lateMustScreen=Boolean.FALSE
				chftDataStatus.inError = Boolean.FALSE
			}
		}else{
			if(hoursSinceAdmissionToToday(careActivity.startDate, careActivity.startTime) > 48 ){
				chftDataStatus.lateMustScreen=Boolean.TRUE
				chftDataStatus.inError = Boolean.TRUE
				chftDataStatus.errorList.add("MUST evaluation should be carried out within 48 hours of admission");
				
			}else{
				chftDataStatus.lateMustScreen=Boolean.FALSE
				chftDataStatus.inError = Boolean.FALSE
			}
		}
		processNutritionManagementData(chftDataStatus, careActivity);
		return chftDataStatus
    }
	
	
	
	
	
	private def hoursSinceAdmissionToToday = {startDate, startTime->
		
		if ( startDate == null ) {
			return 0;
		}
		if ( startTime  == null ) {
			startTime = 0
		}
		
		def admission = ((Date)startDate).toCalendar();
		if(startTime){
			admission.set( Calendar.HOUR_OF_DAY, (int)startTime / 60)
			admission.set( Calendar.MINUTE ,startTime % 60	)
		}else{
			admission.set( Calendar.HOUR_OF_DAY, 0)
		}
		TimeDuration duration = TimeCategory.minus( new Date(), admission.time )
		return  (duration.days * 24) + (duration.hours) + (duration.minutes/60)
	} 
	
	
	
	private def hoursSinceAdmission = {startDate, startTime, endDate, endTime ->
		
		def admission = ((Date)startDate).toCalendar();
		if(startTime){
			admission.set( Calendar.HOUR_OF_DAY, (int)startTime / 60)
			admission.set( Calendar.MINUTE ,startTime % 60	)
		}else{
			admission.set( Calendar.HOUR_OF_DAY, 0)
		}
		
		def screenDate = ((Date)endDate).toCalendar();
		if(endTime){
			screenDate.set( Calendar.HOUR_OF_DAY, (int)endTime / 60)
			screenDate.set( Calendar.MINUTE ,endTime % 60	)
		}else{
			screenDate.set( Calendar.HOUR_OF_DAY, 0)
		}
		
		
		
		TimeDuration duration = TimeCategory.minus( screenDate.time , admission.time )
		
		
		return  (duration.days * 24) + (duration.hours) + (duration.minutes/60)
		
		
		 
		
	}
	
	private processNutritionManagementData(ChftDataStatus chftDataStatus, CareActivity careActivity) {
		
		
		def nutritionalScreenHighRisk = Boolean.FALSE;
		
		if(careActivity?.nutritionManagement?.unableToScreen!=Boolean.TRUE){
			if (careActivity?.nutritionManagement?.mustScore >= 2){
				nutritionalScreenHighRisk = Boolean.TRUE
			}else if(careActivity?.nutritionManagement?.mustScore != null  &&  careActivity?.nutritionManagement?.mustScore < 2){
				nutritionalScreenHighRisk = Boolean.FALSE
			}else{			
				chftDataStatus.inError = Boolean.TRUE
				chftDataStatus.errorList.add( "Whether the patient has had a nutritional screening is missing");
			}
			
			if (nutritionalScreenHighRisk == Boolean.TRUE){
				
				def dietitianSeen = Boolean.FALSE;
				
				if(careActivity?.nutritionManagement?.dietitianReferralDate){
					dietitianSeen = Boolean.TRUE
				}else{
					dietitianSeen = Boolean.FALSE
				}
				
				
				if(careActivity?.nutritionManagement?.dietitianNotSeen != Boolean.TRUE && dietitianSeen!=Boolean.TRUE){
					chftDataStatus.inError = Boolean.TRUE
					chftDataStatus.errorList.add("For high risk patients the dietician referral details must be provided");
				}
				
			}
			
			
		}
		
		
	}
	
}
