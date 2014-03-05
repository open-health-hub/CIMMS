package uk.ac.leeds.lihs.auecr.cimss.stroke

import org.codehaus.groovy.grails.web.json.JSONObject;

import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.TreatmentType;

import grails.converters.JSON;

class TreatmentController extends StrokeBaseController{

	def getTreatmentsPage = {
		log.debug "in getTreatmentsPage"
		def careActivity = CareActivity.get(params.id)
		render(template:'/treatment/treatment',model:['careActivityInstance':careActivity])
	}

	def getTreatments = {
		log.debug "in getTreatments"
		def careActivity = CareActivity.get(params.id)
		renderTreatments(careActivity);
	}

	def updateTreatments = {
		def careActivity = CareActivity.get(params.id)
		def errors = [:]
		def data = request.JSON.Treatments

		if(changedSinceRetrieval(careActivity, data)){
			data.versions = getVersions(careActivity)
			careActivity.errors.rejectValue('version', 'version.changed.treatment')
			careActivity.discard()
			renderTreatmentsWithErrors(data, errors, careActivity);
		}else{
			updateData(careActivity, data, errors)
			if(!errors & careActivity.validate()){
				careActivity.save(flush:true)
				renderTreatments(careActivity);
			}else{
				careActivity.discard()
				renderTreatmentsWithErrors(data, errors, careActivity);
			}
		}
	}

	private changedSinceRetrieval(careActivity, data) {
		def result = hasCareActivityChangedSinceRetrieval(careActivity, data)
		return result ? true : haveTreatmentsChangedSinceRetrieval(careActivity, data)
	}

	private hasCareActivityChangedSinceRetrieval(careActivity, data) {
		if(careActivity.version && careActivity.version >(long)data.versions.careActivity){
			if(careActivity.findCareActivityProperty("respiratoryIntervention") != data.respiratoryIntervention){
				return true
			}
			if(careActivity.findCareActivityProperty("icuIntervention") != data.icuIntervention){
				return true
			}
			if(careActivity.findCareActivityProperty("neuroIntervention") != data.neuroIntervention){
				return true
			}
			if(careActivity.findCareActivityProperty("otherIntervention") != data.otherIntervention){
				return true
			}
			if(careActivity.findCareActivityProperty("otherInterventionText") != data.otherInterventionText){
				return true
			}
			for( treatment in data.compulsory){
				def propertyName = "${treatment.type}Contra".toString()
				if(careActivity.findCareActivityProperty(propertyName) != data[propertyName]){
					return true
					break;
				}
			}
			for( treatment in data.additional){
				def propertyName = "${treatment.type}Contra".toString()
				if(careActivity.findCareActivityProperty(propertyName) != data[propertyName]){
					return true
					break;
				}
			}
		}
		return false
	}

	private haveTreatmentsChangedSinceRetrieval(careActivity, data) {
		def hasChanged = false
		careActivity.treatments.each{treatment ->
			def isSame = false
			data.versions.treatments.each{id, version ->
				if (treatment.id == id || treatment.version == version){
					isSame = true
				}
			}
			if(!isSame){
				hasChanged = true
			}
		}
		if(hasChanged){
			return true
		}
		return false
	}

	private getVersions(careActivity) {
		return ['careActivity':careActivity?.version
			,'treatments':getTreatmentVersions(careActivity)]
	}

	private getTreatmentVersions(careActivity) {
		def result = [:]
		careActivity?.treatments.each { treatment ->
			result.put(treatment.id, treatment.version)
		}
		return result
	}

	private updateData(careActivity, data, errors) {
		setProperty(careActivity, "respiratoryIntervention", data.respiratory)
		setProperty(careActivity, "icuIntervention", data.icu)
		setProperty(careActivity, "neuroIntervention", data.neuro)
		setProperty(careActivity, "otherIntervention", data.otherSpeciality)
		if(data.otherSpeciality){
			if(getValueFromString(data.otherText)){
				setProperty(careActivity, "otherInterventionText", data.otherText)
			}else{
				errors.put("otherInterventionText", "other.intervention.text.missing")
			}
		}else{
			setProperty(careActivity, "otherInterventionText", "")
		}

		processTreatments(careActivity, data.compulsory , errors, true)
		//processTreatments(careActivity, data.additional , errors, false)
	}

	private def setProperty = { careActivity, property, value->
		if(value){
			careActivity.setCareActivityProperty(property, value.toString())
		}else {
			careActivity.clearCareActivityProperty(property);
		}
	}

	private def updateTreatment = { data, treatment, errors ->
		treatment.startDate = DisplayUtils.getDate(data.startDate, errors, "treatments.startDate", "treatment.start.date.invalid.format")
		treatment.startTime = DisplayUtils.getTime(data.startTime, errors, "treatments.startTime", "treatment.start.time.invalid.format")
		treatment.endDate = DisplayUtils.getDate(data.endDate, errors, "treatments.endDate", "treatment.end.date.invalid.format")
		treatment.endTime = DisplayUtils.getTime(data.endTime, errors, "treatments.endtime", "treatment.end.time.invalid.format")
		if(data.type){
			treatment.type = TreatmentType?.findByDescription(data.type)
		}else{
			treatment.type = null
		}
	}

	private def processTreatments  = {careActivity, treatments ,errors, compulsory->
		for( treatment in treatments){
			if(treatment.contraindicated){
				careActivity.setCareActivityProperty("${treatment.type}Contra".toString(), "true")
				if(ControllerHelper.getIntegerFromString(treatment.id)){
					def theTreatment = Treatment.get(treatment.id)
					if (theTreatment){
						careActivity.removeFromTreatments(theTreatment)
						theTreatment.delete()
					}
				}
			}else {
				careActivity.clearCareActivityProperty("${treatment.type}Contra".toString())
				if(ControllerHelper.getIntegerFromString(treatment.id)){
					if(treatment._destroy){
						def theTreatment = Treatment.get(treatment.id)
						careActivity.removeFromTreatments(theTreatment)
						theTreatment.delete()
					}else{
						updateTreatment(treatment,Treatment.get(treatment.id), errors)
					}
				}else{
					if(!treatment._destroy){
						if(compulsory){
							if(treatment.startDate
							|| treatment.startTime
							|| treatment.endDate
							|| treatment.endTime
							){
								def theTreatment = getTreatmentOfThisType(careActivity, treatment.type);
								if(!theTreatment){
									theTreatment = new Treatment()
								}


								updateTreatment(treatment,theTreatment, errors )
								careActivity.addToTreatments(theTreatment)
							}
						}else{
							if(treatment.startDate
							|| treatment.startTime
							|| treatment.endDate
							|| treatment.endTime
							|| treatment.type
							){
								def theTreatment = new Treatment()
								updateTreatment(treatment,theTreatment, errors )
								careActivity.addToTreatments(theTreatment)
							}
						}
					}
				}
			}
		}
	}

	private def getTreatmentOfThisType = { careActivity, type ->
		def theTreatment = null;
		careActivity.treatments.each{treatment ->
			if(treatment.type.description == type){
				theTreatment = treatment
			}
		}
		return theTreatment
	}

	private def renderTreatments = {careActivity ->
		def compulsoryTypes = TreatmentType.findAllByCompulsory(Boolean.TRUE)
		def allTypes = TreatmentType.getAll()
		def treatmentTypes = []
		allTypes.each { type ->
			treatmentTypes << [id:type.id, description:type.description]
		}
		def compulsoryTreatmentMap = [:]
		def compulsoryTreatments=[]
		def additionalTreatments= []
		compulsoryTypes.each{type ->

			def contraindicated = careActivity.findCareActivityProperty("${type.description}Contra")? true:false

			def treatment = [id:""
						,type:type.description
						,startDate:""
						,startTime:""
						,endDate:""
						,endTime:""
						,contraindicated:contraindicated]

			compulsoryTreatmentMap.put(treatment.type,  treatment)
		}




		careActivity.treatments.each{treatment ->
			def compulsoryTreatment = compulsoryTreatmentMap.get(treatment.type.description);
			if(compulsoryTreatment){
				if(compulsoryTreatment.id){
					if(treatment.startDate && ControllerHelper.getDate(compulsoryTreatment.startDate).after(treatment.startDate)){
						additionalTreatments << [id:compulsoryTreatment.id
									,type:compulsoryTreatment.type
									,startDate:compulsoryTreatment.startDate
									,startTime:compulsoryTreatment.startTime
									,endDate:compulsoryTreatment.endDate
									,endTime:compulsoryTreatment.endTime]

						compulsoryTreatmentMap.put(treatment.type.description,  [id:treatment.id
									,type:treatment.type.description
									,startDate:DisplayUtils.displayDate(treatment.startDate)
									,startTime:DisplayUtils.displayTime(treatment.startTime)
									,endDate:DisplayUtils.displayDate(treatment.endDate)
									,endTime:DisplayUtils.displayTime(treatment.endTime)
									,contraindicated:false])
					}else{
						additionalTreatments << [id:treatment.id
									,type:treatment.type.description
									,startDate:DisplayUtils.displayDate(treatment.startDate)
									,startTime:DisplayUtils.displayTime(treatment.startTime)
									,endDate:DisplayUtils.displayDate(treatment.endDate)
									,endTime:DisplayUtils.displayTime(treatment.endTime)]
					}
				}else{
					compulsoryTreatmentMap.put(treatment.type.description,  [id:treatment.id
								,type:treatment.type.description
								,startDate:DisplayUtils.displayDate(treatment.startDate)
								,startTime:DisplayUtils.displayTime(treatment.startTime)
								,endDate:DisplayUtils.displayDate(treatment.endDate)
								,endTime:DisplayUtils.displayTime(treatment.endTime)
								,contraindicated:false])
				}
			}else{
				additionalTreatments << [id:treatment.id
							,type:treatment.type.description
							,startDate:DisplayUtils.displayDate(treatment.startDate)
							,startTime:DisplayUtils.displayTime(treatment.startTime)
							,endDate:DisplayUtils.displayDate(treatment.endDate)
							,endTime:DisplayUtils.displayTime(treatment.endTime)]
			}
		}




		compulsoryTreatmentMap.each{key, value ->
			compulsoryTreatments << value
		}

		def respiratory = careActivity.findCareActivityProperty("respiratoryIntervention") == "true" ? true: false
		def icu = careActivity.findCareActivityProperty("icuIntervention") == "true" ? true: false
		def neuro = careActivity.findCareActivityProperty("neuroIntervention")== "true" ? true: false
		def otherSpeciality = careActivity.findCareActivityProperty("otherIntervention")== "true" ? true: false
		def otherSpecialityText  = careActivity.findCareActivityProperty("otherInterventionText") == null? "" : careActivity.findCareActivityProperty("otherInterventionText")

		def treatments = [versions:getVersions(careActivity)
					,respiratory:respiratory
					,icu:icu
					,neuro:neuro
					,otherSpeciality:otherSpeciality
					,otherText:otherSpecialityText
					, compulsory:compulsoryTreatments
					, additional:additionalTreatments
					, treatmentTypes:treatmentTypes]



		def result = [Treatments:treatments, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
	}

	private def renderTreatmentsWithErrors = {data, errors, careActivity  ->
		log.debug "In renderTreatmentsWithErrors"
		errors.each{key, value ->
			log.debug "Adding non domain error --> ${key} :: ${value}"
			careActivity.errors.reject(value,[key]as Object[], "Custom validation failed for ${key} in the controller".toString())
		}

		def result = [Treatments:data, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
	}
}
