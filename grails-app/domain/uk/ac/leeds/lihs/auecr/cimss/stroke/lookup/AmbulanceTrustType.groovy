package uk.ac.leeds.lihs.auecr.cimss.stroke.lookup

class AmbulanceTrustType {

	String description
	String code
	
    static constraints = {
    }
	
	String toString(){
		return "${description}"
	}
}
