ALTER TABLE [dbo].[clinical_assessment]  WITH CHECK ADD  CONSTRAINT [FKA54EB6EBA04E74B] FOREIGN KEY([no_swallow_screen_performed_reason_at4hours_id])
REFERENCES [dbo].[no_swallow_screen_performed_reason_type] ([id])
GO


ALTER TABLE [dbo].[occupational_therapy_management]  WITH CHECK ADD  CONSTRAINT [FK702D56025A3BE9FA] FOREIGN KEY([no_mood_assessment_reason_type_id])
REFERENCES [dbo].[occupational_therapy_no_assessment_reason_type] ([id])
GO
