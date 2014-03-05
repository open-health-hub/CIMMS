CREATE TABLE [dbo].[clinical_assessment](
	[id] [numeric](19, 0) IDENTITY(1,1) NOT NULL,
	[version] [numeric](19, 0) NOT NULL,
	[facial_weakness] [tinyint] NULL,
	[arm_mrc_scale] [int] NULL,
	[arm_sensory_loss] [varchar](255) NULL,
	[left_arm_affected] [tinyint] NULL,
	[right_arm_affected] [tinyint] NULL,
	[leg_mrc_scale] [int] NULL,
	[leg_sensory_loss] [varchar](255) NULL,
	[left_leg_affected] [tinyint] NULL,
	[right_leg_affected] [tinyint] NULL,
	[dysarthria] [varchar](255) NULL,
	[dysphasia] [varchar](255) NULL,
	[hemianopia] [varchar](255) NULL,
	[inattention] [varchar](255) NULL,
	[other] [varchar](255) NULL,
	[other_text] [varchar](255) NULL,
	[brainstem] [varchar](255) NULL,	
	[independent] [varchar](255) NULL,
	[walk_at_presentation] [tinyint] NULL,
	[mobile_pre_stroke] [tinyint] NULL,
	[swallow_screen_performed] [tinyint] NULL,
	[swallow_screen_date] [datetime] NULL,
	[swallow_screen_time] [int] NULL,
	[no_swallow_screen_performed_reason_id] [numeric](19, 0) NULL,
	[classification_id] [numeric](19, 0) NULL,
	[glasgow_coma_score_id] [numeric](19, 0) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

