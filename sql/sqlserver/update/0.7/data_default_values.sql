Update medication set value='true' where value is null;
Update comorbidity set value='true' where value is null;

update occupational_therapy_management
set mood_assessment_performed = null
where mood_assessment_performed='null';
