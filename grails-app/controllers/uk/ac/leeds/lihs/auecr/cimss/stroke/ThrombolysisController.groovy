package uk.ac.leeds.lihs.auecr.cimss.stroke

import java.util.Date;

import org.codehaus.groovy.grails.web.json.JSONObject;

import grails.converters.JSON;

class ThrombolysisController extends StrokeBaseController{
	
	
	
	def getThrombolysisPage = {
		log.debug "in getThrombolysis"
		def careActivity = CareActivity.get(params.id)
		render(template:'/thrombolysis/thrombolysis',model:['careActivityInstance':careActivity])
	}
	
	def getThrombolysis = {
		log.debug "in getThrombolysis"
		def careActivity = CareActivity.get(params.id);
		renderThrombolysis(careActivity);
	}
	
	def updateThrombolysis = {
		def careActivity = CareActivity.get(params.id)
		def errors = [:]
		def data = request.JSON.ThrombolysisManagement
		
		if(changedSinceRetrieval(careActivity, data)){
			data.versions = getVersions(careActivity)
			careActivity.errors.rejectValue('version', 'version.changed.thrombolysis')
			careActivity.discard()
			renderThrombolysisWithErrors(data, errors, careActivity);
		}else{
			updateData(careActivity, data, errors)
			if(!errors & careActivity.validate()){
				careActivity.save(flush:true)
				renderThrombolysis(careActivity);
			}else{
				careActivity.discard()
				renderThrombolysisWithErrors(data, errors, careActivity);
			}
		}
	}
	
	private getVersions(CareActivity careActivity) {
		return ['careActivity':careActivity?.version
			,'thrombolysis':careActivity?.thrombolysis?.version]
	}
	
	private changedSinceRetrieval(careActivity, data) {
		if(careActivity.version && careActivity.version >(long)data.versions.careActivity){
			if(careActivity.findCareActivityProperty("thrombolysed") != data.thrombolysed){
				//println"${careActivity.findCareActivityProperty("thrombolysed")} --- ${data.thrombolysed}"
				//println "In 1"
				return true
			}
			if(careActivity.findCareActivityProperty("reasonsNotThrombolysed") != data.reasonsNotThrombolysed){
				//println "In 2"
				return true
			}
			if (careActivity.thrombolysis && (careActivity.thrombolysis.version > (long)data.versions.thrombolysis)) {
				//println "In 3"
				return true
			}
		}
		return false
	}
	
	private updateData(careActivity, data, errors) {
		if(data.givenThrombolysis == "no"){
			processNo(careActivity, data, errors)
		}else if(data.givenThrombolysis == "yes"){
			processYes(careActivity, data, errors)
		}else{
			careActivity.clearCareActivityProperty("thrombolysed");
			careActivity.clearCareActivityProperty("reasonsNotThrombolysed")
		}
	}
	
	private  processNo = {careActivity, data, errors ->
		def reasonedNotThrombolysed = [:]
		
		
		
		
		data.reasonsNotThrombolysed.each(){ key, value ->
			//println "${key} : ${value}"
			reasonedNotThrombolysed.put(key, value.toString())
		}
		
		
		if(reasonedNotThrombolysed.noThrombolysisNone == "true"){
			data.reasonsNotThrombolysed.each(){ key, value ->
				reasonedNotThrombolysed.put(key, "false")			
			}
			reasonedNotThrombolysed.noThrombolysisNone="true";
		}
		
		if(reasonedNotThrombolysed.noThrombolysisOutsideHours == "true"){
			data.reasonsNotThrombolysed.each(){ key, value ->
				reasonedNotThrombolysed.put(key, "false")
			}
			reasonedNotThrombolysed.noThrombolysisOutsideHours="true";
		}
		
		if(reasonedNotThrombolysed.noThrombolysisNotAvailable == "true"){
			data.reasonsNotThrombolysed.each(){ key, value ->
				reasonedNotThrombolysed.put(key, "false")
			}
			reasonedNotThrombolysed.noThrombolysisNotAvailable="true";
		}
		
		if(reasonedNotThrombolysed.noThrombolysisScanLate == "true"){
			data.reasonsNotThrombolysed.each(){ key, value ->
				reasonedNotThrombolysed.put(key, "false")
			}
			reasonedNotThrombolysed.noThrombolysisScanLate="true";
		}
		
		
		
		if(reasonedNotThrombolysed.noThrombolysisOther != "true"){
			reasonedNotThrombolysed.put("noThrombolysisOtherText", "")
		}
		careActivity?.thrombolysis?.delete();
		careActivity.thrombolysis = null;
		careActivity.setCareActivityProperty("thrombolysed" , "no")
		careActivity.setCareActivityProperty("reasonsNotThrombolysed" ,reasonedNotThrombolysed.toString() )	
    }
	
	private  processYes = {careActivity, data, errors ->
		careActivity.setCareActivityProperty("thrombolysed", "yes");
		careActivity.clearCareActivityProperty("reasonsNotThrombolysed")
		
		def thrombolysis  = careActivity.thrombolysis;
		if(!thrombolysis ){
			thrombolysis = new Thrombolysis(['thrombolysisDate':
									DisplayUtils.getDate(data.thrombolysis.thrombolysisDate  , errors, "thrombolysis.thrombolysisDate", "thrombolysis.yes.thrombolysis.date.invalid.format")])
			careActivity.thrombolysis = thrombolysis
			thrombolysis.careActivity = careActivity
		}else{
			if(data.thrombolysis.thrombolysisDate){
				thrombolysis.thrombolysisDate = DisplayUtils.getDate(data.thrombolysis.thrombolysisDate , errors, "thrombolysis.thrombolysisDate", "thrombolysis.yes.thrombolysis.date.invalid.format")
			}else{
				thrombolysis.thrombolysisDate = null
			}
		}
		
		
		if(data.thrombolysis.thrombolysisTime){
			thrombolysis.thrombolysisTime = DisplayUtils.getTime(data.thrombolysis.thrombolysisTime , errors, "thrombolysis.thrombolysisTime", "thrombolysis.yes.thrombolysis.time.invalid.format")
		}else{
			thrombolysis.thrombolysisTime=null;
		}
		
		
		
		if(data.thrombolysis.doorToNeedleTime && data.thrombolysis.doorToNeedleTime!= JSONObject.NULL){
			thrombolysis.doorToNeedleTime = data.thrombolysis.doorToNeedleTime.toInteger()
		}else{
			thrombolysis.doorToNeedleTime=null;
		}
		
		
		
		
		if(hasValue(data.thrombolysis.decisionMakerGrade)){
			thrombolysis.decisionMakerGrade = data.thrombolysis.decisionMakerGrade
		}else{
			thrombolysis.decisionMakerGrade = null
		}
		
		if(hasValue(data.thrombolysis.decisionMakerLocation)){
			thrombolysis.decisionMakerLocation = data.thrombolysis.decisionMakerLocation
		}else{
			thrombolysis.decisionMakerLocation = null
		}
		
		if(hasValue(data.thrombolysis.decisionMakerSpeciality)){
			thrombolysis.decisionMakerSpeciality = data.thrombolysis.decisionMakerSpeciality
			if(data.thrombolysis.decisionMakerSpeciality=='other'){
				if(getValueFromString(data.thrombolysis.decisionMakerSpecialityOther)){
					thrombolysis.decisionMakerSpecialityOther = data.thrombolysis.decisionMakerSpecialityOther
				}else{
					thrombolysis.decisionMakerSpecialityOther = null
				}
			}else{
				thrombolysis.decisionMakerSpecialityOther = null
			}
		}else{
			thrombolysis.decisionMakerSpeciality = null
			thrombolysis.decisionMakerSpecialityOther = null
		}
		
		
		if(hasValue(data.thrombolysis.complications)){
			thrombolysis.complications = (data.thrombolysis.complications == "true" ? Boolean.TRUE : Boolean.FALSE)
		}else{
			thrombolysis.complications = null
		}
		
		thrombolysis.complicationTypeHaemorrhage = getBooleanValue(data.thrombolysis.complicationTypeHaemorrhage)
		thrombolysis.complicationTypeOther = getBooleanValue(data.thrombolysis.complicationTypeOther)
		thrombolysis.complicationTypeBleed = getBooleanValue(data.thrombolysis.complicationTypeBleed)
		thrombolysis.complicationTypeOedema = getBooleanValue(data.thrombolysis.complicationTypeOedema)
		
		
	
		
		if(thrombolysis.complicationTypeOther==Boolean.TRUE){
			thrombolysis.complicationTypeOtherText = getValueFromString(data.thrombolysis.complicationTypeOtherText)
		}else{
			thrombolysis.complicationTypeOtherText = null
		}
		
		
		/*if(getValueFromString(data.thrombolysis.complicationType)){
			if(data.thrombolysis.complications == "true"){
				thrombolysis.complicationType = data.thrombolysis.complicationType
				if( data.thrombolysis.complicationType == "other"){
					thrombolysis.complicationTypeOther = getValueFromString(data.thrombolysis.complicationTypeOther)
				}else{
					thrombolysis.complicationTypeOther = null
				}
			}else{
				thrombolysis.complicationType = null
				thrombolysis.complicationTypeOther = null
			}
		}else{
			thrombolysis.complicationType = null
			thrombolysis.complicationTypeOther = null
		}*/
		
		
		if(hasValue(data.thrombolysis.followUpScan)){
			thrombolysis.followUpScan = (data.thrombolysis.followUpScan == "true" ? Boolean.TRUE : Boolean.FALSE)
		}else{
			thrombolysis.followUpScan = null
		}
		
		
		if(data.thrombolysis.followUpScanDate){
			thrombolysis.followUpScanDate = DisplayUtils.getDate(data.thrombolysis.followUpScanDate , errors, "thrombolysis.followUpScanDate", "thrombolysis.yes.thrombolysis.follow.up.scan.date.invalid.format")
			if(data.thrombolysis.followUpScan == "false"){
				thrombolysis.followUpScanDate=null;
			}
		}else{
			thrombolysis.followUpScanDate=null;
		}
		
		if(data.thrombolysis.followUpScanTime){
			thrombolysis.followUpScanTime = DisplayUtils.getTime(data.thrombolysis.followUpScanTime, errors, "thrombolysis.followUpScanTime", "thrombolysis.yes.thrombolysis.follow.up.scan.time.invalid.format")
			if(data.thrombolysis.followUpScan == "false"){
				thrombolysis.followUpScanTime=null;
			}
		}else{
			thrombolysis.followUpScanTime=null;
		}
		
		
		thrombolysis.nihssScoreAt24HoursUnknown = getBooleanValue(data.thrombolysis.nihssScoreAt24HoursUnknown)
		
		if(thrombolysis.nihssScoreAt24HoursUnknown == Boolean.TRUE){
			thrombolysis.nihssScoreAt24Hours=null;
		}else{
			if(getIntegerFromString(data.thrombolysis.nihssScoreAt24Hours) != null){
				thrombolysis.nihssScoreAt24Hours = getIntegerFromString(data.thrombolysis.nihssScoreAt24Hours)
			}else{
				thrombolysis.nihssScoreAt24Hours=null;
			}
		}
		
	
		
		
		
	}
		
	private def hasValue = {value ->
		return 	value && value !="null"  && value !=JSONObject.NULL
    }
	
	private def renderThrombolysis = { careActivity ->
		
		def thrombolysis = []
		if(careActivity.thrombolysis){
			thrombolysis = [id:careActivity.thrombolysis.id
				, admissionDate:DisplayUtils.displayDate(careActivity?.getEffectiveStartDate())
				, admissionTime:DisplayUtils.displayTime(careActivity?.getEffectiveStartTime())
							, thrombolysisDate:careActivity.thrombolysis.thrombolysisDate?.format("dd/MM/yyyy" )
							, thrombolysisTime:DisplayUtils.displayTime(careActivity.thrombolysis.thrombolysisTime)
							, doorToNeedleTime:careActivity.thrombolysis.doorToNeedleTime
							, decisionMakerGrade:careActivity.thrombolysis.decisionMakerGrade
							, decisionMakerLocation:careActivity.thrombolysis.decisionMakerLocation
							, decisionMakerSpeciality:careActivity.thrombolysis.decisionMakerSpeciality
							, decisionMakerSpecialityOther:careActivity.thrombolysis.decisionMakerSpecialityOther
							, complications:careActivity.thrombolysis.complications
							, complicationType:careActivity.thrombolysis.complicationType
							, complicationTypeHaemorrhage:careActivity.thrombolysis.complicationTypeHaemorrhage
							, complicationTypeOedema:careActivity.thrombolysis.complicationTypeOedema
							, complicationTypeBleed:careActivity.thrombolysis.complicationTypeBleed
							, complicationTypeOther:careActivity.thrombolysis.complicationTypeOther
							, complicationTypeOtherText:careActivity.thrombolysis.complicationTypeOtherText
							, followUpScan:careActivity.thrombolysis.followUpScan
							, followUpScanDate:careActivity.thrombolysis.followUpScanDate?.format("dd/MM/yyyy" )
							, followUpScanTime:DisplayUtils.displayTime(careActivity.thrombolysis.followUpScanTime)
							,nihssScoreAt24Hours:careActivity.thrombolysis.nihssScoreAt24Hours
							,nihssScoreAt24HoursUnknown:careActivity.thrombolysis.nihssScoreAt24HoursUnknown
							]
		}
		
		def theCareActivity = [versions:getVersions(careActivity)
						,id:careActivity.id
						, givenThrombolysis:careActivity.careActivityProperties.thrombolysed
						, reasonsNotThrombolysed:careActivity.getReasonsNotThrombolysed()
						, thrombolysis:thrombolysis]
		
		def result = [CareActivity:theCareActivity
						, FieldsInError: getFieldsInError(careActivity)
						, ErrorsAsList:getErrorsAsList(careActivity)
						, InfoMessage:getInfoMessage(careActivity)]	
		render result as JSON
    }
	
	private def renderThrombolysisWithErrors = { data, errors, careActivity  ->
		log.debug "In renderThrombolysisWithErrors"
		errors.each{key, value ->
			log.debug "Adding non domain error --> ${key} :: ${value}"
			careActivity.errors.reject(value,[key] as Object[], "Custom validation failed for ${key} in the controller".toString())
		}
		
		
		careActivity.errors.each{it ->
			log.debug "${it}"
			
		}
		
		
		def result = [CareActivity:data
						, FieldsInError: getFieldsInError(careActivity)
						, ErrorsAsList:getErrorsAsList(careActivity)
						, InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
	}
		
	def  update = {
		log.debug "ThrombolysisController:update -> ${params}"
		def careActivity = CareActivity.get(params.careActivityId);
		if(params.givenThrombolysis=='yes' ){
			careActivity.setCareActivityProperty("thrombolysed", "yes");
			careActivity.clearCareActivityProperty("reasonsNotThrombolysed")
			processYes(careActivity, params)			
		}else if (params.givenThrombolysis=='no'){
			careActivity.setCareActivityProperty("thrombolysed", "no");
			careActivity?.thrombolysis?.delete();
			careActivity.thrombolysis = null;
			processNo(careActivity, params)
		}else{
			careActivity.clearCareActivityProperty("thrombolysed");
			careActivity.clearCareActivityProperty("reasonsNotThrombolysed")
		}
		
		
		
		if(careActivity.validate()  ){
			careActivity.save();
			//flash.message = "last updated @ ${new Date().format('dd-MM-yyyy hh:mm:ss')}"
			flash.message = "Updated"
			render(template:"thrombolysis", model:[careActivityInstance:careActivity])
		}else{		
			flash.message = "Not Updated"
			def model = [careActivityInstance:careActivity,errorList:getErrors(careActivity)]
			render(template:"thrombolysis", model:model)
		}
		
		
		
	}
	
}
