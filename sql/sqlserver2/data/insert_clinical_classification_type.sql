

/****** Object:  Table [dbo].[clinical_classification_type]    Script Date: 11/10/2011 12:36:19 ******/
SET IDENTITY_INSERT [clinical_classification_type] ON
INSERT [clinical_classification_type] ([id], [version], [description]) VALUES (CAST(1 AS Numeric(19, 0)), CAST(0 AS Numeric(19, 0)), N'PACS')
INSERT [clinical_classification_type] ([id], [version], [description]) VALUES (CAST(2 AS Numeric(19, 0)), CAST(0 AS Numeric(19, 0)), N'LACS')
INSERT [clinical_classification_type] ([id], [version], [description]) VALUES (CAST(3 AS Numeric(19, 0)), CAST(0 AS Numeric(19, 0)), N'POCS')
INSERT [clinical_classification_type] ([id], [version], [description]) VALUES (CAST(4 AS Numeric(19, 0)), CAST(0 AS Numeric(19, 0)), N'TACS')
SET IDENTITY_INSERT [clinical_classification_type] OFF