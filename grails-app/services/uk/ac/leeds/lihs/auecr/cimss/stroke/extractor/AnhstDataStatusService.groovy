package uk.ac.leeds.lihs.auecr.cimss.stroke.extractor

import groovy.time.TimeCategory;
import groovy.time.TimeDuration;
import uk.ac.leeds.lihs.auecr.cimss.stroke.CareActivity;

class AnhstDataStatusService {

    static transactional = true

    def getDataStatus(CareActivity careActivity) {
		
		AnhstDataStatus anhstDataStatus = new AnhstDataStatus();
		anhstDataStatus.inError = Boolean.FALSE
		
		def hoursToTakeImage = 0
		
		if(careActivity?.imaging?.scan?.takenDate){
			   if(!careActivity?.imaging?.scan?.takenOverride==Boolean.TRUE){
				   def admission = ((Date)careActivity.startDate).toCalendar();
				   if(careActivity.startTime && careActivity?.imaging?.scan?.takenTime){
					   admission.set( Calendar.HOUR_OF_DAY, (int)careActivity.startTime / 60)
					   admission.set( Calendar.MINUTE ,careActivity.startTime % 60	)
				   }else{
					   admission.set( Calendar.HOUR_OF_DAY, 0)
				   }
				   
				   def imageDate = ((Date)careActivity?.imaging?.scan?.takenDate).toCalendar();
				     if(careActivity.startTime && careActivity?.imaging?.scan?.takenTime){
					   imageDate.set( Calendar.HOUR_OF_DAY, (int)careActivity?.imaging?.scan?.takenTime / 60)
					   imageDate.set( Calendar.MINUTE ,careActivity?.imaging?.scan?.takenTime % 60	)
				   }else{
					   imageDate.set( Calendar.HOUR_OF_DAY, 0)
				   }
			
				   TimeDuration duration = TimeCategory.minus( imageDate.time, admission.time )
				   hoursToTakeImage = (duration.days * 24) + (duration.hours) + (duration.minutes/60)
				
				   if(hoursToTakeImage > 24){
					   anhstDataStatus.lateImage=Boolean.TRUE
					   anhstDataStatus.inError = Boolean.TRUE
					   anhstDataStatus.errorList.add("Imaging is normally carried out within 24 hours of admission");
				   }
			   }
			   		   
		}
	   
		
		
		
		return anhstDataStatus
    }
	
	
	
	
	
	
	
	

}
