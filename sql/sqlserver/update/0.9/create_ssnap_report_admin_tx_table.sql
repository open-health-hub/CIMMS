USE [STROKE]
GO

create table dbo.ssnap_report_admin_tx (
	id numeric(19,0) identity not null, 
	report_id numeric(19,0) not null, 
	report_version numeric(19,0) not null, 
	action varchar(32) not null, 
	administrator numeric(19,0) not null, 
	primary key(id));

ALTER TABLE dbo.ssnap_report_admin_tx 
	ADD CONSTRAINT FK_report_admin_tx__report_id FOREIGN KEY (report_id) 
	REFERENCES dbo.ssnap_report(id);	

--ALTER TABLE dbo.ssnap_report_admin_tx 
--	ADD CONSTRAINT FK_report_admin_tx__report_version FOREIGN KEY (report_version) 
--	REFERENCES dbo.ssnap_report(hsi_version);	
