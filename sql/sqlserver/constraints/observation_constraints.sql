
ALTER TABLE [dbo].[observation]  WITH CHECK ADD  CONSTRAINT [FK74AD82CB93781D4] FOREIGN KEY([type_id])
REFERENCES [dbo].[observation_type] ([id])
GO

ALTER TABLE [dbo].[observation] CHECK CONSTRAINT [FK74AD82CB93781D4]
GO

ALTER TABLE [dbo].[observation]  WITH CHECK ADD  CONSTRAINT [FK74AD82CC11D709F] FOREIGN KEY([care_activity_id])
REFERENCES [dbo].[care_activity] ([id])
GO

ALTER TABLE [dbo].[observation] CHECK CONSTRAINT [FK74AD82CC11D709F]
GO

