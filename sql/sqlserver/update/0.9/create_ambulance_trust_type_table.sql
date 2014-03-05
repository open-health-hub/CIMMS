USE [stroke]
GO

create table [dbo].[ambulance_trust_type] (
	[id] [numeric] (19,0) identity not null, 
	[version] [numeric](19,0) not null, 
	[description] [varchar](64) not null,
	[code] [varchar](16) not null, 
	primary key (id));