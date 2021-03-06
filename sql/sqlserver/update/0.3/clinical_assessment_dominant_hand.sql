/*
   20 April 201214:08:29
   User: 
   Server: D630-G2SLT3J
   Database: stroke
   Application: 
*/

/* To prevent any potential data loss issues, you should review this script in detail before running it outside the context of the database designer.*/
BEGIN TRANSACTION
SET QUOTED_IDENTIFIER ON
SET ARITHABORT ON
SET NUMERIC_ROUNDABORT OFF
SET CONCAT_NULL_YIELDS_NULL ON
SET ANSI_NULLS ON
SET ANSI_PADDING ON
SET ANSI_WARNINGS ON
COMMIT
BEGIN TRANSACTION
GO
ALTER TABLE dbo.clinical_assessment
	DROP CONSTRAINT FKA54EB6EF1C0392D
GO
ALTER TABLE dbo.glasgow_coma_score SET (LOCK_ESCALATION = TABLE)
GO
COMMIT
BEGIN TRANSACTION
GO
ALTER TABLE dbo.clinical_assessment
	DROP CONSTRAINT FKA54EB6ECB0D15DC
GO
ALTER TABLE dbo.no_swallow_screen_performed_reason_type SET (LOCK_ESCALATION = TABLE)
GO
COMMIT
BEGIN TRANSACTION
GO
ALTER TABLE dbo.clinical_assessment
	DROP CONSTRAINT FKA54EB6EAEDB53F9
GO
ALTER TABLE dbo.clinical_classification_type SET (LOCK_ESCALATION = TABLE)
GO
COMMIT
BEGIN TRANSACTION
GO
CREATE TABLE dbo.Tmp_clinical_assessment
	(
	id numeric(19, 0) NOT NULL IDENTITY (1, 1),
	version numeric(19, 0) NOT NULL,
	facial_weakness tinyint NULL,
	facial_palsy varchar(50) NULL,
	face_sensory_loss varchar(50) NULL,
	left_face_affected tinyint NULL,
	right_face_affected tinyint NULL,
	neither_face_affected tinyint NULL,
	arm_mrc_scale int NULL,
	arm_sensory_loss varchar(255) NULL,
	left_arm_affected tinyint NULL,
	right_arm_affected tinyint NULL,
	neither_arm_affected tinyint NULL,
	dominant_hand varchar(50) NULL,
	leg_mrc_scale int NULL,
	leg_sensory_loss varchar(255) NULL,
	left_leg_affected tinyint NULL,
	right_leg_affected tinyint NULL,
	neither_leg_affected tinyint NULL,
	dysarthria varchar(255) NULL,
	aphasia varchar(255) NULL,
	hemianopia varchar(255) NULL,
	inattention varchar(255) NULL,
	other varchar(255) NULL,
	other_text varchar(255) NULL,
	limb_ataxia varchar(255) NULL,
	independent varchar(255) NULL,
	walk_at_presentation tinyint NULL,
	mobile_pre_stroke tinyint NULL,
	swallow_screen_performed tinyint NULL,
	swallow_screen_date datetime NULL,
	swallow_screen_time int NULL,
	no_swallow_screen_performed_reason_id numeric(19, 0) NULL,
	classification_id numeric(19, 0) NULL,
	glasgow_coma_score_id numeric(19, 0) NULL
	)  ON [PRIMARY]
GO
ALTER TABLE dbo.Tmp_clinical_assessment SET (LOCK_ESCALATION = TABLE)
GO
SET IDENTITY_INSERT dbo.Tmp_clinical_assessment ON
GO
IF EXISTS(SELECT * FROM dbo.clinical_assessment)
	 EXEC('INSERT INTO dbo.Tmp_clinical_assessment (id, version, facial_weakness, facial_palsy, face_sensory_loss, left_face_affected, right_face_affected, neither_face_affected, arm_mrc_scale, arm_sensory_loss, left_arm_affected, right_arm_affected, neither_arm_affected, leg_mrc_scale, leg_sensory_loss, left_leg_affected, right_leg_affected, neither_leg_affected, dysarthria, aphasia, hemianopia, inattention, other, other_text, limb_ataxia, independent, walk_at_presentation, mobile_pre_stroke, swallow_screen_performed, swallow_screen_date, swallow_screen_time, no_swallow_screen_performed_reason_id, classification_id, glasgow_coma_score_id)
		SELECT id, version, facial_weakness, facial_palsy, face_sensory_loss, left_face_affected, right_face_affected, neither_face_affected, arm_mrc_scale, arm_sensory_loss, left_arm_affected, right_arm_affected, neither_arm_affected, leg_mrc_scale, leg_sensory_loss, left_leg_affected, right_leg_affected, neither_leg_affected, dysarthria, aphasia, hemianopia, inattention, other, other_text, limb_ataxia, independent, walk_at_presentation, mobile_pre_stroke, swallow_screen_performed, swallow_screen_date, swallow_screen_time, no_swallow_screen_performed_reason_id, classification_id, glasgow_coma_score_id FROM dbo.clinical_assessment WITH (HOLDLOCK TABLOCKX)')
GO
SET IDENTITY_INSERT dbo.Tmp_clinical_assessment OFF
GO
ALTER TABLE dbo.care_activity
	DROP CONSTRAINT FK787D8C7DBD56D383
GO
DROP TABLE dbo.clinical_assessment
GO
EXECUTE sp_rename N'dbo.Tmp_clinical_assessment', N'clinical_assessment', 'OBJECT' 
GO
ALTER TABLE dbo.clinical_assessment ADD CONSTRAINT
	PK__clinical__3213E83F07020F21 PRIMARY KEY CLUSTERED 
	(
	id
	) WITH( STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

GO
ALTER TABLE dbo.clinical_assessment ADD CONSTRAINT
	FKA54EB6EAEDB53F9 FOREIGN KEY
	(
	classification_id
	) REFERENCES dbo.clinical_classification_type
	(
	id
	) ON UPDATE  NO ACTION 
	 ON DELETE  NO ACTION 
	
GO
ALTER TABLE dbo.clinical_assessment ADD CONSTRAINT
	FKA54EB6ECB0D15DC FOREIGN KEY
	(
	no_swallow_screen_performed_reason_id
	) REFERENCES dbo.no_swallow_screen_performed_reason_type
	(
	id
	) ON UPDATE  NO ACTION 
	 ON DELETE  NO ACTION 
	
GO
ALTER TABLE dbo.clinical_assessment ADD CONSTRAINT
	FKA54EB6EF1C0392D FOREIGN KEY
	(
	glasgow_coma_score_id
	) REFERENCES dbo.glasgow_coma_score
	(
	id
	) ON UPDATE  NO ACTION 
	 ON DELETE  NO ACTION 
	
GO
COMMIT
BEGIN TRANSACTION
GO
ALTER TABLE dbo.care_activity ADD CONSTRAINT
	FK787D8C7DBD56D383 FOREIGN KEY
	(
	clinical_assessment_id
	) REFERENCES dbo.clinical_assessment
	(
	id
	) ON UPDATE  NO ACTION 
	 ON DELETE  NO ACTION 
	
GO
ALTER TABLE dbo.care_activity SET (LOCK_ESCALATION = TABLE)
GO
COMMIT
