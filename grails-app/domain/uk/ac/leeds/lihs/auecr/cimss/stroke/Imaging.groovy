package uk.ac.leeds.lihs.auecr.cimss.stroke

import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.NoScanReasonType;

class Imaging {
	
	String scanPostStroke
	NoScanReasonType noScanReason	
	Scan scan
	
	static belongsTo = [careActivity:CareActivity]
	static auditable = [ignore:[]]

    static constraints = {
		noScanReason(nullable:true
			,validator:{noScanReason, imaging ->
				if(imaging?.scanPostStroke=='no'){
					if(!noScanReason){
						return "no.scan.reason.required"
					}
				}
				
			})
		scan(nullable:true)
    }
}
