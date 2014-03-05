ALTER TABLE [dbo].[continence_management]  WITH CHECK ADD  CONSTRAINT [FK5E30DD808D00E9B] FOREIGN KEY([no_continence_plan_reason_id])
REFERENCES [dbo].[no_continence_plan_reason_type] ([id])
GO

ALTER TABLE [dbo].[continence_management] CHECK CONSTRAINT [FK5E30DD808D00E9B]
GO
