package uk.ac.leeds.lihs.auecr.cimss.stroke

import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.StaffRole;

class Evaluation {

	StaffRole evaluator
	
	Date dateEvaluated
	Integer timeEvaluated
	
	static auditable = [ignore:[]]
	
	static belongsTo = [careActivity:CareActivity]
	
	
    static constraints = {
		dateEvaluated(validator:{dateEvaluated, evaluation ->
				if(dateEvaluated && dateEvaluated.after(new Date())){
					return "evaluation.date.not.in.future"
				}
				/*if(dateEvaluated && evaluation.careActivity.beforeAdmission(dateEvaluated, evaluation.timeEvaluated)){
					return "evaluation.date.not.before.admission"
				}*/
				if(dateEvaluated &&  evaluation.careActivity.afterDischarge(dateEvaluated, evaluation.timeEvaluated)){
					return "evaluation.date.not.after.discharge"
				}
				
			})
		
		timeEvaluated(validator:{timeEvaluated, evaluation ->
			if( !evaluation.dateEvaluated){
				if(timeEvaluated){
					return "evaluation.time.without.date"
				}
			}else{
				if( timeEvaluated && evaluation.dateEvaluated == DateTimeHelper.getCurrentDateAtMidnight()){
					if(timeEvaluated > DateTimeHelper.getCurrentTimeAsMinutes() ){
						return "evaluation.time.not.in.future"
					}
				}
			}
			
			
		})
		
    }
}
