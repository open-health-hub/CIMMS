ALTER TABLE [dbo].[barthel]  WITH CHECK ADD  CONSTRAINT [FKEC2588AE99F3E117] FOREIGN KEY([pathway_stage_id])
REFERENCES [dbo].[pathway_stage] ([id])
GO

ALTER TABLE [dbo].[barthel] CHECK CONSTRAINT [FKEC2588AE99F3E117]
GO

ALTER TABLE [dbo].[barthel]  WITH CHECK ADD  CONSTRAINT [FKEC2588AE9FB31C26] FOREIGN KEY([assessment_management_id])
REFERENCES [dbo].[assessment_management] ([id])
GO

ALTER TABLE [dbo].[barthel] CHECK CONSTRAINT [FKEC2588AE9FB31C26]
GO
