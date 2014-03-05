package uk.ac.leeds.lihs.auecr.cimss.stroke.medical.history

import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.RiskFactorType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.MedicalHistory

class RiskFactor {
	
	RiskFactorType type
	String value

	static belongsTo = [medicalHistory:MedicalHistory]
	
	
	static auditable = [ignore:[]]
	
    static constraints = {
    }
}
