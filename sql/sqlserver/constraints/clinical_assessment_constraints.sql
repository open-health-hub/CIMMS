ALTER TABLE [dbo].[clinical_assessment]  WITH CHECK ADD  CONSTRAINT [FKA54EB6EAEDB53F9] FOREIGN KEY([classification_id])
REFERENCES [dbo].[clinical_classification_type] ([id])
GO

ALTER TABLE [dbo].[clinical_assessment] CHECK CONSTRAINT [FKA54EB6EAEDB53F9]
GO

ALTER TABLE [dbo].[clinical_assessment]  WITH CHECK ADD  CONSTRAINT [FKA54EB6ECB0D15DC] FOREIGN KEY([no_swallow_screen_performed_reason_id])
REFERENCES [dbo].[no_swallow_screen_performed_reason_type] ([id])
GO

ALTER TABLE [dbo].[clinical_assessment] CHECK CONSTRAINT [FKA54EB6ECB0D15DC]
GO




ALTER TABLE [dbo].[clinical_assessment]  WITH CHECK ADD  CONSTRAINT [FKA54EB6EF1C0392D] FOREIGN KEY([glasgow_coma_score_id])
REFERENCES [dbo].[glasgow_coma_score] ([id])
GO

ALTER TABLE [dbo].[clinical_assessment] CHECK CONSTRAINT [FKA54EB6EF1C0392D]
GO
