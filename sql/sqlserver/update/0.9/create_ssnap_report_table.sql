USE [STROKE]
GO

create table dbo.ssnap_report (
	id numeric(19,0) identity not null,
    version numeric(19,0) not null,	
	creation_date DATETIME  not null, 
	sent_to_ssnap TINYINT not null, 
	ssnap_upload_status TINYINT not null,
	ssnap_upload_time DATETIME  null, 
	report_type varchar(10)  not null,
	allow_overwrite TINYINT not null,
	report_name varchar(64) NULL,
	start_date DATETIME  not null,
	end_date DATETIME  not null,
	primary key(id)
);

