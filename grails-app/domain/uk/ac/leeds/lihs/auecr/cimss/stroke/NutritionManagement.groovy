package uk.ac.leeds.lihs.auecr.cimss.stroke

import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.InadequateNutritionReasonType

class NutritionManagement {
	
	Date dateScreened
	Integer timeScreened
	Boolean unableToScreen
	Integer mustScore
	Date dietitianReferralDate
	Integer dietitianReferralTime
	Boolean dietitianNotSeen
	
	
	String adequateAt24
	InadequateNutritionReasonType inadequateAt24NutritionReasonType
	String inadequateAt24ReasonOther
	String adequateAt48
	InadequateNutritionReasonType inadequateAt48NutritionReasonType
	String inadequateAt48ReasonOther
	String adequateAt72
	InadequateNutritionReasonType inadequateAt72NutritionReasonType
	String inadequateAt72ReasonOther
	
	static belongsTo = [careActivity:CareActivity]
	static auditable = [ignore:[]]
    static constraints = {
		dateScreened(nullable:true
			,validator:{dateScreened, nutritionManagement ->
				    if(nutritionManagement?.unableToScreen != Boolean.TRUE){
						if(!dateScreened){
							return "nutrition.screen.date.missing"
						}
						if(dateScreened > new Date()){
							return "nutrition.screen.date.not.in.future"
						}
						if(nutritionManagement.careActivity.afterDischarge(dateScreened, nutritionManagement.timeScreened)){
							return "nutrition.screen.date.not.after.discharge"
						}
						if(nutritionManagement.careActivity.beforeAdmission(dateScreened, nutritionManagement.timeScreened)){
							return "nutrition.screen.date.not.before.admission"
						}
					}
					
				
				
		})
		
		
		
		
		timeScreened(nullable:true
			,validator:{timeScreened, nutritionManagement ->
				if(nutritionManagement?.unableToScreen != Boolean.TRUE){
					if(timeScreened == null){
						return "nutrition.screen.time.missing"
					}
				
					if( !nutritionManagement.dateScreened){
						if(timeScreened){
							return "nutrition.screen.time.without.date"
						}
					}else{
						if( nutritionManagement.dateScreened == DateTimeHelper.getCurrentDateAtMidnight()){
							if(timeScreened > DateTimeHelper.getCurrentTimeAsMinutes() ){
								return "nutrition.screen.time.not.in.future"
							}
						}
					}
				}
				
		})
		
		
		mustScore(nullable:true
			,range:0..6
			,validator:{mustScore, nutritionManagement ->
				if(nutritionManagement?.unableToScreen != Boolean.TRUE){
					if(mustScore==null){
						return "nutrition.screen.must.score.missing"
					}
				}
				
		})
		
		
		dietitianReferralDate(nullable:true,
			,validator:{dietitianReferralDate, nutritionManagement ->
				if(dietitianReferralDate > new Date()){
					return "nutrition.dietitian.referral.date.not.in.future"
				}
				if(nutritionManagement.careActivity.afterDischarge(dietitianReferralDate, nutritionManagement.dietitianReferralTime)){
					return "nutrition.dietitian.referral.date.not.after.discharge"
				}
				if(nutritionManagement.careActivity.beforeAdmission(dietitianReferralDate, nutritionManagement.dietitianReferralTime)){
					return "nutrition.dietitian.referral.date.not.before.admission"
				}
				
				
				if(dietitianReferralDate && nutritionManagement.dateScreened){
					if(DateTimeHelper.isAfter(nutritionManagement.dateScreened
									, nutritionManagement.timeScreened, dietitianReferralDate, nutritionManagement.dietitianReferralTime)){
						return "nutrition.dietitian.referral.date.not.before.assessment"
					}
				}
				
				
		})
		dietitianReferralTime(nullable:true
			,validator:{dietitianReferralTime, nutritionManagement ->				
				if( !nutritionManagement.dietitianReferralDate){
					if(dietitianReferralTime){
						return "nutrition.dietitian.referral.time.without.date"
					}
				}else{
					if( nutritionManagement.dietitianReferralDate == DateTimeHelper.getCurrentDateAtMidnight()){
						if(dietitianReferralTime > DateTimeHelper.getCurrentTimeAsMinutes() ){
							return "nutrition.dietitian.referral.time.not.in.future"
						}
					}
				}
		})
		
		
		unableToScreen(nullable:true)
		dietitianNotSeen(nullable:true)
		adequateAt24(nullable:true
			,validator:{adequateAt24, nutritionManagement ->
				if(!adequateAt24){
					if(nutritionManagement.adequateAt48  || nutritionManagement.adequateAt72){
						return "adequate.24.must.be.completed.when.later.exist"
					}
				}
		})
		inadequateAt24NutritionReasonType(nullable:true
			,validator:{inadequateAt24NutritionReasonType, nutritionManagement ->
				if(nutritionManagement?.adequateAt24 == "no" ){
					if(inadequateAt24NutritionReasonType==null){
						return "inadequate.nutrition.at.24.reason.type.missing"
					}
				}
		})
		inadequateAt24ReasonOther(nullable:true
			,validator:{inadequateAt24ReasonOther, nutritionManagement ->
				if(nutritionManagement?.adequateAt24 == "no" ){
					if(!inadequateAt24ReasonOther && nutritionManagement?.inadequateAt24NutritionReasonType?.description=="Other"){
						return "inadequate.nutrition.at.24.reason.type.other.missing"
					}
					if(!inadequateAt24ReasonOther && nutritionManagement?.inadequateAt24NutritionReasonType?.description=="Nasogastric tube not used - other"){
						return "inadequate.nutrition.at.24.reason.type.not.nasogastric.tube.other.missing"
					}
				}
		})
		adequateAt48(nullable:true
			,validator:{adequateAt48, nutritionManagement ->
				if(!adequateAt48){
					if(nutritionManagement.adequateAt72){
						return "adequate.48.must.be.completed.when.later.exist"
					}
				}
		})
		inadequateAt48NutritionReasonType(nullable:true
			,validator:{inadequateAt48NutritionReasonType, nutritionManagement ->
				if(nutritionManagement?.adequateAt48 == "no" ){
					if(inadequateAt48NutritionReasonType==null){
						return "inadequate.nutrition.at.48.reason.type.missing"
					}
				}
		})
		inadequateAt48ReasonOther(nullable:true
			,validator:{inadequateAt48ReasonOther, nutritionManagement ->
				if(nutritionManagement?.adequateAt48 == "no" ){
					if(!inadequateAt48ReasonOther && nutritionManagement?.inadequateAt48NutritionReasonType?.description=="Other"){
						return "inadequate.nutrition.at.48.reason.type.other.missing"
					}
					if(!inadequateAt48ReasonOther && nutritionManagement?.inadequateAt48NutritionReasonType?.description=="Nasogastric tube not used - other"){
						return "inadequate.nutrition.at.48.reason.type.not.nasogastric.tube.other.missing"
					}
				}
		})
		adequateAt72(nullable:true)
		inadequateAt72NutritionReasonType(nullable:true
			,validator:{inadequateAt72NutritionReasonType, nutritionManagement ->
				if(nutritionManagement?.adequateAt72 == "no" ){
					if(inadequateAt72NutritionReasonType==null){
						return "inadequate.nutrition.at.72.reason.type.missing"
					}
				}
		})
		inadequateAt72ReasonOther(nullable:true
			,validator:{inadequateAt72ReasonOther, nutritionManagement ->
				if(nutritionManagement?.adequateAt72 == "no" ){
					if(!inadequateAt72ReasonOther && nutritionManagement?.inadequateAt72NutritionReasonType?.description=="Other"){
						return "inadequate.nutrition.at.72.reason.type.other.missing"
					}
					if(!inadequateAt72ReasonOther && nutritionManagement?.inadequateAt72NutritionReasonType?.description=="Nasogastric tube not used - other"){
						return "inadequate.nutrition.at.72.reason.type.not.nasogastric.tube.other.missing"
					}
				}
		})
    }
}
