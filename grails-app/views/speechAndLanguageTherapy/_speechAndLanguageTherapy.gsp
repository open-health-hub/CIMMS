<div id="speechAndLanguageTherapyForm">
    <g:render template="/templates/sectionHead"></g:render>
    <g:hiddenField name="careActivityId" value="${careActivityInstance.id}"></g:hiddenField>
    
    <div class="containPanel" data-bind="css : {changed: tracker().somethingHasChanged }">
       <div data-bind="html: errorsAsList"></div>   
       <div id="speechAndLanguageTherapy">
       			<div class="page-header">
				<h1>Speech and language therapy</h1>
			</div>
              <g:render template="/assessment/therapy/communication" 
                                model="['careActivityInstance':careActivityInstance,'errorList':errorList]"/>
              <div class="clearfix"></div>
              <g:render template="/assessment/therapy/swallowing" 
                                model="['careActivityInstance':careActivityInstance,'errorList':errorList]"/>
                
        </div> <!-- End of speechAndLanguageTherapy -->
    </div> <!-- End of container -->

	<!-- 
	<hr />
	<h2>Debug</h2>
	
	<div data-bind="text: ko.toJSON(speechAndLanguageTherapyViewModel)"></div>
	
	 -->
 
    
</div>
