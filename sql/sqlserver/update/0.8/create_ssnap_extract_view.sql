/*
 * SSNAP EXPORT VIEW
 * VERSION 0.8.3
 */
 
use stroke;
go

IF EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[SsnapExport]'))
    drop view dbo.SsnapExport
GO

CREATE view dbo.SsnapExport
AS
SELECT DISTINCT
	care.hospital_stay_id as hsi,
	mh.id as md_hist_id,
	'12' AS ImportIdentifier,
	patient.hospital_number AS S1HospitalNumber,
	pas.S1NHSNumber, 
	pas.S1NoNHSNumber,
	pas.S1Surname,
	pas.S1Forename,
	pas.S1BirthDate,
	pas.S1AgeOnArrival,
	pas.S1Gender,
	pas.S1PostcodeOut,
	pas.S1PostcodeIn,
	pas.S1Ethnicity,
	CASE
		WHEN diagnosisCategoryType.description = 'TIA ' THEN 'T'
		WHEN diagnosisCategoryType.description = 'Stroke ' THEN 'S'
		WHEN diagnosisCategoryType.description = 'Other ' THEN 'O'
		ELSE ''
	END as S1Diagnosis,
	pas.S1OnsetInHospital,
    CONVERT (VARCHAR(16), DATEADD( minute, mh.onset_time, mh.onset_date ), 103) + ' ' + CONVERT (VARCHAR(5), DATEADD( minute, mh.onset_time, mh.onset_date ), 114)AS S1OnsetDateTime,  
    CASE 
           WHEN mh.onset_time_unknown IS NULL THEN 1
           WHEN mh.onset_time_unknown IS NOT NULL THEN 0
    END
    AS S1OnsetTimeNotEntered,  
    CASE 
        WHEN mh.onset_date IS NOT NULL THEN 'P'
		WHEN mh.onset_date_estimated = 1 THEN 'BE'
		WHEN mh.during_sleep = 1 THEN 'DS'
		ELSE ''
	END
	AS S1OnsetDateType,
	
	CASE
		WHEN mh.onset_date IS NULL THEN ''
		WHEN mh.onset_time IS NOT NULL THEN 'P'
		WHEN mh.onset_time_estimated = '1' THEN 'BE'
		WHEN mh.onset_date_unknown = '1' THEN 'NK'
		ELSE '' 
	END 
	AS S1OnsetTimeType , 
	pas.S1ArriveByAmbulance	AS S1ArriveByAmbulance,
	pas.S1AmbulanceTrust		AS S1AmbulanceTrust,
	pas.S1CadNumber			AS S1CadNumber,
	pas.S1CadNumberNK			AS S1CadNumberNK,
	pas.S1FirstArrivalDateTime	AS S1FirstArrivalDateTime,
	pas.S1FirstArrivalTimeNotEntered	AS S1FirstArrivalTimeNotEntered,
	pas.S1FirstWard			AS S1FirstWard,
	pas.S1FirstStrokeUnitArrivalDateTime	AS S1FirstStrokeUnitArrivalDateTime,
	pas.S1FirstStrokeUnitArrivalTimeNotEntered	AS S1FirstStrokeUnitArrivalTimeNotEntered,
	pas.S1FirstStrokeUnitArrivalNA	AS S1FirstStrokeUnitArrivalNA,
	coMorb.congestive_heart_failure AS S2CoMCongestiveHeartFailure,
	coMorb.hypertension		AS S2CoMHypertension,
	coMorb.atrial_fibrillation AS S2CoMAtrialFibrilation,
	coMorb.diabetes			AS S2CoMDiabetes,
	CASE 
		WHEN mh.previous_stroke = 'yes' THEN 'Y'
		WHEN mh.previous_tia = 'yes' THEN 'Y'
		ELSE 'N'
	END
	AS S2CoMStrokeTIA,
	dbo.yesOrNo(medt.description, 'antiplatelet') 	AS S2CoMAFAntiplatelet,
	dbo.yesOrNo(medt.description, 'warfarin') 	AS S2CoMAFAnticoagulent,
	rankin.pre_admission_rankin	AS S2RankinBeforeStroke,
	pas.S2NihssArrival AS S2NihssArrival,
	clAsmt.loc_stimulation AS S2NihssArrivalLoc,	
	clAsmt.loc_questions AS S2NihssArrivalLocQuestions,
	clAsmt.loc_tasks AS S2NihssArrivalLocCommands,
	clAsmt.best_gaze AS S2NihssArrivalBestGaze,
	clAsmt.hemianopia AS S2NihssArrivalVisual,
	clAsmt.facial_palsy AS S2NihssArrivalFacialPalsy,
	clAsmt.left_arm_mrc_scale AS S2NihssArrivalMotorArmLeft,
	clAsmt.right_arm_mrc_scale AS S2NihssArrivalMotorArmRight,
	clAsmt.left_leg_mrc_scale AS S2NihssArrivalMotorLegLeft,
	clAsmt.right_leg_mrc_scale AS S2NihssArrivalMotorLegRight,
	clAsmt.limb_ataxia AS S2NihssArrivalLimbAtaxia,
	clAsmt.sensory_loss AS S2NihssArrivalSensory,
	clAsmt.aphasia AS S2NihssArrivalBestLanguage,
	clAsmt.dysarthia AS S2NihssArrivalDysarthria,
	clAsmt.inattention AS S2NihssArrivalExtinctionInattention,
	img.brainImagingDateTime AS S2BrainImagingDateTime,
	img.brainImagingTimeNotEntered AS S2BrainImagingTimeNotEntered,
	img.brainImagingNotPerformed AS S2BrainImagingNotPerformed, 
	img.strokeType AS S2StrokeType,
	CASE
		WHEN img.strokeType = 'PIH' THEN 'NB'
		ELSE tboRec.thrombolysis
	END AS S2Thrombolysis,
	tboRsn.noThrombolysisReason AS S2ThrombolysisNoReason,
	tboRsn.noThrombolysisHaemorhagic AS S2ThrombolysisNoButHaemorrhagic,
	tboRsn.noThrombolysisScanLate AS  S2ThrombolysisNoButTimeWindow,
	tboRsn.noThrombolysisComorbidity AS S2ThrombolysisNoButComorbidity,
	tboRsn.noThrombolysisMedication AS S2ThrombolysisNoButMedication,
	tboRsn.noThrombolysisRefused AS S2ThrombolysisNoButRefusal,
	tboRsn.noThrombolysisAge AS S2ThrombolysisNoButAge,
	tboRsn.noThrombolysisSymptomsImproving AS S2ThrombolysisNoButImproving,
	tboRsn.noThrombolysisTooMildOrTooSevere AS S2ThrombolysisNoButTooMildSevere, 
	tboRsn.noThrombolysisOnsetTimeUnknown AS S2ThrombolysisNoButTimeUnknownWakeUp,
	tboRsn.noThrombolysisOther AS S2ThrombolysisNoButOtherMedical,
	
	tboRec.takenDatetime AS S2ThrombolysisDateTime,
	tboRec.timeNotEntered AS S2ThrombolysisTimeNotEntered,
	tboRec.complications AS S2ThrombolysisComplications,
	tboRec.complicationHaemorrhage AS S2ThrombolysisComplicationSih,
	tboRec.complicationOedema AS S2ThrombolysisComplicationAO,
	tboRec.complicationBleed AS S2ThrombolysisComplicationEB,
	tboRec.complicationOther AS S2ThrombolysisComplicationOther,
	tboRec.complicationOtherText AS S2ThrombolysisComplicationOtherDetails,
	
	tboRec.nihss24Hrs AS S2Nihss24Hrs,
	tboRec.nihss24_unknown AS S2Nihss24HrsNK,
	swScr.swallowScreen4hrsDateTime AS S2SwallowScreening4HrsDateTime,
	swScr.swallowScreen4hrsTimeNotEntered AS S2SwallowScreening4HrsTimeNotEntered,
	swScr.swallowScreen4hrsNotPerformed AS S2SwallowScreening4HrsNotPerformed,
	swScr.swallowScreen4hrsNotPerformedReason AS S2SwallowScreening4HrsNotPerformedReason,
	medRec.previous_tia AS S2TIAInLastMonth,
	medRec.assessed_in_vascular_clinic AS S2NeurovascularClinicAssessed,
	barthelRec.barthel_score AS S2BarthelBeforeStroke,
	img.imageType AS S2BrainImagingModality,
	clinicalSummary.palliativeCare AS S3PalliativeCare,
	clinicalSummary.palliativeCareDecisionDate AS S3PalliativeCareDecisionDate,
	clinicalSummary.endOfLifePathway AS S3EndOfLifePathway,
	onset.strokeNurseAssessedDateTime AS S3StrokeNurseAssessedDateTime,
	onset.strokeNurseAssessedTimeNotEntered AS S3StrokeNurseAssessedTimeNotEntered,
	onset.strokeNurseNotAssessed AS S3StrokeNurseNotAssessed,
	onset.strokeConsultantAssessedDateTime AS S3StrokeConsultantAssessedDateTime,
	onset.strokeConsultantAssessedTimeNotEntered AS S3StrokeConsultantAssessedTimeNotEntered,
	onset.strokeConsultantNotAssessed AS S3StrokeConsultantNotAssessed,
	swScr.swallowScreen72hrsDateTime AS S3SwallowScreening72HrsDateTime,
	swScr.swallowScreen72hrsTimeNotEntered AS S3SwallowScreening72HrsTimeNotEntered, 
	swScr.swallowScreen72hrsNotPerformed AS S3SwallowScreening72HrsNotPerformed,
	swScr.swallowScreen72hrsNotPerformedReason AS S3SwallowScreening72HrsNotPerformedReason,
	occupationalTherapy.occTherapist72HrsDateTime AS S3OccTherapist72HrsDateTime,
	occupationalTherapy.occTherapist72HrsTimeNotEntered AS S3OccTherapist72HrsTimeNotEntered, 
	occupationalTherapy.occTherapist72HrsNotAssessed AS S3OccTherapist72HrsNotAssessed, 
	occupationalTherapy.occTherapist72HrsNotAssessedReason AS S3OccTherapist72HrsNotAssessedReason , 
	physiotherapy.physio72HrsDateTime AS S3Physio72HrsDateTime,
	physiotherapy.physio72HrsTimeNotEntered AS S3Physio72HrsTimeNotEntered,
	physiotherapy.physio72HrsNotAssessed AS S3Physio72HrsNotAssessed,
	physiotherapy.physio72HrsNotAssessedReason AS S3Physio72HrsNotAssessedReason, 
	speechTherapy.spchThpyComms72HrsDateTime AS S3SpLangTherapistComm72HrsDateTime,
	speechTherapy.spchThpyComms72HrsTimeNotEntered AS S3SpLangTherapistComm72HrsTimeNotEntered,
	speechTherapy.spchThpyComms72HrsNotAssessed AS S3SpLangTherapistComm72HrsNotAssessed,
	speechTherapy.spchThpyComms72HrsNotAssessedReason AS S3SpLangTherapistComm72HrsNotAssessedReason,
	speechTherapy.spchThpySwallow72HrsDateTime AS S3SpLangTherapistSwallow72HrsDateTime ,
	speechTherapy.spchThpySwallow72HrsTimeNotEntered AS S3SpLangTherapistSwallow72HrsTimeNotEntered , 
	speechTherapy.spchThpySwallow72HrsNotAssessed AS S3SpLangTherapistSwallow72HrsNotAssessed ,
	speechTherapy.spchThpySwallow72HrsNotAssessedReason AS S3SpLangTherapistSwallow72HrsNotAssessedReason , 

	--
	--  SECTION 4.1
	--
	
	pas.S4ArrivalDateTime,
	pas.S4ArrivalTimeNotEntered,
	pas.S4FirstWard,
	pas.S4StrokeUnitArrivalDateTime,
	pas.S4StrokeUnitArrivalTimeNotEntered,
	pas.S4StrokeUnitArrivalNA,

	--
	-- SECTION 4.4.1
	--
	physiotherapy.physioRequired AS S4Physio,
	physiotherapy.physioDays AS S4PhysioDays,
	physiotherapy.physioMinutes AS S4PhysioMinutes,
	occupationalTherapy.therapyRequired AS S4OccTher,
	occupationalTherapy.therapyDays AS S4OccTherDays,
	occupationalTherapy.therapyMinutes AS S4OccTherMinutes,
	speechTherapy.therapyRequired AS S4SpeechLang,
	speechTherapy.therapyDays AS S4SpeechLangDays,
	speechTherapy.therapyMinutes AS S4SpeechLangMinutes,
	therapyManagement.psychoRequired AS S4Psychology,
	therapyManagement.psychoDays AS S4PsychologyDays,
	therapyManagement.psychoMinutes AS S4PsychologyMinutes,
	therapyManagement.rehabGoalsDate AS S4RehabGoalsDate,
	therapyManagement.rehabGoalsNone AS S4RehabGoalsNone,
	therapyManagement.rehabGoalsNoneReason AS S4RehabGoalsNoneReason,

	--
	-- SECTION 5
	--
	
	clinicalSummary.locWorst7Days AS S5LocWorst7Days,
	clinicalSummary.urinaryTractInfection7Days AS S5UrinaryTractInfection7Days,
	clinicalSummary.pneumoniaAntibiotics7Days AS S5PneumoniaAntibiotics7Days,

	--
	-- SECTION 6
	--
	
	occupationalTherapy.occTherapistByDischargeDateTime AS S6OccTherapistByDischargeDateTime,
	occupationalTherapy.occTherapistByDischargeTimeNotEntered AS S6OccTherapistByDischargeTimeNotEntered,
	occupationalTherapy.occTherapistByDischargeNotAssessed AS S6OccTherapistByDischargeNotAssessed,
	occupationalTherapy.occTherapistByDischargeNotAssessedReason AS S6OccTherapistByDischargeNotAssessedReason,
	physiotherapy.physioByDischargeDateTime AS S6PhysioByDischargeDateTime,
	physiotherapy.physioByDischargeTimeNotEntered AS S6PhysioByDischargeTimeNotEntered,
	physiotherapy.physioByDischargeNotAssessed AS S6PhysioByDischargeNotAssessed,
	physiotherapy.physioByDischargeNotAssessedReason AS S6PhysioByDischargeNotAssessedReason,
	speechTherapy.spchThpyCommsByDischargeDateTime AS S6SpLangTherapistCommByDischargeDateTime,
	speechTherapy.spchThpyCommsByDischargeTimeNotEntered AS S6SpLangTherapistCommByDischargeTimeNotEntered,
	speechTherapy.spchThpyCommsByDischargeNotAssessed AS S6SpLangTherapistCommByDischargeNotAssessed,
	speechTherapy.spchThpyCommsByDischargeNotAssessedReason AS S6SpLangTherapistCommByDischargeNotAssessedReason,
	speechTherapy.spchThpySwallowByDischargeDateTime AS S6SpLangTherapistSwallowByDischargeDateTime,
	speechTherapy.spchThpySwallowByDischargeTimeNotEntered AS S6SpLangTherapistSwallowByDischargeTimeNotEntered,
	speechTherapy.spchThpySwallowByDischargeNotAssessed AS S6SpLangTherapistSwallowByDischargeNotAssessed,
	speechTherapy.spchThpySwallowByDischargeNotAssessedReason AS S6SpLangTherapistSwallowByDischargeNotAssessedReason,
	admissionAssessment.urinaryContinencePlanDate AS S6UrinaryContinencePlanDate,
	admissionAssessment.urinaryContinencePlanNoPlan AS S6UrinaryContinencePlanNoPlan,
	admissionAssessment.urinaryContinencePlanNoPlanReason AS S6UrinaryContinencePlanNoPlanReason,
	admissionAssessment.malnutritionScreening AS S6MalnutritionScreening,
	admissionAssessment.malnutritionScreeningDietitianDate AS S6MalnutritionScreeningDietitianDate,
	admissionAssessment.malnutritionScreeningDietitianNotSeen AS S6MalnutritionScreeningDietitianNotSeen,
	occupationalTherapy.moodScreeningDate AS  S6MoodScreeningDate,
	occupationalTherapy.moodScreeningnoScreening AS S6MoodScreeningNoScreening,
	occupationalTherapy.moodScreeningnoScreeningReaaon AS S6MoodScreeningNoScreeningReason,
	occupationalTherapy.cognitionScreeningDate AS S6CognitionScreeningDate,
	occupationalTherapy.cognitionScreeningNoScreening AS S6CognitionScreeningNoScreening,
	occupationalTherapy.cognitionScreeningNoScreeningReaaon AS S6CognitionScreeningNoScreeningReason,
	clinicalSummary.palliativeCareByDischarge AS S6PalliativeCareByDischarge,
	clinicalSummary.palliativeCareByDischargeDate AS S6PalliativeCareByDischargeDate,
	clinicalSummary.endOfLifePathwayByDischarge AS S6EndOfLifePathwayByDischarge,
	occupationalTherapy.firstRehabGoalsDate AS S6FirstRehabGoalsDate,
	
	--
	-- SECTION 7.1 - 7.3
	--
	postDischarge.discharge_type AS S7DischargeType,
	pas.S7DeathDate,
	pas.S7StrokeUnitDeath,
	pas.S7TransferHospitalCode,
	pas.S7StrokeUnitDischargeDateTime,
	pas.S7StrokeUnitDischargeTimeNotEntered,
	pas.S7HospitalDischargeDateTime,
	pas.S7HospitalDischargeTimeNotEntered,
	--
	-- SECTION 7.3.1 - 7.102
	--
	--
	postDischarge.end_rehab_date AS S7EndRehabDate,
	rankin.discharge_rankin AS S7RankinDischarge,
	postDischarge.care_home_discharge AS S7CareHomeDischarge,
	postDischarge.care_home_discharge_type AS S7CareHomeDischargeType,
	postDischarge.home_discharge_type AS S7HomeDischargeType,
	postDischarge.discharged_esdmt AS S7DischargedEsdmt,
	postDischarge.discharged_mcrt AS S7DischargedMcrt,
	postDischarge.adl_help AS S7AdlHelp,
	postDischarge.adl_help_type AS S7AdlHelpType,
	postDischarge.discharge_visits_per_week AS S7DischargeVisitsPerWeek,
	postDischarge.discharge_visits_per_week_nk AS S7DischargeVisitsPerWeekNK,
	postDischarge.discharge_atrial_fibrilation AS S7DischargeAtrialFibrillation,
	postDischarge.discharge_atrial_fibrillation_anticoagulation AS S7DischargeAtrialFibrillationAnticoagulation,
	postDischarge.discharge_joint_care_planning AS S7DischargeJointCarePlanning,
	postDischarge.discharge_named_contact AS S7DischargeNamedContact,
	postDischarge.discharge_barthel AS S7DischargeBarthel,
	postDischarge.discharge_pi_consent AS S7DischargePIConsent,

	--
	-- SECTION 8
	--
	pas.S8FollowUp,
	pas.S8FollowUpDate,
	pas.S8FollowUpType,
	pas.S8FollowUpBy,
	pas.S8FollowUpByOther,
	pas.S8FollowUpPIConsent,
	pas.S8MoodBehaviourCognitiveScreened,
	pas.S8MoodBehaviourCognitiveSupportNeeded,
	pas.S8MoodBehaviourCognitivePsychologicalSupport,
	pas.S8Living,
	pas.S8LivingOther,
	pas.S8Rankin6Month,
	pas.S8PersistentAtrialFibrillation,
	pas.S8TakingAntiplateletDrug,
	pas.S8TakingAnticoagulent,
	pas.S8TakingLipidLowering,
	pas.S8TakingAntihypertensive,
	pas.S8SinceStrokeAnotherStroke,
	pas.S8SinceStrokeMyocardialInfarction, 
	pas.S8SinceStrokeOtherHospitalisationIllness 
FROM
	dbo.care_activity AS care
	LEFT OUTER JOIN         
		dbo.medical_history AS mh
		ON care.medical_history_id = mh.id
    LEFT OUTER JOIN
		dbo.como() AS coMorb
		on care.hospital_stay_id = coMorb.hospital_stay_id
	LEFT OUTER JOIN 
		dbo.medication AS meds 
		ON care.medical_history_id = meds.medical_history_id
	LEFT OUTER 	JOIN
		dbo.medication_type AS medt ON meds.type_id = medt.id
	LEFt OUTER JOIN
	    dbo.rankinScore() AS rankin ON care.hospital_stay_id = rankin.hospital_stay_id
	LEFT OUTER JOIN
		dbo.clinicAsmt() As clAsmt ON care.hospital_stay_id = clAsmt.hospital_stay_id
	LEFT OUTER JOIN
		dbo.imageRec() as img ON care.medical_history_id = img.medical_history_id
	LEFT OUTER JOIN
		dbo.nothromboReason() AS tboRsn ON care.medical_history_id = tboRsn.medical_history_id
	LEFT OUTER JOIN
		dbo.thromboRec2() AS tboRec ON care.medical_history_id = tboRec.medical_history_id
	LEFT OUTER JOIN 
		dbo.swallowScreen() AS swScr ON care.hospital_stay_id = swScr.hospital_stay_id
	LEFT OUTER JOIN
		dbo.barthelRec('Pre-admission') As barthelRec ON care.medical_history_id = barthelRec.medical_history_id
	LEFT OUTER JOIN
		dbo.medical_history_rec() as medRec ON care.medical_history_id = medRec.medical_history_id
	LEFT OUTER JOIN
		dbo.clSmry() as clinicalSummary 
		on clinicalSummary.hospital_stay_id = care.hospital_stay_id
	LEFT OUTER JOIN
		dbo.strkOnset() AS onset
		ON onset.hospital_stay_id = care.hospital_stay_id
	LEFT OUTER JOIN
		dbo.spchThpy() AS speechTherapy
		ON speechTherapy.hospital_stay_id = care.hospital_stay_id
	LEFT OUTER JOIN
		dbo.occThrpy() AS occupationalTherapy
		ON occupationalTherapy.hospital_stay_id = care.hospital_stay_id
	LEFT OUTER JOIN
		dbo.physio() AS physiotherapy
		ON physiotherapy.hospital_stay_id = care.hospital_stay_id
	LEFT OUTER JOIN
		dbo.thpyMgMt() AS therapyManagement
		ON therapyManagement.hospital_stay_id = care.hospital_stay_id
	LEFT OUTER JOIN
		dbo.admissionAsmt() AS admissionAssessment 
		ON admissionAssessment.hospital_stay_id = care.hospital_stay_id
	LEFT OUTER JOIN
		dbo.postDchg() AS postDischarge
		ON postDischarge.hospital_stay_id = care.hospital_stay_id
	LEFt OUTER JOIN dbo.imaging AS imaging
		ON imaging.id = care.imaging_id		
	LEFT OUTER JOIN scan AS scan
		ON imaging.scan_id = scan.id
	LEFt OUTEr JOIN diagnosis_category_type AS diagnosisCategoryType
		ON diagnosisCategoryType.id = scan.diagnosis_category_id
	LEFT OUTER JOIN
		dbo.PAS AS PAS
		ON care.hospital_stay_id = convert(varchar(255), pas.spell_id)
	JOIN 
		[dbo].[patient_proxy] AS patient 
		ON care.patient_id = patient.id
		