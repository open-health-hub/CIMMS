package uk.ac.leeds.lihs.auecr.cimss.stroke

import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.TreatmentType;

class Treatment {
	
	TreatmentType type
	Date startDate
	Integer startTime
	Date endDate
	Integer endTime
	
	static belongsTo = [careActivity:CareActivity]
	static auditable = [ignore:[]]
	
    static constraints = {
		startDate(nullable:true
				,validator:{startDate, treatment ->
					if(startDate > new Date()){
						return "treatment.start.date.not.in.future"
					}
					if(treatment.careActivity.afterDischarge(startDate, treatment.startTime)){
						return "treatment.start.date.not.after.discharge"
					}
					/*if(treatment.careActivity.beforeAdmission(startDate, treatment.startTime)){
						return "treatment.start.date.not.before.admission"
					}*/
				})
		
		startTime(nullable:true
			,validator:{startTime, treatment ->				
				if( !treatment.startDate){
					if(startTime){
						return "treatment.start.time.without.date"
					}
				}else{
					if( treatment.startDate == DateTimeHelper.getCurrentDateAtMidnight()){
						if(startTime > DateTimeHelper.getCurrentTimeAsMinutes() ){
							return "treatment.start.time.not.in.future"
						}
					}
				}
		})
		endDate(nullable:true
			,validator:{endDate, treatment ->
				
				if(endDate && treatment.startDate){
					if(DateTimeHelper.isAfter(treatment.startDate, treatment.startTime, endDate, treatment.endTime)){
						return "treatment.end.before.start"
					}
				}
				if(endDate > new Date()){
					return "treatment.end.date.not.in.future"
				}
				if(treatment.careActivity.afterDischarge(endDate, treatment.endTime)){
					return "treatment.end.date.not.after.discharge"
				}
				if(treatment.careActivity.beforeAdmission(endDate, treatment.endTime)){
					return "treatment.end.date.not.before.admission"
				}
			})
		endTime(nullable:true
			,validator:{endTime, treatment ->
				if( !treatment.endDate){
					if(endTime){
						return "treatment.end.time.without.date"
					}
				}else{
					if( treatment.endDate == DateTimeHelper.getCurrentDateAtMidnight()){
						if(endTime > DateTimeHelper.getCurrentTimeAsMinutes() ){
							return "treatment.end.time.not.in.future"
						}
					}
				}
		})
    }
	
}
