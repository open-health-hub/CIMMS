package uk.ac.leeds.lihs.auecr.cimss.stroke

import java.util.Date;

import org.codehaus.groovy.grails.web.json.JSONObject;

import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.CatheterReasonType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.NoContinencePlanReasonType;
import grails.converters.JSON;

class ContinenceManagementController extends StrokeBaseController{
	
	def getContinenceManagementPage = {
		log.debug "in getContinenceManagementPage"
		def careActivity = CareActivity.get(params.id)
		render(template:'/assessment/continence',model:['careActivityInstance':careActivity])
	}

    def getContinenceManagement = {
		log.debug "in getContinenceManagement"
		def careActivity = CareActivity.get(params.id);
		renderContinenceManagement(careActivity);
	}
		
	
	private def getVersions = { careActivity ->
		return ['careActivity':careActivity?.version
			,'continenceManagement':careActivity?.continenceManagement?.version
			, 'catheters':getCatheterVersions(careActivity)]
	}
	
	private def getCatheterVersions = { careActivity ->
		def result = [:]
		careActivity?.continenceManagement?.catheterEvents.each {catheterEvent ->
			result.put(catheterEvent.id, catheterEvent.version)
		}
		return result
	}
	
	private def changedSinceRetrieval = { careActivity, data ->
		
		
		if(careActivity?.continenceManagement?.version!=null && data.versions.continenceManagement == JSONObject.NULL){
			return true
		}
		
		if(careActivity?.continenceManagement?.version && careActivity?.continenceManagement?.version >(long)data.versions.continenceManagement){
			return true
			
		}
		// check catheter events
		def hasChanged = false
		careActivity?.continenceManagement?.catheterEvents.each{catheterEvent ->
			def isSame = false
			data.versions.catheters.each{id, version ->
				if (catheterEvent.id == id || catheterEvent.version == version){
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
	
	def updateContinenceManagement = {
		log.info "in updateAdmissionDetails -> ${request.JSON.ContinenceManagement}"
		def careActivity = CareActivity.get(params.id);
		def errors = [:]
		def cathetersToDelete  = []
		
		def data = request.JSON.ContinenceManagement
		
		if(changedSinceRetrieval(careActivity, data)){
			data.versions = getVersions(careActivity)
			careActivity.errors.rejectValue('version', 'version.changed.continence')
			careActivity.discard()
			renderContinenceManagementWithErrors(data,careActivity, errors);
		}else{
			updateData(careActivity, data, cathetersToDelete, errors)
			if(careActivity.validate() & !errors){
				cathetersToDelete.each{catheter ->
					catheter.delete()
				}
				careActivity.save(flush:true)
				renderContinenceManagement(careActivity);;
			}else{
				careActivity.discard()
				renderContinenceManagementWithErrors(data,careActivity, errors);
			}
		}
		
		
	}
	
	
	
	private def updateData = { careActivity, data, cathetersToDelete, errors ->
		
	
		if(!careActivity.continenceManagement){
			careActivity.continenceManagement = new ContinenceManagement()
			careActivity.continenceManagement.careActivity= careActivity
		}
		careActivity.continenceManagement.newlyIncontinent = getBooleanFromString(data.newlyIncontinent)
		
		
		
		careActivity.continenceManagement.inappropriateForContinencePlan = getCheckedTrue(data.inappropriateForContinencePlan)
		
			careActivity.continenceManagement.hasContinencePlan = getBooleanFromString(data.hasContinencePlan)
			if(careActivity.continenceManagement.hasContinencePlan==Boolean.TRUE){
				try{
					careActivity.continenceManagement.continencePlanDate=DisplayUtils.getDate(data.continencePlanDate)
				}catch(IllegalArgumentException iae){
							errors.put("continenceManagement.continencePlanDate", "continence.plan.date.invalid.format")
				}
				try{
					careActivity.continenceManagement.continencePlanTime = DisplayUtils.getTime(data.continencePlanTime)
				}catch(IllegalArgumentException iae){
							errors.put("continenceManagement.continencePlanTime", "continence.plan.time.invalid.format")
				}
				careActivity.continenceManagement.noContinencePlanReason = null
				careActivity.continenceManagement.noContinencePlanReasonOther = null
			}else{
				careActivity.continenceManagement.continencePlanDate = null
				careActivity.continenceManagement.continencePlanTime = null
				careActivity.continenceManagement.noContinencePlanReason  =  NoContinencePlanReasonType.findByDescription(data.noContinencePlanReason)
				if(data.noContinencePlanReason == "other"){
					careActivity.continenceManagement.noContinencePlanReasonOther = getValueFromString(data.noContinencePlanReasonOther)
				}else{
					careActivity.continenceManagement.noContinencePlanReasonOther = null
				}
			}
		
		
		
		
		
		
		careActivity.continenceManagement.priorCatheter = getBooleanFromString(data.priorCatheter)
		careActivity.continenceManagement.catheterisedSinceAdmission = getBooleanFromString(data.catheterisedSinceAdmission)
		
		if(careActivity.continenceManagement.catheterisedSinceAdmission== Boolean.TRUE){
			def history  =  data.catheterHistory
			for( catheter in history){
				if(catheter.id && catheter.id !="null" && catheter.id!=JSONObject.NULL){
					 if(catheter._destroy){
						 def catheterHistory =  careActivity.continenceManagement.findCatheterFromHistoryById(new Long(catheter.id))
						 if(catheterHistory){
						  careActivity.continenceManagement.removeFromCatheterEvents(catheterHistory)
						  cathetersToDelete << catheterHistory
						 
						 }
						
					 }else{
						 updateCatheter(catheter, careActivity.continenceManagement.findCatheterFromHistoryById(new Long(catheter.id)), errors)
					 }
				}else{
					if(!catheter._destroy){
						 if(catheter.insertDate
							 || catheter.insertTime
							 || catheter.reason
							 || catheter.removalDate
							 || catheter.removalTime
							 || catheter.reasonOther
							 ){
							 def catheterHistory = new CatheterHistory()
							 updateCatheter(catheter,catheterHistory , errors)
							 careActivity.continenceManagement.addToCatheterEvents(catheterHistory)
							 catheterHistory.continenceManagement =  careActivity.continenceManagement
						 }
					}
				}
			}
			
			
			// now check consistency of date time
			log.debug "now check consistency of catheter dates and times"
			
			
			def events  = careActivity.continenceManagement.catheterEvents?.sort ({a,b ->
				
				if(a?.insertDate == null && b.insertDate ==null){
					 return 0
				}
				if(a?.insertDate == null) return -1
				if(b?.insertDate == null) return 1
				if(a?.insertDate == b.insertDate){
					if(a?.insertTime == null && b.insertTime ==null){
					 return 0
					}
					if(a?.insertTime == null) return -1
					if(b?.insertTime == null) return 1
					if(a?.insertTime==b?.insertTime) return 0
					if(a?.insertTime<b?.insertTime){
						return -1
					}else{
						return 1
					}
				}
				
				if(a?.insertDate.before(b?.insertDate)) return -1
				return 1 }as Comparator);
			
			def first, invalid, startDate, startTime, endDate, endTime
			
			first = true
			
			
			for(catheter in events){
				log.debug "Processing ${catheter.reason} : ${first} :: ${catheter.removalDate}"
				if(first){
					first = false
				}else{
					if(!endDate || !catheter.insertDate){
						invalid = true
					}else{
						if(endDate.after(catheter.insertDate)){
							invalid = true
						}
						if(endDate == catheter.insertDate){
							if(!endTime && !catheter.insertTime){
								invalid = true
							}
							if(endTime > catheter.insertTime){
								invalid = true
							}
						}
					}
					
				}
				startDate = catheter.insertDate
				startTime = catheter.insertTime
				endDate = catheter.removalDate
				endTime = catheter.removalTime
			}
			if(invalid){
				errors.put("continenceManagement.catheterEvents", "catheter.events.invalid.sequence")
			}
			
					
		}else{
			def toDelete =[]
			careActivity.continenceManagement.catheterEvents.each {catheterHistory ->
				toDelete.add(catheterHistory)
			}
			toDelete.each{catheterHistory ->
				careActivity.continenceManagement.removeFromCatheterEvents(catheterHistory)
				cathetersToDelete << catheterHistory
			}
		}
		
		
		
		
		
	}
	
	
	private def updateCatheter = { data, catheter, errors -> 
		
		catheter.insertDate = DisplayUtils.getDate(data.insertDate, errors, "continenceManagement.catheterEvents.insertDate", "catheter.insert.date.invalid.format") 
		catheter.insertTime = DisplayUtils.getTime(data.insertTime , errors, "continenceManagement.catheterEvents.insertTime", "catheter.insert.time.invalid.format")
		catheter.removalDate = DisplayUtils.getDate(data.removalDate, errors, "continenceManagement.catheterEvents.removalDate", "catheter.removal.date.invalid.format")
		catheter.removalTime = DisplayUtils.getTime(data.removalTime, errors, "continenceManagement.catheterEvents.removalTime", "catheter.removal.time.invalid.format")
		if(data.reason){
			catheter.reason = CatheterReasonType?.findByDescription(data.reason)
		}else{
			catheter.reason = null
		}
		if(data.reasonOther && data.reason=="clinical"){
			catheter.reasonOther = data.reasonOther
		}else{
			catheter.reasonOther = null
		}
    }
		
	private def renderContinenceManagement = { careActivity  ->	
		def catheterHistory = [] 
			for (event in careActivity?.continenceManagement?.getCatheterEventsMostRecentFirst()?.reverse()){
				def catheter = [id:event.id
								, insertDate:event.insertDate?.format("dd/MM/yyyy" )
								, insertTime: DisplayUtils.displayTime(event?.insertTime)
								, removalDate:event.removalDate?.format("dd/MM/yyyy" )
								, removalTime: DisplayUtils.displayTime(event?.removalTime)
								, reason:event.reason?.description
								, reasonOther:event.reasonOther]				
				catheterHistory << catheter
			}
		
		def continenceManagement = [id:careActivity?.nutritionManagement?.id	
									, versions:getVersions(careActivity)					
									, newlyIncontinent:careActivity?.continenceManagement?.newlyIncontinent.toString()
									, hasContinencePlan:careActivity?.continenceManagement?.hasContinencePlan.toString()
									, inappropriateForContinencePlan:careActivity?.continenceManagement?.inappropriateForContinencePlan
									, continencePlanDate:careActivity?.continenceManagement?.continencePlanDate?.format("dd/MM/yyyy" )
									, continencePlanTime:DisplayUtils.displayTime(careActivity?.continenceManagement?.continencePlanTime)
									, noContinencePlanReason:careActivity?.continenceManagement?.noContinencePlanReason.toString()
									, noContinencePlanReasonOther:careActivity?.continenceManagement?.noContinencePlanReasonOther
									, priorCatheter:careActivity?.continenceManagement?.priorCatheter.toString()
									, catheterisedSinceAdmission:careActivity?.continenceManagement?.catheterisedSinceAdmission.toString()
									, catheterHistory:catheterHistory ]
		def result = [ContinenceManagement:continenceManagement, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
	}
	
	private def renderContinenceManagementWithErrors = {data, careActivity, errors ->
		log.debug "In renderContinenceManagementWithErrors"
		errors.each{key, value ->
			log.debug "Adding non domain error --> ${key} :: ${value}"
			careActivity.errors.reject(value,[key]as Object[], "Custom validation failed for ${key} in the controller".toString())
		}
		def result = [ContinenceManagement:data, FieldsInError: getFieldsInError(careActivity), ErrorsAsList:getErrorsAsList(careActivity), InfoMessage:getInfoMessage(careActivity)]
		render result as JSON
	}
		
}	
