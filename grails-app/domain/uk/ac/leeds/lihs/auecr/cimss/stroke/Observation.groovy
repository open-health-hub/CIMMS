package uk.ac.leeds.lihs.auecr.cimss.stroke

import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.ObservationType;

class Observation {
	
	Date dateMade
	Integer timeMade
	ObservationType type
	String 	value
	
	static belongsTo = [careActivity:CareActivity]
	
	static auditable = [ignore:[]]
    static constraints = {
		
		
		dateMade( validator:{dateMade, observation ->
			if(dateMade > new Date()){
				return "observation.start.date.not.in.future"
			}
			if(observation.careActivity.afterDischarge(dateMade, observation.timeMade)){
				return "observation.start.date.not.after.discharge"
			}
			if(observation.careActivity.beforeAdmission(dateMade, observation.timeMade)){
				return "observation.start.date.not.before.admission"
			}
		})
		
		timeMade( nullable:true
			,validator:{timeMade, observation ->
				if( !observation.dateMade){
					if(timeMade){
						return "observation.start.time.without.date"
					}
				}else{
					if( observation.dateMade == DateTimeHelper.getCurrentDateAtMidnight()){
						if(timeMade > DateTimeHelper.getCurrentTimeAsMinutes() ){
							return "observation.start.time.not.in.future"
						}
					}
				}
		})
			
    }
}
