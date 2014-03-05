ALTER TABLE [dbo].[occupational_therapy_management]  WITH CHECK ADD  CONSTRAINT [FK702D5602CABCAA06] FOREIGN KEY([no_assessment_reason_type_id])
REFERENCES [dbo].[occupational_therapy_no_assessment_reason_type] ([id])
GO

ALTER TABLE [dbo].[occupational_therapy_management] CHECK CONSTRAINT [FK702D5602CABCAA06]
GO
