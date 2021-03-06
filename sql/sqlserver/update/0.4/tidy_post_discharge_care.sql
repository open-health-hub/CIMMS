/*
   11 September 201206:48:46
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
EXECUTE sp_rename N'dbo.post_discharge_care.visit_unknown', N'Tmp_number_of_social_service_visits_unknown', 'COLUMN' 
GO
EXECUTE sp_rename N'dbo.post_discharge_care.visit', N'Tmp_number_of_social_service_visits_1', 'COLUMN' 
GO
EXECUTE sp_rename N'dbo.post_discharge_care.Tmp_number_of_social_service_visits_unknown', N'number_of_social_service_visits_unknown', 'COLUMN' 
GO
EXECUTE sp_rename N'dbo.post_discharge_care.Tmp_number_of_social_service_visits_1', N'number_of_social_service_visits', 'COLUMN' 
GO
ALTER TABLE dbo.post_discharge_care
	DROP COLUMN help_adl, discharge_location_different, discharged_chome_with, discharged_home, esd_referral_discharge, esd_referral_date_discharge, esd_referral_date_discharge_unknown
GO
ALTER TABLE dbo.post_discharge_care SET (LOCK_ESCALATION = TABLE)
GO
COMMIT
select Has_Perms_By_Name(N'dbo.post_discharge_care', 'Object', 'ALTER') as ALT_Per, Has_Perms_By_Name(N'dbo.post_discharge_care', 'Object', 'VIEW DEFINITION') as View_def_Per, Has_Perms_By_Name(N'dbo.post_discharge_care', 'Object', 'CONTROL') as Contr_Per 