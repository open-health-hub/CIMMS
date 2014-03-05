/****** Script for SelectTopNRows command from SSMS  ******/
update medical_history set previous_stroke='yes' 
where id in (SELECT [medical_history_id]
  FROM [stroke].[dbo].[comorbidity]
  JOIN comorbidity_type on (comorbidity_type.id = type_id)
  where description = 'previous stroke')
  

update medical_history set previous_tia='yes' 
where id in (SELECT [medical_history_id]
  FROM [stroke].[dbo].[comorbidity]
  JOIN comorbidity_type on (comorbidity_type.id = type_id)
  where description = 'previous tia')
  
  
  

