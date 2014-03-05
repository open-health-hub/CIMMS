USE [STROKE]
GO

create table dbo.ssnap_report_field (
	id numeric(19,0) identity not null, 
	record_id numeric(19,0)  not null, 	 
	field_name varchar(64) not null, 
	field_number varchar(64) null, 
	field_value varchar(64)  null,
	primary key(id));

ALTER TABLE dbo.ssnap_report_field 
	ADD CONSTRAINT FK_report_field__report_record FOREIGN KEY (record_id) 
	REFERENCES dbo.ssnap_report_record(id) 	

