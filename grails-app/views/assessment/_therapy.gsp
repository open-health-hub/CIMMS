<div id="therapyForm">	
	<g:render template="/templates/sectionHead"></g:render>
	<g:hiddenField name="careActivityId" value="${careActivityInstance.id}"></g:hiddenField>
	<div class="containPanel " data-bind="css : {changed: tracker().somethingHasChanged }">

	 <div data-bind="html: errorsAsList"></div>
	 
	
<!--  
 <hr />
<h2>Debug</h2>

<div data-bind="text: ko.toJSON(therapyManagementViewModel)"></div>
 -->
	

	
		<div id="therapy">	
								
								
								

		  		<div class="span-24-empty-line">
		</div>		
	</div>
	</div>
	


</div>