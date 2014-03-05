update modified_rankin
set assessment_management_id = (SELECT id FROM assessment_management
where modified_rankin_id is not null and modified_rankin_id = modified_rankin.id)
, modified_rankin_assessments_idx = 0
, pathway_stage_id = 2


