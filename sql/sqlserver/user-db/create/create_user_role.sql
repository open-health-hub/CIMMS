USE [users]
GO

/****** Object:  Table [dbo].[user_role]    Script Date: 05/21/2012 11:30:39 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[user_role](
	[role_id] [numeric](19, 0) NOT NULL,
	[user_id] [numeric](19, 0) NOT NULL
) ON [PRIMARY]

GO


