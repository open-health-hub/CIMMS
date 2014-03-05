<div id="preMorbidAssessmentForm">
	<g:render template="/templates/sectionHead"></g:render>
	<g:hiddenField name="careActivityId" value="${careActivityInstance.id}"></g:hiddenField>
	<div class="containPanel" data-bind="css : {changed: tracker().somethingHasChanged }">
		<div data-bind="html: errorsAsList"></div>
		<div id="preMorbidAssessment">					
	       <div class="page-header"><h1>Pre-morbid Assessments</h1></div>	
	       
            <div id="pre-morbid-assessments">

                <g:render template="/admission/assessment/assessments" 
                                model="['careActivityInstance':careActivityInstance,'errorList':errorList]"/>
        
            </div>
		</div>
	</div>
</div>