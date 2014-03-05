USE users;
GO

SET IDENTITY_INSERT [role] ON
INSERT [role] ([id], [version], [authority]) VALUES (CAST(1 AS Numeric(19, 0)), CAST(0 AS Numeric(19, 0)), N'administrator')
INSERT [role] ([id], [version], [authority]) VALUES (CAST(2 AS Numeric(19, 0)), CAST(0 AS Numeric(19, 0)), N'therapist')

SET IDENTITY_INSERT [role] OFF
