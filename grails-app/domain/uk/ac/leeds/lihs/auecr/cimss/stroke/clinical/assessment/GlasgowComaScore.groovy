package uk.ac.leeds.lihs.auecr.cimss.stroke.clinical.assessment


import uk.ac.leeds.lihs.auecr.cimss.stroke.ClinicalAssessment
import uk.ac.leeds.lihs.auecr.cimss.stroke.DateTimeHelper;

class GlasgowComaScore {
	
	Date dateAssessed
	Integer timeAssessed
	Integer motorScore
	Integer eyeScore
	Integer verbalScore
	
	static belongsTo = [clinicalAssessment:ClinicalAssessment]
	static auditable = [ignore:[]]
    static constraints = {
		dateAssessed(nullable:false
			,validator:{dateAssessed, glasgowComaScore ->
				if(dateAssessed && dateAssessed.after(new Date())){
					return "glasgow.coma.score.date.not.in.future"
				}
				/*if(glasgowComaScore.clinicalAssessment.careActivity.beforeAdmission(dateAssessed, glasgowComaScore.timeAssessed)){
					return "glasgow.coma.score.date.not.before.admission"
				}*/
				if(glasgowComaScore.clinicalAssessment.careActivity.afterDischarge(dateAssessed, glasgowComaScore.timeAssessed)){
					return "glasgow.coma.score.date.not.after.discharge"
				}
				
			})
		timeAssessed(nullable:false
			,validator:{timeAssessed, glasgowComaScore ->
				if( !glasgowComaScore.dateAssessed){
					if(timeAssessed){
						return "glasgow.coma.score.time.without.date"
					}
				}else{
					if( glasgowComaScore.dateAssessed == DateTimeHelper.getCurrentDateAtMidnight()){
						if(timeAssessed > DateTimeHelper.getCurrentTimeAsMinutes() ){
							return "glasgow.coma.score.time.not.in.future"
						}
					}
				}
				
				
			})
		motorScore(nullable:true, max:6
			,validator:{motorScore, glasgowComaScore ->
				if(!motorScore && ( glasgowComaScore.eyeScore || glasgowComaScore.verbalScore)){
					return "incomplete.glasgow.coma.score.motor"
				}
			}
		)
		eyeScore(nullable:true, max:4
			,validator:{eyeScore, glasgowComaScore ->
				if(!eyeScore && ( glasgowComaScore.motorScore || glasgowComaScore.verbalScore)){
					return "incomplete.glasgow.coma.score.eye"
				}
			}
		)
		verbalScore(nullable:true, max:5
			,validator:{verbalScore, glasgowComaScore ->
				if(!verbalScore && ( glasgowComaScore.eyeScore || glasgowComaScore.motorScore)){
					return "incomplete.glasgow.coma.score.verbal"
				}
			}
		)	
    }
}
