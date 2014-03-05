
CREATE TABLE [dbo].[audit_record_log](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT NULL,
	[actor] [varchar](255) NULL,
	[date_created] [datetime] NOT NULL,
	[entity_name] [varchar](255) NULL,
	[event_name] [varchar](255) NULL,
	[field_values] [text] NULL,
	[last_updated] [datetime] NOT NULL,
	[persisted_object_id] [varchar](255) NULL,
	[persisted_object_version] [numeric](19, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
