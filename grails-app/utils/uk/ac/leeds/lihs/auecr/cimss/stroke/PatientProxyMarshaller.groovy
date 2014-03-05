package uk.ac.leeds.lihs.auecr.cimss.stroke

import grails.converters.JSON


class PatientProxyMarshaller {

	void register() {
		
		JSON.registerObjectMarshaller( PatientProxy) { PatientProxy patient ->
			CareActivity careActivity = patient.careActivities[0];
			return [
				firstName		: patient.getForename(),
				surname			: patient.getSurname(),
				hospitalNumber	: patient.getHospitalNumber(),
				dob				: patient.dateOfBirth,
				admissionDate	: careActivity.getEffectiveStartDate(), 
				address			: patient.getPostcode(),
				id				: patient.id,
				careActivityId	: careActivity.id
			]
		}
	}
}

