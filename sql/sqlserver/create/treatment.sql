CREATE TABLE [dbo].[treatment](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT NULL,
	[version] [numeric](19, 0) NOT NULL,
	[start_date] [datetime] NULL,
	[start_time] [int] NULL,
	[type_id] [numeric](19, 0) NOT NULL,
	[end_date] [datetime] NULL,
	[end_time] [int] NULL,
	[care_activity_id] [numeric](19, 0) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]