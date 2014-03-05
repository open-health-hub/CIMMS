package uk.ac.leeds.lihs.auecr.cimss.stroke


import groovy.time.TimeCategory;
import groovy.time.TimeDuration;

class DateTimeHelper {
	
	public static Integer getCurrentTimeAsMinutes (){
		def calendar = new GregorianCalendar()
		return calendar.get(Calendar.HOUR_OF_DAY) * 60  + calendar.get(Calendar.MINUTE)
	}
	
	public static Date getCurrentDateAtMidnight (){
		def calendar = new GregorianCalendar()
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);
		return calendar.time		
	}
	
	
	public static Date computeDate(java.sql.Timestamp date, Integer time) {
		Calendar theDate = GregorianCalendar.getInstance()
		theDate.setTime(date)
		if(time != null){
			theDate.set( Calendar.HOUR_OF_DAY, (int)time/ 60)
			theDate.set( Calendar.MINUTE , time % 60	)
		}
		return theDate.getTime()
	}
	
	public static Float hoursBetween (Date date1, Integer time1, Date date2, Integer time2){
		
		
		if(date1 && date2){
			def theFirstDate = (date1).toCalendar();
			def theSecondDate = (date2).toCalendar();
			if(time1 != null  && time2 != null){
				theFirstDate.set( Calendar.HOUR_OF_DAY, (int)time1/ 60)
				theFirstDate.set( Calendar.MINUTE , time1 % 60	)
				theSecondDate.set( Calendar.HOUR_OF_DAY, (int)time2/ 60)
				theSecondDate.set( Calendar.MINUTE , time2 % 60	)
			}else{
				theFirstDate.set( Calendar.HOUR_OF_DAY, 0)
				theSecondDate.set( Calendar.HOUR_OF_DAY, 0)
			}
			def TimeDuration duration = TimeCategory.minus( theSecondDate.time, theFirstDate.time )
			def hoursTaken = (duration.days * 24) + (duration.hours) + (duration.minutes/60)
			return hoursTaken;
		}
		return null	
	}
	
	
	
	/*def theDate = ((Date)careActivity?.therapyManagement?.rehabGoalsSetDate ).toCalendar();
	if(careActivity.startTime && careActivity?.therapyManagement?.rehabGoalsSetTime){
		theDate.set( Calendar.HOUR_OF_DAY, (int)careActivity?.therapyManagement?.rehabGoalsSetTime / 60)
		theDate.set( Calendar.MINUTE ,careActivity?.therapyManagement?.rehabGoalsSetTime % 60	)
	}else{
		theDate.set( Calendar.HOUR_OF_DAY, 0)
	}

	def TimeDuration duration = TimeCategory.minus( theDate.time, admission.time )
	def hoursTaken = (duration.days * 24) + (duration.hours) + (duration.minutes/60)
	
	if(hoursTaken > 72 ){
		if(data.rehabGoalsNotSetReason !="null"  && data.rehabGoalsNotSetReason!= JSONObject.NULL){
			careActivity.therapyManagement.rehabGoalsNotSetReasonType = RehabGoalsNotSetReasonType.findByDescription(data.rehabGoalsNotSetReason)
		}else{
			careActivity.therapyManagement.rehabGoalsNotSetReasonType = null;
		}
	}else{
	
		careActivity.therapyManagement.rehabGoalsNotSetReasonType = null;
	}*/
	
	
	
	public static boolean isBefore (Date date1, Integer time1, Date date2, Integer time2){
		 if(date1 && date2){
			 if(date1.before(date2)){
				 return true
			 }
			 if(date1 == date2){
				 if(time1 != null  && time2 != null){
					 if(time1 < time2){
						 return true
					 }
				 }
			 }
		 }
		 return false		
	}
	
	public static boolean isAfter (Date date1, Integer time1, Date date2, Integer time2){
		//println("in isAfter ${date1} : ${date2} : ${time1} : ${time2}")
		if(date1 && date2){
			if(date1.after(date2)){
				//println "date decision"
				return true
			}
			if(date1 == date2){
				if(time1 != null  && time2 != null){
					if(time1 > time2){
						//println "time decision"
						return true
					}
				}
			}
		}
		return false
   }

}
