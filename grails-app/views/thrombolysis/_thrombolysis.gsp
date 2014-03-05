<div class="pull-left sidebar" >
    
    <div class="sidebar-head">	    
		<h4>Thrombolysis</h4>
    </div>
    
    <tmpl:/templates/patientInfo careActivityInstance=${careActivityInstance} page="thrombolysis"/>
</div>
<div id="thrombolysisForm"  class="data-entry-narrow">
	<g:render template="/templates/sectionHead"></g:render>	
	<div class="containPanel" data-bind="css : {changed: tracker().somethingHasChanged }">
		<g:hiddenField name="careActivityId" value="${careActivityInstance.id}"></g:hiddenField>
		<div data-bind="html: errorsAsList"></div>
		
		<div class="page-header"><h1>Thrombolysis</h1></div>
		
		<div class="question-block">
			<div class="question-alt">
				<label>Was the patient given thrombolysis? <small>(Note: if image not done, enter "No")</small></label>  	
			</div>
		    <div class="answer-block">	
		  
				  <g:buttonRadioGroup 
				  									name="givenThrombolysis"
													labels="['Yes','No' ]" 
													values="['yes','no']"
													bind="thrombolysisManagement"
													spans="[3,3]"
													class="answer"
													value="${careActivityInstance?.careActivityProperties?.thrombolysed}" >
																${it.radio} 
																${it.label}
					</g:buttonRadioGroup>
							
			 </div>
		 </div>
		 <div class="clearfix spacer-1"></div>
		 <g:render template="/thrombolysis/noThrombolysis" model="['careActivityInstance':careActivityInstance,'errorList':errorList]"></g:render>
	 	 <g:render template="/thrombolysis/yesThrombolysis" model="['careActivityInstance':careActivityInstance,'errorList':errorList]"></g:render>  
	</div><!-- end of containPanel -->	
</div>	
