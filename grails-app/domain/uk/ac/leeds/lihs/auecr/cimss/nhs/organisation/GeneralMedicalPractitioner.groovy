package uk.ac.leeds.lihs.auecr.cimss.nhs.organisation

class GeneralMedicalPractitioner {
	
	String code
	String name
	String practiceCode
	
	
    static constraints = {
    }
	
	static mapping = {
		table "general_medical_practitioner"
		version false
		id name:"code", generator:"assigned"
		code column: "code"
		name column:"name"
		practiceCode column:"practiceCode"
	
	}
}
