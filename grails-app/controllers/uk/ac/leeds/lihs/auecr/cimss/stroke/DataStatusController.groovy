package uk.ac.leeds.lihs.auecr.cimss.stroke


import uk.ac.leeds.lihs.auecr.cimss.stroke.extractor.ChftDataStatus;
import uk.ac.leeds.lihs.auecr.cimss.stroke.extractor.AnhstDataStatus;
import uk.ac.leeds.lihs.auecr.cimss.stroke.extractor.CimssExtract;
import uk.ac.leeds.lihs.auecr.cimss.stroke.extractor.SsnapExtract

import grails.converters.JSON;

class DataStatusController extends StrokeBaseController{
	def cimssExtractorService
	def chftDataStatusService 
	def anhstDataStatusService
	def ssnapExtractorService
	def patientInfoService
	
	def getDataStatus = {
		log.debug "in getDataStatus params.id = ${params.id}"
		def careActivity = CareActivity.get(params.id);		
		
		def DataStatus dataStatus = doGetDataStatus(careActivity)	
		render dataStatus as JSON
	}

	def doGetDataStatus (CareActivity  careActivity) { 
		log.debug "in doGetDataStatus"
		
		return new DataStatus([versions:getVersions(careActivity)
								,cimssExtract:cimssExtractorService.extractData(careActivity)
								,chftDataStatus:chftDataStatusService.getDataStatus(careActivity)
								,anhstDataStatus:anhstDataStatusService.getDataStatus(careActivity)
								,ssnapExtract:ssnapExtractorService.extractData(careActivity)
								,ssnap72HrExtract:ssnapExtractorService.extract72HrData(careActivity)
								,patientInfo:patientInfoService.getPatientInfo(careActivity)] )
	}

	
	def getVersions = {careActivity ->
		return [careActivity:careActivity.version]
	}
}

class DataStatus {
	Map versions	
	ChftDataStatus chftDataStatus
	AnhstDataStatus anhstDataStatus
	CimssExtract  cimssExtract	
	SsnapExtract ssnapExtract
	SsnapExtract ssnap72HrExtract
	PatientInfo patientInfo
}

