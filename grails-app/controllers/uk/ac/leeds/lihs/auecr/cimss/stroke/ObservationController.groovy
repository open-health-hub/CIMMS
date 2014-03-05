package uk.ac.leeds.lihs.auecr.cimss.stroke

import grails.converters.JSON;

import java.util.List;

import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.ObservationType;

class ObservationController extends StrokeBaseController{

     def getObservations = {
		def careActivity = CareActivity.get(params.id)
		renderObservations(careActivity);
	}
	 
	 
	 
	 def updateObservation = { data, observation->
		 observation.dateMade = DisplayUtils.getDate(data.dateMade)
		 observation.timeMade = DisplayUtils.getTime(data.timeMade)
		 if(data.type){
			 observation.type = ObservationType?.findByDescription(data.type)
		 }else{
			 observation.type = null
		 }
		 observation.value = data.theValue
	 }
	 
	 
	 def updateObservations = {
		 def careActivity = CareActivity.get(params.id)
		 def data = request.JSON.Observations 
		 
		 for( observation in data){
			if(ControllerHelper.getIntegerFromString(observation.id)){
				 if(observation._destroy){
					 def theObservation = Observation.get(observation.id)
					 careActivity.removeFromObservations(theObservation)
					 theObservation.delete()
				 }else{
					 updateObservation(observation,Observation.get(observation.id))
				 }
			}else{
				if(!observation._destroy){
				
					if(	observation.dateMade
						 || observation.timeMade
						 || observation.type
						 || observation.theValue
						 ){
						 def theObservation = new Observation()
						 updateObservation(observation,theObservation)
						 careActivity.addToObservations(theObservation)
						 }
					 
					 
				}
			}
		 }
		
		 for (observation in careActivity.observations){
		 
		 }
		 
		 
		 if(careActivity.validate()){
			 careActivity.save(flush:true)
			 renderObservations(careActivity);
		 }else{
			 renderObservations(careActivity);
		 }
	 }
	 
	 
	 def renderObservations = {careActivity ->	
		 
		 def allTypes = ObservationType.getAll()
		 
		 def observationTypes = []
		 allTypes.each { type ->
			 observationTypes << [id:type.id, description:type.description]
		 }
	
		 
		 
		 def observations = []
		 
		
		 
		 careActivity.observations?.sort {it?.dateMade}?.reverse().each{ observation ->
			observations << [id:observation.id
								,dateMade:DisplayUtils.displayDate(observation.dateMade)
								,timeMade:DisplayUtils.displayTime(observation.timeMade)
								,theValue:observation?.value
								,type:observation?.type?.description] 
			 
		 }
		 
		 
		 
		 
		 def result = [Observations:observations
			 			, ObservationTypes:observationTypes
			 			, FieldsInError: getFieldsInError(careActivity)
						, ErrorsAsList:getErrorsAsList(careActivity)
						, InfoMessage:getInfoMessage(careActivity)]
		 render result as JSON
	 }
	 

	
}
