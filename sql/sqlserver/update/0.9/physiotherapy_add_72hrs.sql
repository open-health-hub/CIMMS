USE [stroke]
GO

alter table [dbo].[physiotherapy_management]
	add [assessment_performed_in72hrs] [tinyint] NULL;

alter table [dbo].[physiotherapy_management]
	add [no72hr_assessment_reason_type_id] [numeric](19,0) NULL;

alter table [dbo].[physiotherapy_management]
	add constraint [FKC1288C5BD1473F2F]
	foreign key ([no72hr_assessment_reason_type_id]) 
		references [physiotherapy_no_assessment_reason_type];
		  

