package uk.ac.leeds.lihs.auecr.cimss.reporting.ssnap


class SsnapReportDataSource {

	String hsi
	String importIdentifier
	String s1HospitalNumber
	String s1NHSNumber
	String s1NoNHSNumber
	String s1Surname
	String s1Forename
	String s1BirthDate
	String s1AgeOnArrival
	String s1Gender
	String s1PostcodeOut
	String s1PostcodeIn
	String s1Ethnicity
	String s1Diagnosis
	String s1OnsetInHospital
	String s1OnsetDateTime
	String s1OnsetTimeNotEntered
	String s1OnsetDateType
	String s1OnsetTimeType
	String s1ArriveByAmbulance
	String s1AmbulanceTrust
	String s1CadNumber
	String s1CadNumberNK
	String s1FirstArrivalDateTime
	String s1FirstArrivalTimeNotEntered
	String s1FirstWard
	String s1FirstStrokeUnitArrivalDateTime
	String s1FirstStrokeUnitArrivalTimeNotEntered
	String s1FirstStrokeUnitArrivalNA
	String s2CoMCongestiveHeartFailure
	String s2CoMHypertension
	String s2CoMAtrialFibrilation
	String s2CoMDiabetes
	String s2CoMStrokeTIA
	String s2CoMAFAntiplatelet
	String s2CoMAFAnticoagulent
	String s2RankinBeforeStroke
	String s2NihssArrival
	String s2NihssArrivalLoc
	String s2NihssArrivalLocQuestions
	String s2NihssArrivalLocCommands
	String s2NihssArrivalBestGaze
	String s2NihssArrivalVisual
	String s2NihssArrivalFacialPalsy
	String s2NihssArrivalMotorArmLeft
	String s2NihssArrivalMotorArmRight
	String s2NihssArrivalMotorLegLeft
	String s2NihssArrivalMotorLegRight
	String s2NihssArrivalLimbAtaxia
	String s2NihssArrivalSensory
	String s2NihssArrivalBestLanguage
	String s2NihssArrivalDysarthria
	String s2NihssArrivalExtinctionInattention
	String s2BrainImagingDateTime
	String s2BrainImagingTimeNotEntered
	String s2BrainImagingNotPerformed
	String s2StrokeType
	String s2Thrombolysis
	String s2ThrombolysisNoReason
	String s2ThrombolysisNoButHaemorrhagic
	String s2ThrombolysisNoButTimeWindow
	String s2ThrombolysisNoButComorbidity
	String s2ThrombolysisNoButMedication
	String s2ThrombolysisNoButRefusal
	String s2ThrombolysisNoButAge
	String s2ThrombolysisNoButImproving
	String s2ThrombolysisNoButTooMildSevere
	String s2ThrombolysisNoButTimeUnknownWakeUp
	String s2ThrombolysisNoButOtherMedical
	String s2ThrombolysisDateTime
	String s2ThrombolysisTimeNotEntered
	String s2ThrombolysisComplications
	String s2ThrombolysisComplicationSih
	String s2ThrombolysisComplicationAO
	String s2ThrombolysisComplicationEB
	String s2ThrombolysisComplicationOther
	String s2ThrombolysisComplicationOtherDetails
	String s2Nihss24Hrs
	String s2Nihss24HrsNK
	String s2SwallowScreening4HrsDateTime
	String s2SwallowScreening4HrsTimeNotEntered
	String s2SwallowScreening4HrsNotPerformed
	String s2SwallowScreening4HrsNotPerformedReason
	String s2TIAInLastMonth
	String s2NeurovascularClinicAssessed
	String s2BarthelBeforeStroke
	String s2BrainImagingModality
	String s3PalliativeCare
	String s3PalliativeCareDecisionDate
	String s3EndOfLifePathway
	String s3StrokeNurseAssessedDateTime
	String s3StrokeNurseAssessedTimeNotEntered
	String s3StrokeNurseNotAssessed
	String s3StrokeConsultantAssessedDateTime
	String s3StrokeConsultantAssessedTimeNotEntered
	String s3StrokeConsultantNotAssessed
	String s3SwallowScreening72HrsDateTime
	String s3SwallowScreening72HrsTimeNotEntered
	String s3SwallowScreening72HrsNotPerformed
	String s3SwallowScreening72HrsNotPerformedReason
	String s3OccTherapist72HrsDateTime
	String s3OccTherapist72HrsTimeNotEntered
	String s3OccTherapist72HrsNotAssessed
	String s3OccTherapist72HrsNotAssessedReason
	String s3Physio72HrsDateTime
	String s3Physio72HrsTimeNotEntered
	String s3Physio72HrsNotAssessed
	String s3Physio72HrsNotAssessedReason
	String s3SpLangTherapistComm72HrsDateTime
	String s3SpLangTherapistComm72HrsTimeNotEntered
	String s3SpLangTherapistComm72HrsNotAssessed
	String s3SpLangTherapistComm72HrsNotAssessedReason
	String s3SpLangTherapistSwallow72HrsDateTime
	String s3SpLangTherapistSwallow72HrsTimeNotEntered
	String s3SpLangTherapistSwallow72HrsNotAssessed
	String s3SpLangTherapistSwallow72HrsNotAssessedReason
	String s4ArrivalDateTime
	String s4ArrivalTimeNotEntered
	String s4FirstWard
	String s4StrokeUnitArrivalDateTime
	String s4StrokeUnitArrivalTimeNotEntered
	String s4StrokeUnitArrivalNA
	String s4Physio
	String s4PhysioDays
	String s4PhysioMinutes
	String s4OccTher
	String s4OccTherDays
	String s4OccTherMinutes
	String s4SpeechLang
	String s4SpeechLangDays
	String s4SpeechLangMinutes
	String s4Psychology
	String s4PsychologyDays
	String s4PsychologyMinutes
	String s4RehabGoalsDate
	String s4RehabGoalsNone
	String s4RehabGoalsNoneReason
	String s5LocWorst7Days
	String s5UrinaryTractInfection7Days
	String s5PneumoniaAntibiotics7Days
	String s6OccTherapistByDischargeDateTime
	String s6OccTherapistByDischargeTimeNotEntered
	String s6OccTherapistByDischargeNotAssessed
	String s6OccTherapistByDischargeNotAssessedReason
	String s6PhysioByDischargeDateTime
	String s6PhysioByDischargeTimeNotEntered
	String s6PhysioByDischargeNotAssessed
	String s6PhysioByDischargeNotAssessedReason
	String s6SpLangTherapistCommByDischargeDateTime
	String s6SpLangTherapistCommByDischargeTimeNotEntered
	String s6SpLangTherapistCommByDischargeNotAssessed
	String s6SpLangTherapistCommByDischargeNotAssessedReason
	String s6SpLangTherapistSwallowByDischargeDateTime
	String s6SpLangTherapistSwallowByDischargeTimeNotEntered
	String s6SpLangTherapistSwallowByDischargeNotAssessed
	String s6SpLangTherapistSwallowByDischargeNotAssessedReason
	String s6UrinaryContinencePlanDate
	String s6UrinaryContinencePlanNoPlan
	String s6UrinaryContinencePlanNoPlanReason
	String s6MalnutritionScreening
	String s6MalnutritionScreeningDietitianDate
	String s6MalnutritionScreeningDietitianNotSeen
	String s6MoodScreeningDate
	String s6MoodScreeningNoScreening
	String s6MoodScreeningNoScreeningReason
	String s6CognitionScreeningDate
	String s6CognitionScreeningNoScreening
	String s6CognitionScreeningNoScreeningReason
	String s6PalliativeCareByDischarge
	String s6PalliativeCareByDischargeDate
	String s6EndOfLifePathwayByDischarge
	String s6FirstRehabGoalsDate
	String s7DischargeType
	String s7DeathDate
	String s7StrokeUnitDeath
	String s7TransferTeamCode
	String s7StrokeUnitDischargeDateTime
	String s7StrokeUnitDischargeTimeNotEntered
	String s7HospitalDischargeDateTime
	String s7HospitalDischargeTimeNotEntered
	String s7EndRehabDate
	String s7RankinDischarge
	String s7CareHomeDischarge
	String s7CareHomeDischargeType
	String s7HomeDischargeType
	String s7DischargedEsdmt
	String s7DischargedMcrt
	String s7AdlHelp
	String s7AdlHelpType
	String s7DischargeVisitsPerWeek
	String s7DischargeVisitsPerWeekNK
	String s7DischargeAtrialFibrillation
	String s7DischargeAtrialFibrillationAnticoagulation
	String s7DischargeJointCarePlanning
	String s7DischargeNamedContact
	String s7DischargeBarthel
	String s7DischargePIConsent
	String s8FollowUp
	String s8FollowUpDate
	String s8FollowUpType
	String s8FollowUpBy
	String s8FollowUpByOther
	String s8FollowUpPIConsent
	String s8MoodBehaviourCognitiveScreened
	String s8MoodBehaviourCognitiveSupportNeeded
	String s8MoodBehaviourCognitivePsychologicalSupport
	String s8Living
	String s8LivingOther
	String s8Rankin6Month
	String s8PersistentAtrialFibrillation
	String s8TakingAntiplateletDrug
	String s8TakingAnticoagulent
	String s8TakingLipidLowering
	String s8TakingAntihypertensive
	String s8SinceStrokeAnotherStroke
	String s8SinceStrokeMyocardialInfarction
	String s8SinceStrokeOtherHospitalisationIllness
	
	SsnapReportCareActivity ssnapReportCareActivity
	
    static constraints = {
    }
		
	
	static mapping = {
		table 'SsnapExport'
		cache usage:'read-only'
		id name: 'hsi', type: 'string'
		version false

		ssnapReportCareActivity column: 'care_activity_id', insertable: false, uupdateable:false, lazy: false
		hsi     column: 'hsi', insertable: false, updateable:false, lazy: false
		importIdentifier     column: 'ImportIdentifier'
		s1HospitalNumber     column: 'S1HospitalNumber'
		s1NHSNumber     column: 'S1NHSNumber'
		s1NoNHSNumber     column: 'S1NoNHSNumber'
		s1Surname     column: 'S1Surname'
		s1Forename     column: 'S1Forename'
		s1BirthDate     column: 'S1BirthDate'
		s1AgeOnArrival     column: 'S1AgeOnArrival'
		s1Gender     column: 'S1Gender'
		s1PostcodeOut     column: 'S1PostcodeOut'
		s1PostcodeIn     column: 'S1PostcodeIn'
		s1Ethnicity     column: 'S1Ethnicity'
		s1Diagnosis     column: 'S1Diagnosis'
		s1OnsetInHospital     column: 'S1OnsetInHospital'
		s1OnsetDateTime     column: 'S1OnsetDateTime'
		s1OnsetTimeNotEntered     column: 'S1OnsetTimeNotEntered'
		s1OnsetDateType     column: 'S1OnsetDateType'
		s1OnsetTimeType     column: 'S1OnsetTimeType'
		s1ArriveByAmbulance     column: 'S1ArriveByAmbulance'
		s1AmbulanceTrust     column: 'S1AmbulanceTrust'
		s1CadNumber     column: 'S1CadNumber'
		s1CadNumberNK     column: 'S1CadNumberNK'
		s1FirstArrivalDateTime     column: 'S1FirstArrivalDateTime'
		s1FirstArrivalTimeNotEntered     column: 'S1FirstArrivalTimeNotEntered'
		s1FirstWard     column: 'S1FirstWard'
		s1FirstStrokeUnitArrivalDateTime     column: 'S1FirstStrokeUnitArrivalDateTime'
		s1FirstStrokeUnitArrivalTimeNotEntered     column: 'S1FirstStrokeUnitArrivalTimeNotEntered'
		s1FirstStrokeUnitArrivalNA     column: 'S1FirstStrokeUnitArrivalNA'
		s2CoMCongestiveHeartFailure     column: 'S2CoMCongestiveHeartFailure'
		s2CoMHypertension     column: 'S2CoMHypertension'
		s2CoMAtrialFibrilation     column: 'S2CoMAtrialFibrilation'
		s2CoMDiabetes     column: 'S2CoMDiabetes'
		s2CoMStrokeTIA     column: 'S2CoMStrokeTIA'
		s2CoMAFAntiplatelet     column: 'S2CoMAFAntiplatelet'
		s2CoMAFAnticoagulent     column: 'S2CoMAFAnticoagulent'
		s2RankinBeforeStroke     column: 'S2RankinBeforeStroke'
		s2NihssArrival     column: 'S2NihssArrival'
		s2NihssArrivalLoc     column: 'S2NihssArrivalLoc'
		s2NihssArrivalLocQuestions     column: 'S2NihssArrivalLocQuestions'
		s2NihssArrivalLocCommands     column: 'S2NihssArrivalLocCommands'
		s2NihssArrivalBestGaze     column: 'S2NihssArrivalBestGaze'
		s2NihssArrivalVisual     column: 'S2NihssArrivalVisual'
		s2NihssArrivalFacialPalsy     column: 'S2NihssArrivalFacialPalsy'
		s2NihssArrivalMotorArmLeft     column: 'S2NihssArrivalMotorArmLeft'
		s2NihssArrivalMotorArmRight     column: 'S2NihssArrivalMotorArmRight'
		s2NihssArrivalMotorLegLeft     column: 'S2NihssArrivalMotorLegLeft'
		s2NihssArrivalMotorLegRight     column: 'S2NihssArrivalMotorLegRight'
		s2NihssArrivalLimbAtaxia     column: 'S2NihssArrivalLimbAtaxia'
		s2NihssArrivalSensory     column: 'S2NihssArrivalSensory'
		s2NihssArrivalBestLanguage     column: 'S2NihssArrivalBestLanguage'
		s2NihssArrivalDysarthria     column: 'S2NihssArrivalDysarthria'
		s2NihssArrivalExtinctionInattention     column: 'S2NihssArrivalExtinctionInattention'
		s2BrainImagingDateTime     column: 'S2BrainImagingDateTime'
		s2BrainImagingTimeNotEntered     column: 'S2BrainImagingTimeNotEntered'
		s2BrainImagingNotPerformed     column: 'S2BrainImagingNotPerformed'
		s2StrokeType     column: 'S2StrokeType'
		s2Thrombolysis     column: 'S2Thrombolysis'
		s2ThrombolysisNoReason     column: 'S2ThrombolysisNoReason'
		s2ThrombolysisNoButHaemorrhagic     column: 'S2ThrombolysisNoButHaemorrhagic'
		s2ThrombolysisNoButTimeWindow     column: 'S2ThrombolysisNoButTimeWindow'
		s2ThrombolysisNoButComorbidity     column: 'S2ThrombolysisNoButComorbidity'
		s2ThrombolysisNoButMedication     column: 'S2ThrombolysisNoButMedication'
		s2ThrombolysisNoButRefusal     column: 'S2ThrombolysisNoButRefusal'
		s2ThrombolysisNoButAge     column: 'S2ThrombolysisNoButAge'
		s2ThrombolysisNoButImproving     column: 'S2ThrombolysisNoButImproving'
		s2ThrombolysisNoButTooMildSevere     column: 'S2ThrombolysisNoButTooMildSevere'
		s2ThrombolysisNoButTimeUnknownWakeUp     column: 'S2ThrombolysisNoButTimeUnknownWakeUp'
		s2ThrombolysisNoButOtherMedical     column: 'S2ThrombolysisNoButOtherMedical'
		s2ThrombolysisDateTime     column: 'S2ThrombolysisDateTime'
		s2ThrombolysisTimeNotEntered     column: 'S2ThrombolysisTimeNotEntered'
		s2ThrombolysisComplications     column: 'S2ThrombolysisComplications'
		s2ThrombolysisComplicationSih     column: 'S2ThrombolysisComplicationSih'
		s2ThrombolysisComplicationAO     column: 'S2ThrombolysisComplicationAO'
		s2ThrombolysisComplicationEB     column: 'S2ThrombolysisComplicationEB'
		s2ThrombolysisComplicationOther     column: 'S2ThrombolysisComplicationOther'
		s2ThrombolysisComplicationOtherDetails     column: 'S2ThrombolysisComplicationOtherDetails'
		s2Nihss24Hrs     column: 'S2Nihss24Hrs'
		s2Nihss24HrsNK     column: 'S2Nihss24HrsNK'
		s2SwallowScreening4HrsDateTime     column: 'S2SwallowScreening4HrsDateTime'
		s2SwallowScreening4HrsTimeNotEntered     column: 'S2SwallowScreening4HrsTimeNotEntered'
		s2SwallowScreening4HrsNotPerformed     column: 'S2SwallowScreening4HrsNotPerformed'
		s2SwallowScreening4HrsNotPerformedReason     column: 'S2SwallowScreening4HrsNotPerformedReason'
		s2TIAInLastMonth     column: 'S2TIAInLastMonth'
		s2NeurovascularClinicAssessed     column: 'S2NeurovascularClinicAssessed'
		s2BarthelBeforeStroke     column: 'S2BarthelBeforeStroke'
		s2BrainImagingModality     column: 'S2BrainImagingModality'
		s3PalliativeCare     column: 'S3PalliativeCare'
		s3PalliativeCareDecisionDate     column: 'S3PalliativeCareDecisionDate'
		s3EndOfLifePathway     column: 'S3EndOfLifePathway'
		s3StrokeNurseAssessedDateTime     column: 'S3StrokeNurseAssessedDateTime'
		s3StrokeNurseAssessedTimeNotEntered     column: 'S3StrokeNurseAssessedTimeNotEntered'
		s3StrokeNurseNotAssessed     column: 'S3StrokeNurseNotAssessed'
		s3StrokeConsultantAssessedDateTime     column: 'S3StrokeConsultantAssessedDateTime'
		s3StrokeConsultantAssessedTimeNotEntered     column: 'S3StrokeConsultantAssessedTimeNotEntered'
		s3StrokeConsultantNotAssessed     column: 'S3StrokeConsultantNotAssessed'
		s3SwallowScreening72HrsDateTime     column: 'S3SwallowScreening72HrsDateTime'
		s3SwallowScreening72HrsTimeNotEntered     column: 'S3SwallowScreening72HrsTimeNotEntered'
		s3SwallowScreening72HrsNotPerformed     column: 'S3SwallowScreening72HrsNotPerformed'
		s3SwallowScreening72HrsNotPerformedReason     column: 'S3SwallowScreening72HrsNotPerformedReason'
		s3OccTherapist72HrsDateTime     column: 'S3OccTherapist72HrsDateTime'
		s3OccTherapist72HrsTimeNotEntered     column: 'S3OccTherapist72HrsTimeNotEntered'
		s3OccTherapist72HrsNotAssessed     column: 'S3OccTherapist72HrsNotAssessed'
		s3OccTherapist72HrsNotAssessedReason     column: 'S3OccTherapist72HrsNotAssessedReason'
		s3Physio72HrsDateTime     column: 'S3Physio72HrsDateTime'
		s3Physio72HrsTimeNotEntered     column: 'S3Physio72HrsTimeNotEntered'
		s3Physio72HrsNotAssessed     column: 'S3Physio72HrsNotAssessed'
		s3Physio72HrsNotAssessedReason     column: 'S3Physio72HrsNotAssessedReason'
		s3SpLangTherapistComm72HrsDateTime     column: 'S3SpLangTherapistComm72HrsDateTime'
		s3SpLangTherapistComm72HrsTimeNotEntered     column: 'S3SpLangTherapistComm72HrsTimeNotEntered'
		s3SpLangTherapistComm72HrsNotAssessed     column: 'S3SpLangTherapistComm72HrsNotAssessed'
		s3SpLangTherapistComm72HrsNotAssessedReason     column: 'S3SpLangTherapistComm72HrsNotAssessedReason'
		s3SpLangTherapistSwallow72HrsDateTime     column: 'S3SpLangTherapistSwallow72HrsDateTime'
		s3SpLangTherapistSwallow72HrsTimeNotEntered     column: 'S3SpLangTherapistSwallow72HrsTimeNotEntered'
		s3SpLangTherapistSwallow72HrsNotAssessed     column: 'S3SpLangTherapistSwallow72HrsNotAssessed'
		s3SpLangTherapistSwallow72HrsNotAssessedReason     column: 'S3SpLangTherapistSwallow72HrsNotAssessedReason'
		s4ArrivalDateTime     column: 'S4ArrivalDateTime'
		s4ArrivalTimeNotEntered     column: 'S4ArrivalTimeNotEntered'
		s4FirstWard     column: 'S4FirstWard'
		s4StrokeUnitArrivalDateTime     column: 'S4StrokeUnitArrivalDateTime'
		s4StrokeUnitArrivalTimeNotEntered     column: 'S4StrokeUnitArrivalTimeNotEntered'
		s4StrokeUnitArrivalNA     column: 'S4StrokeUnitArrivalNA'
		s4Physio     column: 'S4Physio'
		s4PhysioDays     column: 'S4PhysioDays'
		s4PhysioMinutes     column: 'S4PhysioMinutes'
		s4OccTher     column: 'S4OccTher'
		s4OccTherDays     column: 'S4OccTherDays'
		s4OccTherMinutes     column: 'S4OccTherMinutes'
		s4SpeechLang     column: 'S4SpeechLang'
		s4SpeechLangDays     column: 'S4SpeechLangDays'
		s4SpeechLangMinutes     column: 'S4SpeechLangMinutes'
		s4Psychology     column: 'S4Psychology'
		s4PsychologyDays     column: 'S4PsychologyDays'
		s4PsychologyMinutes     column: 'S4PsychologyMinutes'
		s4RehabGoalsDate     column: 'S4RehabGoalsDate'
		s4RehabGoalsNone     column: 'S4RehabGoalsNone'
		s4RehabGoalsNoneReason     column: 'S4RehabGoalsNoneReason'
		s5LocWorst7Days     column: 'S5LocWorst7Days'
		s5UrinaryTractInfection7Days     column: 'S5UrinaryTractInfection7Days'
		s5PneumoniaAntibiotics7Days     column: 'S5PneumoniaAntibiotics7Days'
		s6OccTherapistByDischargeDateTime     column: 'S6OccTherapistByDischargeDateTime'
		s6OccTherapistByDischargeTimeNotEntered     column: 'S6OccTherapistByDischargeTimeNotEntered'
		s6OccTherapistByDischargeNotAssessed     column: 'S6OccTherapistByDischargeNotAssessed'
		s6OccTherapistByDischargeNotAssessedReason     column: 'S6OccTherapistByDischargeNotAssessedReason'
		s6PhysioByDischargeDateTime     column: 'S6PhysioByDischargeDateTime'
		s6PhysioByDischargeTimeNotEntered     column: 'S6PhysioByDischargeTimeNotEntered'
		s6PhysioByDischargeNotAssessed     column: 'S6PhysioByDischargeNotAssessed'
		s6PhysioByDischargeNotAssessedReason     column: 'S6PhysioByDischargeNotAssessedReason'
		s6SpLangTherapistCommByDischargeDateTime     column: 'S6SpLangTherapistCommByDischargeDateTime'
		s6SpLangTherapistCommByDischargeTimeNotEntered     column: 'S6SpLangTherapistCommByDischargeTimeNotEntered'
		s6SpLangTherapistCommByDischargeNotAssessed     column: 'S6SpLangTherapistCommByDischargeNotAssessed'
		s6SpLangTherapistCommByDischargeNotAssessedReason     column: 'S6SpLangTherapistCommByDischargeNotAssessedReason'
		s6SpLangTherapistSwallowByDischargeDateTime     column: 'S6SpLangTherapistSwallowByDischargeDateTime'
		s6SpLangTherapistSwallowByDischargeTimeNotEntered     column: 'S6SpLangTherapistSwallowByDischargeTimeNotEntered'
		s6SpLangTherapistSwallowByDischargeNotAssessed     column: 'S6SpLangTherapistSwallowByDischargeNotAssessed'
		s6SpLangTherapistSwallowByDischargeNotAssessedReason     column: 'S6SpLangTherapistSwallowByDischargeNotAssessedReason'
		s6UrinaryContinencePlanDate     column: 'S6UrinaryContinencePlanDate'
		s6UrinaryContinencePlanNoPlan     column: 'S6UrinaryContinencePlanNoPlan'
		s6UrinaryContinencePlanNoPlanReason     column: 'S6UrinaryContinencePlanNoPlanReason'
		s6MalnutritionScreening     column: 'S6MalnutritionScreening'
		s6MalnutritionScreeningDietitianDate     column: 'S6MalnutritionScreeningDietitianDate'
		s6MalnutritionScreeningDietitianNotSeen     column: 'S6MalnutritionScreeningDietitianNotSeen'
		s6MoodScreeningDate     column: 'S6MoodScreeningDate'
		s6MoodScreeningNoScreening     column: 'S6MoodScreeningNoScreening'
		s6MoodScreeningNoScreeningReason     column: 'S6MoodScreeningNoScreeningReason'
		s6CognitionScreeningDate     column: 'S6CognitionScreeningDate'
		s6CognitionScreeningNoScreening     column: 'S6CognitionScreeningNoScreening'
		s6CognitionScreeningNoScreeningReason     column: 'S6CognitionScreeningNoScreeningReason'
		s6PalliativeCareByDischarge     column: 'S6PalliativeCareByDischarge'
		s6PalliativeCareByDischargeDate     column: 'S6PalliativeCareByDischargeDate'
		s6EndOfLifePathwayByDischarge     column: 'S6EndOfLifePathwayByDischarge'
		s6FirstRehabGoalsDate     column: 'S6FirstRehabGoalsDate'
		s7DischargeType     column: 'S7DischargeType'
		s7DeathDate     column: 'S7DeathDate'
		s7StrokeUnitDeath     column: 'S7StrokeUnitDeath'
		s7TransferTeamCode     column: 'S7TransferTeamCode'
		s7StrokeUnitDischargeDateTime     column: 'S7StrokeUnitDischargeDateTime'
		s7StrokeUnitDischargeTimeNotEntered     column: 'S7StrokeUnitDischargeTimeNotEntered'
		s7HospitalDischargeDateTime     column: 'S7HospitalDischargeDateTime'
		s7HospitalDischargeTimeNotEntered     column: 'S7HospitalDischargeTimeNotEntered'
		s7EndRehabDate     column: 'S7EndRehabDate'
		s7RankinDischarge     column: 'S7RankinDischarge'
		s7CareHomeDischarge     column: 'S7CareHomeDischarge'
		s7CareHomeDischargeType     column: 'S7CareHomeDischargeType'
		s7HomeDischargeType     column: 'S7HomeDischargeType'
		s7DischargedEsdmt     column: 'S7DischargedEsdmt'
		s7DischargedMcrt     column: 'S7DischargedMcrt'
		s7AdlHelp     column: 'S7AdlHelp'
		s7AdlHelpType     column: 'S7AdlHelpType'
		s7DischargeVisitsPerWeek     column: 'S7DischargeVisitsPerWeek'
		s7DischargeVisitsPerWeekNK     column: 'S7DischargeVisitsPerWeekNK'
		s7DischargeAtrialFibrillation     column: 'S7DischargeAtrialFibrillation'
		s7DischargeAtrialFibrillationAnticoagulation     column: 'S7DischargeAtrialFibrillationAnticoagulation'
		s7DischargeJointCarePlanning     column: 'S7DischargeJointCarePlanning'
		s7DischargeNamedContact     column: 'S7DischargeNamedContact'
		s7DischargeBarthel     column: 'S7DischargeBarthel'
		s7DischargePIConsent     column: 'S7DischargePIConsent'
		s8FollowUp     column: 'S8FollowUp'
		s8FollowUpDate     column: 'S8FollowUpDate'
		s8FollowUpType     column: 'S8FollowUpType'
		s8FollowUpBy     column: 'S8FollowUpBy'
		s8FollowUpByOther     column: 'S8FollowUpByOther'
		s8FollowUpPIConsent     column: 'S8FollowUpPIConsent'
		s8MoodBehaviourCognitiveScreened     column: 'S8MoodBehaviourCognitiveScreened'
		s8MoodBehaviourCognitiveSupportNeeded     column: 'S8MoodBehaviourCognitiveSupportNeeded'
		s8MoodBehaviourCognitivePsychologicalSupport     column: 'S8MoodBehaviourCognitivePsychologicalSupport'
		s8Living     column: 'S8Living'
		s8LivingOther     column: 'S8LivingOther'
		s8Rankin6Month     column: 'S8Rankin6Month'
		s8PersistentAtrialFibrillation     column: 'S8PersistentAtrialFibrillation'
		s8TakingAntiplateletDrug     column: 'S8TakingAntiplateletDrug'
		s8TakingAnticoagulent     column: 'S8TakingAnticoagulent'
		s8TakingLipidLowering     column: 'S8TakingLipidLowering'
		s8TakingAntihypertensive     column: 'S8TakingAntihypertensive'
		s8SinceStrokeAnotherStroke     column: 'S8SinceStrokeAnotherStroke'
		s8SinceStrokeMyocardialInfarction     column: 'S8SinceStrokeMyocardialInfarction'
		s8SinceStrokeOtherHospitalisationIllness     column: 'S8SinceStrokeOtherHospitalisationIllness'

	}
}
