USE [stroke]
GO

/****** Object:  Table [dbo].[identifier]    Script Date: 08/30/2012 19:01:24 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[identifier](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT NULL,
	[version] [numeric](19, 0) NOT NULL,
	[reference] [varchar](255) NOT NULL,
	[type_id] [numeric](19, 0) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[identifier]  WITH CHECK ADD  CONSTRAINT [FK9F88ACA919029DF5] FOREIGN KEY([type_id])
REFERENCES [dbo].[identifier_type] ([id])
GO

ALTER TABLE [dbo].[identifier] CHECK CONSTRAINT [FK9F88ACA919029DF5]
GO


