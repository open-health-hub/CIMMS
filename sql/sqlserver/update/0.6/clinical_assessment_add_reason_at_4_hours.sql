/*
   11 October 201214:11:57
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
ALTER TABLE dbo.clinical_assessment ADD
	no_swallow_screen_performed_reason_at4hours_id numeric(19, 0) NULL
GO
ALTER TABLE dbo.clinical_assessment SET (LOCK_ESCALATION = TABLE)
GO
COMMIT
select Has_Perms_By_Name(N'dbo.clinical_assessment', 'Object', 'ALTER') as ALT_Per, Has_Perms_By_Name(N'dbo.clinical_assessment', 'Object', 'VIEW DEFINITION') as View_def_Per, Has_Perms_By_Name(N'dbo.clinical_assessment', 'Object', 'CONTROL') as Contr_Per 