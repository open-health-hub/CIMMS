package uk.ac.leeds.lihs.auecr.cimss.stroke

class PatientLifeStyle {
	
	String livedAlonePreAdmission
	
	static belongsTo = [careActivity:CareActivity]
	
	static auditable = [ignore:[]]

    static constraints = {
		livedAlonePreAdmission(nullable:true)
    }
}
