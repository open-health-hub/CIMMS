
ALTER TABLE [dbo].[therapy_management]  WITH CHECK ADD  CONSTRAINT [FK4046B5D99FB31C26] FOREIGN KEY([assessment_management_id])
REFERENCES [dbo].[assessment_management] ([id])
GO

ALTER TABLE [dbo].[therapy_management] CHECK CONSTRAINT [FK4046B5D99FB31C26]
GO
