/*
 * Update the SchemaVersion table
 */

USE stroke;
GO

UPDATE dbo.SchemaVersion 
SET isCurrent = 0 
WHERE isCurrent = 1
GO

INSERT INTO  dbo.SchemaVersion ( versionNumber, comment, dateCreated, isCurrent)
VALUES ( '0.8.3', 'Version 0.8.3', GETDATE(), 1)
GO
