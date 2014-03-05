ALTER TABLE [dbo].[risk_factor]  WITH CHECK ADD  CONSTRAINT [FKDAD694BFA1C0C699] FOREIGN KEY([medical_history_id])
REFERENCES [dbo].[medical_history] ([id])
GO

ALTER TABLE [dbo].[risk_factor] CHECK CONSTRAINT [FKDAD694BFA1C0C699]
GO

ALTER TABLE [dbo].[risk_factor]  WITH CHECK ADD  CONSTRAINT [FKDAD694BFF76277CA] FOREIGN KEY([type_id])
REFERENCES [dbo].[risk_factor_type] ([id])
GO

ALTER TABLE [dbo].[risk_factor] CHECK CONSTRAINT [FKDAD694BFF76277CA]
GO