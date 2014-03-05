package uk.ac.leeds.lihs.auecr.cimss.stroke.lookup

class GeneralPractitioner {
	
	String surname
	String name
	String practice
	String address
	String postcode
	
	

    static constraints = {
		practice(nullable:true)
		address(nullable:true)
		postcode(nullable:true)
    }
}
