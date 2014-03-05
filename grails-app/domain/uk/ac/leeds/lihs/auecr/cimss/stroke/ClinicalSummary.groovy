package uk.ac.leeds.lihs.auecr.cimss.stroke

import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.LevelOfConsciousness;

class ClinicalSummary {

	LevelOfConsciousness worstLevelOfConsciousness
	String urinaryTractInfection
	String newPneumonia
	
	String palliativeCare
	Date palliativeCareDate
	String endOfLifePathway
	
	
	
	static belongsTo = [careActivity:CareActivity]
	static auditable = [ignore:[]]
	
    static constraints = {
		worstLevelOfConsciousness(nullable:true)
		urinaryTractInfection(nullable:true)
		newPneumonia(nullable:true)
		
		palliativeCare(nullable:true)
		palliativeCareDate(nullable:true,
			validator:{palliativeCareDate, clinicalSummary ->
				if(palliativeCareDate){
					if(palliativeCareDate.after(new Date())){
						return "palliative.care.date.not.in.future"
					}
					if(clinicalSummary.careActivity.beforeAdmission(palliativeCareDate, null)){
						return "palliative.care.date.not.before.admission"
					}
					if(clinicalSummary.careActivity.afterDischarge(palliativeCareDate, null)){
						return "palliative.care.date.not.after.discharge"
					}
					
				}
				
			})
		
		
		
		
		endOfLifePathway(nullable:true)
		
    }
}
 