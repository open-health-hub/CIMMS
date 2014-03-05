CREATE TABLE [dbo].[care_activity](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT NULL,
	[version] [numeric](19, 0) NOT NULL,
	[start_date] [datetime] NOT NULL,
	[start_time] [int] NULL,
	[final_diagnosis] [varchar](255) NULL,
	[end_date] [datetime] NULL,
	[end_time] [int] NULL,
	[care_activities_idx] [int] NULL,
	[clinical_assessment_id] [numeric](19, 0) NULL,
	[continence_management_id] [numeric](19, 0) NULL,
	[imaging_id] [numeric](19, 0) NULL,
	[medical_history_id] [numeric](19, 0) NULL,
	[nutrition_management_id] [numeric](19, 0) NULL,
	[fluid_management_id] [numeric](19, 0) NULL,
	[therapy_management_id] [numeric](19, 0) NULL,
	[thrombolysis_id] [numeric](19, 0) NULL,
	[patient_id] [numeric](19, 0) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
