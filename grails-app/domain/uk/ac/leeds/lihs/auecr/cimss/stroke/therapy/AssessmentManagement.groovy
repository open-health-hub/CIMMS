package uk.ac.leeds.lihs.auecr.cimss.stroke.therapy

import uk.ac.leeds.lihs.auecr.cimss.stroke.TherapyManagement
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.PathwayStage;
import uk.ac.leeds.lihs.auecr.cimss.stroke.therapy.assessment.Barthel
import uk.ac.leeds.lihs.auecr.cimss.stroke.therapy.assessment.ModifiedRankin

class AssessmentManagement {

	Boolean preAdmissionBarthelNotKnown
	Boolean preAdmissionModifiedRankinNotKnown = false
    Boolean baselineBarthelNotKnown
	Boolean baselineModifiedRankinNotKnown	
	Boolean dischargeBarthelNotKnown = false
	Boolean dischargeModifiedRankinNotKnown
	
	List<Barthel> barthelAssessments
	List<ModifiedRankin> modifiedRankinAssessments
	
	static hasMany = [ barthelAssessments : Barthel , modifiedRankinAssessments : ModifiedRankin ]
	
	static auditable = [ignore:[]]
	static belongsTo = [therapyManagement:TherapyManagement]

    static constraints = {
		preAdmissionBarthelNotKnown(nullable:true)
		preAdmissionModifiedRankinNotKnown(nullable:true)
		baselineBarthelNotKnown(nullable:true)
		baselineModifiedRankinNotKnown(nullable:true)
		dischargeBarthelNotKnown(nullable:true)
		dischargeModifiedRankinNotKnown(nullable:true)
    }
	

	
	public Barthel addBarthelByStage(String stage){
		if(!findBarthelByStage(stage)){
			if(barthelAssessments){
				barthelAssessments.add(new Barthel(pathwayStage:PathwayStage.findByDescription(stage)))
			}else{
				barthelAssessments = [new Barthel(pathwayStage:PathwayStage.findByDescription(stage))]
			}
			return findBarthelByStage(stage)
		}else{
			return findBarthelByStage(stage)
		}
	}
	
	public Barthel findBarthelByStage(String stage){		
		if(barthelAssessments){
			return barthelAssessments.find {it?.pathwayStage?.description == stage}
		}
		return null
	}
	
	public void deleteBarthelByStage(String stage){
		if(findBarthelByStage(stage)){
			barthelAssessments.remove(findBarthelByStage(stage))
		}
	}
	
		
	
	public ModifiedRankin addModifiedRankinByStage(String stage){		
		if(!findModifiedRankinByStage(stage)){
			if(modifiedRankinAssessments){
				modifiedRankinAssessments.add(new ModifiedRankin(pathwayStage:PathwayStage.findByDescription(stage)))
			}else{
				modifiedRankinAssessments = [new ModifiedRankin(pathwayStage:PathwayStage.findByDescription(stage))]
			}
			return findModifiedRankinByStage(stage)
		}else{
			return findModifiedRankinByStage(stage)
		}		
	}
	
	
	
	public ModifiedRankin  findModifiedRankinByStage(String stage){
		if(modifiedRankinAssessments){
			return modifiedRankinAssessments.find {it?.pathwayStage?.description == stage}
		}
		return null
	}
	
	public void deleteModifiedRankinByStage(String stage){
		if(findModifiedRankinByStage(stage)){
			modifiedRankinAssessments.remove(findModifiedRankinByStage(stage))
		}
	}
	
	
	
}
