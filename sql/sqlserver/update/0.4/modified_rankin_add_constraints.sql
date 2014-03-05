ALTER TABLE [dbo].[modified_rankin]  WITH CHECK ADD  CONSTRAINT [FK8658F48799F3E117] FOREIGN KEY([pathway_stage_id])
REFERENCES [dbo].[pathway_stage] ([id])
GO

ALTER TABLE [dbo].[modified_rankin] CHECK CONSTRAINT [FK8658F48799F3E117]
GO

ALTER TABLE [dbo].[modified_rankin]  WITH CHECK ADD  CONSTRAINT [FK8658F4879FB31C26] FOREIGN KEY([assessment_management_id])
REFERENCES [dbo].[assessment_management] ([id])
GO

ALTER TABLE [dbo].[modified_rankin] CHECK CONSTRAINT [FK8658F4879FB31C26]
GO