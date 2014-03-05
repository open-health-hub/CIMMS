package uk.ac.leeds.lihs.auecr.cimss.stroke

import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.DiagnosisType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.DiagnosisCategoryType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.ImageType;

class Scan {
	
	Date requestDate
	Integer requestTime
	Date takenDate
	Integer takenTime
	Boolean takenOverride = Boolean.FALSE;
	ImageType imageType
	DiagnosisType diagnosisType 
	String diagnosisTypeOther
	DiagnosisCategoryType diagnosisCategory
	static belongsTo = [imaging:Imaging]
	
	static auditable = [ignore:[]]

    static constraints = {
		takenOverride(nullable:true)
		requestDate(nullable:true
			, validator:{requestDate, scan ->
				if(requestDate > new Date()){
					return "scan.request.date.not.in.future"
				}
				
				
				
				if(scan.imaging.careActivity.afterDischarge(requestDate, scan.requestTime)){
					return "scan.request.date.not.after.discharge"
				}
				/*if(scan.imaging.careActivity.beforeAdmission(requestDate, scan.requestTime)){
					return "scan.request.date.not.before.admission"
				}*/
			}
		)
		
		
		
		
		
		requestTime(nullable:true
			,validator:{requestTime, scan ->
				if(scan.requestDate && requestTime == null){
					return "scan.request.time.must.be.provided"
				}
				if( !scan.requestDate){
					if(requestTime){
						return "scan.request.time.without.date"
					}
				}else{
					if( scan.requestDate == DateTimeHelper.getCurrentDateAtMidnight()){
						if(requestTime > DateTimeHelper.getCurrentTimeAsMinutes() ){
							return "scan.request.time.not.in.future"
						}
					}
				}
		})
		takenDate(nullable:true
			, validator:{takenDate, scan ->
				if(takenDate && scan.requestDate){
					if(DateTimeHelper.isAfter(scan.requestDate, scan.requestTime, takenDate, scan.takenTime)){
						return "scan.taken.before.request"
					}
				}
				if(takenDate > new Date()){
					return "scan.taken.date.not.in.future"
				}
				if(scan.imaging.careActivity.afterDischarge(takenDate, scan.takenTime)){
					return "scan.taken.date.not.after.discharge"
				}
				/*if(scan.imaging.careActivity.beforeAdmission(takenDate, scan.takenTime)){
					return "scan.taken.date.not.before.admission"
				}*/
			}
		)
		takenTime(nullable:true
			,validator:{takenTime, scan ->
				if(scan.takenDate && takenTime == null){
					return "scan.taken.time.must.be.provided"
				}
				if( !scan.takenDate){
					if(takenTime){
						return "scan.taken.time.without.date"
					}
				}else{
					if( scan.takenDate == DateTimeHelper.getCurrentDateAtMidnight()){
						if(takenTime > DateTimeHelper.getCurrentTimeAsMinutes() ){
							return "scan.taken.time.not.in.future"
						}
					}
				}
		})
		imageType(nullable:true)
		diagnosisCategory(nullable:true)
		diagnosisType(nullable:true)
		diagnosisTypeOther(nullable:true
			,validator:{diagnosisTypeOther, scan ->
				if(scan?.diagnosisType?.description=='Other'){
					if(!diagnosisTypeOther){
						return "scan.diagnosis.type.other.required"
					}
				}
				
			})
		
    }
}
