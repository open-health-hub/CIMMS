ALTER TABLE [dbo].[speech_and_language_therapy_management]  WITH CHECK ADD  CONSTRAINT [FKC1288C5B2D5728A0] FOREIGN KEY([no_communication_assessment_reason_type_id])
REFERENCES [dbo].[communication_no_assessment_reason_type] ([id])
GO

ALTER TABLE [dbo].[speech_and_language_therapy_management] CHECK CONSTRAINT [FKC1288C5B2D5728A0]
GO

ALTER TABLE [dbo].[speech_and_language_therapy_management]  WITH CHECK ADD  CONSTRAINT [FKC1288C5B72134C8E] FOREIGN KEY([no_swallowing_assessment_reason_type_id])
REFERENCES [dbo].[swallowing_no_assessment_reason_type] ([id])
GO

ALTER TABLE [dbo].[speech_and_language_therapy_management] CHECK CONSTRAINT [FKC1288C5B72134C8E]
GO
