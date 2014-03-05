CREATE TABLE [dbo].[nutrition_management](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT NULL,
	[version] [numeric](19, 0) NOT NULL,
	[date_screened] [datetime] NULL,
	[time_screened] [int] NULL,
	[unable_to_screen] [tinyint] NULL,
	[adequate_at24] [varchar](255) NULL,
	[adequate_at48] [varchar](255) NULL,
	[adequate_at72] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
