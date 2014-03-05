/****** Object:  Table [dbo].[treatment_type]    Script Date: 11/10/2011 12:36:19 ******/
SET IDENTITY_INSERT [treatment_type] ON
INSERT [treatment_type] ([id], [version], [description], [compulsory]) VALUES (CAST(1 AS Numeric(19, 0)), CAST(0 AS Numeric(19, 0)), N'Antiplatelet', 1)
INSERT [treatment_type] ([id], [version], [description], [compulsory]) VALUES (CAST(2 AS Numeric(19, 0)), CAST(0 AS Numeric(19, 0)), N'Anticoagulation', 1)
INSERT [treatment_type] ([id], [version], [description], [compulsory]) VALUES (CAST(3 AS Numeric(19, 0)), CAST(0 AS Numeric(19, 0)), N'Lipid lowering', 1)
INSERT [treatment_type] ([id], [version], [description], [compulsory]) VALUES (CAST(4 AS Numeric(19, 0)), CAST(0 AS Numeric(19, 0)), N'Antihypertensive', 1)
INSERT [treatment_type] ([id], [version], [description], [compulsory]) VALUES (CAST(5 AS Numeric(19, 0)), CAST(0 AS Numeric(19, 0)), N'Laxative', 0)
INSERT [treatment_type] ([id], [version], [description], [compulsory]) VALUES (CAST(6 AS Numeric(19, 0)), CAST(0 AS Numeric(19, 0)), N'Oxygen', 1)
SET IDENTITY_INSERT [treatment_type] OFF
