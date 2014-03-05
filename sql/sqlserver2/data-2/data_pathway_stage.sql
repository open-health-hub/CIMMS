use stroke;
go

SET IDENTITY_INSERT [dbo].[pathway_stage] ON
INSERT [dbo].[pathway_stage] ([id], [version], [description]) VALUES (CAST(1 AS Numeric(19, 0)), CAST(0 AS Numeric(19, 0)), N'Pre-admission')
INSERT [dbo].[pathway_stage] ([id], [version], [description]) VALUES (CAST(2 AS Numeric(19, 0)), CAST(0 AS Numeric(19, 0)), N'Baseline')
INSERT [dbo].[pathway_stage] ([id], [version], [description]) VALUES (CAST(3 AS Numeric(19, 0)), CAST(0 AS Numeric(19, 0)), N'Discharge')
SET IDENTITY_INSERT [dbo].[pathway_stage] OFF
