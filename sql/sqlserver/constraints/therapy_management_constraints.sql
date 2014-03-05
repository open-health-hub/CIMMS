
ALTER TABLE [dbo].[therapy_management]  WITH CHECK ADD  CONSTRAINT [FK4046B5D916479FC5] FOREIGN KEY([baseline_assessment_management_id])
REFERENCES [dbo].[baseline_assessment_management] ([id])
GO

ALTER TABLE [dbo].[therapy_management] CHECK CONSTRAINT [FK4046B5D916479FC5]
GO

ALTER TABLE [dbo].[therapy_management]  WITH CHECK ADD  CONSTRAINT [FK4046B5D931DB5E90] FOREIGN KEY([cognitive_status_no_assessment_type_id])
REFERENCES [dbo].[cognitive_status_no_assessment_type] ([id])
GO

ALTER TABLE [dbo].[therapy_management] CHECK CONSTRAINT [FK4046B5D931DB5E90]
GO

ALTER TABLE [dbo].[therapy_management]  WITH CHECK ADD  CONSTRAINT [FK4046B5D93611E1FF] FOREIGN KEY([rehab_goals_not_set_reason_type_id])
REFERENCES [dbo].[rehab_goals_not_set_reason_type] ([id])
GO

ALTER TABLE [dbo].[therapy_management] CHECK CONSTRAINT [FK4046B5D93611E1FF]
GO

ALTER TABLE [dbo].[therapy_management]  WITH CHECK ADD  CONSTRAINT [FK4046B5D9AD908856] FOREIGN KEY([physiotherapy_management_id])
REFERENCES [dbo].[physiotherapy_management] ([id])
GO

ALTER TABLE [dbo].[therapy_management] CHECK CONSTRAINT [FK4046B5D9AD908856]
GO

ALTER TABLE [dbo].[therapy_management]  WITH CHECK ADD  CONSTRAINT [FK4046B5D9E4185627] FOREIGN KEY([occupational_therapy_management_id])
REFERENCES [dbo].[occupational_therapy_management] ([id])
GO

ALTER TABLE [dbo].[therapy_management] CHECK CONSTRAINT [FK4046B5D9E4185627]
GO

ALTER TABLE [dbo].[therapy_management]  WITH CHECK ADD  CONSTRAINT [FK4046B5D9FA3677E5] FOREIGN KEY([speech_and_language_therapy_management_id])
REFERENCES [dbo].[speech_and_language_therapy_management] ([id])
GO

ALTER TABLE [dbo].[therapy_management] CHECK CONSTRAINT [FK4046B5D9FA3677E5]
GO

