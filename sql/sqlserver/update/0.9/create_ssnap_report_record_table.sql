USE [STROKE]
GO

create table dbo.ssnap_report_record (
	id numeric(19,0) identity not null, 
	report_id numeric(19,0)  not null, 	 
	hospital_stay_id VARCHAR(255) not null, 
	hsi_version numeric(19,0) not null,
	primary key(id));

ALTER TABLE dbo.ssnap_report_record 
	ADD CONSTRAINT FK_report_record__report FOREIGN KEY (report_id) 
	REFERENCES dbo.ssnap_report(id) 	

