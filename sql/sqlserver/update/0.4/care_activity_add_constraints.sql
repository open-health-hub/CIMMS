
ALTER TABLE [dbo].[care_activity]  WITH CHECK ADD  CONSTRAINT [FK787D8C7D6E35B564] FOREIGN KEY([patient_life_style_id])
REFERENCES [dbo].[patient_life_style] ([id])
GO

ALTER TABLE [dbo].[care_activity] CHECK CONSTRAINT [FK787D8C7D6E35B564]
GO

ALTER TABLE [dbo].[care_activity]  WITH CHECK ADD  CONSTRAINT [FK787D8C7DD4B0C4DE] FOREIGN KEY([post_discharge_care_id])
REFERENCES [dbo].[post_discharge_care] ([id])
GO

ALTER TABLE [dbo].[care_activity] CHECK CONSTRAINT [FK787D8C7DD4B0C4DE]
GO