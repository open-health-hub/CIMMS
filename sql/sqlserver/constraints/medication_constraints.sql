ALTER TABLE [dbo].[medication]  WITH CHECK ADD  CONSTRAINT [FK7725CACF82F8391B] FOREIGN KEY([type_id])
REFERENCES [dbo].[medication_type] ([id])
GO

ALTER TABLE [dbo].[medication] CHECK CONSTRAINT [FK7725CACF82F8391B]
GO

ALTER TABLE [dbo].[medication]  WITH CHECK ADD  CONSTRAINT [FK7725CACFA1C0C699] FOREIGN KEY([medical_history_id])
REFERENCES [dbo].[medical_history] ([id])
GO

ALTER TABLE [dbo].[medication] CHECK CONSTRAINT [FK7725CACFA1C0C699]
GO

