update barthel
set assessment_management_id = (SELECT id FROM assessment_management
where barthel_id is not null and barthel_id = barthel.id)
, barthel_assessments_idx = 0
, pathway_stage_id = 2


