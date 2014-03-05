ALTER TABLE [dbo].[care_activity]  WITH CHECK ADD  CONSTRAINT [FK787D8C7D29D6FE40] FOREIGN KEY([patient_id])
REFERENCES [dbo].[patient_proxy] ([id])
GO

ALTER TABLE [dbo].[care_activity] CHECK CONSTRAINT [FK787D8C7D29D6FE40]
GO

ALTER TABLE [dbo].[care_activity]  WITH CHECK ADD  CONSTRAINT [FK787D8C7D64158A38] FOREIGN KEY([imaging_id])
REFERENCES [dbo].[imaging] ([id])
GO

ALTER TABLE [dbo].[care_activity] CHECK CONSTRAINT [FK787D8C7D64158A38]
GO

ALTER TABLE [dbo].[care_activity]  WITH CHECK ADD  CONSTRAINT [FK787D8C7DA1C0C699] FOREIGN KEY([medical_history_id])
REFERENCES [dbo].[medical_history] ([id])
GO

ALTER TABLE [dbo].[care_activity] CHECK CONSTRAINT [FK787D8C7DA1C0C699]
GO

ALTER TABLE [dbo].[care_activity]  WITH CHECK ADD  CONSTRAINT [FK787D8C7DAA226EA9] FOREIGN KEY([nutrition_management_id])
REFERENCES [dbo].[nutrition_management] ([id])
GO

ALTER TABLE [dbo].[care_activity] CHECK CONSTRAINT [FK787D8C7DAA226EA9]
GO

ALTER TABLE [dbo].[care_activity]  WITH CHECK ADD  CONSTRAINT [FK787D8C7DB5532281] FOREIGN KEY([continence_management_id])
REFERENCES [dbo].[continence_management] ([id])
GO

ALTER TABLE [dbo].[care_activity] CHECK CONSTRAINT [FK787D8C7DB5532281]
GO

ALTER TABLE [dbo].[care_activity]  WITH CHECK ADD  CONSTRAINT [FK787D8C7DBD56D383] FOREIGN KEY([clinical_assessment_id])
REFERENCES [dbo].[clinical_assessment] ([id])
GO

ALTER TABLE [dbo].[care_activity] CHECK CONSTRAINT [FK787D8C7DBD56D383]
GO

ALTER TABLE [dbo].[care_activity]  WITH CHECK ADD  CONSTRAINT [FK787D8C7DBF7884CB] FOREIGN KEY([therapy_management_id])
REFERENCES [dbo].[therapy_management] ([id])
GO

ALTER TABLE [dbo].[care_activity] CHECK CONSTRAINT [FK787D8C7DBF7884CB]
GO

ALTER TABLE [dbo].[care_activity]  WITH CHECK ADD  CONSTRAINT [FK787D8C7DF4FE840D] FOREIGN KEY([fluid_management_id])
REFERENCES [dbo].[fluid_management] ([id])
GO

ALTER TABLE [dbo].[care_activity] CHECK CONSTRAINT [FK787D8C7DF4FE840D]
GO

ALTER TABLE [dbo].[care_activity]  WITH CHECK ADD  CONSTRAINT [FK787D8C7DFB21439C] FOREIGN KEY([thrombolysis_id])
REFERENCES [dbo].[thrombolysis] ([id])
GO

ALTER TABLE [dbo].[care_activity] CHECK CONSTRAINT [FK787D8C7DFB21439C]
GO
