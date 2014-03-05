/*
 * SSNAP PATIENT VIEW
 * VERSION 0.9
 */
 
USE stroke;
go

IF EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[patient_data]'))
    drop view [dbo].[patient_data]
GO

CREATE VIEW [dbo].[patient_data]
AS
	SELECT DISTINCT
    p.[id]
      ,[nhs_number] AS [S1NHSNumber]
      , CASE 
			WHEN [nhs_number] IS NULL THEN '1' 
			ELSE '0'
		END AS [S1NoNHSNumber]
      ,[hospital_number] 
      ,[district_number]
      ,[surname] AS [S1Surname]
      ,[forename] AS [S1Forename]
      ,[gender] AS [S1Gender]
      ,[ethnicity] AS [S1Ethnicity]
      ,RTRIM(LEFT([postcode], 4)) AS [S1PostcodeOut]
      ,RIGHT([postcode],3) AS [S1PostcodeIn]
      ,CASE 
			WHEN ISNULL(m.stroke_unit_death,0) = 1 THEN 'Y'
			ELSE 'N'
		END AS S7StrokeUnitDeath
      ,CONVERT(varchar(10), [date_of_birth], 103)  AS [S1BirthDate]
      ,CASE 
			WHEN pdc.discharged_to = 'died' THEN CONVERT(varchar(10), [date_of_death], 103)
			ELSE ''
		END AS [S7DeathDate]
      ,dbo.makeDateTime(m.[hospital_admission_date],ISNULL(m.[hospital_admission_time],0)) AS S1FirstArrivalDateTime
      ,CASE 
	      WHEN ISNULL(a.transferred_from_another_hospital,0) = 1 THEN dbo.makeDateTime(a.[this_hospital_arrival_date], a.[this_hospital_arrival_time])
	      ELSE dbo.makeDateTime(m.[hospital_admission_date],m.[hospital_admission_time])  -- Same as 1st arrival because no transfer indicated
	   END AS S4ArrivalDateTime 
	  ,m.hospital_admission_date
	  ,m.hospital_admission_time
      ,CASE 
			WHEN m.[hospital_admission_time] IS NULL THEN '1'
			ELSE '0'
		END AS [S1FirstArrivalTimeNotEntered]
	  ,CASE 
			WHEN ISNULL(a.transferred_from_another_hospital,0) = 1 AND a.[this_hospital_arrival_time] IS NULL THEN '1'
			WHEN ISNULL(a.transferred_from_another_hospital,0) = 0 AND m.[hospital_admission_time] IS NULL THEN '1'
			ELSE '0'
		END AS S4ArrivalTimeNotEntered
	   ,m.[did_not_stay_in_stroke_ward] AS S4StrokeUnitArrivalNA
	  ,CASE
	        WHEN m.[stroke_ward_admission_date] IS NULL THEN '1'
	        ELSE '0'
		END AS S1FirstStrokeUnitArrivalNA	
      ,dbo.makeDateTime(m.[hospital_discharge_date],m.[hospital_discharge_time]) AS [S7HospitalDischargeDateTime]
      ,CASE 
			WHEN m.[hospital_discharge_time] IS NULL THEN '1'
			ELSE '0'
		END AS [S7HospitalDischargeTimeNotEntered]      
      ,m.[admission_ward] AS S1FirstWard
      ,m.[admission_ward] AS S4FirstWard
      ,dbo.makeDateTime(m.[stroke_ward_admission_date],m.[stroke_ward_admission_time]) AS S1FirstStrokeUnitArrivalDateTime
      ,CASE 
			WHEN m.[stroke_ward_admission_time] IS NULL THEN '1'
			ELSE '0'
		END AS S1FirstStrokeUnitArrivalTimeNotEntered    
      ,CASE 
			WHEN m.stroke_ward_discharge_date IS NOT NULL AND m.stroke_ward_discharge_time IS NOT NULL THEN dbo.makeDateTime(m.[stroke_ward_discharge_date],m.[stroke_ward_discharge_time]) 
			ELSE ''
		END AS S7StrokeUnitDischargeDateTime
	  ,m.stroke_ward_discharge_date
	  ,m.stroke_ward_discharge_time
      ,CASE 
			WHEN m.stroke_ward_admission_date IS NULL THEN ''
			WHEN m.[stroke_ward_discharge_time] IS NULL THEN '1'
			ELSE '0'
		END AS S7StrokeUnitDischargeTimeNotEntered
	  ,dbo.makeDateTime(m.stroke_ward_admission_date, m.stroke_ward_admission_time) AS S4StrokeUnitArrivalDateTime
	  ,CASE 
			WHEN m.[stroke_ward_admission_time] IS NULL THEN '1'
			ELSE '0'
		END AS S4StrokeUnitArrivalTimeNotEntered	  
    FROM dbo.care_activity c 
    JOIN dbo.patient_proxy p ON c.patient_id = p.id 
    JOIN dbo.medical_history m ON m.id = c.medical_history_id
    JOIN dbo.arrival a ON a.id = m.arrival_id
	LEFT OUTER JOIN dbo.post_discharge_care pdc ON c.post_discharge_care_id = pdc.id
GO
