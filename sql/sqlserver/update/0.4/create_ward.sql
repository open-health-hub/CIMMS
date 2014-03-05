USE [stroke]
GO

/****** Object:  Table [dbo].[ward]    Script Date: 09/03/2012 05:47:02 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[ward](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT NULL,
	[version] [numeric](19, 0) NOT NULL,
	[site_id] [numeric](19, 0) NOT NULL,
	[ward_name] [varchar](255) NOT NULL,
	[ward_number] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[ward]  WITH CHECK ADD  CONSTRAINT [FK37927CC3E96F2C] FOREIGN KEY([site_id])
REFERENCES [dbo].[site] ([id])
GO

ALTER TABLE [dbo].[ward] CHECK CONSTRAINT [FK37927CC3E96F2C]
GO


