ALTER TABLE [dbo].[comorbidity]  WITH CHECK ADD  CONSTRAINT [FKE7C6E6B5A1C0C699] FOREIGN KEY([medical_history_id])
REFERENCES [dbo].[medical_history] ([id])
GO

ALTER TABLE [dbo].[comorbidity] CHECK CONSTRAINT [FKE7C6E6B5A1C0C699]
GO

ALTER TABLE [dbo].[comorbidity]  WITH CHECK ADD  CONSTRAINT [FKE7C6E6B5C88723DD] FOREIGN KEY([type_id])
REFERENCES [dbo].[comorbidity_type] ([id])
GO

ALTER TABLE [dbo].[comorbidity] CHECK CONSTRAINT [FKE7C6E6B5C88723DD]
GO

