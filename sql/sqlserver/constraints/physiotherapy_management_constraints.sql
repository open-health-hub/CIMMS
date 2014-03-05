ALTER TABLE [dbo].[physiotherapy_management]  WITH CHECK ADD  CONSTRAINT [FK114847918703F2E4] FOREIGN KEY([no_assessment_reason_type_id])
REFERENCES [dbo].[physiotherapy_no_assessment_reason_type] ([id])
GO

ALTER TABLE [dbo].[physiotherapy_management] CHECK CONSTRAINT [FK114847918703F2E4]
GO