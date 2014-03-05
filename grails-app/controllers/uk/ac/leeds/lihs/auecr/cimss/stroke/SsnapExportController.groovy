package uk.ac.leeds.lihs.auecr.cimss.stroke

class SsnapExportController {

	def ssnapExtractorService
	
    def getExportFile = { 
		def ssnapExtractList = ssnapExtractorService.extractDataForAllCareActivity()		
		def dateString = new Date().format("yyyyMMddHHmmss");
		response.setContentType("application/octet-stream")
		response.setHeader("Content-disposition", "filename=ssnapexport-${dateString}.csv")
		response.outputStream << getOutputText(ssnapExtractList)
	}
	
	private getOutputText(ssnapExtractList) {
		def outputText = new StringBuffer()
		appendLine("onset/awareness of symptoms,given is,time of onset/awareness of symptoms,time given is,congestive heart failure,hypertension,atrial fibrillation,diabetes,stroke / tia,was the patient on antiplatelet medication prior to admission,was the patient on anticoagulant medication prior to admission,what was the patient's modified rankin score before this stroke,what was the patient's nihss score on arrival,level of consciousness (loc),loc questions,loc tasks,best vision,visual,facial palsy,motor arm left,motor arm right,motor leg left,motor leg right,limb ataxia,sensory,best language,dysarthria,extinction and inattention,time of first brain imaging after stroke,type of stroke,was the patient given thrombolysis,if no what was the reason,if no select the reasons,haemorrhagic stroke,arrived at thrombolysis time window,stroke too mild or too severe,contraindicated medication,symptom onset time unknown / wake up stroke,symptoms improving,age,co morbidity,patient or relative refusal,other medical reason,time patient was thrombolysed,other medical reason,did the patient have any complications from the thrombolysis,symptomatic intracranial haemorrhage,angio oedema", outputText)				
		ssnapExtractList.each { ssnapExtract ->
			appendLine(ssnapExtract.outputText, outputText)			
		}
		return outputText
	}
	
	private appendLine(String column, StringBuffer outputText) {
		outputText.append(column)
		outputText.append("\r\n");
	}
}
