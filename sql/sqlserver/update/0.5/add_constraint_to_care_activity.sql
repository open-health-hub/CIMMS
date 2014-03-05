ALTER TABLE [dbo].[care_activity]  WITH CHECK ADD  CONSTRAINT [FK787D8C7D98965B91] FOREIGN KEY([clinical_summary_id])
REFERENCES [dbo].[clinical_summary] ([id])
GO