USE [stroke]
GO

alter table [dbo].[occupational_therapy_management]
	add [assessment_performed_in72hrs] [tinyint] NULL;

alter table [dbo].[occupational_therapy_management]
	add [no72hr_assessment_reason_type_id] [numeric](19,0) NULL;

alter table [dbo].[occupational_therapy_management]
	add constraint [FKC1288C5BD1473F2A]
	foreign key ([no72hr_assessment_reason_type_id]) 
		references [occupational_therapy_no_assessment_reason_type];
		  

