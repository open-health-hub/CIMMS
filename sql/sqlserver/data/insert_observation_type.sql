/****** Object:  Table [dbo].[observation_type]    Script Date: 11/10/2011 12:03:55 ******/
SET IDENTITY_INSERT [observation_type] ON
INSERT [observation_type] ([id], [version], [description]) VALUES (CAST(1 AS Numeric(19, 0)), CAST(0 AS Numeric(19, 0)), N'Conscious Level')
INSERT [observation_type] ([id], [version], [description]) VALUES (CAST(2 AS Numeric(19, 0)), CAST(0 AS Numeric(19, 0)), N'Neurological State')
INSERT [observation_type] ([id], [version], [description]) VALUES (CAST(3 AS Numeric(19, 0)), CAST(0 AS Numeric(19, 0)), N'Condition compared to yesterday')
INSERT [observation_type] ([id], [version], [description]) VALUES (CAST(4 AS Numeric(19, 0)), CAST(0 AS Numeric(19, 0)), N'Oxygen saturation')
SET IDENTITY_INSERT [observation_type] OFF