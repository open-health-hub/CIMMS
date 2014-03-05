CREATE TABLE [dbo].[barthel](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT NULL,
	[version] [numeric](19, 0) NOT NULL,
	[bathing] [int] NULL,
	[bladder] [int] NULL,
	[bowels] [int] NULL,
	[dressing] [int] NULL,
	[feeding] [int] NULL,
	[grooming] [int] NULL,
	[manual_total] [int] NULL,
	[mobility] [int] NULL,
	[stairs] [int] NULL,
	[toilet] [int] NULL,
	[transfer] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
