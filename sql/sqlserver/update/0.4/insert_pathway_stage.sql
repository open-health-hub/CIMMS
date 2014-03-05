
SET IDENTITY_INSERT [pathway_stage] ON
INSERT [pathway_stage] ([id], [version], [description]) VALUES (CAST(1 AS Numeric(19, 0)), CAST(0 AS Numeric(19, 0)), N'Pre-admission')
INSERT [pathway_stage] ([id], [version], [description]) VALUES (CAST(2 AS Numeric(19, 0)), CAST(0 AS Numeric(19, 0)), N'Baseline')
INSERT [pathway_stage] ([id], [version], [description]) VALUES (CAST(3 AS Numeric(19, 0)), CAST(0 AS Numeric(19, 0)), N'Discharge')
SET IDENTITY_INSERT [pathway_stage] OFF
