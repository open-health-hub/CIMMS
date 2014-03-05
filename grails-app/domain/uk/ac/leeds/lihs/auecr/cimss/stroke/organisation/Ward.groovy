package uk.ac.leeds.lihs.auecr.cimss.stroke.organisation

class Ward {
	
	String wardName
	String wardNumber
	
	static belongsTo = [site:Site]
	
    static constraints = {
    }
}
