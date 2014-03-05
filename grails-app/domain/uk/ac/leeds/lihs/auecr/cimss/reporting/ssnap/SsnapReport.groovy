package uk.ac.leeds.lihs.auecr.cimss.reporting.ssnap

class SsnapReport {

	String reportType;
	Long version;
	Date creationDate;
	Boolean sentToSsnap;
	String ssnapUploadStatus;
	Date ssnapUploadTime;	
	Boolean allowOverwrite;
	String reportName;
	Date startDate
	Date endDate
	
	static hasMany = [records:SsnapReportRecord]
	
    static constraints = {
		reportType(nullable:false, validator: {reportType, ssnapReport ->
			
				if (reportType!='SSNAP72' && reportType != 'SSNAP') {
					return "unknown.report.type"
				}
			}
		)
		ssnapUploadTime(nullable:true)
		reportName(nullable:true)
    }
}
