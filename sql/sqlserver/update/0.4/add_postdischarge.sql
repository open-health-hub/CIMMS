ALTER TABLE post_discharge_care ADD documented_evidence varchar(50) NULL
ALTER TABLE post_discharge_care ADD documentation_post_discharge tinyint NULL
ALTER TABLE post_discharge_care ADD help_adl tinyint NULL
ALTER TABLE post_discharge_care ADD visit_unknown tinyint NULL
ALTER TABLE post_discharge_care ADD visit tinyint NULL
ALTER TABLE post_discharge_care ADD discharge_location_different tinyint NULL
ALTER TABLE post_discharge_care ADD discharged_to varchar(50) NULL
ALTER TABLE post_discharge_care ADD discharged_home varchar(50) NULL
ALTER TABLE post_discharge_care ADD patient_previously_resident tinyint NULL
ALTER TABLE post_discharge_care ADD temporary_or_perm varchar(50) NULL
ALTER TABLE post_discharge_care ADD discharged_chome_with varchar(50) NULL
ALTER TABLE post_discharge_care ADD stroke_specialist tinyint NULL
ALTER TABLE post_discharge_care ADD esd_referral_discharge tinyint NULL
ALTER TABLE post_discharge_care ADD esd_referral_date_discharge datetime NULL
ALTER TABLE post_discharge_care ADD esd_referral_date_discharge_unknown tinyint NULL
go