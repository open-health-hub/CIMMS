package uk.ac.leeds.lihs.auecr.cimss.stroke.lookup

class TreatmentType {
	
	
	String description
	Boolean compulsory = Boolean.FALSE	

    static constraints = {
    }
	
	String toString(){
		return "${description}"
	}
}
