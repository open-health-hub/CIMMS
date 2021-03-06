/*
   06 March 201208:27:42
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
CREATE TABLE dbo.Tmp_nutrition_management
	(
	id numeric(19, 0) NOT NULL IDENTITY (1, 1),
	version numeric(19, 0) NOT NULL,
	date_screened datetime NULL,
	time_screened int NULL,
	must_score int NULL,
	dietitian_referral_date datetime NULL,
	dietitian_referral_time int NULL,
	unable_to_screen tinyint NULL,
	adequate_at24 varchar(255) NULL,
	inadequate_at24nutrition_reason_type_id numeric(19, 0) NULL,
	inadequate_at24reason_other varchar(255) NULL,
	adequate_at48 varchar(255) NULL,
	inadequate_at48nutrition_reason_type_id numeric(19, 0) NULL,
	inadequate_at48reason_other varchar(255) NULL,
	adequate_at72 varchar(255) NULL,
	inadequate_at72nutrition_reason_type_id numeric(19, 0) NULL,
	inadequate_at72reason_other varchar(255) NULL
	)  ON [PRIMARY]
GO
ALTER TABLE dbo.Tmp_nutrition_management SET (LOCK_ESCALATION = TABLE)
GO
SET IDENTITY_INSERT dbo.Tmp_nutrition_management ON
GO
IF EXISTS(SELECT * FROM dbo.nutrition_management)
	 EXEC('INSERT INTO dbo.Tmp_nutrition_management (id, version, date_screened, time_screened, must_score, unable_to_screen, adequate_at24, inadequate_at24nutrition_reason_type_id, inadequate_at24reason_other, adequate_at48, inadequate_at48nutrition_reason_type_id, inadequate_at48reason_other, adequate_at72, inadequate_at72nutrition_reason_type_id, inadequate_at72reason_other)
		SELECT id, version, date_screened, time_screened, must_score, unable_to_screen, adequate_at24, inadequate_at24nutrition_reason_type_id, inadequate_at24reason_other, adequate_at48, inadequate_at48nutrition_reason_type_id, inadequate_at48reason_other, adequate_at72, inadequate_at72nutrition_reason_type_id, inadequate_at72reason_other FROM dbo.nutrition_management WITH (HOLDLOCK TABLOCKX)')
GO
SET IDENTITY_INSERT dbo.Tmp_nutrition_management OFF
GO
ALTER TABLE dbo.care_activity
	DROP CONSTRAINT FK787D8C7DAA226EA9
GO
DROP TABLE dbo.nutrition_management
GO
EXECUTE sp_rename N'dbo.Tmp_nutrition_management', N'nutrition_management', 'OBJECT' 
GO
ALTER TABLE dbo.nutrition_management ADD CONSTRAINT
	PK__nutritio__3213E83F534D60F1 PRIMARY KEY CLUSTERED 
	(
	id
	) WITH( STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

GO
COMMIT
select Has_Perms_By_Name(N'dbo.nutrition_management', 'Object', 'ALTER') as ALT_Per, Has_Perms_By_Name(N'dbo.nutrition_management', 'Object', 'VIEW DEFINITION') as View_def_Per, Has_Perms_By_Name(N'dbo.nutrition_management', 'Object', 'CONTROL') as Contr_Per BEGIN TRANSACTION
GO
ALTER TABLE dbo.care_activity ADD CONSTRAINT
	FK787D8C7DAA226EA9 FOREIGN KEY
	(
	nutrition_management_id
	) REFERENCES dbo.nutrition_management
	(
	id
	) ON UPDATE  NO ACTION 
	 ON DELETE  NO ACTION 
	
GO
ALTER TABLE dbo.care_activity SET (LOCK_ESCALATION = TABLE)
GO
COMMIT
select Has_Perms_By_Name(N'dbo.care_activity', 'Object', 'ALTER') as ALT_Per, Has_Perms_By_Name(N'dbo.care_activity', 'Object', 'VIEW DEFINITION') as View_def_Per, Has_Perms_By_Name(N'dbo.care_activity', 'Object', 'CONTROL') as Contr_Per 