package uk.ac.leeds.lihs.auecr.cimss.stroke

import uk.ac.leeds.lihs.auecr.cimss.stroke.therapy.AssessmentManagement;
import grails.converters.JSON;

import org.codehaus.groovy.grails.web.json.JSONObject;

class DischargeAssessmentController extends StrokeBaseController{
	
	def getDischargeAssessmentPage = {
		def careActivity = CareActivity.get(params.id)
		render(template:'/discharge/dischargeAssessments',model:['careActivityInstance':careActivity])
	}
	
	def getDischargeAssessment = {
		def careActivity = CareActivity.get(params.id)
		renderDischargeAssessment(careActivity);
	}
	
	def updateDischargeAssessment = {
		log.info "in updateDischargeAssessment -> ${request.JSON.DischargeAssessment}"
		def careActivity = CareActivity.get(params.id)
		def errors = [:]
		def data = request.JSON.DischargeAssessment
		
		if(changedAssessmentSinceRetrieval(careActivity, data)){
			data.versions = getVersions(careActivity)
			careActivity.errors.rejectValue('version', 'version.changed.discharge')
			careActivity.discard()
			renderDischargeWithErrors(data, errors, careActivity);
		}else{
			updateDischargeAssessmentData(careActivity, data, errors)
			if(!errors & careActivity.validate()){
				careActivity.save(flush:true)
				renderDischargeAssessment(careActivity);
			}else{
				careActivity.discard()
				renderDischargeAssessmentWithErrors(data, errors, careActivity);
			}
		}
	}
	
	private def getVersions = { careActivity ->
		return ['careActivity':careActivity?.version
			,'postDischargeCare':careActivity?.postDischargeCare?.version]
	}
	
	private def changedAssessmentSinceRetrieval = { careActivity, data ->
		
		return false
	}
	
	private def updateDischargeAssessmentData = {careActivity, data, errors ->
		if(getValueFromString(data.inAfOnDischarge)){
			careActivity.inAfOnDischarge=data.inAfOnDischarge
		}else{
			careActivity.inAfOnDischarge=null;
		}
		if(careActivity.inAfOnDischarge=="Yes"){
			if(getValueFromString(data.onAnticoagulantAtDischarge)){
				careActivity.onAnticoagulantAtDischarge=data.onAnticoagulantAtDischarge
			}else{
				careActivity.onAnticoagulantAtDischarge=null;
			}
		}else{
			careActivity.onAnticoagulantAtDischarge=null;
		
		}
		
		if(!careActivity.therapyManagement){
			careActivity.therapyManagement = new TherapyManagement()
			careActivity.therapyManagement.careActivity = careActivity
		}
		
		updateAssessmentManagement(careActivity.therapyManagement, data.assessments, errors)
	}
	
	private def updateAssessmentManagement = { therapyManagement, data, errors ->
		ensureAssessmentManagementExists(therapyManagement)
		processBarthel(therapyManagement, data)
		processModifiedRankin(therapyManagement, data)
	}

	private ensureAssessmentManagementExists(therapyManagement) {
		
		
		
		if(!therapyManagement?.assessmentManagement){
			therapyManagement.assessmentManagement = new AssessmentManagement()
			therapyManagement.assessmentManagement.therapyManagement = therapyManagement
		}
	}
	
	private processModifiedRankin(therapyManagement, data) {
		
		setModifiedRankinNotKnown(therapyManagement, data)
		
		def baselineModifiedRankin = therapyManagement.assessmentManagement.findModifiedRankinByStage("Discharge");
		
		
		if(therapyManagement.assessmentManagement.dischargeModifiedRankinNotKnown){
			if(baselineModifiedRankin){
				baselineModifiedRankin.delete()
			}
			therapyManagement.assessmentManagement.deleteModifiedRankinByStage("Discharge")
		}else if (getIntegerFromString(data.modifiedRankin.modifiedRankinScore)!=null ){
			if(!baselineModifiedRankin){
				baselineModifiedRankin = therapyManagement.assessmentManagement.addModifiedRankinByStage("Discharge")
			}
			baselineModifiedRankin.modifiedRankinScore = getIntegerFromString(data.modifiedRankin.modifiedRankinScore)
		}else{
			if(baselineModifiedRankin){
				baselineModifiedRankin.delete()
			}
			therapyManagement.assessmentManagement.deleteModifiedRankinByStage("Discharge")

		}
	}

	private setModifiedRankinNotKnown(therapyManagement, data) {
		if(data.modifiedRankinNotKnown && data.modifiedRankinNotKnown!=JSONObject.NULL){
			therapyManagement.assessmentManagement.dischargeModifiedRankinNotKnown = data.modifiedRankinNotKnown
		}else{
			therapyManagement.assessmentManagement.dischargeModifiedRankinNotKnown = false
		}
	}

	private processBarthel(therapyManagement, data) {
		
		setBarthelNotKnownFlag(therapyManagement, data)
		def baselineBarthel = therapyManagement.assessmentManagement.findBarthelByStage("Discharge")
		
		def dischargedTo = therapyManagement.careActivity?.postDischargeCare?.dischargedTo;
		def barthelNotApplicable = ( dischargedTo == 'intermediateCare' || dischargedTo == 'died' || dischargedTo == 'otherHospital' ) 
			
			
		

			
			
			if(therapyManagement.assessmentManagement.dischargeBarthelNotKnown || barthelNotApplicable){
				if(baselineBarthel){
					baselineBarthel.delete()
				}
				therapyManagement.assessmentManagement.deleteBarthelByStage("Discharge")
				log.debug("barthel deleted - Not known or N/A")
			}else if(getIntegerFromString(data.barthel.bowels)!=null
						|| getIntegerFromString(data.barthel.bladder)!=null
						|| getIntegerFromString(data.barthel.grooming)!=null
						|| getIntegerFromString(data.barthel.toilet)!=null
						|| getIntegerFromString(data.barthel.feeding)!=null
						|| getIntegerFromString(data.barthel.transfer)!=null
						|| getIntegerFromString(data.barthel.mobility)!=null
						|| getIntegerFromString(data.barthel.dressing)!=null
						|| getIntegerFromString(data.barthel.stairs)!=null
						|| getIntegerFromString(data.barthel.bathing)!=null
						|| getIntegerFromString(data.barthel.manualTotal)!=null ){
				if(!baselineBarthel){
					baselineBarthel = therapyManagement.assessmentManagement.addBarthelByStage("Discharge")
				}
	
				baselineBarthel.bowels = getIntegerFromString(data.barthel.bowels)
				baselineBarthel.bladder = getIntegerFromString(data.barthel.bladder)
				baselineBarthel.grooming = getIntegerFromString(data.barthel.grooming)
				baselineBarthel.toilet = getIntegerFromString(data.barthel.toilet)
				baselineBarthel.feeding = getIntegerFromString(data.barthel.feeding)
				baselineBarthel.transfer = getIntegerFromString(data.barthel.transfer)
				baselineBarthel.mobility = getIntegerFromString(data.barthel.mobility)
				baselineBarthel.dressing = getIntegerFromString(data.barthel.dressing)
				baselineBarthel.stairs = getIntegerFromString(data.barthel.stairs)
				baselineBarthel.bathing = getIntegerFromString(data.barthel.bathing)
	
				if(baselineBarthel.hasDetail()){
					baselineBarthel.manualTotal = null
				}else{
					baselineBarthel.manualTotal = getIntegerFromString(data.barthel.manualTotal)
				}
				log.debug("barthel OK")
			}else{
				if(baselineBarthel){
					baselineBarthel.delete()
				}
				therapyManagement.assessmentManagement.deleteBarthelByStage("Discharge")
				log.debug("barthel deleted")
			}
		
	}
	
	

	private setBarthelNotKnownFlag(therapyManagement, data) {
		if(data.barthelNotKnown && data.barthelNotKnown!=JSONObject.NULL){
			therapyManagement.assessmentManagement.dischargeBarthelNotKnown = data.barthelNotKnown
		}else{
			therapyManagement.assessmentManagement.dischargeBarthelNotKnown = false
		}
	}
	
	private def renderDischargeAssessment = {careActivity ->
		
	   def modifiedRankin = [id:""
		   , modifiedRankinScore:""]
	   if(careActivity?.therapyManagement?.assessmentManagement?.findModifiedRankinByStage("Discharge")){
		   modifiedRankin.id = careActivity?.therapyManagement?.assessmentManagement?.findModifiedRankinByStage("Discharge").id
		   modifiedRankin.modifiedRankinScore =  careActivity?.therapyManagement?.assessmentManagement?.findModifiedRankinByStage("Discharge").modifiedRankinScore
	   }
	   if ( careActivity.postDischargeCare?.dischargedTo == "died" && modifiedRankin.modifiedRankinScore == "")  {
		   modifiedRankin.modifiedRankinScore = "6"
	   }

	   def barthel = [id:null
		   , manualTotal:null
		   , bowels:null
		   , bladder:null
		   , grooming:null
		   , toilet:null
		   , feeding:null
		   , transfer:null
		   , mobility:null
		   , dressing:null
		   , stairs:null
		   , bathing:null ]
			 
	   if(careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Discharge")){
		   barthel.id = careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Discharge")?.id
		   barthel.bowels =  careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Discharge")?.bowels
		   barthel.bladder =  careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Discharge")?.bladder
		   barthel.grooming =  careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Discharge")?.grooming
		   barthel.toilet =  careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Discharge")?.toilet
		   barthel.feeding =  careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Discharge")?.feeding
		   barthel.transfer =  careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Discharge")?.transfer
		   barthel.mobility =  careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Discharge")?.mobility
		   barthel.dressing =  careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Discharge")?.dressing
		   barthel.stairs =  careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Discharge")?.stairs
		   barthel.bathing =  careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Discharge")?.bathing
		   barthel.manualTotal =  careActivity?.therapyManagement?.assessmentManagement?.findBarthelByStage("Discharge")?.manualTotal
	   }
	   
	   def assessments = [id:careActivity?.therapyManagement?.assessmentManagement?.id
									  , barthelNotKnown:careActivity?.therapyManagement?.assessmentManagement?.dischargeBarthelNotKnown
								   , modifiedRankinNotKnown:careActivity?.therapyManagement?.assessmentManagement?.dischargeModifiedRankinNotKnown
								   , barthel:barthel
								   , modifiedRankin:modifiedRankin]
	   
		def dischargeAssessment = [id:careActivity
									, dischargedTo: careActivity?.postDischargeCare?.dischargedTo
									, versions:getVersions(careActivity)
									, assessments:assessments
									, inAfOnDischarge:careActivity?.inAfOnDischarge
									, onAnticoagulantAtDischarge:careActivity?.onAnticoagulantAtDischarge]
										
		   
	   def result = [dischargeAssessment:dischargeAssessment, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
	   render result as JSON

		
	}
	
	private def renderDischargeAssessmentWithErrors = { data, errors, careActivity  ->
		log.debug "In renderDischargeAssessmentWithErrors"
		data.remove('originalData')
		errors.each{key, value ->
			log.debug "${key} :: ${value}"
			careActivity.errors.rejectValue(key, value, "Custom validation failed for ${key} in the controller")
		}
		def result = [therapyManagement:data, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
	}
	
}
