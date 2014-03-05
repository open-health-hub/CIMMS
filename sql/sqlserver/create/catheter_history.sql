CREATE TABLE [dbo].[catheter_history](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT NULL,
	[version] [numeric](19, 0) NOT NULL,
	[insert_date] [datetime] NULL,
	[insert_time] [int] NULL,
	[reason_id] [numeric](19, 0) NOT NULL,
	[removal_date] [datetime] NULL,
	[removal_time] [int] NULL,
	[continence_management_id] [numeric](19, 0) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]