package uk.ac.leeds.lihs.auecr.cimss.stroke

import java.util.Date;

import uk.ac.leeds.lihs.auecr.cimss.stroke.CareActivity;
import uk.ac.leeds.lihs.auecr.cimss.stroke.DateTimeHelper;

class PatientInfoService {

    static transactional = true

    def getPatientInfo(CareActivity careActivityInstance) {
		def patientInfo = new PatientInfo()
		
		patientInfo.careStart = DisplayUtils.displayDateTime(DateTimeHelper.computeDate(careActivityInstance.startDate,careActivityInstance.startTime))
		
		if ( careActivityInstance?.patient?.dateOfBirth ) {
			patientInfo.dob = DisplayUtils.displayDate(careActivityInstance?.patient?.dateOfBirth)
		}
		if ( careActivityInstance?.medicalHistory?.onsetDate   ) {
			def time = careActivityInstance?.medicalHistory?.onsetTime
			if ( time == null ) {
				time = 0
			}
			patientInfo.onset = DisplayUtils.displayDateTime(DateTimeHelper.computeDate(careActivityInstance?.medicalHistory?.onsetDate,time))
		} 
		if ( careActivityInstance?.medicalHistory?.inpatientAtOnset != null ) { 
			patientInfo.inpatientAtOnset = careActivityInstance?.medicalHistory?.inpatientAtOnset
		} 
		if ( careActivityInstance?.medicalHistory?.arrival?.thisHospitalArrivalDate && careActivityInstance?.medicalHistory?.arrival?.thisHospitalArrivalTime != null) {
			patientInfo.arrival = DisplayUtils.displayDateTime(DateTimeHelper.computeDate(careActivityInstance?.medicalHistory?.arrival?.thisHospitalArrivalDate, careActivityInstance?.medicalHistory?.arrival?.thisHospitalArrivalTime))
    	}
		return patientInfo
    }
}
