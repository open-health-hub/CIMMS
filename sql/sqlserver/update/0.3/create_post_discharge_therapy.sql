/****** Object:  Table [dbo].[post_discharge_therapy]    Script Date: 04/18/2012 12:12:00 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[post_discharge_therapy](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT NULL,
	[version] [numeric](19, 0) NOT NULL,
	[post_discharge_care_id] [numeric](19, 0) NOT NULL,
	[type_id] [numeric](19, 0) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[post_discharge_therapy]  WITH CHECK ADD  CONSTRAINT [FK6170500DBD7519D3] FOREIGN KEY([type_id])
REFERENCES [dbo].[post_discharge_therapy_type] ([id])
GO

ALTER TABLE [dbo].[post_discharge_therapy] CHECK CONSTRAINT [FK6170500DBD7519D3]
GO

ALTER TABLE [dbo].[post_discharge_therapy]  WITH CHECK ADD  CONSTRAINT [FK6170500DD4B0C4DE] FOREIGN KEY([post_discharge_care_id])
REFERENCES [dbo].[post_discharge_care] ([id])
GO

ALTER TABLE [dbo].[post_discharge_therapy] CHECK CONSTRAINT [FK6170500DD4B0C4DE]
GO


