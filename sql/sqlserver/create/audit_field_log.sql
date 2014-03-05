CREATE TABLE [dbo].[audit_field_log](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT NULL,
	[actor] [varchar](255) NULL,
	[date_created] [datetime] NOT NULL,
	[entity_name] [varchar](255) NULL,
	[event_name] [varchar](255) NULL,
	[last_updated] [datetime] NOT NULL,
	[new_value] [text] NULL,
	[old_value] [text] NULL,
	[persisted_object_id] [varchar](255) NULL,
	[persisted_object_version] [numeric](19, 0) NULL,
	[property_name] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO