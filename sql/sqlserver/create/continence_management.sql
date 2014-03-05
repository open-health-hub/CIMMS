CREATE TABLE [dbo].[continence_management](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT NULL,
	[version] [numeric](19, 0) NOT NULL,
	[prior_catheter] [tinyint] NOT NULL,
	[newly_incontinent] [tinyint] NOT NULL,
	[catheterised_since_admission] [tinyint] NOT NULL,
	[has_continence_plan] [tinyint] NOT NULL,
	[continence_plan_date] [datetime] NULL,
	[continence_plan_time] [int] NULL,
	[no_continence_plan_reason_id] [numeric](19, 0) NULL,
	[no_continence_plan_reason_other] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
