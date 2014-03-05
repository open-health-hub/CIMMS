<div id="dischargeAssessmentForm">
	<g:render template="/templates/sectionHead"></g:render>
	<g:hiddenField name="careActivityId" value="${careActivityInstance.id}"></g:hiddenField>
	<div class="containPanel"
		data-bind="css : {changed: tracker().somethingHasChanged }">
		<div data-bind="html: errorsAsList"></div>


		<div id="discharge">

				<g:render template="/discharge/assessment/assessments" 
								model="['careActivityInstance':careActivityInstance,'errorList':errorList]"/>
		
		</div>
		
	</div>

</div>