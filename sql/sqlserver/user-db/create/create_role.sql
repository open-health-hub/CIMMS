USE [users]
GO

/****** Object:  Table [dbo].[role]    Script Date: 05/21/2012 11:30:06 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[role](
	[id] [numeric](19, 0) NOT NULL,
	[version] [numeric](19, 0) NOT NULL,
	[authority] [varchar](255) NOT NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO


