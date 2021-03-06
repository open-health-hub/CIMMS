/*
   18 April 201206:21:24
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
ALTER TABLE dbo.medical_history ADD
	previous_stroke varchar(50) NULL,
	previous_tia varchar(50) NULL
GO
ALTER TABLE dbo.medical_history SET (LOCK_ESCALATION = TABLE)
GO
COMMIT
