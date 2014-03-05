USE [users]
GO
/****** Object:  Table [dbo].[user_role]    Script Date: 07/22/2013 21:55:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[user_role](
    [role_id] [numeric](19, 0) NOT NULL,
    [user_id] [numeric](19, 0) NOT NULL,
 CONSTRAINT [PK_URL_10] PRIMARY KEY CLUSTERED
(
    [role_id] ASC,
    [user_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[role]    Script Date: 07/22/2013 21:55:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[role](
    [id] [numeric](19, 0) NOT NULL,
    [version] [numeric](19, 0) NOT NULL,
    [authority] [varchar](255) NOT NULL,
 CONSTRAINT [PK__RLE_11] PRIMARY KEY CLUSTERED
(
    [id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[request_map]    Script Date: 07/22/2013 21:55:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[request_map](
    [id] [numeric](19, 0) NOT NULL,
    [version] [numeric](19, 0) NOT NULL,
    [config_attribute] [varchar](255) NOT NULL,
    [url] [varchar](255) NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[cimss_user_role]    Script Date: 07/22/2013 21:55:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[cimss_user_role](
    [role_id] [numeric](19, 0) NOT NULL,
    [user_id] [numeric](19, 0) NOT NULL,
 CONSTRAINT [PK_CMURL_160613] PRIMARY KEY CLUSTERED
(
    [role_id] ASC,
    [user_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[app_user]    Script Date: 07/22/2013 21:55:25 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[app_user](
    [id] [numeric](19, 0) NOT NULL,
    [version] [numeric](19, 0) NOT NULL,
    [account_expired] [tinyint] NOT NULL,
    [account_locked] [tinyint] NOT NULL,
    [enabled] [tinyint] NOT NULL,
    [password] [varchar](255) NOT NULL,
    [password_expired] [tinyint] NOT NULL,
    [username] [varchar](255) NOT NULL,
 CONSTRAINT [PK_APU_10] PRIMARY KEY CLUSTERED
(
    [id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
