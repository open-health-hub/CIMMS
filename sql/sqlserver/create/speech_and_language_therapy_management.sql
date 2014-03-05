CREATE TABLE [dbo].[speech_and_language_therapy_management](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT NULL,
	[version] [numeric](19, 0) NOT NULL,
	[communication_assessment_date] [datetime] NULL,
	[communication_assessment_time] [int] NULL,
	[communication_assessment_performed] [tinyint] NOT NULL,
	[no_communication_assessment_reason_type_id] [numeric](19, 0) NULL,
	[swallowing_assessment_date] [datetime] NULL,
	[swallowing_assessment_time] [int] NULL,
	[swallowing_assessment_performed] [tinyint] NOT NULL,
	[no_swallowing_assessment_reason_type_id] [numeric](19, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]