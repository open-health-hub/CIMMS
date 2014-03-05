USE [stroke]
GO

ALTER TABLE [dbo].[post_discharge_care] 
ADD [end_of_life_pathway] [varchar](255) NULL;

ALTER TABLE [dbo].[post_discharge_care] 
ADD [palliative_care] [varchar](255) NULL;

ALTER TABLE [dbo].[post_discharge_care] 
ADD [palliative_care_date] [datetime] NULL;