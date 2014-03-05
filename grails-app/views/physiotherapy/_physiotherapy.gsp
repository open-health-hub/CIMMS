<div id="physiotherapyForm">
    <g:render template="/templates/sectionHead"></g:render>
    <g:hiddenField name="careActivityId" value="${careActivityInstance.id}"></g:hiddenField>
    
    <div class="containPanel" data-bind="css : {changed: tracker().somethingHasChanged }">
       <div data-bind="html: errorsAsList"></div>   
       <div id="physiotherapy">
       <g:render template="/assessment/therapy/physiotherapy" 
                 model="['careActivityInstance':careActivityInstance,'errorList':errorList]"/>
    
                
        </div> <!-- End of physiotherapy -->
    </div> <!-- End of container -->

	<!-- 
	<hr />
	<h2>Debug</h2>
	
	<div data-bind="text: ko.toJSON(physiotherapyViewModel)"></div>
	
	 -->
 
    
</div>
