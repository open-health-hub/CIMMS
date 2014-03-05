package uk.ac.leeds.lihs.auecr.cimss.stroke.therapy.assessment

import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.PathwayStage;

import uk.ac.leeds.lihs.auecr.cimss.stroke.therapy.AssessmentManagement

class Barthel {
	
	
	PathwayStage pathwayStage
	
	
	Integer manualTotal
	Integer bowels
	Integer bladder
	Integer grooming
	Integer toilet
	Integer feeding
	Integer transfer
	Integer mobility
	Integer dressing
	Integer stairs
	Integer bathing 
	

	
	static belongsTo = [assessmentManagement:AssessmentManagement]

	static auditable = [ignore:[]]
	

    static constraints = {
		 pathwayStage(nullable:true)
		 manualTotal(nullable:true
			 ,range:0..20)
		 bowels(nullable:true
			 ,validator:{bowels, barthel ->
				if(bowels == null && barthel.hasDetail() ){
					return "barthel.assessment.bowels.missing"
				}
			 })
		 bladder(nullable:true
			 ,validator:{bladder, barthel ->
				if(bladder == null && barthel.hasDetail() ){
					return "barthel.assessment.bladder.missing"
				}
			 })
		 grooming(nullable:true
			 ,validator:{grooming, barthel ->
				if(grooming == null && barthel.hasDetail() ){
					return "barthel.assessment.grooming.missing"
				}
			 })
		 toilet(nullable:true
			 ,validator:{toilet, barthel ->
				if(toilet == null && barthel.hasDetail() ){
					return "barthel.assessment.toilet.missing"
				}
			 })
		 feeding(nullable:true
			 ,validator:{feeding, barthel ->
				if(feeding == null && barthel.hasDetail() ){
					return "barthel.assessment.feeding.missing"
				}
			 })
		 transfer(nullable:true
			 ,validator:{transfer, barthel ->
				if(transfer == null && barthel.hasDetail() ){
					return "barthel.assessment.transfer.missing"
				}
			 })
		 mobility(nullable:true
			 ,validator:{mobility, barthel ->
				if(mobility == null && barthel.hasDetail() ){
					return "barthel.assessment.mobility.missing"
				}
			 })
		 dressing(nullable:true
			 ,validator:{dressing, barthel ->
				if(dressing == null && barthel.hasDetail() ){
					return "barthel.assessment.dressing.missing"
				}
			 })
		 stairs(nullable:true
			 ,validator:{stairs, barthel ->
				if(stairs == null && barthel.hasDetail() ){
					return "barthel.assessment.stairs.missing"
				}
			 })
		 bathing(nullable:true
			 ,validator:{bathing, barthel ->
				if(bathing == null && barthel.hasDetail() ){
					return "barthel.assessment.bathing.missing"
				}
			 })
    }
	
	def score = {
		if(hasDetail()){
			return 	bowels +  bladder +  grooming  +  toilet + feeding + transfer   +  mobility   +  dressing  +  stairs  + bathing  
		}else{
			return manualTotal
		}
	}
	
	def hasDetail = {
		return 	bowels >=0  || bladder >=0|| grooming >=0 || toilet >=0|| feeding  >=0	|| transfer  >=0 || mobility  >=0 || dressing >=0 || stairs  >=0|| bathing  >=0
	}
	
	def isComplete(){
		return 	bowels && bladder && grooming && toilet && feeding 	&& transfer && mobility && dressing && stairs && bathing
	}
	
}
