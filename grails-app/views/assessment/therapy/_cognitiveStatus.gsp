
<div class="clearfix"></div>
                                             
<div>

	<div class="page-header"><h2>Cognitive status</h2></div>

	<div class="question-block">
	
		<div class="question-alt">
			<label>Has the patient's cognitive status been assessed using a validated tool?</label>
		</div>
		
		<div class="answer-block">	
			<g:buttonRadioGroup
				class="answer"
				name="cognitiveStatusAssessed" labels="['Yes' , 'No']"
				bind="therapyManagement"
				values="['true','false']" spans="[3,3]" value="">
				${it.radio}
				${it.label}
			</g:buttonRadioGroup>
		</div>

	</div>

	<div id="cognitiveStatusAssessmentDateTime" data-bind="visible: therapyManagement.cognitiveStatusAssessed() == 'true' ">
	
	    <div class="question-block">
			<div class="question-alt"><label>Date</label></div>
			<div class="answer-block">
				<div class="answer value"  data-bind="inError:'therapyManagement.cognitiveStatusAssessmentDate'">
					<g:textField style="width:106px" class="standardDatePicker " data-bind="value: therapyManagement.cognitiveStatusAssessmentDate"
						name="cognitiveStatusAssessmentDate"  />
				</div>
			</div>
		</div>
				<%-- 
				<div class="span-2 ">Time</div>
				<div class="span-3 value  " style="vertical-align: 2px;" data-bind="inError:'therapyManagement.cognitiveStatusAssessmentTime'">
					<g:textField style="width:50px;" class="time" data-bind="value: therapyManagement.cognitiveStatusAssessmentTime"
						name="cognitiveStatusAssessmentTime" value="" />
				</div>
				 --%>
	</div>
	
	<div id="noCognitiveStatusAssessmentReasons" data-bind="visible: therapyManagement.cognitiveStatusAssessed() == 'false' ">
	
	<div class="question-block">
			
		<div class="question-alt"  data-bind="inError:'therapyManagement.cognitiveStatusNoAssessmentType'">
			<label>What was the reason?</label>
		</div>
	
		<div class="answer-block">
			<div class="answer">
	        	<input  type="radio"  
	                data-bind="checkedWithToggle: therapyManagement.cognitiveStatusNoAssessmentReason" 
	                name="cognitiveStatusNoAssessmentReason" 
	                id="cognitiveStatusNoAssessmentReasonUnwell" 
	                value="unwell" />
	        	<label 
	               for="cognitiveStatusNoAssessmentReasonUnwell" >Patient Unwell</label>
	    	</div>
	    	<div class="answer">
	        	<input  type="radio"  
	                data-bind="checkedWithToggle: therapyManagement.cognitiveStatusNoAssessmentReason" 
	                name="cognitiveStatusNoAssessmentReason" 
	                id="cognitiveStatusNoAssessmentReasonRefused" 
	                value="refused" />
	        	<label  
	               for="cognitiveStatusNoAssessmentReasonRefused" >Patient Refused</label>
	    	</div>
    
	   		<div class="answer">
	        	<input  type="radio"  
	                data-bind="checkedWithToggle: therapyManagement.cognitiveStatusNoAssessmentReason" 
	                name="cognitiveStatusNoAssessmentReason" 
	                id="cognitiveStatusNoAssessmentReasonOrganisational" 
	                value="organisational" />
	        	<label  
	               for="cognitiveStatusNoAssessmentReasonOrganisational" >Organisational reasons</label>
	    	</div>            	
     
	    	<div class="answer">
	        	<input  type="radio"  
	                data-bind="checkedWithToggle: therapyManagement.cognitiveStatusNoAssessmentReason" 
	                name="cognitiveStatusNoAssessmentReason" 
	                id="cognitiveStatusNoAssessmentReasonUnknown" 
	                value="unknown" />
	        	<label  
	               for="cognitiveStatusNoAssessmentReasonUnknown" >Unknown</label>
	    	</div>	
		</div>
		</div>
	</div>
</div>

