
CREATE TABLE [dbo].[scan](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT NULL,
	[version] [numeric](19, 0) NOT NULL,
	[request_date] [datetime] NULL,
	[request_time] [int] NULL,
	[taken_date] [datetime] NULL,
	[taken_time] [int] NULL,
	[diagnosis_type_id] [numeric](19, 0) NULL,
	[diagnosis_type_other] [varchar](255) NULL,
	[image_type_id] [numeric](19, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
