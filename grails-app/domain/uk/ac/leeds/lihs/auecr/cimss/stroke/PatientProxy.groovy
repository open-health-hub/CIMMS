package uk.ac.leeds.lihs.auecr.cimss.stroke

class PatientProxy {
	
	String hospitalNumber;
	String nhsNumber;
	String districtNumber;
	
	String surname;
	String forename;
	String gender;
	String ethnicity;
	String postcode;
	Date dateOfBirth;
	Date dateOfDeath;
	
	List careActivities;
	
	static hasMany = [
		careActivities:CareActivity]
	
	static auditable = [ignore:[]]

    static constraints = {
		hospitalNumber(nullable:true)	
		nhsNumber(nullable:true)
		districtNumber(nullable:true)
		surname(nullable:true)
		forename(nullable:true)
		gender(nullable:true)
		ethnicity(nullable:true)
		postcode(nullable:true, size: 4..8, blank: true)
		dateOfBirth(nullable:true)
		dateOfDeath(nullable:true)
    }
	
	public CareActivity getCareActivity(Date startDate){
		def result = null
		careActivities.each{it ->
			if(it.startDate == startDate){
				result = it
			}	
		}
		
		return result
	}
	
	 boolean isValid(mesgs) {		 
		 checkValid("surname",surname,mesgs)
		 checkValid("forename",forename,mesgs)
		 checkValid("gender",gender,mesgs)
		 checkValid("ethnicity",ethnicity,mesgs)
		 checkValid("postcode",postcode,mesgs)
		 checkValid("dateOfBirth",dateOfBirth,mesgs)
//		 checkValid("dateOfDeath",dateOfDeath,mesgs)
	 }
	 
	 boolean checkValid(paramName, param, mesgs) {
		 if ( param == null || param == "" ) {
			 mesgs.push("valid '${paramName}' parameter must be supplied")
			 return false
		 }
		 return true
	 }	 
}
