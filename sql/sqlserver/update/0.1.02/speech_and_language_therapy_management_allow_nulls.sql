/*
   24 February 201216:12:49
   User: 
   Server: 2740-2CE13203BP
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
ALTER TABLE dbo.speech_and_language_therapy_management
	DROP CONSTRAINT FKC1288C5B72134C8E
GO
ALTER TABLE dbo.swallowing_no_assessment_reason_type SET (LOCK_ESCALATION = TABLE)
GO
COMMIT
BEGIN TRANSACTION
GO
ALTER TABLE dbo.speech_and_language_therapy_management
	DROP CONSTRAINT FKC1288C5B2D5728A0
GO
ALTER TABLE dbo.communication_no_assessment_reason_type SET (LOCK_ESCALATION = TABLE)
GO
COMMIT
BEGIN TRANSACTION
GO
CREATE TABLE dbo.Tmp_speech_and_language_therapy_management
	(
	id numeric(19, 0) NOT NULL IDENTITY (1, 1),
	version numeric(19, 0) NOT NULL,
	communication_assessment_date datetime NULL,
	communication_assessment_time int NULL,
	communication_assessment_performed tinyint NULL,
	no_communication_assessment_reason_type_id numeric(19, 0) NULL,
	swallowing_assessment_date datetime NULL,
	swallowing_assessment_time int NULL,
	swallowing_assessment_performed tinyint NULL,
	no_swallowing_assessment_reason_type_id numeric(19, 0) NULL
	)  ON [PRIMARY]
GO
ALTER TABLE dbo.Tmp_speech_and_language_therapy_management SET (LOCK_ESCALATION = TABLE)
GO
SET IDENTITY_INSERT dbo.Tmp_speech_and_language_therapy_management ON
GO
IF EXISTS(SELECT * FROM dbo.speech_and_language_therapy_management)
	 EXEC('INSERT INTO dbo.Tmp_speech_and_language_therapy_management (id, version, communication_assessment_date, communication_assessment_time, communication_assessment_performed, no_communication_assessment_reason_type_id, swallowing_assessment_date, swallowing_assessment_time, swallowing_assessment_performed, no_swallowing_assessment_reason_type_id)
		SELECT id, version, communication_assessment_date, communication_assessment_time, communication_assessment_performed, no_communication_assessment_reason_type_id, swallowing_assessment_date, swallowing_assessment_time, swallowing_assessment_performed, no_swallowing_assessment_reason_type_id FROM dbo.speech_and_language_therapy_management WITH (HOLDLOCK TABLOCKX)')
GO
SET IDENTITY_INSERT dbo.Tmp_speech_and_language_therapy_management OFF
GO
ALTER TABLE dbo.therapy_management
	DROP CONSTRAINT FK4046B5D9FA3677E5
GO
DROP TABLE dbo.speech_and_language_therapy_management
GO
EXECUTE sp_rename N'dbo.Tmp_speech_and_language_therapy_management', N'speech_and_language_therapy_management', 'OBJECT' 
GO
ALTER TABLE dbo.speech_and_language_therapy_management ADD CONSTRAINT
	PK__speech_a__3213E83F66603565 PRIMARY KEY CLUSTERED 
	(
	id
	) WITH( STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

GO
ALTER TABLE dbo.speech_and_language_therapy_management ADD CONSTRAINT
	FKC1288C5B2D5728A0 FOREIGN KEY
	(
	no_communication_assessment_reason_type_id
	) REFERENCES dbo.communication_no_assessment_reason_type
	(
	id
	) ON UPDATE  NO ACTION 
	 ON DELETE  NO ACTION 
	
GO
ALTER TABLE dbo.speech_and_language_therapy_management ADD CONSTRAINT
	FKC1288C5B72134C8E FOREIGN KEY
	(
	no_swallowing_assessment_reason_type_id
	) REFERENCES dbo.swallowing_no_assessment_reason_type
	(
	id
	) ON UPDATE  NO ACTION 
	 ON DELETE  NO ACTION 
	
GO
COMMIT
BEGIN TRANSACTION
GO
ALTER TABLE dbo.therapy_management ADD CONSTRAINT
	FK4046B5D9FA3677E5 FOREIGN KEY
	(
	speech_and_language_therapy_management_id
	) REFERENCES dbo.speech_and_language_therapy_management
	(
	id
	) ON UPDATE  NO ACTION 
	 ON DELETE  NO ACTION 
	
GO
ALTER TABLE dbo.therapy_management SET (LOCK_ESCALATION = TABLE)
GO
COMMIT
