CREATE TABLE [dbo].[baseline_assessment_management](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT NULL,
	[version] [numeric](19, 0) NOT NULL,
	[barthel_id] [numeric](19, 0) NULL,
	[barthel_not_known] [tinyint] NOT NULL,
	[modified_rankin_id] [numeric](19, 0) NULL,
	[modified_rankin_not_known] [tinyint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
