/****** Script for SelectTopNRows command from SSMS  ******/
update [stroke].[dbo].[occupational_therapy_management]
set no_mood_assessment_reason_type_id = 4
where mood_assessment_performed!='true'
GO
