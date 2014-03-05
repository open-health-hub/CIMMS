package uk.ac.leeds.lihs.auecr.cimss.access

class RequestMap {

	String url
	String configAttribute

	static mapping = {
		cache true
	}

	static constraints = {
		url blank: false, unique: true
		configAttribute blank: false
	}
}
