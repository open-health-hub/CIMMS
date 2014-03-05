package uk.ac.leeds.lihs.auecr.cimss.stroke


import grails.converters.JSON;

class DataExtractorController extends StrokeBaseController{
	def cimssExtractorService
	
    def getCimssExtract = {
		log.debug "in getNutritionManagement"
		def careActivity = CareActivity.get(params.id);
		renderCimssExtract(careActivity);
	}
	
	
	def renderCimssExtract = {careActivity ->
		
		render cimssExtractorService.extractData(careActivity) as JSON
	}
}
