package uk.ac.leeds.lihs.auecr.cimss.stroke.therapy.assessment

import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.PathwayStage;

import uk.ac.leeds.lihs.auecr.cimss.stroke.therapy.AssessmentManagement

class ModifiedRankin {
	
	
	PathwayStage pathwayStage
	
	Integer modifiedRankinScore
	
	static belongsTo = [assessmentManagement:AssessmentManagement]
	static auditable = [ignore:[]]

    static constraints = {
		pathwayStage(nullable:true)
		modifiedRankinScore(nullable:true,range:0..6)
    }
}
