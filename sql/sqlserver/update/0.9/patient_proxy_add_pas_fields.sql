USE stroke;
go




ALTER TABLE dbo.patient_proxy
	ADD surname VARCHAR(255) NULL,
	forename VARCHAR(255) NULL,
	gender VARCHAR(255) NULL,
	ethnicity VARCHAR(255) NULL,
	postcode VARCHAR(10) NULL,
	date_of_birth DATETIME NULL,
	date_of_death DATETIME NULL;