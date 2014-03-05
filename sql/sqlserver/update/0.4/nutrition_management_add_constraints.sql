
ALTER TABLE [dbo].[nutrition_management]  WITH CHECK ADD  CONSTRAINT [FKF319A34A60BD38C2] FOREIGN KEY([inadequate_at24nutrition_reason_type_id])
REFERENCES [dbo].[inadequate_nutrition_reason_type] ([id])
GO

ALTER TABLE [dbo].[nutrition_management] CHECK CONSTRAINT [FKF319A34A60BD38C2]
GO

ALTER TABLE [dbo].[nutrition_management]  WITH CHECK ADD  CONSTRAINT [FKF319A34A619B3E5B] FOREIGN KEY([inadequate_at72nutrition_reason_type_id])
REFERENCES [dbo].[inadequate_nutrition_reason_type] ([id])
GO

ALTER TABLE [dbo].[nutrition_management] CHECK CONSTRAINT [FKF319A34A619B3E5B]
GO

ALTER TABLE [dbo].[nutrition_management]  WITH CHECK ADD  CONSTRAINT [FKF319A34A75311304] FOREIGN KEY([inadequate_at48nutrition_reason_type_id])
REFERENCES [dbo].[inadequate_nutrition_reason_type] ([id])
GO

ALTER TABLE [dbo].[nutrition_management] CHECK CONSTRAINT [FKF319A34A75311304]
GO