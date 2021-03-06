/*
   14 September 201210:12:32
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
ALTER TABLE dbo.clinical_assessment ADD
	best_gaze varchar(255) NULL,
	loc_questions varchar(255) NULL,
	loc_stimulation varchar(255) NULL,
	loc_tasks varchar(255) NULL
GO
ALTER TABLE dbo.clinical_assessment SET (LOCK_ESCALATION = TABLE)
GO
COMMIT
select Has_Perms_By_Name(N'dbo.clinical_assessment', 'Object', 'ALTER') as ALT_Per, Has_Perms_By_Name(N'dbo.clinical_assessment', 'Object', 'VIEW DEFINITION') as View_def_Per, Has_Perms_By_Name(N'dbo.clinical_assessment', 'Object', 'CONTROL') as Contr_Per 