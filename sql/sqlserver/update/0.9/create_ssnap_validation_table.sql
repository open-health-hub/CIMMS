USE [STROKE]
GO

create table dbo.ssnap_validation (
	id numeric(19,0) identity not null, 
	field_name varchar(24)  not null, 	 
	field_number varchar(10) not null, 
	validation_expr varchar(128)  null, 
	primary key(id));

