package uk.ac.leeds.lihs.auecr.cimss.stroke.medical.history

import uk.ac.leeds.lihs.auecr.cimss.stroke.MedicalHistory
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.MedicationType;

class Medication {

	MedicationType type
	String value
	static belongsTo = [medicalHistory:MedicalHistory]
	static auditable = [ignore:[]]
    static constraints = {
		value(nullable:true)
    }
}
