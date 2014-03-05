package uk.ac.leeds.lihs.auecr.cimss.stroke

import grails.converters.JSON
import uk.ac.leeds.lihs.auecr.cimss.access.UserRole

class TherapistMarshaller {
	void register() {
		
		JSON.registerObjectMarshaller( UserRole ) { UserRole therapist ->
			return [
				firstName		: therapist.user.firstName,
				surname			: therapist.user.surname,
				therapistNumber	: therapist.user.id
			]
		}
	}
}
