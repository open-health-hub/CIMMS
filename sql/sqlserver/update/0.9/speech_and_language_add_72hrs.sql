USE [stroke]
GO

alter table [dbo].[speech_and_language_therapy_management]
	add [communication_assessment_performed_in72hrs] [tinyint] NULL;

alter table [dbo].[speech_and_language_therapy_management]
	add [no72hr_communication_assessment_reason_type_id] [numeric](19,0) NULL;

alter table [dbo].[speech_and_language_therapy_management] 
	add [no72hr_swallowing_assessment_reason_type_id] [numeric](19,0) NULL;

alter table [dbo].[speech_and_language_therapy_management] 
	add [swallowing_assessment_performed_in72hrs] [tinyint] NULL;

alter table [dbo].[speech_and_language_therapy_management]
	add constraint [FKC1288C5BD1473F09]
	foreign key ([no72hr_swallowing_assessment_reason_type_id]) 
		references [swallowing_no_assessment_reason_type];
		  
alter table [dbo].[speech_and_language_therapy_management] 
	add constraint [FKC1288C5BB7DE045]
	foreign key ([no72hr_communication_assessment_reason_type_id]) 
		references [communication_no_assessment_reason_type];
