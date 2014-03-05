USE [users]
GO

/****** Object:  Table [dbo].[app_user]    Script Date: 05/21/2012 11:26:03 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[app_user](
	[id] [numeric](19, 0) NOT NULL,
	[version] [numeric](19, 0) NOT NULL,
	[account_expired] [tinyint] NOT NULL,
	[account_locked] [tinyint] NOT NULL,
	[enabled] [tinyint] NOT NULL,
	[password] [varchar](255) NOT NULL,
	[password_expired] [tinyint] NOT NULL,
	[username] [varchar](255) NOT NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO


