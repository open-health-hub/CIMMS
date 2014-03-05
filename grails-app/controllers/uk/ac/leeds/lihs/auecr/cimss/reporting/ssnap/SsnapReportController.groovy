package uk.ac.leeds.lihs.auecr.cimss.reporting.ssnap

import java.util.Date;
import uk.ac.leeds.lihs.auecr.cimss.stroke.CareActivity
import uk.ac.leeds.lihs.auecr.cimss.stroke.DataStatus
import uk.ac.leeds.lihs.auecr.cimss.stroke.DataStatusController


class SsnapReportController extends DataStatusController {
	
	// Export service provided by Export plugin
	def exportService
	def grailsApplication  //inject GrailsApplication
	List allReports
	

	def index = {
		List reportsAll = SsnapReport.findAll()

		[list: reportsAll]
	}

	
    def makeReport = {
		
		Date startDate = new Date()
		
		if (params?.startDate) {
			startDate = new Date().parse("yyyy-MM-dd", params.startDate)
		} 
	
		Date endDate = new Date()
		if (params?.endDate) {
			endDate = new Date().parse("yyyy-MM-dd", params.endDate)
		}

		log.debug("StartDate = ${startDate},  endDate = ${endDate}")

		List pendingRecordsAll = 
			SsnapReportDataSource.findAll(
				"FROM SsnapReportDataSource AS ds  WHERE ds.hsi NOT IN (SELECT rec.hospitalStayId FROM SsnapReportRecord AS rec) AND ds.ssnapReportCareActivity.startDate >= ? AND ds.ssnapReportCareActivity.startDate <= ?", [startDate, endDate])
		
		def pendingRecords = [] 
//		if (pendingRecordsAll.size > 10) {
//			pendingRecords.addAll(pendingRecordsAll[0..10])
//		} else {
			pendingRecords.addAll(pendingRecordsAll);
//		}
		
		List readyForReporting = []
		def reportType = params.reportType ? params.reportType : "SSNAP72"
		
		pendingRecords.each {
			def CareActivity careActivity = CareActivity.findByHospitalStayId(it.hsi)
			if ( careActivity ) {
				def DataStatus dataStatus = doGetDataStatus(careActivity)
				if ( reportType == "SSNAP72" &&  (dataStatus?.ssnap72HrExtract?.inError) == false ) {
					readyForReporting.add(it)
				} else if ( reportType == "SSNAP" && (dataStatus?.ssnapExtract?.inError) == false ) {
					readyForReporting.add(it)
				} 
			}
			log.info "ds "+it.hsi
		}
		log.info "hsi's found "+pendingRecords.size +" ready for reporting: "+readyForReporting.size

		makeReport(readyForReporting, startDate, endDate, params)
		redirect (action: "index")		
	}
	
	def deleteReport = {
		def reportId = params.get('reportId')
		
		if ( reportId == null ) {
			render "No reportId specified"
		} else {
			SsnapReport report = SsnapReport.findById(reportId)
			if (report) {
				report.delete()
			} else {			
				render "Report [reportId=${reportId}]: Not found"			
			}
		}
		redirect (action: "index")
	}
	
	def getCsv = {
		
		def reportId = params.get('reportId')
		
		if ( reportId == null ) {
			render "No reportId specified"
		} else {
		
			SsnapReport report = SsnapReport.findById(reportId) 
			if (report) {
				response.contentType = grailsApplication.config.grails.mime.types['csv']
				response.setHeader("Content-disposition", "attachment; filename=${report.reportName}.csv")
				
				def fldParams = [
					'ImportIdentifier',
					'S1HospitalNumber',
					'S1NHSNumber',
					'S1NoNHSNumber',
					'S1Surname',
					'S1Forename',
					'S1BirthDate',
					'S1AgeOnArrival',
					'S1Gender',
					'S1PostcodeOut',
					'S1PostcodeIn',
					'S1Ethnicity',
					'S1Diagnosis',
					'S1OnsetInHospital',
					'S1OnsetDateTime',
					'S1OnsetTimeNotEntered',
					'S1OnsetDateType',
					'S1OnsetTimeType',
					'S1ArriveByAmbulance',
					'S1AmbulanceTrust',
					'S1CadNumber',
					'S1CadNumberNK',
					'S1FirstArrivalDateTime',
					'S1FirstArrivalTimeNotEntered',
					'S1FirstWard',
					'S1FirstStrokeUnitArrivalDateTime',
					'S1FirstStrokeUnitArrivalTimeNotEntered',
					'S1FirstStrokeUnitArrivalNA',
					'S2CoMCongestiveHeartFailure',
					'S2CoMHypertension',
					'S2CoMAtrialFibrilation',
					'S2CoMDiabetes',
					'S2CoMStrokeTIA',
					'S2CoMAFAntiplatelet',
					'S2CoMAFAnticoagulent',
					'S2RankinBeforeStroke',
					'S2NihssArrival',
					'S2NihssArrivalLoc',
					'S2NihssArrivalLocQuestions',
					'S2NihssArrivalLocCommands',
					'S2NihssArrivalBestGaze',
					'S2NihssArrivalVisual',
					'S2NihssArrivalFacialPalsy',
					'S2NihssArrivalMotorArmLeft',
					'S2NihssArrivalMotorArmRight',
					'S2NihssArrivalMotorLegLeft',
					'S2NihssArrivalMotorLegRight',
					'S2NihssArrivalLimbAtaxia',
					'S2NihssArrivalSensory',
					'S2NihssArrivalBestLanguage',
					'S2NihssArrivalDysarthria',
					'S2NihssArrivalExtinctionInattention',
					'S2BrainImagingDateTime',
					'S2BrainImagingTimeNotEntered',
					'S2BrainImagingNotPerformed',
					'S2StrokeType',
					'S2Thrombolysis',
					'S2ThrombolysisNoReason',
					'S2ThrombolysisNoButHaemorrhagic',
					'S2ThrombolysisNoButTimeWindow',
					'S2ThrombolysisNoButComorbidity',
					'S2ThrombolysisNoButMedication',
					'S2ThrombolysisNoButRefusal',
					'S2ThrombolysisNoButAge',
					'S2ThrombolysisNoButImproving',
					'S2ThrombolysisNoButTooMildSevere',
					'S2ThrombolysisNoButTimeUnknownWakeUp',
					'S2ThrombolysisNoButOtherMedical',
					'S2ThrombolysisDateTime',
					'S2ThrombolysisTimeNotEntered',
					'S2ThrombolysisComplications',
					'S2ThrombolysisComplicationSih',
					'S2ThrombolysisComplicationAO',
					'S2ThrombolysisComplicationEB',
					'S2ThrombolysisComplicationOther',
					'S2ThrombolysisComplicationOtherDetails',
					'S2Nihss24Hrs',
					'S2Nihss24HrsNK',
					'S2SwallowScreening4HrsDateTime',
					'S2SwallowScreening4HrsTimeNotEntered',
					'S2SwallowScreening4HrsNotPerformed',
					'S2SwallowScreening4HrsNotPerformedReason',
					'S2TIAInLastMonth',
					'S2NeurovascularClinicAssessed',
					'S2BarthelBeforeStroke',
					'S2BrainImagingModality',
					'S3PalliativeCare',
					'S3PalliativeCareDecisionDate',
					'S3EndOfLifePathway',
					'S3StrokeNurseAssessedDateTime',
					'S3StrokeNurseAssessedTimeNotEntered',
					'S3StrokeNurseNotAssessed',
					'S3StrokeConsultantAssessedDateTime',
					'S3StrokeConsultantAssessedTimeNotEntered',
					'S3StrokeConsultantNotAssessed',
					'S3SwallowScreening72HrsDateTime',
					'S3SwallowScreening72HrsTimeNotEntered',
					'S3SwallowScreening72HrsNotPerformed',
					'S3SwallowScreening72HrsNotPerformedReason',
					'S3OccTherapist72HrsDateTime',
					'S3OccTherapist72HrsTimeNotEntered',
					'S3OccTherapist72HrsNotAssessed',
					'S3OccTherapist72HrsNotAssessedReason',
					'S3Physio72HrsDateTime',
					'S3Physio72HrsTimeNotEntered',
					'S3Physio72HrsNotAssessed',
					'S3Physio72HrsNotAssessedReason',
					'S3SpLangTherapistComm72HrsDateTime',
					'S3SpLangTherapistComm72HrsTimeNotEntered',
					'S3SpLangTherapistComm72HrsNotAssessed',
					'S3SpLangTherapistComm72HrsNotAssessedReason',
					'S3SpLangTherapistSwallow72HrsDateTime',
					'S3SpLangTherapistSwallow72HrsTimeNotEntered',
					'S3SpLangTherapistSwallow72HrsNotAssessed',
					'S3SpLangTherapistSwallow72HrsNotAssessedReason',
					'S4ArrivalDateTime',
					'S4ArrivalTimeNotEntered',
					'S4FirstWard',
					'S4StrokeUnitArrivalDateTime',
					'S4StrokeUnitArrivalTimeNotEntered',
					'S4StrokeUnitArrivalNA',
					'S4Physio',
					'S4PhysioDays',
					'S4PhysioMinutes',
					'S4OccTher',
					'S4OccTherDays',
					'S4OccTherMinutes',
					'S4SpeechLang',
					'S4SpeechLangDays',
					'S4SpeechLangMinutes',
					'S4Psychology',
					'S4PsychologyDays',
					'S4PsychologyMinutes',
					'S4RehabGoalsDate',
					'S4RehabGoalsNone',
					'S4RehabGoalsNoneReason',
					'S5LocWorst7Days',
					'S5UrinaryTractInfection7Days',
					'S5PneumoniaAntibiotics7Days',
					'S6OccTherapistByDischargeDateTime',
					'S6OccTherapistByDischargeTimeNotEntered',
					'S6OccTherapistByDischargeNotAssessed',
					'S6OccTherapistByDischargeNotAssessedReason',
					'S6PhysioByDischargeDateTime',
					'S6PhysioByDischargeTimeNotEntered',
					'S6PhysioByDischargeNotAssessed',
					'S6PhysioByDischargeNotAssessedReason',
					'S6SpLangTherapistCommByDischargeDateTime',
					'S6SpLangTherapistCommByDischargeTimeNotEntered',
					'S6SpLangTherapistCommByDischargeNotAssessed',
					'S6SpLangTherapistCommByDischargeNotAssessedReason',
					'S6SpLangTherapistSwallowByDischargeDateTime',
					'S6SpLangTherapistSwallowByDischargeTimeNotEntered',
					'S6SpLangTherapistSwallowByDischargeNotAssessed',
					'S6SpLangTherapistSwallowByDischargeNotAssessedReason',
					'S6UrinaryContinencePlanDate',
					'S6UrinaryContinencePlanNoPlan',
					'S6UrinaryContinencePlanNoPlanReason',
					'S6MalnutritionScreening',
					'S6MalnutritionScreeningDietitianDate',
					'S6MalnutritionScreeningDietitianNotSeen',
					'S6MoodScreeningDate',
					'S6MoodScreeningNoScreening',
					'S6MoodScreeningNoScreeningReason',
					'S6CognitionScreeningDate',
					'S6CognitionScreeningNoScreening',
					'S6CognitionScreeningNoScreeningReason',
					'S6PalliativeCareByDischarge',
					'S6PalliativeCareByDischargeDate',
					'S6EndOfLifePathwayByDischarge',
					'S6FirstRehabGoalsDate',
					'S7DischargeType',
					'S7DeathDate',
					'S7StrokeUnitDeath',
					'S7TransferTeamCode',
					'S7StrokeUnitDischargeDateTime',
					'S7StrokeUnitDischargeTimeNotEntered',
					'S7HospitalDischargeDateTime',
					'S7HospitalDischargeTimeNotEntered',
					'S7EndRehabDate',
					'S7RankinDischarge',
					'S7CareHomeDischarge',
					'S7CareHomeDischargeType',
					'S7HomeDischargeType',
					'S7DischargedEsdmt',
					'S7DischargedMcrt',
					'S7AdlHelp',
					'S7AdlHelpType',
					'S7DischargeVisitsPerWeek',
					'S7DischargeVisitsPerWeekNK',
					'S7DischargeAtrialFibrillation',
					'S7DischargeAtrialFibrillationAnticoagulation',
					'S7DischargeJointCarePlanning',
					'S7DischargeNamedContact',
					'S7DischargeBarthel',
					'S7DischargePIConsent',
					'S8FollowUp',
					'S8FollowUpDate',
					'S8FollowUpType',
					'S8FollowUpBy',
					'S8FollowUpByOther',
					'S8FollowUpPIConsent',
					'S8MoodBehaviourCognitiveScreened',
					'S8MoodBehaviourCognitiveSupportNeeded',
					'S8MoodBehaviourCognitivePsychologicalSupport',
					'S8Living',
					'S8LivingOther',
					'S8Rankin6Month',
					'S8PersistentAtrialFibrillation',
					'S8TakingAntiplateletDrug',
					'S8TakingAnticoagulent',
					'S8TakingLipidLowering',
					'S8TakingAntihypertensive',
					'S8SinceStrokeAnotherStroke',
					'S8SinceStrokeMyocardialInfarction',
					'S8SinceStrokeOtherHospitalisationIllness'	]
				
				def flds = []
				
				report.records.each {
					
					
					flds.push(it)
				}
				
				exportService.export('csv', response.outputStream,flds, fldParams,[:],[:], [:])
			} else {
				render "Report [reportId=${reportId}]: Not found"
			}
		}
	}
	
	private def makeReport(careActivityList, startDate, endDate, params) {
		
		def myReport = new SsnapReport()
		myReport.creationDate = new Date()
		myReport.reportType = params?.reportType
		myReport.sentToSsnap = Boolean.FALSE
		myReport.ssnapUploadStatus = ""
		myReport.ssnapUploadTime = null	
		myReport.allowOverwrite = Boolean.FALSE
		myReport.reportName = params?.reportName
		myReport.startDate = startDate
		myReport.endDate = endDate
		
		careActivityList.each{
			log.debug("Making Record: for HSI "+it.hsi)
			SsnapReportRecord record = makeRecord(it)
			log.debug("Making Record: done")
			log.debug("Adding Record to report")
			myReport.addToRecords(record)
			log.debug("Adding Record to report - done")
		}
		myReport.save(flush:true);
		myReport.errors.each {
			println it
		}
		return myReport
	}
	
	private def makeRecord(SsnapReportDataSource ds) {
		
		SsnapReportRecord record = new SsnapReportRecord()
		record.hsiVersion = 0 // ds.ssnapReportCareActivity?.version 
		record.hospitalStayId = ds.hsi
		
		makeFields(ds, record)
	}
	
	private def makeFields(SsnapReportDataSource ds, SsnapReportRecord record) {
		
		log.debug("Making Field: for record "+record.hospitalStayId)

		if ( ds ) {
			record.addToFields(new SsnapReportField(fieldName:'ImportIdentifier', fieldNumber:'1.0', fieldValue:ds.importIdentifier))
			record.addToFields(new SsnapReportField(fieldName:'S1HospitalNumber', fieldNumber:'1.1', fieldValue:ds.s1HospitalNumber))
			record.addToFields(new SsnapReportField(fieldName:'S1NHSNumber', fieldNumber:'1.2', fieldValue:ds.s1NHSNumber))
			record.addToFields(new SsnapReportField(fieldName:'S1NoNHSNumber', fieldNumber:'1.2', fieldValue:ds.s1NoNHSNumber))
			record.addToFields(new SsnapReportField(fieldName:'S1Surname', fieldNumber:'1.3', fieldValue:ds.s1Surname))
			record.addToFields(new SsnapReportField(fieldName:'S1Forename', fieldNumber:'1.4', fieldValue:ds.s1Forename))
			record.addToFields(new SsnapReportField(fieldName:'S1BirthDate', fieldNumber:'1.5', fieldValue:ds.s1BirthDate))
			record.addToFields(new SsnapReportField(fieldName:'S1AgeOnArrival', fieldNumber:'1.5', fieldValue:ds.s1AgeOnArrival))
			record.addToFields(new SsnapReportField(fieldName:'S1Gender', fieldNumber:'1.6', fieldValue:ds.s1Gender))
			record.addToFields(new SsnapReportField(fieldName:'S1PostcodeOut', fieldNumber:'1.7', fieldValue:ds.s1PostcodeOut))
			record.addToFields(new SsnapReportField(fieldName:'S1PostcodeIn', fieldNumber:'1.7', fieldValue:ds.s1PostcodeIn))
			record.addToFields(new SsnapReportField(fieldName:'S1Ethnicity', fieldNumber:'1.8', fieldValue:ds.s1Ethnicity))
			record.addToFields(new SsnapReportField(fieldName:'S1Diagnosis', fieldNumber:'1.9', fieldValue:ds.s1Diagnosis))
			record.addToFields(new SsnapReportField(fieldName:'S1OnsetInHospital', fieldNumber:'1.10', fieldValue:ds.s1OnsetInHospital))
			record.addToFields(new SsnapReportField(fieldName:'S1OnsetDateTime', fieldNumber:'1.11', fieldValue:ds.s1OnsetDateTime))
			record.addToFields(new SsnapReportField(fieldName:'S1OnsetTimeNotEntered', fieldNumber:'1.1.0.1', fieldValue:ds.s1OnsetTimeNotEntered))
			record.addToFields(new SsnapReportField(fieldName:'S1OnsetDateType', fieldNumber:'1.11.1', fieldValue:ds.s1OnsetDateType))
			record.addToFields(new SsnapReportField(fieldName:'S1OnsetTimeType', fieldNumber:'1.11.2', fieldValue:ds.s1OnsetTimeType))
			record.addToFields(new SsnapReportField(fieldName:'S1ArriveByAmbulance', fieldNumber:'1.12', fieldValue:ds.s1ArriveByAmbulance))
			record.addToFields(new SsnapReportField(fieldName:'S1AmbulanceTrust', fieldNumber:'1.12.1', fieldValue:ds.s1AmbulanceTrust))
			record.addToFields(new SsnapReportField(fieldName:'S1CadNumber', fieldNumber:'1.12.2', fieldValue:ds.s1CadNumber))
			record.addToFields(new SsnapReportField(fieldName:'S1CadNumberNK', fieldNumber:'1.12.3', fieldValue:ds.s1CadNumberNK))
			record.addToFields(new SsnapReportField(fieldName:'S1FirstArrivalDateTime', fieldNumber:'1.13', fieldValue:ds.s1FirstArrivalDateTime))
			record.addToFields(new SsnapReportField(fieldName:'S1FirstArrivalTimeNotEntered', fieldNumber:'1.13.1', fieldValue:ds.s1FirstArrivalTimeNotEntered))
			record.addToFields(new SsnapReportField(fieldName:'S1FirstWard', fieldNumber:'1.14', fieldValue:ds.s1FirstWard))
			record.addToFields(new SsnapReportField(fieldName:'S1FirstStrokeUnitArrivalDateTime', fieldNumber:'1.15', fieldValue:ds.s1FirstStrokeUnitArrivalDateTime))
			record.addToFields(new SsnapReportField(fieldName:'S1FirstStrokeUnitArrivalTimeNotEntered', fieldNumber:'1.15.1', fieldValue:ds.s1FirstStrokeUnitArrivalTimeNotEntered))
			record.addToFields(new SsnapReportField(fieldName:'S1FirstStrokeUnitArrivalNA', fieldNumber:'1.15.2', fieldValue:ds.s1FirstStrokeUnitArrivalNA))
			record.addToFields(new SsnapReportField(fieldName:'S2CoMCongestiveHeartFailure', fieldNumber:'2.1.1', fieldValue:ds.s2CoMCongestiveHeartFailure))
			record.addToFields(new SsnapReportField(fieldName:'S2CoMHypertension', fieldNumber:'2.1.2', fieldValue:ds.s2CoMHypertension))
			record.addToFields(new SsnapReportField(fieldName:'S2CoMAtrialFibrilation', fieldNumber:'2.1.3', fieldValue:ds.s2CoMAtrialFibrilation))
			record.addToFields(new SsnapReportField(fieldName:'S2CoMDiabetes', fieldNumber:'2.1.4', fieldValue:ds.s2CoMDiabetes))
			record.addToFields(new SsnapReportField(fieldName:'S2CoMStrokeTIA', fieldNumber:'2.1.5', fieldValue:ds.s2CoMStrokeTIA))
			record.addToFields(new SsnapReportField(fieldName:'S2CoMAFAntiplatelet', fieldNumber:'2.1.6', fieldValue:ds.s2CoMAFAntiplatelet))
			record.addToFields(new SsnapReportField(fieldName:'S2CoMAFAnticoagulent', fieldNumber:'2.1.7', fieldValue:ds.s2CoMAFAnticoagulent))
			record.addToFields(new SsnapReportField(fieldName:'S2RankinBeforeStroke', fieldNumber:'2.2', fieldValue:ds.s2RankinBeforeStroke))
			record.addToFields(new SsnapReportField(fieldName:'S2NihssArrival', fieldNumber:'2.3', fieldValue:ds.s2NihssArrival))
			record.addToFields(new SsnapReportField(fieldName:'S2NihssArrivalLoc', fieldNumber:'2.3.1', fieldValue:ds.s2NihssArrivalLoc))
			record.addToFields(new SsnapReportField(fieldName:'S2NihssArrivalLocQuestions', fieldNumber:'2.3.2', fieldValue:ds.s2NihssArrivalLocQuestions))
			record.addToFields(new SsnapReportField(fieldName:'S2NihssArrivalLocCommands', fieldNumber:'2.3.3', fieldValue:ds.s2NihssArrivalLocCommands))
			record.addToFields(new SsnapReportField(fieldName:'S2NihssArrivalBestGaze', fieldNumber:'2.3.4', fieldValue:ds.s2NihssArrivalBestGaze))
			record.addToFields(new SsnapReportField(fieldName:'S2NihssArrivalVisual', fieldNumber:'2.3.5', fieldValue:ds.s2NihssArrivalVisual))
			record.addToFields(new SsnapReportField(fieldName:'S2NihssArrivalFacialPalsy', fieldNumber:'2.3.6', fieldValue:ds.s2NihssArrivalFacialPalsy))
			record.addToFields(new SsnapReportField(fieldName:'S2NihssArrivalMotorArmLeft', fieldNumber:'2.3.7', fieldValue:ds.s2NihssArrivalMotorArmLeft))
			record.addToFields(new SsnapReportField(fieldName:'S2NihssArrivalMotorArmRight', fieldNumber:'2.3.8', fieldValue:ds.s2NihssArrivalMotorArmRight))
			record.addToFields(new SsnapReportField(fieldName:'S2NihssArrivalMotorLegLeft', fieldNumber:'2.3.9', fieldValue:ds.s2NihssArrivalMotorLegLeft))
			record.addToFields(new SsnapReportField(fieldName:'S2NihssArrivalMotorLegRight', fieldNumber:'2.3.10', fieldValue:ds.s2NihssArrivalMotorLegRight))
			record.addToFields(new SsnapReportField(fieldName:'S2NihssArrivalLimbAtaxia', fieldNumber:'2.3.11', fieldValue:ds.s2NihssArrivalLimbAtaxia))
			record.addToFields(new SsnapReportField(fieldName:'S2NihssArrivalSensory', fieldNumber:'2.3.12', fieldValue:ds.s2NihssArrivalSensory))
			record.addToFields(new SsnapReportField(fieldName:'S2NihssArrivalBestLanguage', fieldNumber:'2.3.13', fieldValue:ds.s2NihssArrivalBestLanguage))
			record.addToFields(new SsnapReportField(fieldName:'S2NihssArrivalDysarthria', fieldNumber:'2.3.14', fieldValue:ds.s2NihssArrivalDysarthria))
			record.addToFields(new SsnapReportField(fieldName:'S2NihssArrivalExtinctionInattention', fieldNumber:'2.3.15', fieldValue:ds.s2NihssArrivalExtinctionInattention))
			record.addToFields(new SsnapReportField(fieldName:'S2BrainImagingDateTime', fieldNumber:'2.4', fieldValue:ds.s2BrainImagingDateTime))
			record.addToFields(new SsnapReportField(fieldName:'S2BrainImagingTimeNotEntered', fieldNumber:'2.4.1', fieldValue:ds.s2BrainImagingTimeNotEntered))
			record.addToFields(new SsnapReportField(fieldName:'S2BrainImagingNotPerformed', fieldNumber:'2.4.2', fieldValue:ds.s2BrainImagingNotPerformed))
			record.addToFields(new SsnapReportField(fieldName:'S2StrokeType', fieldNumber:'2.5', fieldValue:ds.s2StrokeType))
			record.addToFields(new SsnapReportField(fieldName:'S2Thrombolysis', fieldNumber:'2.6', fieldValue:ds.s2Thrombolysis))
			record.addToFields(new SsnapReportField(fieldName:'S2ThrombolysisNoReason', fieldNumber:'2.6.1', fieldValue:ds.s2ThrombolysisNoReason))
			record.addToFields(new SsnapReportField(fieldName:'S2ThrombolysisNoButHaemorrhagic', fieldNumber:'2.6.2.1', fieldValue:ds.s2ThrombolysisNoButHaemorrhagic))
			record.addToFields(new SsnapReportField(fieldName:'S2ThrombolysisNoButTimeWindow', fieldNumber:'2.6.2.2', fieldValue:ds.s2ThrombolysisNoButTimeWindow))
			record.addToFields(new SsnapReportField(fieldName:'S2ThrombolysisNoButComorbidity', fieldNumber:'2.6.2.3', fieldValue:ds.s2ThrombolysisNoButComorbidity))
			record.addToFields(new SsnapReportField(fieldName:'S2ThrombolysisNoButMedication', fieldNumber:'2.6.2.4', fieldValue:ds.s2ThrombolysisNoButMedication))
			record.addToFields(new SsnapReportField(fieldName:'S2ThrombolysisNoButRefusal', fieldNumber:'2.6.2.5', fieldValue:ds.s2ThrombolysisNoButRefusal))
			record.addToFields(new SsnapReportField(fieldName:'S2ThrombolysisNoButAge', fieldNumber:'2.6.2.6', fieldValue:ds.s2ThrombolysisNoButAge))
			record.addToFields(new SsnapReportField(fieldName:'S2ThrombolysisNoButImproving', fieldNumber:'2.6.2.7', fieldValue:ds.s2ThrombolysisNoButImproving))
			record.addToFields(new SsnapReportField(fieldName:'S2ThrombolysisNoButTooMildSevere', fieldNumber:'2.6.2.8', fieldValue:ds.s2ThrombolysisNoButTooMildSevere))
			record.addToFields(new SsnapReportField(fieldName:'S2ThrombolysisNoButTimeUnknownWakeUp', fieldNumber:'2.6.2.9', fieldValue:ds.s2ThrombolysisNoButTimeUnknownWakeUp))
			record.addToFields(new SsnapReportField(fieldName:'S2ThrombolysisNoButOtherMedical', fieldNumber:'2.6.2.10', fieldValue:ds.s2ThrombolysisNoButOtherMedical))
			record.addToFields(new SsnapReportField(fieldName:'S2ThrombolysisDateTime', fieldNumber:'2.7', fieldValue:ds.s2ThrombolysisDateTime))
			record.addToFields(new SsnapReportField(fieldName:'S2ThrombolysisTimeNotEntered', fieldNumber:'2.7.1', fieldValue:ds.s2ThrombolysisTimeNotEntered))
			record.addToFields(new SsnapReportField(fieldName:'S2ThrombolysisComplications', fieldNumber:'2.8', fieldValue:ds.s2ThrombolysisComplications))
			record.addToFields(new SsnapReportField(fieldName:'S2ThrombolysisComplicationSih', fieldNumber:'2.8.1.1', fieldValue:ds.s2ThrombolysisComplicationSih))
			record.addToFields(new SsnapReportField(fieldName:'S2ThrombolysisComplicationAO', fieldNumber:'2.8.1.2', fieldValue:ds.s2ThrombolysisComplicationAO))
			record.addToFields(new SsnapReportField(fieldName:'S2ThrombolysisComplicationEB', fieldNumber:'2.8.1.3', fieldValue:ds.s2ThrombolysisComplicationEB))
			record.addToFields(new SsnapReportField(fieldName:'S2ThrombolysisComplicationOther', fieldNumber:'2.8.1.4', fieldValue:ds.s2ThrombolysisComplicationOther))
			record.addToFields(new SsnapReportField(fieldName:'S2ThrombolysisComplicationOtherDetails', fieldNumber:'2.8.1', fieldValue:ds.s2ThrombolysisComplicationOtherDetails))
			record.addToFields(new SsnapReportField(fieldName:'S2Nihss24Hrs', fieldNumber:'2.9', fieldValue:ds.s2Nihss24Hrs))
			record.addToFields(new SsnapReportField(fieldName:'S2Nihss24HrsNK', fieldNumber:'2.9.1', fieldValue:ds.s2Nihss24HrsNK))
			record.addToFields(new SsnapReportField(fieldName:'S2SwallowScreening4HrsDateTime', fieldNumber:'2.10', fieldValue:ds.s2SwallowScreening4HrsDateTime))
			record.addToFields(new SsnapReportField(fieldName:'S2SwallowScreening4HrsTimeNotEntered', fieldNumber:'2.10.1', fieldValue:ds.s2SwallowScreening4HrsTimeNotEntered))
			record.addToFields(new SsnapReportField(fieldName:'S2SwallowScreening4HrsNotPerformed', fieldNumber:'2.10.2', fieldValue:ds.s2SwallowScreening4HrsNotPerformed))
			record.addToFields(new SsnapReportField(fieldName:'S2SwallowScreening4HrsNotPerformedReason', fieldNumber:'2.10.1', fieldValue:ds.s2SwallowScreening4HrsNotPerformedReason))
			record.addToFields(new SsnapReportField(fieldName:'S2TIAInLastMonth', fieldNumber:'2.101', fieldValue:ds.s2TIAInLastMonth))
			record.addToFields(new SsnapReportField(fieldName:'S2NeurovascularClinicAssessed', fieldNumber:'2.102', fieldValue:ds.s2NeurovascularClinicAssessed))
			record.addToFields(new SsnapReportField(fieldName:'S2BarthelBeforeStroke', fieldNumber:'2.103', fieldValue:ds.s2BarthelBeforeStroke))
			record.addToFields(new SsnapReportField(fieldName:'S2BrainImagingModality', fieldNumber:'2.104', fieldValue:ds.s2BrainImagingModality))
			record.addToFields(new SsnapReportField(fieldName:'S3PalliativeCare', fieldNumber:'3.1', fieldValue:ds.s3PalliativeCare))
			record.addToFields(new SsnapReportField(fieldName:'S3PalliativeCareDecisionDate', fieldNumber:'3.1.1', fieldValue:ds.s3PalliativeCareDecisionDate))
			record.addToFields(new SsnapReportField(fieldName:'S3EndOfLifePathway', fieldNumber:'3.1.2', fieldValue:ds.s3EndOfLifePathway))
			record.addToFields(new SsnapReportField(fieldName:'S3StrokeNurseAssessedDateTime', fieldNumber:'3.2', fieldValue:ds.s3StrokeNurseAssessedDateTime))
			record.addToFields(new SsnapReportField(fieldName:'S3StrokeNurseAssessedTimeNotEntered', fieldNumber:'3.2.1', fieldValue:ds.s3StrokeNurseAssessedTimeNotEntered))
			record.addToFields(new SsnapReportField(fieldName:'S3StrokeNurseNotAssessed', fieldNumber:'3.2.2', fieldValue:ds.s3StrokeNurseNotAssessed))
			record.addToFields(new SsnapReportField(fieldName:'S3StrokeConsultantAssessedDateTime', fieldNumber:'3.3', fieldValue:ds.s3StrokeConsultantAssessedDateTime))
			record.addToFields(new SsnapReportField(fieldName:'S3StrokeConsultantAssessedTimeNotEntered', fieldNumber:'3.3.1', fieldValue:ds.s3StrokeConsultantAssessedTimeNotEntered))
			record.addToFields(new SsnapReportField(fieldName:'S3StrokeConsultantNotAssessed', fieldNumber:'3.3.2', fieldValue:ds.s3StrokeConsultantNotAssessed))
			record.addToFields(new SsnapReportField(fieldName:'S3SwallowScreening72HrsDateTime', fieldNumber:'3.4', fieldValue:ds.s3SwallowScreening72HrsDateTime))
			record.addToFields(new SsnapReportField(fieldName:'S3SwallowScreening72HrsTimeNotEntered', fieldNumber:'3.4.0.1', fieldValue:ds.s3SwallowScreening72HrsTimeNotEntered))
			record.addToFields(new SsnapReportField(fieldName:'S3SwallowScreening72HrsNotPerformed', fieldNumber:'3.4.0.2', fieldValue:ds.s3SwallowScreening72HrsNotPerformed))
			record.addToFields(new SsnapReportField(fieldName:'S3SwallowScreening72HrsNotPerformedReason', fieldNumber:'3.4.1', fieldValue:ds.s3SwallowScreening72HrsNotPerformedReason))
			record.addToFields(new SsnapReportField(fieldName:'S3OccTherapist72HrsDateTime', fieldNumber:'3.5', fieldValue:ds.s3OccTherapist72HrsDateTime))
			record.addToFields(new SsnapReportField(fieldName:'S3OccTherapist72HrsTimeNotEntered', fieldNumber:'3.5.0.1', fieldValue:ds.s3OccTherapist72HrsTimeNotEntered))
			record.addToFields(new SsnapReportField(fieldName:'S3OccTherapist72HrsNotAssessed', fieldNumber:'3.5.0.2', fieldValue:ds.s3OccTherapist72HrsNotAssessed))
			record.addToFields(new SsnapReportField(fieldName:'S3OccTherapist72HrsNotAssessedReason', fieldNumber:'3.5.1', fieldValue:ds.s3OccTherapist72HrsNotAssessedReason))
			record.addToFields(new SsnapReportField(fieldName:'S3Physio72HrsDateTime', fieldNumber:'3.6', fieldValue:ds.s3Physio72HrsDateTime))
			record.addToFields(new SsnapReportField(fieldName:'S3Physio72HrsTimeNotEntered', fieldNumber:'3.6.0.1', fieldValue:ds.s3Physio72HrsTimeNotEntered))
			record.addToFields(new SsnapReportField(fieldName:'S3Physio72HrsNotAssessed', fieldNumber:'3.6.0.2', fieldValue:ds.s3Physio72HrsNotAssessed))
			record.addToFields(new SsnapReportField(fieldName:'S3Physio72HrsNotAssessedReason', fieldNumber:'3.6.1', fieldValue:ds.s3Physio72HrsNotAssessedReason))
			record.addToFields(new SsnapReportField(fieldName:'S3SpLangTherapistComm72HrsDateTime', fieldNumber:'3.7', fieldValue:ds.s3SpLangTherapistComm72HrsDateTime))
			record.addToFields(new SsnapReportField(fieldName:'S3SpLangTherapistComm72HrsTimeNotEntered', fieldNumber:'3.7.0.1', fieldValue:ds.s3SpLangTherapistComm72HrsTimeNotEntered))
			record.addToFields(new SsnapReportField(fieldName:'S3SpLangTherapistComm72HrsNotAssessed', fieldNumber:'3.7.0.2', fieldValue:ds.s3SpLangTherapistComm72HrsNotAssessed))
			record.addToFields(new SsnapReportField(fieldName:'S3SpLangTherapistComm72HrsNotAssessedReason', fieldNumber:'3.7.1', fieldValue:ds.s3SpLangTherapistComm72HrsNotAssessedReason))
			record.addToFields(new SsnapReportField(fieldName:'S3SpLangTherapistSwallow72HrsDateTime', fieldNumber:'3.8', fieldValue:ds.s3SpLangTherapistSwallow72HrsDateTime))
			record.addToFields(new SsnapReportField(fieldName:'S3SpLangTherapistSwallow72HrsTimeNotEntered', fieldNumber:'3.8.0.1', fieldValue:ds.s3SpLangTherapistSwallow72HrsTimeNotEntered))
			record.addToFields(new SsnapReportField(fieldName:'S3SpLangTherapistSwallow72HrsNotAssessed', fieldNumber:'3.8.0.2', fieldValue:ds.s3SpLangTherapistSwallow72HrsNotAssessed))
			record.addToFields(new SsnapReportField(fieldName:'S3SpLangTherapistSwallow72HrsNotAssessedReason', fieldNumber:'3.8.1', fieldValue:ds.s3SpLangTherapistSwallow72HrsNotAssessedReason))
			record.addToFields(new SsnapReportField(fieldName:'S4ArrivalDateTime', fieldNumber:'4.1', fieldValue:ds.s4ArrivalDateTime))
			record.addToFields(new SsnapReportField(fieldName:'S4ArrivalTimeNotEntered', fieldNumber:'4.1.0.1', fieldValue:ds.s4ArrivalTimeNotEntered))
			record.addToFields(new SsnapReportField(fieldName:'S4FirstWard', fieldNumber:'4.2', fieldValue:ds.s4FirstWard))
			record.addToFields(new SsnapReportField(fieldName:'S4StrokeUnitArrivalDateTime', fieldNumber:'4.3', fieldValue:ds.s4StrokeUnitArrivalDateTime))
			record.addToFields(new SsnapReportField(fieldName:'S4StrokeUnitArrivalTimeNotEntered', fieldNumber:'4.3.0.1', fieldValue:ds.s4StrokeUnitArrivalTimeNotEntered))
			record.addToFields(new SsnapReportField(fieldName:'S4StrokeUnitArrivalNA', fieldNumber:'4.3.0.2', fieldValue:ds.s4StrokeUnitArrivalNA))
			record.addToFields(new SsnapReportField(fieldName:'S4Physio', fieldNumber:'4.4.1', fieldValue:ds.s4Physio))
			record.addToFields(new SsnapReportField(fieldName:'S4PhysioDays', fieldNumber:'4.5.1', fieldValue:ds.s4PhysioDays))
			record.addToFields(new SsnapReportField(fieldName:'S4PhysioMinutes', fieldNumber:'4.6.1', fieldValue:ds.s4PhysioMinutes))
			record.addToFields(new SsnapReportField(fieldName:'S4OccTher', fieldNumber:'4.4.2', fieldValue:ds.s4OccTher))
			record.addToFields(new SsnapReportField(fieldName:'S4OccTherDays', fieldNumber:'4.5.2', fieldValue:ds.s4OccTherDays))
			record.addToFields(new SsnapReportField(fieldName:'S4OccTherMinutes', fieldNumber:'4.6.2', fieldValue:ds.s4OccTherMinutes))
			record.addToFields(new SsnapReportField(fieldName:'S4SpeechLang', fieldNumber:'4.4.3', fieldValue:ds.s4SpeechLang))
			record.addToFields(new SsnapReportField(fieldName:'S4SpeechLangDays', fieldNumber:'4.5.3', fieldValue:ds.s4SpeechLangDays))
			record.addToFields(new SsnapReportField(fieldName:'S4SpeechLangMinutes', fieldNumber:'4.6.3', fieldValue:ds.s4SpeechLangMinutes))
			record.addToFields(new SsnapReportField(fieldName:'S4Psychology', fieldNumber:'4.4.4', fieldValue:ds.s4Psychology))
			record.addToFields(new SsnapReportField(fieldName:'S4PsychologyDays', fieldNumber:'4.5.4', fieldValue:ds.s4PsychologyDays))
			record.addToFields(new SsnapReportField(fieldName:'S4PsychologyMinutes', fieldNumber:'4.6.4', fieldValue:ds.s4PsychologyMinutes))
			record.addToFields(new SsnapReportField(fieldName:'S4RehabGoalsDate', fieldNumber:'4.7', fieldValue:ds.s4RehabGoalsDate))
			record.addToFields(new SsnapReportField(fieldName:'S4RehabGoalsNone', fieldNumber:'4.7.0.1', fieldValue:ds.s4RehabGoalsNone))
			record.addToFields(new SsnapReportField(fieldName:'S4RehabGoalsNoneReason', fieldNumber:'4.7.1', fieldValue:ds.s4RehabGoalsNoneReason))
			record.addToFields(new SsnapReportField(fieldName:'S5LocWorst7Days', fieldNumber:'5.1', fieldValue:ds.s5LocWorst7Days))
			record.addToFields(new SsnapReportField(fieldName:'S5UrinaryTractInfection7Days', fieldNumber:'5.2', fieldValue:ds.s5UrinaryTractInfection7Days))
			record.addToFields(new SsnapReportField(fieldName:'S5PneumoniaAntibiotics7Days', fieldNumber:'5.3', fieldValue:ds.s5PneumoniaAntibiotics7Days))
			record.addToFields(new SsnapReportField(fieldName:'S6OccTherapistByDischargeDateTime', fieldNumber:'6.1', fieldValue:ds.s6OccTherapistByDischargeDateTime))
			record.addToFields(new SsnapReportField(fieldName:'S6OccTherapistByDischargeTimeNotEntered', fieldNumber:'6.1.0.1', fieldValue:ds.s6OccTherapistByDischargeTimeNotEntered))
			record.addToFields(new SsnapReportField(fieldName:'S6OccTherapistByDischargeNotAssessed', fieldNumber:'6.1.0.2', fieldValue:ds.s6OccTherapistByDischargeNotAssessed))
			record.addToFields(new SsnapReportField(fieldName:'S6OccTherapistByDischargeNotAssessedReason', fieldNumber:'6.1.1', fieldValue:ds.s6OccTherapistByDischargeNotAssessedReason))
			record.addToFields(new SsnapReportField(fieldName:'S6PhysioByDischargeDateTime', fieldNumber:'6.2', fieldValue:ds.s6PhysioByDischargeDateTime))
			record.addToFields(new SsnapReportField(fieldName:'S6PhysioByDischargeTimeNotEntered', fieldNumber:'6.2.0.1', fieldValue:ds.s6PhysioByDischargeTimeNotEntered))
			record.addToFields(new SsnapReportField(fieldName:'S6PhysioByDischargeNotAssessed', fieldNumber:'6.2.0.2', fieldValue:ds.s6PhysioByDischargeNotAssessed))
			record.addToFields(new SsnapReportField(fieldName:'S6PhysioByDischargeNotAssessedReason', fieldNumber:'6.2.1', fieldValue:ds.s6PhysioByDischargeNotAssessedReason))
			record.addToFields(new SsnapReportField(fieldName:'S6SpLangTherapistCommByDischargeDateTime', fieldNumber:'6.3', fieldValue:ds.s6SpLangTherapistCommByDischargeDateTime))
			record.addToFields(new SsnapReportField(fieldName:'S6SpLangTherapistCommByDischargeTimeNotEntered', fieldNumber:'6.3.0.1', fieldValue:ds.s6SpLangTherapistCommByDischargeTimeNotEntered))
			record.addToFields(new SsnapReportField(fieldName:'S6SpLangTherapistCommByDischargeNotAssessed', fieldNumber:'6.3.0.2', fieldValue:ds.s6SpLangTherapistCommByDischargeNotAssessed))
			record.addToFields(new SsnapReportField(fieldName:'S6SpLangTherapistCommByDischargeNotAssessedReason', fieldNumber:'6.3.1', fieldValue:ds.s6SpLangTherapistCommByDischargeNotAssessedReason))
			record.addToFields(new SsnapReportField(fieldName:'S6SpLangTherapistSwallowByDischargeDateTime', fieldNumber:'6.4', fieldValue:ds.s6SpLangTherapistSwallowByDischargeDateTime))
			record.addToFields(new SsnapReportField(fieldName:'S6SpLangTherapistSwallowByDischargeTimeNotEntered', fieldNumber:'6.4.0.1', fieldValue:ds.s6SpLangTherapistSwallowByDischargeTimeNotEntered))
			record.addToFields(new SsnapReportField(fieldName:'S6SpLangTherapistSwallowByDischargeNotAssessed', fieldNumber:'6.4.0.2', fieldValue:ds.s6SpLangTherapistSwallowByDischargeNotAssessed))
			record.addToFields(new SsnapReportField(fieldName:'S6SpLangTherapistSwallowByDischargeNotAssessedReason', fieldNumber:'6.4.1', fieldValue:ds.s6SpLangTherapistSwallowByDischargeNotAssessedReason))
			record.addToFields(new SsnapReportField(fieldName:'S6UrinaryContinencePlanDate', fieldNumber:'6.5', fieldValue:ds.s6UrinaryContinencePlanDate))
			record.addToFields(new SsnapReportField(fieldName:'S6UrinaryContinencePlanNoPlan', fieldNumber:'6.5.0.1', fieldValue:ds.s6UrinaryContinencePlanNoPlan))
			record.addToFields(new SsnapReportField(fieldName:'S6UrinaryContinencePlanNoPlanReason', fieldNumber:'6.5.1', fieldValue:ds.s6UrinaryContinencePlanNoPlanReason))
			record.addToFields(new SsnapReportField(fieldName:'S6MalnutritionScreening', fieldNumber:'6.6', fieldValue:ds.s6MalnutritionScreening))
			record.addToFields(new SsnapReportField(fieldName:'S6MalnutritionScreeningDietitianDate', fieldNumber:'6.6.1', fieldValue:ds.s6MalnutritionScreeningDietitianDate))
			record.addToFields(new SsnapReportField(fieldName:'S6MalnutritionScreeningDietitianNotSeen', fieldNumber:'6.6.1.0', fieldValue:ds.s6MalnutritionScreeningDietitianNotSeen))
			record.addToFields(new SsnapReportField(fieldName:'S6MoodScreeningDate', fieldNumber:'6.7', fieldValue:ds.s6MoodScreeningDate))
			record.addToFields(new SsnapReportField(fieldName:'S6MoodScreeningNoScreening', fieldNumber:'6.7.0.1', fieldValue:ds.s6MoodScreeningNoScreening))
			record.addToFields(new SsnapReportField(fieldName:'S6MoodScreeningNoScreeningReason', fieldNumber:'6.7.1', fieldValue:ds.s6MoodScreeningNoScreeningReason))
			record.addToFields(new SsnapReportField(fieldName:'S6CognitionScreeningDate', fieldNumber:'6.8', fieldValue:ds.s6CognitionScreeningDate))
			record.addToFields(new SsnapReportField(fieldName:'S6CognitionScreeningNoScreening', fieldNumber:'6.8.0.1', fieldValue:ds.s6CognitionScreeningNoScreening))
			record.addToFields(new SsnapReportField(fieldName:'S6CognitionScreeningNoScreeningReason', fieldNumber:'6.8.1', fieldValue:ds.s6CognitionScreeningNoScreeningReason))
			record.addToFields(new SsnapReportField(fieldName:'S6PalliativeCareByDischarge', fieldNumber:'6.9', fieldValue:ds.s6PalliativeCareByDischarge))
			record.addToFields(new SsnapReportField(fieldName:'S6PalliativeCareByDischargeDate', fieldNumber:'6.9.1', fieldValue:ds.s6PalliativeCareByDischargeDate))
			record.addToFields(new SsnapReportField(fieldName:'S6EndOfLifePathwayByDischarge', fieldNumber:'6.9.2', fieldValue:ds.s6EndOfLifePathwayByDischarge))
			record.addToFields(new SsnapReportField(fieldName:'S6FirstRehabGoalsDate', fieldNumber:'6.10', fieldValue:ds.s6FirstRehabGoalsDate))
			record.addToFields(new SsnapReportField(fieldName:'S7DischargeType', fieldNumber:'7.1', fieldValue:ds.s7DischargeType))
			record.addToFields(new SsnapReportField(fieldName:'S7DeathDate', fieldNumber:'7.1.1', fieldValue:ds.s7DeathDate))
			record.addToFields(new SsnapReportField(fieldName:'S7StrokeUnitDeath', fieldNumber:'7.1.2', fieldValue:ds.s7StrokeUnitDeath))
			record.addToFields(new SsnapReportField(fieldName:'S7TransferTeamCode', fieldNumber:'7.1.3', fieldValue:ds.s7TransferTeamCode))
			record.addToFields(new SsnapReportField(fieldName:'S7StrokeUnitDischargeDateTime', fieldNumber:'7.2', fieldValue:ds.s7StrokeUnitDischargeDateTime))
			record.addToFields(new SsnapReportField(fieldName:'S7StrokeUnitDischargeTimeNotEntered', fieldNumber:'7.2.0.1', fieldValue:ds.s7StrokeUnitDischargeTimeNotEntered))
			record.addToFields(new SsnapReportField(fieldName:'S7HospitalDischargeDateTime', fieldNumber:'7.3', fieldValue:ds.s7HospitalDischargeDateTime))
			record.addToFields(new SsnapReportField(fieldName:'S7HospitalDischargeTimeNotEntered', fieldNumber:'7.3.0.1', fieldValue:ds.s7HospitalDischargeTimeNotEntered))
			record.addToFields(new SsnapReportField(fieldName:'S7EndRehabDate', fieldNumber:'7.3.1', fieldValue:ds.s7EndRehabDate))
			record.addToFields(new SsnapReportField(fieldName:'S7RankinDischarge', fieldNumber:'7.4', fieldValue:ds.s7RankinDischarge))
			record.addToFields(new SsnapReportField(fieldName:'S7CareHomeDischarge', fieldNumber:'7.5', fieldValue:ds.s7CareHomeDischarge))
			record.addToFields(new SsnapReportField(fieldName:'S7CareHomeDischargeType', fieldNumber:'7.5.1', fieldValue:ds.s7CareHomeDischargeType))
			record.addToFields(new SsnapReportField(fieldName:'S7HomeDischargeType', fieldNumber:'7.6', fieldValue:ds.s7HomeDischargeType))
			record.addToFields(new SsnapReportField(fieldName:'S7DischargedEsdmt', fieldNumber:'7.7', fieldValue:ds.s7DischargedEsdmt))
			record.addToFields(new SsnapReportField(fieldName:'S7DischargedMcrt', fieldNumber:'7.8', fieldValue:ds.s7DischargedMcrt))
			record.addToFields(new SsnapReportField(fieldName:'S7AdlHelp', fieldNumber:'7.9', fieldValue:ds.s7AdlHelp))
			record.addToFields(new SsnapReportField(fieldName:'S7AdlHelpType', fieldNumber:'7.9.1', fieldValue:ds.s7AdlHelpType))
			record.addToFields(new SsnapReportField(fieldName:'S7DischargeVisitsPerWeek', fieldNumber:'7.9.2', fieldValue:ds.s7DischargeVisitsPerWeek))
			record.addToFields(new SsnapReportField(fieldName:'S7DischargeVisitsPerWeekNK', fieldNumber:'7.9.2.0.1', fieldValue:ds.s7DischargeVisitsPerWeekNK))
			record.addToFields(new SsnapReportField(fieldName:'S7DischargeAtrialFibrillation', fieldNumber:'7.10', fieldValue:ds.s7DischargeAtrialFibrillation))
			record.addToFields(new SsnapReportField(fieldName:'S7DischargeAtrialFibrillationAnticoagulation', fieldNumber:'7.10.1', fieldValue:ds.s7DischargeAtrialFibrillationAnticoagulation))
			record.addToFields(new SsnapReportField(fieldName:'S7DischargeJointCarePlanning', fieldNumber:'7.11', fieldValue:ds.s7DischargeJointCarePlanning))
			record.addToFields(new SsnapReportField(fieldName:'S7DischargeNamedContact', fieldNumber:'7.12', fieldValue:ds.s7DischargeNamedContact))
			record.addToFields(new SsnapReportField(fieldName:'S7DischargeBarthel', fieldNumber:'7.101', fieldValue:ds.s7DischargeBarthel))
			record.addToFields(new SsnapReportField(fieldName:'S7DischargePIConsent', fieldNumber:'7.102', fieldValue:ds.s7DischargePIConsent))
			record.addToFields(new SsnapReportField(fieldName:'S8FollowUp', fieldNumber:'8.1', fieldValue:ds.s8FollowUp))
			record.addToFields(new SsnapReportField(fieldName:'S8FollowUpDate', fieldNumber:'8.1.1', fieldValue:ds.s8FollowUpDate))
			record.addToFields(new SsnapReportField(fieldName:'S8FollowUpType', fieldNumber:'8.1.2', fieldValue:ds.s8FollowUpType))
			record.addToFields(new SsnapReportField(fieldName:'S8FollowUpBy', fieldNumber:'8.1.3', fieldValue:ds.s8FollowUpBy))
			record.addToFields(new SsnapReportField(fieldName:'S8FollowUpByOther', fieldNumber:'8.1.4', fieldValue:ds.s8FollowUpByOther))
			record.addToFields(new SsnapReportField(fieldName:'S8FollowUpPIConsent', fieldNumber:'8.1.5', fieldValue:ds.s8FollowUpPIConsent))
			record.addToFields(new SsnapReportField(fieldName:'S8MoodBehaviourCognitiveScreened', fieldNumber:'8.2', fieldValue:ds.s8MoodBehaviourCognitiveScreened))
			record.addToFields(new SsnapReportField(fieldName:'S8MoodBehaviourCognitiveSupportNeeded', fieldNumber:'8.2.1', fieldValue:ds.s8MoodBehaviourCognitiveSupportNeeded))
			record.addToFields(new SsnapReportField(fieldName:'S8MoodBehaviourCognitivePsychologicalSupport', fieldNumber:'8.2.2', fieldValue:ds.s8MoodBehaviourCognitivePsychologicalSupport))
			record.addToFields(new SsnapReportField(fieldName:'S8Living', fieldNumber:'8.3', fieldValue:ds.s8Living))
			record.addToFields(new SsnapReportField(fieldName:'S8LivingOther', fieldNumber:'8.3.1', fieldValue:ds.s8LivingOther))
			record.addToFields(new SsnapReportField(fieldName:'S8Rankin6Month', fieldNumber:'8.4', fieldValue:ds.s8Rankin6Month))
			record.addToFields(new SsnapReportField(fieldName:'S8PersistentAtrialFibrillation', fieldNumber:'8.5', fieldValue:ds.s8PersistentAtrialFibrillation))
			record.addToFields(new SsnapReportField(fieldName:'S8TakingAntiplateletDrug', fieldNumber:'8.6.1', fieldValue:ds.s8TakingAntiplateletDrug))
			record.addToFields(new SsnapReportField(fieldName:'S8TakingAnticoagulent', fieldNumber:'8.6.2', fieldValue:ds.s8TakingAnticoagulent))
			record.addToFields(new SsnapReportField(fieldName:'S8TakingLipidLowering', fieldNumber:'8.6.3', fieldValue:ds.s8TakingLipidLowering))
			record.addToFields(new SsnapReportField(fieldName:'S8TakingAntihypertensive', fieldNumber:'8.6.4', fieldValue:ds.s8TakingAntihypertensive))
			record.addToFields(new SsnapReportField(fieldName:'S8SinceStrokeAnotherStroke', fieldNumber:'8.7.1', fieldValue:ds.s8SinceStrokeAnotherStroke))
			record.addToFields(new SsnapReportField(fieldName:'S8SinceStrokeMyocardialInfarction', fieldNumber:'8.7.2', fieldValue:ds.s8SinceStrokeMyocardialInfarction))
			record.addToFields(new SsnapReportField(fieldName:'S8SinceStrokeOtherHospitalisationIllness', fieldNumber:'8.7.3', fieldValue:ds.s8SinceStrokeOtherHospitalisationIllness))
		}
		else {
			log.debug("Making Field: for record "+record.hospitalStayId+", no SSNAP export data found")
		}
	}
}
