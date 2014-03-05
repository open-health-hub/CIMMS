<div id="occupationalTherapyForm">
    <g:render template="/templates/sectionHead"></g:render>
    <g:hiddenField name="careActivityId" value="${careActivityInstance.id}"></g:hiddenField>
    
    <div class="containPanel" data-bind="css : {changed: tracker().somethingHasChanged }">
       <div data-bind="html: errorsAsList"></div>   
       <div id="occupationalTherapy">
       <g:render template="/assessment/therapy/occupationalTherapist" 
                 model="['careActivityInstance':careActivityInstance,'errorList':errorList]"/>
        <g:render template="/assessment/therapy/cognitiveStatus" 
                  model="['careActivityInstance':careActivityInstance,'errorList':errorList]"/>
        
                
        </div> <!-- End of occupationalTherapy -->
    </div> <!-- End of container -->

	<!-- 
	<hr />
	<h2>Debug</h2>
	
	<div data-bind="text: ko.toJSON(occupationalTherapyViewModel)"></div>
	
	 -->
 
    
</div>
