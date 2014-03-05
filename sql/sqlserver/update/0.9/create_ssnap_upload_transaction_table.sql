USE [STROKE]
GO

create table dbo.ssnap_upload_transaction (
	id numeric(19,0) identity not null, 
	report_id numeric(19,0)  not null, 	 
	tx_date DATETIME not null, 
	tx_type TINYINT not null,
	succeeded TINYINT not null, 
	diagnostic_mesg varchar(180)  null,
	primary key(id));

ALTER TABLE dbo.ssnap_upload_transaction 
	ADD CONSTRAINT FK_upload_tx__report FOREIGN KEY (report_id) 
	REFERENCES dbo.ssnap_rep
	ort(id) 	

