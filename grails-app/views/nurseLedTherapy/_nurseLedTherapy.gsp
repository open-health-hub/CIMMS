<div id="nurseLedTherapyForm">
    <g:render template="/templates/sectionHead"></g:render>
    <g:hiddenField name="careActivityId" value="${careActivityInstance.id}"></g:hiddenField>
    
    <div class="containPanel" data-bind="css : {changed: tracker().somethingHasChanged }">
       <div data-bind="html: errorsAsList"></div>   
       <div id="nurseLedTherapy">
       <g:render template="/assessment/therapy/rehab" 
                                model="['careActivityInstance':careActivityInstance,'errorList':errorList]"/>     
        
                
        </div> <!-- End of nurseLedTherapy -->
    </div> <!-- End of container -->
    
    

	<!-- 
	<hr />
	<h2>Debug</h2>
	
	<div data-bind="text: ko.toJSON(nurseLedTherapyViewModel)"></div>
	
	 -->
 
    
</div>
