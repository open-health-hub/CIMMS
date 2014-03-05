ALTER TABLE [dbo].[catheter_history]  WITH CHECK ADD  CONSTRAINT [FK313FE94355CDF454] FOREIGN KEY([reason_id])
REFERENCES [dbo].[catheter_reason_type] ([id])
GO

ALTER TABLE [dbo].[catheter_history] CHECK CONSTRAINT [FK313FE94355CDF454]
GO

ALTER TABLE [dbo].[catheter_history]  WITH CHECK ADD  CONSTRAINT [FK313FE943B5532281] FOREIGN KEY([continence_management_id])
REFERENCES [dbo].[continence_management] ([id])
GO

ALTER TABLE [dbo].[catheter_history] CHECK CONSTRAINT [FK313FE943B5532281]
GO
