
IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[discharge_view]'))
DROP VIEW [dbo].[discharge_view]

GO

create view discharge_view as 
SELECT   
		   start_date,
		   fit_for_discharge_date, 
			social_worker_referral,
			social_worker_referral_date, 
			social_worker_referral_unknown
			social_worker_assessment,
			social_worker_assessment_date,
			social_worker_assessment_unknown,
			esd_referral,
			esd_referral_date,
			esd_referral_date_unknown
			
				
			
			
			
FROM care_activity ca
left JOIN post_discharge_care pdc  on pdc.id = ca.post_discharge_care_id

