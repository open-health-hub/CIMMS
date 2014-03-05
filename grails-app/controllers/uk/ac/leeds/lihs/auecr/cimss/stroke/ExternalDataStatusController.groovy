package uk.ac.leeds.lihs.auecr.cimss.stroke

import org.codehaus.groovy.grails.web.json.JSONObject;

import uk.ac.leeds.lihs.auecr.cimss.stroke.extractor.AnhstDataStatus;
import uk.ac.leeds.lihs.auecr.cimss.stroke.extractor.ChftDataStatus;
import uk.ac.leeds.lihs.auecr.cimss.stroke.extractor.CimssExtract;
import uk.ac.leeds.lihs.auecr.cimss.stroke.extractor.SsnapExtract;
import grails.converters.JSON;

class ExternalDataStatusController extends DataStatusController {

	
    def index = {
		
		def hsiParam = params.get('hsi')
		
		if ( hsiParam == null ) {
			return "No HSI specified"
		}
		
		log.debug "hsiParam="+hsiParam
		def ds = []
		hsiParam.splitEachLine(','){hsi->getStatusInfo(hsi,ds)};		
	
		render ds as JSON
	}
	
	private def getStatusInfo(hsiList, dsList) {
		log.debug "hsiList="+hsiList
		
		for ( hsi in hsiList ) {
			log.debug "hsi="+hsi
			def CareActivity careActivity = CareActivity.findByHospitalStayId(hsi)
			if ( careActivity ) {
				def DataStatus dataStatus = doGetDataStatus(careActivity)
		
				def ds = [
					hsi:hsi
					,chft:dataStatus.chftDataStatus.inError
					,anhst:dataStatus.anhstDataStatus.inError
					,cimss:dataStatus.cimssExtract.inError
					,ssnap:dataStatus.ssnapExtract.inError
					,ssnap72:dataStatus.ssnap72HrExtract.inError
					]
				
				dsList.push(ds)
			}
		}
	}
}
