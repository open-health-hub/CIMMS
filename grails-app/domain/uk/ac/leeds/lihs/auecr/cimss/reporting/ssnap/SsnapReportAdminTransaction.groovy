package uk.ac.leeds.lihs.auecr.cimss.reporting.ssnap

class SsnapReportAdminTransaction {
	
	Integer reportVersion
	String action
	String administrator

	static belongsTo = {
		report:SsnapReport
	}
	
	static mapping = {
		table 'ssnap_report_admin_tx'
	}
	
    static constraints = {
		reportVersion (nullable:false)
		action (nullable:false)
		administrator (nullable:false)
    }
}
