package uk.ac.leeds.lihs.auecr.cimss.reporting.ssnap

class SsnapUploadTransaction {

	Date txDate
	Integer txType
	Boolean succeeded
	String diagnosticMesg

	static belongsTo = [report:SsnapReport]
	
    static constraints = {
		txDate(nullable:false)
		txType(nullable:false)
		succeeded(nullable:false)
		diagnosticMesg(nullable:true)
    }
}
