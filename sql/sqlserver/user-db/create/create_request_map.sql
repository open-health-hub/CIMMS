USE [users]
GO

/****** Object:  Table [dbo].[request_map]    Script Date: 05/21/2012 11:29:45 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[request_map](
	[id] [numeric](19, 0) NOT NULL,
	[version] [numeric](19, 0) NOT NULL,
	[config_attribute] [varchar](255) NOT NULL,
	[url] [varchar](255) NOT NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO


