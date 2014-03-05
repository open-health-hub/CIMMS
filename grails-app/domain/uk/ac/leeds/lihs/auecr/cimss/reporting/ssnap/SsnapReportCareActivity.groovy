package uk.ac.leeds.lihs.auecr.cimss.reporting.ssnap

import java.util.Date;

class SsnapReportCareActivity {

	
	String hospitalStayId
	
	Date startDate
	Integer startTime
	Date endDate
	Integer endTime
	
    static constraints = {
    }
	
	static mapping = {
		table 'care_activity'
		version false
		cache usage:'read-only'		
//		id  name:'hospitalStayId' , type: 'string'
	}
	
	static hasOne = [reportDataSource:SsnapReportDataSource]
}
