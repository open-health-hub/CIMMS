USE [stroke]
GO


ALTER TABLE [dbo].[post_discharge_care] 
ADD [ssnap_participation_consent] VARCHAR(16)  NULL;

ALTER TABLE [dbo].[post_discharge_care] 
ADD [new_care_team] VARCHAR(8)  NULL;