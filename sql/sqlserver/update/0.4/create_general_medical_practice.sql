USE [stroke]
GO

/****** Object:  Table [dbo].[general_medical_practice]    Script Date: 08/30/2012 18:25:20 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[general_medical_practice](
	[code] [varchar](255) NOT NULL,
	[addressLine1] [varchar](255) NOT NULL,
	[addressLine2] [varchar](255) NOT NULL,
	[addressLine3] [varchar](255) NOT NULL,
	[addressLine4] [varchar](255) NOT NULL,
	[addressLine5] [varchar](255) NOT NULL,
	[postcode] [varchar](255) NOT NULL,
	[practiceName] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[code] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO


