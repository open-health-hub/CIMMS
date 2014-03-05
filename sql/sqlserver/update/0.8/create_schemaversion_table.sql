
/*
 * CREATE SCHEMA VERSION TABLE
 */
 
USE stroke;
GO

CREATE TABLE dbo.SchemaVersion (
	versionNumber varchar(10) primary key,
	comment varchar(255),
	dateCreated datetime,
	isCurrent char(1) default 0 
)

ALTER TABLE dbo.SchemaVersion 
    ADD CONSTRAINT CK_SchemaVersion_isCurrent
    CHECK (isCurrent = 1 OR isCurrent = 0)