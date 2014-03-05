package uk.ac.leeds.lihs.auecr.cimss.stroke

import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.AmbulanceTrustType

class Arrival {

	Boolean transferredFromAnotherHospital = Boolean.FALSE

    Date thisHospitalArrivalDate
    Integer thisHospitalArrivalTime
	
	Boolean arriveByAmbulance = Boolean.FALSE
	AmbulanceTrustType ambulanceTrust
	String cadNumber
	Boolean cadNumberUnknown
	Boolean outcomeQuestionnairOptOut = Boolean.FALSE
	
	static belongsTo = [medicalHistory:MedicalHistory]
	
	static auditable = [ignore:[]]
	
    static constraints = {
		ambulanceTrust(nullable:true)
        transferredFromAnotherHospital(nullable:true)
        thisHospitalArrivalDate(nullable:true)
        thisHospitalArrivalTime(nullable:true)		
		arriveByAmbulance(nullable:true)
		cadNumber(nullable:true,size:4..10,matches:"[0-9]+",
		validator: { 
			cadNumber, obj ->
			validateCadNumber(cadNumber, obj.cadNumberUnknown)
		})
		cadNumberUnknown(nullable:true,
			validator: {
				cadNumberUnknown, obj ->
				validateCadNumber(obj.cadNumber, cadNumberUnknown)
			})
		outcomeQuestionnairOptOut(nullable:true)
		}
	
	private static validateCadNumber(cadNumber, cadNumberUnknown) {
		if (cadNumber != null && cadNumberUnknown == Boolean.TRUE) {
			return "cadNumberUnknown.true.when.cadNumber.set"
		}
	}
}
