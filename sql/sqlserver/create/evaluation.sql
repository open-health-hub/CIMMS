CREATE TABLE [dbo].[evaluation](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT NULL,
	[version] [numeric](19, 0) NOT NULL,
	[date_evaluated] [datetime] NOT NULL,
	[time_evaluated] [int] NOT NULL,
	[evaluator_id] [numeric](19, 0) NOT NULL,
	[care_activity_id] [numeric](19, 0) NOT NULL,

PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
