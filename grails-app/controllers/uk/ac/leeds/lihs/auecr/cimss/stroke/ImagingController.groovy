package uk.ac.leeds.lihs.auecr.cimss.stroke

import org.codehaus.groovy.grails.web.json.JSONObject;

import grails.converters.JSON;

import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.DiagnosisType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.DiagnosisCategoryType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.ImageType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.NoScanReasonType;

import groovy.time.TimeCategory;
import groovy.time.TimeDuration;



class ImagingController extends StrokeBaseController {
	
	
	def getImagingPage = {
		log.debug "in getImagingPage"
		def careActivity = CareActivity.get(params.id)
		render(template:'/assessment/imaging',model:['careActivityInstance':careActivity])
	}

	def getImaging = {
		log.debug "in getImaging"
		def careActivity = CareActivity.get(params.id)
		renderImaging(careActivity);
	}
	
	private def getVersions = { careActivity ->
		return ['careActivity':careActivity?.version
			,'imaging':careActivity?.imaging?.version
			,'scan':careActivity?.imaging?.scan?.version]
	}
	
	private def changedSinceRetrieval = { careActivity, data ->
		
		
		
		if(careActivity?.imaging?.version!=null && data.versions.imaging == JSONObject.NULL){
			return true
		}
		
		if(careActivity?.imaging?.version && careActivity.imaging?.version >(long)data.versions.imaging){
			return true
		}
		
		
		if(careActivity?.imaging?.scan?.version!=null && data.versions.scan == JSONObject.NULL){
			return true
		}
		if(careActivity?.imaging?.scan?.version && careActivity.imaging?.scan?.version >(long)data.versions.scan){
			return true
		}
		
		
		return false
	}
	
	def updateImaging = {
		log.info "in updateImaging -> ${request.JSON.Imaging}"
		def careActivity = CareActivity.get(params.id)
		def errors = [:]
		def data = request.JSON.Imaging
		
		if(changedSinceRetrieval(careActivity, data)){
			data.versions = getVersions(careActivity)
			careActivity.errors.rejectValue('version', 'version.changed.imaging')
			careActivity.discard()
			renderImagingWithErrors(data, careActivity, errors);
		}else{
			updateData(careActivity, data, errors)
			if(careActivity.validate() & !errors){
					careActivity.save(flush:true)
					renderImaging(careActivity);
			}else{
					careActivity.discard()
					renderImagingWithErrors(data, careActivity, errors);
			}
		}

	}
	
	
	private def updateData = { careActivity, data , errors ->
		if(!careActivity.imaging){
			careActivity.imaging = new Imaging()
			careActivity.imaging.careActivity = careActivity
		}
		
		careActivity.imaging.scanPostStroke = data.scanPostStroke
		if(careActivity.imaging.scanPostStroke=="no"){
			if(getValueFromString(data.noScanReason)){
				careActivity.imaging.noScanReason = NoScanReasonType.findByDescription(data.noScanReason);
			}else{
			careActivity.imaging.noScanReason = null;
			}
			if(careActivity.imaging.scan){
				careActivity.imaging.scan.delete()
				careActivity.imaging.scan = null
			}
		}else if( careActivity.imaging.scanPostStroke=="yes"){
			careActivity.imaging.noScanReason = null;
			if(!careActivity.imaging.scan){
				careActivity.imaging.scan = new Scan()
				careActivity.imaging.scan.imaging = careActivity.imaging
			}
			careActivity.imaging.scan.requestDate=DisplayUtils.getDate(data.scan.requestDate, errors, "imaging.scan.requestDate", "scan.request.date.invalid.format")
			careActivity.imaging.scan.requestTime=DisplayUtils.getTime(data.scan.requestTime, errors, "imaging.scan.requestTime", "scan.request.time.invalid.format")
			
			careActivity.imaging.scan.takenDate=DisplayUtils.getDate(data.scan.takenDate, errors, "imaging.scan.takenDate", "scan.taken.date.invalid.format")
			careActivity.imaging.scan.takenTime=DisplayUtils.getTime(data.scan.takenTime, errors, "imaging.scan.takenTime", "scan.taken.time.invalid.format")
			careActivity.imaging.scan.takenOverride=data.scan.takenOverride;
			if(getValueFromString(data.scan.imageType)){
				careActivity.imaging.scan.imageType = ImageType.findByDescription(data.scan.imageType);
			}else{
				careActivity.imaging.scan.imageType = null
			}
			if(getValueFromString(data.scan.imageDiagnosisCategory)){
				careActivity.imaging.scan.diagnosisCategory = DiagnosisCategoryType.findByDescription(data.scan.imageDiagnosisCategory.replace("_"," "));				
			}else{
				careActivity.imaging.scan.diagnosisCategory = null
			}
			if(getValueFromString(data.scan.imageDiagnosisType)){
				careActivity.imaging.scan.diagnosisType = DiagnosisType.findByDescription(data.scan.imageDiagnosisType.replace("_"," "));
				if(data.scan.imageDiagnosisType=="Other"){
					careActivity.imaging.scan.diagnosisTypeOther = getValueFromString(data.scan.imageDiagnosisTypeOther)
				}else{
					if ( careActivity.imaging.scan.diagnosisType.description =="Intercerebral Haemorrhage")
					{
						careActivity?.thrombolysis?.delete();
						careActivity.thrombolysis = null;
						careActivity.setCareActivityProperty("thrombolysed" , "no")
						def reasonedNotThrombolysed  = '[noThrombolysisHaemorrhagicStroke:true]' 
						careActivity.setCareActivityProperty("reasonsNotThrombolysed" ,reasonedNotThrombolysed )
						addInfoMessage("<strong>NOTE:</strong> we automatically changed <strong>Thrombolyis</strong> to \'No\', reason: 'Haemorrhagic stroke'")				
					}
					careActivity.imaging.scan.diagnosisTypeOther = null
				}
			}else{
				careActivity.imaging.scan.diagnosisType = null
				careActivity.imaging.scan.diagnosisTypeOther = null
			}
			
			
		}else{
			careActivity.imaging.noScanReason = null;
			if(careActivity.imaging.scan){
				careActivity.imaging.scan.delete()
				careActivity.imaging.scan = null
			}
		
		}
		
		
		
		
	}
	
	private def renderImaging = {careActivity ->
		
		def hoursToTakeImage = 0
		 
		if(careActivity?.imaging?.scan?.takenDate){
			def admission = ((Date)careActivity.startDate).toCalendar();
			if(careActivity.startTime && careActivity?.imaging?.scan?.takenTime){
				admission.set( Calendar.HOUR_OF_DAY, (int)careActivity.startTime / 60)
				admission.set( Calendar.MINUTE ,careActivity.startTime % 60	)
			}else{
				admission.set( Calendar.HOUR_OF_DAY, 0)
			}		
			
			def imageDate = ((Date)careActivity?.imaging?.scan?.takenDate).toCalendar();
			if(careActivity.startTime && careActivity?.imaging?.scan?.takenTime){
				imageDate.set( Calendar.HOUR_OF_DAY, (int)careActivity?.imaging?.scan?.takenTime / 60)
				imageDate.set( Calendar.MINUTE ,careActivity?.imaging?.scan?.takenTime % 60	)
			}else{
				imageDate.set( Calendar.HOUR_OF_DAY, 0)
			}

			TimeDuration duration = TimeCategory.minus( imageDate.time, admission.time )
			hoursToTakeImage = (duration.days * 24) + (duration.hours) + (duration.minutes/60)
					
		}
		
		
		
		
		def scan = [id:careActivity?.imaging?.scan?.id
			,admissionDate:DisplayUtils.displayDate(careActivity?.startDate)
			,admissionTime:DisplayUtils.displayTime(careActivity?.startTime)
			,requestDate:DisplayUtils.displayDate(careActivity?.imaging?.scan?.requestDate)
			,requestTime:DisplayUtils.displayTime(careActivity?.imaging?.scan?.requestTime)
			,takenDate:DisplayUtils.displayDate(careActivity?.imaging?.scan?.takenDate)
			,takenTime:DisplayUtils.displayTime(careActivity?.imaging?.scan?.takenTime)
			,takenOverLimit:(hoursToTakeImage>24)
			,takenOverride:careActivity?.imaging?.scan?.takenOverride == Boolean.TRUE ? true: false
			,imageType:careActivity?.imaging?.scan?.imageType?.description
			,imageDiagnosisType:careActivity?.imaging?.scan?.diagnosisType?.description?.replace(" ", "_")
			,imageDiagnosisTypeOther:careActivity?.imaging?.scan?.diagnosisTypeOther
			,imageDiagnosisCategory:careActivity?.imaging?.scan?.diagnosisCategory?.description ]
		
		def imaging = [id:careActivity?.imaging?.id
		  , versions:getVersions(careActivity)
		  , scanPostStroke:careActivity?.imaging?.scanPostStroke
		  , noScanReason:careActivity?.imaging?.noScanReason?.description
		  , scan:scan]
		
		def result = [Imaging:imaging, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
	}
	
	private def renderImagingWithErrors = {data, careActivity, errors ->
		log.debug "In renderImagingWithErrors"
		errors.each{key, value ->
			log.debug "Adding non domain error --> ${key} :: ${value}"
			careActivity.errors.reject(value,[key] as Object[], "Custom validation failed for ${key} in the controller".toString())
		}
		
		def result = [Imaging:data, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
	}
	
}
