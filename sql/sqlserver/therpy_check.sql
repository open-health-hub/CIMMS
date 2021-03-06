/****** Script for SelectTopNRows command from SSMS  ******/
SELECT TOP 1000 tm.[id]

	  , c.hospital_stay_id
	  , tm.nurse_led_therapy_required as nl_required
	  , tm.nurse_led_therapy_days_of_therapy as nl_days
	  , tm.nurse_led_therapy_minutes_of_therapy as nl_mins	  	  	  	  
	  , tm.pyschology_therapy_required as ps_required
	  , tm.pyschology_days_of_therapy as ps_days
	  , tm.pyschology_minutes_of_therapy as ps_mins	  	  	  
	  , salt.therapy_required as sl_required
	  , salt.days_of_therapy as sl_days
	  , salt.minutes_of_therapy as sl_mins	  	  
	  , otm.therapy_required as ot_required
	  , otm.days_of_therapy as ot_days
	  , otm.minutes_of_therapy as ot_mins	  
	  , pm.therapy_required as physio_required
	  , pm.days_of_therapy as physio_days
	  , pm.minutes_of_therapy as physio_mins
      ,[nurse_led_therapy_days_of_therapy]
      ,[nurse_led_therapy_minutes_of_therapy]
      ,[nurse_led_therapy_required]
      ,[pyschology_days_of_therapy]
      ,[pyschology_minutes_of_therapy]
      ,[pyschology_therapy_required]
  FROM [stroke].[dbo].[therapy_management] as tm
  join stroke.dbo.care_activity as c on c.therapy_management_id = tm.id
  join stroke.dbo.physiotherapy_management as pm on tm.physiotherapy_management_id = pm.id 
  join stroke.dbo.occupational_therapy_management as otm on tm.occupational_therapy_management_id = otm.id 
  join stroke.dbo.speech_and_language_therapy_management as salt on tm.speech_and_language_therapy_management_id = salt.id 
  
  where c.hospital_stay_id = 1713