USE [stroke]
GO

/****** Object:  Table [dbo].[general_medical_practitioner]    Script Date: 08/30/2012 18:26:08 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[general_medical_practitioner](
	[code] [varchar](255) NOT NULL,
	[name] [varchar](255) NOT NULL,
	[practiceCode] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[code] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO


