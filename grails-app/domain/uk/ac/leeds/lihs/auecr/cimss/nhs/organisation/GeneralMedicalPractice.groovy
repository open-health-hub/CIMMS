package uk.ac.leeds.lihs.auecr.cimss.nhs.organisation

class GeneralMedicalPractice {
	
	String code
	String practiceName
	String addressLine1
	String addressLine2
	String addressLine3
	String addressLine4
	String addressLine5
	String postcode
	
	
	
	
    static constraints = {
    }
	
	static mapping = {
		table "general_medical_practice"
		version false
		id name:"code", generator:"assigned"
		code column: "code"
		practiceName column:"practiceName"
		addressLine1 column:"addressLine1"
		addressLine2 column:"addressLine2"
		addressLine3 column:"addressLine3"
		addressLine4 column:"addressLine4"
		addressLine5 column:"addressLine5"
		postcode column:"postcode"
		
		
	}
}
