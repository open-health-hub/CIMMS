/*
   15 October 201211:05:49
   User: 
   Server: BHTS-MATERNITYB
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
CREATE TABLE dbo.Tmp_thrombolysis
	(
	id numeric(19, 0) NOT NULL IDENTITY (1, 1),
	version numeric(19, 0) NOT NULL,
	thrombolysis_date datetime NOT NULL,
	complications tinyint NOT NULL,
	thrombolysis_time int NOT NULL,
	complication_type varchar(255) NULL,
	complication_type_other varchar(255) NULL,
	decision_maker_grade varchar(255) NULL,
	decision_maker_location varchar(255) NOT NULL,
	decision_maker_speciality varchar(255) NULL,
	decision_maker_speciality_other varchar(255) NULL,
	follow_up_scan tinyint NOT NULL,
	follow_up_scan_date datetime NULL,
	follow_up_scan_time int NULL,
	door_to_needle_time int NULL,
	nihss_score_at24hours int NULL
	)  ON [PRIMARY]
GO
ALTER TABLE dbo.Tmp_thrombolysis SET (LOCK_ESCALATION = TABLE)
GO
SET IDENTITY_INSERT dbo.Tmp_thrombolysis ON
GO
IF EXISTS(SELECT * FROM dbo.thrombolysis)
	 EXEC('INSERT INTO dbo.Tmp_thrombolysis (id, version, thrombolysis_date, complications, thrombolysis_time, complication_type, decision_maker_grade, decision_maker_location, decision_maker_speciality, decision_maker_speciality_other, follow_up_scan, follow_up_scan_date, follow_up_scan_time, door_to_needle_time, nihss_score_at24hours)
		SELECT id, version, thrombolysis_date, complications, thrombolysis_time, complication_type, decision_maker_grade, decision_maker_location, decision_maker_speciality, decision_maker_speciality_other, follow_up_scan, follow_up_scan_date, follow_up_scan_time, door_to_needle_time, nihss_score_at24hours FROM dbo.thrombolysis WITH (HOLDLOCK TABLOCKX)')
GO
SET IDENTITY_INSERT dbo.Tmp_thrombolysis OFF
GO
ALTER TABLE dbo.care_activity
	DROP CONSTRAINT FK787D8C7DFB21439C
GO
DROP TABLE dbo.thrombolysis
GO
EXECUTE sp_rename N'dbo.Tmp_thrombolysis', N'thrombolysis', 'OBJECT' 
GO
ALTER TABLE dbo.thrombolysis ADD CONSTRAINT
	PK__thrombol__3213E83F0C85DE4D PRIMARY KEY CLUSTERED 
	(
	id
	) WITH( STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

GO
COMMIT
select Has_Perms_By_Name(N'dbo.thrombolysis', 'Object', 'ALTER') as ALT_Per, Has_Perms_By_Name(N'dbo.thrombolysis', 'Object', 'VIEW DEFINITION') as View_def_Per, Has_Perms_By_Name(N'dbo.thrombolysis', 'Object', 'CONTROL') as Contr_Per BEGIN TRANSACTION
GO
ALTER TABLE dbo.care_activity ADD CONSTRAINT
	FK787D8C7DFB21439C FOREIGN KEY
	(
	thrombolysis_id
	) REFERENCES dbo.thrombolysis
	(
	id
	) ON UPDATE  NO ACTION 
	 ON DELETE  NO ACTION 
	
GO
ALTER TABLE dbo.care_activity SET (LOCK_ESCALATION = TABLE)
GO
COMMIT
select Has_Perms_By_Name(N'dbo.care_activity', 'Object', 'ALTER') as ALT_Per, Has_Perms_By_Name(N'dbo.care_activity', 'Object', 'VIEW DEFINITION') as View_def_Per, Has_Perms_By_Name(N'dbo.care_activity', 'Object', 'CONTROL') as Contr_Per 