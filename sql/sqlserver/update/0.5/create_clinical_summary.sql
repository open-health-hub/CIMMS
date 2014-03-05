USE [stroke]
GO

/****** Object:  Table [dbo].[clinical_summary]    Script Date: 09/20/2012 07:44:57 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[clinical_summary](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT NULL,
	[version] [numeric](19, 0) NOT NULL,
	[new_pneumonia] [varchar](255) NULL,
	[urinary_tract_infection] [varchar](255) NULL,
	[worst_level_of_consciousness_id] [numeric](19, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[clinical_summary]  WITH CHECK ADD  CONSTRAINT [FK3B15127ABBFC43FA] FOREIGN KEY([worst_level_of_consciousness_id])
REFERENCES [dbo].[level_of_consciousness] ([id])
GO

ALTER TABLE [dbo].[clinical_summary] CHECK CONSTRAINT [FK3B15127ABBFC43FA]
GO


