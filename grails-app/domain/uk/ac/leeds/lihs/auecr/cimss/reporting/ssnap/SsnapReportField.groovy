package uk.ac.leeds.lihs.auecr.cimss.reporting.ssnap

class SsnapReportField {
	
	String fieldName
	String fieldNumber
	String fieldValue
	
	static belongsTo = [record:SsnapReportRecord]
		
    static constraints = {
		fieldName(nullable:false)
		fieldNumber(nullable:false)
		fieldValue(nullable:true)
    }
	
	static mapping = {
		sort "fieldNumber"
		version false
	}
}
