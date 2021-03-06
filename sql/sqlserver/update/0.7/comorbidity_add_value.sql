/*
   17 October 201218:09:43
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
ALTER TABLE dbo.comorbidity
	DROP CONSTRAINT FKE7C6E6B5C88723DD
GO
ALTER TABLE dbo.comorbidity_type SET (LOCK_ESCALATION = TABLE)
GO
COMMIT
select Has_Perms_By_Name(N'dbo.comorbidity_type', 'Object', 'ALTER') as ALT_Per, Has_Perms_By_Name(N'dbo.comorbidity_type', 'Object', 'VIEW DEFINITION') as View_def_Per, Has_Perms_By_Name(N'dbo.comorbidity_type', 'Object', 'CONTROL') as Contr_Per BEGIN TRANSACTION
GO
ALTER TABLE dbo.comorbidity
	DROP CONSTRAINT FKE7C6E6B5A1C0C699
GO
ALTER TABLE dbo.medical_history SET (LOCK_ESCALATION = TABLE)
GO
COMMIT
select Has_Perms_By_Name(N'dbo.medical_history', 'Object', 'ALTER') as ALT_Per, Has_Perms_By_Name(N'dbo.medical_history', 'Object', 'VIEW DEFINITION') as View_def_Per, Has_Perms_By_Name(N'dbo.medical_history', 'Object', 'CONTROL') as Contr_Per BEGIN TRANSACTION
GO
CREATE TABLE dbo.Tmp_comorbidity
	(
	id numeric(19, 0) NOT NULL IDENTITY (1, 1),
	version numeric(19, 0) NOT NULL,
	type_id numeric(19, 0) NULL,
	value varchar(250) NULL,
	medical_history_id numeric(19, 0) NOT NULL
	)  ON [PRIMARY]
GO
ALTER TABLE dbo.Tmp_comorbidity SET (LOCK_ESCALATION = TABLE)
GO
SET IDENTITY_INSERT dbo.Tmp_comorbidity ON
GO
IF EXISTS(SELECT * FROM dbo.comorbidity)
	 EXEC('INSERT INTO dbo.Tmp_comorbidity (id, version, type_id, medical_history_id)
		SELECT id, version, type_id, medical_history_id FROM dbo.comorbidity WITH (HOLDLOCK TABLOCKX)')
GO
SET IDENTITY_INSERT dbo.Tmp_comorbidity OFF
GO
DROP TABLE dbo.comorbidity
GO
EXECUTE sp_rename N'dbo.Tmp_comorbidity', N'comorbidity', 'OBJECT' 
GO
ALTER TABLE dbo.comorbidity ADD CONSTRAINT
	PK__comorbid__3213E83F4BAC3F29 PRIMARY KEY CLUSTERED 
	(
	id
	) WITH( STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]

GO
ALTER TABLE dbo.comorbidity ADD CONSTRAINT
	FKE7C6E6B5A1C0C699 FOREIGN KEY
	(
	medical_history_id
	) REFERENCES dbo.medical_history
	(
	id
	) ON UPDATE  NO ACTION 
	 ON DELETE  NO ACTION 
	
GO
ALTER TABLE dbo.comorbidity ADD CONSTRAINT
	FKE7C6E6B5C88723DD FOREIGN KEY
	(
	type_id
	) REFERENCES dbo.comorbidity_type
	(
	id
	) ON UPDATE  NO ACTION 
	 ON DELETE  NO ACTION 
	
GO
COMMIT
select Has_Perms_By_Name(N'dbo.comorbidity', 'Object', 'ALTER') as ALT_Per, Has_Perms_By_Name(N'dbo.comorbidity', 'Object', 'VIEW DEFINITION') as View_def_Per, Has_Perms_By_Name(N'dbo.comorbidity', 'Object', 'CONTROL') as Contr_Per 