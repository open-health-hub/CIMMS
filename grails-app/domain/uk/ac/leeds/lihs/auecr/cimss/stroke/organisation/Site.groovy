package uk.ac.leeds.lihs.auecr.cimss.stroke.organisation

class Site {

    String siteName
	
	static hasMany = [wards:Ward]

    static constraints = {
    }
	
	String toString(){
		"${siteName}"
	}
}
