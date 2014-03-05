
CREATE TABLE [dbo].[therapy_management](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT NULL,
	[version] [numeric](19, 0) NOT NULL,
	[cognitive_status_assessed] [tinyint] NOT NULL,
	[cognitive_status_assessment_date] [datetime] NULL,
	[cognitive_status_assessment_time] [int] NULL,
	[cognitive_status_no_assessment_type_id] [numeric](19, 0) NULL,
	[rehab_goals_set] [tinyint] NOT NULL,
	[rehab_goals_set_date] [datetime] NULL,
	[rehab_goals_set_time] [int] NULL,
	[rehab_goals_not_set_reason_type_id] [numeric](19, 0) NULL,
	[baseline_assessment_management_id] [numeric](19, 0) NULL,
	[physiotherapy_management_id] [numeric](19, 0) NULL,
	[occupational_therapy_management_id] [numeric](19, 0) NULL,
	[speech_and_language_therapy_management_id] [numeric](19, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]