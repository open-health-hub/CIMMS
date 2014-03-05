CREATE TABLE [dbo].[thrombolysis](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT NULL,
	[version] [numeric](19, 0) NOT NULL,
	[thrombolysis_date] [datetime] NOT NULL,
	[complications] [tinyint] NOT NULL,
	[thrombolysis_time] [int] NOT NULL,
	[complication_type] [varchar](255) NULL,
	[decision_maker_grade] [varchar](255) NOT NULL,
	[decision_maker_location] [varchar](255) NOT NULL,
	[decision_maker_speciality] [varchar](255) NOT NULL,
	[decision_maker_speciality_other] [varchar](255) NULL,
	[follow_up_scan] [tinyint] NOT NULL,
	[follow_up_scan_date] [datetime] NULL,
	[follow_up_scan_time] [int] NULL,
	[door_to_needle_time] [int] NULL,	
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]