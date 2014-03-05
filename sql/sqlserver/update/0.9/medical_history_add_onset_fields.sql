USE stroke;
go

alter table medical_history 
add 
	hospital_admission_date DATETIME NULL,
	hospital_admission_time numeric(19,0) NULL,
	hospital_discharge_date DATETIME NULL,
	hospital_discharge_time numeric(19,0) NULL,
	admission_ward VARCHAR(255) NULL,
	stroke_ward_admission_date DATETIME NULL,
	stroke_ward_admission_time numeric(19,0) NULL,
	stroke_ward_discharge_date DATETIME NULL,
	stroke_ward_discharge_time numeric(19,0) NULL,
	stroke_unit_death TINYINT NULL,
        did_not_stay_in_stroke_ward TINYINT NULL;