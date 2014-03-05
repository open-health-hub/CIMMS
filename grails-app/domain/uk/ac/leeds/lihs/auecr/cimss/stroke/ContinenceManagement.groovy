package uk.ac.leeds.lihs.auecr.cimss.stroke

import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.NoContinencePlanReasonType;

class ContinenceManagement {
	
	Boolean newlyIncontinent;
	Boolean hasContinencePlan;
	Boolean inappropriateForContinencePlan
	Date continencePlanDate;
	Integer continencePlanTime;
	NoContinencePlanReasonType noContinencePlanReason;
	String noContinencePlanReasonOther;
	Boolean priorCatheter;
	
	Boolean catheterisedSinceAdmission;
	
	static auditable = [ignore:[]]
	
	static belongsTo = [careActivity:CareActivity]
	
	static hasMany = [catheterEvents:CatheterHistory]
	
    static constraints = {
		
		inappropriateForContinencePlan(nullable:true)
		hasContinencePlan(nullable:true
			,validator:{hasContinencePlan, continenceManagement ->
				if(hasContinencePlan == null 
						&& (continenceManagement.inappropriateForContinencePlan==null 
							|| continenceManagement.inappropriateForContinencePlan == Boolean.FALSE) ){
					return "continence.plan.missing"
				}
			}
		)
		continencePlanDate(nullable:true
			,validator:{continencePlanDate, continenceManagement ->
				if(continenceManagement?.hasContinencePlan == Boolean.TRUE){
					if(!continencePlanDate){
						return "continence.plan.yes.date.missing"
					}
					if(continencePlanDate > new Date()){
						return "continence.plan.date.not.in.future"
					}
					if(continenceManagement.careActivity.afterDischarge(continencePlanDate, continenceManagement.continencePlanTime)){
						return "continence.plan..date.not.after.discharge"
					}
					if(continenceManagement.careActivity.beforeAdmission(continencePlanDate, continenceManagement.continencePlanTime)){
						return "continence.plan..date.not.before.admission"
					}
					
				}
		})
		continencePlanTime(nullable:true)
			
		noContinencePlanReason(nullable:true
			,validator:{noContinencePlanReason, continenceManagement ->
				if(continenceManagement?.hasContinencePlan == Boolean.FALSE){
					if(!noContinencePlanReason){
						return "continence.no.plan.reason.missing"
					}
					
				}
		})
		noContinencePlanReasonOther(nullable:true
			,validator:{noContinencePlanReasonOther, continenceManagement ->
				if(continenceManagement?.noContinencePlanReason?.description == "other"){
					if(!noContinencePlanReasonOther){
						return "continence.no.plan.reason.other.missing"
					}
				}
		})
		catheterisedSinceAdmission(
			validator:{catheterisedSinceAdmission, continenceManagement ->
				if(catheterisedSinceAdmission == Boolean.TRUE){
					if(!continenceManagement.catheterEvents || continenceManagement?.catheterEvents.size()==0){
						return "continence.catheterised.since.admission.requires.at.least.one.catheter.history"
					}
					
				}
		})
		
    }
	
	def  findCatheterFromHistoryById = {id ->
		def  result = null;
		for( catheter in catheterEvents){
			if(catheter.id == id){
				result = catheter
			}
		}
		return result;
	}
	
	List getCatheterEventsMostRecentFirst(){
		return catheterEvents?.sort ({a,b -> 
									
				if(a?.insertDate == null && b.insertDate ==null){
					 return 0
				}
				if(a?.insertDate == null) return -1
				if(b?.insertDate == null) return 1
				if(a?.insertDate == b.insertDate){
					if(a?.insertTime == null && b.insertTime ==null){
					 return 0
					}
					if(a?.insertTime == null) return -1
					if(b?.insertTime == null) return 1
					if(a?.insertTime==b?.insertTime) return 0
					if(a?.insertTime<b?.insertTime){
						return -1
					}else{
						return 1
					}
				}
				
				if(a?.insertDate.before(b?.insertDate)) return -1
				return 1 }as Comparator)?.reverse();
								
								
		
		
	}
	
	
	String continencePlanTimeAsString (){
		if(continencePlanTime){
			def minutes = continencePlanTime % 60
			def hours = (continencePlanTime - minutes)/60
			return "${hours}:${minutes}"
		}
	}
	
	def parseContinencePlanTime = {timeString ->
		continencePlanTime = parseTime (timeString)
	}
	
	
	private def parseTime = {timeString ->
		def hours = timeString.tokenize(':')[0]
		def minutes = timeString.tokenize(':')[1]
		return (hours.toInteger() * 60) + minutes.toInteger()
	}
	
}
