
ALTER TABLE [dbo].[fluid_management]  WITH CHECK ADD  CONSTRAINT [FKADC371D851F3421B] FOREIGN KEY([inadequate_at72fluid_reason_type_id])
REFERENCES [dbo].[inadequate_fluid_reason_type] ([id])
GO

ALTER TABLE [dbo].[fluid_management] CHECK CONSTRAINT [FKADC371D851F3421B]
GO

ALTER TABLE [dbo].[fluid_management]  WITH CHECK ADD  CONSTRAINT [FKADC371D89F7D344] FOREIGN KEY([inadequate_at48fluid_reason_type_id])
REFERENCES [dbo].[inadequate_fluid_reason_type] ([id])
GO

ALTER TABLE [dbo].[fluid_management] CHECK CONSTRAINT [FKADC371D89F7D344]
GO

ALTER TABLE [dbo].[fluid_management]  WITH CHECK ADD  CONSTRAINT [FKADC371D8CA888802] FOREIGN KEY([inadequate_at24fluid_reason_type_id])
REFERENCES [dbo].[inadequate_fluid_reason_type] ([id])
GO

ALTER TABLE [dbo].[fluid_management] CHECK CONSTRAINT [FKADC371D8CA888802]
GO
