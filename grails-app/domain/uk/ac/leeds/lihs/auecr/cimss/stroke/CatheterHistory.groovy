package uk.ac.leeds.lihs.auecr.cimss.stroke

import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.CatheterReasonType;

class CatheterHistory {
	
	Date insertDate
	Integer insertTime
	Date removalDate
	Integer removalTime
	CatheterReasonType reason
	String reasonOther 
	
	static belongsTo = [continenceManagement:ContinenceManagement]

	static auditable = [ignore:[]]
	

    static constraints = {
		insertDate(nullable:true
				,validator:{insertDate, catheter ->
					if(insertDate > new Date()){
						return "catheter.insert.date.not.in.future"
					}
					if(catheter.continenceManagement.careActivity.afterDischarge(insertDate, catheter.insertTime)){
						return "catheter.insert.date.not.after.discharge"
					}
					if(catheter.continenceManagement.careActivity.beforeAdmission(insertDate, catheter.insertTime)){
						return "catheter.insert.date.not.before.admission"
					}
				})
		
		insertTime(nullable:true
			,validator:{insertTime, catheter ->				
				if( !catheter.insertDate){
					if(insertTime != null){
						return "catheter.insert.time.without.date"
					}
				}else{
					if( catheter.insertDate == DateTimeHelper.getCurrentDateAtMidnight()){
						if(insertTime > DateTimeHelper.getCurrentTimeAsMinutes() ){
							return "catheter.insert.time.not.in.future"
						}
					}
				}
		})
		
		
		reasonOther(nullable:true)
		
		
		removalDate(nullable:true
			,validator:{removalDate, catheter ->
				
				if(removalDate && catheter.insertDate){
					if(DateTimeHelper.isAfter(catheter.insertDate, catheter.insertTime, removalDate, catheter.removalTime)){
						return "catheter.removal.before.insert"
					}
				}
				if(removalDate > new Date()){
					return "catheter.removal.date.not.in.future"
				}
				if(catheter.continenceManagement.careActivity.afterDischarge(removalDate, catheter.removalTime)){
					return "catheter.removal.date.not.after.discharge"
				}
				if(catheter.continenceManagement.careActivity.beforeAdmission(removalDate, catheter.removalTime)){
					return "catheter.removal.date.not.before.admission"
				}
			})
		removalTime(nullable:true
			,validator:{removalTime, catheter ->
				if( !catheter.removalDate){
					if(removalTime != null){
						return "catheter.removal.time.without.date"
					}
				}else{
					if( catheter.removalDate == DateTimeHelper.getCurrentDateAtMidnight()){
						if(removalTime > DateTimeHelper.getCurrentTimeAsMinutes() ){
							return "catheter.removal.time.not.in.future"
						}
					}
				}
		})
    }
}
