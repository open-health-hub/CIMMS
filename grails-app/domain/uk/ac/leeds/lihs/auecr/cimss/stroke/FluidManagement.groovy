package uk.ac.leeds.lihs.auecr.cimss.stroke

import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.InadequateFluidReasonType;

class FluidManagement {
	
	String litrePlusAt24
	InadequateFluidReasonType inadequateAt24FluidReasonType
	String inadequateAt24ReasonOther
	String litrePlusAt48
	InadequateFluidReasonType inadequateAt48FluidReasonType
	String inadequateAt48ReasonOther
	String litrePlusAt72
	InadequateFluidReasonType inadequateAt72FluidReasonType
	String inadequateAt72ReasonOther
	
	
	static belongsTo = [careActivity:CareActivity]
	
	
	static auditable = [ignore:[]]

    static constraints = {
		
	
		
		litrePlusAt24(nullable:true
			,validator:{litrePlusAt24, fluidManagement ->
				if(!litrePlusAt24){
					if(fluidManagement.litrePlusAt48  || fluidManagement.litrePlusAt72){
						return "fluid.adequate.24.must.be.completed.when.later.exist"
					}
				}
		})
		inadequateAt24FluidReasonType(nullable:true
			,validator:{inadequateAt24FluidReasonType, fluidManagement ->
				if(fluidManagement?.litrePlusAt24 == "no" ){
					if(inadequateAt24FluidReasonType==null){
						return "inadequate.fluid.at.24.reason.type.missing"
					}
				}
		})
		inadequateAt24ReasonOther(nullable:true
			,validator:{inadequateAt24ReasonOther, fluidManagement ->
				if(fluidManagement?.litrePlusAt24 == "no" ){
					if(!inadequateAt24ReasonOther && fluidManagement?.inadequateAt24FluidReasonType?.description=="Other"){
						return "inadequate.fluid.at.24.reason.type.other.missing"
					}
					if(!inadequateAt24ReasonOther && fluidManagement?.inadequateAt24FluidReasonType?.description=="Nasogastric tube not used - other"){
						return "inadequate.fluid.at.24.reason.type.not.nasogastric.tube.other.missing"
					}
				}
		})
		litrePlusAt48(nullable:true
			,validator:{litrePlusAt48, fluidManagement ->
				if(!litrePlusAt48){
					if(fluidManagement.litrePlusAt72){
						return "fluid.adequate.48.must.be.completed.when.later.exist"
					}
				}
		})
		inadequateAt48FluidReasonType(nullable:true
			,validator:{inadequateAt48FluidReasonType, fluidManagement ->
				if(fluidManagement?.litrePlusAt48 == "no" ){
					if(inadequateAt48FluidReasonType==null){
						return "inadequate.fluid.at.48.reason.type.missing"
					}
				}
		})
		inadequateAt48ReasonOther(nullable:true
			,validator:{inadequateAt48ReasonOther, fluidManagement ->
				if(fluidManagement?.litrePlusAt48 == "no" ){
					if(!inadequateAt48ReasonOther && fluidManagement?.inadequateAt48FluidReasonType?.description=="Other"){
						return "inadequate.fluid.at.48.reason.type.other.missing"
					}
					if(!inadequateAt48ReasonOther && fluidManagement?.inadequateAt48FluidReasonType?.description=="Nasogastric tube not used - other"){
						return "inadequate.fluid.at.48.reason.type.not.nasogastric.tube.other.missing"
					}
				}
		})
		litrePlusAt72(nullable:true)
		inadequateAt72FluidReasonType(nullable:true
			,validator:{inadequateAt72FluidReasonType, fluidManagement ->
				if(fluidManagement?.litrePlusAt72 == "no" ){
					if(inadequateAt72FluidReasonType==null){
						return "inadequate.fluid.at.72.reason.type.missing"
					}
				}
		})
		inadequateAt72ReasonOther(nullable:true
			,validator:{inadequateAt72ReasonOther, fluidManagement ->
				if(fluidManagement?.litrePlusAt72 == "no" ){
					if(!inadequateAt72ReasonOther && fluidManagement?.inadequateAt72FluidReasonType?.description=="Other"){
						return "inadequate.fluid.at.72.reason.type.other.missing"
					}
					if(!inadequateAt72ReasonOther && fluidManagement?.inadequateAt72FluidReasonType?.description=="Nasogastric tube not used - other"){
						return "inadequate.fluid.at.72.reason.type.not.nasogastric.tube.other.missing"
					}
				}
		})
		
		
	}
	
	
	
}
