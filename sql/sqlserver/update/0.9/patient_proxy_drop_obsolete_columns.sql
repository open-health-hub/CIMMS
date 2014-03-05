USE stroke;
go

ALTER TABLE dbo.patient_proxy drop column hospital_admission_date;
ALTER TABLE dbo.patient_proxy drop column hospital_admission_time;
ALTER TABLE dbo.patient_proxy drop column hospital_discharge_date;
ALTER TABLE dbo.patient_proxy drop column hospital_discharge_time;
ALTER TABLE dbo.patient_proxy drop column admission_ward;
ALTER TABLE dbo.patient_proxy drop column stroke_ward_admission_date;
ALTER TABLE dbo.patient_proxy drop column stroke_ward_admission_time;
ALTER TABLE dbo.patient_proxy drop column stroke_ward_discharge_date;
ALTER TABLE dbo.patient_proxy drop column stroke_ward_discharge_time;
ALTER TABLE dbo.patient_proxy drop column stroke_unit_death;