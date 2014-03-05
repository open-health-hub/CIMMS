package uk.ac.leeds.lihs.auecr.cimss.access

class Role {

	String authority
	
	static mapping = {
		cache true
		table "cimss_role"
	}

	static constraints = {
		authority blank: false, unique: true
	}
}
