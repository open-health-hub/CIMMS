ALTER TABLE [dbo].[baseline_assessment_management]  WITH CHECK ADD  CONSTRAINT [FKB0801F666B9E8FF2] FOREIGN KEY([modified_rankin_id])
REFERENCES [dbo].[modified_rankin] ([id])
GO

ALTER TABLE [dbo].[baseline_assessment_management] CHECK CONSTRAINT [FKB0801F666B9E8FF2]
GO

ALTER TABLE [dbo].[baseline_assessment_management]  WITH CHECK ADD  CONSTRAINT [FKB0801F669FD5A8F5] FOREIGN KEY([barthel_id])
REFERENCES [dbo].[barthel] ([id])
GO

ALTER TABLE [dbo].[baseline_assessment_management] CHECK CONSTRAINT [FKB0801F669FD5A8F5]
GO